/**
 * 
 */
package cn.game.rjserver.basicutils.lang;

import java.util.List;
import java.util.Map;

/**
 * @author wzf(rallen)
 * 
 */
public class DataTypeUtils {

	public static int[] toIntarr(Map map, String key) {
		if (map != null && map.get(key) != null) {
			int t[] = { 0, 1 };
			String str = map.get(key).toString();
			String arr[] = str.split("\\|");
			for (int i = 0; i < arr.length; i++) {
				t[i] = toInt(arr[i]);
			}
			return t;
		}
		return null;
	}

	public static int toInt(Map map, String key) {
		if (map != null && map.get(key) != null && map.get(key).toString().trim().matches("^[+-]?\\d+")) {
			return Integer.parseInt(map.get(key).toString());
		}
		return 0;
	}

	public static int toInt(String val) {
		if (val != null && val.matches("^[+-]?\\d+")) {
			return Integer.parseInt(val);
		}
		return 0;
	}

	public static int toInt(Integer val) {
		if (val != null) {
			return val.intValue();
		}
		return 0;
	}

	public static short toShort(Map map, String key) {
		if (map != null && map.get(key) != null && map.get(key).toString().trim().matches("^[+-]?\\d+")) {
			return Short.parseShort(map.get(key).toString());
		}
		return 0;
	}

	public static long toLong(Map map, String key) {
		if (map.get(key) != null) {
			if (map.get(key).toString().trim().matches("^[+-]?\\d+")) {
				return Long.parseLong(map.get(key).toString());
			} else if (map.get(key).toString().trim().contains("e+")) {
				java.math.BigDecimal big = new java.math.BigDecimal(map.get(key).toString().trim());
				return big.longValue();
			}
		}
		return 0;
	}

	public static long toLong(String val) {
		if (val != null) {
			if (val.matches("^[+-]?\\d+")) {
				return Long.parseLong(val);
			} else if (val.trim().contains("e+")) {
				java.math.BigDecimal big = new java.math.BigDecimal(val.trim());
				return big.longValue();
			}
		}
		return 0;
	}

	public static double toDouble(Map map, String key) {
		try {
			if (map != null && map.get(key) != null && !map.get(key).toString().trim().equals("")) {
				return Double.parseDouble(map.get(key).toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public static String toString(Map map, String key) {
		if (map.get(key) != null) {
			String str = map.get(key).toString();
			if (str == null || str.trim().equals("null")) {
				return null;
			}
			return str;
		}
		return null;
	}

	public static List toList(Map map, String key) {
		try {
			return (List) map.get(key);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
