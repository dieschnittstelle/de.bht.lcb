package de.bht.lcb.config;

/**
 * this represents a reference to a device by a foi, through which the foi declares that it will use the device's iovars 
 * 
 * @author joern.kreutel
 */
public interface FOIDeviceRef {
	
	public LCBId getDeviceId();
	
	public IOVarDescriptionCollection getExcludedIOVars();
	
}
