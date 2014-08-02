package de.bht.lcb.config.impl.io;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.bht.lcb.config.DeviceConfig;
import de.bht.lcb.config.DeviceType;
import de.bht.lcb.config.FOIConfig;
import de.bht.lcb.config.FOIType;
import de.bht.lcb.config.IOGatewayConfig;
import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.config.IOVarDescriptionCollection;
import de.bht.lcb.config.LCBEntity;
import de.bht.lcb.config.LCBHistory;
import de.bht.lcb.config.LCBId;
import de.bht.lcb.config.LCBNodeConfig;
import de.bht.lcb.config.LCBPropertiesCollection;
import de.bht.lcb.config.LCBProperty;
import de.bht.lcb.config.UIGatewayConfig;
import de.bht.lcb.config.impl.AbstractLCBEntityImpl;
import de.bht.lcb.config.impl.DeviceConfigImpl;
import de.bht.lcb.config.impl.DeviceTypeImpl;
import de.bht.lcb.config.impl.FOIConfigImpl;
import de.bht.lcb.config.impl.FOIDeviceRefImpl;
import de.bht.lcb.config.impl.FOITypeImpl;
import de.bht.lcb.config.impl.IOGatewayConfigImpl;
import de.bht.lcb.config.impl.IOVarDescriptionCollectionImpl;
import de.bht.lcb.config.impl.IOVarDescriptionImpl;
import de.bht.lcb.config.impl.LCBException;
import de.bht.lcb.config.impl.LCBHistoryImpl;
import de.bht.lcb.config.impl.LCBIdImpl;
import de.bht.lcb.config.impl.LCBNodeConfigImpl;
import de.bht.lcb.config.impl.LCBPropertiesCollectionImpl;
import de.bht.lcb.config.impl.LCBPropertyImpl;
import de.bht.lcb.config.impl.UIGatewayConfigImpl;
import de.bht.lcb.config.impl.util.LCBUtils;
import de.bht.lcb.config.impl.util.ReflectionUtils;

/**
 * @author joern.kreutel
 */
public class DOMLCBNodeConfigLoader implements LCBNodeConfigLoader {

	protected static Logger logger = Logger
			.getLogger(DOMLCBNodeConfigLoader.class);

	private static String configfile = "samplelcbconfig.xml";

	private static final String TAG_DEVICETYPE = "devicetype";
	private static final String TAG_DEVICE = "device";
	private static final String TAG_FOITYPE = "foitype";
	private static final String TAG_FOI = "foi";
	private static final String TAG_IOGATEWAY = "iogateway";
	private static final String TAG_UI = "uigateway";
	private static final String TAG_PROPERTY = "property";
	private static final String TAG_IOVAR = "iovar";
	private static final String TAG_REQUIRES = "requires";
	private static final String TAG_HASOPTION = "hasoption";
	private static final String TAG_HISTORY = "history";
	private static final String TAG_DEVICEREF = "device";
	private static final String TAG_EXCLUDE = "exclude";

	private static final String ATTR_REF = "ref";
	private static final String ATTR_ID = "id";
	private static final String ATTR_JCLASS = "jclass";
	private static final String ATTR_TYPE_ID = "typeid";
	private static final String ATTR_SEMURI = "semuri";

	public DOMLCBNodeConfigLoader() {

	}

	/**
	 * ATTENTION: the configfile will currently be read from the classpath.
	 * There might be more than one occurence of a file on the classpath
	 * depending on the project imports. To avoid confusion, each particular lcb
	 * project should better use a unique file for the config and initialise the
	 * config passing the name of that file.
	 */
	@Override
	public LCBNodeConfig load() {
		return load(configfile);
	}

	public LCBNodeConfig load(String configres) {
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream(
					configres);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);

			logger.info("parsed document from configfile: " + doc);

			LCBNodeConfigImpl config = new LCBNodeConfigImpl();

			System.out
					.println("\n****************** READ ******************\n");

			// we iterate over the elements and try to automatically invoke
			// instantiate the beans representing the config
			NodeList children = doc.getDocumentElement().getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
					config.addEntity(createEntity((Element) children.item(i)));
				}
			}

			System.out
					.println("\n****************** RESOLVE ******************\n");

			// we need to resolve the types on the device descriptions and fois
			for (LCBId devid : config.getDeviceConfigIds()) {
				DeviceConfig device = config.getDeviceConfig(devid);
				DeviceType devtype = config.getDeviceType(device.getType());
				logger.info("resolved type of device " + devid + ": " + devtype);
				if (devtype == null) {
					throw new LCBException("could not resolve device type "
							+ device.getType() + " on device config " + device
							+ "!");
				}
				device.setDeviceType(devtype);
			}

			for (LCBId foiid : config.getFOIConfigIds()) {
				FOIConfig foi = config.getFOIConfig(foiid);
				FOIType foitype = config.getFOIType(foi.getType());
				logger.info("resolved type of foi " + foiid + ": " + foitype);
				if (foitype == null) {
					throw new LCBException("could not resolve foi type "
							+ foi.getType() + " on foi config " + foi + "!");
				}
				foi.setFoiType(foitype);
			}

			return config;
		} catch (LCBException e) {
			throw e;
		} catch (Exception e) {
			throw new LCBException(e);
		}
	}

	private LCBEntity createEntity(Element el) {
		logger.info("processElement: " + el.getTagName());
		if (el.getTagName() == TAG_DEVICE) {
			return createDeviceConfig(el);
		} else if (el.getTagName() == TAG_DEVICETYPE) {
			return createDevicetype(el);
		} else if (el.getTagName() == TAG_FOITYPE) {
			return createFoitype(el);
		} else if (el.getTagName() == TAG_FOI) {
			return createFoiConfig(el);
		} else if (el.getTagName() == TAG_IOGATEWAY) {
			return createIOGateway(el);
		} else if (el.getTagName() == TAG_UI) {
			return createUI(el);
		}

		throw new LCBException("unknown entity type: " + el.getTagName());
	}

	private FOIType createFoitype(Element el) {
		FOITypeImpl foit = new FOITypeImpl();
		addId(el, foit);

		// read the requirements and options
		// we need to iterate over each device element that describes a device
		// used by this foi
		NodeList requiresEls = el.getElementsByTagName(TAG_REQUIRES);
		if (requiresEls.getLength() > 0) {
			foit.setIOVarRequirements(readIOVarDescriptions((Element) requiresEls
					.item(0)));
		}
		NodeList hasoptionEls = el.getElementsByTagName(TAG_HASOPTION);
		if (hasoptionEls.getLength() > 0) {
			foit.setIOVarOptions(readIOVarDescriptions((Element) hasoptionEls
					.item(0)));
		}

		// we also set the local vars read from the element itself
		foit.setLocalIOVars(readIOVarDescriptions(el));

		// read the properties
		foit.setProperties(readProperties(el));

		foit.setJclass(el.getAttribute(ATTR_JCLASS));

		return foit;
	}

	private UIGatewayConfig createUI(Element el) {
		UIGatewayConfigImpl ui = new UIGatewayConfigImpl();
		ui.setId(createId(el));

		ui.setProperties(readProperties(el));

		ui.setJclass(el.getAttribute(ATTR_JCLASS));

		return ui;
	}

	private IOGatewayConfig createIOGateway(Element el) {
		IOGatewayConfigImpl gw = new IOGatewayConfigImpl();
		gw.setId(createId(el));

		gw.setProperties(readProperties(el));

		gw.setJclass(el.getAttribute(ATTR_JCLASS));

		return gw;
	}

	private FOIConfig createFoiConfig(Element el) {
		FOIConfigImpl foi = new FOIConfigImpl();
		addId(el, foi);
		foi.setType(createId(el, ATTR_TYPE_ID));

		// we now lookup the devicerefs and create a deviceref for each element
		NodeList devicerefs = el.getElementsByTagName(TAG_DEVICEREF);
		for (int i = 0; i < devicerefs.getLength(); i++) {
			Element currentEl = (Element) devicerefs.item(i);
			// create a new deviceref
			FOIDeviceRefImpl devref = new FOIDeviceRefImpl();
			devref.setDeviceId(createId(currentEl, ATTR_REF));

			// check whether we have an exclude element
			NodeList excludes = currentEl.getElementsByTagName(TAG_EXCLUDE);
			if (excludes.getLength() > 0) {
				devref.setExcludedIOVars(readIOVarDescriptions((Element) excludes
						.item(0)));
			}
			foi.addDeviceRef(devref);
		}

		// read the properties
		foi.setProperties(readProperties(el));

		return foi;
	}

	private DeviceType createDevicetype(Element el) {
		DeviceTypeImpl devtype = new DeviceTypeImpl();
		addId(el, devtype);
		ReflectionUtils.readInstanceAttributesFromElement(DeviceType.class, el,
				devtype);
		devtype.setIOVars(readIOVarDescriptions(el));
		devtype.setProperties(readProperties(el));
		NodeList hists = el.getElementsByTagName(TAG_HISTORY);
		if (hists.getLength() > 0) {
			LCBHistoryImpl hist = new LCBHistoryImpl();
			ReflectionUtils.readInstanceAttributesFromElement(LCBHistory.class,
					(Element) hists.item(0), hist);
			hist.setIOVars(readIOVarDescriptions((Element) hists.item(0)));
			devtype.setHistory(hist);
		}

		devtype.setJclass(el.getAttribute(ATTR_JCLASS));

		return devtype;
	}

	private DeviceConfig createDeviceConfig(Element el) {
		DeviceConfigImpl dev = new DeviceConfigImpl();
		addId(el, dev);
		dev.setType(createId(el, ATTR_TYPE_ID));		
		dev.setProperties(readProperties(el));		
		ReflectionUtils.readInstanceAttributesFromElement(DeviceConfig.class,
				el, dev);
		// dev.set
		return dev;
	}

	private void addId(Element el, AbstractLCBEntityImpl entity) {
		entity.setId(createId(el));
	}

	private LCBId createId(Element el) {
		return createId(el, ATTR_ID, ATTR_SEMURI);
	}

	private LCBId createId(Element el, String idattr) {
		return createId(el, idattr, null);
	}

	private LCBId createId(Element el, String idattr, String uriattr) {
		LCBIdImpl id = LCBUtils.id(el.getAttribute(idattr));
		if (uriattr != null) {
			String uri = el.getAttribute(uriattr);
			if (uri != null && uri.length() > 0) {
				id.setSemuri(uri);
			}
		}

		return id;
	}

	private IOVarDescriptionCollection readIOVarDescriptions(Element mother) {
		IOVarDescriptionCollectionImpl iovars = new IOVarDescriptionCollectionImpl();

		NodeList children = mother.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node currentChild = children.item(i);
			if (currentChild.getNodeType() == Node.ELEMENT_NODE
					&& TAG_IOVAR.equals(((Element) currentChild).getTagName())) {
				IOVarDescriptionImpl desc = new IOVarDescriptionImpl();
				ReflectionUtils.readInstanceAttributesFromElement(
						IOVarDescription.class, (Element) currentChild, desc);
				iovars.addIOVarDescription(desc);
				logger.info("created iovar: " + desc.getName() + "=" + desc);
				desc.setProperties(readProperties((Element) currentChild));
			}
		}

		return iovars;
	}

	private LCBPropertiesCollection readProperties(Element mother) {
		LCBPropertiesCollectionImpl props = new LCBPropertiesCollectionImpl();

		NodeList children = mother.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node currentChild = children.item(i);
			if (currentChild.getNodeType() == Node.ELEMENT_NODE
					&& TAG_PROPERTY.equals(((Element) currentChild)
							.getTagName())) {
				LCBPropertyImpl prop = new LCBPropertyImpl();
				ReflectionUtils.readInstanceAttributesFromElement(
						LCBProperty.class, (Element) currentChild, prop);
				logger.info("created property: " + prop.getName() + "=" + prop);
				props.addProperty(prop);
			}
		}

		return props;
	}

}
