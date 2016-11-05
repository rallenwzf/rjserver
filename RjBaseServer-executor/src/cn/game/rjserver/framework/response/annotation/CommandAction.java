/**
 * rallen(wzf)
 */
package cn.game.rjserver.framework.response.annotation;

import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.framework.protocol.Agreement;

/**
 * @author rallen
 * 
 */
public interface CommandAction {

	public void execute(long urid, Agreement cag, GameIoSession session) throws Exception;

}
