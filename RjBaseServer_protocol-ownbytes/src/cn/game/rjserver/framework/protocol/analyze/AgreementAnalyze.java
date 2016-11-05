/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.protocol.analyze;

import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.protocol.Header;
import cn.game.rjserver.framework.protocol.Message;

/**
 * @author Administrator
 */
public interface AgreementAnalyze {
	/**
	 * @param buf
	 * @param session
	 * @return
	 */
	public Header decodeHead(Object buf, GameIoSession session);

	/**
	 * @param buf
	 * @return
	 */
	public Agreement decodeBody(Agreement ag, Object buf, GameIoSession session);

	/**
	 * @param buf
	 * @return
	 */
	public Agreement decode(Object buf, GameIoSession session);

	/**
	 * @param ag
	 * @param buf
	 */
	public void encode(Agreement ag, Object buf, GameIoSession session);

	/**
	 * @param ag
	 * @return
	 */
	public int getEncodeAgreementLength(Agreement ag);

}
