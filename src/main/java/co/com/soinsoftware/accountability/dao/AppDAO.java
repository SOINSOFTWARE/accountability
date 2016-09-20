package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.App;

/**
 * @author Carlos Rodriguez
 * @since 20/09/2016
 * @version 1.0
 */
public class AppDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Set<App> select() {
		Set<App> appSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			appSet = (query.list().isEmpty()) ? null : new HashSet<App>(
					query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return appSet;
	}

	public void save(final App app) {
		boolean isNew = (app.getId() == null) ? true : false;
		this.save(app, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_APP);
		return query.toString();
	}
}