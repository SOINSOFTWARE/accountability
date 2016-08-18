package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import co.com.soinsoftware.accountability.entity.Uapxcompany;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class UapXCompanyDAO extends AbstractDAO {

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
}