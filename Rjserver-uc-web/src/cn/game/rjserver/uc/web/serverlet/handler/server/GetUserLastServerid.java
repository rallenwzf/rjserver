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
@Cmd(action = 104, desc = "获取用户最后一次登录的服务器ID")
public class GetUserLastServerid implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		String userAccount = request.getParameter("userAccount");
		long serverId = UcProviderManager.getInstance().getServerProvider()
				.getUserLastServerid(userAccount);
		return "<userLastServerid>" + serverId + "</userLastServerid>";
	}

}
