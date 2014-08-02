package de.bht.lcb.config;

import java.util.Collection;

/**
 * @author joern.kreutel
 * abstracts away from provisioning/consuming of iovars
 */
public interface IOVarDescriptionCollection {

	public Collection<String> getIOVarNames();

	public IOVarDescription getIOVarDescription(String iovarid);
	
}
