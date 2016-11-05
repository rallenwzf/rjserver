/**
 * rallen
 */
package cn.game.rjserver.uc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.game.rjserver.uc.entity.TucOthersdkUser;
import cn.game.rjserver.uc.entity.TucUserinfo;
import cn.game.rjserver.uc.exception.DbException;
import cn.game.rjserver.uc.provider.UcProviderManager;

/**
 * @author wangzhifeng(rallen)
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String configPath = System.getProperty("user.dir") + "/file.config/";
		System.out.println(configPath);
		// PropertyConfigurator.configure(configPath + "/log4j.properties");

		// InitDcsConfig.getInstace();
		//
		// CollectedInfo info = new CollectedInfo();
		// info.setGamecode("41522");
		// new UserApiImpl().creatNewUser(info);

		// String
		// string="insert into tuc_userinfo t values(NULL,'000003',0,'null','null',0,NULL,NULL,'null','null',null,null,null,'012441','null','null','null','null','null','','null')";
		// string = string.replaceAll("'null'", "NULL");
		// System.out.println(string);

		// try {
		// // TucUserinfo ti =
		// //
		// UcProviderManager.getInstance().getUserProvider().getUser("1010003880");
		// // System.out.println(ti);
		// CollectedInfo info = new CollectedInfo();
		// info.setGamecode("012441");
		// new UserApiImpl().creatNewUser(info);
		// // TucOthersdkUser t =
		// // ProviderManager.getInstance().getUserProvider()
		// // .getSdkUserShipBysdkuid("100163858", "1");
		// // System.out.println(t.getSdkuid());
		// } catch (DbException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

}
