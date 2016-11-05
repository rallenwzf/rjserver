/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.push;

import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.BaseAgreement;

/**
 * @author Administrator
 * 
 */
public interface ServerPushAction {
	public void push(GameIoSession session, BaseAgreement ag);

}
