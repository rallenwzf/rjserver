package cn.game.rjserver.basicutils.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常规字符串的转换及查找等处理
 * 
 * @author rajar.net wzf<br/>
 *         class explain: <br/>
 *         2009-9-25
 */
public class RegexRegular {
	// 十六进制下数字到字符的映射数组
	public final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };
	public final static String[] HEXD_ROUND = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F" };
	public static String INT_TAG_EXP = "DD+";

	/**
	 * 转换字节数组为十六进制字符串
	 * 
	 * @param 字节数组
	 * @return 十六进制字符串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 将一个字节转化成十六进制形式的字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 判断存在 containing a (Estimate)'s isHas method
	 * 
	 * @param target
	 * @param regex
	 * @return boolean
	 */
	public static boolean isHas(String target, String regex) {
		try {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(target);
			if (matcher.find()) {
				return true;
			}
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 从字符串中提取第一个三位以内的数 containing a (Estimate)'s getFig method
	 * 
	 * @param target
	 * @return int
	 */
	public static int getFig(String target) {
		try {
			Pattern pattern = Pattern.compile("\\d{1,3}");
			Matcher matcher = pattern.matcher(target);
			if (matcher.find()) {
				// System.out.println(Integer.parseInt(matcher.group(0))+"--bb--"+matcher.groupCount());
				return Integer.parseInt(matcher.group(0));
			}
			return 0;
		} catch (Throwable e) {
			// e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 不区分大小写 containing a (Regular)'s replaceAll method
	 * 
	 * @param regex
	 * @param replacement
	 * @param target
	 * @return String
	 */
	public static String replaceAll(String regex, String replacement, String target) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(target);
		return matcher.replaceAll(replacement);
	}

	/**
	 * 返回regex在target中出现的次数 containing a (RegexRegular)'s getRegCount method
	 * 
	 * @param regex
	 * @param target
	 * @return int
	 */
	public static int getRegCount(String regex, String target) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

	/**
	 * 从字符串中提取所有数字 containing a (RegexRegular)'s getFigs method
	 * 
	 * @param target
	 * @return String
	 */
	public static String getFigs(String target) {
		try {
			Pattern pattern = Pattern.compile("\\d{1,9}");
			Matcher matcher = pattern.matcher(target);
			String str = "";
			while (matcher.find()) {
				// System.out.println(Integer.parseInt(matcher.group(0))+"--bb--"+matcher.groupCount());
				str += matcher.group(0) + "";
			}
			return str;
		} catch (Throwable e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * regex为空是从字符串中提取数字 containing a (Estimate)'s getFig method
	 * 
	 * @param target
	 * @return int
	 */
	public static int getFig(String target, String regex) {
		try {
			if (regex == null) {
				regex = "\\d{1,7}";
			}
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(target);
			if (matcher.find()) {
				// System.out.println(Integer.parseInt(matcher.group(0))+"--bb--"+matcher.groupCount());
				return Integer.parseInt(matcher.group(0));
			}
			return 0;
		} catch (Throwable e) {
			// e.printStackTrace();
			return 0;
		}
	}

	public static List<String> getRagexString(String target, String regex) {
		List<String> result = null;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(target);
		while (matcher.find()) {
			if (result == null)
				result = new ArrayList<String>();
			// System.out.println(matcher.group(0)+"--bb--"+matcher.groupCount());
			result.add(matcher.group(0));
		}
		return result;
	}

	/**
	 * 根据取到的内容判断是否为标题 containing a (Regular)'s judgeTitleRegular method
	 * 
	 * @param title
	 * @return boolean
	 */
	public static boolean judgeTitleRegular(String title) {
		String t = title.replaceAll("\\w", "");
		if (t.length() > 7) {
			return true;
		} else {
			// 再处理
		}
		return false;
	}

	/**
	 * 判断是否为数字 containing a (Estimate)'s IsInt method
	 * 
	 * @param c
	 * @return boolean
	 */
	public static boolean IsInt(String c) {
		return c.replaceAll(" ", "").matches("\\d{1,9}");
	}

	/**
	 * containing a (Estimate)'s isChinese method
	 * 
	 * @param s
	 * @return boolean
	 */
	public static boolean isChinese(String s) {
		if (s.getBytes().length == s.length()) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 过滤数字 containing a (Estimate)'s filterFig method
	 * 
	 * @param c
	 * @return boolean
	 */
	public static String filterFig(String c) {
		return c.replaceAll("\\d{1,9}", "");

	}

	/**
	 * 过滤数字 containing a (Estimate)'s filterFig method
	 * 
	 * @param c
	 * @return boolean
	 */
	public static String filterFigTo(String c) {
		if (c.matches("\\d+")) {
			return c.replaceAll("\\d+", INT_TAG_EXP);
		}
		return c.replaceAll("\\d{1,9}", "");

	}

	/**
	 * 字符串首字母大写 containing a (Estimate)'s firstLetterCapital method
	 * 
	 * @param str
	 * @return String
	 */
	public static String firstLetterCapital(String str) {
		char[] chrs = str.toCharArray();
		String newStr = new String();
		for (int i = 0; i < chrs.length; i++) {
			if (chrs[i] == '.') {
				chrs[i + 1] = Character.toUpperCase(chrs[i + 1]);
			} else {
				if (i == 0) {
					chrs[i] = Character.toUpperCase(chrs[i]);
				}
				newStr = newStr + chrs[i];
			}
		}
		return newStr.replaceAll("-", "");
	}

	/**
	 * 字符串拆分取pv containing a (Estimate)'s splitStr method
	 * 
	 * @param str
	 * @param regx
	 * @return int[]
	 */
	public static int[] splitStr(Vector v) {
		try {
			// System.out.println(str+"--------");
			Object mm[] = v.toArray();
			int num1 = 0;
			int num2 = 0;
			int n1 = 0, n2 = 1;
			if (mm.length > 1) {
				// 超过两个时，取最后两个
				n1 = mm.length - 1;
				n2 = mm.length - 2;
				if (IsInt(mm[n1].toString())) {
					num1 = Integer.parseInt(mm[n1].toString());
				}
				if (IsInt(mm[n2].toString())) {
					num2 = Integer.parseInt(mm[n2].toString());
				}
				int max = num1 > num2 ? num1 : num2;// 返回大的
				int min = num1 > num2 ? num2 : num1;// 返回小的
				int pv[] = { max, min };
				return pv;
			} else {
				int pv[] = { Integer.parseInt(mm[n1].toString()), -1 };
				return pv;
			}

		} catch (Exception e) {
			return null;
		}
	}
}
