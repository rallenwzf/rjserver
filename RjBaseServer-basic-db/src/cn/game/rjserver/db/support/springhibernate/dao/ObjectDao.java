package cn.game.rjserver.db.support.springhibernate.dao;

/*********************************************
 Copyright (c) 2011 by rallen
 *********************************************/

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.game.rjserver.db.support.hibernate.entity.Entity;
import cn.game.rjserver.db.support.springhibernate.query.PageQuery;
import cn.game.rjserver.db.support.springhibernate.query.ShQuery;



/**
 * @author Administrator
 * 
 * public class ObjectDaoImpl extends HibernateDaoSupport implements ObjectDao {
 * }
 */
public interface ObjectDao {

	public abstract Serializable create(Entity entity) throws DaoException;

	public abstract void update(Entity entity) throws DaoException;

	public abstract void update(String hql) throws DaoException;

	public abstract void remove(Entity entity) throws DaoException;

	public abstract void remove(Serializable serializable, Class class1)
			throws DaoException;

	public abstract Entity load(Serializable serializable, Class class1)
			throws DaoException;

	public abstract List loadAll(Class class1) throws DaoException;

	public abstract PageQuery find(PageQuery pagequery) throws DaoException;

	public abstract List find(String s) throws DaoException;

	public abstract List find(String s, Object aobj[]) throws DaoException;

	public abstract int entityCount(String countString, ShQuery query)
			throws DaoException;

	public abstract List doQueryName(String queryname, Map filter);

	public abstract List<Map> doQueryNameToMap(String queryname, Map filter,
			String columnNames[]);

	public abstract List<Map> doQueryNameToMap(String queryname, Map filter,
			String[] columnNames, Integer curPage, Integer pageSize);
}
