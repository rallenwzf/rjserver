/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.http;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import cn.game.rjserver.framework.executor.GameIoSession;
import cn.game.rjserver.log.MyLogger;


/**
 * @author wangzhifeng(rallen)
 */
public class SessionManager {
	static MyLogger logger = MyLogger.getLog("SessionManager_Big_error");
	public static Map<Long, HttpSession> roleSessionMap = new HashMap<Long, HttpSession>();

	public static HttpSession getHttpSession(long uRoleId) {
		return roleSessionMap.get(uRoleId);
	}

	public static void addAttr(long uRoleId, String name, Object value) {
		HttpSession session = getHttpSession(uRoleId);
		if (session != null) {
			session.setAttribute(name, value);
		} else {
			logger.e("HttpSession  is null .......");
		}
	}

	public static void removeSessionAttr(long uRoleId, String key) {
		HttpSession httpSession = getHttpSession(uRoleId);
		if (httpSession != null) {
			httpSession.removeAttribute(key);
		}
	}

	public static void addAttr(GameIoSession ioSession, String name, String value) {
		HttpSession session = getHttpSession(ioSession);
		if (session != null) {
			session.setAttribute(name, value);
		} else {
			logger.e("HttpSession  is null .......");
		}
	}

	public static void removeAttr(GameIoSession ioSession, String name) {
		HttpSession session = getHttpSession(ioSession);
		if (session != null) {
			session.removeAttribute(name);
		} else {
			logger.e("HttpSession  is null .......");
		}
	}

	public static void addAttr(GameIoSession ioSession, String name, Object value) {
		HttpSession session = getHttpSession(ioSession);
		if (session != null) {
			session.setAttribute(name, value);
		} else {
			logger.e("HttpSession  is null .......");
		}
	}

	public static String getAttr(GameIoSession ioSession, String name) {
		HttpSession session = getHttpSession(ioSession);
		if (session != null) {
			if (session.getAttribute(name) != null) {
				return session.getAttribute(name).toString();
			}
			return null;
		} else {
			logger.e("HttpSession  is null .......");
		}
		return null;
	}

	public static Object getAttr(GameIoSession ioSession, String name, boolean b) {
		HttpSession session = getHttpSession(ioSession);
		if (session != null) {
			if (session.getAttribute(name) != null) {
				return session.getAttribute(name);
			}
			return null;
		} else {
			logger.e("HttpSession  is null .......");
		}
		return null;
	}

	public static Object getAttr(long uRoleId, String name) {
		HttpSession session = getHttpSession(uRoleId);
		if (session != null) {
			if (session.getAttribute(name) != null) {
				return session.getAttribute(name);
			}
			return null;
		}
		return null;
	}

	public static HttpSession getHttpSession(GameIoSession ioSession) {
		if (ioSession instanceof HttpIoSession) {
			HttpIoSession dssion = (HttpIoSession) ioSession;
			return dssion.getRequest().getSession();
		} else {
			logger.e("DragonIoSession.getRequest().getSession()  is error .......");
		}
		return null;
	}

}
