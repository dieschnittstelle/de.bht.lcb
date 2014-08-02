package de.bht.lcb.simpleimpl.devices;

import org.apache.log4j.Logger;

import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.runtime.impl.AbstractDeviceImpl;

/**
 * @author joern.kreutel
 */
public class IndoorHeatingControllerConnector extends AbstractDeviceImpl {

	protected static Logger logger = Logger
			.getLogger(IndoorHeatingControllerConnector.class);

	// we declare a local variable that will be instantiated with values from
	// the config
	private String ctrlAddress;
	private String ctrlPort;

	@Override
	public void initialise() {
		logger.info("initialise");
		ctrlAddress = (String) getConfigProperty("controller_ipaddress");
		ctrlPort = (String) getConfigProperty("controller_port");
	}

	@Override
	protected Object readIOVar(IOVarDescription desc) {
		String value = "21";
		logger.info("readIOVar from  " + ctrlAddress + ":" + ctrlPort + ": " + desc.getPropertyValue("internalname") + "=" + value);
		return value; 
	}

	@Override
	protected void writeIOVar(IOVarDescription desc, Object varValue) {
		logger.info("writeIOVar to  " + ctrlAddress + ":" + ctrlPort + ": "  + desc.getPropertyValue("internalname") + "=" + varValue);
	}
	
}
