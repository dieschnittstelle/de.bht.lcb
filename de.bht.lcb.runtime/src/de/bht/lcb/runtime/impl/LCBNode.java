package de.bht.lcb.runtime.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.bht.lcb.config.DeviceConfig;
import de.bht.lcb.config.FOIConfig;
import de.bht.lcb.config.IOGatewayConfig;
import de.bht.lcb.config.JClassReference;
import de.bht.lcb.config.LCBId;
import de.bht.lcb.config.LCBNodeConfig;
import de.bht.lcb.config.UIGatewayConfig;
import de.bht.lcb.config.impl.LCBException;
import de.bht.lcb.config.impl.io.DOMLCBNodeConfigLoader;
import de.bht.lcb.config.impl.io.LCBNodeConfigLoader;
import de.bht.lcb.runtime.Device;
import de.bht.lcb.runtime.FOI;
import de.bht.lcb.runtime.IOGateway;
import de.bht.lcb.runtime.UIGateway;

/**
 * this is the main runtime class that reads in the node config and initialised
 * all components to be managed by this node
 * 
 * if we are running in standalone mode, this is the application class in case
 * we are running inside of a ServletContainer this class will be initialised by
 * the ServletContextListener
 * 
 * we assume that we have a single iogateway and uigateway (however, these
 * interfaces may be implemented by wrappers over multiple remote gateways)
 * 
 * @author joern.kreutel
 */
public class LCBNode {

	protected static Logger logger = Logger.getLogger(LCBNode.class);

	/**
	 * the config
	 */
	private LCBNodeConfig config;

	private Map<String, Device> devices = new HashMap<String, Device>();

	private Map<String, FOI> fois = new HashMap<String, FOI>();

	private IOGateway iogateway;

	private UIGateway uigateway;

	/**
	 * initialise the node reading in the config
	 * 
	 * @param configresource
	 */
	public LCBNode(String configresource) {
		// read in the config
		LCBNodeConfigLoader configloader = new DOMLCBNodeConfigLoader();
		this.config = configloader.load(configresource);
	}

	/**
	 * run the node
	 */
	public void startup() {

		System.out.println("****************** STARTUP ******************");

		try {

			// initialise the runtime components based on the config
			for (LCBId devid : this.config.getDeviceConfigIds()) {
				DeviceConfig devconf = this.config.getDeviceConfig(devid);
				Device dev = (Device) createRuntimeComponent(devconf
						.getDeviceType());
				dev.setConfig(devconf);
				this.devices.put(devid.stringify(), dev);
			}

			for (LCBId foiid : this.config.getFOIConfigIds()) {
				FOIConfig foiconf = this.config.getFOIConfig(foiid);
				FOI foi = (FOI) createRuntimeComponent(foiconf.getFoiType());
				foi.setConfig(foiconf);
				this.fois.put(foiid.stringify(), foi);
			}

			/*
			 * an lcb node needs an io gateway!!!
			 */
			Collection<LCBId> iogwids = this.config.getIOGatewaysIds();
			logger.info("iogatewayids are: " + iogwids);
			IOGatewayConfig gwconf = this.config.getIOGateway(iogwids
					.iterator().next());
			this.iogateway = (IOGateway) createRuntimeComponent(gwconf);
			this.iogateway.setConfig(gwconf);

			Collection<LCBId> uigwids = this.config.getUIGatewayIds();
			if (!uigwids.isEmpty()) {
				UIGatewayConfig uigwconf = this.config.getUIGateway(uigwids
						.iterator().next());
				this.uigateway = (UIGateway) createRuntimeComponent(uigwconf);
				this.uigateway.setConfig(uigwconf);
				this.uigateway.initialise();
			} else {
				logger.info("LCB node does not use an ui gateway.");
			}

			// now we have initialised the components we can register the
			// devices and bind the fois
			logger.info("registering devices: " + this.devices.keySet());
			for (String devid : this.devices.keySet()) {
				Device currentDevice = this.devices.get(devid);
				// we explicitly pass the vice-versa-references here - could
				// also be done inside of setGateway or register
				currentDevice.setGateway(this.iogateway);
				this.iogateway.register(currentDevice);
				/*
				 * we initialise the device here -- NOTE THAT SYNCHRONISATION IS
				 * AN ISSUE AS DEVICES MAY TRY TO PUBLISH EVENTS WITHOUT THE
				 * REST OF THE SYSTEM BEING SETUP. CURRENTLY THIS IS SIMPLY
				 * DEALT WITH BY CATCHING EXCEPTIOONS DURING EVENT PUBLISHING.
				 */
				currentDevice.initialise();
			}
			logger.info("binding fois: " + this.fois.keySet());
			for (String foiid : this.fois.keySet()) {
				this.fois.get(foiid).bindIO(this.iogateway);
			}

			// we then register the fois with the uigateway (keep this here)
			logger.info("publishing fois: " + this.fois.keySet());
			for (String foiid : this.fois.keySet()) {
				// we explicitly pass the vice-versa-references here - could
				// also be done inside of publish or bindUI
				this.fois.get(foiid).bindUI(this.uigateway);
				this.uigateway.publish(this.fois.get(foiid));
			}

			// we finally initialise the fois, which might start their
			// respective control cycle
			logger.info("initialising fois: " + this.fois.keySet());
			for (String foiid : this.fois.keySet()) {
				this.fois.get(foiid).initialise();
			}

			System.out.println("****************** STARTUP DONE *************");

		} catch (LCBException e) {
			throw e;
		} catch (Exception e) {
			String err = "got exception trying to initialise lcb node: " + e;
			logger.error(err, e);
			throw new LCBException(err, e);
		}

	}

	public Object createRuntimeComponent(JClassReference config) {
		try {
			Class<?> klass = Class.forName(config.getJclass());
			return klass.newInstance();
		} catch (Exception e) {
			String err = "got exception trying to create runtime component for jclass "
					+ config.getJclass() + ": " + e;
			logger.error(err, e);

			throw new LCBException(err, e);
		}

	}

}
