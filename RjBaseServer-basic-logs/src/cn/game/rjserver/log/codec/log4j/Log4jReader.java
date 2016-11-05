package cn.game.rjserver.log.codec.log4j;

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

public class Log4jReader extends LogReaderImpl {
	private Thread readerThread;

	public Log4jReader() {
		super();
	}

	public Log4jReader(String logPath) {
		super(logPath);
	}

	@Override
	public void readSigle(String filePath) {
		BufferedReader reader = null;
		try {
			File logFile = new File(filePath);
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(logFile),
					LoggerConfig.getConfig("fileCode")));
			String line;
			// 循环读取内容
			int id = 0;
			String name = logFile.getName();
			String hiyouLogPath = logFile.getAbsolutePath() + "/hiyouData_" + name;
			System.out.println("file=" + name + " |id=" + ++id);
			while ((line = reader.readLine()) != null) {
				if (line != null && !line.trim().equals("")) {
					if (line.contains("HiyouLog")) {
						String qz[] = line.split("INFO");
						// String content = qz[0] + "\r\t" +
						// qz[1].split("- ")[1];
						// // System.out.println();
						// writeFile(hiyouLogPath, content + "\r\n", true);
						String content = qz[0] + "\t" + qz[1].split("- ")[1];
						// System.out.println();
						if (content.contains("[") && content.contains("]")) {
							content = content.replaceFirst("\\[[\\s\\S]*\\]", "\t");
						}
						writeFile(hiyouLogPath, content + "\n", true, null);
					}
				}
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

}
