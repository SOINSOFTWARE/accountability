package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.AppDAO;
import co.com.soinsoftware.accountability.entity.App;

/**
 * @author Carlos Rodriguez
 * @since 20/09/2016
 * @version 1.0
 */
public class AppBLL {

	private static AppBLL instance;

	private final AppDAO dao;

	public static AppBLL getInstance() {
		if (instance == null) {
			instance = new AppBLL();
		}
		return instance;
	}

	public Set<App> select() {
		return this.dao.select();
	}

	public void save(final App app) {
		this.dao.save(app);
	}

	private AppBLL() {
		super();
		this.dao = new AppDAO();
	}
}