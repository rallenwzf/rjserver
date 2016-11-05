package cn.game.rjserver.log;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import cn.game.rjserver.log.codec.LogCodecFactory;
import cn.game.rjserver.log.codec.gamelog4j.GameLog4jFactory;

/**
 * @author Administrator
 */
public class GameEntityLogger {

	private static String path = null;
	public static String DEFAULT_NAME = "game";
	public static LogCodecFactory codecfactory;
	private static Map<String, GameEntityLogger> logs = Collections.synchronizedMap(new HashMap<String, GameEntityLogger>());
	private static GameEntityPushToOtherSdk pusher;
	private String name;

	private GameEntityLogger(String name) {
		this.name = name;
		if (path == null) {
			path = LoggerConfig.getConfig("gameDataslogPath");
		}
	}

	public synchronized static LogCodecFactory getCodecfactory() {
		if (codecfactory == null) {
			codecfactory = new GameLog4jFactory();
		}
		return codecfactory;
	}

	public static void setCodecfactory(LogCodecFactory codecfactory) {
		GameEntityLogger.codecfactory = codecfactory;
	}

	/**
	 * 其他方法调用前调用
	 * 
	 * @param path
	 */
	public static void setPath(String path) {
		if (path == null) {
			path = LoggerConfig.getConfig("hiyoulogPath");
		} else {
			GameEntityLogger.path = path;
		}
	}

	public synchronized static GameEntityLogger getLog() {
		return getLog(DEFAULT_NAME);
	}

	public synchronized static GameEntityLogger getLog(String name) {
		String cacheName = name;
		if (name != null && !name.equals("")) {
			if (path != null && !path.equals("")) {
				name = path + "/" + name;
			}
			if (logs.get(name) != null) {
				return logs.get(name);
			} else {
				closeLastHourFile(path, cacheName);
				GameEntityLogger logger = new GameEntityLogger(name);
				logs.put(name, logger);
				return logger;
			}
		} else {
			return getLog(DEFAULT_NAME);
		}
	}

	/**
	 * 释放之前的文件操作权 [主要是控制按小时的日志生成方式]
	 * 
	 * @param name
	 */
	public static void closeLastHourFile(String path, String name) {
		try {
			Iterator<String> it = logs.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				if (key.contains(name)) {
					logs.get(key).close(key);
				}
			}
		} catch (Exception e) {
		}
	}

	public static Map<String, GameEntityLogger> getLogs() {
		return logs;
	}

	public void close(String logKey) {
		try {
			getCodecfactory().getWriter().close(logKey);
			logs.remove(logKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static GameEntityPushToOtherSdk getPusher() {
		return pusher;
	}

	public static void setPusher(GameEntityPushToOtherSdk pusherparma) {
		pusher = pusherparma;
	}

	/**
	 * @param logEntire
	 *            .toString()
	 */
	public void i(Object logEntire) {
		try {
			getCodecfactory().getWriter().write(this.name, logEntire, 0);
			if (pusher != null) {
				pusher.pushTosdk(logEntire.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void i(String message) {
		try {
			getCodecfactory().getWriter().write(this.name, message, 0);
			if (pusher != null) {
				pusher.pushTosdk(message);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
