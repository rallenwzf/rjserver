/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.server;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.game.rjserver.uc.entity.TgsmServers;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.CallToGameserver;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;

/**
 * @author rallen
 * 
 */
@Cmd(action = 111, desc = "用户进入、退出游戏服")
public class InoutServer implements ActionCommand {
	static Map<Long, Long> map = new HashMap<Long, Long>();

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		long uid = Long.parseLong(request.getParameter("uid"));
		long serverid = Long.parseLong(request.getParameter("serverid"));
		String isinto = request.getParameter("isinto");
		if (Integer.parseInt(isinto) == 1) {
			//
			try {
				if (map.containsKey(uid)) {
					if (map.get(uid).longValue() == serverid) {
						//
					} else {
						// 通知游戏服断开用户；剔除用户
						TgsmServers t = UcProviderManager.getInstance().getServerProvider().getServer(serverid);
						System.out.println("callExit:" + t.getServerhosting());
						CallToGameserver.callExit(t.getServerhosting(), uid);
						System.out.println("callExit end :" + t.getServerhosting());
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				map.put(uid, serverid);
				UcProviderManager.getInstance().getServerProvider().updateServerRoleDate(serverid, uid);
			}
		} else {
			if (map.containsKey(uid)) {
				if (map.get(uid).longValue() == serverid) {
					map.remove(uid);
				}
			}

		}

		return "<result>0</result>";
	}

}
