/**
 * rallen wangzhifeng
 */
package cn.game.rjserver.basicutils.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Administrator
 *
 */
public class ExceptionUtils {
	/**
	 * 获取Exception 完整信息
	 * @param t
	 * @return
	 */
	public static String getTrace(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter writer = new PrintWriter(stringWriter);
		t.printStackTrace(writer);
		StringBuffer buffer = stringWriter.getBuffer();
		return buffer.toString();
	}
}
