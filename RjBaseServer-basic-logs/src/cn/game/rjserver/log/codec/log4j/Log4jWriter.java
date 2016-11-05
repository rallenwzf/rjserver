/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.codec.log4j;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.TTCCLayout;

import cn.game.rjserver.log.GameEntityLogger;
import cn.game.rjserver.log.MyLogger;
import cn.game.rjserver.log.codec.LogReader;
import cn.game.rjserver.log.codec.LogWriter;
import cn.game.rjserver.log.codec.LogWriterImpl;
import cn.game.rjserver.warn.util.ExceptionUtils;

/**
 * @author wangzhifeng(rallen)
 */
public class Log4jWriter extends LogWriterImpl {
	public static Map<String, Logger> logs = Collections.synchronizedMap(new HashMap<String, Logger>());
	public static String DEFAULT_NAME = "hy_debug";

	public Log4jWriter() {

	}

	protected Logger createLogger(String name) {
		// TTCCLayout layout = new TTCCLayout("yyyy-MM-dd HH:mm:ss");
		// layout.setTimeZone("GMT+8:00");
		PatternLayout layout = new PatternLayout("yyyy-MM-dd HH:mm:ss");
		layout.setConversionPattern("%-d{yyyy-MM-dd HH:mm:ss.SSS} %5p [%t] - %m%n");
		// layout.activateOptions();
		DailyRollingFileAppender fileAppender = null;
		try {
			fileAppender = new DailyRollingFileAppender(layout, name, "'_'yyyy-MM-dd'.log'");
			// fileAppender.setEncoding("gb2312");
			// fileAppender.setAppend(true);
			// fileAppender.setDatePattern("'_'yyyy-MM-dd'.log'");
		} catch (Exception e) {
			// TODO: handle exception
		}
		Logger log = Logger.getLogger(name);
		log.setLevel(Level.ALL);
		log.addAppender(fileAppender);
		return log;
	}

	public Logger findLogger(String name) {
		if (name != null && !name.equals("")) {
			if (logs.get(name) != null) {
				return logs.get(name);
			} else {
				Logger logger = createLogger(name);
				logs.put(name, logger);
				return logger;
			}
		} else {
			if (DEFAULT_NAME != null && !DEFAULT_NAME.equals("")) {
				return findLogger(DEFAULT_NAME);
			}
			return null;
		}
	}

	@Override
	public void write(String fileName, Object log, int level) {
		// TODO Auto-generated method stub
		Logger logger = findLogger(fileName);
		if (logger != null) {
			String content = log.toString();
			Throwable t = null;
			if (log instanceof Throwable) {
				t = (Throwable) log;
				content = ExceptionUtils.getTrace(t);
			}
			if (level == INFO) {
				logger.info(content);
			} else if (level == ERROR) {
				logger.error(content);
			} else {
				logger.debug(content);
			}
		}
	}

	@Override
	public Object getLogger(String fileName) {
		// TODO Auto-generated method stub
		return findLogger(fileName);
	}

	@Override
	public void close(String logKey) {
		// TODO Auto-generated method stub
		Logger logger = findLogger(logKey);
		if (logger != null) {
			Enumeration<FileAppender> em = logger.getAllAppenders();
			while (em.hasMoreElements()) {
				em.nextElement().close();
				logs.remove(logKey);
			}
		}
	}

}
