/**
 * 
 */
package cn.game.rjserver.basicutils.lang;

import java.io.*;
import java.util.zip.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.lang.Math;
import java.net.URLDecoder;

/**
 * @author kyle
 */
public class StringFormat {

	/**
	 * 
	 */
	public StringFormat() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// String xx =
		// "这里需要特别注意的是，如果你想把压缩后的byte[]保存到字符串中，不能直接使用new String(byte)或者byte.toString()，因为这样转换之后容量是增加的。同样的道理，如果是字符串的话，也不能直接使用new String().getBytes()获取byte[]传入到decompress中进行解压缩";
		// byte[] compressedByte = StringFormat.compress(xx);

		// Base64.encodeBase64(compressedByte);
		// String ss = new String(Base64.encodeBase64(compressedByte));
		// System.out.println("xx_length="+xx.length());

		// String xx_ = StringFormat.decompress(compressedByte);

		// System.out.println("xx="+StringFormat.getRandom());
		// System.out.println("xx_="+xx_);
		// System.out.println(""+"*你好".toCharArray()[0]);
		// System.out.println(""+StringFormat.isChinese("*你好".toCharArray()[0]));
		// int[] myArray = new int[]{12,13,14,25};
		// System.out.println(StringFormat.Bubble(myArray));

		// System.out.println(StringFormat.getVerifyCode("100066"));
		// StringFormat.testEncoder();
		System.out.println(StringFormat.str_replace("jiangyanhainihaojiang", "jiang", "xx"));

	}

	/**
	 * 压缩字符串为 byte[]
	 * 储存可以使用new sun.misc.BASE64Encoder().encodeBuffer(byte[] b)方法
	 * 保存为字符串 *
	 * 
	 * @param str
	 *            压缩前的文本
	 * @return
	 */
	public static final byte[] compress(String str) {
		if (str == null)
			return null;

		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes("UTF-8"));
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
			e.printStackTrace();
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return compressed;
	}

	/**
	 * 将压缩后的 byte[] 数据解压缩
	 * 
	 * @param compressed
	 *            压缩后的 byte[] 数据
	 * @return 解压后的字符串
	 */
	public static final String decompress(byte[] compressed) {
		if (compressed == null)
			return null;

		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
			ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return decompressed;
	}

	/**
	 * getYYMMDD
	 * 
	 * @param date
	 * @return
	 */
	public static String getYYMMDD(Date date) {
		String dateStr = "";
		if (date != null) {
			dateStr = new SimpleDateFormat("yyyy-MM-dd").format(date);
		}
		return dateStr;
	}

	/**
	 * getYYMMDDHHMMSS
	 * 
	 * @param date
	 * @return
	 */
	public static String getYYMMDDHHMMSS(Date date) {
		String dateStr = "";
		if (date != null) {
			dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		}
		return dateStr;
	}

	/**
	 * isChinese
	 * 判断字符是否是中文
	 * 
	 * @param c
	 * @return
	 */
	public static boolean isChinese(char c) {

		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

		|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

		|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION

		|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

		|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

			return true;

		}

		return false;

	}

	/**
	 * getFirstCharByString
	 * 取得字符串的首个字符
	 * 
	 * @param myStr
	 * @return
	 */
	public static char getFirstCharByString(String myStr) {

		return myStr.toCharArray()[0];

	}

	/**
	 * getRandom
	 * 
	 * @return int
	 */
	public static int getRandom() {

		int rtn_value = 0;
		rtn_value = (int) ((Math.random() * 10) % 3);

		return rtn_value;
	}

	/**
	 * Bubble
	 * 数组排序后，取得最大值
	 * 
	 * @param arrays
	 * @return
	 */
	public static int Bubble(int[] arrays) {
		int rtn_value = 0;

		if (arrays == null || arrays.length == 0)
			return rtn_value;
		for (int i = 0; i < arrays.length - 1; i++) {
			for (int j = 0; j < arrays.length - i - 1; j++) {
				if (arrays[j] > arrays[j + 1]) {
					int temp = arrays[j];
					arrays[j] = arrays[j + 1];
					arrays[j + 1] = temp;
				}
			}
		}
		rtn_value = arrays[arrays.length - 1];
		return rtn_value;
	}

	/**
	 * getTime
	 * 
	 * @param YYMMDDHHMMSS
	 * @return
	 */
	public static int getTime(String YYMMDDHHMMSS) {

		YYMMDDHHMMSS = YYMMDDHHMMSS == null ? "" : YYMMDDHHMMSS.trim();

		if (YYMMDDHHMMSS.equals("")) {
			return 0;
		}

		try {

			SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date begin = dfs.parse(YYMMDDHHMMSS);
			return (int) begin.getTime();

		} catch (Exception e) {

			e.printStackTrace();
			return 0;

		}

	}

	/**
	 * str_replace
	 * 
	 * @param strSource
	 * @param strFrom
	 * @param strTo
	 * @return
	 */
	public static String str_replace(String strSource, String strFrom, String strTo) {

		if (strFrom == null || strFrom.equals(""))
			return strSource;
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;
		return strDest;
	}

}
