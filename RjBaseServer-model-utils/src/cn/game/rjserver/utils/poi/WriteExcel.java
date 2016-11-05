/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

package cn.game.rjserver.utils.poi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author rallen 2011-6-1
 */
public abstract class WriteExcel {

	public List dataLst;
	public int rowId;

	public WriteExcel() {
	}

	public WriteExcel(List dataLst) {
		this.dataLst = dataLst;
	}

	public void write(String fileName, int rowId) {
		this.rowId = rowId;
		this.write(fileName);
	}

	public abstract void write(String fileName);

	public abstract void writeOut(OutputStream outStream);

	public void write(OutputStream outStream, boolean isAppend) {
		try {
			writeExcel(outStream, isAppend);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected abstract void writeExcel(OutputStream outStream, boolean isAppend);

	public void close() {
		rowId = 0;
		this.dataLst = null;
	}

	public static WriteExcel getWriteExcel(String fileName) {
		boolean isExcel2003 = ExcelUtils.isExcel2003(fileName);
		if (isExcel2003) {
			return new WriteExcel2003();
		} else {
			return new WriteExcel2007();
		}
	}

	public Object cellData(int colId, Object obj) {
		return obj;
	}

	public List getDataLst() {
		return dataLst;
	}

	public void setDataLst(List dataLst) {
		this.dataLst = dataLst;
	}

	/**
	 * @doc class explain:
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
