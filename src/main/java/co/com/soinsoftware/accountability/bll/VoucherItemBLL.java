package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.VoucherItemDAO;
import co.com.soinsoftware.accountability.entity.Voucheritem;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherItemBLL {

	private static VoucherItemBLL instance;

	private final VoucherItemDAO dao;

	public static VoucherItemBLL getInstance() {
		if (instance == null) {
			instance = new VoucherItemBLL();
		}
		return instance;
	}

	public Set<Voucheritem> select() {
		return this.dao.select();
	}

	public void save(final Voucheritem voucherItem) {
		this.dao.save(voucherItem);
	}

	private VoucherItemBLL() {
		super();
		this.dao = new VoucherItemDAO();
	}
}