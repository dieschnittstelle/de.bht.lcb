package de.bht.lcb.runtime.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bht.lcb.config.IOGatewayConfig;
import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.runtime.Device;
import de.bht.lcb.runtime.FOI;
import de.bht.lcb.runtime.IOGateway;

/**
 * a simple implemenation of a gateway - without history! - simply using a map
 * of device objects
 * 
 * @author joern.kreutel
 */
public class SimpleIOGatewayImpl implements IOGateway {

	private Map<String, Device> devices = new HashMap<String, Device>();
	// another map of subscribers to device event
	private Map<String, List<FOI>> deviceEventSubscribers = new HashMap<String, List<FOI>>();

	private IOGatewayConfig config;

	@Override
	public void register(Device device) {
		this.devices.put(device.getId(), device);
	}

	@Override
	public void unregister(String deviceId) {
		this.devices.remove(deviceId);
	}

	@Override
	public Object read(String deviceId, String varId) {
		return this.devices.get(deviceId).read(varId);
	}

	@Override
	public void write(String deviceId, String varId, Object varValue) {
		this.devices.get(deviceId).write(varId, varValue);
	}

	@Override
	public void run(String deviceId, String operationId,
			String... operationArgs) {
		this.devices.get(deviceId).run(operationId, operationArgs);
	}

	@Override
	public List<?> getHistory(String deviceId, String varId, long from, long to) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<IOVarDescription> getIOVarsForDevice(String deviceId) {
		return this.devices.get(deviceId).getIOVars();
	}

	@Override
	public void setConfig(IOGatewayConfig config) {
		this.config = config;
	}

	@Override
	public void publishDeviceEvent(String deviceid, String deviceevent) {
		// check whether we have subscribers
		List<FOI> subscribers = this.deviceEventSubscribers.get(deviceid);
		if (subscribers != null) {
			for (FOI foi : subscribers) {
				foi.onDeviceEvent(deviceid, deviceevent);
			}
		}
	}

	/**
	 * let a foi subscribe for events of a given device. Currently all events
	 * will be passed to the foi, configuration might allow filtering here -
	 * could be added to the FOIConfig and be retrieved here!
	 * 
	 * @param foi
	 * @param deviceid
	 */
	public void subscribeForDeviceEvent(FOI foi, String deviceid) {
		List<FOI> subscribers = this.deviceEventSubscribers.get(deviceid);
		if (subscribers == null) {
			subscribers = new ArrayList<FOI>();
			this.deviceEventSubscribers.put(deviceid, subscribers);
		}	
		if (!subscribers.contains(foi)) {
			subscribers.add(foi);
		}
	}

}
