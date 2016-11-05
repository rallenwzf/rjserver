/**
 * rallen wangzhifeng
 */
package example;

import java.util.Date;

import cn.game.rjserver.log.GameEntityLogger;
import cn.game.rjserver.log.MyLogger;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class TestLog {

	static MyLogger logger = MyLogger.getLog("test");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		logger.d("测试程序中debug、info等日志记录，且分文件记录");
		logger.e("测试程序中debug、info等日志记录，且分文件记录");
	}

}
