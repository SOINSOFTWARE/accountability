package co.com.soinsoftware.accountability.controller;

import co.com.soinsoftware.accountability.entity.Rol;
import co.com.soinsoftware.accountability.entity.User;

/**
 * @author Carlos Rodriguez
 * @since 30/08/2016
 * @version 1.0
 */
public class RoleController {

	private static final String ADMIN_ROL = "ADMIN";

	private static final String ACCOUNT_ROL = "CONT";

	private static final String AUX_ROL = "AUX";

	private static RoleController instance;

	private RoleController() {
		super();
	}

	public static RoleController getInstance() {
		if (instance == null) {
			instance = new RoleController();
		}
		return instance;
	}

	public boolean isAdminRol(final User loggedUser) {
		return this.isRol(loggedUser, ADMIN_ROL);
	}

	public boolean isAccountRol(final User loggedUser) {
		return this.isRol(loggedUser, ACCOUNT_ROL);
	}

	public boolean isAuxRol(final User loggedUser) {
		return this.isRol(loggedUser, AUX_ROL);
	}

	private boolean isRol(final User loggedUser, final String code) {
		final Rol rol = loggedUser.getRol();
		return rol.getCode().equals(code);
	}
}