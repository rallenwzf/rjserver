package cn.game.rjserver.log.api;

import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.game.rjserver.log.GameEntityLogger;
import cn.game.rjserver.log.LoggerConfig;


/**
 * @author wangzhifeng(rallen) 分小时存储
 */
public class WriteDatasLogImpl implements WriteDatasLog {
	/**
	 * 分隔符
	 */
	public static String DATAS_DELIMITER = ",";
	public static String LOGPATH = null;
	private static WriteDatasLogImpl hiyouLog;

	private WriteDatasLogImpl() {
	}

	private static String getLogPath() {
		if (LOGPATH == null) {
			String dataslogPath = LoggerConfig.getConfig("dataslogPath");
			if (dataslogPath == null || dataslogPath.trim().equals("")) {
				LOGPATH = "dataslog";
			} else {
				LOGPATH = dataslogPath;
			}
		}
		return LOGPATH;
	}

	/**
	 * 分小时存储日志文件
	 * 
	 * @return
	 */
	public static WriteDatasLog getLog() {
		String path = getPathByHour(new Date());
		GameEntityLogger.setPath(path);
		return getLog(path);
	}

	public static String getPathByHour(Date time) {
		SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd");
		String today = st.format(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
		if (hour.length() == 1)
			hour = "0" + hour;
		String path = (new StringBuilder(String.valueOf(getLogPath()))).append(File.separator).append(today)
				.append(File.separator).append(hour).toString();
		return path;
	}

	private static WriteDatasLog getLog(String path) {
		if (path != null && path != "") {
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
		if (hiyouLog == null)
			hiyouLog = new WriteDatasLogImpl();
		return hiyouLog;
	}

	@Override
	public void writeMessage(String funcName, int type, String message) {
		GameEntityLogger gameEntityLogger = GameEntityLogger.getLog(funcName);
		gameEntityLogger
				.i((new StringBuilder(String.valueOf(type))).append(DATAS_DELIMITER).append(message).toString());
	}

	@Override
	public void writeMessage(String funcName, int type, Object[] message) {
		// TODO Auto-generated method stub
		GameEntityLogger gameEntityLogger = GameEntityLogger.getLog(funcName);
		StringBuffer sbuff = new StringBuffer();
		if (message != null) {
			for (int i = 0; i < message.length; i++) {
				String str = "";
				if (message[i] != null)
					str = message[i].toString();
				sbuff.append(str);
				if (i < message.length - 1) {
					sbuff.append(DATAS_DELIMITER);
				}
			}
		}
		gameEntityLogger.i((new StringBuilder(String.valueOf(type))).append(DATAS_DELIMITER).append(sbuff.toString())
				.toString());
	}

}