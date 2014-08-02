package de.bht.lcb.runtime;

import java.util.List;

import de.bht.lcb.config.FOIConfig;

/**
 * the FOI interface is quite similar to the device interface. However, note
 * that the two components are situated at a different architectural level, i.e.
 * a single FOI's getters/setters may operate on the variables of various
 * underlying devices
 * 
 * FOI methods will either be invoked via the UIGateway by an action of the user
 * interface or by other FOIs (see a FOI hierarchy like room - floor - building
 * - premise)
 * 
 * The mapping of varIds invoked on the FOI to the vars of the devices that make
 * the values available will be determined based on the LCB config that will be
 * passed to FOI implementations on initialisation (could be made available by
 * an AbstractFOIImpl that will be extended by the particular FOI classes)
 * 
 * @author joern.kreutel
 */
public interface FOI {

	/**
	 * pass a gateway via which the FOI retrieves variable values - for remote
	 * gateways this might be a proxy implementation of a JAX-RS resource. For
	 * local gateways it will just be the Java SE gateway implementation.
	 * 
	 * On the first run, we will restrict FOIs to access all devices via the
	 * same gateway, but this could be modified provided a more sophisticated
	 * gateway client implementation
	 */
	public void bindIO(IOGateway iogateway);

	public void bindUI(UIGateway iogateway);
	
	public Object get(String varId);

	public void set(String varId, Object varValue);

	public void run(String operationId, String... operationsArgs);

	/**
	 * this allows to retrieve the history of some variable for a given time
	 * interval. TODO: The class used for representing a single history item
	 * needs to be determined
	 */
	public List<?> getHistory(String varId, long from, long to);

	public void setConfig(FOIConfig foiconf);

	public void initialise();
	
	public String getId();
	
	/**
	 * this method lets a foi react to a device event that is passed by the iogateway
	 * @param deviceevent
	 */
	public void onDeviceEvent(String deviceid,String deviceevent);
	
}
