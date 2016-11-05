/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.server;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import cn.game.rjserver.uc.entity.TgsmGameupdate;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;
import cn.game.rjserver.uc.web.serverlet.handler.MessageUitls;

/**
 * @author rallen
 * 
 */
@Cmd(action = 102, desc = "获取某版本上层的所有升级信息")
public class GetAllUpgradeList implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		int maxVersion = Integer.parseInt(request.getParameter("maxVersion"));
		StringBuffer sf = new StringBuffer();

		List<TgsmGameupdate> list = UcProviderManager.getInstance().getServerProvider()
				.getAllServerVersionList( maxVersion);
		return MessageUitls.getTgsmGameupdateListXml(list, sf);
//		if (list != null && !list.isEmpty()) {
//			return MessageUitls.getTgsmGameupdateListXml(list, sf);
//		} else {
//			throw new Exception(this.getClass().getName() + "未查找到信息");
//		}
	}

}
