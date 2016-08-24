package co.com.soinsoftware.accountability.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Report;
import co.com.soinsoftware.accountability.entity.ReportItem;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Voucheritem;
import co.com.soinsoftware.accountability.view.JFBalance;

/**
 * @author Carlos Rodriguez
 * @since 23/08/2016
 * @version 1.0
 */
public abstract class AbstractReportController {

	public static final long ACCOUNT_REFUND_INCOME = 4175;
	
	public static final long ACCOUNT_TAXES = 2404;

	public static final long CLASS_ACTIVO = 1;

	public static final long CLASS_PASIVO = 2;

	public static final long CLASS_PATRIMONIO = 3;

	public static final long GROUP_BUY_COST = 62;

	public static final long GROUP_NO_OPERATIONAL_EXPENSE = 53;

	public static final long GROUP_NO_OPERATIONAL_INCOME = 42;

	public static final long GROUP_OPERATIONAL_EXPENSE = 51;

	public static final long GROUP_OPERATIONAL_INCOME = 41;

	public static final long GROUP_OPERATIONAL_SALE_EXPENSE = 52;

	public static final long GROUP_SALES_COST = 61;

	private static final int LEVEL_ACCOUNT = 3;

	private static final int LEVEL_SUBACCOUNT = 4;

	private static final int LEVEL_EMP_HELPER = 5;

	public abstract Report buildReport(final Company company,
			final List<Voucher> voucherList, final String description);

	protected abstract void addMissingReportItems(final Set<ReportItem> itemSet);

	public String getReportDateDescription(final String range, final int year,
			final int month, final String monthName) {
		String description = "";
		if (range == JFBalance.RANGE_YEAR) {
			description = this.getDateDescriptionByYear(year);
		} else {
			description = this
					.getDateDescriptionByMonth(year, month, monthName);
		}
		return description;
	}

	public long getReportItemValue(final Report report, final long code) {
		long value = 0;
		for (final ReportItem reportItem : report.getReportItemSet()) {
			if (reportItem.getCode() == code && reportItem.getValue() != null) {
				value = reportItem.getValue();
				break;
			}
		}
		return value;
	}

	public long getReportItemValue(final Set<ReportItem> reportItemSet,
			final long order) {
		long value = 0;
		for (final ReportItem reportItem : reportItemSet) {
			if (reportItem.getOrder() == order && reportItem.getValue() != null) {
				value = reportItem.getValue();
				break;
			}
		}
		return value;
	}

	protected Uap getClassUap(final Uap uap) {
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

	protected Uap getGroupUap(final Uap uap) {
		Uap groupUap = null;
		if (uap.getLevel() == LEVEL_EMP_HELPER) {
			groupUap = uap.getUap().getUap().getUap();
		} else if (uap.getLevel() == LEVEL_SUBACCOUNT) {
			groupUap = uap.getUap().getUap();
		} else if (uap.getLevel() == LEVEL_ACCOUNT) {
			groupUap = uap.getUap();
		}
		return groupUap;
	}

	protected Uap getAccountUap(final Uap uap) {
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

	protected long calculateItemValue(final Voucheritem voucherItem) {
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

	protected void addReportItemToSetWhenNoContains(
			final Set<ReportItem> itemSet, final ReportItem item) {
		if (!itemSet.contains(item)) {
			itemSet.add(item);
		}
	}

	protected void addReportItemToSet(final Set<ReportItem> reportItemSet,
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

	protected ReportItem getNewReportItem(final Uap uap, final Long value) {
		final String name = uap.getName();
		final long code = uap.getCode();
		return new ReportItem(uap, name, value, code);
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

	private int getLastDayOfMonth(final int year, final int month) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}