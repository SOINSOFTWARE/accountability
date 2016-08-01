package co.com.soinsoftware.accountability.bll;

import co.com.soinsoftware.accountability.dao.UserDAO;
import co.com.soinsoftware.accountability.entity.User;
import java.math.BigDecimal;

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
	
	public User select(final String login, final String password) {
		return this.dao.select(login, password);
	}
	
	public User select(final long identification) {
		return this.dao.select(identification);
	}
	
	public void save(final User user) {
		this.dao.save(user);
	}
	
	public BigDecimal selectDebt(final User client) {
		final Integer idUser = (client == null) ? null : client.getId();
		return this.dao.selectDebt(idUser);
	}
	
	private UserBLL() {
		super();
		this.dao = new UserDAO();
	}
}