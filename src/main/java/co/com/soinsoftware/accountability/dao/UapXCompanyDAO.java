package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Uapxcompany;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class UapXCompanyDAO extends AbstractDAO {

	private static final String COLUMN_COMPANY = "company";

	@SuppressWarnings("unchecked")
	public Set<Uapxcompany> select() {
		Set<Uapxcompany> uapXCompanySet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			uapXCompanySet = (query.list().isEmpty()) ? null
					: new HashSet<Uapxcompany>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return uapXCompanySet;
	}

	@SuppressWarnings("unchecked")
	public Set<Uapxcompany> select(final Company company) {
		Set<Uapxcompany> uapXCompanySet = null;
		try {
			final Query query = this
					.createQuery(this.getSelectStatementCompany());
			query.setParameter(COLUMN_COMPANY, company);
			uapXCompanySet = (query.list().isEmpty()) ? null
					: new HashSet<Uapxcompany>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return uapXCompanySet;
	}

	public void save(final Uapxcompany uapXCompany) {
		boolean isNew = (uapXCompany.getId() == null) ? true : false;
		this.save(uapXCompany, isNew);
	}

	public void save(final Set<Uapxcompany> uapXCompanySet) {
		final SessionController controller = SessionController.getInstance();
		final Session session = controller.openSession();
		for (final Uapxcompany uapXComp : uapXCompanySet) {
			session.save(uapXComp);
		}
		session.getTransaction().commit();
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_UAP_COMPANY);
		return query.toString();
	}

	private String getSelectStatementCompany() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_COMPANY);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_COMPANY);
		return query.toString();
	}
}