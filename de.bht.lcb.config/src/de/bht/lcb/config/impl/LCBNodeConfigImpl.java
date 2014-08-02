package de.bht.lcb.config.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.bht.lcb.config.DeviceConfig;
import de.bht.lcb.config.DeviceType;
import de.bht.lcb.config.FOIConfig;
import de.bht.lcb.config.FOIType;
import de.bht.lcb.config.IOGatewayConfig;
import de.bht.lcb.config.LCBEntity;
import de.bht.lcb.config.LCBId;
import de.bht.lcb.config.LCBNodeConfig;
import de.bht.lcb.config.UIGatewayConfig;

/**
 * @author joern.kreutel
 */
public class LCBNodeConfigImpl implements LCBNodeConfig {

	private Map<LCBId, DeviceType> devicetypes = new HashMap<LCBId, DeviceType>();
	private Map<LCBId, DeviceConfig> devices = new HashMap<LCBId, DeviceConfig>();
	private Map<LCBId, FOIType> foitypes = new HashMap<LCBId, FOIType>();
	private Map<LCBId, FOIConfig> fois = new HashMap<LCBId, FOIConfig>();
	private Map<LCBId, IOGatewayConfig> gateways = new HashMap<LCBId, IOGatewayConfig>();
	private Map<LCBId, UIGatewayConfig> uis = new HashMap<LCBId, UIGatewayConfig>();

	@Override
	public Collection<LCBId> getDeviceTypeIds() {
		return this.devicetypes.keySet();
	}

	@Override
	public DeviceType getDeviceType(LCBId id) {
		return this.devicetypes.get(id);
	}

	@Override
	public Collection<LCBId> getFOITypeIds() {
		return this.foitypes.keySet();
	}

	@Override
	public FOIType getFOIType(LCBId id) {
		return this.foitypes.get(id);
	}

	@Override
	public Collection<LCBId> getDeviceConfigIds() {
		return this.devices.keySet();
	}

	@Override
	public DeviceConfig getDeviceConfig(LCBId id) {
		return this.devices.get(id);
	}

	@Override
	public Collection<LCBId> getIOGatewaysIds() {
		return this.gateways.keySet();
	}

	@Override
	public IOGatewayConfig getIOGateway(LCBId id) {
		return this.gateways.get(id);
	}
	
	public void addEntity(LCBEntity entity) {
		if (entity instanceof DeviceType) {
			this.devicetypes.put(entity.getId(), (DeviceType)entity);
		}
		else if (entity instanceof DeviceConfig) {
			this.devices.put(entity.getId(), (DeviceConfig)entity);
		}
		else if (entity instanceof FOIType) {
			this.foitypes.put(entity.getId(), (FOIType)entity);
		}
		else if (entity instanceof FOIConfig) {
			this.fois.put(entity.getId(), (FOIConfig)entity);
		}
		else if (entity instanceof IOGatewayConfig) {
			this.gateways.put(entity.getId(), (IOGatewayConfig)entity);
		}
		else if (entity instanceof UIGatewayConfig) {
			this.uis.put(entity.getId(), (UIGatewayConfig)entity);
		}
	}

	@Override
	public Collection<LCBId> getUIGatewayIds() {
		return this.uis.keySet();
	}

	@Override
	public UIGatewayConfig getUIGateway(LCBId id) {
		return this.uis.get(id);
	}

	@Override
	public Collection<LCBId> getFOIConfigIds() {
		return this.fois.keySet();
	}

	@Override
	public FOIConfig getFOIConfig(LCBId id) {
		return this.fois.get(id);
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("{LCBConfig\n\t devicetypes:");
		buf.append(String.valueOf(this.devicetypes));
		buf.append("\n\t devices:");
		buf.append(String.valueOf(this.devices));
		buf.append("\n\t foistype:");
		buf.append(String.valueOf(this.foitypes));
		buf.append("\n\t fois:");
		buf.append(String.valueOf(this.fois));
		buf.append("\n\t gateways:");
		buf.append(String.valueOf(this.gateways));
		buf.append("\n\t uis:");
		buf.append(String.valueOf(this.uis));
		buf.append("\n}");
		
		return buf.toString();
	}

}
