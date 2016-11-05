/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.server;

import javax.servlet.http.HttpServletRequest;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;

/**
 * @author rallen
 * 
 */
@Cmd(action = 105, desc = "角色创建或升级")
public class SendRole implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		long uid = Long.parseLong(request.getParameter("uid"));
		long serverid = Long.parseLong(request.getParameter("serverid"));
		long roleid = Long.parseLong(request.getParameter("roleid"));
		int level = Integer.parseInt(request.getParameter("level"));
		String rolenikename = request.getParameter("rolenikename");
		String head = request.getParameter("head");
		boolean b = UcProviderManager.getInstance().getServerProvider()
				.addServerRole(serverid, uid, roleid, level, rolenikename,head);
		return "<result>" + (b ? 0 : 1) + "</result>";
	}

}
