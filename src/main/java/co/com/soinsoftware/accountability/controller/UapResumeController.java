package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Report;
import co.com.soinsoftware.accountability.entity.ReportItem;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Voucheritem;

public class UapResumeController extends AbstractReportController {

	private final VoucherController voucherController;

	public UapResumeController() {
		super();
		this.voucherController = new VoucherController();
	}

	public List<Voucheritem> selectAccountUapCurrentState(
			final Company company, final int year, final int month) {
		final Set<Voucheritem> accountVoucherItemSet = new HashSet<>();
		final List<Voucher> voucherList = this.voucherController.select(year,
				month, company, null);
		if (voucherList != null && voucherList.size() > 0) {
			for (final Voucher voucher : voucherList) {
				final Set<Voucheritem> voucherItemSet = voucher
						.getVoucheritems();
				for (final Voucheritem voucherItem : voucherItemSet) {
					final Voucheritem accountItem = this
							.buildAccountVoucherItem(voucherItem);
					this.addVoucherItemToSet(accountVoucherItemSet, accountItem);
				}
			}
		}
		return this.sortVoucherItemSet(accountVoucherItemSet);
	}

	public List<Voucheritem> selectGroupUapCurrentState(
			final List<Voucheritem> accountVoucherItemList) {
		final Set<Voucheritem> groupVoucherItemSet = new HashSet<>();
		for (final Voucheritem voucherItem : accountVoucherItemList) {
			final Voucheritem groupItem = this
					.buildGroupVoucherItem(voucherItem);
			this.addVoucherItemToSet(groupVoucherItemSet, groupItem);
		}
		return this.sortVoucherItemSet(groupVoucherItemSet);
	}

	public List<Voucheritem> selectClassUapCurrentState(
			final List<Voucheritem> groupVoucherItemList) {
		final Set<Voucheritem> classVoucherItemSet = new HashSet<>();
		for (final Voucheritem voucherItem : groupVoucherItemList) {
			final Voucheritem groupItem = this
					.buildClassVoucherItem(voucherItem);
			this.addVoucherItemToSet(classVoucherItemSet, groupItem);
		}
		return this.sortVoucherItemSet(classVoucherItemSet);
	}

	@Override
	public Report buildReport(final Company company,
			final List<Voucher> voucherList, final String description) {
		return null;
	}

	@Override
	protected void addMissingReportItems(final Set<ReportItem> itemSet) {
	}

	private Voucheritem buildAccountVoucherItem(final Voucheritem voucherItem) {
		final Uap uap = voucherItem.getUap();
		final Uap accountUap = this.getAccountUap(uap);
		return this.buildVoucherItem(voucherItem, accountUap);
	}

	private Voucheritem buildGroupVoucherItem(final Voucheritem voucherItem) {
		final Uap uap = voucherItem.getUap();
		final Uap groupUap = uap.getUap();
		return this.buildVoucherItem(voucherItem, groupUap);
	}

	private Voucheritem buildClassVoucherItem(final Voucheritem voucherItem) {
		final Uap uap = voucherItem.getUap();
		final Uap classUap = uap.getUap();
		return this.buildVoucherItem(voucherItem, classUap);
	}

	private Voucheritem buildVoucherItem(final Voucheritem voucherItem,
			final Uap uap) {
		final Voucher voucher = voucherItem.getVoucher();
		final long debtValue = voucherItem.getDebtvalue();
		final long creditValue = voucherItem.getCreditvalue();
		final Date creation = voucherItem.getCreation();
		final Date updated = voucherItem.getUpdated();
		final boolean enabled = voucherItem.isEnabled();
		return new Voucheritem(uap, voucher, debtValue, creditValue, creation,
				updated, enabled);
	}

	private void addVoucherItemToSet(final Set<Voucheritem> voucherItemSet,
			final Voucheritem item) {
		boolean found = false;
		for (final Voucheritem itemInSet : voucherItemSet) {
			final Uap uap = item.getUap();
			final Uap uapInSet = itemInSet.getUap();
			if (uap.getCode() == uapInSet.getCode()) {
				found = true;
				final long newItemDebtValue = item.getDebtvalue();
				final long newItemCreditValue = item.getCreditvalue();
				final long oldItemDebtValue = itemInSet.getDebtvalue();
				final long oldItemCreditValue = itemInSet.getCreditvalue();
				itemInSet.setDebtvalue(oldItemDebtValue + newItemDebtValue);
				itemInSet.setCreditvalue(oldItemCreditValue
						+ newItemCreditValue);
				break;
			}

		}
		if (!found) {
			voucherItemSet.add(item);
		}
	}

	private List<Voucheritem> sortVoucherItemSet(
			final Set<Voucheritem> voucherItemSet) {
		List<Voucheritem> voucherItemList = new ArrayList<>();
		if (voucherItemSet != null && voucherItemSet.size() > 0) {
			voucherItemList = new ArrayList<>(voucherItemSet);
			Collections.sort(voucherItemList);
		}
		return voucherItemList;
	}
}