package de.bht.lcb.config.impl;

import java.util.Collection;

import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.config.LCBPropertiesCollection;
import de.bht.lcb.config.LCBProperty;

/**
 * @author joern.kreutel
 */
public class IOVarDescriptionImpl implements IOVarDescription {

	private String accesstype;
	
	private String semuri;
	
	private String name;
	
	private String interval;
	
	private String lifetime;
	
	private LCBPropertiesCollection props;
	
	private String value;
	
	private String type;
	
	@Override
	public Collection<String> getPropertyNames() {
		return props.getPropertyNames();
	}

	@Override
	public String getPropertyValue(String prop) {
		return props.getPropertyValue(prop);
	}

	public String getAccesstype() {
		return accesstype;
	}

	public void setAccesstype(String accesstype) {
		this.accesstype = accesstype;
	}

	public String getSemuri() {
		return semuri;
	}

	public void setSemuri(String semuri) {
		this.semuri = semuri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getLifetime() {
		return lifetime;
	}

	public void setLifetime(String lifetime) {
		this.lifetime = lifetime;
	}

	@Override
	public LCBProperty getProperty(String prop) {
		return props.getProperty(prop);
	}
	
	public void setProperties(LCBPropertiesCollection props) {
		this.props = props;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
