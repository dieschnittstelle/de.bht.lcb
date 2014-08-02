package de.bht.lcb.runtime;

import de.bht.lcb.config.UIGatewayConfig;

/**
 * this is that part of the system dealt with by Tim Kreutzer's UI architecture.
 * Publishing a FOI will establish a Web Socket connection to the server that
 * provides the UI. Incoming requests will be handled via method invocations on
 * the foi
 * 
 * @author joern.kreutel
 */
public interface UIGateway {

	public void publish(FOI foi);

	public void unpublish(String foiId);

	public void setConfig(UIGatewayConfig uigwconf);

	public void initialise();

	/*
	 * we need to think about what kind of interface the uigateway is supposed
	 * to provide to outside - for getting started, we use incoming get/set and
	 * outgoing onIOVarChanged and onEvent
	 */
	public Object getIOVar(String foiId, String ioVarId);

	public void setIOVar(String foiId, String ioVarId, Object ioVarValue);

	public void onIOVarChanged(String foiId, String varId, Object varValue);

	public void onEvent(String foiId, String eventId,Object eventData);

}
