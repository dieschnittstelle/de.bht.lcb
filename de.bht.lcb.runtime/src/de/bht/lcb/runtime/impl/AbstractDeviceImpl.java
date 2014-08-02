package de.bht.lcb.runtime.impl;

import java.util.List;

import org.apache.log4j.Logger;

import de.bht.lcb.config.DeviceConfig;
import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.config.impl.LCBException;
import de.bht.lcb.runtime.Device;
import de.bht.lcb.runtime.IOGateway;

/**
 * @author joern.kreutel
 */
public abstract class AbstractDeviceImpl implements Device {

	protected static Logger logger = Logger.getLogger(AbstractDeviceImpl.class);

	private DeviceConfig config;

	/*
	 * the gateway used for publishing events
	 */
	private IOGateway gateway;

	@Override
	public String getId() {
		return this.config.getId().stringify();
	}

	@Override
	public void run(String operationId, String... operationArgs) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IOVarDescription> getIOVars() {
		return this.config.getAllIOVarDescriptions();
	}

	@Override
	public void setConfig(DeviceConfig devconf) {
		this.config = devconf;
	}

	/**
	 * this allows the feedback mechanism for events triggered by devices
	 */
	public void setGateway(IOGateway gateway) {
		this.gateway = gateway;
	}

	public void publishEvent(String deviceevent) {
		try {
			this.gateway.publishDeviceEvent(this.getId(), deviceevent);
		} catch (Exception e) {
			logger.error("got an exception during publishing event "
					+ deviceevent
					+ " from device "
					+ this.getId()
					+ ". If this occurs during startup it might be due to the lcb node not being setup completely yet",e);
		}
	}

	/*
	 * the read/write methods cannot be implemented that generically as for
	 * AbstractFOI because it will be due to the particular device implemenation
	 * to "translate" the invocations to calls to the underlying devices (e.g.
	 * WagoController). We therefore only provide the utility of reading the
	 * IOVarDescription based on which this call can be done.
	 */

	@Override
	public Object read(String varId) {
		IOVarDescription desc = this.config.getIOVarDescription(varId);
		if (desc == null) {
			String err = "read(): cannot resolve variable " + varId + "!";
			throw new LCBException(err);
		}
		return this.readIOVar(desc);
	}

	@Override
	public void write(String varId, Object varValue) {
		IOVarDescription desc = this.config.getIOVarDescription(varId);
		if (desc == null) {
			String err = "write(): cannot resolve variable " + varId + "!";
			throw new LCBException(err);
		}
		this.writeIOVar(desc, varValue);
	}

	/*
	 * the following methods are meant to be employed by the concrete subclasses
	 * of this class
	 */

	/**
	 * utility: get a property value
	 * 
	 * @param prop
	 * @return
	 */
	protected Object getConfigProperty(String prop) {
		return this.config.getPropertyValue(prop);
	}

	/**
	 * give access to the complete config if necessary
	 * 
	 * @return
	 */
	protected DeviceConfig getConfig() {
		return this.config;
	}

	protected abstract Object readIOVar(IOVarDescription desc);

	protected abstract void writeIOVar(IOVarDescription desc, Object varValue);

}
