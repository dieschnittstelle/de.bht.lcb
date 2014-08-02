package de.bht.lcb.config.impl;

import java.util.Collection;

import de.bht.lcb.config.FOIType;
import de.bht.lcb.config.IOVarDescriptionCollection;
import de.bht.lcb.config.LCBPropertiesCollection;
import de.bht.lcb.config.LCBProperty;

/**
 * @author joern.kreutel
 */
public class FOITypeImpl extends AbstractLCBEntityImpl implements FOIType {

	private LCBPropertiesCollection props = new LCBPropertiesCollectionImpl();
	
	private IOVarDescriptionCollection options = new IOVarDescriptionCollectionImpl();
	
	private IOVarDescriptionCollection requirements = new IOVarDescriptionCollectionImpl();
	
	private IOVarDescriptionCollection localvars = new IOVarDescriptionCollectionImpl();
	
	private String jclass;
	
	@Override
	public IOVarDescriptionCollection getIOVarRequirements() {
		return this.requirements;
	}

	@Override
	public IOVarDescriptionCollection getIOVarOptions() {
		return this.options;
	}

	public void setIOVarOptions(IOVarDescriptionCollection options) {
		this.options = options;
	}

	public void setIOVarRequirements(IOVarDescriptionCollection requirements) {
		this.requirements = requirements;
	}

	@Override
	public IOVarDescriptionCollection getLocalIOVars() {
		return this.localvars;
	}
	
	public void setLocalIOVars(IOVarDescriptionCollection iovars) {
		this.localvars = iovars;
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("{FOIType ");
		buf.append(getId());
		buf.append(" localvars:");
		buf.append(getLocalIOVars());
		buf.append(" requirements:");
		buf.append(getIOVarRequirements());
		buf.append(" options:");
		buf.append(getIOVarOptions());
		
		return buf.toString();
	}

	@Override
	public String getJclass() {
		return this.jclass;
	}

	public void setJclass(String jclass) {
		this.jclass = jclass;
	}

	@Override
	public Collection<String> getPropertyNames() {
		return this.props.getPropertyNames();
	}

	@Override
	public String getPropertyValue(String prop) {
		return this.props.getPropertyValue(prop);
	}

	@Override
	public LCBProperty getProperty(String prop) {
		return this.props.getProperty(prop);
	}
	
	public void setProperties(LCBPropertiesCollection props) {
		this.props = props;
	}

}
