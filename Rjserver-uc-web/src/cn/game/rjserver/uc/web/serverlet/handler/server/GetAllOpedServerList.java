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
@Cmd(action = 101, desc = "获取所有已开启服务器，包含排序")
public class GetAllOpedServerList implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		StringBuffer sf = new StringBuffer();
		List<TgsmServers> list = UcProviderManager.getInstance().getServerProvider().getAllOpedServerList();
		return MessageUitls.getTgsmServersList(list, sf);
//		if (list != null && !list.isEmpty()) {
//			return MessageUitls.getTgsmServersList(list, sf);
//		} else {
//			throw new Exception(this.getClass().getName() + "参数错误");
//		}
	}
}
