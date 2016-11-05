/**
 * rallen
 */
package cn.game.rjserver.uc.dao;

import java.sql.ResultSet;
import java.util.List;

import cn.game.rjserver.uc.exception.DbException;

/**
 * @author wangzhifeng(rallen)
 */
public interface BaseDao {
	/**
	 * 单表单条查询
	 * 
	 * @param cla
	 * @param sql
	 * @return 单条记录
	 */
	public Object selectSingleEntity(Class<?> cla, String sql) throws DbException;

	/**
	 * 主要是多表连表查询
	 * 
	 * @param clas
	 * @param sql
	 * @return 单条记录
	 */
	public Object[] selectSingleEntity(Class<?> clas[], String sql) throws DbException;

	/**
	 * 查询
	 * 
	 * @param cla
	 * @param sql
	 * @return 多条记录
	 */
	public List selectEntities(Class<?> cla, String sql) throws DbException;

	/**
	 * 查询
	 * 
	 * @param cla
	 * @param sql
	 * @return 多条记录
	 */
	public List selectEntities(Class<?> cla) throws DbException;

	/**
	 * 分页
	 * 
	 * @param cla
	 * @param pageId
	 * @param pageNum
	 * @return
	 */
	public TPageEntity selectEntities(Class<?> cla, int pageId, int pageNum) throws DbException;

	/**
	 * 分页
	 * 
	 * @param cla
	 * @param sql
	 * @param pageId
	 * @param pageNum
	 * @return
	 */
	public TPageEntity selectEntities(Class<?> cla, String sql, int pageId, int pageNum) throws DbException;

	/**
	 * @param sql
	 * @return 记录条数
	 */
	public int selectCount(String sql) throws DbException;

	/**
	 * @param sql
	 * @return
	 */
	public long selectId(String sql) throws DbException;

	/**
	 * @param sql
	 * @return
	 */
	public boolean update(String sql) throws DbException;

}
