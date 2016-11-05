/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.log.MyLogger;


/**
 * 子协议的推送寄存处
 * 
 * @author wangzhifeng(rallen)
 */
public class PushSubServerAg {
	public static String SESSION_AG_KEY = "SESSION_SERVERAG_KEY";
	static MyLogger logger = MyLogger.getLog("PushSubServerAg");
	static Map<Long, List<Agreement>> agStorageMap = new HashMap<Long, List<Agreement>>();

	public static void pushServerAg(long id, Agreement ag) {
		pushServerAg(id, ag, false);
	}

	/**
	 * 存放需推送的子协议
	 * 
	 * @param id
	 * @param ag
	 */
	public static void pushServerAg(long id, Agreement ag, boolean needOnline) {
		try {
			if (ag == null) {
				return;
			}
			Object o = SessionManager.getHttpSession(id);
			if (o == null) {
				logger.d("pushServerAg()_agStorageMap__id=" + id + " |ag=" + ag.toString());
				// session还未登录缓存，将推送协议做寄存
				if (!needOnline) {
					if (!agStorageMap.containsKey(id)) {
						List<Agreement> list = new ArrayList<Agreement>();
						list.add(ag);
						agStorageMap.put(id, list);
					} else {
						agStorageMap.get(id).add(ag);
					}
				}
				return;
			}
			logger.d("pushServerAg()__id=" + id + " |ag=" + ag.toString());
			Object obj = SessionManager.getAttr(id, SESSION_AG_KEY);
			if (obj == null) {
				List<Agreement> list = new ArrayList<Agreement>();
				list.add(ag);
				SessionManager.addAttr(id, SESSION_AG_KEY, list);
			} else {
				List<Agreement> list = (List<Agreement>) obj;
				list.add(ag);
				SessionManager.addAttr(id, SESSION_AG_KEY, list);
			}
		} catch (Exception e) {
		}
	}

	public static void writeServerAg(long id, Agreement parentAg) {
		try {
			Object o = SessionManager.getHttpSession(id);
			if (o != null) {
				// logger.d("writeServerStorageAg()_ttt_id=" + id);
				//
				writeServerStorageAg(id, parentAg);
			}
			Object obj = SessionManager.getAttr(id, SESSION_AG_KEY);
			if (obj != null) {
				// logger.d("writeServerAg()__id=" + id);
				//
				writeServerAgAlong(id, parentAg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送寄存的推送协议
	 * 
	 * @param id
	 * @param parentAg
	 */
	private synchronized static void writeServerStorageAg(long id, Agreement parentAg) {
		try {
			// logger.d("writeServerStorageAg()__id=" + id);
			List<Agreement> list = agStorageMap.get(id);
			if (list != null) {
				// logger.d("writeServerStorageAg()__id=" + id);
				for (int i = 0; i < list.size(); i++) {
					Agreement ag = list.get(i);
					if (ag != null) {
						parentAg.addAgreement(ag);
						logger.d("writeServerStorageAg()__id=" + id + " |ag=" + ag.toString());
					}
				}
			}
			agStorageMap.remove(id);
		} catch (Exception e) {
		}
	}

	/**
	 * 发送绑定子协议推送
	 * 
	 * @param id
	 * @param parentAg
	 */
	private synchronized static void writeServerAgAlong(long id, Agreement parentAg) {
		try {
			Object obj = SessionManager.getAttr(id, SESSION_AG_KEY);
			if (obj != null) {
				// logger.d("writeServerAgAlong()__id=" + id);
				List<Agreement> list = (List<Agreement>) obj;
				for (int i = 0; i < list.size(); i++) {
					Agreement ag = list.get(i);
					if (ag != null) {
						parentAg.addAgreement(ag);
						logger.d("writeServerAgAlong()__id=" + id + " |ag=" + ag.toString());
					}
				}
				SessionManager.removeSessionAttr(id, SESSION_AG_KEY);
			}
		} catch (Exception e) {
		}
	}

	public static void clear(long id) {
		agStorageMap.remove(id);
	}
}
