package com.sunkaisens.omc.util;
/**
 * 
 * 
 * 
 * 
 * 各种校验规则的工具类
 *
 */
public abstract class RegExpUtil {
	
	public static final char IGONECASE = 'i';
	
	public static final char GLOBAL = 'g';
	
	/** key-value properties splitter */
	public static final String PROPERTY_SPLITTER_REGEX = "(\\s*(:|=)\\s*)";
	
	public static final String COMPLEX_NAME_SPLITTER_REGEX = "\\.|-";
	
	public static final String COMPLEX_VAL_SPLITTER_REGEX = "(\\s+)?(\\||/|:|\\s+)(\\s+)?";
	
	public static final String COMPLEX_MUTIPLE_REGEX = "(\\d{1,2}[\\|:])+[a-fA-F0-9(0x|0X)\\.]+";
	
	public static final String ARRAYS_SPLITTER_REGEX = ",";
	
	//-- 值类型正则 --//
	public static final String DECIMAL = "\\d+\\.\\d{1,2}";
	
	public static final String INTEGER = "\\d+";
	
	public static final String HEX = "(?:0x|0X)?(?:\\p{XDigit})+";
	
	public static final String IP = "(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|[1-9])\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)";

	public static final String IP_3LINE = "(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|[1-9])-(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)-(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)";
	
	public static final String IP_3POINT = "(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|[1-9])\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)\\.(?:25[0-5]|2[0-4]\\d|1\\d\\d|\\d\\d|\\d)";
	
	public static final String IP_3HEX = "(?:0x|0X)?[0-9,a-f,A-F]{3, 6}";
	
	public static final String TS_MAP = "(?:0x|0X)[0-9,a-f,A-F]{8}";
	
	public static final String UNICODE = "(?:\\\\u(?:\\p{XDigit}{4}))";

	public static final String CN_CHARS = "[\\u4e00-\\u9fa5_\\-\\.a-zA-Z0-9]+";
	
	public static final String NUM_TRANS = "(?:[#*?+\\dX]+)";

}
