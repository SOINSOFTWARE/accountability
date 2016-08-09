package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.VoucherTypeXCompanyDAO;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Vouchertype;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class VoucherTypeXCompanyBLL {

	private static VoucherTypeXCompanyBLL instance;

	private final VoucherTypeXCompanyDAO dao;

	public static VoucherTypeXCompanyBLL getInstance() {
		if (instance == null) {
			instance = new VoucherTypeXCompanyBLL();
		}
		return instance;
	}

	public Set<Vouchertypexcompany> select() {
		return this.dao.select();
	}

	public Vouchertypexcompany select(final Vouchertype voucherType,
			final Company company) {
		return this.dao.select(voucherType, company);
	}

	public void save(final Vouchertypexcompany voucherTypeXComp) {
		this.dao.save(voucherTypeXComp);
	}

	private VoucherTypeXCompanyBLL() {
		super();
		this.dao = new VoucherTypeXCompanyDAO();
	}
}