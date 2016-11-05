/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.framework.cache;

/**
 * @author wangzhifeng(rallen)
 */
public interface ServerdataCache {
	public void clear(Object key);

	public void reload();

	public void push(Object key, Object value);

	public Object get(Object key);
}
