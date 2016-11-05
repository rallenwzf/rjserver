/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.db.support.jdbc.connsource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author wangzhifeng(rallen)
 */
public interface ConnectionSource {
	/**
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException;

}
