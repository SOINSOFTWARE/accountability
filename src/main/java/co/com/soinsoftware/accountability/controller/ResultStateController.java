package co.com.soinsoftware.accountability.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.UapBLL;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Report;
import co.com.soinsoftware.accountability.entity.ReportItem;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Voucheritem;

/**
 * @author Carlos Rodriguez
 * @since 23/08/2016
 * @version 1.0
 */
public class ResultStateController extends AbstractReportController {

	private static final String REPORT_NAME = "ESTADO DE RESULTADO";

	private static final String ITEM_OPERATIONAL_EXPENSES = "GASTOS OPERACIONALES";

	private static final String ITEM_OPERATIONAL_INCOMES = "INGRESOS OPERACIONALES";

	private static final String ITEM_REFUND = "DEVOLUCIONES, REBAJAS Y DESCUENTOS";

	private static final String ITEM_NET_SALES = "VENTAS NETA";

	private static final String ITEM_SALES_COST = "COSTOS DE VENTAS";

	private static final String ITEM_GROSS_PROFITS_LOSSES = "UTILIDADES O PERDIDAS BRUTAS";

	private static final String ITEM_SALES_EXPENSES = "GASTOS DE VENTAS";

	private static final String ITEM_TOTAL_EXPENSES = "TOTAL GASTOS GENERALES";

	private static final String ITEM_OPERATIONAL_PROFITS_LOSSES = "UTILIDADES O PERDIDAS OPERACIONALES";

	private static final String ITEM_NO_OPERATIONAL_EXPENSES = "GASTOS NO OPERACIONALES";

	private static final String ITEM_NO_OPERATIONAL_INCOMES = "INGRESOS NO OPERACIONALES";

	private static final String ITEM_GROSS_PROFIT_BEFORE_TAX = "UTILIDAD NETA ANTES DE IMPUESTOS";

	private static final String ITEM_TAXES = "IMPUESTO DE RENTA Y COMPLEMENTARIOS";

	private static final String ITEM_TOTAL_PROFITS_LOSSES = "UTILIDADES O PERDIDAS DEL EJERCICIO";

	private final UapBLL uapBLL;

	private final Uap noOpIncomeUap;

	private final Uap noOpExpenseUap;

	private final Uap opIncomeUap;

	private final Uap opExpenseUap;

	private final Uap opSaleExpenseUap;

	public ResultStateController() {
		super();
		this.uapBLL = UapBLL.getInstance();
		this.opIncomeUap = this.getUapByCode(GROUP_OPERATIONAL_INCOME);
		this.opExpenseUap = this.getUapByCode(GROUP_OPERATIONAL_EXPENSE);
		this.opSaleExpenseUap = this
				.getUapByCode(GROUP_OPERATIONAL_SALE_EXPENSE);
		this.noOpIncomeUap = this.getUapByCode(GROUP_NO_OPERATIONAL_INCOME);
		this.noOpExpenseUap = this.getUapByCode(GROUP_NO_OPERATIONAL_EXPENSE);
	}

	@Override
	public Report buildReport(final Company company,
			final List<Voucher> voucherList, final String description) {
		final Set<ReportItem> accountReportItemSet = new HashSet<>();
		for (final Voucher voucher : voucherList) {
			final Set<Voucheritem> voucherItemSet = voucher.getVoucheritems();
			for (final Voucheritem voucherItem : voucherItemSet) {
				final Uap uap = voucherItem.getUap();
				if (this.isResultStateGroupUap(uap)
						|| this.isTaxAccountUap(uap)) {
					final Uap accountUap = this.getAccountUap(uap);
					final long value = this.calculateItemValue(voucherItem);
					final ReportItem item = this.getNewReportItem(accountUap,
							value);
					this.addReportItemToSet(accountReportItemSet, item);
				}
			}
		}
		final Set<ReportItem> reportItemSet = this
				.addGroupUapToReportItemSet(accountReportItemSet);
		this.calculateGroupValues(reportItemSet);
		return new Report(company, REPORT_NAME, description, reportItemSet);
	}

	@Override
	protected void addMissingReportItems(final Set<ReportItem> itemSet) {
		final ReportItem operationalIncomeItem = this
				.getOperationalIncomeItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, operationalIncomeItem);
		final ReportItem refundIncomeItem = this.getRefundIncomeItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, refundIncomeItem);
		final ReportItem netSalesItem = this.getNetSalesItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, netSalesItem);
		final ReportItem salesCostItem = this.getSalesCostItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, salesCostItem);
		final ReportItem grossProfitOrLosseItem = this
				.getGrossProfitsOrLossesItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, grossProfitOrLosseItem);
		final ReportItem operationalExpenseItem = this
				.getOperationalExpenseItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, operationalExpenseItem);
		final ReportItem salesExpenseItem = this.getSalesExpenseItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, salesExpenseItem);
		final ReportItem totalExpensesItem = this.getTotalExpensesItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, totalExpensesItem);
		final ReportItem operationalProfitOrLosseItem = this
				.getOperationalProfitsOrLossesItem(0);
		this.addReportItemToSetWhenNoContains(itemSet,
				operationalProfitOrLosseItem);
		final ReportItem noOperationalIncomeItem = this
				.getNoOperationalIncomeItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, noOperationalIncomeItem);
		final ReportItem noOperationalExpenseItem = this
				.getNoOperationalExpenseItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, noOperationalExpenseItem);
		final ReportItem grossProfitBeforeTaxItem = this
				.getGrossProfitBeforeTaxItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, grossProfitBeforeTaxItem);
		final ReportItem taxesItem = this.getTaxesItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, taxesItem);
		final ReportItem totalProfitsOrLossesItem = this
				.getTotalProfitsOrLossesItem(0);
		this.addReportItemToSetWhenNoContains(itemSet, totalProfitsOrLossesItem);
	}

	private boolean isResultStateGroupUap(final Uap uap) {
		final Uap groupUap = this.getGroupUap(uap);
		return (this.isOperationalIncomeGroup(groupUap)
				|| this.isCostGroup(groupUap)
				|| this.isOperationalExpenseGroup(groupUap)
				|| this.isSalesExpenseGroup(groupUap)
				|| this.isNoOperationalIncomeGroup(groupUap) || this
					.isNoOperationalExpenseGroup(groupUap));
	}

	private boolean isResultStateRefundIncomeUap(final Uap uap) {
		final Uap accountUap = this.getAccountUap(uap);
		return (accountUap.getCode() == ACCOUNT_REFUND_INCOME);
	}

	private boolean isTaxAccountUap(final Uap uap) {
		final Uap accountUap = this.getAccountUap(uap);
		return (accountUap.getCode() == ACCOUNT_TAXES);
	}

	private Set<ReportItem> addGroupUapToReportItemSet(
			final Set<ReportItem> reportItemSet) {
		final Set<ReportItem> groupReportItemSet = new HashSet<>();
		if (reportItemSet != null && reportItemSet.size() > 0) {
			for (final ReportItem reportItem : reportItemSet) {
				final long value = reportItem.getValue();
				final ReportItem item = this.getReportItem(reportItem.getUap(),
						value);
				item.getReportItemSet().add(reportItem);
				this.addReportItemToSet(groupReportItemSet, item);
			}
		}
		this.addMissingReportItems(groupReportItemSet);
		return groupReportItemSet;
	}

	private void calculateGroupValues(final Set<ReportItem> itemSet) {
		this.calculateNetSales(itemSet);
		this.calculateGrossProfitsOrLosses(itemSet);
		this.calculateTotalExpenses(itemSet);
		this.calculateOperationalProfitsOrLosses(itemSet);
		this.calculateGrossProfitsOrLossesBeforeTaxes(itemSet);
		this.calculateTotalProfitsOrLosses(itemSet);
	}

	private ReportItem getReportItem(final Uap uap, final long value) {
		ReportItem reportItem = null;
		final Uap groupUap = this.getGroupUap(uap);
		if (this.isResultStateRefundIncomeUap(uap)) {
			reportItem = this.getRefundIncomeItem(value);
		} else if (this.isTaxAccountUap(uap)) {
			reportItem = this.getTaxesItem(value);
		} else {
			if (this.isOperationalIncomeGroup(groupUap)) {
				reportItem = this.getOperationalIncomeItem(value);
			} else if (this.isCostGroup(groupUap)) {
				reportItem = this.getSalesCostItem(value);
			} else if (this.isOperationalExpenseGroup(groupUap)) {
				reportItem = this.getOperationalExpenseItem(value);
			} else if (this.isSalesExpenseGroup(groupUap)) {
				reportItem = this.getSalesExpenseItem(value);
			} else if (this.isNoOperationalIncomeGroup(groupUap)) {
				reportItem = this.getNoOperationalIncomeItem(value);
			} else if (this.isNoOperationalExpenseGroup(groupUap)) {
				reportItem = this.getNoOperationalExpenseItem(value);
			}
		}
		return reportItem;
	}

	private Uap getUapByCode(final long code) {
		return this.uapBLL.select(code);
	}

	private ReportItem getOperationalIncomeItem(final long value) {
		return new ReportItem(this.opIncomeUap, ITEM_OPERATIONAL_INCOMES,
				value, 1);
	}

	private ReportItem getRefundIncomeItem(final long value) {
		return new ReportItem(this.opIncomeUap, ITEM_REFUND, value, 2);
	}

	private ReportItem getNetSalesItem(final long value) {
		return new ReportItem(this.opIncomeUap, ITEM_NET_SALES, value, 3);
	}

	private ReportItem getSalesCostItem(final long value) {
		return new ReportItem(this.opIncomeUap, ITEM_SALES_COST, value, 4);
	}

	private ReportItem getGrossProfitsOrLossesItem(final long value) {
		return new ReportItem(this.opIncomeUap, ITEM_GROSS_PROFITS_LOSSES,
				value, 5);
	}

	private ReportItem getOperationalExpenseItem(final long value) {
		return new ReportItem(this.opExpenseUap, ITEM_OPERATIONAL_EXPENSES,
				value, 6);
	}

	private ReportItem getSalesExpenseItem(final long value) {
		return new ReportItem(this.opSaleExpenseUap, ITEM_SALES_EXPENSES,
				value, 7);
	}

	private ReportItem getTotalExpensesItem(final long value) {
		return new ReportItem(this.opSaleExpenseUap, ITEM_TOTAL_EXPENSES,
				value, 8);
	}

	private ReportItem getOperationalProfitsOrLossesItem(final long value) {
		return new ReportItem(this.opSaleExpenseUap,
				ITEM_OPERATIONAL_PROFITS_LOSSES, value, 9);
	}

	private ReportItem getNoOperationalIncomeItem(final long value) {
		return new ReportItem(this.noOpIncomeUap, ITEM_NO_OPERATIONAL_INCOMES,
				value, 10);
	}

	private ReportItem getNoOperationalExpenseItem(final long value) {
		return new ReportItem(this.noOpExpenseUap,
				ITEM_NO_OPERATIONAL_EXPENSES, value, 11);
	}

	private ReportItem getGrossProfitBeforeTaxItem(final long value) {
		return new ReportItem(this.noOpExpenseUap,
				ITEM_GROSS_PROFIT_BEFORE_TAX, value, 12);
	}

	private ReportItem getTaxesItem(final long value) {
		return new ReportItem(this.noOpExpenseUap, ITEM_TAXES, value, 13);
	}

	private ReportItem getTotalProfitsOrLossesItem(final long value) {
		return new ReportItem(this.noOpExpenseUap, ITEM_TOTAL_PROFITS_LOSSES,
				value, 14);
	}

	private boolean isOperationalIncomeGroup(final Uap groupUap) {
		return groupUap.getCode() == GROUP_OPERATIONAL_INCOME;
	}

	private boolean isOperationalExpenseGroup(final Uap groupUap) {
		return groupUap.getCode() == GROUP_OPERATIONAL_EXPENSE;
	}

	private boolean isSalesExpenseGroup(final Uap groupUap) {
		return groupUap.getCode() == GROUP_OPERATIONAL_SALE_EXPENSE;
	}

	private boolean isNoOperationalIncomeGroup(final Uap groupUap) {
		return groupUap.getCode() == GROUP_NO_OPERATIONAL_INCOME;
	}

	private boolean isNoOperationalExpenseGroup(final Uap groupUap) {
		return groupUap.getCode() == GROUP_NO_OPERATIONAL_EXPENSE;
	}

	private boolean isCostGroup(final Uap groupUap) {
		return groupUap.getCode() == GROUP_BUY_COST
				|| groupUap.getCode() == GROUP_SALES_COST;
	}

	private void calculateNetSales(final Set<ReportItem> itemSet) {
		final long opIncomeValue = this.getReportItemValue(itemSet, 1);
		final long refundValue = this.getReportItemValue(itemSet, 2);
		for (final ReportItem item : itemSet) {
			if (item.getOrder() == 3) {
				item.setValue(opIncomeValue + refundValue);
				break;
			}
		}
	}

	private void calculateGrossProfitsOrLosses(final Set<ReportItem> itemSet) {
		final long netSales = this.getReportItemValue(itemSet, 3);
		final long salesCost = this.getReportItemValue(itemSet, 4);
		for (final ReportItem item : itemSet) {
			if (item.getOrder() == 5) {
				item.setValue(netSales - salesCost);
				break;
			}
		}
	}

	private void calculateTotalExpenses(final Set<ReportItem> itemSet) {
		final long opExpenses = this.getReportItemValue(itemSet, 6);
		final long salesExpenses = this.getReportItemValue(itemSet, 7);
		for (final ReportItem item : itemSet) {
			if (item.getOrder() == 8) {
				item.setValue(opExpenses + salesExpenses);
				break;
			}
		}
	}

	private void calculateOperationalProfitsOrLosses(
			final Set<ReportItem> itemSet) {
		final long grossProfitsOrLosses = this.getReportItemValue(itemSet, 5);
		final long totalExpenses = this.getReportItemValue(itemSet, 8);
		for (final ReportItem item : itemSet) {
			if (item.getOrder() == 9) {
				item.setValue(grossProfitsOrLosses - totalExpenses);
				break;
			}
		}
	}

	private void calculateGrossProfitsOrLossesBeforeTaxes(
			final Set<ReportItem> itemSet) {
		final long opProfitsOrLosses = this.getReportItemValue(itemSet, 9);
		final long noOpIncomes = this.getReportItemValue(itemSet, 10);
		final long noOpExpenses = this.getReportItemValue(itemSet, 11);
		for (final ReportItem item : itemSet) {
			if (item.getOrder() == 12) {
				item.setValue(opProfitsOrLosses + noOpIncomes - noOpExpenses);
				break;
			}
		}
	}

	private void calculateTotalProfitsOrLosses(final Set<ReportItem> itemSet) {
		final long grossProfitsOrLossesBT = this
				.getReportItemValue(itemSet, 12);
		final long taxes = this.getReportItemValue(itemSet, 13);
		for (final ReportItem item : itemSet) {
			if (item.getOrder() == 14) {
				item.setValue(grossProfitsOrLossesBT - taxes);
				break;
			}
		}
	}
}