package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.CompanyTypeDAO;
import co.com.soinsoftware.accountability.entity.Companytype;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class CompanyTypeBLL {
	
	private static CompanyTypeBLL instance;

	private final CompanyTypeDAO dao;

	public static CompanyTypeBLL getInstance() {
		if (instance == null) {
			instance = new CompanyTypeBLL();
		}
		return instance;
	}

	public Set<Companytype> select() {
		return this.dao.select();
	}

	public Companytype select(final String name) {
		return this.dao.select(name);
	}

	public void save(final Companytype companyType) {
		this.dao.save(companyType);
	}

	private CompanyTypeBLL() {
		super();
		this.dao = new CompanyTypeDAO();
	}
}