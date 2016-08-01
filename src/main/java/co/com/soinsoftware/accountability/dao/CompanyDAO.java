package co.com.soinsoftware.accountability.dao;

import co.com.soinsoftware.accountability.entity.Company;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class CompanyDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Set<Company> select() {
		Set<Company> companySet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			companySet = (query.list().isEmpty()) ? null
					: new HashSet<Company>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return companySet;
	}

	public Company select(final String name) {
		Company company = null;
		try {
			final Query query = this.createQuery(this.getSelectStatementName());
			query.setParameter(COLUMN_NAME, name);
			company = (query.list().isEmpty()) ? null : (Company) query.list()
					.get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return company;
	}

	public void save(final Company company) {
		boolean isNew = (company.getId() == null) ? true : false;
		this.save(company, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_COMPANY);
		return query.toString();
	}
}