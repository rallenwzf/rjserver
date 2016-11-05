/**
 * 
 */
package cn.game.rjserver.basicutils.lang;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

/**
 * @author rallen 2011-3-9
 */
public class DateUtils {
	/**
	 * 进行日期范围搜索时格式转换
	 * 
	 * @param timestamp
	 * @return
	 */
	public static String timestampToShortStr(Timestamp timestamp) {
		Date date = new Date(timestamp.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	/**
	 * 进行日期范围搜索时格式转换
	 * 
	 * @param date
	 * @return
	 */
	public static String timestampToShortStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}

	public static String getDefaultDatestr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}

	/**
	 * @param date
	 * @param patterm
	 *            "yyyyMMdd"
	 * @return
	 */
	public static String timestampToShortStr(Date date, String patterm) {
		SimpleDateFormat sdf = new SimpleDateFormat(patterm);
		return sdf.format(date);
	}

	public static Date StringToDate(String dateString, String patterm) {
		SimpleDateFormat sdf = new SimpleDateFormat(patterm);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String DateToString(Date d, String patterm) {
		SimpleDateFormat sdf = new SimpleDateFormat(patterm);
		return sdf.format(d);
	}

	public static Date getShotDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 秒=0，毫秒=0
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMiddleDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 得到几天前或后的时间 num<0 前
	 * 
	 * @doc class explain: containing a getSometime method
	 * @param num
	 * @return Date
	 */
	public static Date getSometime(int num) {
		try {
			Date date = new Date();
			Calendar startDT = Calendar.getInstance();
			startDT.setTime(date);
			startDT.add(Calendar.DAY_OF_MONTH, num);
			return startDT.getTime();
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到前几天的时间 num<0 时，为前几天
	 * 
	 * @doc class explain: containing a getSometime method
	 * @param d
	 * @param num
	 * @return Date
	 */
	public static Date getSometime(Date d, int num) {
		try {
			Calendar startDT = Calendar.getInstance();
			startDT.setTime(d);
			startDT.add(Calendar.DAY_OF_MONTH, num);
			return startDT.getTime();
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 定制每日hour时执行方法
	 * 
	 * @param hour
	 *            几点
	 * @param first
	 *            是否立刻执行第一次
	 * @return Date
	 */
	public static Date getTaskSartDate(int hour, boolean first) {
		Calendar calendar = Calendar.getInstance();
		/** * 定制每日hour时执行方法 ** */
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date date = calendar.getTime();
		if (!first) {
			if (date.before(new Date())) {
				date = addDay(date, 1);
			}
		}
		return date;
	}

	/**
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, num);
		return startDT.getTime();
	}

	/**
	 * @doc class explain: [start,end)
	 * @param start
	 * @param end
	 * @return
	 */
	public static List getDateList(Date start, Date end) {
		Vector v = new Vector();

		Date t = start;
		while (t.before(end)) {
			v.add(t);
			t = getSometime(t, 1);
		}
		return v;
	}

	/**
	 * 求俩个日期间隔天数，要确保第二个日期大于第一个日期
	 * 
	 * @param t1
	 * @param t2
	 * @return
	 * @throws ParseException
	 */
	public static int getBetweenDays(long time, long time1)
			throws ParseException {
		int betweenDays = 0;
		Date d1 = new Date(time);
		Date d2 = new Date(time1);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1.setTime(d2);
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays;
	}
	
}
