package de.bht.lcb.runtime;

import java.util.List;

import de.bht.lcb.config.DeviceConfig;
import de.bht.lcb.config.IOVarDescription;

/**
 * a device providing read/write access to a range of IO variables and possibly
 * to custom operations (comparable to stored procedures in databases)
 * 
 * TODO: we need @JsonTypeInfo for being able to pass device proxies via http
 * requests to remote uigateways
 * 
 * devices should be able to publish events to a gateway which will then be
 * propagated to the fois that are associated with that device. This way,
 * devices can implement local control cycles to avoid continuous polling by
 * fois via the gateway (which will possibly be done over the network). We
 * currently try implement only a very rudimentary version of this mechanism...
 * it is not yet dealt with at the level of the configuration (where we could
 * set filters for events in fois etc.)
 * 
 * currently, device events are strings, i.e. any additional information
 * relevant for the foi would need to be retrieved via polling (event mechanism
 * could be characterised as "push2poll", comparable to push notifications for
 * mobile platforms)
 * 
 * TODO as the uigateway foresees to receice ioVarChanged messages, it would
 * make sense to add such a notification mechanism also to the iogateway in
 * order to avoid push2poll roundtrips!
 * 
 * @author joern.kreutel
 */
public interface Device {

	public String getId();

	/**
	 * TODO: the Object type for variable values might be too coarse-grained
	 * 
	 * @param varId
	 * @return
	 */
	public Object read(String varId);

	public void write(String varId, Object varValue);

	public void run(String operationId, String... operationArgs);

	/**
	 * we need to publish the iovars that are provided by the device
	 */
	public List<IOVarDescription> getIOVars();

	/**
	 * set the config
	 * 
	 * @param devconf
	 */
	public void setConfig(DeviceConfig devconf);

	/**
	 * foresee an initialisation method for devices
	 */
	public void initialise();

	/**
	 * this allows the feedback mechanism for events triggered by devices
	 */
	public void setGateway(IOGateway gateway);

	public void publishEvent(String deviceevent);

}
