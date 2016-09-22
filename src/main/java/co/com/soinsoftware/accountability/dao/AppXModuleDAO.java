package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Appxmodule;

/**
 * @author Carlos Rodriguez
 * @since 20/09/2016
 * @version 1.0
 */
public class AppXModuleDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Set<Appxmodule> select() {
		Set<Appxmodule> appXModuleSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			appXModuleSet = (query.list().isEmpty()) ? null : new HashSet<Appxmodule>(
					query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return appXModuleSet;
	}

	public void save(final Appxmodule appXModule) {
		boolean isNew = (appXModule.getId() == null) ? true : false;
		this.save(appXModule, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_APP_MODULE);
		return query.toString();
	}
}