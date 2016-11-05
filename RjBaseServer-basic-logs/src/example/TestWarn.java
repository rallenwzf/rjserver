/**
 * rallen wangzhifeng
 */
package example;

import cn.game.rjserver.warn.GameWarner;

/**
 * @author wangzhifeng(rallen)
 * 
 */
public class TestWarn {

	static GameWarner warner = GameWarner.getWarner();

	final class WarnCMD {
		public static final int CMD_LOGIN = 1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * WarnCommand.xml文件中对应配置该报警代号的详细
		 */
		warner.warn(201);
	}

}
