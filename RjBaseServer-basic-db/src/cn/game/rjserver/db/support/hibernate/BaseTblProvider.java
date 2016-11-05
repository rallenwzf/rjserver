/*********************************************
    Copyright (c) 2009 by rayjar.net
 *********************************************/

/**
 * RAYJAR_POMS 
 * 2009-10-30
 * com.rajar.dbcomm.db.BaseDAO.java
 */
package cn.game.rjserver.db.support.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.NamedQueryDefinition;
import org.hibernate.exception.ConstraintViolationException;

/**
 * 
 * @author rajar.net wzf<br/>
 *         class explain: <br/>
 *         2010-4-2
 */
public class BaseTblProvider extends AbstractTblProvider {
	private static BaseTblProvider baseProvide;

	protected BaseTblProvider() {
	}

	public static synchronized BaseTblProvider getInstance() {
		if (baseProvide == null) {
			baseProvide = new BaseTblProvider();
		}
		return baseProvide;
	}

	@Override
	public void closeSession(Session session) {
		// TODO Auto-generated method stub
		MySessionFactory.closeSession(session);
	}

	@Override
	public Session getSession() {
		// TODO Auto-generated method stub
		return MySessionFactory.currentSession();
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return MySessionFactory.getConfiguration();
	}
}
