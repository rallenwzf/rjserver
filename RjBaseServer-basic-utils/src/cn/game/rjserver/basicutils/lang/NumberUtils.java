/**
 * 
 */
package cn.game.rjserver.basicutils.lang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rallen 2011-3-10
 */
public class NumberUtils {
	public static String getCnNum(int number) {
		String str = "";
		String words = number + "";
		for (int i = 0; i < words.length(); i++) {
			String tt = "";
			switch (words.charAt(i)) {
			case '0':
				tt = "零";
				break;
			case '1':
				tt = "一";
				break;
			case '2':
				tt = "二";
				break;
			case '3':
				tt = "三";
				break;
			case '4':
				tt = "四";
				break;
			case '5':
				tt = "五";
				break;
			case '6':
				tt = "六";
				break;
			case '7':
				tt = "七";
				break;
			case '8':
				tt = "八";
				break;
			case '9':
				tt = "九";
				break;
			default:
				break;
			}
			str += tt;
		}
		return str;
	}
}
