package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.UserDAO;
import co.com.soinsoftware.accountability.entity.User;

/**
 * @author Carlos Rodriguez
 * @since 11/07/2016
 * @version 1.0
 */
public class UserBLL {

	private static UserBLL instance;

	private final UserDAO dao;

	public static UserBLL getInstance() {
		if (instance == null) {
			instance = new UserBLL();
		}
		return instance;
	}

	public Set<User> select() {
		return this.dao.select();
	}

	public User select(final String login, final String password) {
		return this.dao.select(login, password);
	}

	public User select(final long identification) {
		return this.dao.select(identification);
	}

	public User select(final String login) {
		return this.dao.select(login);
	}

	public void save(final User user) {
		this.dao.save(user);
	}

	private UserBLL() {
		super();
		this.dao = new UserDAO();
	}
}