/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import cn.game.rjserver.framework.executor.GameIoSession;

/**
 * @doc 存储session及用户ID
 * @author Administrator
 */
public class UserManager {

	public static final String SESSION_KEY_USERID = "user_acccount";
	public static final String SESSION_KEY_UTID = "uid";

	public static final Map<String, GameIoSession> sessions = Collections
			.synchronizedMap(new HashMap<String, GameIoSession>());

	public static final Set<String> users = Collections.synchronizedSet(new HashSet<String>());

	private static UserManager userManager;

	public static synchronized UserManager getInstance() {
		if (userManager == null) {
			userManager = new UserManager();
			System.out.println("getInstance userManager new ");
		}
		return userManager;
	}

	public static void setUserManager(UserManager userManager) {
		UserManager.userManager = userManager;
	}

	public void addUser(GameIoSession session, String utid, String userId) {
		if (utid != null && !utid.equals("")) {
			if (sessions.containsKey(utid)) {
				// 可以不做链接清除，由执行器来驱动正常退出
				if (session.getId() != sessions.get(utid).getId()) {
					removeUser(sessions.get(utid));
				}
			}
			session.setAttribute(SESSION_KEY_UTID, utid);
			session.setAttribute(SESSION_KEY_USERID, userId);
			session.setAttribute("loginDate", new Date());
			sessions.put(utid, session);
			// MdcInjectionFilter.setProperty(session, SESSION_KEY_UTID, utid);
			// Allow all users
			// System.out.println(utid + "---------" +
			// session.getAttribute("utid"));
			users.add(utid);
		}
	}

	public void replaceSession(GameIoSession session, String utid, String userId) {
		sessions.put(utid, session);
		users.add(utid);
	}

	public GameIoSession getUserSession(String sessionKey) {
		return sessions.get(sessionKey);
	}

	public void removeUser(GameIoSession session) {
		removeUser(session, true);
	}

	public Map<String, GameIoSession> getSession() {
		return sessions;
	}

	/**
	 * @param session
	 * @param immediately
	 *            是否立即关闭连接
	 */
	public void removeUser(GameIoSession session, boolean immediately) {

		if (session != null) {
			String uid = null;
			if (session.getAttribute(SESSION_KEY_UTID) != null)
				uid = session.getAttribute(SESSION_KEY_UTID).toString();
			GameIoSession s = null;
			if (uid != null) {
				if (sessions.containsKey(uid)) {
					s = sessions.get(uid);
				}
				users.remove(uid);
				sessions.remove(uid);
			}
			// 防止同一连接的产生,
			if (s == null || (s != null && session.getId() != s.getId())) {
				try {
					session.close(immediately);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void removeUser(String uid, boolean isCloseSession) {
		if (uid != null) {
			users.remove(uid);
			GameIoSession session = sessions.get(uid);
			sessions.remove(uid);
			if (isCloseSession) {
				try {
					session.close(true);
				} catch (Exception e) {
				}
			}
		}
	}

	public void addAttribute(String uid, String name, String value) {
		GameIoSession session = getUserSession(uid);
		if (session != null) {
			if (session.containsAttribute(name)) {
				session.removeAttribute(name);
			}
			session.setAttribute(name, value);
		}
	}

	public String getAttribute(String uid, String name) {
		GameIoSession session = getUserSession(uid);
		if (session != null) {
			Object obj = session.getAttribute(name);
			if (obj != null) {
				return obj.toString();
			}
		}
		return null;
	}

	public int getUserCount() {
		return users.size();
	}

	public static Set<String> getUsers() {
		return users;
	}

}
