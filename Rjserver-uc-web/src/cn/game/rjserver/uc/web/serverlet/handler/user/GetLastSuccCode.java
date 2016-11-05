/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.user;

import javax.servlet.http.HttpServletRequest;

import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;
import cn.game.rjserver.uc.web.datas.DatasManager;

/**
 * @author rallen
 * 
 */
@Cmd(action = 4, desc = "获取最新的校验码")
public class GetLastSuccCode implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {

		String userAccount = request.getParameter("userAccount");
		String str = DatasManager.getDatasManager().getUserCodeMap().get(userAccount.toLowerCase());
		// DatasManager.getDatasManager().getUserCodeMap().remove(userAccount +
		// "_" + gameCode);
		return "<lastSuccCode>" + str + "</lastSuccCode>";
	}

}
