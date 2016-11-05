/**
 * rallen wangzhifeng
 */
package test;

import java.util.Date;

import org.apache.log4j.PropertyConfigurator;

import cn.game.rjserver.log.codec.LogReader;
import cn.game.rjserver.log.codec.gamelog4j.GameLog4jReader;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class MainReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// log4j
		PropertyConfigurator.configure(System.getProperty("user.dir")
				+ "/log4j.properties");
		System.out.println("分析log to DB...");
		LogReader read = new GameLog4jReader("log");
		read.read();
	}

}
