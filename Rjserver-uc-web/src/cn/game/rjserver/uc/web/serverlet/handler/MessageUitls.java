/**
 * rallen(wzf)
 */
package cn.game.rjserver.uc.web.serverlet.handler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import cn.game.rjserver.log.api.WriteDatasLog;
import cn.game.rjserver.log.api.WriteDatasLogImpl;
import cn.game.rjserver.uc.entity.TgsmGameupdate;
import cn.game.rjserver.uc.entity.TgsmServerUserroles;
import cn.game.rjserver.uc.entity.TgsmServers;
import cn.game.rjserver.uc.entity.TsdkOrder;
import cn.game.rjserver.uc.entity.TucOthersdkUser;
import cn.game.rjserver.uc.entity.TucUserinfo;

/**
 * @author rallen
 * 
 */
public class MessageUitls {

	public static void getTucOthersdkUserXml(TucOthersdkUser tucOthersdkUser, StringBuffer sf) {
		if (tucOthersdkUser == null)
			return;
		sf.append("<tucOthersdkUser>");
		sf.append("<totherid>" + tucOthersdkUser.getTotherid() + "</totherid>");
		sf.append("<useraccount>" + tucOthersdkUser.getTucUsers().getUseraccount()
				+ "</useraccount>");
		sf.append("<sdkuid>" + tucOthersdkUser.getSdkuid() + "</sdkuid>");
		sf.append("<sdkuname>" + tucOthersdkUser.getSdkuname() + "</sdkuname>");
		sf.append("<sdktype>" + tucOthersdkUser.getSdktype() + "</sdktype>");
		sf.append("<sdksid>" + tucOthersdkUser.getSdksid() + "</sdksid>");
		sf.append("<status>" + tucOthersdkUser.getStatus() + "</status>");
		sf.append("</tucOthersdkUser>");
	}

	public static String getTsdkOrderXml(TsdkOrder tsdkOrder, StringBuffer sf) {
		if (tsdkOrder == null)
			return "";
		sf.append("<tsdkOrder>");
		sf.append("<id>" + tsdkOrder.getId() + "</id>");
		sf.append("<sdkcode>" + tsdkOrder.getSdkcode() + "</sdkcode>");
		sf.append("<ordercode>" + tsdkOrder.getOrdercode() + "</ordercode>");
		sf.append("<transactid>" + tsdkOrder.getTransactid() + "</transactid>");
		sf.append("<fee>" + tsdkOrder.getFee() + "</fee>");
		sf.append("<sign>" + tsdkOrder.getSign() + "</sign>");
		sf.append("<status>" + tsdkOrder.getStatus() + "</status>");
		sf.append("</tsdkOrder>");
		return sf.toString();
	}

	public static String getTucUserinfoXml(TucUserinfo tuserinfo, StringBuffer sf, boolean isNew) {
		if (tuserinfo == null)
			return "";
		sf.append("<tuserinfo>");
		sf.append("<userinfoid>" + tuserinfo.getUserinfoid() + "</userinfoid>");
		sf.append("<uid>" + tuserinfo.getTucUsers().getUid() + "</uid>");
		sf.append("<useraccount>" + tuserinfo.getTucUsers().getUseraccount() + "</useraccount>");
		if (isNew) {
			sf.append("<password>" + tuserinfo.getTucUsers().getUserpwd() + "</password>");
		}
		sf.append("<mobile>" + tuserinfo.getMobile() + "</mobile>");
		sf.append("<email>" + tuserinfo.getEmail() + "</email>");
		sf.append("<uname>" + tuserinfo.getTucUsers().getUname() + "</uname>");
		sf.append("<usercoin>" + tuserinfo.getUsercoin() + "</usercoin>");
		sf.append("<identifycard>" + tuserinfo.getIdentifycard() + "</identifycard>");
		sf.append("<smsverifycode>" + tuserinfo.getSmsverifycode() + "</smsverifycode>");

		sf.append("</tuserinfo>");
		return sf.toString();

	}

	public static String getTgsmServersList(List<TgsmServers> list, StringBuffer sf) {
		Iterator<TgsmServers> it = list.iterator();
		while (it.hasNext()) {
			TgsmServers tgsmServers = it.next();
			if (tgsmServers == null) {
				continue;
			}
			sf.append("<tgsmServers>");
			sf.append("<serverid>" + tgsmServers.getServerid() + "</serverid>");
			sf.append("<servername>" + tgsmServers.getServername() + "</servername>");
			sf.append("<serverhosting>" + tgsmServers.getServerhosting() + "</serverhosting>");
			sf.append("<status>" + tgsmServers.getStatus() + "</status>");
			sf.append("<commendval>" + tgsmServers.getCommendval() + "</commendval>");
			sf.append("<code>" + tgsmServers.getCode() + "</code>");
			sf.append("<sort>" + tgsmServers.getSort() + "</sort>");
			sf.append("</tgsmServers>");
		}
		return sf.toString();

	}

	public static String getDefaultTgsmServers(TgsmServers tgsmServers, StringBuffer sf) {
		sf.append("<tgsmServers>");
		sf.append("<serverid>" + tgsmServers.getServerid() + "</serverid>");
		sf.append("<servername>" + tgsmServers.getServername() + "</servername>");
		sf.append("<serverhosting>" + tgsmServers.getServerhosting() + "</serverhosting>");
		sf.append("<status>" + tgsmServers.getStatus() + "</status>");
		sf.append("<commendval>" + tgsmServers.getCommendval() + "</commendval>");
		sf.append("<code>" + tgsmServers.getCode() + "</code>");
		sf.append("<sort>" + tgsmServers.getSort() + "</sort>");
		sf.append("</tgsmServers>");
		return sf.toString();

	}

	public static String getTgsmServerUserrolesList(List<TgsmServerUserroles> list, StringBuffer sf) {
		Iterator<TgsmServerUserroles> it = list.iterator();
		while (it.hasNext()) {
			TgsmServerUserroles tgsmServers = it.next();
			if (tgsmServers == null) {
				continue;
			}
			sf.append("<tserverroles>");
			sf.append("<serverid>" + tgsmServers.getTgsmServers().getServerid() + "</serverid>");
			sf.append("<uid>" + tgsmServers.getUid() + "</uid>");
			sf.append("<roleid>" + tgsmServers.getUroleid() + "</roleid>");
			sf.append("<rolelevel>" + tgsmServers.getRolelevel() + "</rolelevel>");
			sf.append("<rolenikename>" + tgsmServers.getRolenikename() + "</rolenikename>");
			sf.append("<rolehead>" + tgsmServers.getRolehead() + "</rolehead>");
			sf.append("</tserverroles>");
		}
		return sf.toString();

	}

	public static String getTgsmGameupdateListXml(List<TgsmGameupdate> list, StringBuffer sf) {
		Iterator<TgsmGameupdate> it = list.iterator();
		while (it.hasNext()) {
			TgsmGameupdate tgsmGameupdate = it.next();
			if (tgsmGameupdate == null) {
				continue;
			}
			sf.append("<tgsmGameupdate>");
			sf.append("<resid>" + tgsmGameupdate.getResid() + "</resid>");
			sf.append("<version>" + tgsmGameupdate.getVersion() + "</version>");
			sf.append("<versionname>" + tgsmGameupdate.getVersionname() + "</versionname>");
			sf.append("<gamename>" + tgsmGameupdate.getGamename() + "</gamename>");
			sf.append("<gamecode>" + tgsmGameupdate.getGamecode() + "</gamecode>");
			sf.append("<versionserialcode>" + tgsmGameupdate.getVersionserialcode()
					+ "</versionserialcode>");
			sf.append("<src>" + tgsmGameupdate.getSrc() + "</src>");
			sf.append("<mustupdate>" + tgsmGameupdate.getMustupdate() + "</mustupdate>");
			sf.append("<createdate>" + tgsmGameupdate.getCreatedate() + "</createdate>");
			sf.append("</tgsmGameupdate>");
		}
		return sf.toString();
	}

	public static long toLong(Object obj) {
		if (obj == null || !obj.toString().matches("\\d+")) {
			return 0;
		} else {
			return Long.parseLong(obj.toString());
		}

	}

}
