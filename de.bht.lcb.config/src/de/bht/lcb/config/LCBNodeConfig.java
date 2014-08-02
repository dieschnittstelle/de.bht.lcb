package de.bht.lcb.config;

import java.util.Collection;

/**
 * @author joern.kreutel
 */
public interface LCBNodeConfig {
	
	public Collection<LCBId> getDeviceTypeIds();
	
	public DeviceType getDeviceType(LCBId id);
	
	public Collection<LCBId> getFOITypeIds();
	
	public FOIType getFOIType(LCBId id);
	
	public Collection<LCBId> getFOIConfigIds();
	
	public FOIConfig getFOIConfig(LCBId id);

	public Collection<LCBId> getDeviceConfigIds();
	
	public DeviceConfig getDeviceConfig(LCBId id);
	
	public Collection<LCBId> getIOGatewaysIds();
	
	public IOGatewayConfig getIOGateway(LCBId id);
	
	public Collection<LCBId> getUIGatewayIds();
	
	public UIGatewayConfig getUIGateway(LCBId id);


}
