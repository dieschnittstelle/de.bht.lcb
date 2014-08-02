package de.bht.lcb.runtime;

import java.util.List;

import de.bht.lcb.config.IOGatewayConfig;
import de.bht.lcb.config.IOVarDescription;

/**
 * iogateways are the access layer to devices for FOIs
 * 
 * this interface will be implemented in three ways: - as a Java SE class that
 * keeps registered devices in a map and retrieves them on invocation of a
 * read/write method - in case the gateways is initialised inside of a web
 * container it will be made accessible via the servlet context - as a JAX-RS
 * resource implementation that handles remote invocations and redirects them to
 * the gateway implementation retrieved from the servlet context - as a
 * client-side proxy to a JAX-RS resource that is used both by FOIs and by LCB
 * nodes that publish devices to an external gateway
 * 
 * - in case we have an LCB node publishing devices to an external proxy, it
 * will both use the Java SE gateway implementation for locally tracking devices
 * and will publish the devices as instances of DeviceProxy to the external
 * gateway. Incoming requests will be handled by the JAX-RS ressource
 * implementation
 * 
 * IDs of all devices (and FOIs) need to be unique over a whole LCB cluster.
 * Rather than dealing with that at the level of a (distriuted) lcb config, one
 * could also use dynamically created URIs that use the Id of the different LCB
 * Nodes of a cluster as qualifiers
 * 
 * @author joern.kreutel
 */
// @Path("/devices")
// @Consumes({"application/json"})
// @Produces({"application/json"})
public interface IOGateway {

	// POST
	public void register(Device device);

	// DELETE
	// @Path("/{deviceId}")
	public void unregister(String deviceId);

	// @GET
	// @Path("/{deviceId}/{varId}")
	public Object read(String deviceId, String varid);

	// @PUT
	// @Path("/{deviceId}/{varId}")
	public void write(String deviceId, String varId, Object varValue);

	/**
	 * TODO: need to check whether ... arguments may be passed as arrays-valued
	 * query params in JAX-RS, otherwise a list of Strings needs to be used
	 * 
	 * @param deviceId
	 * @param operationId
	 * @param operationArgs
	 */
	// @POST
	// @Path("/{deviceId}/operations/{operationId}")
	public void run(String deviceId, String operationId,
			String... operationArgs);

	/**
	 * the gateway is also responsible for making the history of a variable on
	 * some device accessible, i.e. gateway implementations may retrieve
	 * variable values independently of any requests by a gateway client and
	 * will store them
	 */
	// @GET
	// @Path("/{deviceId}/history/{varId}")
	// from and to will be passed as @QueryParam
	public List<?> getHistory(String deviceId, String varId, long from, long to);

	/**
	 * retrieve the iovars provided by a device - this is used when binding a
	 * foi to a gateway
	 * 
	 * @param deviceId
	 * @return
	 */
	// @GET
	// @Path("/{deviceId}/config/iovars")
	public List<IOVarDescription> getIOVarsForDevice(String deviceId);

	// over the network, this needs to be dealt with by a request to the remote
	// gateway, to which the devices have been registered, i.e. gateways
	// communicate with each other bidirectionally (register/publish vs.
	// read/write/getHistory)
	// @POST
	// @Path("/{deviceId}/events/{devicevent}")
	public void publishDeviceEvent(String deviceId, String deviceevent);

	// the following methods will not need to be published over the network as
	// they are used locally on the client side(s)
	public void subscribeForDeviceEvent(FOI foi, String deviceid);

	public void setConfig(IOGatewayConfig ioGateway);

}
