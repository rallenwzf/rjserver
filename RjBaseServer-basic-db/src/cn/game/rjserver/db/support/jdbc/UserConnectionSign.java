/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.db.support.jdbc;

/**
 * @author wangzhifeng(rallen)
 *	为了方便同一个web 跨多个数据库来连接，可以在session中存储，然后实现此接口做调用
 */
public interface UserConnectionSign {
	/**
	 * 获得当前用户连接的c3p0连接池key
	 * @return
	 */
	public String getConnectionSourceKey();
}
