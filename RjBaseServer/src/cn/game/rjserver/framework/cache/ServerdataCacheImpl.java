/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangzhifeng(rallen)
 */
public class ServerdataCacheImpl implements ServerdataCache {
	protected Map<Object, Object> dataMaps = new HashMap<Object, Object>();

	@Override
	public void clear(Object key) {
		// TODO Auto-generated method stub
		dataMaps.remove(key);
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void push(Object key, Object value) {
		// TODO Auto-generated method stub
		dataMaps.put(key, value);
	}

	@Override
	public Object get(Object key) {
		// TODO Auto-generated method stub
		return dataMaps.get(key);
	}

}
