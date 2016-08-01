package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.RolDAO;
import co.com.soinsoftware.accountability.entity.Rol;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class RolBLL {

	private static RolBLL instance;

	private final RolDAO dao;

	public static RolBLL getInstance() {
		if (instance == null) {
			instance = new RolBLL();
		}
		return instance;
	}

	public Set<Rol> select() {
		return this.dao.select();
	}

	public Rol select(final String name) {
		return this.dao.select(name);
	}

	public void save(final Rol rol) {
		this.dao.save(rol);
	}

	private RolBLL() {
		super();
		this.dao = new RolDAO();
	}
}