package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.VoucherBLL;
import co.com.soinsoftware.accountability.bll.VoucherItemBLL;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Voucheritem;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherController {

	private final VoucherBLL voucherBLL;

	private final VoucherItemBLL voucherItemBLL;

	public VoucherController() {
		super();
		this.voucherBLL = VoucherBLL.getInstance();
		this.voucherItemBLL = VoucherItemBLL.getInstance();
	}

	public List<Voucher> select(final int year, final int month,
			final Company company, final Vouchertypexcompany voucherTypeXComp) {
		List<Voucher> voucherList = new ArrayList<>();
		final Set<Voucher> voucherSet = this.voucherBLL.select(year, month,
				company, voucherTypeXComp);
		if (voucherSet != null) {
			voucherList = new ArrayList<>(voucherSet);
			if (voucherList.size() > 0) {
				Collections.sort(voucherList);
			}
		}
		return voucherList;
	}

	public Voucher saveVoucher(final Vouchertypexcompany voucherTypeXComp,
			final Date voucherDate, final Set<Voucheritem> voucherItemSet) {
		final Date currentDate = new Date();
		final long voucherNumber = voucherTypeXComp.getNumbercurrent();
		final Voucher voucher = new Voucher(voucherTypeXComp, voucherNumber,
				voucherDate, currentDate, currentDate, true);
		this.saveVoucher(voucher);
		this.saveVoucherItems(voucher, voucherItemSet);
		return voucher;
	}

	public void saveVoucher(final Voucher voucher) {
		this.voucherBLL.save(voucher);
	}

	public void saveVoucherItems(final Voucher voucher,
			final Set<Voucheritem> voucherItemSet) {
		final Date currentDate = new Date();
		for (final Voucheritem voucherItem : voucherItemSet) {
			voucherItem.setVoucher(voucher);
			voucherItem.setCreation(currentDate);
			voucherItem.setUpdated(currentDate);
			this.saveVoucherItem(voucherItem);
		}
		voucher.setVoucheritems(voucherItemSet);
	}

	public void saveVoucherItem(final Voucheritem voucherItem) {
		this.voucherItemBLL.save(voucherItem);
	}
}