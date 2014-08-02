package de.bht.lcb.config;


/**
 * @author joern.kreutel
 */
public interface DeviceType extends LCBEntity, IOVarDescriptionCollection, LCBPropertiesCollection, JClassReference {
	
	public String getJclass();
	
	public LCBHistory getHistory();

}
