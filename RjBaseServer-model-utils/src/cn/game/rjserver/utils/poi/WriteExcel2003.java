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
public class WriteExcel2003 extends WriteExcel {

	private HSSFSheet sheet;
	private HSSFWorkbook wb;

	public WriteExcel2003() {
		this.close();
	}

	public WriteExcel2003(List dataLst) {
		this.close();
		this.dataLst = dataLst;
	}

	@Override
	public void write(String fileName) {
		// TODO Auto-generated method stub
		this.close();
		boolean isExcel2003 = ExcelUtils.isExcel2003(fileName);
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
			if (isExcel2003) {
				write(fileOut, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fileOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writeOut(OutputStream outStream) {
		// TODO Auto-generated method stub
		try {
			this.wb.write(outStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void writeExcel(OutputStream outStream, boolean isAppend) {
		// TODO Auto-generated method stub
		if (this.wb == null) {
			this.wb = new HSSFWorkbook();
			this.sheet = this.wb.createSheet();
		}
		if (!isAppend) {
			this.rowId = 0;
		}
		for (int i = 0; i < dataLst.size(); i++) {
			// 行
			if (isAppend) {
				this.rowId++;
			} else {
				this.rowId = 0;
			}
			Object objs = dataLst.get(i);
			Object objArr[] = null;
			if (dataLst.get(i) instanceof List) {
				objArr = ((List) objs).toArray();
			} else {
				objArr = (Object[]) objs;
			}
			HSSFRow row = sheet.createRow(this.rowId++);
			for (int j = 0; j < objArr.length; j++) {
				// 列
				Object cdObject = cellData(j, objArr[j]);
				String cellData = cdObject != null ? cdObject.toString() : "";
				HSSFCell cell = row.createCell(j);
				cell.setCellValue(new HSSFRichTextString(cellData));
			}
		}
		try {
			if (!isAppend) {
				this.wb.write(outStream);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		rowId = 0;
		sheet = null;
		wb = null;
		super.close();
	}

}
