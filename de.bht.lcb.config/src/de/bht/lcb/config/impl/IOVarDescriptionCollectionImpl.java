package de.bht.lcb.config.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.config.IOVarDescriptionCollection;

/**
 * @author joern.kreutel
 */
public class IOVarDescriptionCollectionImpl implements IOVarDescriptionCollection {

	private Map<String, IOVarDescription> iovars = new HashMap<String, IOVarDescription>();
	
	@Override
	public Collection<String> getIOVarNames() {
		return iovars.keySet();
	}

	@Override
	public IOVarDescription getIOVarDescription(String iovarname) {
		return iovars.get(iovarname);
	}
	
	public void addIOVarDescription(IOVarDescription desc) {
		this.iovars.put(desc.getName(), desc);
	}
	
	public boolean hasIOVarDescription(String iovarname) {
		return iovars.containsKey(iovarname);
	}
	
	public String toString() {
		return String.valueOf(iovars);
	}

}
