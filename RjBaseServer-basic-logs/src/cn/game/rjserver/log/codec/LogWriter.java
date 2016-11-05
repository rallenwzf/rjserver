/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.codec;

import org.apache.log4j.Logger;

/**
 * @author wangzhifeng(rallen)
 */
public interface LogWriter {

	public static final int INFO = 1;
	public static final int DEBUG = 2;
	public static final int ERROR = 3;

	/**
	 * @param log
	 */
	public void write(String fileName, Object log, int level);

	public Object getLogger(String logKey);

	public void setStartThread(boolean start);

	public boolean getStartThread(boolean start);

	public void close(String logKey);

}
