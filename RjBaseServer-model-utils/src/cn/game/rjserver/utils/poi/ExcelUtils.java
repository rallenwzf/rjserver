/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

package cn.game.rjserver.utils.poi;

import java.text.DecimalFormat;

/**
 * @author rallen 2011-6-1
 */
public class ExcelUtils {

	/**
	 * 检查文件名是否为空或者是否是Excel格式的文件
	 * 
	 * @doc class explain:
	 * @param fileName
	 * @return
	 */
	public static boolean isXlsFile(String fileName) {
		if (fileName != null && fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")) {
			return true;
		}
		return false;
	}

	/**
	 * 检查文件名是否是Excel2003 否则认为是Excel2007
	 * 
	 * @doc class explain:
	 * @param fileName
	 * @return
	 */
	public static boolean isExcel2003(String fileName) {
		if (fileName.matches("^.+\\.(?i)(xls)$")) {
			return true;
		}
		return false;
	}

	/**
	 * <ul>
	 * <li>Description:[正确地处理整数后自动加零的情况]</li>
	 * <ul>
	 * 
	 * @param sNum
	 * @return
	 */
	public static String getRightStr(String sNum) {
		DecimalFormat decimalFormat = new DecimalFormat("#.000000");
		String resultStr = decimalFormat.format(new Double(sNum));
		if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
			resultStr = resultStr.substring(0, resultStr.indexOf("."));
		}else if (resultStr.matches("\\.[0]+$")) {
			resultStr = "0";
		}
		return resultStr;
	}
}
