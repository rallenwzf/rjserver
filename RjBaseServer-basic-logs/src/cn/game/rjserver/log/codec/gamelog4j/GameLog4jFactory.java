/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.codec.gamelog4j;

import cn.game.rjserver.log.codec.LogCodecFactory;
import cn.game.rjserver.log.codec.LogReader;
import cn.game.rjserver.log.codec.LogWriter;

/**
 * @author wangzhifeng(rallen) 日志实体
 * 
 */
public class GameLog4jFactory implements LogCodecFactory {

	private final LogWriter writer = new GameLog4jWriter();
	private final LogReader reader = new GameLog4jReader();

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
