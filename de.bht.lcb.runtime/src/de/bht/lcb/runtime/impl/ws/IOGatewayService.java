package de.bht.lcb.runtime.impl.ws;

import java.util.List;

import de.bht.lcb.config.IOGatewayConfig;
import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.runtime.Device;
import de.bht.lcb.runtime.FOI;
import de.bht.lcb.runtime.IOGateway;

/**
 * we add this here only as a very brief sketch. This class should later be
 * placed in a separate project that has dependencies to a Java EE runtime and
 * to keep the lcb.runtime project free from such dependencies (only using
 * JAX-RS and Jackson libs)
 * 
 * CHECK: this gateway service should be used by both sides of a remote gateway
 * scenario, i.e. both by the side that publishes devices, and by the receiver
 * 
 * @author joern.kreutel
 */
public class IOGatewayService implements IOGateway {

	/**
	 * the IOGateway that is used for servive the remote requests - it will be
	 * initialised by JAX-RS dependency injection of ServletContext and reading
	 * it out from there - see the ESA examples for JAX-RS :)
	 */
	private IOGateway gateway;

	@Override
	public void register(Device device) {
		this.gateway.register(device);
	}

	@Override
	public void unregister(String deviceId) {
		this.gateway.unregister(deviceId);
	}

	@Override
	public Object read(String deviceId, String varid) {
		return this.gateway.read(deviceId, varid);
	}

	@Override
	public void write(String deviceId, String varId, Object varValue) {
		this.gateway.write(deviceId, varId, varValue);
	}

	@Override
	public void run(String deviceId, String operationId,
			String... operationArgs) {
		this.gateway.run(deviceId, operationId, operationArgs);
	}

	@Override
	public List<?> getHistory(String deviceId, String varId, long from, long to) {
		return this.gateway.getHistory(deviceId, varId, from, to);
	}

	@Override
	public List<IOVarDescription> getIOVarsForDevice(String deviceId) {
		return this.gateway.getIOVarsForDevice(deviceId);
	}

	@Override
	public void publishDeviceEvent(String id, String deviceevent) {
		this.gateway.publishDeviceEvent(id, deviceevent);
	}

	/*
	 * these methods are not published as it is only used on the client-side
	 */

	@Override
	public void setConfig(IOGatewayConfig ioGateway) {
		// TODO Auto-generated method stub
	}

	@Override
	public void subscribeForDeviceEvent(FOI foi, String deviceid) {
		// TODO Auto-generated method stub
	}

}
