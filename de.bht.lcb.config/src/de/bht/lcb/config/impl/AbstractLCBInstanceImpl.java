package de.bht.lcb.config.impl;

import de.bht.lcb.config.LCBId;
import de.bht.lcb.config.LCBInstance;

/**
 * @author joern.kreutel
 */
public class AbstractLCBInstanceImpl extends AbstractLCBEntityImpl implements LCBInstance {

	private LCBId type;
	
	public LCBId getType() {
		return this.type;
	}
	
	public void setType(LCBId type) {
		this.type = type;
	}
}
