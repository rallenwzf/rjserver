/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.codec.gamelog4j;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import cn.game.rjserver.log.GameEntityLogger;
import cn.game.rjserver.log.MyLogger;
import cn.game.rjserver.log.codec.LogReader;
import cn.game.rjserver.log.codec.LogWriter;
import cn.game.rjserver.log.codec.LogWriterImpl;

/**
 * @author wangzhifeng(rallen)
 *         按小时存储
 */
public class GameLog4jWriter extends LogWriterImpl {
	private static Map<String, Logger> logs = Collections.synchronizedMap(new HashMap<String, Logger>());

	public GameLog4jWriter() {

	}

	protected Logger createLogger(String name) {
		PatternLayout layout = new PatternLayout("yyyy-MM-dd HH:mm:ss");
		layout.setConversionPattern("%-d{yyyy-MM-dd HH:mm:ss},%m%n");
		// layout.activateOptions();
		DailyRollingFileAppender fileAppender = null;
		try {
			fileAppender = new DailyRollingFileAppender(layout, name, "'_'HH'.log'");
			fileAppender.setEncoding(this.getEncoding());
			// fileAppender.setAppend(true);
			// fileAppender.setDatePattern("'_'yyyy-MM-dd'.log'");
			// fileAppender.setThreshold(Priority.INFO);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Logger log = Logger.getLogger(name);
		log.setLevel(Level.INFO);
		log.addAppender(fileAppender);
		return log;
	}

	@Override
	public void close(String key) {
		Logger logger = findLogger(key);
		if (logger != null) {
			Enumeration<FileAppender> em = logger.getAllAppenders();
			while (em.hasMoreElements()) {
				em.nextElement().close();
				logs.remove(key);
			}
		}
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
			return null;
		}
	}

	@Override
	public void write(String fileName, Object log, int level) {
		// TODO Auto-generated method stub
		Logger logger = findLogger(fileName);
		if (logger != null) {
			logger.info(log.toString());
		}
	}

	@Override
	public Object getLogger(String fileName) {
		// TODO Auto-generated method stub
		return findLogger(fileName);
	}
}
