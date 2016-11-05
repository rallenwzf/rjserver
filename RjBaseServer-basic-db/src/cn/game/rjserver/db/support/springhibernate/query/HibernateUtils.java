/*********************************************
    Copyright (c) 2011 by rallen
 *********************************************/

package cn.game.rjserver.db.support.springhibernate.query;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.NamedQueryDefinition;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import cn.game.rjserver.db.support.springhibernate.SpringManager;


/**
 * @author rallen 2011-5-26
 */
public class HibernateUtils {
	public static String sessionFactory_ID = "sessionFactory";

	/*
	 * return queryname HQL
	 */
	public static String getNamedHQLQuery(String queryName) {
		Map mapHQLQuery = getConfiguration().getNamedSQLQueries();
		NamedQueryDefinition namedQueryDefinition = (NamedQueryDefinition) mapHQLQuery
				.get(queryName);
		String strHQLQuery = namedQueryDefinition.getQueryString();
		return strHQLQuery;
	}

	public static Configuration getConfiguration() {
		LocalSessionFactoryBean sessionFactoryBean = (LocalSessionFactoryBean) SpringManager
				.getBean(sessionFactory_ID);
		Configuration conf = sessionFactoryBean.getConfiguration();
		return conf;
	}

	public static SessionFactory getSessionFactory() {
		LocalSessionFactoryBean sessionFactoryBean = (LocalSessionFactoryBean) SpringManager
				.getBean(sessionFactory_ID);
		return sessionFactoryBean.getObject();
	}
}
