package de.bht.lcb.config.test;

import org.apache.log4j.Logger;
import de.bht.lcb.config.LCBNodeConfig;
import de.bht.lcb.config.impl.io.DOMLCBNodeConfigLoader;
import de.bht.lcb.config.impl.io.LCBNodeConfigLoader;

/**
 * @author joern.kreutel
 */
public class TestLCBNodeConfigLoader {

	protected static Logger logger = Logger.getLogger(TestLCBNodeConfigLoader.class);
	
	private LCBNodeConfigLoader configloader = new DOMLCBNodeConfigLoader();
	
	public TestLCBNodeConfigLoader() {
		
	}
	
	public void initialise() {
		LCBNodeConfig config = configloader.load();
		
		logger.info("initialised config: " + config);
		
		// once the config is loaded, we need to initialise the devices and let them connect to the gateway
	}
	
	public static void main(String[] args) {
		TestLCBNodeConfigLoader node = new TestLCBNodeConfigLoader();
		node.initialise();
		
	}
	
	
	
}
