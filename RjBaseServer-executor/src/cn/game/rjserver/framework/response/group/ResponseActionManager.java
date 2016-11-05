/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.response.group;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import cn.game.rjserver.framework.protocol.Agreement;
import cn.game.rjserver.framework.response.group.ResponseActionAdapter;

/**
 * 客户端请求响应 管理器
 * 
 * @author Administrator
 */
public class ResponseActionManager {
	private Map<String, ResponseActionAdapter> actions;

	private static ResponseActionManager actionManager;

	public static ResponseActionManager getInstance() {
		if (actionManager == null) {
			actionManager = new ResponseActionManager();
		}
		return actionManager;
	}

	private ResponseActionManager() {
		actions = new HashMap<String, ResponseActionAdapter>();
	}

	public synchronized void addAction(String key, ResponseActionAdapter action) {
		if (!actions.containsKey(key)) {
			actions.put(key, action);
		}
	}

	/**
	 * @param agreement
	 * @return
	 */
	public ResponseActionAdapter findAction(Agreement agreement) {
		for (ResponseActionAdapter action : actions.values()) {
			if (action.accept(agreement)) {
				return action;
			} else {
				continue;
			}
		}
		return null;
	}

	public Map<String, ResponseActionAdapter> getActions() {
		return actions;
	}
}
