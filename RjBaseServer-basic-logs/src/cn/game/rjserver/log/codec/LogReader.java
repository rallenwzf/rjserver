/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.codec;

import java.io.File;

import org.apache.log4j.Logger;

import cn.game.rjserver.log.push.Pusher;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public interface LogReader {
	static Logger logger = Logger.getLogger(LogReader.class.getName());

	/**
	 * 读日志
	 */
	public void read();

	/**
	 * 读某个日志文件
	 * 
	 * @param log
	 */
	public void read(String filePath);

	public void readSigle(String filePath);

	public void setStartThread(boolean start);

	public boolean getStartThread(boolean start);

	public void setPusher(Pusher pusher);

	public Pusher getPusher();
}
