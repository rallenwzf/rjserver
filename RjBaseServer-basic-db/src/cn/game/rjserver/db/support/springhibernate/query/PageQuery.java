package cn.game.rjserver.db.support.springhibernate.query;

import java.util.*;


public class PageQuery {

	private final int MAX_PAGE_ROWS = 25;
	private final int MAX_PAGE_SIZE = 10;
	private int pageRows;
	private int pageIndex;
	private int pageSize;
	private int rowSize;
	private int firstResult;
	private int maxResults;
	private int maxPageSize;
	private ShQuery query;
	private String queryString;
	private String countString;
	private int firstPage;
	private List pageList;
	private List currentPage;

	public PageQuery() {
		initQuery();
	}

	public PageQuery(int pageIndex, ShQuery query) {
		initQuery();
		setPageIndex(pageIndex);
		setQuery(query);
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

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageIndex > pageSize)
			setPageIndex(pageSize);
	}

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
		calcPage();
		calcFirstResult();
	}

	public int getFirstResult() {
		return firstResult;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

	public int getMaxPageSize() {
		return maxPageSize;
	}

	public void setMaxPageSize(int maxPageSize) {
		this.maxPageSize = maxPageSize;
	}

	public List getPageList() {
		return pageList;
	}

	public void setPageList(List pageList) {
		this.pageList = pageList;
		setFirstPage(pageIndex);
	}

	public List getCurrentPage() {
		currentPage = new LinkedList();
		Iterator iter = pageList.iterator();
		for (int i = firstResult; i < (pageIndex - 1) * pageRows; i++)
			iter.next();

		for (int i = 0; i < pageRows && iter.hasNext(); i++)
			currentPage.add(iter.next());

		return currentPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public int getLastPage() {
		if (getFirstPage() + getMaxPageSize() > getPageSize())
			return getPageSize();
		else
			return (getFirstPage() + getMaxPageSize()) - 1;
	}

	public boolean isExistPage(int pageIndex) {
		if (pageIndex == 0)
			return false;
		return pageIndex >= getFirstPage() && pageIndex <= getLastPage();
	}

	public String getCountString() {
		buildCountString();
		return countString;
	}

	public void setCountString(String countString) {
		this.countString = countString;
	}

	public ShQuery getQuery() {
		return query;
	}

	public void setQuery(ShQuery query) {
		this.query = query;
		setQueryString(QueryUtils.getHQL(query));
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	private void calcPage() {
		if (rowSize % pageRows > 0)
			setPageSize(rowSize / pageRows + 1);
		else
			setPageSize(rowSize / pageRows);
	}

	private void calcFirstResult() {
		firstResult = (pageIndex - 1) * pageRows;
	}

	private void buildCountString() {
		String temp = queryString.toUpperCase();
		countString = "Select Count(*) " + queryString.substring(temp.indexOf("FROM"));
	}

	private void initQuery() {
		setPageIndex(0);
		setMaxResults(200);
		setPageRows(20);
		setMaxPageSize(10);
		setPageSize(-1);
	}
}
