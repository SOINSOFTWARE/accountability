package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.Rol;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class RolDAO extends AbstractDAO {

	@SuppressWarnings("unchecked")
	public Set<Rol> select() {
		Set<Rol> rolSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementEnabled());
			rolSet = (query.list().isEmpty()) ? null : new HashSet<Rol>(
					query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return rolSet;
	}

	public Rol select(final String name) {
		Rol rol = null;
		try {
			final Query query = this.createQuery(this.getSelectStatementName());
			query.setParameter(COLUMN_NAME, name);
			rol = (query.list().isEmpty()) ? null : (Rol) query.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return rol;
	}

	public void save(final Rol rol) {
		boolean isNew = (rol.getId() == null) ? true : false;
		this.save(rol, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_ROL);
		return query.toString();
	}
}