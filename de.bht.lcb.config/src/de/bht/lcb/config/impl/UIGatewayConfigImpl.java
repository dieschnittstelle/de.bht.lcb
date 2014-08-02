package de.bht.lcb.config.impl;

import java.util.Collection;

import de.bht.lcb.config.LCBPropertiesCollection;
import de.bht.lcb.config.LCBProperty;
import de.bht.lcb.config.UIGatewayConfig;

/**
 * it is debatable whether uigateway and iogateway should be dealt with as
 * entities
 * 
 * @author joern.kreutel
 */
public class UIGatewayConfigImpl extends AbstractLCBEntityImpl implements
		UIGatewayConfig {

	public void setJclass(String jclass) {
		this.jclass = jclass;
	}

	private LCBPropertiesCollection props;
	
	private String jclass;

	@Override
	public Collection<String> getPropertyNames() {
		return props.getPropertyNames();
	}

	@Override
	public String getPropertyValue(String prop) {
		return props.getPropertyValue(prop);
	}

	@Override
	public LCBProperty getProperty(String prop) {
		return props.getProperty(prop);
	}

	public void setProperties(LCBPropertiesCollection props) {
		this.props = props;
	}

	@Override
	public String getJclass() {
		return jclass;
	}

}
