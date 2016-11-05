/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.db.support.jdbc.connsource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import cn.game.rjserver.db.support.jdbc.connsource.ConnectionSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * @author wangzhifeng(rallen)
 */
public class C3p0ConnectionSourceImpl implements ConnectionSource {
	private static ComboPooledDataSource dataSource;
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private String configPath;

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub

		Connection conn = (Connection) threadLocal.get();
		if (conn == null || conn.isClosed()) {
			if (dataSource == null) {
				loadDataSource(configPath);
			}
			conn = (dataSource != null) ? dataSource.getConnection() : null;
			threadLocal.set(conn);
		}
		return conn;
	}

	/**
	 * @param configPath
	 */
	public void loadDataSource(String configPath) {
		try {
			dataSource = new ComboPooledDataSource();
			Properties properties = new Properties();
			File file = new File(configPath);
			FileInputStream is = new FileInputStream(file);
			properties.load(is);
			dataSource.setProperties(properties);
			dataSource.setUser(properties.getProperty("user"));
			dataSource.setPassword(properties.getProperty("password"));
			dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
			dataSource.setDriverClass(properties.getProperty("driverClass"));
			
			dataSource.setInitialPoolSize(Integer.parseInt(properties.getProperty("initialPoolSize")));
			dataSource.setMinPoolSize(Integer.parseInt(properties.getProperty("minPoolSize")));
			dataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty("maxPoolSize")));
			dataSource.setMaxIdleTime(Integer.parseInt(properties.getProperty("maxIdleTime")));
			dataSource.setIdleConnectionTestPeriod(Integer.parseInt(properties
					.getProperty("idleConnectionTestPeriod")));
			dataSource.setMaxConnectionAge(Integer.parseInt(properties.getProperty("maxConnectionAge")));
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param configPath
	 */
	public void loadDataSource(InputStream stream) {
		try {
			// this.getClass().getResourceAsStream("1.txt"));
			dataSource = new ComboPooledDataSource();
			Properties properties = new Properties();
			properties.load(stream);
			dataSource.setProperties(properties);
			dataSource.setUser(properties.getProperty("user"));
			dataSource.setPassword(properties.getProperty("password"));
			dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
			dataSource.setDriverClass(properties.getProperty("driverClass"));

			dataSource.setInitialPoolSize(Integer.parseInt(properties.getProperty("initialPoolSize")));
			dataSource.setMinPoolSize(Integer.parseInt(properties.getProperty("minPoolSize")));
			dataSource.setMaxPoolSize(Integer.parseInt(properties.getProperty("maxPoolSize")));
			dataSource.setMaxIdleTime(Integer.parseInt(properties.getProperty("maxIdleTime")));
			dataSource.setIdleConnectionTestPeriod(Integer.parseInt(properties
					.getProperty("idleConnectionTestPeriod")));
			dataSource.setMaxConnectionAge(Integer.parseInt(properties.getProperty("maxConnectionAge")));
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

}
