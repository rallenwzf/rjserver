package cn.game.rjserver.log.codec;

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
import java.util.Vector;

import org.apache.log4j.Logger;

import cn.game.rjserver.log.LoggerConfig;
import cn.game.rjserver.log.entity.HyLog;
import cn.game.rjserver.log.push.Pusher;

public abstract class LogReaderImpl implements LogReader {
	private String logPath;
	public static String DEFAULT_ENCODING = "utf-8";
	public String encoding;
	public boolean startThread = true;
	public ReaderThread readerThread;
	private Pusher pusher;

	public LogReaderImpl() {
		this(null);
	}

	public LogReaderImpl(String logPath) {
		this.logPath = logPath;
	}

	@Override
	public void setPusher(Pusher pusher) {
		// TODO Auto-generated method stub
		this.pusher = pusher;
	}

	@Override
	public Pusher getPusher() {
		// TODO Auto-generated method stub
		return pusher;
	}

	@Override
	public void setStartThread(boolean start) {
		// TODO Auto-generated method stub
		this.startThread = start;
	}

	@Override
	public boolean getStartThread(boolean start) {
		// TODO Auto-generated method stub
		return this.startThread;
	}

	@Override
	public void read() {
		// TODO Auto-generated method stub
		if (this.logPath == null || this.logPath.equals("")) {
			logger.debug("文件目录未找着");
		} else {
			File f = new File(logPath);
			List<String> list = readFiles(f, null);
			for (String path : list) {
				logger.debug("load..." + path);
				read(path);
			}
		}
	}

	@Override
	public void read(String filePath) {
		// TODO Auto-generated method stub
		if (startThread) {
			if (readerThread == null) {
				readerThread = new ReaderThread(this);
				readerThread.addHandleList(filePath);
				readerThread.start();
			}
		} else {
			this.readSigle(filePath);
		}
	}

	@Override
	public abstract void readSigle(String filePath);

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

	class ReaderThread extends Thread {
		private LogReader reader;
		private Vector<String> handleList;

		public ReaderThread(LogReader reader) {
			this.reader = reader;
			handleList = new Vector<String>();
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (handleList.size() > 0) {
				String fileName = handleList.remove(0);
				reader.readSigle(fileName);
			}
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void addHandleList(String fileName) {
			handleList.add(fileName);
			this.notifyAll();
		}

		public LogReader getReader() {
			return reader;
		}

		public void setReader(LogReader reader) {
			this.reader = reader;
		}

	}

	/**
	 * @param f
	 * @param list
	 * @return
	 */
	public static List<String> readFiles(File f, List<String> list) {
		if (list == null) {
			list = new ArrayList<String>();
		}
		if (f.isDirectory()) {
			File fs[] = f.listFiles();
			for (int i = 0; i < fs.length; i++) {
				readFiles(fs[i], list);
			}
		} else {
			list.add(f.getAbsolutePath());
		}
		return list;
	}

	public static Date StringToTime(String dateString) {
		Date d = null;
		String str[] = { "HH:mm:ss", "H:mm:ss" };
		for (int i = 0; i < str.length; i++) {
			try {
				if (d == null) {
					d = StringToDate(dateString, str[i]);
				} else {
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return d;
	}

	public static Date StringToDate(String dateString) {
		Date d = null;
		String str[] = { "yyyy-MM-dd", "yyyy-M-dd", "yyyy-MM-d", "yyyy-M-d" };
		for (int i = 0; i < str.length; i++) {
			try {
				if (d == null) {
					d = StringToDate(dateString, str[i]);
				} else {
					break;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return d;
	}

	public static Date StringToDate(String dateString, String patterm) {
		SimpleDateFormat sdf = new SimpleDateFormat(patterm);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return null;
	}

	public static void writeFile(String filePath, String content, boolean isAppend, String fileCode) {
		File f = new File(filePath);
		OutputStreamWriter out = null;
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			// FileWriter fw=new FileWriter(logf);
			out = new OutputStreamWriter(new FileOutputStream(filePath, isAppend), fileCode);
			out.append(content);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getEncoding() {
		if (encoding == null) {
			encoding = LoggerConfig.getConfig("encoding");
			if (encoding == null) {
				encoding = DEFAULT_ENCODING;
			}
		}
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getLogPath() {
		return logPath;
	}

	public void setLogPath(String logPath) {
		this.logPath = logPath;
	}
}
