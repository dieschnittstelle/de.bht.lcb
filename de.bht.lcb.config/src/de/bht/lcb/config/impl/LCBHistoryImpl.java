package de.bht.lcb.config.impl;

import java.util.Collection;

import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.config.IOVarDescriptionCollection;
import de.bht.lcb.config.LCBHistory;

/**
 * @author joern.kreutel
 */
public class LCBHistoryImpl implements LCBHistory {

	private IOVarDescriptionCollection iovars;
	private String provider;
	
	@Override
	public Collection<String> getIOVarNames() {
		return this.iovars.getIOVarNames();
	}

	@Override
	public IOVarDescription getIOVarDescription(String iovarid) {
		return this.iovars.getIOVarDescription(iovarid);
	}

	@Override
	public String getProvider() {
		return this.provider;
	}

	public void setIOVars(IOVarDescriptionCollection iovars) {
		this.iovars = iovars;		
	}
	
}
