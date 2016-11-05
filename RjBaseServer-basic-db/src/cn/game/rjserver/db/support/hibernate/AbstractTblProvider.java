/*********************************************
 * Copyright (c) 2011 by rallen
 *********************************************/

/**
 * a4a-commons-db
 * 2011-3-3
 */
package cn.game.rjserver.db.support.hibernate;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.engine.NamedQueryDefinition;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.springframework.transaction.config.TxNamespaceHandler;

import cn.game.rjserver.db.support.hibernate.entity.EntityImpl;

/**
 * @author rallen 2011-3-3
 * @doc class explain:
 */
public abstract class AbstractTblProvider {
	protected PersistenceListener dbListener;

	public PersistenceListener getDbListener() {
		return dbListener;
	}

	public void setDbListener(PersistenceListener dbListener) {
		this.dbListener = dbListener;
	}

	/**
	 * 获得表名
	 */
	public String getTableName(Class clazz) {
		PersistentClass pc = this.getConfiguration().getClassMapping(clazz.getName());
		return pc.getTable().getName();
	}

	/**
	 * 获得列名
	 */
	public String getColumnName(Class clazz, int icol) {
		PersistentClass pc = this.getConfiguration().getClassMapping(clazz.getName());
		return pc.getTable().getColumn(icol).getName();
	}

	/**
	 * 获得所有列名
	 */
	public List<String> getColumnNames(Class clazz) {
		PersistentClass pc = this.getConfiguration().getClassMapping(clazz.getName());
		Iterator<Column> itr = pc.getTable().getColumnIterator();
		List<String> columns = new ArrayList<String>();
		while (itr.hasNext()) {
			Column tmp = itr.next();
			columns.add(tmp.getName());
		}
		return columns;
	}

	// select all
	public List<?> selectImpList(Class<?> c) throws Exception {
		String ormClassName = c.getSimpleName();
		String hql = "from " + ormClassName + " as tbl";
		return getEntityImpList(hql);
	}

	// 查询
	public List<?> selectImpList(Class<?> c, List<Criterion> creterions) throws Exception {
		return selectImpList(c, creterions, null);
	}

	// 查询
	public List<?> selectImpList(Class<?> c, List<Criterion> creterions, Projection projection) throws Exception {
		List<?> list = null;
		Session session = null;
		if (creterions == null) {
			return selectImpList(c);
		}
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(c);
			// Criterion creterion = Expression.between("date", begin, end);
			for (int i = 0; i < creterions.size(); i++) {
				criteria = criteria.add(creterions.get(i));
			}
			if (projection != null) {
				criteria.setProjection(projection);
			}
			list = criteria.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("getEntityImpList error：" + e.getMessage());
		} finally {
			this.closeSession(session);
		}
		return list;
	}

	// 查询
	public List<?> selectImpList(Class<?> c, List<Criterion> creterions, int max) throws Exception {
		List<?> list = null;
		Session session = null;
		if (creterions == null) {
			return selectImpList(c);
		}
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(c);
			// Criterion creterion = Expression.between("date", begin, end);
			for (int i = 0; i < creterions.size(); i++) {
				criteria = criteria.add(creterions.get(i));
			}
			criteria.setMaxResults(max);
			list = criteria.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("getEntityImpList error：" + e.getMessage());
		} finally {
			this.closeSession(session);
		}
		return list;
	}

	// select all
	public List<?> selectImpList(String ormClassName) throws Exception {
		String hql = "from " + ormClassName + " as tbl";
		return getEntityImpList(hql);
	}

	// 查询
	public List<?> getEntityImpList(String hql) throws Exception {
		List<?> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			list = query.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("getEntityImpList error：" + e.getMessage());
		} finally {
			this.closeSession(session);
		}
		return list;
	}

	// 查询
	public List<?> getEntityImpList(String hql, int index, int max) throws Exception {
		List<?> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setFirstResult(index);
			query.setMaxResults(max);
			list = query.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("getEntityImpList error：" + e.getMessage());
		} finally {
			this.closeSession(session);
		}
		return list;
	}

	// id 索引
	public EntityImpl getUniqueEntity(Class<?> c, long id) throws Exception {
		String ormClassName = c.getSimpleName();
		return (EntityImpl) getUniqueEntity("from " + ormClassName + " as tbl where tbl.id=" + id);
	}

	// 单一查询
	public Object getUniqueEntity(String hql) throws Exception {
		Object nnti = null;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			// System.out.println("======getUniqueEntity=======hql:"+hql);
			Query query = session.createQuery(hql);
			nnti = query.uniqueResult();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("getUniqueEntity error：" + e.getMessage());
		} finally {
			this.closeSession(session);
		}
		return nnti;
	}

	// 添加
	public long addEntityImp(EntityImpl orm) throws Exception {
		if (dbListener != null && !dbListener.saveOrUpdate(orm))
			return -1;
		long result = 0;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			session.save(orm);
			tx.commit();
			result = orm.getId();
		} catch (Exception e) {
			throw new Exception("add " + orm.getClass().getName() + " error：" + e.getMessage());
		} finally {
			this.closeSession(session);
		}
		return result;
	}

	// 添加
	public Serializable saveEntity(Object orm) throws Exception {
		if (dbListener != null && !dbListener.saveOrUpdate(orm))
			return -1;
		Serializable s = 0;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			s = session.save(orm);
			tx.commit();
		} catch (Exception e) {
			throw new Exception("add " + orm.getClass().getName() + " error：" + e.getMessage());
		} finally {
			this.closeSession(session);
		}
		return s;
	}

	// 更新或添加
	public long saveOrUpdateEntityImp(EntityImpl orm) throws Exception {
		if (dbListener != null && !dbListener.saveOrUpdate(orm))
			return -1;
		Long result = null;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(orm);
			result = orm.getId();
			tx.commit();
		} catch (Exception e) {
			throw new Exception("add " + orm.getClass().getSimpleName() + " error：" + e.getMessage());
		} finally {
			this.closeSession(session);
		}
		return result;
	}

	// 更新
	public void updateEntityImp(Object orm) throws Exception {
		if (dbListener != null && !dbListener.saveOrUpdate(orm))
			return;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			session.update(orm);
			tx.commit();
		} catch (Exception e) {
			if (e instanceof ConstraintViolationException) {
				throw new Exception("update " + orm.getClass().getSimpleName() + " error: has a same one");
			} else {
				throw new Exception("update " + orm.getClass().getSimpleName() + " error：" + e.getMessage());
			}
		} finally {
			this.closeSession(session);
		}
	}

	// 执行update sql
	public int update(String hql) throws Exception {
		Session session = null;
		int ret = -1;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Query queryupdate = session.createQuery(hql);
			System.out.println(hql);
			ret = queryupdate.executeUpdate();
			tx.commit();

		} catch (Exception e) {
			if (e instanceof ConstraintViolationException) {
				throw new Exception("update error: has a same one");
			} else {
				throw new Exception("update error：" + e.getMessage());
			}
		} finally {
			this.closeSession(session);
		}
		return ret;
	}

	// 删除
	public void deleteEntityImp(Object orm) throws Exception {
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(orm);
			tx.commit();
		} catch (Exception e) {
			if (e instanceof ConstraintViolationException) {
				throw new Exception("delete " + orm.getClass().getSimpleName() + " error: is using");
			} else {
				throw new Exception("delete " + orm.getClass().getSimpleName() + " error：" + e.getMessage());
			}
		} finally {
			this.closeSession(session);
		}
	}

	// 分页查询
	public List<?> findPageByQuery(int pageNo, int pageSize, String hql, Map map) throws Exception {
		List<?> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			if (map != null) {
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					query.setParameter(key.toString(), map.get(key));
				}
			}
			query.setFirstResult((pageNo - 1) * pageSize);
			query.setMaxResults(pageSize);
			list = query.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
		return list;
	}

	// 分页查询
	public List<?> findPageByFR(int start, int end, String hql, Map map) throws Exception {
		List<?> list = null;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			if (map != null) {
				Iterator it = map.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					query.setParameter(key.toString(), map.get(key));
				}
			}
			query.setFirstResult(start);
			query.setMaxResults((end - start) > 0 ? (end - start) : 1);
			list = query.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
		return list;
	}

	public List doHQLQueryName(String queryname, Map filter) throws Exception {
		List<?> list = null;
		Session session = null;
		try {
			String hql = this.getNamedHQLQuery(queryname);
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			if (filter != null) {
				Iterator it = filter.keySet().iterator();
				while (it.hasNext()) {
					Object key = it.next();
					query.setParameter(key.toString(), filter.get(key));
				}
			}
			list = query.list();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
		return list;
	}

	public List doSQLQueryNameToMap(String queryname, Map filter, String columnNames[]) throws Exception {
		// TODO Auto-generated method stub
		List list = null;
		Session session = null;
		try {
			String hql = this.getNamedSQLQuery(queryname);
			session = this.getSession();
			SQLQuery sqlquery = session.createSQLQuery(hql);

			if (filter != null && filter.size() != 0) {
				Set keySet = filter.keySet();
				Iterator it = keySet.iterator();
				while (it.hasNext()) {
					String key = (String) it.next();
					sqlquery.setParameter(key, filter.get(key));
				}
			} else {
				String nameParam[] = sqlquery.getNamedParameters();
				for (String param : nameParam) {
					sqlquery.setParameter(param, null);
				}
			}
			int columnLen = columnNames.length;
			for (int i = 0; i < columnLen; i++) {
				sqlquery.addScalar(columnNames[i], Hibernate.STRING);
			}
			Query query = sqlquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
		return list;
	}

	// hql to count
	public long getCount(String hql) throws Exception {
		long count = 0;
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			// System.out.println();
			Object obj = query.iterate().next();
			if (obj != null) {
				count = Long.parseLong(obj.toString());
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
		return count;
	}

	public void doSQLQueryUpdate(String sql) throws Exception {
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			session.createSQLQuery(sql).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
	}

	public void doExecuteUpdate(String hql) throws Exception {
		Session session = null;
		try {
			session = this.getSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
	}

	// 原生sql
	public <T> List<T> getResultBySql(String sql, Class<T> entityClass) throws Exception {
		List rs = null;
		Session session = null;
		try {
			session = getSession();
			rs = session.createSQLQuery(sql).addEntity("en", entityClass).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeSession(session);
		}
		return rs;
	}

	// getNamedSQLQueries
	public String getNamedSQLQuery(String queryName) {
		// TODO Auto-generated method stub
		Map mapsqlQuery = getConfiguration().getNamedSQLQueries();
		/*
		 * Iterator it = mapsqlQuery.keySet().iterator(); while (it.hasNext()) {
		 * String name = it.next().toString(); System.out.println(name + "___" +
		 * mapsqlQuery.get(name)); }
		 */
		if (mapsqlQuery.containsKey(queryName)) {
			NamedQueryDefinition namedQueryDefinition = (NamedQueryDefinition) mapsqlQuery.get(queryName);
			String strsqlQuery = namedQueryDefinition.getQueryString();
			// System.out.println(queryName + "_queryName__" + strsqlQuery);
			return strsqlQuery;
		}
		return null;
	}

	public String getNamedHQLQuery(String queryName) {
		// TODO Auto-generated method stub
		Map mapHQLQuery = getConfiguration().getNamedQueries();
		NamedQueryDefinition namedQueryDefinition = (NamedQueryDefinition) mapHQLQuery.get(queryName);
		String strHQLQuery = namedQueryDefinition.getQueryString();
		return strHQLQuery;
	}

	public abstract Configuration getConfiguration();

	// Session
	public abstract Session getSession() throws Exception;

	// close Session
	public abstract void closeSession(Session session) throws Exception;

}
