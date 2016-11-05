/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.actioncmd;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rallen
 * 
 */
public interface ActionCommand {

	public String execute(HttpServletRequest request) throws Exception;

}
