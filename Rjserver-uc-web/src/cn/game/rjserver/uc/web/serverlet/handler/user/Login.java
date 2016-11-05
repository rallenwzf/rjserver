/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.user;

import javax.servlet.http.HttpServletRequest;

import cn.game.rjserver.basicutils.lang.MD5;
import cn.game.rjserver.basicutils.lang.RandomUtils;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.exception.DbException;
import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;
import cn.game.rjserver.uc.web.datas.DatasManager;
import cn.game.rjserver.uc.web.entity.CollectedInfo;
import cn.game.rjserver.uc.web.serverlet.handler.MessageUitls;

/**
 * @author rallen
 * 
 */
@Cmd(action = 3, desc = "登录，返回用户信息")
public class Login implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {

		String useraccount = request.getParameter("useraccount");
		String password = request.getParameter("password");

		String imei = request.getParameter("imei");
		String imsi = request.getParameter("imsi");
		long screenwidth = MessageUitls.toLong(request.getParameter("screenwidth"));
		long screenheight = MessageUitls.toLong(request.getParameter("screenheight"));
		long gameversion = MessageUitls.toLong(request.getParameter("gameversion"));
		String systemsdk = request.getParameter("systemsdk");
		String systemversion = request.getParameter("systemversion");
		String channel = request.getParameter("channel");
		String phonemode = request.getParameter("phonemode");
		String userip = request.getParameter("userip");
		CollectedInfo info = new CollectedInfo();
		info.setImei(imei);
		info.setImsi(imsi);
		info.setScreenwidth(screenwidth);
		info.setScreenheight(screenheight);
		info.setGameversion(gameversion);
		info.setSystemsdk(systemsdk);
		info.setSystemversion(systemversion);
		info.setChannel(channel);
		info.setPhonemode(phonemode);
		info.setUserip(userip);
		DatasManager.getDatasManager().addCollectedInfo(useraccount, info);
		TucUserinfo tuserinfo = UcProviderManager.getInstance().getUserProvider().vilidate(useraccount, password);
		if (tuserinfo != null && tuserinfo.getTucUsers() != null) {
			// String succCode = MD5.MD5(RandomUtils.getRandom(20));
			StringBuffer sf = new StringBuffer();
			// 登录成功生成前置服校验码
			DatasManager.getDatasManager().addCode(tuserinfo.getTucUsers().getUseraccount(), RandomUtils.getRandom(10));
			return MessageUitls.getTucUserinfoXml(tuserinfo, sf, false);
		} else {
			throw new Exception("参数错误");
		}
	}
}
