/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.user;

import javax.servlet.http.HttpServletRequest;

import cn.game.rjserver.uc.exception.DbException;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;

/**
 * @author rallen
 * 
 */
@Cmd(action = 8, desc = "绑定邮箱")
public class UpdateEmail implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {

		String uidStr = request.getParameter("uid");
		String email = request.getParameter("email");
		if (uidStr == null || !uidStr.matches("\\d+")) {
			throw new Exception("uid参数不正确");
		}
		long uid = Long.parseLong(uidStr);

		boolean flag = UcProviderManager.getInstance().getUserProvider().updateEmail(uid, email);

		return "<updateemail>" + flag + "</updateemail>";
	}

}
