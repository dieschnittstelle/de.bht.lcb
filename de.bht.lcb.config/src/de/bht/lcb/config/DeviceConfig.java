package de.bht.lcb.config;

import java.util.List;

/**
 * this describes a particular device of a given type that 1:1 corresponds to a runtime device
 * also here, properties and iovar descriptions might be set, which might actually override the ones specified in the device type
 * 
 * @author joern.kreutel
 */
public interface DeviceConfig extends LCBEntity, LCBInstance,
		IOVarDescriptionCollection, LCBPropertiesCollection {

	public void setDeviceType(DeviceType type);

	public DeviceType getDeviceType();
	
	public List<IOVarDescription> getAllIOVarDescriptions(); 
	
}
