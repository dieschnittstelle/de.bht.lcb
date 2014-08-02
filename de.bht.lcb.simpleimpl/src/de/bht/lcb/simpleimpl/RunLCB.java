package de.bht.lcb.simpleimpl;

import de.bht.lcb.runtime.impl.LCBNode;

/**
 * @author joern.kreutel
 */
public class RunLCB {
	
	public static void main(String[] args) {
		
		LCBNode node = new LCBNode("simplelcbconfig.xml");
				
		node.startup();
		
	}

}
