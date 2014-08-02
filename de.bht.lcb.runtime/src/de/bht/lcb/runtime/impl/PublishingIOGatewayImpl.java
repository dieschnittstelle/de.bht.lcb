package de.bht.lcb.runtime.impl;

import de.bht.lcb.runtime.Device;
import de.bht.lcb.runtime.IOGateway;

/**
 * this is a gateway that publishes its devices as proxies to a remote gateway
 * 
 * incoming calls will be handled via a JAX-RS resource implementation of
 * IOGateway
 * 
 * @author joern.kreutel
 */
public class PublishingIOGatewayImpl extends SimpleIOGatewayImpl {

	/**
	 * this proxy needs to be instantiated based on the given config that will
	 * also state the url of the remote gateway
	 */
	private IOGateway remoteGatewayProxy;

	/**
	 * also the publisherUrl needs to be set when initialising this gateway from
	 * the config - as this gateway needs to be run inside a servlet container
	 * it may retrieve the ip address of the container from the ServletContext
	 */
	private String publisherUrl;

	@Override
	public void register(Device device) {
		super.register(device);
		this.remoteGatewayProxy.register(new DeviceProxyImpl(publisherUrl,
				device));
	}

	@Override
	public void unregister(String deviceId) {
		super.unregister(deviceId);
		this.remoteGatewayProxy.unregister(deviceId);
	}

}
