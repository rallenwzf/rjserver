/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.log.codec;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public interface LogCodecFactory {
	/**
	 * @return
	 * @throws Exception
	 */
	LogWriter getWriter() throws Exception;

	/**
	 * @return
	 * @throws Exception
	 */
	LogReader getReader() throws Exception;
	
	
}
