/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

package cn.game.rjserver.utils.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author rallen 2011-6-1
 */
public class ReadExcel {
	/** 总行数 */
	private int totalRows = 0;

	/** 总列数 */
	private int totalCells = 0;

	private int sheetId = 0;

	public ReadExcel() {
	}

	public ReadExcel(int sheetId) {
		this.sheetId = sheetId;
	}

	/**
	 * <ul>
	 * <li>Description:[根据文件名读取excel文件]</li>
	 * <ul>
	 * 
	 * @param fileName
	 * @return
	 */
	public List<ArrayList<String>> read(String fileName) {
		List<ArrayList<String>> dataList = null;

		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (!ExcelUtils.isXlsFile(fileName)) {
			return dataList;
		}
		boolean isExcel2003 = ExcelUtils.isExcel2003(fileName);

		/** 检查文件是否存在 */
		File file = new File(fileName);
		if (file == null || !file.exists()) {
			return dataList;
		}
		try {
			dataList = read(new FileInputStream(file), isExcel2003);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dataList;
	}

	/**
	 * <ul>
	 * <li>Description:[根据流读取Excel文件]</li>
	 * <ul>
	 * 
	 * @param inputStream
	 * @param isExcel2003
	 * @return
	 */
	public List<ArrayList<String>> read(InputStream inputStream, boolean isExcel2003) {
		List<ArrayList<String>> dataList = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = isExcel2003 ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(
					inputStream);
			dataList = read(wb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * <ul>
	 * <li>Description:[读取数据]</li>
	 * <ul>
	 * 
	 * @param wb
	 * @return
	 */
	protected List<ArrayList<String>> read(Workbook wb) {
		List<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
		/** 得到第一个shell */
		Sheet sheet = wb.getSheetAt(this.sheetId);
		this.totalRows = sheet.getPhysicalNumberOfRows();
		if (this.totalRows >= 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		/** 循环Excel的行 */
		for (int r = 0; r < this.totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}

			ArrayList<String> rowLst = new ArrayList<String>();
			/** 循环Excel的列 */
			for (short c = 0; c < this.getTotalCells(); c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (cell == null) {
					rowLst.add(cellValue);
					continue;
				}
				
				/** 处理数字型的,自动去零 */
				if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
					/** 在excel里,日期也是数字,在此要进行判断 */
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						cellValue = cell.getDateCellValue().toLocaleString();
					} else {
						cellValue = ExcelUtils.getRightStr(cell.getNumericCellValue() + "");
					}
				}
				/** 处理字符串型 */
				else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
					cellValue = cell.getStringCellValue();
				}
				/** 处理布尔型 */
				else if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
					cellValue = cell.getBooleanCellValue() + "";
				}
				/** 其它的,非以上几种数据类型 */
				else {
					cellValue = cell.toString() + "";
				}

				rowLst.add(cellValue);
			}
			dataList.add(rowLst);
		}
		return dataList;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public int getTotalCells() {
		return totalCells;
	}

	/**
	 * <ul>
	 * <li>Description:[测试main方法]</li>
	 * <ul>
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		List<ArrayList<String>> dataList = new ReadExcel()
				.read("D:/三星版块列表.xlsx");
		for (ArrayList<String> innerLst : dataList) {
			StringBuffer rowData = new StringBuffer();
			for (String dataStr : innerLst) {
				rowData.append(",").append(dataStr);
			}
			if (rowData.length() > 0) {
				System.out.println(rowData.deleteCharAt(0).toString());
			}
		}
	}
}
