package de.bht.lcb.config.impl.io;

import de.bht.lcb.config.LCBNodeConfig;

/**
 * @author joern.kreutel
 */
public interface LCBNodeConfigLoader {
	
	public LCBNodeConfig load();
	
	public LCBNodeConfig load(String configname);

}
