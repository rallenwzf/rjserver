/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.db.support.jdbc.connsource;

import java.util.HashMap;
import java.util.Map;

import cn.game.rjserver.db.support.jdbc.ConnectionException;
import cn.game.rjserver.db.support.jdbc.UserConnectionSign;

/**
 * @author wangzhifeng(rallen)
 */
public class ConnectionSourceFactory {
	private static Map<String, ConnectionSource> connectionSourceMap = new HashMap<String, ConnectionSource>();
	public static String DEFAULT_KEY = "mainDB";
	private static UserConnectionSign userConnSign;
	private static ConnectionException connectionException;

	public static ConnectionSource getConnectionSource() {
		ConnectionSource connectionSource;
		if (userConnSign != null) {
			String key = userConnSign.getConnectionSourceKey();
			if (key != null) {
				connectionSource = connectionSourceMap.get(key);
				if (connectionSource != null) {
					return connectionSource;
				}
			}
		}
		connectionSource = connectionSourceMap.get(DEFAULT_KEY);
		if (connectionSource == null) {
			// connectionSource = new JDCConnect();
			// connectionSourceMap.put(DEFAULT_KEY, connectionSource);
			throw new RuntimeException("数据没设置连接池啊，如果想用单连接操作，setConnectionSource(new JDCConnect())");
		}
		return connectionSource;
	}

	public static ConnectionSource getConnectionSource(String key) {
		if (key == null || key.trim().equals("")) {
			return getConnectionSource();
		}
		return connectionSourceMap.get(key);
	}

	public static void setConnectionSource(ConnectionSource connectionSource) {
		connectionSourceMap.put(DEFAULT_KEY, connectionSource);
	}

	public static void addConnectionSource(String key, ConnectionSource connectionSource) {
		connectionSourceMap.put(key, connectionSource);
	}

	public static ConnectionException getConnectionException() {
		return connectionException;
	}

	public static void setConnectionException(ConnectionException connectionException) {
		ConnectionSourceFactory.connectionException = connectionException;
	}

	public static UserConnectionSign getUserConnSign() {
		return userConnSign;
	}

	public static void setUserConnSign(UserConnectionSign userConnSign) {
		ConnectionSourceFactory.userConnSign = userConnSign;
	}

}
