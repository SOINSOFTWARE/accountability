package co.com.soinsoftware.accountability.controller;

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

public class BalanceReportController extends AbstractReportController {

	private static final String REPORT_NAME = "BALANCE GENERAL";

	private List<Uap> uapList;

	public BalanceReportController() {
		super();
	}

	@Override
	public Report buildReport(final Company company,
			final List<Voucher> voucherList, final String description) {
		this.uapList = new UapController().selectUapClassLevel(company);
		final Set<ReportItem> accountReportItemSet = new HashSet<>();
		for (final Voucher voucher : voucherList) {
			final Set<Voucheritem> voucherItemSet = voucher.getVoucheritems();
			for (final Voucheritem voucherItem : voucherItemSet) {
				final Uap uap = voucherItem.getUap();
				if (this.isBalanceReportClassUap(uap)) {
					final Uap accountUap = this.getAccountUap(uap);
					final long value = this.calculateItemValue(voucherItem);
					final ReportItem item = this.getNewReportItem(accountUap,
							value);
					this.addReportItemToSet(accountReportItemSet, item);
				}
			}
		}
		final Set<ReportItem> reportItemSet = this
				.addClassUapToReportItemSet(accountReportItemSet);
		return new Report(company, REPORT_NAME, description, reportItemSet);
	}

	@Override
	protected void addMissingReportItems(final Set<ReportItem> itemSet) {
		final ReportItem itemActivo = this.getReportItemActivo();
		final ReportItem itemPasivo = this.getReportItemPasivo();
		final ReportItem itemPatrimonio = this.getReportItemPatrimonio();
		if (!itemSet.contains(itemActivo)) {
			itemSet.add(itemActivo);
			final ReportItem itemWithTotal = this.buildTotalClassUap(
					itemActivo.getUap(), 0);
			itemSet.add(itemWithTotal);
		}
		if (!itemSet.contains(itemPasivo)) {
			itemSet.add(itemPasivo);
			final ReportItem itemWithTotal = this.buildTotalClassUap(
					itemPasivo.getUap(), 0);
			itemSet.add(itemWithTotal);
		}
		if (!itemSet.contains(itemPatrimonio)) {
			itemSet.add(itemPatrimonio);
			final ReportItem itemWithTotal = this.buildTotalClassUap(
					itemPatrimonio.getUap(), 0);
			itemSet.add(itemWithTotal);
		}
	}

	private boolean isBalanceReportClassUap(final Uap uap) {
		final Uap classUap = this.getClassUap(uap);
		return (classUap.getCode() == CLASS_ACTIVO
				|| classUap.getCode() == CLASS_PASIVO || classUap.getCode() == CLASS_PATRIMONIO);
	}

	private Set<ReportItem> addGroupUapToReportItemSet(
			final Set<ReportItem> reportItemSet) {
		final Set<ReportItem> groupReportItemSet = new HashSet<>();
		if (reportItemSet != null && reportItemSet.size() > 0) {
			for (final ReportItem reportItem : reportItemSet) {
				final Uap groupUap = reportItem.getUap().getUap();
				final long value = reportItem.getValue();
				final ReportItem item = this.getNewReportItem(groupUap, value);
				item.getReportItemSet().add(reportItem);
				this.addReportItemToSet(groupReportItemSet, item);
			}
		}
		return groupReportItemSet;
	}

	private Set<ReportItem> addClassUapToReportItemSet(
			final Set<ReportItem> reportItemSet) {
		final Set<ReportItem> groupReportItemSet = this
				.addGroupUapToReportItemSet(reportItemSet);
		final Set<ReportItem> classReportItemSet = new HashSet<>();
		if (groupReportItemSet != null && groupReportItemSet.size() > 0) {
			for (final ReportItem reportItem : groupReportItemSet) {
				final Uap classUap = reportItem.getUap().getUap();
				final long value = reportItem.getValue();
				final ReportItem item = this.getNewReportItem(classUap, null);
				final ReportItem itemWithTotal = this.buildTotalClassUap(
						classUap, value);
				item.getReportItemSet().add(reportItem);
				this.addReportItemToSet(classReportItemSet, item);
				this.addReportItemToSet(classReportItemSet, itemWithTotal);
			}
		}
		this.addMissingReportItems(classReportItemSet);
		return classReportItemSet;
	}

	private ReportItem getReportItemActivo() {
		for (final Uap uap : this.uapList) {
			if (uap.getCode() == CLASS_ACTIVO) {
				return this.getNewReportItem(uap, null);
			}
		}
		return null;
	}

	private ReportItem getReportItemPasivo() {
		for (final Uap uap : this.uapList) {
			if (uap.getCode() == CLASS_PASIVO) {
				return this.getNewReportItem(uap, null);
			}
		}
		return null;
	}

	private ReportItem getReportItemPatrimonio() {
		for (final Uap uap : this.uapList) {
			if (uap.getCode() == CLASS_PATRIMONIO) {
				return this.getNewReportItem(uap, null);
			}
		}
		return null;
	}

	private ReportItem buildTotalClassUap(final Uap classUap, final long value) {
		final Uap uap = new Uap(classUap.getCode(), "TOTAL "
				+ classUap.getName(), 1, classUap.isDebt(),
				classUap.isCredit(), false, new Date(), new Date(), true);
		final String name = uap.getName();
		final long code = classUap.getCode();
		return new ReportItem(uap, name, value, code);
	}
}