package de.bht.lcb.config.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import de.bht.lcb.config.DeviceConfig;
import de.bht.lcb.config.DeviceType;
import de.bht.lcb.config.IOVarDescription;
import de.bht.lcb.config.LCBId;
import de.bht.lcb.config.LCBProperty;

/**
 * @author joern.kreutel
 */
public class DeviceConfigImpl extends
		AbstractLCBEntityImplWithIOVarsAndProperties implements DeviceConfig {

	protected static Logger logger = Logger.getLogger(DeviceConfigImpl.class);

	private LCBId type;

	private DeviceType deviceType;

	public LCBId getType() {
		return this.type;
	}

	public void setType(LCBId type) {
		this.type = type;
	}

	@Override
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public DeviceType getDeviceType() {
		return this.deviceType;
	}

	/**
	 * the iovarnames are the union of iovars from the deviceType and the local
	 * iovar declarations, which might override the latter.
	 * 
	 * TODO: For the time being, we do not merge declarations, but simple return
	 * the local one if we have one and otherwise the one from the type - note
	 * that without merging, any properties set on an iovar description at the
	 * level of the device type will be lost...
	 */

	@Override
	public Collection<String> getIOVarNames() {
		Set<String> allvars = new HashSet<String>();
		allvars.addAll(deviceType.getIOVarNames());
		allvars.addAll(super.getIOVarNames());

		return allvars;
	}

	@Override
	public List<IOVarDescription> getAllIOVarDescriptions() {
		// we use the iovar names and retrieve the descriptions either from the
		// device or from the device type
		List<IOVarDescription> descs = new ArrayList<IOVarDescription>();
		for (String iovar : getIOVarNames()) {
			// check whether we have the description locally
			IOVarDescription localDesc = this.getIOVarDescription(iovar);
			if (localDesc != null) {
				descs.add(localDesc);
			} else {
				descs.add(this.deviceType.getIOVarDescription(iovar));
			}
		}

		return descs;
	}

	// we make properties available both locally and from the device type, first
	// trying to find them locally

	@Override
	public IOVarDescription getIOVarDescription(String iovarid) {
		IOVarDescription desc = super.getIOVarDescription(iovarid);
		if (desc != null) {
			return desc;
		}
		return this.deviceType.getIOVarDescription(iovarid);
	}

	@Override
	public String getPropertyValue(String propname) {
		LCBProperty prop = super.getProperty(propname);
		if (prop != null) {
			return prop.getValue();
		}
		prop = this.deviceType.getProperty(propname);
		if (prop != null) {
			return prop.getValue();
		}
		logger.error(this.getId() + ": property " + propname
				+ " is neither defined locally, nor on the device type "
				+ getDeviceType().getId());
		return null;
	}

}
