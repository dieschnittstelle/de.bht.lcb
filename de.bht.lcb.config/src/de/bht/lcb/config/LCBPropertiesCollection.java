package de.bht.lcb.config;

import java.util.Collection;

/**
 * @author joern.kreutel
 */
public interface LCBPropertiesCollection {
	
	public Collection<String> getPropertyNames();
	
	public String getPropertyValue(String prop);

	public LCBProperty getProperty(String prop);

}
