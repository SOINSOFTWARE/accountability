package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.UapXCompanyDAO;
import co.com.soinsoftware.accountability.entity.Uapxcompany;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class UapXCompanyBLL {

	private static UapXCompanyBLL instance;

	private final UapXCompanyDAO dao;

	public static UapXCompanyBLL getInstance() {
		if (instance == null) {
			instance = new UapXCompanyBLL();
		}
		return instance;
	}

	public Set<Uapxcompany> select() {
		return this.dao.select();
	}

	public void save(final Uapxcompany uapXCompany) {
		this.dao.save(uapXCompany);
	}

	public void save(final Set<Uapxcompany> uapXCompanySet) {
		this.dao.save(uapXCompanySet);
	}

	private UapXCompanyBLL() {
		super();
		this.dao = new UapXCompanyDAO();
	}
}