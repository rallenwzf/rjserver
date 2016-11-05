package cn.game.rjserver.log;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.TTCCLayout;

import cn.game.rjserver.log.codec.LogCodecFactory;
import cn.game.rjserver.log.codec.LogWriter;
import cn.game.rjserver.log.codec.gamelog4j.GameLog4jFactory;
import cn.game.rjserver.log.codec.log4j.Log4jCodecFactory;

/**
 * @author Administrator 每个模块单独输出日志
 */
public class MyLogger {
	// static {
	// // log4j
	// PropertyConfigurator.configure(System.getProperty("user.dir")
	// + "/file.logAndWarn.config/log4j.properties");
	// }
	private static Map<String, MyLogger> logs = Collections.synchronizedMap(new HashMap<String, MyLogger>());
	public static String path = null;
	public static String DEFAULT_NAME = "mylog";
	public static LogCodecFactory codecfactory;

	private String name;

	private MyLogger(String name, Class c) {
		if (c != null) {
			this.name = name + "_" + c.getName();
		} else {
			this.name = name;
		}
		if (path == null) {
			path = LoggerConfig.getConfig("logPath");
		}

	}

	public synchronized static LogCodecFactory getCodecfactory() {
		if (codecfactory == null) {
			codecfactory = new Log4jCodecFactory();
		}
		return codecfactory;
	}

	public static void setCodecfactory(LogCodecFactory codecfactory) {
		GameEntityLogger.codecfactory = codecfactory;
	}

	public static MyLogger getLog(String name, Class c) {
		if (name != null) {
			if (logs.containsKey(name)) {
				return logs.get(name);
			} else {
				MyLogger log = new MyLogger(name, c);
				logs.put(name, log);
				return log;
			}
		} else {
			name = DEFAULT_NAME;
			return new MyLogger(name, c);
		}

	}

	public static MyLogger getLog(String name) {
		return getLog(name, null);
	}

	public static MyLogger getLog(Class cls) {
		return getLog("debug", null);
	}

	public static void unRegist(MyLogger log) {
		logs.values().remove(log);
	}

	public static void setPath(String path) {
		if (path == null) {
			path = LoggerConfig.getConfig("logPath");
		}
		if (path != null) {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			MyLogger.path = path;
		}

		logs.clear();
	}

	public static void unRegist(String name) {
		logs.remove(name);
	}

	public void i(Object message) {
		try {
			getCodecfactory().getWriter().write(this.path + "/" + this.name, message, LogWriter.INFO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void i(int uid, String message) {
		try {
			getCodecfactory().getWriter().write(this.path + "/" + this.name, "uid=" + uid + "|" + message,
					LogWriter.INFO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void d(Object message) {
		try {
			getCodecfactory().getWriter().write(this.path + "/" + this.name, message, LogWriter.DEBUG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void d(int uid, String message) {
		try {
			getCodecfactory().getWriter().write(this.path + "/" + this.name, "uid=" + uid + "|" + message,
					LogWriter.DEBUG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void e(Object message) {
		try {
			getCodecfactory().getWriter().write(this.path + "/" + this.name, message, LogWriter.ERROR);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
