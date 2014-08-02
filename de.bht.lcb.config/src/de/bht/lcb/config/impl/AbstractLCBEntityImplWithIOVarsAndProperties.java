package de.bht.lcb.config.impl;

import java.util.Collection;

import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.config.IOVarDescriptionCollection;
import de.bht.lcb.config.LCBPropertiesCollection;
import de.bht.lcb.config.LCBProperty;

/**
 * @author joern.kreutel
 */
public abstract class AbstractLCBEntityImplWithIOVarsAndProperties extends
		AbstractLCBEntityImpl implements LCBPropertiesCollection,
		IOVarDescriptionCollection {

	private IOVarDescriptionCollection iovars = new IOVarDescriptionCollectionImpl();

	private LCBPropertiesCollection props = new LCBPropertiesCollectionImpl();

	@Override
	public Collection<String> getIOVarNames() {
		return this.iovars.getIOVarNames();
	}

	@Override
	public IOVarDescription getIOVarDescription(String iovarid) {
		return this.iovars.getIOVarDescription(iovarid);
	}

	@Override
	public Collection<String> getPropertyNames() {
		return props.getPropertyNames();
	}

	@Override
	public String getPropertyValue(String prop) {
		return props.getPropertyValue(prop);
	}
	
	public void setIOVars(IOVarDescriptionCollection iovars) {
		this.iovars = iovars;
	}
	
	@Override
	public LCBProperty getProperty(String prop) {
		return props.getProperty(prop);
	}
	
	public void setProperties(LCBPropertiesCollection props) {
		this.props = props;
	}


}
