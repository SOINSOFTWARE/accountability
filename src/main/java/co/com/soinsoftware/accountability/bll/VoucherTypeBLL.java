package co.com.soinsoftware.accountability.bll;

import java.util.Set;

import co.com.soinsoftware.accountability.dao.VoucherTypeDAO;
import co.com.soinsoftware.accountability.entity.Vouchertype;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class VoucherTypeBLL {

	private static VoucherTypeBLL instance;

	private final VoucherTypeDAO dao;

	public static VoucherTypeBLL getInstance() {
		if (instance == null) {
			instance = new VoucherTypeBLL();
		}
		return instance;
	}

	public Set<Vouchertype> select() {
		return this.dao.select();
	}

	public Vouchertype select(final String code) {
		return this.dao.select(code);
	}

	public void save(final Vouchertype voucherType) {
		this.dao.save(voucherType);
	}

	private VoucherTypeBLL() {
		super();
		this.dao = new VoucherTypeDAO();
	}
}