/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.user;

import javax.servlet.http.HttpServletRequest;

import cn.game.rjserver.uc.entity.TucUsers;
import cn.game.rjserver.uc.exception.DbException;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;

/**
 * @author rallen
 * 
 */
@Cmd(action = 7, desc = "退出")
public class Logout implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		String uidStr = request.getParameter("uid");
		if (uidStr == null || !uidStr.matches("\\d+")) {
			throw new Exception("uid参数不正确");
		}
		// long uid = Long.parseLong(uidStr);
		//
		// TucUserLogoutlog log = new TucUserLogoutlog();
		// TucUsers tucUsers = new TucUsers();
		// tucUsers.setUid(uid);
		// log.setTucUsers(tucUsers);
		// boolean flag =
		// UcProviderManager.getInstance().getUserProvider().addLogoutLog(log);
		return "<addLogoutLog>" + true + "</addLogoutLog>";
	}

}
