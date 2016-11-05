/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.response.group;


import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;


/**
 * @author Administrator
 * 
 */
public interface ResponseAction {
	public void messageReceived(GameIoSession session, Agreement ag);

	public void messageCallbacked(GameIoSession session, Agreement ag);
}
