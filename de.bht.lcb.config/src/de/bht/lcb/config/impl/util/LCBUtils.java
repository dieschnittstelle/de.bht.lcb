package de.bht.lcb.config.impl.util;

import java.util.HashMap;
import java.util.Map;

import de.bht.lcb.config.impl.LCBIdImpl;

/**
 * @author joern.kreutel
 */
public class LCBUtils {

	private static Map<String, LCBIdImpl> ids = new HashMap<String, LCBIdImpl>();

	public static LCBIdImpl id(String id) {
		if (ids.containsKey(id)) {
			return ids.get(id);
		}

		LCBIdImpl newid = new LCBIdImpl(id, null);
		synchronized (ids) {
			ids.put(id, newid);
		}

		return newid;
	}

}
