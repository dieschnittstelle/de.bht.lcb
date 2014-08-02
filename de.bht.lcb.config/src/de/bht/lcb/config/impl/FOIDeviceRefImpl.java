package de.bht.lcb.config.impl;

import de.bht.lcb.config.FOIDeviceRef;
import de.bht.lcb.config.IOVarDescriptionCollection;
import de.bht.lcb.config.LCBId;

/**
 * @author joern.kreutel
 */
public class FOIDeviceRefImpl implements FOIDeviceRef {

	private LCBId deviceId;
	
	private IOVarDescriptionCollection excludedIOVars;

	@Override
	public LCBId getDeviceId() {
		return this.deviceId;
	}

	@Override
	public IOVarDescriptionCollection getExcludedIOVars() {
		return this.excludedIOVars;
	}

	public void setDeviceId(LCBId deviceId) {
		this.deviceId = deviceId;
	}

	public void setExcludedIOVars(IOVarDescriptionCollection excludedIOVars) {
		this.excludedIOVars = excludedIOVars;
	}
		
}
