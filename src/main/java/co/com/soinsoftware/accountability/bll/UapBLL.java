package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.UapDAO;
import co.com.soinsoftware.accountability.entity.Uap;

/**
 * @author Carlos Rodriguez
 * @since 04/08/2016
 * @version 1.0
 */
public class UapBLL {

	private static UapBLL instance;

	private final UapDAO dao;

	public static UapBLL getInstance() {
		if (instance == null) {
			instance = new UapBLL();
		}
		return instance;
	}

	public Set<Uap> select() {
		return this.dao.select();
	}

	public Set<Uap> select(final int level) {
		return this.dao.select(level);
	}

	public Uap select(final String code) {
		return this.dao.select(code);
	}

	public void save(final Uap uap) {
		this.dao.save(uap);
	}

	private UapBLL() {
		super();
		this.dao = new UapDAO();
	}
}