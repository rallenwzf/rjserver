/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.basicutils.lang;

import java.util.Random;

/**
 * @author wangzhifeng(rallen)
 */
public class RandomUtils {
	public final static String[] HEXD_ROUND = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F" };
	public final static String[] NUMBER_ROUND = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	static Random r = new Random();

	public static String getNumberRandom(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			str += NUMBER_ROUND[r.nextInt(NUMBER_ROUND.length)];
		}
		return str;
	}

	public static String getRandom(int length) {
		String str = "";
		for (int i = 0; i < length; i++) {
			str += HEXD_ROUND[r.nextInt(HEXD_ROUND.length)];
		}
		return str;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static int getProbabilityRandom() {
		int rnum = r.nextInt(10000);
		return rnum;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isInProbability(int value) {
		int rnum = r.nextInt(10000);
		if (value >= rnum) {
			return true;
		}
		return false;
	}
}
