package co.com.soinsoftware.accountability.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.UapBLL;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Report;
import co.com.soinsoftware.accountability.entity.ReportItem;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Voucheritem;
import co.com.soinsoftware.accountability.view.JFBalance;

public class BalanceReportController {

	public static final long CLASS_ACTIVO = 1;

	public static final long CLASS_PASIVO = 2;

	public static final long CLASS_PATRIMONIO = 3;

	private static final String REPORT_BALANCE = "BALANCE GENERAL";

	private static final int LEVEL_ACCOUNT = 3;

	private static final int LEVEL_SUBACCOUNT = 4;

	private static final int LEVEL_EMP_HELPER = 5;

	private final UapBLL uapBLL;

	private final Set<Uap> uapSet;

	public BalanceReportController() {
		super();
		this.uapBLL = UapBLL.getInstance();
		uapSet = this.uapBLL.select(1);
	}

	public Report buildBalance(final Company company,
			final List<Voucher> voucherList, final String description) {
		final Set<ReportItem> accountReportItemSet = new HashSet<>();
		for (final Voucher voucher : voucherList) {
			final Set<Voucheritem> voucherItemSet = voucher.getVoucheritems();
			for (final Voucheritem voucherItem : voucherItemSet) {
				final Uap uap = voucherItem.getUap();
				if (this.isBalanceReportClassUap(uap)) {
					final Uap accountUap = this.getAccountUap(uap);
					final long value = this.calculateItemValue(voucherItem);
					final ReportItem item = new ReportItem(accountUap, value,
							accountUap.getCode());
					this.addReportItemToSet(accountReportItemSet, item);
				}
			}
		}
		final Set<ReportItem> reportItemSet = this
				.addClassUapToReportItemSet(accountReportItemSet);
		return new Report(company, REPORT_BALANCE, description, reportItemSet);
	}

	public long getClassValue(final Report balanceReport,
			final long balanceClass) {
		long value = 0;
		for (final ReportItem reportItem : balanceReport.getReportItemSet()) {
			if (reportItem.getCode() == balanceClass
					&& reportItem.getValue() != null) {
				value = reportItem.getValue();
				break;
			}
		}
		return value;
	}

	public String getBalanceReportDateDescription(final String range,
			final int year, final int month, final String monthName) {
		String description = "";
		if (range == JFBalance.RANGE_YEAR) {
			description = this.getDateDescriptionByYear(year);
		} else {
			description = this
					.getDateDescriptionByMonth(year, month, monthName);
		}
		return description;
	}

	private String getDateDescriptionByYear(final int year) {
		final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd");
		final StringBuilder builder = new StringBuilder();
		String description = "";
		if (currentYear == year) {
			final Date currentDate = new Date();
			builder.append("A ");
			builder.append(dateFormat.format(currentDate));
			builder.append(" del ");
			builder.append(year);
			description = builder.toString();
		} else if (currentYear > year) {
			builder.append("A diciembre 31 del ");
			builder.append(year);
			description = builder.toString();
		} else {
			description = "Año futuro sin datos aún";
		}
		return description;
	}

	private String getDateDescriptionByMonth(final int year, final int month,
			final String monthName) {
		final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		final int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		final SimpleDateFormat dayDateFormat = new SimpleDateFormat("dd");
		final StringBuilder builder = new StringBuilder();
		String description = "";
		if (currentYear == year && currentMonth == month) {
			final Date currentDate = new Date();
			builder.append("Desde el 1 hasta el ");
			builder.append(dayDateFormat.format(currentDate));
			builder.append(" de ");
			builder.append(monthName);
			builder.append(" del ");
			builder.append(year);
			description = builder.toString();
		} else if (currentYear >= year) {
			final int lastDay = this.getLastDayOfMonth(year, month);
			builder.append("Desde el 1 hasta el ");
			builder.append(lastDay);
			builder.append(" de ");
			builder.append(monthName);
			builder.append(" del ");
			builder.append(year);
			description = builder.toString();
		} else {
			description = "Año futuro sin datos aún";
		}
		return description;
	}

	public int getLastDayOfMonth(final int year, final int month) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	private boolean isBalanceReportClassUap(final Uap uap) {
		final Uap classUap = this.getClassUap(uap);
		return (classUap.getCode() == CLASS_ACTIVO
				|| classUap.getCode() == CLASS_PASIVO || classUap.getCode() == CLASS_PATRIMONIO);
	}

	private Uap getClassUap(final Uap uap) {
		Uap classUap = null;
		if (uap.getLevel() == LEVEL_EMP_HELPER) {
			classUap = uap.getUap().getUap().getUap().getUap();
		} else if (uap.getLevel() == LEVEL_SUBACCOUNT) {
			classUap = uap.getUap().getUap().getUap();
		} else if (uap.getLevel() == LEVEL_ACCOUNT) {
			classUap = uap.getUap().getUap();
		}
		return classUap;
	}

	private Uap getAccountUap(final Uap uap) {
		Uap accountUap = null;
		if (uap.getLevel() == LEVEL_EMP_HELPER) {
			accountUap = uap.getUap().getUap();
		} else if (uap.getLevel() == LEVEL_SUBACCOUNT) {
			accountUap = uap.getUap();
		} else if (uap.getLevel() == LEVEL_ACCOUNT) {
			accountUap = uap;
		}
		return accountUap;
	}

	private long calculateItemValue(final Voucheritem voucherItem) {
		final long debtValue = voucherItem.getDebtvalue();
		final long creditValue = voucherItem.getCreditvalue();
		long value = 0;
		if (voucherItem.getUap().isDebt()) {
			value = debtValue - creditValue;
		} else {
			value = creditValue - debtValue;
		}
		return value;
	}

	private void addReportItemToSet(final Set<ReportItem> reportItemSet,
			final ReportItem item) {
		if (reportItemSet.contains(item)) {
			Iterator<ReportItem> iterator = reportItemSet.iterator();
			while (iterator.hasNext()) {
				final ReportItem itemInSet = iterator.next();
				if (itemInSet.equals(item)) {
					if (item.getValue() != null) {
						itemInSet.addToValue(item.getValue());
					}
					itemInSet.getReportItemSet()
							.addAll(item.getReportItemSet());
					break;
				}
			}
		} else {
			reportItemSet.add(item);
		}
	}

	private Set<ReportItem> addGroupUapToReportItemSet(
			final Set<ReportItem> reportItemSet) {
		final Set<ReportItem> groupReportItemSet = new HashSet<>();
		if (reportItemSet != null && reportItemSet.size() > 0) {
			for (final ReportItem reportItem : reportItemSet) {
				final Uap groupUap = reportItem.getUap().getUap();
				final long value = reportItem.getValue();
				final ReportItem item = new ReportItem(groupUap, value,
						groupUap.getCode());
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
				final ReportItem item = new ReportItem(classUap, null,
						classUap.getCode());
				final ReportItem itemWithTotal = this.buildTotalClassUap(
						classUap, value);
				item.getReportItemSet().add(reportItem);
				this.addReportItemToSet(classReportItemSet, item);
				this.addReportItemToSet(classReportItemSet, itemWithTotal);
			}
		}
		this.completeMissingReportItems(classReportItemSet);
		return classReportItemSet;
	}

	private void completeMissingReportItems(
			final Set<ReportItem> classReportItemSet) {
		final ReportItem itemActivo = this.getReportItemActivo();
		final ReportItem itemPasivo = this.getReportItemPasivo();
		final ReportItem itemPatrimonio = this.getReportItemPatrimonio();
		if (!classReportItemSet.contains(itemActivo)) {
			classReportItemSet.add(itemActivo);
			final ReportItem itemWithTotal = this.buildTotalClassUap(
					itemActivo.getUap(), 0);
			classReportItemSet.add(itemWithTotal);
		}
		if (!classReportItemSet.contains(itemPasivo)) {
			classReportItemSet.add(itemPasivo);
			final ReportItem itemWithTotal = this.buildTotalClassUap(
					itemPasivo.getUap(), 0);
			classReportItemSet.add(itemWithTotal);
		}
		if (!classReportItemSet.contains(itemPatrimonio)) {
			classReportItemSet.add(itemPatrimonio);
			final ReportItem itemWithTotal = this.buildTotalClassUap(
					itemPatrimonio.getUap(), 0);
			classReportItemSet.add(itemWithTotal);
		}
	}

	private ReportItem getReportItemActivo() {
		for (final Uap uap : this.uapSet) {
			if (uap.getCode() == CLASS_ACTIVO) {
				final ReportItem item = new ReportItem(uap, null, uap.getCode());
				return item;
			}
		}
		return null;
	}

	private ReportItem getReportItemPasivo() {
		for (final Uap uap : this.uapSet) {
			if (uap.getCode() == CLASS_PASIVO) {
				final ReportItem item = new ReportItem(uap, null, uap.getCode());
				return item;
			}
		}
		return null;
	}

	private ReportItem getReportItemPatrimonio() {
		for (final Uap uap : this.uapSet) {
			if (uap.getCode() == CLASS_PATRIMONIO) {
				final ReportItem item = new ReportItem(uap, null, uap.getCode());
				return item;
			}
		}
		return null;
	}

	private ReportItem buildTotalClassUap(final Uap classUap, final long value) {
		final Uap uap = new Uap(classUap.getCode(), "TOTAL "
				+ classUap.getName(), 1, classUap.isDebt(),
				classUap.isCredit(), false, new Date(), new Date(), true);
		return new ReportItem(uap, value, classUap.getCode());
	}
}