package cn.game.rjserver.db.support.jdbc;

import java.sql.*;
import java.util.*;
import java.io.*;

import cn.game.rjserver.db.support.jdbc.connsource.ConnectionSourceFactory;
import cn.game.rjserver.db.support.jdbc.connsource.JDCConnect;
import cn.game.rjserver.db.support.jdbc.page.Pageable;
import cn.game.rjserver.db.support.jdbc.page.PageableResultSet;

public class JdbOp {

	public Connection m_conn;
	// public JDCConnect jdcConn = null; // 数据库连接类
	public Statement m_statement = null;
	public String m_errMsg = null;
	public String dbKey;

	// protected int m_scrollType = ResultSet.TYPE_SCROLL_SENSITIVE; //缺省结果集油标类型
	// protected int m_updateType = ResultSet.CONCUR_READ_ONLY;
	/**
	 * consturcturer no parames
	 */
	public JdbOp() {
	}

	public JdbOp(String dbKey) {
		this.dbKey = dbKey;
	}

	/**
	 * 创建连接
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public boolean createConnect() throws Exception {
		// jdcConn = new JDCConnect();
		// m_conn = jdcConn.getConn(1);
		try {
			m_conn = ConnectionSourceFactory.getConnectionSource(dbKey).getConnection();
			m_statement = m_conn.createStatement();
		} catch (Exception e) {
			if (ConnectionSourceFactory.getConnectionException() != null) {
				ConnectionSourceFactory.getConnectionException().dbConnectError(dbKey, e);
			}
			throw e;
		}
		return true;
	}

	/**
	 * 创建连接
	 * 
	 * @return
	 * @throws java.lang.Exception
	 */
	public boolean createConnect(int dbFlag) throws Exception {
		// jdcConn = new JDCConnect();
		// m_conn = jdcConn.getConn(dbFlag);
		try {
			m_conn = ConnectionSourceFactory.getConnectionSource(dbKey).getConnection();
			m_statement = m_conn.createStatement();
		} catch (Exception e) {
			if (ConnectionSourceFactory.getConnectionException() != null) {
				ConnectionSourceFactory.getConnectionException().dbConnectError(dbKey, e);
			}
			throw e;
		}
		return true;
	}

	/**
	 * 断开连接
	 * 
	 * @throws SQLException
	 */
	public void disConnect() throws SQLException {
		try {
			if (m_conn != null) {
				if (!m_conn.isClosed()) {
					m_conn.close();
					// jdcConn = null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	// public boolean executeUpdate(String mySql) throws Exception {
	// m_errMsg = "";
	// boolean result = false;
	// try {
	// Statement st = m_conn.createStatement();
	// int num = st.executeUpdate(mySql);
	// if (num > 0) {
	// result = true;
	// }
	// st.close();
	// } catch (Exception e) {
	// m_errMsg = this.getClass().getName() + ".executeUpdate(): " + e +
	// "语句如下:\n" + mySql;
	// System.out.println(m_errMsg);
	// throw new Exception("executeUpdate error：" + e.getMessage());
	// }
	// return result;
	// }

	/**
	 * 执行Sql语句
	 * Statement.executeUpdate()方式执行
	 * 
	 * @param mySql
	 *            : 要执行的Sql语句
	 * @throws Exception
	 */
	public boolean exeSql(String mySql) throws Exception {
		m_errMsg = "";
		boolean result = false;
		try {
			Statement st = m_conn.createStatement();
			// result = st.execute(mySql);
			int num = st.executeUpdate(mySql);
			if (num > 0) {
				result = true;
			}
			st.close();
		} catch (Exception e) {
			m_errMsg = this.getClass().getName() + ".exeSql(): " + e + "语句如下:\n" + mySql;
			System.out.println(m_errMsg);
			if (ConnectionSourceFactory.getConnectionException() != null) {
				ConnectionSourceFactory.getConnectionException().dbExcuteError(dbKey, e);
			}
			throw new Exception("getEntityImpList error：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 获取结果集
	 * 
	 * @param mySql
	 * @return
	 * @throws java.lang.Exception
	 */
	public ResultSet getData(String mySql) throws Exception {
		m_errMsg = "";
		try {
			ResultSet rs = m_statement.executeQuery(mySql);
			return rs;
		} catch (Exception e) {
			m_errMsg = this.getClass().getName() + ".getData(): " + e;
			System.out.println(m_errMsg + ":\n" + mySql);
			if (ConnectionSourceFactory.getConnectionException() != null) {
				ConnectionSourceFactory.getConnectionException().dbExcuteError(dbKey, e);
			}
			throw new Exception(m_errMsg);
		}
	}

	/**
	 * getData2
	 * 
	 * @param mySql
	 * @return Pageable
	 * @throws Exception
	 */
	public Pageable getData2(String mySql) throws Exception {
		m_errMsg = "";
		try {
			PreparedStatement pstm = m_conn.prepareStatement(mySql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			Pageable rs = new PageableResultSet(pstm.executeQuery());
			return rs;
		} catch (Exception e) {
			m_errMsg = this.getClass().getName() + ".getData2(): " + e;
			System.out.println(m_errMsg + ":\n" + mySql);
			if (ConnectionSourceFactory.getConnectionException() != null) {
				ConnectionSourceFactory.getConnectionException().dbExcuteError(dbKey, e);
			}
			throw new Exception(m_errMsg);
		}
	}

	/**
	 * getConn
	 * 
	 * @return Connection
	 */
	public Connection getConn() {
		return this.m_conn;
	}

}
