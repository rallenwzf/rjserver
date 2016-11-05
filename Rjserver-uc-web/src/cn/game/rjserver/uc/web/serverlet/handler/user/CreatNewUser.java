/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.omg.CORBA.PUBLIC_MEMBER;

import cn.game.rjserver.basicutils.lang.RandomUtils;

import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.entity.TucUsers;

import cn.game.rjserver.uc.provider.UcProviderManager;
import cn.game.rjserver.uc.utils.UserIdFormat;
import cn.game.rjserver.uc.web.actioncmd.ActionCommand;
import cn.game.rjserver.uc.web.actioncmd.Cmd;
import cn.game.rjserver.uc.web.datas.DatasManager;
import cn.game.rjserver.uc.web.entity.CollectedInfo;
import cn.game.rjserver.uc.web.entity.UserInfo;
import cn.game.rjserver.uc.web.serverlet.handler.MessageUitls;

/**
 * @author rallen
 * 
 */
@Cmd(action = 1, desc = "自动创建用户(自动生成账号密码)，返回用戶信息")
public class CreatNewUser implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) throws Exception {

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

		TucUsers defaultUser = new TucUsers();
		defaultUser.setCreatedate(new Date());
		TucUserinfo defaultUserinfo = new TucUserinfo();
		defaultUserinfo.setUsercoin((long) 0);

		defaultUserinfo.setImei(imei);
		defaultUserinfo.setImsi(imsi);
		defaultUserinfo.setScreenwidth(screenwidth);
		defaultUserinfo.setScreenheight(screenheight);
		defaultUserinfo.setGameversion(gameversion);
		defaultUserinfo.setSystemsdk(systemsdk);
		defaultUserinfo.setSystemversion(systemversion);
		defaultUserinfo.setChannel(channel);
		defaultUserinfo.setPhonemode(phonemode);
		defaultUserinfo.setUserip(userip);
		
		//
		StringBuffer sf = new StringBuffer();
		TucUserinfo tuserinfo = UcProviderManager.getInstance().getUserProvider().createUser(defaultUser, defaultUserinfo, true);
		if (tuserinfo != null) {
			// UserInfo uinfo = this.plfUserInfo(tuserinfo);
			// uinfo.setPassword(tuserinfo.getTucUsers().getUserpwd());
			// uinfo.setNew(true);
			//
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
			DatasManager.getDatasManager().addCollectedInfo(tuserinfo.getTucUsers().getUseraccount(), info);
			// 登录成功生成前置服校验码
			DatasManager.getDatasManager().addCode(tuserinfo.getTucUsers().getUseraccount(), RandomUtils.getRandom(10));

			return MessageUitls.getTucUserinfoXml(tuserinfo, sf, true);
		} else {
			throw new Exception("参数错误");
		}

	}
}
