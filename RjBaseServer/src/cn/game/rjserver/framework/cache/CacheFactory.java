/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangzhifeng(rallen)
 */
public class CacheFactory {
	public static String DAFAULT_DATACACHE_KEY = "defaultkey";
	private static CacheFactory factory;
	private Map<String, ServerdataCache> dataCaches;

	public static CacheFactory getInstance() {
		if (factory == null) {
			factory = new CacheFactory();
		}
		return factory;
	}

	private CacheFactory() {
		dataCaches = new HashMap<String, ServerdataCache>();
		dataCaches.put(DAFAULT_DATACACHE_KEY, new ServerdataCacheImpl());
	}

	public Map<String, ServerdataCache> getDataCaches() {
		return dataCaches;
	}

	public ServerdataCache getServerdataCache(String key) {
		return dataCaches.get(key);
	}

	public ServerdataCache getServerdataCache() {
		return dataCaches.get(DAFAULT_DATACACHE_KEY);
	}

	public void addServerdataCache(String key, ServerdataCache dataCache) {
		dataCaches.put(key, dataCache);
	}
}
