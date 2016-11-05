/**
 * rallen 
 */
package cn.game.rjserver.uc.dao;

import java.util.List;

/**
 * @author Administrator
 * 
 */
public class Page {
	/**
	 * 每页行数
	 */
	private int pageRows;
	/**
	 * 页码
	 */
	private int pageIndex;
	/**
	 * 总页数
	 */
	private int pageCount;
	/**
	 * 总行数 
	 */
	private int rowCount;
	private List currentPage;

	public Page() {
		pageIndex=1; 
		pageRows = 20;
	}

	public int getPageRows() {
		return pageRows;
	}

	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex < 1)
			pageIndex = 1;
		this.pageIndex = pageIndex;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
		if (pageIndex > pageCount)
			setPageIndex(pageCount);
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		calcPage();
	}

	public List getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(List currentPage) {
		this.currentPage = currentPage;
	}

	private void calcPage() {
		if (rowCount % pageRows > 0)
			setPageCount(rowCount / pageRows + 1);
		else
			setPageCount(rowCount / pageRows);
	}

}
