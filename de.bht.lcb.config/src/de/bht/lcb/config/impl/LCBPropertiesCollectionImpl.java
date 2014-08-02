package de.bht.lcb.config.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.bht.lcb.config.LCBPropertiesCollection;
import de.bht.lcb.config.LCBProperty;

/**
 * @author joern.kreutel
 */
public class LCBPropertiesCollectionImpl implements LCBPropertiesCollection {
	
	private Map<String, LCBProperty> props = new HashMap<String, LCBProperty>();

	@Override
	public Collection<String> getPropertyNames() {
		return props.keySet();
	}

	@Override
	public String getPropertyValue(String prop) {
		return props.get(prop).getValue();
	}

	@Override
	public LCBProperty getProperty(String prop) {
		return props.get(prop);
	}

	public void addProperty(LCBPropertyImpl prop) {
		this.props.put(prop.getName(), prop);
	}
	
	public String toString() {
		return String.valueOf(props);
	}

}
