/**
 * rallen wangzhifeng
 */
package test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import cn.game.rjserver.log.GameEntityLogger;
import cn.game.rjserver.log.MyLogger;
import cn.game.rjserver.warn.Global;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class TestWrite {

	static GameEntityLogger logger = GameEntityLogger.getLog();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		logger.setPath("log");
//		logger.i("ninnnnnnnnnnnnnnnnnnn");
//		String str=Global.PROJECT_CODESTRING;
//		String logpath = Configuration.getConfig("Global.PROJECT_LOGPATH");
//		System.out.println(logpath);
//		
//		MyLogger.getLog("tt").d("--------sdfsdfsdf");
		Calendar time = Calendar.getInstance();
		time.add(Calendar.HOUR_OF_DAY, -1);
		time.set(Calendar.MINUTE, 0);
		time.set(Calendar.SECOND, 0);
		System.out.println(time.getTime().toLocaleString()+"___"+File.separator+"____"+(time.get(Calendar.DAY_OF_MONTH)));
	}

}
