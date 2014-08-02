package de.bht.lcb.config;

import java.util.List;

/**
 * @author joern.kreutel
 */
public interface FOIConfig extends LCBEntity, LCBInstance, LCBPropertiesCollection {
			
	public void setFoiType(FOIType type);
	
	public FOIType getFoiType();
	
	public List<FOIDeviceRef> getDevicerefs();
	
}
