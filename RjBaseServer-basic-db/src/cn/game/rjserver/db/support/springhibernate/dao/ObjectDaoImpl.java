package cn.game.rjserver.db.support.springhibernate.dao;

import cn.game.rjserver.db.support.hibernate.entity.Entity;
import cn.game.rjserver.db.support.springhibernate.query.HibernateUtils;
import cn.game.rjserver.db.support.springhibernate.query.PageQuery;
import cn.game.rjserver.db.support.springhibernate.query.ShQuery;

import com.sun.istack.internal.FinalArrayList;

import java.io.Serializable;
import java.sql.SQLException;
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
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ObjectDaoImpl extends HibernateDaoSupport implements ObjectDao {

	public Serializable create(Entity obj) throws DaoException {
		return getHibernateTemplate().save(obj);
	}

	public void update(Entity obj) throws DaoException {
		getHibernateTemplate().update(obj);
	}

	public void update(final String hql) throws DaoException {
		try {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					try {
						Query query = session.createQuery(hql);
						query.executeUpdate();
					} catch (Exception e) {
						System.err.println(" function getHibernateTemplate().execute has error ");
						return false;
					}
					return true;
				}
			});
		} catch (Exception e) {
		}
	}

	public void remove(Entity obj) throws DaoException {
		getHibernateTemplate().delete(obj);
	}

	public void remove(Serializable objId, Class ref) throws DaoException {
		getHibernateTemplate().delete(load(objId, ref));
	}

	public Entity load(Serializable objId, Class ref) throws DaoException {
		return (Entity) getHibernateTemplate().load(ref, objId);
	}

	public List loadAll(Class ref) throws DaoException {
		return getHibernateTemplate().loadAll(ref);
	}

	public Object doExceute(final String sql, final List filter) {
		return this.getHibernateTemplate().execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			@Override
			public Object doInHibernate(Session session)

			throws HibernateException, SQLException {
				// String procedureSql = "CALL PROCEDURE(?, ?)";
				Query query = session.createSQLQuery(sql);
				if (filter != null && filter.size() != 0) {
					for (int i = 0; i < filter.size(); i++) {
						query.setParameter(i, filter.get(i));
					}
				}
				return query.executeUpdate();
			}
		});

	}

	public PageQuery find(final PageQuery pageQuery) throws DaoException {
		return (PageQuery) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				try {
					calcPage(session);
					Query query = session.createQuery(pageQuery.getQueryString());
					query.setFirstResult(pageQuery.getFirstResult());
					query.setMaxResults(pageQuery.getMaxResults());
					Object values[] = pageQuery.getQuery().getParaValues().toArray();
					Object types[] = pageQuery.getQuery().getParaTypes().toArray();
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							if (types.length == values.length && types[i] != null) {
								query.setParameter(i, values[i], (Type) types[i]);
							} else
								query.setParameter(i, values[i]);
						}
					}
					// System.err.println("doInHibernate 121231231 count="
					// + query.list().size() + "---"
					// + query.toString());
					pageQuery.setPageList(query.list());
					// System.err
					// .println("doInHibernate 5674565755 count="
					// + query.list().size() + "---"
					// + query.toString());
				} catch (Exception e) {
					System.err.println("page find function getHibernateTemplate().execute has error ");
					e.printStackTrace();
				}
				return pageQuery;
			}

			protected void calcPage(Session session) throws HibernateException {
				Query query = session.createQuery(pageQuery.getCountString());
				Object values[] = pageQuery.getQuery().getParaValues().toArray();
				Object types[] = pageQuery.getQuery().getParaTypes().toArray();
				if (values != null) {
					for (int i = 0; i < values.length; i++) {
						if (types.length == values.length && types[i] != null) {
							query.setParameter(i, values[i], (Type) types[i]);
							// System.out.println(i + "——————" + values[i]
							// + "————————doInHibernate 121231231 count="
							// + types[i].getClass().getName());
						} else
							query.setParameter(i, values[i]);

					}
				}

				int rowSize = 0;
				// System.err.println("calcPage 1111111 count="
				// + query.list().size() + "---"
				// + query.toString());
				List countList = query.list();
				if (countList != null && countList.size() > 0) {
					rowSize = Integer.parseInt(countList.get(0).toString());
					// System.err.println("calcPage count="
					// + query.list().size());
				} else {
					// System.err.println("calcPage count=null");
				}

				pageQuery.setRowSize(rowSize);

				// System.err.println("calcPage 2222222222 count="
				// + query.list().size() + "---"
				// + query.toString());
			}

		});
	}

	public List find(String hql) throws DaoException {
		return getHibernateTemplate().find(hql);
	}

	public List findForResultTransformer(String sql, Class aliasToBeanClass, Map<String, Class> entitys,
			Map<String, Type> scalars) throws DaoException {
		Session session = this.getSession();
		SQLQuery sqlQuery = session.createSQLQuery(sql);

		sqlQuery.setResultTransformer(Transformers.aliasToBean(aliasToBeanClass));
		if (entitys != null && entitys.size() != 0) {
			Set keySet = entitys.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				sqlQuery.addEntity(key, entitys.get(key));
			}
		}
		if (scalars != null && scalars.size() != 0) {
			Set keySet = scalars.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				sqlQuery.addScalar(key, scalars.get(key));
			}
		}
		List recordList = sqlQuery.list();
		this.releaseSession(session);
		return recordList;
	}

	public List find(String hql, Object values[]) throws DaoException {
		return getHibernateTemplate().find(hql, values);
	}

	public int rowsCount(String sql) throws DaoException {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		SQLQuery sqlQuery = session.createSQLQuery(sql);
		int count = Integer.parseInt(sqlQuery.uniqueResult().toString());
		return count;
	}

	@Override
	public int entityCount(String countString, ShQuery query) throws DaoException {
		// TODO Auto-generated method stub
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query hquery = session.createQuery(countString);
		if (query != null) {
			Object values[] = query.getParaValues().toArray();
			Object types[] = query.getParaTypes().toArray();
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					if (types.length == values.length && types[i] != null)
						hquery.setParameter(i, values[i], (Type) types[i]);
					else
						hquery.setParameter(i, values[i]);
				}
			}
		}
		int rowSize = 0;
		List countList = hquery.list();
		if (countList != null && countList.size() > 0) {
			rowSize = Integer.parseInt(countList.get(0).toString());
		}
		session.clear();
		return rowSize;
	}

	public List doQueryName(String queryname, Map filter) {
		String paramNames[] = new String[0];
		Object values[] = new Object[0];
		List list = null;
		if (filter != null && filter.size() != 0) {
			int filterLen = filter.size();
			int i = 0;
			paramNames = new String[filterLen];
			values = new Object[filterLen];
			Set keySet = filter.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				paramNames[i] = key;
				values[i++] = filter.get(key);
			}
			list = this.getHibernateTemplate().findByNamedQueryAndNamedParam(queryname, paramNames, values);
		} else {
			list = this.getHibernateTemplate().findByNamedQuery(queryname);
		}
		return list;
	}

	public List<Map> doSqlQueryToMap(String sql, Map filter, String columnNames[]) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		List<Map> mapList = new ArrayList();
		Session session = this.getSession();
		SQLQuery sqlquery = session.createSQLQuery(sql);

		if (filter != null && filter.size() != 0) {
			Set keySet = filter.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				sqlquery.setParameter(key, filter.get(key));
			}
		}
		int columnLen = columnNames.length;
		for (int i = 0; i < columnLen; i++) {
			sqlquery.addScalar(columnNames[i], Hibernate.STRING);
		}
		sqlquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = sqlquery.list();
		int listLen = list.size();
		for (int i = 0; i < listLen; i++) {
			Map curObject = (Map) list.get(i);
			for (int j = 0; j < columnLen; j++) {
				String value = (String) curObject.get(columnNames[j]);
				if (value == null)
					value = "";
				curObject.put(columnNames[j], value.trim());
			}
			mapList.add(curObject);
		}
		return mapList;
	}

	public List<Map> doQueryNameToMap(String queryname, Map filter, String columnNames[]) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		List<Map> mapList = new ArrayList();
		String sql = HibernateUtils.getNamedHQLQuery(queryname);
		Session session = this.getSession();
		SQLQuery sqlquery = session.createSQLQuery(sql);

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
		sqlquery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		list = sqlquery.list();
		int listLen = list.size();
		for (int i = 0; i < listLen; i++) {
			Map curObject = (Map) list.get(i);
			for (int j = 0; j < columnLen; j++) {
				String value = (String) curObject.get(columnNames[j]);
				if (value == null)
					value = "";
				curObject.put(columnNames[j], value.trim());
			}
			mapList.add(curObject);
		}
		return mapList;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tdhy.dao.ObjectDao#doQueryNameToMap(java.lang.String,
	 * java.util.Map, java.lang.String[], java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Map> doQueryNameToMap(String queryname, Map filter, String[] columnNames, Integer curPage,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
}
