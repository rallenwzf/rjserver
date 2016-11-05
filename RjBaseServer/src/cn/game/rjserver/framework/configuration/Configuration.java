/*********************************************
    Copyright (c) 2011 by rallen(zhifengwang)
 *********************************************/

package cn.game.rjserver.framework.configuration;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

/**
 * @author rallen
 */
public class Configuration {
	private static Map<String, String> configurationMap = new HashMap<String, String>();
	// CONFIGURATION
	public static String CONFIGURATION_FILENAME = System
			.getProperty("user.dir") + "/file.config/hiyou_server.conf";

	public static Vector<ConfigurationListener> listeners = new Vector<ConfigurationListener>();

	/**
	 * 加载配置 同文件名只单次加载
	 * 
	 * @param configFileName
	 */
	public static synchronized void configure(String configFileName) {
		if (configurationMap.size() == 0
				|| (configFileName != null && !configFileName
						.equals(CONFIGURATION_FILENAME))) {
			configurationMap.clear();
		}
		if (configFileName == null) {
			configFileName = CONFIGURATION_FILENAME;
		} else {
			CONFIGURATION_FILENAME = configFileName;
		}
		File f = new File(configFileName);
		if (f.exists()) {
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(f));
				Properties p = new Properties();
				p.load(in);
				configurationMap = (Map<String, String>) p.clone();
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				changeConfigure();
			}
		}
	}

	/**
	 * 添加Properties配置
	 * 
	 * @param configFileName
	 */
	public static void addConfigure(String configFileName) {
		File f = new File(configFileName);
		if (f.exists()) {
			try {
				InputStream in = new BufferedInputStream(new FileInputStream(f));
				Properties p = new Properties();
				p.load(in);
				configurationMap.putAll((Map<String, String>) p.clone());
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				changeConfigure();
			}
		}
	}

	private static void changeConfigure() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onChangeConfiguration();
		}
	}

	public static void clear() {
		configurationMap.clear();
	}

	public static Map<String, String> getMap() {
		return configurationMap;
	}

	public static String getConfig(String key) {
		return configurationMap.get(key);
	}

	public static void addListener(ConfigurationListener listener) {
		listeners.add(listener);
	}

	public static void removeListener(ConfigurationListener listener) {
		listeners.remove(listener);
	}

	static {
		configure(null);
	}

}
