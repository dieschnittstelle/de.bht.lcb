package de.bht.lcb.simpleimpl.devices;

import org.apache.log4j.Logger;

import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.runtime.impl.AbstractDeviceImpl;

/**
 * @author joern.kreutel
 */
public class GenericControllerConnector extends AbstractDeviceImpl {

	protected static Logger logger = Logger
			.getLogger(GenericControllerConnector.class);

	// we declare a local variable that will be instantiated with values from
	// the config
	private String ctrlAddress;

	@Override
	public void initialise() {
		logger.info("initialise");
		ctrlAddress = (String) getConfigProperty("controller_ipaddress");

		// this is for showing how devices can implement their own control
		// cycles and feed back data to the fois via the event publishing
		// mechanism supported by the gateway

		// as this class implements a generic device, we may check as what kind
		// of specific device the given instance is running
		if ("devtype_outdoorTemperature".equals(this.getConfig()
				.getDeviceType().getId().getId())) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						// in the specified interval, we start reading the
						// outdoor
						// temperature
						try {
							Thread.sleep(5000);
							// note that this is only used for testing whether
							// the
							// event publishing mechanism is working
							publishEvent("outdoorTemperatureRead");
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}

	}

	/*
	 * these are examples that show how the IOVarDescription can be used to
	 * "translate" a read/write request to a call to the underlying hardware
	 * layer
	 */

	@Override
	protected Object readIOVar(IOVarDescription desc) {
		String value = "42";
		logger.info("readIOVar from  " + ctrlAddress + ": "
				+ desc.getPropertyValue("internalname") + "=" + value);
		return value;
	}

	@Override
	protected void writeIOVar(IOVarDescription desc, Object varValue) {
		logger.info("writeIOVar to  " + ctrlAddress + ": "
				+ desc.getPropertyValue("internalname") + "=" + varValue);
	}

}
