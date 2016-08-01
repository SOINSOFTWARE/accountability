package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Companytype;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class CompanyTypeDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Set<Companytype> select() {
		Set<Companytype> companyTypeSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			companyTypeSet = (query.list().isEmpty()) ? null
					: new HashSet<Companytype>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return companyTypeSet;
	}

	public Companytype select(final String name) {
		Companytype companyType = null;
		try {
			final Query query = this.createQuery(this.getSelectStatementName());
			query.setParameter(COLUMN_NAME, name);
			companyType = (query.list().isEmpty()) ? null : (Companytype) query
					.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return companyType;
	}

	public void save(final Companytype companyType) {
		boolean isNew = (companyType.getId() == null) ? true : false;
		this.save(companyType, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_COMPANY_TYPE);
		return query.toString();
	}
}