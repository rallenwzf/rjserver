/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.user;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.PRIVATE_MEMBER;

import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.exception.DbException;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;
import cn.game.rjserver.uc.web.serverlet.handler.MessageUitls;

/**
 * @author rallen
 * 
 */
@Cmd(action = 5, desc = "获取用户信息")
public class GetUser implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {

		String userAccount = request.getParameter("userAccount");
		TucUserinfo tuserinfo = UcProviderManager.getInstance().getUserProvider().getUser(userAccount);
		StringBuffer sf = new StringBuffer();
		return MessageUitls.getTucUserinfoXml(tuserinfo, sf, true);

	}

}
