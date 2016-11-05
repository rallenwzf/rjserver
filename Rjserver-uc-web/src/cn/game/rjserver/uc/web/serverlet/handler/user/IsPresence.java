/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.user;

import javax.servlet.http.HttpServletRequest;

import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;

/**
 * @author rallen
 * 
 */
@Cmd(action = 6, desc = "判断用户账户是否存在")
public class IsPresence implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
	
		String userAccount = request.getParameter("userAccount");	
		boolean flag = UcProviderManager.getInstance().getUserProvider().isHasUser(userAccount);			
		return "<isHasUser>" + flag + "</isHasUser>";
	}

}
