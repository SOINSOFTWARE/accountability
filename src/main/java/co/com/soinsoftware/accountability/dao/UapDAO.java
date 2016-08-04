package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Uap;

/**
 * @author Carlos Rodriguez
 * @since 04/08/2016
 * @version 1.0
 */
public class UapDAO extends AbstractDAO {

	private static final String COLUMN_CODE = "code";

	private static final String COLUMN_LEVEL = "level";

	@SuppressWarnings("unchecked")
	public Set<Uap> select() {
		Set<Uap> uapSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			uapSet = (query.list().isEmpty()) ? null : new HashSet<Uap>(
					query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return uapSet;
	}

	@SuppressWarnings("unchecked")
	public Set<Uap> select(final int level) {
		Set<Uap> uapSet = null;
		try {
			final Query query = this
					.createQuery(this.getSelectStatementLevel());
			query.setParameter(COLUMN_LEVEL, level);
			uapSet = (query.list().isEmpty()) ? null : new HashSet<Uap>(
					query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return uapSet;
	}

	public Uap select(final String code) {
		Uap uap = null;
		try {
			final Query query = this.createQuery(this.getSelectStatementCode());
			query.setParameter(COLUMN_CODE, code);
			uap = (query.list().isEmpty()) ? null : (Uap) query.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return uap;
	}

	public void save(final Uap uap) {
		boolean isNew = (uap.getId() == null) ? true : false;
		this.save(uap, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_UAP);
		return query.toString();
	}

	private String getSelectStatementCode() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_CODE);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_CODE);
		return query.toString();
	}

	private String getSelectStatementLevel() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_LEVEL);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_LEVEL);
		return query.toString();
	}
}