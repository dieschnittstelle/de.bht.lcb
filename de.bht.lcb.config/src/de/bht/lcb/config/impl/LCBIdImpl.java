package de.bht.lcb.config.impl;

import de.bht.lcb.config.LCBId;

/**
 * @author joern.kreutel
 */
public class LCBIdImpl implements LCBId {
	
	private String id;
	
	private String semuri;

	public String getId() {
		return id;
	}

	public LCBIdImpl(String id, String semuri) {
		super();
		this.id = id;
		this.semuri = semuri;
	}

	public void setSemuri(String semuri) {
		this.semuri = semuri;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSemuri() {
		return semuri;
	}
	
	public String toString() {
		return this.id + (this.semuri != null ? "#" + this.semuri : "");
	}

	@Override
	public String stringify() {
		return this.id;
	}
	
}
