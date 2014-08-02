package de.bht.lcb.config.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import de.bht.lcb.config.FOIConfig;
import de.bht.lcb.config.FOIDeviceRef;
import de.bht.lcb.config.FOIType;
import de.bht.lcb.config.LCBPropertiesCollection;
import de.bht.lcb.config.LCBProperty;

/**
 * @author joern.kreutel
 */
public class FOIConfigImpl extends AbstractLCBInstanceImpl implements FOIConfig {

	protected static Logger logger = Logger.getLogger(FOIConfigImpl.class);
	
	private List<FOIDeviceRef> devicerefs = new ArrayList<FOIDeviceRef>();

	private FOIType foiType;
	
	private LCBPropertiesCollection props;

	public void addDeviceRef(FOIDeviceRef ref) {
		this.devicerefs.add(ref);
	}

	public List<FOIDeviceRef> getDevicerefs() {
		return devicerefs;
	}

	public FOIType getFoiType() {
		return foiType;
	}

	public void setFoiType(FOIType foiType) {
		this.foiType = foiType;
	}

	@Override
	public Collection<String> getPropertyNames() {
		return this.props.getPropertyNames();
	}


	@Override
	public LCBProperty getProperty(String prop) {
		return this.props.getProperty(prop);
	}
	
	public void setProperties(LCBPropertiesCollection props) {
		this.props = props;
	}

	@Override
	public String getPropertyValue(String propname) {
		LCBProperty prop = this.props.getProperty(propname);
		if (prop != null) {
			return prop.getValue();
		}
		prop = this.foiType.getProperty(propname);
		if (prop != null) {
			return prop.getValue();
		}
		logger.error(this.getId() + ": property " + propname
				+ " is neither defined locally, nor on the foi type "
				+ getFoiType().getId());
		return null;
	}

}
