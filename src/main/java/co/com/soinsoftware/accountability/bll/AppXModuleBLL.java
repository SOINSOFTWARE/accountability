package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.AppXModuleDAO;
import co.com.soinsoftware.accountability.entity.Appxmodule;

/**
 * @author Carlos Rodriguez
 * @since 20/09/2016
 * @version 1.0
 */
public class AppXModuleBLL {

	private static AppXModuleBLL instance;

	private final AppXModuleDAO dao;

	public static AppXModuleBLL getInstance() {
		if (instance == null) {
			instance = new AppXModuleBLL();
		}
		return instance;
	}

	public Set<Appxmodule> select() {
		return this.dao.select();
	}

	public void save(final Appxmodule appXModule) {
		this.dao.save(appXModule);
	}

	private AppXModuleBLL() {
		super();
		this.dao = new AppXModuleDAO();
	}
}