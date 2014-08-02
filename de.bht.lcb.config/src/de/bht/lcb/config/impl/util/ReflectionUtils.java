package de.bht.lcb.config.impl.util;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import de.bht.lcb.config.impl.LCBException;

/**
 * @author joern.kreutel
 */
public class ReflectionUtils {
	
	protected static Logger logger = Logger.getLogger(ReflectionUtils.class);

	public static boolean isGetter(Method meth) {
		return (meth.getName().startsWith("get"));
	}

	public static boolean isSetter(Method meth) {
		return (meth.getName().startsWith("set"));
	}

	public static Method getGetter(Class<?> klass, String attr) {
		try {
			return klass.getDeclaredMethod("get" + initialUc(attr),
					new Class<?>[] {});
		} catch (Exception e) {
			throw new LCBException(e);
		}
	}

	public static Method getSetter(Class<?> klass, String attr) {
		return getSetter(klass, attr, java.lang.String.class);
	}

	public static Method getSetter(Class<?> klass, String attr, Class<?> type) {
		try {
			return klass.getDeclaredMethod("set" + initialUc(attr),
					new Class<?>[] { type });
		} catch (Exception e) {
			throw new LCBException(e);
		}
	}

	public static String getAttrname(String gettersetter) {
		return initialLc(gettersetter.substring("set".length()));
	}

	public static String initialUc(String somestring) {
		return somestring.substring(0, 1).toUpperCase()
				+ somestring.substring(1);
	}

	public static String initialLc(String somestring) {
		return somestring.substring(0, 1).toLowerCase()
				+ somestring.substring(1);
	}
		
	public static void readInstanceAttributesFromElement(Class<?> type, Element el,
			Object obj) {
		readInstanceAttributesFromElement(type, el, obj, true);
	}

	public static void readInstanceAttributesFromElement(Class<?> type, Element el,
			Object obj, boolean emptystringasnull) {

		try {
			// we iterate over the getter methods
			for (Method meth : type.getDeclaredMethods()) {
				if (ReflectionUtils.isGetter(meth)) {
					String attrname = ReflectionUtils.getAttrname(meth
							.getName());
					String attrval = el.getAttribute(attrname);
					if (attrval != null
							&& (attrval.length() > 0 || !emptystringasnull)) {
						logger.debug("setting attribute: " + attrname + "=" + attrval);
						ReflectionUtils.getSetter(obj.getClass(), attrname).invoke(obj,
								attrval);
					}
				}
			}
		} catch (Exception e) {
			throw new LCBException(e);
		}

	}

}
