package cn.game.rjserver.log.codec.gamelog4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.game.rjserver.log.LoggerConfig;
import cn.game.rjserver.log.codec.LogReader;
import cn.game.rjserver.log.codec.LogReaderImpl;
import cn.game.rjserver.log.entity.HyLog;

public class GameLog4jReader extends LogReaderImpl {
	public static boolean threadPools = false;

	public GameLog4jReader() {
		super();
	}

	public GameLog4jReader(String logPath) {
		super(logPath);
	}

	@Override
	public void readSigle(String filePath) {
		BufferedReader reader = null;
		try {
			File logFile = new File(filePath);
			if (logFile.exists()) {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile),
						LoggerConfig.getConfig("fileCode")));
				String line;
				// 循环读取内容
				int id = 0;
				int count = 0;
				String name = logFile.getName();
				System.out.println("file=" + name + " |id=" + id);
				while ((line = reader.readLine()) != null) {
					if (line != null && !line.trim().equals("")) {
						if (line.contains("[") && line.contains("]")) {
							line = line.replaceFirst("\\[[\\s\\S]*\\]", "\t");
						}
						String str[] = line.split("\t");
						if (str.length < 3) {
							// log时间、行为、用户号是必须存在的
							// System.out.println(line);
							continue;
						}
						HyLog log = new HyLog();
						log.setAction(str[1]);
						log.setUserAccount(str[2]);
						log.setIsReal(str[3]);
						log.setChannel(str[4]);
						try {
							log.setStartDate(StringToDate(str[5]));
							log.setStartTime(StringToTime(str[6]));
							log.setStartHour(str[7]);
							log.setEndDate(StringToDate(str[8]));
							log.setEndTime(StringToTime(str[9]));
							log.setEndHour(str[10]);
							if (!str[11].equals("")) {
								log.setSeconds(Integer.parseInt(str[11]));
							}
							log.setPropsAction(str[12]);
						} catch (Exception e) {
						}
						pushHiyouLog(log);
					}
				}
				System.out.println("file=" + name + " |总加载量：" + count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param log
	 */
	protected void pushHiyouLog(HyLog log) {
		try {
			// BaseTblProvider.getInstance().saveOrUpdateEntityImp(log);
			if (this.getPusher() != null) {
				this.getPusher().pushHiyouLog(log);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
