package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.VoucherDAO;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherBLL {

	private static VoucherBLL instance;

	private final VoucherDAO dao;

	public static VoucherBLL getInstance() {
		if (instance == null) {
			instance = new VoucherBLL();
		}
		return instance;
	}

	public Set<Voucher> select() {
		return this.dao.select();
	}

	public Set<Voucher> select(final int year, final int month,
			final Company company, final Vouchertypexcompany voucherTypeXComp) {
		Set<Voucher> voucherSet = null;
		if (voucherTypeXComp == null) {
			if (month > -1) {
				voucherSet = this.dao.select(year, month, company);
			} else {
				voucherSet = this.dao.select(year, company);
			}
		} else {
			voucherSet = this.dao.select(year, month, voucherTypeXComp);
		}
		return voucherSet;
	}

	public void save(final Voucher voucher) {
		this.dao.save(voucher);
	}

	private VoucherBLL() {
		super();
		this.dao = new VoucherDAO();
	}
}