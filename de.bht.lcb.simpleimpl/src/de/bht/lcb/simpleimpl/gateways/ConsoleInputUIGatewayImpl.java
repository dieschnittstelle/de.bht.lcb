package de.bht.lcb.simpleimpl.gateways;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import de.bht.lcb.config.UIGatewayConfig;
import de.bht.lcb.runtime.FOI;
import de.bht.lcb.runtime.UIGateway;

/**
 * @author joern.kreutel
 */
public class ConsoleInputUIGatewayImpl implements UIGateway {

	protected static Logger logger = Logger
			.getLogger(ConsoleInputUIGatewayImpl.class);

	private Map<String, FOI> fois = new HashMap<String, FOI>();

	// inputing over the console while output is given by other threads is quite
	// problematic..., so we predefine a range of actions that will be
	// identified via an integer
	private String[] commands = new String[] {
			"set roomHeating_218 steuerungszyklus_intervall 7000",
			"set roomHeating_216 steuerungszyklus_intervall 3000",
			"set roomHeating_216 fenster_oeffnungsgrad 30",
			"get roomHeating_218 aussentemperatur",
			"get roomHeating_216 raumtemperatur_ist" };

	@Override
	public void publish(FOI foi) {
		logger.info("publish: " + foi);
		fois.put(foi.getId(), foi);
	}

	@Override
	public void unpublish(String foiId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConfig(UIGatewayConfig uigwconf) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialise() {
		// prompt for user input in a separate thread
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					UserAction action = null;
					try {
						System.out.println("/>");
						BufferedReader br = new BufferedReader(
								new InputStreamReader(System.in));
						String input = br.readLine();
						// check whether a full command has been input or
						// whether we only have a reference
						try {
							int cmdref = Integer.parseInt(input);
							action = new UserAction(commands[cmdref]);
						} catch (NumberFormatException e) {
							action = new UserAction(input);
						}
					} catch (Exception e) {
						System.out
								.println("got excaption trying to read input: "
										+ e);
						action = null;
					}
					if (action != null) {
						// we now run the user action...
						processUserAction(action);
					}
				}
			}

			private void processUserAction(UserAction action) {

				if (action.getType().equalsIgnoreCase("set")) {
					System.out.println("setting value " + action.getValue()
							+ " of variable " + action.getVariable()
							+ " on foi " + action.getFoiid());
					setIOVar(action.getFoiid(),action.getVariable(),
							action.getValue());
				} else if (action.getType().equalsIgnoreCase("get")) {
					System.out.println("getting value " + action.getValue()
							+ " of variable " + action.getVariable()
							+ " on foi " + action.getFoiid());
					Object value = getIOVar(action.getFoiid(),
							action.getVariable());
					System.out.println("got value: " + value);
				}

			}
		}).start();
	}

	public class UserAction {

		private String type;

		private String foiid;

		private String variable;

		private String value;

		public UserAction(String input) {
			String[] segments = input.split(" ");
			this.type = segments[0];
			this.foiid = segments[1];
			this.variable = segments[2];
			if (segments.length > 3) {
				this.value = segments[3];
			}
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getFoiid() {
			return foiid;
		}

		public void setFoiid(String foiid) {
			this.foiid = foiid;
		}

		public String getVariable() {
			return variable;
		}

		public void setVariable(String variable) {
			this.variable = variable;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

	@Override
	public Object getIOVar(String foiId, String ioVarId) {
		return this.fois.get(foiId).get(ioVarId);
	}

	@Override
	public void setIOVar(String foiId, String ioVarId, Object ioVarValue) {
		this.fois.get(foiId).set(ioVarId, ioVarValue);
	}

	@Override
	public void onIOVarChanged(String foiId, String varId, Object varValue) {
		System.out.println(foiId + ": onIOVarChanged: " + varId + "="
				+ varValue);
	}

	@Override
	public void onEvent(String foiId, String eventId, Object eventData) {
		System.err.println(foiId + ": onEvent:" + eventId
				+ (eventData != null ? ("=" + eventData) : "") + "\n");
	}

}
