package co.com.soinsoftware.accountability.dao;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import co.com.soinsoftware.accountability.entity.User;

/**
 * @author Carlos Rodriguez
 * @since 11/07/2016
 * @version 1.0
 */
public class UserDAO extends AbstractDAO {

	private static final String COLUMN_IDENTIFICATION = "identification";

	private static final String COLUMN_LOGIN = "login";

	private static final String COLUMN_PASS = "password";

	@SuppressWarnings("unchecked")
	public Set<User> select() {
		Set<User> userSet = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementNoFirst());
			userSet = (query.list().isEmpty()) ? null : new HashSet<User>(
					query.list());
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return userSet;
	}

	public User select(final String login, final String password) {
		User user = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementLoginAndPass());
			query.setParameter(COLUMN_LOGIN, login);
			query.setParameter(COLUMN_PASS, password);
			user = (query.list().isEmpty()) ? null : (User) query.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return user;
	}

	public User select(final long identification) {
		User user = null;
		try {
			final Query query = this.createQuery(this
					.getSelectStatementIdentification());
			query.setParameter(COLUMN_IDENTIFICATION, identification);
			user = (query.list().isEmpty()) ? null : (User) query.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return user;
	}

	public User select(final String login) {
		User user = null;
		try {
			final Query query = this
					.createQuery(this.getSelectStatementLogin());
			query.setParameter(COLUMN_LOGIN, login);
			user = (query.list().isEmpty()) ? null : (User) query.list().get(0);
		} catch (HibernateException ex) {
			System.out.println(ex);
		}
		return user;
	}

	public void save(final User user) {
		boolean isNew = (user.getId() == null) ? true : false;
		this.save(user, isNew);
	}

	@Override
	protected String getSelectStatement() {
		final StringBuilder query = new StringBuilder();
		query.append(SQL_FROM);
		query.append(TABLE_USER);
		return query.toString();
	}

	private String getSelectStatementLoginAndPass() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_LOGIN);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_LOGIN);
		query.append(SQL_AND);
		query.append(COLUMN_PASS);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_PASS);
		return query.toString();
	}

	private String getSelectStatementIdentification() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_IDENTIFICATION);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_IDENTIFICATION);
		return query.toString();
	}

	private String getSelectStatementLogin() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_LOGIN);
		query.append(SQL_EQUALS_WITH_PARAM);
		query.append(COLUMN_LOGIN);
		return query.toString();
	}

	private String getSelectStatementNoFirst() {
		final StringBuilder query = new StringBuilder(
				this.getSelectStatementEnabled());
		query.append(SQL_AND);
		query.append(COLUMN_ID);
		query.append(" <> 1 ");
		return query.toString();
	}
}