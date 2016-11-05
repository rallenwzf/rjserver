/**
 * rallen wangzhifeng
 */
package test;

import org.apache.log4j.PropertyConfigurator;

import cn.game.rjserver.log.codec.LogReader;
import cn.game.rjserver.log.codec.gamelog4j.GameLog4jReader;
import cn.game.rjserver.log.codec.log4j.Log4jReader;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class SeparateMainReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PropertyConfigurator.configure(System.getProperty("user.dir")
				+ "/log4j.properties");
		System.out.println("迁移debug to log(HiyouLogEntire格式)...");
		LogReader read = new Log4jReader("debug");
		read.read();
	}

}
