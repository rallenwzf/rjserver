/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol.analyze;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import cn.game.rjserver.framework.protocol.Agreement;

/**
 * @author Administrator 指令转换
 */
public interface CommandFormatter {

	/**
	 * 客户端指令转换为服务器Agreement
	 * 
	 * @param buf
	 * @param ag
	 * @return
	 */
	public Agreement decodeBody(Object buf, Agreement ag);

	/**
	 * 是否适用
	 * 
	 * @param cmdID
	 * @return
	 */
	public boolean accept(short cmdID);
}
