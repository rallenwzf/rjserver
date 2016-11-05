package cn.game.rjserver.db.support.jdbc.page;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

public class PageableResultSet implements Pageable { 

	 protected java.sql.ResultSet rs=null;
	 protected int rowsCount;
	 protected int pageSize;
	 protected int curPage;
	 protected String command = "";
	
	 
	 public PageableResultSet(java.sql.ResultSet rs) throws java.sql.SQLException {
		 
		  if(rs==null) throw new SQLException("given ResultSet is NULL","user");
		  	rs.last();
		  rowsCount=rs.getRow();
		  //System.out.println(rowsCount);
		  rs.beforeFirst();
		  this.rs=rs;
	
	 }
	 public int getCurPage() {
	  return curPage;
	 }
	
	 public int getPageCount() {
	  if(rowsCount==0) return 0;
	  if(pageSize==0) return 1;
	//  calculate PageCount
	  double tmpD=(double)rowsCount/pageSize;
	  int tmpI=(int)tmpD;
	  if(tmpD>tmpI) tmpI++;
	  return tmpI;
	 }
	
	 public int getPageRowsCount() {
	  if(pageSize==0) return rowsCount;
	  if(getRowsCount()==0) return 0;
	  if(curPage!=getPageCount()) return pageSize;
	  return rowsCount-(getPageCount()-1)*pageSize;
	 }
	   
	 public int getPageSize() {
	  return pageSize;
	 }
	
	 public int getRowsCount() {
	  return rowsCount;
	 }
	
	 public void gotoPage(int page) {
	  if (rs == null)
	   return;
	   if (page < 1)
	   page = 1;
	   if (page > getPageCount())
	   page = getPageCount();
	   int row = (page - 1) * pageSize + 1;
	   try {
	   rs.absolute(row);
	   curPage = page;
	   }
	   catch (java.sql.SQLException e) {
	   }
	
	
	 }
	
	 public void pageFirst() throws SQLException {
	  int row=(curPage-1)*pageSize+1;
	  rs.absolute(row);
	
	 }
	
	 public void pageLast() throws SQLException {
	  int row=(curPage-1)*pageSize+getPageRowsCount();
	  rs.absolute(row);
	
	
	 }
	
	 public void setPageSize(int pageSize) {
	  if(pageSize>=0){
	   this.pageSize=pageSize;
	   curPage=1;
	   }
	
	 }
	
	 
	
	 public boolean next() throws SQLException {
	  return rs.next();
	 }
	
	 public boolean absolute(int row) throws SQLException {
	  return rs.absolute(row);
	 }
	
	 public void afterLast() throws SQLException {
	  rs.afterLast();
	  
	 }
	
	 public void beforeFirst() throws SQLException {
	  rs.beforeFirst();
	  
	 }
	
	 public void cancelRowUpdates() throws SQLException {
	  rs.cancelRowUpdates();
	 }
	
	 public void clearWarnings() throws SQLException {
	  rs.clearWarnings();
	  
	 }
	
	 public void close() throws SQLException {
	  rs.close();
	  
	 }
	
	 public void deleteRow() throws SQLException {
	  rs.deleteRow();
	  
	 }
	
	 public int findColumn(String columnName) throws SQLException {
	  return rs.findColumn(columnName);
	 }
	
	 public boolean first() throws SQLException {
	  return rs.first();
	 }
	
	 public Array getArray(int i) throws SQLException {
	  return rs.getArray(i);
	 }
	
	 public Array getArray(String colName) throws SQLException {
	  return rs.getArray(colName);
	 }
	
	 public InputStream getAsciiStream(int columnIndex) throws SQLException {
	  return rs.getAsciiStream(columnIndex);
	 }
	
	 public InputStream getAsciiStream(String columnName) throws SQLException {
	  return rs.getAsciiStream(columnName);
	 }
	
	 public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
	  return rs.getBigDecimal(columnIndex);
	 }
	
	 public BigDecimal getBigDecimal(String columnName) throws SQLException {
	  return rs.getBigDecimal(columnName);
	 }
	
	 public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
	  return rs.getBigDecimal(columnIndex, scale);
	 }
	
	 public BigDecimal getBigDecimal(String columnName, int scale) throws SQLException {
	  return rs.getBigDecimal(columnName, scale);
	 }
	
	 public InputStream getBinaryStream(int columnIndex) throws SQLException {
	  return rs.getBinaryStream(columnIndex);
	 }
	
	 public InputStream getBinaryStream(String columnName) throws SQLException {
	  return rs.getBinaryStream(columnName);
	 }
	
	 public Blob getBlob(int i) throws SQLException {
	  return rs.getBlob(i);
	 }
	
	 public Blob getBlob(String colName) throws SQLException {
	  return rs.getBlob(colName);
	 }
	
	 public boolean getBoolean(int columnIndex) throws SQLException {
	  return rs.getBoolean(columnIndex);
	 }
	
	 public boolean getBoolean(String columnName) throws SQLException {
	  return rs.getBoolean(columnName);
	 }
	
	 public byte getByte(int columnIndex) throws SQLException {
	  return rs.getByte(columnIndex);
	 }
	
	 public byte getByte(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getByte(columnName);
	 }
	
	 public byte[] getBytes(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getBytes(columnIndex);
	 }
	
	 public byte[] getBytes(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getBytes(columnName);
	 }
	
	 public Reader getCharacterStream(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getCharacterStream(columnIndex);
	 }
	
	 public Reader getCharacterStream(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getCharacterStream(columnName);
	 }
	
	 public Clob getClob(int i) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getClob(i);
	 }
	
	 public Clob getClob(String colName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getClob(colName);
	 }
	
	 public int getConcurrency() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getConcurrency();
	 }
	
	 public String getCursorName() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getCursorName();
	 }
	
	 public Date getDate(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getDate(columnIndex);
	 }
	
	 public Date getDate(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getDate(columnName);
	 }
	
	 public Date getDate(int columnIndex, Calendar cal) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getDate(columnIndex, cal);
	 }
	
	 public Date getDate(String columnName, Calendar cal) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getDate(columnName, cal);
	 }
	
	 public double getDouble(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getDouble(columnIndex);
	 }
	
	 public double getDouble(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getDouble(columnName);
	 }
	
	 public int getFetchDirection() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getFetchDirection();
	 }
	
	 public int getFetchSize() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getFetchSize();
	 }
	
	 public float getFloat(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getFloat(columnIndex);
	 }
	
	 public float getFloat(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getFloat(columnName);
	 }
	
	 public int getInt(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getInt(columnIndex);
	 }
	
	 public int getInt(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getInt(columnName);
	 }
	
	 public long getLong(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getLong(columnIndex);
	 }
	
	 public long getLong(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getLong(columnName);
	 }
	
	 public ResultSetMetaData getMetaData() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getMetaData();
	 }
	
	 public Object getObject(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getObject(columnIndex);
	 }
	
	 public Object getObject(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getObject(columnName);
	 }
	
	 public Object getObject(int i, Map<String, Class<?>> map) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getObject(i, map);
	 }
	
	 public Object getObject(String colName, Map<String, Class<?>> map) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getObject(colName, map);
	 }
	
	 public Ref getRef(int i) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getRef(i);
	 }
	
	 public Ref getRef(String colName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getRef(colName);
	 }
	
	 public int getRow() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getRow();
	 }
	
	 public short getShort(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getShort(columnIndex);
	 }
	
	 public short getShort(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getShort(columnName);
	 }
	
	 public Statement getStatement() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getStatement();
	 }
	
	 public String getString(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getString(columnIndex);
	 }
	
	 public String getString(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getString(columnName);
	 }
	
	 public Time getTime(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getTime(columnIndex);
	 }
	
	 public Time getTime(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getTime(columnName);
	 }
	
	 public Time getTime(int columnIndex, Calendar cal) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getTime(columnIndex, cal);
	 }
	
	 public Time getTime(String columnName, Calendar cal) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getTime(columnName, cal);
	 }
	
	 public Timestamp getTimestamp(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getTimestamp(columnIndex);
	 }
	
	 public Timestamp getTimestamp(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getTimestamp(columnName);
	 }
	
	 public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getTimestamp(columnIndex, cal);
	 }
	
	 public Timestamp getTimestamp(String columnName, Calendar cal) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getTimestamp(columnName, cal);
	 }
	
	 public int getType() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getType();
	 }
	
	 public URL getURL(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getURL(columnIndex);
	 }
	
	 public URL getURL(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getURL(columnName);
	 }
	
	 public InputStream getUnicodeStream(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getUnicodeStream(columnIndex);
	 }
	
	 public InputStream getUnicodeStream(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getUnicodeStream(columnName);
	 }
	
	 public SQLWarning getWarnings() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.getWarnings();
	 }
	
	 public void insertRow() throws SQLException {
	  // TODO 自动生成方法存根
	  rs.insertRow();
	  
	 }
	
	 public boolean isAfterLast() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.isAfterLast();
	 }
	
	 public boolean isBeforeFirst() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.isBeforeFirst();
	 }
	
	 public boolean isFirst() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.isFirst();
	 }
	
	 public boolean isLast() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.isLast();
	 }
	
	 public boolean last() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.last();
	 }
	
	 public void moveToCurrentRow() throws SQLException {
	  // TODO 自动生成方法存根
	  rs.moveToCurrentRow();
	 }
	
	 public void moveToInsertRow() throws SQLException {
	  // TODO 自动生成方法存根
	  rs.moveToInsertRow();
	 }
	
	 public boolean previous() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.previous();
	 }
	
	 public void refreshRow() throws SQLException {
	  // TODO 自动生成方法存根
	  rs.refreshRow();
	 }
	
	 public boolean relative(int rows) throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.relative(rows);
	 }
	
	 public boolean rowDeleted() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.rowDeleted();
	 }
	
	 public boolean rowInserted() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.rowInserted();
	 }
	
	 public boolean rowUpdated() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.rowUpdated();
	 }
	
	 public void setFetchDirection(int direction) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.setFetchDirection(direction);
	 }
	
	 public void setFetchSize(int rows) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.setFetchSize(rows);
	 }
	
	 public void updateArray(int columnIndex, Array x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateArray(columnIndex, x);
	 }
	
	 public void updateArray(String columnName, Array x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateArray(columnName, x);
	 }
	
	 public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateAsciiStream(columnIndex, x, length);
	 }
	
	 public void updateAsciiStream(String columnName, InputStream x, int length) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateAsciiStream(columnName, x, length);
	 }
	
	 public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBigDecimal(columnIndex, x);
	 }
	
	 public void updateBigDecimal(String columnName, BigDecimal x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBigDecimal(columnName, x);
	 }
	
	 public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBinaryStream(columnIndex, x, length);
	 }
	
	 public void updateBinaryStream(String columnName, InputStream x, int length) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBinaryStream(columnName, x, length);
	 }
	
	 public void updateBlob(int columnIndex, Blob x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBlob(columnIndex, x);
	 }
	
	 public void updateBlob(String columnName, Blob x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBlob(columnName, x);
	 }
	
	 public void updateBoolean(int columnIndex, boolean x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBoolean(columnIndex, x);
	 }
	
	 public void updateBoolean(String columnName, boolean x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBoolean(columnName, x);
	 }
	
	 public void updateByte(int columnIndex, byte x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateByte(columnIndex, x);
	 }
	
	 public void updateByte(String columnName, byte x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateByte(columnName, x);
	 }
	
	 public void updateBytes(int columnIndex, byte[] x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBytes(columnIndex, x);
	 }
	
	 public void updateBytes(String columnName, byte[] x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateBytes(columnName, x);
	 }
	
	 public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateCharacterStream(columnIndex, x, length);
	 }
	
	 public void updateCharacterStream(String columnName, Reader reader, int length) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateCharacterStream(columnName, reader, length);
	 }
	
	 public void updateClob(int columnIndex, Clob x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateClob(columnIndex, x); 
	 }
	
	 public void updateClob(String columnName, Clob x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateClob(columnName, x);  
	 }
	
	 public void updateDate(int columnIndex, Date x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateDate(columnIndex, x);
	 }
	
	 public void updateDate(String columnName, Date x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateDate(columnName, x);
	 }
	
	 public void updateDouble(int columnIndex, double x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateDouble(columnIndex, x); 
	 }
	
	 public void updateDouble(String columnName, double x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateDouble(columnName, x);
	 }
	
	 public void updateFloat(int columnIndex, float x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateFloat(columnIndex, x);
	 }
	
	 public void updateFloat(String columnName, float x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateFloat(columnName, x);
	 }
	
	 public void updateInt(int columnIndex, int x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateInt(columnIndex, x);
	 }
	
	 public void updateInt(String columnName, int x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateInt(columnName, x);
	 }
	
	 public void updateLong(int columnIndex, long x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateLong(columnIndex, x);
	 }
	
	 public void updateLong(String columnName, long x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateLong(columnName, x);
	 }
	
	 public void updateNull(int columnIndex) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateNull(columnIndex);
	 }
	
	 public void updateNull(String columnName) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateNull(columnName);
	 }
	
	 public void updateObject(int columnIndex, Object x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateObject(columnIndex, x);
	 }
	
	 public void updateObject(String columnName, Object x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateObject(columnName, x);
	 }
	
	 public void updateObject(int columnIndex, Object x, int scale) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateObject(columnIndex, x, scale);
	 }
	
	 public void updateObject(String columnName, Object x, int scale) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateObject(columnName, x, scale);
	 }
	
	 public void updateRef(int columnIndex, Ref x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateRef(columnIndex, x);
	 }
	
	 public void updateRef(String columnName, Ref x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateRef(columnName, x);
	 }
	
	 public void updateRow() throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateRow();
	 }
	
	 public void updateShort(int columnIndex, short x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateShort(columnIndex, x);
	 }
	
	 public void updateShort(String columnName, short x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateShort(columnName, x);
	 }
	
	 public void updateString(int columnIndex, String x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateString(columnIndex, x);
	 }
	
	 public void updateString(String columnName, String x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateString(columnName, x);
	 }
	
	 public void updateTime(int columnIndex, Time x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateTime(columnIndex, x);
	 }
	
	 public void updateTime(String columnName, Time x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateTime(columnName, x);
	 }
	
	 public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateTimestamp(columnIndex, x);
	 }
	
	 public void updateTimestamp(String columnName, Timestamp x) throws SQLException {
	  // TODO 自动生成方法存根
	  rs.updateTimestamp(columnName, x);
	 }
	
	 public boolean wasNull() throws SQLException {
	  // TODO 自动生成方法存根
	  return rs.wasNull();
	 }
	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getRowId(columnIndex);
	}
	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getRowId(columnLabel);
	}
	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateRowId(columnIndex,x);		
	}
	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateRowId(columnLabel, x);
		
	}
	@Override
	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return rs.getHoldability();
	}
	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return rs.isClosed();
	}
	@Override
	public void updateNString(int columnIndex, String nString)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNString(columnIndex, nString);
		
	}
	@Override
	public void updateNString(String columnLabel, String nString)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNString(columnLabel, nString);
		
	}
	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNClob(columnIndex, nClob);
	}
	@Override
	public void updateNClob(String columnLabel, NClob nClob)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNClob(columnLabel, nClob);
		
	}
	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getNClob(columnIndex);
	}
	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getNClob(columnLabel);
	}
	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getSQLXML(columnIndex);
	}
	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getSQLXML(columnLabel);
	}
	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateSQLXML(columnIndex, xmlObject);
	}
	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateSQLXML(columnLabel, xmlObject);
	}
	@Override
	public String getNString(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getNString(columnIndex);
	}
	@Override
	public String getNString(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getNString(columnLabel);
	}
	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getNCharacterStream(columnIndex);
	}
	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getNCharacterStream(columnLabel);
	}
	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNCharacterStream(columnIndex, x, length);
	}
	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNCharacterStream(columnLabel, reader,
				length);
	}
	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateAsciiStream(columnIndex, x, length);
	}
	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateBinaryStream(columnIndex, x, length);
	}
	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateCharacterStream(columnIndex, x, length);
	}
	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateAsciiStream(columnLabel, x, length);
	}
	@Override
	public void updateBinaryStream(String columnLabel, InputStream x,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateBinaryStream(columnLabel, x,
				length);
	}
	@Override
	public void updateCharacterStream(String columnLabel, Reader reader,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateCharacterStream(columnLabel, reader,
				length);
	}
	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateBlob(columnIndex, inputStream, length);
	}
	@Override
	public void updateBlob(String columnLabel, InputStream inputStream,
			long length) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateBlob(columnLabel, inputStream,
				length);
	}
	@Override
	public void updateClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateClob(columnIndex, reader, length);
	}
	@Override
	public void updateClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateClob(columnLabel, reader, length);
	}
	@Override
	public void updateNClob(int columnIndex, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNClob(columnIndex, reader, length);
	}
	@Override
	public void updateNClob(String columnLabel, Reader reader, long length)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNClob(columnLabel, reader, length);
	}
	@Override
	public void updateNCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNCharacterStream(columnIndex, x);
	}
	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNCharacterStream(columnLabel, reader);
	}
	@Override
	public void updateAsciiStream(int columnIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateAsciiStream(columnIndex, x);
	}
	@Override
	public void updateBinaryStream(int columnIndex, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateBinaryStream(columnIndex, x);
	}
	@Override
	public void updateCharacterStream(int columnIndex, Reader x)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateCharacterStream(columnIndex, x);
	}
	@Override
	public void updateAsciiStream(String columnLabel, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateAsciiStream(columnLabel, x);
	}
	@Override
	public void updateBinaryStream(String columnLabel, InputStream x)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateBinaryStream(columnLabel, x);
	}
	@Override
	public void updateCharacterStream(String columnLabel, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateCharacterStream(columnLabel, reader);
	}
	@Override
	public void updateBlob(int columnIndex, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateBlob(columnIndex, inputStream);
	}
	@Override
	public void updateBlob(String columnLabel, InputStream inputStream)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateBlob(columnLabel, inputStream);
	}
	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateClob(columnIndex, reader);
	}
	@Override
	public void updateClob(String columnLabel, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateClob(columnLabel, reader);
	}
	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNClob(columnIndex, reader);
	}
	@Override
	public void updateNClob(String columnLabel, Reader reader)
			throws SQLException {
		// TODO Auto-generated method stub
		rs.updateNClob(columnLabel, reader);
		
	}
	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return rs.unwrap(iface);
	}
	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		
		return rs.isWrapperFor(iface);
	}
	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getObject(columnIndex, type);
	}
	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		// TODO Auto-generated method stub
		return rs.getObject(columnLabel, type);
	}

}


