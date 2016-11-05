package cn.game.rjserver.uc.api.gsm;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.game.rjserver.uc.api.entity.ServerRolesInfo;
import cn.game.rjserver.uc.api.entity.ServersInfo;
import cn.game.rjserver.uc.api.entity.UpgradeInfo;
import cn.game.rjserver.uc.sdk.ApiResult;
import cn.game.rjserver.uc.sdk.ApiStatus;
import cn.game.rjserver.uc.sdk.Config;
import cn.game.rjserver.uc.sdk.uitls.ResponseParser;
import cn.game.rjserver.uc.sdk.uitls.SendRequest;

public class ServerApiImpl implements ServerApi {

	@Override
	public ApiResult<List<ServersInfo>> getAllOpedServerList(String channel, boolean needDefault) {
		// TODO Auto-generated method stub
		try {
			List<ServersInfo> list = null;
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=101");
			sBuilder.append("&channel=" + channel);
			sBuilder.append("&needDefault=" + (needDefault ? 0 : 1));
			Document document = ResponseParser.getDocument(SendRequest.sendPost(
					Config.ServerApi_Url, sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				list = new ArrayList<ServersInfo>();
				List<Element> es = (List<Element>) root.element("message").elements("tgsmServers");
				for (int i = 0; i < es.size(); i++) {
					Element e = es.get(i);
					ServersInfo server = new ServersInfo();
					server.setCode(toLong(e.element("code").getTextTrim()));
					server.setCommend(toInt(e.element("commendval").getTextTrim()));
					server.setServerHosting(e.element("serverhosting").getTextTrim());
					server.setServerId(toInt(e.element("serverid").getTextTrim()));
					server.setServerName(e.element("servername").getTextTrim());
					server.setSort(toInt(e.element("sort").getTextTrim()));
					server.setStatus(toInt(e.element("status").getTextTrim()));

					list.add(server);
				}
				return new ApiResult<List<ServersInfo>>(list, ApiStatus.OK);
			} else {
				return new ApiResult<List<ServersInfo>>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<List<ServersInfo>>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<List<UpgradeInfo>> getAllUpgradeList(int maxVersion) {
		try {
			List<UpgradeInfo> list = null;
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=102");
			sBuilder.append("&maxVersion=" + maxVersion);
			Document document = ResponseParser.getDocument(SendRequest.sendPost(
					Config.ServerApi_Url, sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				list = new ArrayList<UpgradeInfo>();
				List<Element> es = (List<Element>) root.element("message").elements(
						"tgsmGameupdate");
				for (int i = 0; i < es.size(); i++) {
					Element e = es.get(i);
					UpgradeInfo info = new UpgradeInfo();
					info.setGameCode(e.element("gamecode").getTextTrim());
					info.setGameName(e.element("gamename").getTextTrim());
					info.setId(toLong(e.element("resid").getTextTrim()));
					info.setMustUpdate(toInt(e.element("mustupdate").getTextTrim()));
					info.setSrc(e.element("src").getTextTrim());
					info.setVersion(toInt(e.element("version").getTextTrim()));
					info.setVersionName(e.element("versionname").getTextTrim());
					info.setVersionSerialcode(e.element("versionserialcode").getTextTrim());

					list.add(info);
				}
				return new ApiResult<List<UpgradeInfo>>(list, ApiStatus.OK);
			} else {
				return new ApiResult<List<UpgradeInfo>>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<List<UpgradeInfo>>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<List<ServerRolesInfo>> getAllServerRolesList(long uid) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=103");
			sBuilder.append("&uid=" + uid);
			Document document = ResponseParser.getDocument(SendRequest.sendPost(
					Config.ServerApi_Url, sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				List<ServerRolesInfo> list = new ArrayList<ServerRolesInfo>();
				List<Element> es = (List<Element>) root.element("message").elements("tserverroles");
				for (int i = 0; i < es.size(); i++) {
					Element e = es.get(i);
					ServerRolesInfo server = new ServerRolesInfo();
					server.setRoleId(toLong(e.element("roleid").getTextTrim()));
					server.setRoleLevel(toInt(e.element("rolelevel").getTextTrim()));
					server.setRoleNikename(e.element("rolenikename").getTextTrim());
					server.setRoleHead(e.element("rolehead").getTextTrim());
					server.setUid(toInt(e.element("uid").getTextTrim()));
					server.setServerId(toInt(e.element("serverid").getTextTrim()));

					list.add(server);
				}

				return new ApiResult<List<ServerRolesInfo>>(list, ApiStatus.OK);
			} else {
				return new ApiResult<List<ServerRolesInfo>>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<List<ServerRolesInfo>>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<Long> getUserLastServerid(String userAccount) {
		try {
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=104");
			sBuilder.append("&userAccount=" + userAccount);
			Document document = ResponseParser.getDocument(SendRequest.sendPost(
					Config.ServerApi_Url, sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				Element message = root.element("message");
				long id = toLong(message.element("userLastServerid").getTextTrim());
				return new ApiResult<Long>(id, ApiStatus.OK);
			} else {
				return new ApiResult<Long>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<Long>(0l, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<Boolean> sendRole(long serverid, ServerRolesInfo info) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=105");
			sBuilder.append("&uid=" + info.getUid());
			sBuilder.append("&serverid=" + serverid);
			sBuilder.append("&roleid=" + info.getRoleId());
			sBuilder.append("&level=" + info.getRoleLevel());
			sBuilder.append("&rolenikename=" + info.getRoleNikename());
			sBuilder.append("&head=" + info.getRoleHead());
			Document document = ResponseParser.getDocument(SendRequest.sendPost(
					Config.ServerApi_Url, sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				Element message = root.element("message");
				return new ApiResult<Boolean>(true, ApiStatus.OK);
			} else {
				return new ApiResult<Boolean>(false, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<Boolean>(false, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<ServersInfo> getDefultServer(String channel, String userAccount) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=110");
			sBuilder.append("&channel=" + channel);
			sBuilder.append("&userAccount=" + userAccount);
			Document document = ResponseParser.getDocument(SendRequest.sendPost(
					Config.ServerApi_Url, sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				Element e = (Element) root.element("message").element("tgsmServers");
				ServersInfo server = new ServersInfo();
				server.setCode(toLong(e.element("code").getTextTrim()));
				server.setCommend(toInt(e.element("commendval").getTextTrim()));
				server.setServerHosting(e.element("serverhosting").getTextTrim());
				server.setServerId(toInt(e.element("serverid").getTextTrim()));
				server.setServerName(e.element("servername").getTextTrim());
				server.setSort(toInt(e.element("sort").getTextTrim()));
				server.setStatus(toInt(e.element("status").getTextTrim()));
				return new ApiResult<ServersInfo>(server, ApiStatus.OK);
			} else {
				return new ApiResult<ServersInfo>(null, ApiStatus.FAILED);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ApiResult<ServersInfo>(null, ApiStatus.EXCEPTION.setException(e));
		}
	}

	@Override
	public ApiResult<Boolean> inoutServer(long serverid, long uid, boolean isinto) {
		// TODO Auto-generated method stub
		try {
			StringBuilder sBuilder = new StringBuilder("pwd=" + Config.UC_Api_Pwd);
			sBuilder.append("&action=111");
			sBuilder.append("&uid=" + uid);
			sBuilder.append("&serverid=" + serverid);
			sBuilder.append("&isinto=" + (isinto ? 1 : 0));
			Document document = ResponseParser.getDocument(SendRequest.sendPost(
					Config.ServerApi_Url, sBuilder.toString()));
			Element root = document.getRootElement();
			String resultCode = root.element("resultCode").getTextTrim();
			if (resultCode.equals("0")) {
				//
				return new ApiResult<Boolean>(true, ApiStatus.OK);
			} else {
				return new ApiResult<Boolean>(false, ApiStatus.FAILED);
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

	protected int toInt(Object obj) {
		if (obj == null || !obj.toString().matches("\\d+")) {
			return 0;
		} else {
			return Integer.parseInt(obj.toString());
		}

	}

}
