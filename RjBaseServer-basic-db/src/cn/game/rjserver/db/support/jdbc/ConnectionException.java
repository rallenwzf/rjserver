/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.db.support.jdbc;

/**
 * @author wangzhifeng(rallen)
 */
public interface ConnectionException {
	public void dbConnectError(String dbKey, Exception e);

	public void dbExcuteError(String dbKey, Exception e);
}
