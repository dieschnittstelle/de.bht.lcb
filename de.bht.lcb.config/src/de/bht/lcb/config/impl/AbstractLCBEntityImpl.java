package de.bht.lcb.config.impl;

import de.bht.lcb.config.LCBEntity;
import de.bht.lcb.config.LCBId;

/**
 * @author joern.kreutel
 */
public class AbstractLCBEntityImpl implements LCBEntity {

	private LCBId id;
	
	@Override
	public LCBId getId() {
		return this.id;
	}

	public void setId(LCBId id) {
		this.id = id;
	}

}
