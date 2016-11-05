/**
 * rallen(wzf)
 */
package cn.game.rjserver.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author rallen
 * 
 */
public class ModelUtilsConfig {
	private static Map<String, String> configurationMap = null;

	public static Map<String, String> getConfigurationMap() {
		return configurationMap;
	}

	public static void setConfigurationMap(Map<String, String> configurationMap) {
		ModelUtilsConfig.configurationMap = configurationMap;
	}

	public static String getConfig(String key) {
		return configurationMap.get(key);
	}
}
