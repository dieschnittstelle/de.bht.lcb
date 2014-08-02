package de.bht.lcb.config.impl;

import java.io.Serializable;

/**
 * @author joern.kreutel
 */
public class LCBException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 7622498894727506534L;

	public LCBException(Exception e) {
		super(e);
	}
	
	public LCBException(String msg) {
		super(msg);
	}
	
	public LCBException(String msg,Exception e) {
		super(msg,e);
	}
	
}
