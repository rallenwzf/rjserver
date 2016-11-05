/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.codec.log4j;

import cn.game.rjserver.log.codec.LogCodecFactory;
import cn.game.rjserver.log.codec.LogReader;
import cn.game.rjserver.log.codec.LogWriter;

/**
 * @author wangzhifeng(rallen) log4j日志记录
 * 
 */
public class Log4jCodecFactory implements LogCodecFactory {

	private final LogWriter writer = new Log4jWriter();
	private final LogReader reader = new Log4jReader();

	@Override
	public LogWriter getWriter() throws Exception {
		// TODO Auto-generated method stub
		return writer;
	}

	@Override
	public LogReader getReader() throws Exception {
		// TODO Auto-generated method stub
		return reader;
	}

}
