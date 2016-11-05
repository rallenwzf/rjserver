package cn.game.rjserver.db.support.jdbc.connsource;

import java.sql.*;
import java.lang.ClassNotFoundException;
import java.sql.SQLException;

import cn.game.rjserver.db.support.jdbc.DbInfo;

public class JDCConnect implements ConnectionSource {
	public JDCConnect() {
	}

	// /**
	// * @param request
	// * @param dbFlag
	// * 1-mssql 2-mysql
	// * @return
	// * @throws ServletException
	// * @throws java.lang.ClassNotFoundException
	// * @throws SQLException
	// */
	// public Connection getConn(int dbFlag) throws ServletException,
	// ClassNotFoundException, SQLException {
	//
	// Connection conn = null;
	// try {
	// DbInfo di = new DbInfo();
	// di.setDbInfo();
	// Class.forName(di.getClassName()).newInstance();
	// if (dbFlag == 1) {
	// conn = DriverManager.getConnection(di.getDbUrl(), di.getUsr(),
	// di.getPwd());
	// }
	// return conn;
	// } catch (ClassNotFoundException ce) {
	// ce.printStackTrace();
	// throw ce;
	// } catch (SQLException se) {
	// se.printStackTrace();
	// throw se;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

	@Override
	public Connection getConnection() throws SQLException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			DbInfo di = new DbInfo();
			di.setDbInfo();
			Class.forName(di.getClassName()).newInstance();
			conn = DriverManager.getConnection(di.getDbUrl(), di.getUsr(), di.getPwd());
			return conn;
		} catch (SQLException se) {
			se.printStackTrace();
			throw se;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
