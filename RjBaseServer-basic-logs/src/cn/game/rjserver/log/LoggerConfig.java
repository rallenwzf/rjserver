/**
 * rallen(wzf)
 */
package cn.game.rjserver.log;

import java.util.HashMap;
import java.util.Map;

import cn.game.rjserver.warn.Global;

/**
 * @author rallen
 *
 */
public class LoggerConfig {
	private static Map<String, String> configurationMap = new HashMap<String, String>();

	public static Map<String, String> getConfigurationMap() {
		return configurationMap;
	}

	public static void setConfigurationMap(Map<String, String> configurationMap) {
		LoggerConfig.configurationMap = configurationMap;
		Global.load();
	}
	
	public static String getConfig(String key) {
		return configurationMap.get(key);
	}
}
