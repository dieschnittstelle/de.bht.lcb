package de.bht.lcb.config.impl;

import de.bht.lcb.config.DeviceType;
import de.bht.lcb.config.LCBHistory;

/**
 * @author joern.kreutel
 */
public class DeviceTypeImpl extends AbstractLCBEntityImplWithIOVarsAndProperties implements DeviceType {
	
	private String jclass;
	
	private LCBHistory history;
	
	@Override
	public String getJclass() {
		return this.jclass;
	}
	
	public void setJclass(String klass) {
		this.jclass = klass;
	}

	@Override
	public LCBHistory getHistory() {
		return this.history;
	}
	
	public void setHistory(LCBHistory hist) {
		this.history = hist;
	}

}
