/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.codec;

import org.apache.log4j.Logger;

import cn.game.rjserver.log.LoggerConfig;


/**
 * @author wangzhifeng(rallen)
 */
public abstract class LogWriterImpl implements LogWriter {
	public static String DEFAULT_ENCODING = "utf-8";
	public String encoding;
	public boolean startThread = true;

	@Override
	public void write(String fileName, Object log, int level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close(String logKey) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getLogger(String fileName) {
		// TODO Auto-generated method stub
		return null;
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
}
