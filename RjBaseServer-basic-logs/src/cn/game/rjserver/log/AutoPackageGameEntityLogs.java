/**
 * rallen.wang@gmail.com
 */
package cn.game.rjserver.log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import cn.game.rjserver.log.api.WriteDatasLogImpl;
import cn.game.rjserver.log.zip.GameEntityLogsPackager;

/**
 * @author wangzhifeng(rallen)
 */
public class AutoPackageGameEntityLogs {
	static Logger logger = Logger.getLogger(AutoPackageGameEntityLogs.class);

	/**
	 * 启动按小时自动打包日志，每小时5分打包上小时的日志
	 */
	public static void startAutoPackage() {
		// 每小时执行一次
		Timer timer;
		long countTime = 60 * 60 * 1000;
		Calendar calendar = Calendar.getInstance();
		// calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 5);
		calendar.set(Calendar.SECOND, 0);
		Date time = calendar.getTime();
		timer = new Timer();
		// 判断首次执行时间
		if (time.before(new Date())) {
			calendar.add(Calendar.HOUR_OF_DAY, 1);
			time = calendar.getTime();
		}
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Calendar time = Calendar.getInstance();
				time.add(Calendar.HOUR_OF_DAY, -1);
				time.set(Calendar.MINUTE, 0);
				time.set(Calendar.SECOND, 0);
				packageLogs(time.getTime());
			}
		}, time, countTime);
	}

	public static void packageLogs(Date time) {
		GameEntityLogsPackager gelp = new GameEntityLogsPackager();
		String logSourcePath = WriteDatasLogImpl.getPathByHour(time);
		String logTargetFilePath = getLogTargetFilePath(time);
		String logTargetFileName = getLogTargetFileName(time);
		String logBakPath = getLogBakPath(time);
		//
		gelp.setLogSourcePath(logSourcePath);
		gelp.setLogTargetFilePath(logTargetFilePath);
		gelp.setLogTargetFileName(logTargetFileName);
		gelp.setLogBakPath(logBakPath);
		gelp.createPackLog();
	}

	private static String getLogTargetFilePath(Date time) {
		String dataslogPath = LoggerConfig.getConfig("dataslogZipPath");
		String logpath = "";
		if (dataslogPath == null || dataslogPath.trim().equals("")) {
			logpath = "dataslog_zip";
		} else {
			logpath = dataslogPath;
		}
		SimpleDateFormat st = new SimpleDateFormat("yyyyMM");
		String today = st.format(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		String day = "" + cal.get(Calendar.DAY_OF_MONTH);
		if (day.length() == 1)
			day = "0" + day;
		String path = (new StringBuilder(String.valueOf(logpath))).append(File.separator)
				.append(today).append(File.separator).append(day).toString();
		return path;
	}

	private static String getLogTargetFileName(Date time) {
		SimpleDateFormat st = new SimpleDateFormat("yyyyMMddHH");
		String today = st.format(time);
		String name = today + ".zip";
		return name;
	}

	private static String getLogBakPath(Date time) {
		String dataslogPath = LoggerConfig.getConfig("dataslogBakPath");
		String logpath = "";
		if (dataslogPath == null || dataslogPath.trim().equals("")) {
			logpath = "dataslog_bak";
		} else {
			logpath = dataslogPath;
		}
		SimpleDateFormat st = new SimpleDateFormat("yyyy-MM");
		String today = st.format(time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		String day = "" + cal.get(Calendar.DAY_OF_MONTH);
		if (day.length() == 1)
			day = "0" + day;
		String hour = "" + cal.get(Calendar.HOUR_OF_DAY);
		if (hour.length() == 1)
			hour = "0" + hour;
		String path = (new StringBuilder(String.valueOf(logpath))).append(File.separator)
				.append(today).append(File.separator).append(day).append(File.separator)
				.append(hour).toString();
		return path;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
