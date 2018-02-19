package pfq.store;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class AppSettings {
	private Properties prop = new Properties();
	private File propertiesFile ;
	private static AppSettings SINGLETON;

	private AppSettings() {
		
		propertiesFile = new File("settings.properties");
	     
		if (propertiesFile.exists() && propertiesExist()) {
            System.out.println("Properties file was found and is intact");
        } else {
            System.out.println("Properties file is being created");
            createProperties();
            System.out.println("Properties was created!");
        }
       
	}

	static {
		SINGLETON = new AppSettings();
	}
	
	private  boolean propertiesExist() {
		InputStream input = null;
		boolean exists = false;

		try {
			input = new FileInputStream(propertiesFile);

			prop.load(input);

			exists = prop.getProperty("host") != null && prop.getProperty("port") != null;

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return exists;
	}

	private  void createProperties() {
			prop.setProperty("host", "127.0.0.1");
			prop.setProperty("port", "8080");
			saveProperties();	
	}

	private void saveProperties() {
		OutputStream output = null;
		try {
			output = new FileOutputStream(propertiesFile);
			prop.store(output, null);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String get(String key) {
		return SINGLETON.prop.getProperty(key);
	}
	
	public static String get(String key, String deflt) {
		String obj = SINGLETON.prop.getProperty(key);
		if (obj == null) {
			return deflt;
		} else {
			return obj;
		}
	}
	
	public static int getInt(String key, int deflt) {
		String obj = SINGLETON.prop.getProperty(key);
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
			SINGLETON.prop.setProperty(key, data);
		}
	}
	
	public static void save() {
		SINGLETON.saveProperties();
	}
	
	
}
