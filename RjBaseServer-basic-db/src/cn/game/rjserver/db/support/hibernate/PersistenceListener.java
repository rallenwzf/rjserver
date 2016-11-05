/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.db.support.hibernate;

/**
 * @author Administrator
 */
public interface PersistenceListener {
	public boolean saveOrUpdate(Object orm);

	public boolean select();
}
