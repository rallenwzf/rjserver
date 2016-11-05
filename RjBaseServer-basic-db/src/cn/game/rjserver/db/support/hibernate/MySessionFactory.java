/*********************************************
 * Copyright (c) 2009 by
 *********************************************/
/**
 * RAYJAR_POMS
 * 2009-11-2
 */
package cn.game.rjserver.db.support.hibernate;

import java.io.File;
import java.net.URL;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.DefaultNamingStrategy;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class MySessionFactory {

	static Logger logger = Logger.getLogger(MySessionFactory.class.getName());
	/**
	 * Location of hibernate.cfg.xml file. Location should be on the classpath
	 * as Hibernate uses #resourceAsStream style lookup for its configuration
	 * file. The default classpath location of the hibernate config file is in
	 * the default package. Use #setConfigFile() to update the location of the
	 * configuration file for the current session.
	 */
	// private static String CONFIG_FILE_LOCATION = "E:\\hibernate.cfg.xml";
	// private static URL CONFIG_FILE_URL = null;
	private static String CONFIG_FILE_LOCATION = System.getProperty("user.dir")
			+ "/file.config/hibernate.cfg.xml";

	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static Configuration configuration = new Configuration();
	private static String configFile = null;
	private static SessionFactory sessionFactory = null;

	private MySessionFactory() {

	}

	/**
	 * Returns the ThreadLocal Session instance. Lazy initialize the
	 * <code>SessionFactory</code> if needed.
	 * 
	 * @return Session
	 * @throws HibernateException
	 */
	public static synchronized Session currentSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		// System.out.println(configuration.getProperty("dialect"));
		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				rebuildSessionFactory();
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}

		return session;
	}

	/**
	 * Rebuild hibernate session factory
	 */
	public static synchronized void rebuildSessionFactory() {
		try {
			// getConfiguration().configure(new File(configFile));
			// System.out.println("--------------"
			// + getConfiguration().getProperty("connection.url"));
			sessionFactory = getConfiguration().buildSessionFactory();
		} catch (Exception e) {
			logger.error("Error Creating SessionFactory:" + e);
		}
	}

	/**
	 * Close the single hibernate session instance.
	 * 
	 * @throws HibernateException
	 */
	public static synchronized void closeSession() throws HibernateException {
		Session session = (Session) threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}

	/**
	 * containing a (POMSSessionFactory)'s closeSession method
	 * 
	 * @param s
	 * @throws HibernateException
	 *             void
	 */
	public static synchronized void closeSession(Session s) throws HibernateException {
		if (s != null) {
			s.close();
		}
	}

	/**
	 * return session factory
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			rebuildSessionFactory();
		}
		return sessionFactory;
	}

	public static synchronized void setSessionFactory(SessionFactory s) {
		sessionFactory = s;
	}

	/**
	 * return session factory
	 * session factory will be rebuilded in the next call
	 */
	public static synchronized void setConfigFile(String configFile) throws Exception {
		MySessionFactory.configFile = configFile;
		sessionFactory = null;
		configuration = new Configuration();
		getConfiguration().configure(new File(MySessionFactory.configFile));
		// rebuildSessionFactory();
	}

	/**
	 * return session factory
	 * session factory will be rebuilded in the next call
	 */
	public static synchronized void setConfigFile(URL url) {
		MySessionFactory.configFile = url.toString();
		sessionFactory = null;
		configuration = new Configuration();
		getConfiguration().configure(new File(MySessionFactory.configFile));
		// rebuildSessionFactory();
	}

	/**
	 * return hibernate configuration
	 */
	public static Configuration getConfiguration() {
		if (configFile == null) {
			configFile = CONFIG_FILE_LOCATION;
			configuration.configure(new File(MySessionFactory.configFile));
		}
		return configuration;
	}

	public static synchronized void setConfiguration(Configuration c) {
		configFile = "Configuration";
		configuration = c;
	}

	public static void setNamingStrategy(DefaultNamingStrategy ns) {
		getConfiguration().setNamingStrategy(ns);
	}

	/**
	 * containing a (POMSSessionFactory)'s setConfigurationProptise method
	 * 
	 * @param propertyName
	 * @param value
	 *            void
	 */
	public static synchronized void setConfigurationProptise(String propertyName, String value) {
		getConfiguration().setProperty(propertyName, value);
	}
}