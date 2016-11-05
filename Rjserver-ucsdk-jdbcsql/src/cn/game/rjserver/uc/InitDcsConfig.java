/**
 * rallen
 */
package cn.game.rjserver.uc;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import cn.game.rjserver.db.support.jdbc.JdbOp;
import cn.game.rjserver.db.support.jdbc.connsource.C3p0ConnectionSourceImpl;
import cn.game.rjserver.db.support.jdbc.connsource.ConnectionSourceFactory;
import cn.game.rjserver.uc.dao.HBM;
import cn.game.rjserver.uc.exception.DbException;
import cn.game.rjserver.uc.provider.GsmucServerProviderImpl;
import cn.game.rjserver.uc.provider.GsmucUserProviderImpl;
import cn.game.rjserver.uc.provider.UcProviderManager;

/**
 * @author wangzhifeng(rallen)
 */
public class InitDcsConfig {
	private static InitDcsConfig initConfig;
	public static final String DBKEY = "DB_UC_key";
	public static boolean JAR_RUN = true;
	public static String c3p0dbConf = null;

	public synchronized static InitDcsConfig getInstace() {
		if (initConfig == null) {
			initConfig = new InitDcsConfig();
			initConfig.initDb();
		}
		return initConfig;
	}

	public static String getC3p0dbConf() {
		return c3p0dbConf;
	}

	public static void setC3p0dbConf(String c3p0dbConf) {
		InitDcsConfig.c3p0dbConf = c3p0dbConf;
	}

	public void initDb() {
		UcProviderManager.getInstance().setServerProvider(new GsmucServerProviderImpl());
		UcProviderManager.getInstance().setUserProvider(new GsmucUserProviderImpl());
		if (JAR_RUN) {
			C3p0ConnectionSourceImpl dbSource = new C3p0ConnectionSourceImpl();
			if (c3p0dbConf == null) {
				try {
					c3p0dbConf = this.getClass().getResource("").getPath() + "conf/c3p0db.properties";
					dbSource.setConfigPath(c3p0dbConf);
				} catch (Exception e) {
					e.printStackTrace();
				}
				dbSource.loadDataSource(this.getClass().getResourceAsStream("conf/c3p0db.properties"));
			} else {
				dbSource.setConfigPath(c3p0dbConf);
				dbSource.loadDataSource(c3p0dbConf);
			}
			ConnectionSourceFactory.addConnectionSource(DBKEY, dbSource);
			JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
			try {
				System.out.println("UC_SDK db 测试连接:" + c3p0dbConf);
				jdbOp.createConnect();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					jdbOp.disConnect();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		} else {
			if (c3p0dbConf == null)
				c3p0dbConf = this.getClass().getResource("").getPath() + "conf/c3p0db.properties";
			System.out.println("UC_SDK db:" + c3p0dbConf);
			C3p0ConnectionSourceImpl dbSource = new C3p0ConnectionSourceImpl();
			dbSource.setConfigPath(c3p0dbConf);
			dbSource.loadDataSource(c3p0dbConf);
			ConnectionSourceFactory.addConnectionSource(DBKEY, dbSource);
		}
	}

}
