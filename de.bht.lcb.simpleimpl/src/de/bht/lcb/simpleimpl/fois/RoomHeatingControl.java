package de.bht.lcb.simpleimpl.fois;

import org.apache.log4j.Logger;

import de.bht.lcb.runtime.impl.AbstractFOIImpl;

/**
 * this class is supposed to implement all controls over the room heating using
 * the iovars provided locally and by the devices
 * 
 * @author joern.kreutel
 */
public class RoomHeatingControl extends AbstractFOIImpl {

	protected static Logger logger = Logger.getLogger(RoomHeatingControl.class);

	@Override
	public void initialise() {
		// this shows how to start a control process indedendently of any user
		// interaction
		new Thread(new Runnable() {
			@Override
			public void run() {
				controlCycle();
			}
		}).start();
	}

	public void controlCycle() {

		while (true) {
			// we use the value of the steuerungszyklus_intervall iovar - which
			// might also be set from outside
			String interval = (String) super.get("steuerungszyklus_intervall");
			System.err
					.println(getConfig().getId()
							+ ": running controlCycle using steuerungszyklus_intervall: "
							+ interval + "\n");

			try {
				Thread.sleep(Integer.parseInt(interval));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@Override
	public void onDeviceEvent(String deviceid, String deviceevent) {
		System.out.println(getId() + ": onDeviceEvent: " + deviceid + ": "
				+ deviceevent + "\n");
		publishEvent(deviceevent, null);
	}

}
