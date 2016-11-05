/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.cache;

import java.util.Vector;

/**
 * @author Administrator
 * 
 */
public class CacheChangerHandler {
	public Vector<CacheChangeAdviceListener> listeners = new Vector<CacheChangeAdviceListener>();
	private static CacheChangerHandler handler;

	public static CacheChangerHandler getInstace() {
		if (handler == null) {
			handler = new CacheChangerHandler();
		}
		return handler;
	}

	public void addListener(CacheChangeAdviceListener listener) {
		listeners.add(listener);
	}

	public void removeListener(CacheChangeAdviceListener listener) {
		listeners.remove(listener);
	}

	public void advice() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).cacheChange(0);
		}
	}

	public void advice(int type) {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).cacheChange(type);
		}
	}
}
