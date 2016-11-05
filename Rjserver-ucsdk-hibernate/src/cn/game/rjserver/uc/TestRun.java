/**
 * rallen
 */
package cn.game.rjserver.uc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.PropertyConfigurator;

import cn.game.rjserver.db.support.hibernate.MySessionFactory;
import cn.game.rjserver.uc.entity.TucOthersdkUser;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.exception.DbException;
import cn.game.rjserver.uc.provider.UcProviderManager;

/**
 * @author wangzhifeng(rallen)
 */
public class TestRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String configPath = System.getProperty("user.dir") + "/file.config/";
		System.out.println(configPath);
		PropertyConfigurator.configure(configPath + "/log4j.properties");
		MySessionFactory.rebuildSessionFactory();
	}

}
