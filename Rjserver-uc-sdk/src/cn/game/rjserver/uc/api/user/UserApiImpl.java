/**
 * rallen
 */
package cn.game.rjserver.uc.api.user;

import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.game.rjserver.uc.api.entity.CollectedInfo;
import cn.game.rjserver.uc.api.entity.ServersInfo;
import cn.game.rjserver.uc.api.entity.TsdkOrder;
import cn.game.rjserver.uc.api.entity.UserInfo;
import cn.game.rjserver.uc.sdk.ApiResult;
import cn.game.rjserver.uc.sdk.ApiStatus;
import cn.game.rjserver.uc.sdk.Config;
import cn.game.rjserver.uc.sdk.uitls.ResponseParser;
import cn.game.rjserver.uc.sdk.uitls.SendRequest;

/**
 * @author wangzhifeng(rallen)
 */
public class UserApiImpl implements UserApi {

	final boolean debug = true;

	@Override
	public ApiResult<UserInfo> creatNewUser(CollectedInfo info) {
		// TODO Auto-generated method stub
		try {

			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=1");
			sBuilder.append("&imei=" + info.getImei());
			sBuilder.append("&imsi=" + info.getImsi());
			sBuilder.append("&screenwidth=" + info.getScreenwidth());
			sBuilder.append("&screenheight=" + info.getScreenheight());
			sBuilder.append("&gameversion=" + info.getGameversion());
			sBuilder.append("&systemsdk=" + info.getSystemsdk());
			sBuilder.append("&systemversion=" + info.getSystemversion());
			sBuilder.append("&channel=" + info.getChannel());
			sBuilder.append("&phonemode=" + info.getPhonemode());
			sBuilder.append("&userip=" + info.getUserip());
			Document document = ResponseParser.getDocument(SendRequest.sendPost(Config.UserApi_Url,
					sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				UserInfo userInfo = new UserInfo();
				Element e = root.element("message").element("tuserinfo");
				userInfo.setUid(toLong(e.element("uid").getTextTrim()));
				userInfo.setUseraccount(e.element("useraccount").getTextTrim());
				userInfo.setEmail(e.element("email").getTextTrim());
				userInfo.setMobile(e.element("mobile").getTextTrim());
				userInfo.setNew(true);
				userInfo.setPassword(e.element("password").getTextTrim());
				userInfo.setPetname(e.element("uname").getTextTrim());
				userInfo.setUsercoin(toLong(e.element("usercoin").getTextTrim()));
				return new ApiResult<UserInfo>(userInfo, ApiStatus.OK);
			} else {
				return new ApiResult<UserInfo>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<UserInfo>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<UserInfo> reg(String userAccount, String password, CollectedInfo info) {
		// TODO Auto-generated method stub
		try {

			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=2");
			sBuilder.append("&useraccount=" + userAccount);
			sBuilder.append("&password=" + password);
			sBuilder.append("&imei=" + info.getImei());
			sBuilder.append("&imsi=" + info.getImsi());
			sBuilder.append("&screenwidth=" + info.getScreenwidth());
			sBuilder.append("&screenheight=" + info.getScreenheight());
			sBuilder.append("&gameversion=" + info.getGameversion());
			sBuilder.append("&systemsdk=" + info.getSystemsdk());
			sBuilder.append("&systemversion=" + info.getSystemversion());
			sBuilder.append("&channel=" + info.getChannel());
			sBuilder.append("&phonemode=" + info.getPhonemode());
			sBuilder.append("&userip=" + info.getUserip());
			Document document = ResponseParser.getDocument(SendRequest.sendPost(Config.UserApi_Url,
					sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				UserInfo userInfo = new UserInfo();
				Element e = root.element("message").element("tuserinfo");
				userInfo.setUid(toLong(e.element("uid").getTextTrim()));
				userInfo.setUseraccount(e.element("useraccount").getTextTrim());
				userInfo.setEmail(e.element("email").getTextTrim());
				userInfo.setMobile(e.element("mobile").getTextTrim());
				userInfo.setNew(true);
				userInfo.setPassword(e.element("password").getTextTrim());
				userInfo.setPetname(e.element("uname").getTextTrim());
				userInfo.setUsercoin(toLong(e.element("usercoin").getTextTrim()));
				return new ApiResult<UserInfo>(userInfo, ApiStatus.OK);
			} else {
				return new ApiResult<UserInfo>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<UserInfo>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<UserInfo> login(String useraccount, String password, CollectedInfo info) {
		try {
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=3");
			sBuilder.append("&useraccount=" + useraccount);
			sBuilder.append("&password=" + password);
			sBuilder.append("&imei=" + info.getImei());
			sBuilder.append("&imsi=" + info.getImsi());
			sBuilder.append("&screenwidth=" + info.getScreenwidth());
			sBuilder.append("&screenheight=" + info.getScreenheight());
			sBuilder.append("&gameversion=" + info.getGameversion());
			sBuilder.append("&systemsdk=" + info.getSystemsdk());
			sBuilder.append("&systemversion=" + info.getSystemversion());
			sBuilder.append("&channel=" + info.getChannel());
			sBuilder.append("&phonemode=" + info.getPhonemode());
			sBuilder.append("&userip=" + info.getUserip());

			Document document = ResponseParser.getDocument(SendRequest.sendPost(Config.UserApi_Url,
					sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				UserInfo userInfo = new UserInfo();
				Element e = root.element("message").element("tuserinfo");
				userInfo.setUid(toLong(e.element("uid").getTextTrim()));
				userInfo.setUseraccount(e.element("useraccount").getTextTrim());
				userInfo.setEmail(e.element("email").getTextTrim());
				userInfo.setMobile(e.element("mobile").getTextTrim());
				userInfo.setNew(true);
				// userInfo.setPassword(e.element("password").getTextTrim());
				userInfo.setPetname(e.element("uname").getTextTrim());
				userInfo.setUsercoin(toLong(e.element("usercoin").getTextTrim()));
				return new ApiResult<UserInfo>(userInfo, ApiStatus.OK);
			} else {
				return new ApiResult<UserInfo>(null, ApiStatus.FAILED);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<UserInfo>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<String> getLastSuccCode(String userAccount) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=4");
			sBuilder.append("&userAccount=" + userAccount);
			System.out.println(sBuilder.toString());
			Document document = ResponseParser.getDocument(SendRequest.sendPost(Config.UserApi_Url,
					sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				Element message = root.element("message");
				String str = message.element("lastSuccCode").getTextTrim();
				return new ApiResult<String>(str, ApiStatus.OK);
			} else {
				return new ApiResult<String>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<String>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<UserInfo> getUser(String userAccount) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=5");
			sBuilder.append("&userAccount=" + userAccount);
			Document document = ResponseParser.getDocument(SendRequest.sendPost(Config.UserApi_Url,
					sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				UserInfo userInfo = new UserInfo();
				Element e = root.element("message").element("tuserinfo");
				userInfo.setUid(toLong(e.element("uid").getTextTrim()));
				userInfo.setUseraccount(e.element("useraccount").getTextTrim());
				userInfo.setEmail(e.element("email").getTextTrim());
				userInfo.setMobile(e.element("mobile").getTextTrim());
				userInfo.setNew(true);
				userInfo.setPassword(e.element("password").getTextTrim());
				userInfo.setPetname(e.element("uname").getTextTrim());
				userInfo.setUsercoin(toLong(e.element("usercoin").getTextTrim()));
				return new ApiResult<UserInfo>(userInfo, ApiStatus.OK);
			} else {
				return new ApiResult<UserInfo>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<UserInfo>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<Boolean> isPresence(String userAccount) {
		// TODO Auto-generated method stub
		try {
			boolean b = false;
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=6");
			sBuilder.append("&userAccount=" + userAccount);
			Document document = ResponseParser.getDocument(SendRequest.sendPost(Config.UserApi_Url,
					sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				Element message = root.element("message");
				b = Boolean.parseBoolean(message.element("isHasUser").getTextTrim());
				return new ApiResult<Boolean>(b, ApiStatus.OK);
			} else {
				return new ApiResult<Boolean>(b, ApiStatus.FAILED);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<Boolean>(false, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<Boolean> logout(long uid) {
		// TODO Auto-generated method stub
		try {
			boolean b = false;
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=7");
			sBuilder.append("&uid=" + uid);
			Document document = ResponseParser.getDocument(SendRequest.sendPost(Config.UserApi_Url,
					sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				Element message = root.element("message");
				b = Boolean.parseBoolean(message.element("addLogoutLog").getTextTrim());
				return new ApiResult<Boolean>(b, ApiStatus.OK);
			} else {
				return new ApiResult<Boolean>(b, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<Boolean>(false, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<Boolean> updateEmail(long uid, String email) {
		try {
			boolean b = false;
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=8");
			sBuilder.append("&uid=" + uid);
			sBuilder.append("&email=" + email);
			Document document = ResponseParser.getDocument(SendRequest.sendPost(Config.UserApi_Url,
					sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				Element message = root.element("message");
				b = Boolean.parseBoolean(message.element("updateemail").getTextTrim());
				return new ApiResult<Boolean>(b, ApiStatus.OK);
			} else {
				return new ApiResult<Boolean>(b, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<Boolean>(false, ApiStatus.EXCEPTION.setException(e));
		}
	}

	protected long toLong(Object obj) {
		if (obj == null || !obj.toString().matches("\\d+")) {
			return 0;
		} else {
			return Long.parseLong(obj.toString());
		}

	}

}
