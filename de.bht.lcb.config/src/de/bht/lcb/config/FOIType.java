package de.bht.lcb.config;

/**
 * @author joern.kreutel
 */
public interface FOIType extends LCBEntity, JClassReference, LCBPropertiesCollection {

	public IOVarDescriptionCollection getIOVarRequirements();

	public IOVarDescriptionCollection getIOVarOptions();

	/**
	 * local iovars are iovars that are used locally by a foi without being
	 * bound 1:1 to iovars of the underlying devices
	 * 
	 * @return
	 */
	public IOVarDescriptionCollection getLocalIOVars();

}
