package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.VoucherDAO;
import co.com.soinsoftware.accountability.entity.Voucher;

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

	public void save(final Voucher voucher) {
		this.dao.save(voucher);
	}

	private VoucherBLL() {
		super();
		this.dao = new VoucherDAO();
	}
}