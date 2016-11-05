/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.server;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import cn.game.rjserver.uc.entity.TgsmServerUserroles;
import cn.game.rjserver.uc.entity.TgsmServers;
import cn.game.rjserver.uc.exception.SqlParameterException;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;
import cn.game.rjserver.uc.web.serverlet.handler.MessageUitls;

/**
 * @author rallen
 * 
 */
@Cmd(action = 110, desc = "获取默认服")
public class GetDefaultServer implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		String channel = request.getParameter("channel");
		String userAccount = request.getParameter("userAccount");
		StringBuffer sf = new StringBuffer();
		List<TgsmServers> list = UcProviderManager.getInstance().getServerProvider()
				.getAllOpedServerList();
		TgsmServers defaultServer = null;
		boolean b = false;
		for (int i = 0; i < list.size(); i++) {
			TgsmServers t = list.get(i);
			if (t.getCommendval() >= 100) {
				defaultServer = t;
				b = true;
				break;
			}
			if (defaultServer == null) {
				defaultServer = t;
			} else {
				if (defaultServer.getCommendval() < t.getCommendval()) {
					defaultServer = t;
				}
			}
		}
		if (b && defaultServer != null) {
			return MessageUitls.getDefaultTgsmServers(defaultServer, sf);
		} else {
			long id = UcProviderManager.getInstance().getServerProvider()
					.getUserLastServerid(userAccount);
			for (int i = 0; i < list.size(); i++) {
				TgsmServers t = list.get(i);
				if (t.getServerid() == id) {
					defaultServer = t;
					break;
				}
			}
			if (defaultServer != null) {
				return MessageUitls.getDefaultTgsmServers(defaultServer, sf);
			}
		}

		return null;
	}
}
