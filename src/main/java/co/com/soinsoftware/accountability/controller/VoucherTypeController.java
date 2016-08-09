package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.VoucherTypeBLL;
import co.com.soinsoftware.accountability.entity.Vouchertype;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class VoucherTypeController {

	private final VoucherTypeBLL voucherTypeBLL;

	public VoucherTypeController() {
		super();
		this.voucherTypeBLL = VoucherTypeBLL.getInstance();
	}

	public List<Vouchertype> selectVoucherTypes() {
		List<Vouchertype> voucherTypeList = new ArrayList<>();
		final Set<Vouchertype> voucherTypeSet = this.voucherTypeBLL.select();
		if (voucherTypeSet != null) {
			voucherTypeList = new ArrayList<>(voucherTypeSet);
			if (voucherTypeList.size() > 0) {
				Collections.sort(voucherTypeList);
			}
		}
		return voucherTypeList;
	}

	public Vouchertype selectVoucherType(final String code) {
		return this.voucherTypeBLL.select(code);
	}

	public Vouchertype saveVoucherType(final String code, final String name) {
		final Date currentDate = new Date();
		final Vouchertype voucherType = new Vouchertype(code, name,
				currentDate, currentDate, true);
		this.saveVoucherType(voucherType);
		return voucherType;
	}

	public void saveVoucherType(final Vouchertype voucherType) {
		this.voucherTypeBLL.save(voucherType);
	}
}