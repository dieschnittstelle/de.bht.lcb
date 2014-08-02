package de.bht.lcb.runtime.impl;

import java.util.List;

import de.bht.lcb.config.DeviceConfig;
import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.runtime.Device;
import de.bht.lcb.runtime.IOGateway;

/**
 * this is used to represent a device within a remote gateway - in particular,
 * the proxy will carry the baseUrl of its publishing gateway
 * 
 * note that the read/write methods are implemented by passing the call to the
 * respective methods of IOGateway
 * 
 * it looks as if the call to the remote gateway can be encapsulated completely
 * inside of this class...
 * 
 * @author joern.kreutel
 */
public class DeviceProxyImpl implements Device {

	private String publisherUrl;

	private String id;
	
	private List<IOVarDescription> iOVars;

	/**
	 * this is a proxy to the published device that is proxied by this
	 * object (only used on the client-side)
	 */
	// @JsonIgnore
	private IOGateway gatewayProxy;

	/**
	 * this is used for automatic serialisation / deserialisation
	 */
	public DeviceProxyImpl() {

	}

	/**
	 * this is used for instantiating a proxy from a real device
	 */
	public DeviceProxyImpl(String publisherUrl, Device proxiedDevice) {
		this.id = proxiedDevice.getId();
		this.setPublisherUrl(publisherUrl);
		this.iOVars = proxiedDevice.getIOVars();
	}

	@Override
	public Object read(String varId) {
		initialiseGatewayProxy();
		return this.gatewayProxy.read(this.id, varId);
	}

	@Override
	public void write(String varId, Object varValue) {
		initialiseGatewayProxy();
		this.gatewayProxy.write(this.id, varId, varValue);
	}

	@Override
	public void run(String operationId, String... operationArgs) {
		initialiseGatewayProxy();
		this.gatewayProxy.write(this.id, operationId, operationArgs);
	}

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPublisherUrl() {
		return publisherUrl;
	}

	public void setPublisherUrl(String publisherUrl) {
		this.publisherUrl = publisherUrl;
	}

	/**
	 * here we will initialise the gateway proxy given the publisherUrl we are
	 * carrying and using some JAX-RS implementation
	 */
	private void initialiseGatewayProxy() {
		if (this.gatewayProxy == null) {
			this.gatewayProxy = null; // TODO: implement this
		}
	}

	@Override
	public List<IOVarDescription> getIOVars() {
		return this.iOVars;
	}
	
	public void setIOVars(List<IOVarDescription> iovars) {
		this.iOVars = iovars;
	}

	/**
	 * the following methods are not required for cliend-side proxies
	 */
	
	@Override
	public void setConfig(DeviceConfig devconf) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * TODO: need to think about how this should actually be done over the network...
	 */

	@Override
	public void initialise() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGateway(IOGateway gateway) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void publishEvent(String deviceevent) {
		// TODO Auto-generated method stub
		
	}

}
