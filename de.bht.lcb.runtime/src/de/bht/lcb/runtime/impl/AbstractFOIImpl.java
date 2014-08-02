package de.bht.lcb.runtime.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import de.bht.lcb.config.FOIConfig;
import de.bht.lcb.config.FOIDeviceRef;
import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.config.impl.LCBException;
import de.bht.lcb.runtime.FOI;
import de.bht.lcb.runtime.IOGateway;
import de.bht.lcb.runtime.UIGateway;

/**
 * in case a foi shall start running independently of any user input this must
 * be done by starting a separate thread once the initialise method is called!
 * 
 * @author joern.kreutel
 */
public abstract class AbstractFOIImpl implements FOI {

	protected static Logger logger = Logger.getLogger(AbstractFOIImpl.class);

	private FOIConfig config;

	private IOGateway iogateway;

	/**
	 * we also have a reference to the ui gateway for allowing the foi to publish output to the gateway
	 */
	private UIGateway uigateway;
	
	/**
	 * two maps of iovars and devices that are provided by the iovars
	 */
	private Map<String, String> requiredIOVarsMapping = new HashMap<String, String>();
	private Map<String, String> optionalIOVarsMapping = new HashMap<String, String>();

	// we have a map that stores the *values* of local iovariables that are not
	// bound to iovars of devices - particular foi impls may use instance
	// attribute for this purpose, though
	private Map<String, Object> localIOVarValues = new HashMap<String, Object>();
	
	public void bindUI(UIGateway uigateway) {
		this.uigateway = uigateway;
	}

	@Override
	public void bindIO(IOGateway iogateway) {
		
		logger.info(this.getId() + ": binding foi to iogateway: " + iogateway);

		this.iogateway = iogateway;

		// we first instantiate the maps with the keys using the config
		for (String reqvar : config.getFoiType().getIOVarRequirements()
				.getIOVarNames()) {
			this.requiredIOVarsMapping.put(reqvar, null);
		}

		for (String reqvar : config.getFoiType().getIOVarOptions()
				.getIOVarNames()) {
			this.optionalIOVarsMapping.put(reqvar, null);
		}

		// we then iterate over the devices and try to populate the maps (we do
		// not consider exclusions of iovars for particular devices for the time
		// being)
		for (FOIDeviceRef devref : config.getDevicerefs()) {
			// query the gateway to pass us the iovars published by the given
			// device
			String deviceid = devref.getDeviceId().stringify();
			for (IOVarDescription desc : this.iogateway
					.getIOVarsForDevice(deviceid)) {
				if (this.requiredIOVarsMapping.containsKey(desc.getName())) {
					logger.info("required iovar " + desc.getName()
							+ " is provided by device: " + deviceid);
					this.requiredIOVarsMapping.put(desc.getName(), deviceid);
				} else if (this.optionalIOVarsMapping.containsKey(desc
						.getName())) {
					logger.info("optonal iovar " + desc.getName()
							+ " is provided by device: " + deviceid);
					this.optionalIOVarsMapping.put(desc.getName(), deviceid);
				}
			}

			// we also subscribe to events from that device
			iogateway.subscribeForDeviceEvent(this, deviceid);
		}

		// we then need to verify whether all required variables are bound
		for (String varid : this.requiredIOVarsMapping.keySet()) {
			if (this.requiredIOVarsMapping.get(varid) == null) {
				String err = "inconsistent configuration. required iovar "
						+ varid + " is not bound by any device!";
				logger.error(err);
				throw new LCBException(err);
			}
		}

		for (String varid : this.optionalIOVarsMapping.keySet()) {
			if (this.optionalIOVarsMapping.get(varid) == null) {
				logger.info("optional iovar is not bound.");
			}
		}

		for (String varname : config.getFoiType().getLocalIOVars()
				.getIOVarNames()) {
			// check whether we value has been pre-set
			Object value = config.getFoiType().getLocalIOVars()
					.getIOVarDescription(varname).getValue();
			if (value != null) {
				logger.info("using pre-set value for iovar " + varname + ": "
						+ value);
			}
			this.localIOVarValues.put(varname, value);
		}

	}

	@Override
	public Object get(String varId) {

		// logger.debug("local: " + localIOVarValues);
		// logger.debug("required: " + requiredIOVarsMapping);
		// logger.debug("optional: " + optionalIOVarsMapping);
		// we need to check whether the variable is a local, required or
		// optional one
		if (this.localIOVarValues.containsKey(varId)) {
			return localIOVarValues.get(varId);
		} else if (this.requiredIOVarsMapping.containsKey(varId)) {
			return this.iogateway.read(this.requiredIOVarsMapping.get(varId),
					varId);
		} else if (this.optionalIOVarsMapping.containsKey(varId)) {
			return this.iogateway.read(this.optionalIOVarsMapping.get(varId),
					varId);
		}

		String err = "get(): got reference to an unknown variable: " + varId
				+ "!";
		throw new LCBException(err);
	}

	@Override
	public void set(String varId, Object varValue) {
		// we need to check whether the variable is a local, required or
		// optional one
		if (this.localIOVarValues.containsKey(varId)) {
			localIOVarValues.put(varId, varValue);
		} else if (this.requiredIOVarsMapping.containsKey(varId)) {
			this.iogateway.write(this.requiredIOVarsMapping.get(varId), varId,
					varValue);
		} else if (this.optionalIOVarsMapping.containsKey(varId)) {
			this.iogateway.write(this.optionalIOVarsMapping.get(varId), varId,
					varValue);
		} else {
			String err = "set(): got reference to an unknown variable: "
					+ varId + "!";
			throw new LCBException(err);
		}
	}

	/*
	 * the following methods are not supported for the time being
	 */

	@Override
	public void run(String operationId, String... operationsArgs) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<?> getHistory(String varId, long from, long to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfig(FOIConfig foiconf) {
		this.config = foiconf;
	}
	
	// we make the config accessible to the subclasses
	protected FOIConfig getConfig() {
		return this.config;
	}

	public String getId() {
		return this.config.getId().stringify();
	}
	
	/*
	 * the following methods are utility for publishing variable changes and events from the foi to the uigateway
	 */
	
	protected void publishEvent(String eventId,Object eventData) {
		this.uigateway.onEvent(this.getId(), eventId, eventData);
	}
	
	protected void publishIOVarChanged(String varId,Object varValue) {
		this.uigateway.onIOVarChanged(this.getId(), varId, varValue);
	}


}
