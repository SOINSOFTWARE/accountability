package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Documenttype;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class DocumentTypeDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Set<Documenttype> select() {
		Set<Documenttype> documentTypeSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			documentTypeSet = (query.list().isEmpty()) ? null
					: new HashSet<Documenttype>(query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return documentTypeSet;
	}

	public Documenttype select(final String name) {
		Documenttype documentType = null;
		try {
			final Query query = this.createQuery(this.getSelectStatementName());
			query.setParameter(COLUMN_NAME, name);
			documentType = (query.list().isEmpty()) ? null
					: (Documenttype) query.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return documentType;
	}

	public void save(final Documenttype documentType) {
		boolean isNew = (documentType.getId() == null) ? true : false;
		this.save(documentType, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_DOCUMENT_TYPE);
		return query.toString();
	}
}