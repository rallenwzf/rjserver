/**
 * rallen
 */
package cn.game.rjserver.uc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.game.rjserver.db.support.jdbc.JdbOp;
import cn.game.rjserver.db.support.jdbc.connsource.ConnectionSourceFactory;
import cn.game.rjserver.db.support.jdbc.page.Pageable;
import cn.game.rjserver.log.MyLogger;
import cn.game.rjserver.uc.InitDcsConfig;
import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen)
 */
public class BaseDaoImpl implements BaseDao {
	protected static MyLogger logger = MyLogger.getLog("db");

	@Override
	public Object selectSingleEntity(Class<?> cla, String sql) throws DbException {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			logger.d("db_select：" + sql);
			jdbOp.createConnect();
			ResultSet rs = jdbOp.getData(sql);
			if (rs != null && rs.next()) {
				Object obj = cla.newInstance();
				HBM.fillEntity(obj, rs);
				return obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Object[] selectSingleEntity(Class<?> clas[], String sql) throws DbException {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			logger.d("db_select：" + sql);
			Object[] objs = new Object[clas.length];
			jdbOp.createConnect();
			ResultSet rs = jdbOp.getData(sql);
			if (rs != null && rs.next()) {
				for (int i = 0; i < clas.length; i++) {
					Object obj = clas[i].newInstance();
					HBM.fillEntity(obj, rs);
					objs[i] = obj;
				}
				return objs;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public List selectEntities(Class<?> cla, String sql) throws DbException {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			List list = new ArrayList();
			jdbOp.createConnect();
			logger.d("db_select：" + sql);
			ResultSet rs = jdbOp.getData(sql);
			if (rs != null) {
				while (rs.next()) {
					Object obj = cla.newInstance();
					HBM.fillEntity(obj, rs);
					list.add(obj);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return null;
	}
	@Override
	public List selectEntities(Class<?> cla) throws DbException {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			List list = new ArrayList();
			jdbOp.createConnect();
			String sql = "select * from " + HBM.getTableName(cla.newInstance());
			ResultSet rs = jdbOp.getData(sql);
			logger.d("db_select：" + sql);
			if (rs != null) {
				while (rs.next()) {
					Object obj = cla.newInstance();
					HBM.fillEntity(obj, rs);
					list.add(obj);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TPageEntity selectEntities(Class<?> cla, int pageId, int pageNum) throws DbException {
		// TODO Auto-generated method stub
		TPageEntity tPageEntity = new TPageEntity();
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			jdbOp.createConnect();
			List list = new ArrayList();
			tPageEntity.setEntityList(list);
			String sql = "select * from " + HBM.getTableName(cla.newInstance());
			Pageable rs = jdbOp.getData2(sql);
			logger.d("db_select：" + sql);
			// ResultSet rs = jdbOp.getData(sql);
			rs.setPageSize(pageNum);// 设置每页显示数目
			rs.gotoPage(pageId);// 设置当前选择第几页
			tPageEntity.setPageCount(rs.getPageCount());
			for (int i = 0; i < rs.getPageRowsCount(); i++) {
				Object obj = cla.newInstance();
				HBM.fillEntity(obj, rs);
				list.add(obj);
				rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return tPageEntity; 
	} 

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TPageEntity selectEntities(Class<?> cla, String sql, int pageId, int pageNum) throws DbException {
		// TODO Auto-generated method stub
		TPageEntity tPageEntity = new TPageEntity();
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			jdbOp.createConnect();
			List list = new ArrayList();
			tPageEntity.setEntityList(list);
			Pageable rs = jdbOp.getData2(sql);
			logger.d("db_select：" + sql);
			// ResultSet rs = jdbOp.getData(sql);
			rs.setPageSize(pageNum);// 设置每页显示数目
			rs.gotoPage(pageId);// 设置当前选择第几页
			tPageEntity.setPageCount(rs.getPageCount());
			for (int i = 0; i < rs.getPageRowsCount(); i++) {
				Object obj = cla.newInstance();
				HBM.fillEntity(obj, rs);
				list.add(obj);
				rs.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return tPageEntity;
	}

	@Override
	public int selectCount(String sql) throws DbException {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			jdbOp.createConnect();
			logger.d("db_select：" + sql);
			ResultSet rs = jdbOp.getData(sql);
			if (rs != null && rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public long selectId(String sql) throws DbException {
		// TODO Auto-generated method stub
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			jdbOp.createConnect();
			logger.d("db_select：" + sql);
			ResultSet rs = jdbOp.getData(sql);
			if (rs != null && rs.next()) {
				return rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public boolean update(String sql) throws DbException {
		// TODO Auto-generated method stub
		boolean keyVal = false;
		JdbOp jdbOp = new JdbOp(InitDcsConfig.DBKEY);
		try {
			jdbOp.createConnect();
			sql = sql.replaceAll("'null'", "NULL");
			logger.d("db_update：" + sql);
			keyVal = jdbOp.exeSql(sql);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DbException(e.getCause());
		} finally {
			try {
				jdbOp.disConnect();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return keyVal;
	}

}
