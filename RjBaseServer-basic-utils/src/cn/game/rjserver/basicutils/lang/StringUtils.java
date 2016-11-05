package cn.game.rjserver.basicutils.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public StringUtils() {
	}

	public static String listToString(List list, String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				sb.append(list.get(i));
			} else {
				sb.append(list.get(i));
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	public static List<Integer> stringToListint(String str, String separator) {
		List<Integer> rs = new ArrayList();
		if (str != null) {
			String arr[] = str.split(separator);
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null && arr[i].trim().matches("\\d+")) {
					rs.add(Integer.parseInt(arr[i]));
				}
			}
		}
		return rs;
	}

	public static List<Long> stringToListlong(String str, String separator) {
		List<Long> rs = new ArrayList();
		if (str != null) {
			String arr[] = str.split(separator);
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null && arr[i].trim().matches("\\d+")) {
					rs.add(Long.parseLong(arr[i]));
				}
			}
		}
		return rs;
	}

	public static List<String> stringToListstring(String str, String separator) {
		List<String> rs = new ArrayList();
		if (str != null) {
			String arr[] = str.split(separator);
			for (int i = 0; i < arr.length; i++) {
				if (arr[i] != null && !arr[i].trim().equals("")) {
					rs.add(arr[i]);
				}
			}
		}
		return rs;
	}

	/**
	 * 
	 * @param s
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static String bSubstring(String s, int length) {
		try {
			byte[] bytes = s.getBytes("Unicode");
			int n = 0;
			int i = 2;
			for (; i < bytes.length && n < length; i++) {
				if (i % 2 == 1) {
					n++;
				} else {
					if (bytes[i] != 0) {
						n++;
					}
				}
			}
			{
				if (bytes[i - 1] != 0)
					i = i - 1;
				else
					i = i + 1;
			}
			return new String(bytes, 0, i, "Unicode");
		} catch (Exception e) {
			return "";
		}
	}

	public static void SplitIt(String SplitStr, int length) {
		int loopCount;
		loopCount = (SplitStr.length() % length == 0) ? (SplitStr.length() / length) : (SplitStr.length() / length + 1);
		System.out.println("Will  Split  into  " + loopCount);// 计算将分成多少个小字符串，也即循环多少次可以把这些分好的小字符串输出
		for (int i = 1; i <= loopCount; i++) {
			if (i == loopCount) {
				System.out.println(SplitStr.substring((i - 1) * length, SplitStr.length()));// 输出最后一个子字符串
			} else {
				System.out.println();// 输出每次得到的子字符串
			}
		}
	}

	public static String CollectionToString(Collection c, String w) {
		String str = "";
		int i = 0;
		for (Iterator it = c.iterator(); it.hasNext();) {
			String tt = it.next().toString();
			if (!tt.equals("")) {
				if (i == 0) {
					str += tt;
				} else {
					str += w + tt;
				}
				i++;
			}
		}
		return str;
	}

	public static String ArrayToString(Object obj[], String separator) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < obj.length; i++) {
			if (i == 0) {
				sb.append(obj[i]);
			} else {
				sb.append(separator);
				sb.append(obj[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * 按每行特定字节长度，切分字符串为多行
	 * 
	 * @param src
	 * @param rowLength
	 * @return
	 */
	public static List<String> cutString(String src, int typeLength) {
		List<String> tempStrings = new ArrayList<String>();
		int length = src.length();
		String temp = "";
		int tempLength = 0;
		for (int i = 1; i < length; i++) {
			if (tempLength >= typeLength) {
				tempStrings.add(temp);
				temp = "";
				tempLength = 0;
			}
			String t = src.substring(i - 1, i);
			if (isDoubleByte(t)) {
				tempLength += 2;
			} else {
				tempLength++;
			}
			temp += t;
			continue;
		}
		// 最后一遍切完
		if (!temp.equals("")) {
			tempStrings.add(temp);
			temp = "";
			tempLength = 0;
		}
		return tempStrings;
	}

	/**
	 * 判断是否为双字节(包含汉字)
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isDoubleByte(String target) {
		try {
			Pattern pattern = Pattern.compile("[^\\x00-\\xff]");
			Matcher matcher = pattern.matcher(target);
			while (matcher.find()) {
				return true;
			}
			return false;
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @return
	 */
	public static int getCharNum(String text) {
		if (text == null || text.trim().equals("")) {
			return 0;
		} else {
			int num = 0;
			for (int i = 0; i < text.length(); i++) {
				if (isDoubleByte(text.charAt(i) + "")) {
					num += 2;
				} else {
					num += 1;
				}
			}
			return num;
		}
	}
}
