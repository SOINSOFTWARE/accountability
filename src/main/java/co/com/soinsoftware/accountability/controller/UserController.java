package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.RolBLL;
import co.com.soinsoftware.accountability.bll.UserBLL;
import co.com.soinsoftware.accountability.entity.Rol;
import co.com.soinsoftware.accountability.entity.User;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class UserController {

	private final RolBLL rolBLL;

	private final UserBLL userBLL;

	public UserController() {
		super();
		this.rolBLL = RolBLL.getInstance();
		this.userBLL = UserBLL.getInstance();
	}

	public List<Rol> selectRoles() {
		List<Rol> rolList = new ArrayList<>();
		final Set<Rol> rolSet = this.rolBLL.select();
		if (rolSet != null && rolSet.size() > 0) {
			rolList = new ArrayList<>(rolSet);
			Collections.sort(rolList);
		}
		return rolList;
	}

	public List<User> selectUsers() {
		List<User> userList = new ArrayList<>();
		final Set<User> userSet = this.userBLL.select();
		if (userSet != null && userSet.size() > 0) {
			userList = new ArrayList<>(userSet);
			Collections.sort(userList);
		}
		return userList;
	}

	public User saveUser(final long identification, final String name,
			final String lastName, final Rol rol, final String login,
			final String password) {
		final Date currentDate = new Date();
		final User user = new User(rol, identification, name, lastName, login,
				password, currentDate, currentDate, true);
		this.saveUser(user);
		return user;
	}

	public void saveUser(final User user) {
		this.userBLL.save(user);
	}

	public boolean isLoginUsed(final String login) {
		final User user = this.userBLL.select(login);
		return user != null;
	}
}