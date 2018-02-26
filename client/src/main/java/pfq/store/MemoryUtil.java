package pfq.store;

import java.util.HashMap;

public class MemoryUtil {
	HashMap<String,String> variables = null;
	private static MemoryUtil SINGLETON;
	private MemoryUtil() {
		this.variables = new HashMap<>();
	}
	
	static {
		SINGLETON = new MemoryUtil();
	}
	
	
	public static String get(String key) {
		return SINGLETON.variables.get(key);
	}
	
	public static String get(String key, String deflt) {
		String obj = SINGLETON.variables.get(key);
		if (obj == null) {
			return deflt;
		} else {
			return obj;
		}
	}
	
	public static String get(String key, String deflt, boolean savee) {
		String obj = SINGLETON.variables.get(key);
		if (obj == null) {
			if(savee) {
			   SINGLETON.variables.put(key, deflt);
			}
			return deflt;
		} else {
			return obj;
		}
	}
	
	public static int getInt(String key, int deflt) {
		String obj = SINGLETON.variables.get(key);
		if (obj == null) {
			return deflt;
		} else {
			return new Integer(obj).intValue();
		}
	}
	
	public static void put(String key, String data) {
		if (data == null) {
			throw new IllegalArgumentException();
		} else {
			SINGLETON.variables.put(key, data);
		}
	}
}
