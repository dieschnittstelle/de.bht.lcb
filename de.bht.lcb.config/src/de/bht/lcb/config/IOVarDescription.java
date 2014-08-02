package de.bht.lcb.config;


/**
 * @author joern.kreutel
 */
public interface IOVarDescription extends LCBPropertiesCollection {
	
	public static final String ACCESSTYPE_READ = "read";
	public static final String ACCESSTYPE_WRITE = "write";
	public static final String ACCESSTYPE_READWRITE = "readwrite";
		
	public String getAccesstype();
	
	public String getName();
	
	public String getSemuri();
	
	public String getInterval();
	
	public String getLifetime();
	
	public String getValue();

	public String getType();

}
