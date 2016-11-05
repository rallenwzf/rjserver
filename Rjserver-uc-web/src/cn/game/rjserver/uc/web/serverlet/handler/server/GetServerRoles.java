/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.game.rjserver.uc.entity.TgsmServerUserroles;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;
import cn.game.rjserver.uc.web.serverlet.handler.MessageUitls;

/**
 * @author rallen
 * 
 */
@Cmd(action = 103, desc = "获取所有服务器上角色信息")
public class GetServerRoles implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		long uid = Long.parseLong(request.getParameter("uid"));
		List<TgsmServerUserroles> list = UcProviderManager.getInstance().getServerProvider().getServerRolesList(uid);
		StringBuffer sf = new StringBuffer();
		return MessageUitls.getTgsmServerUserrolesList(list, sf);
	}

}
