package co.com.soinsoftware.accountability.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Ledger;
import co.com.soinsoftware.accountability.entity.LedgerItem;
import co.com.soinsoftware.accountability.entity.ReportItem;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Voucheritem;
import co.com.soinsoftware.accountability.entity.Vouchertype;

/**
 * @author Carlos Rodriguez
 * @since 01/09/2016
 * @version 1.0
 */
public class LedgerReportController extends AbstractReportController {

	private static final String REPORT_NAME = "LIBRO MAYOR";

	@Override
	public Ledger buildReport(final Company company,
			final List<Voucher> voucherList, final String description) {
		final Set<LedgerItem> ledgerItemSet = new HashSet<>();
		for (final Voucher voucher : voucherList) {
			final Set<Voucheritem> voucherItemSet = voucher.getVoucheritems();
			for (final Voucheritem voucherItem : voucherItemSet) {
				final Uap accountUap = this.getAccountUap(voucherItem.getUap());
				final LedgerItem childernItem = this.buildChildenLedgerItem(
						voucher, voucherItem);
				final LedgerItem accountLedgerItem = this.getAccountLedgerItem(
						ledgerItemSet, accountUap);
				accountLedgerItem.addLedgerItemToList(childernItem);
				this.addAccountLedgerItemToSet(ledgerItemSet, accountLedgerItem);
			}
		}
		this.calculateTotals(ledgerItemSet);
		return new Ledger(company, REPORT_NAME, description, ledgerItemSet);
	}

	@Override
	protected void addMissingReportItems(Set<ReportItem> itemSet) {
		// TODO Auto-generated method stub

	}

	private LedgerItem buildChildenLedgerItem(final Voucher voucher,
			final Voucheritem voucherItem) {
		final Vouchertype voucherType = voucher.getVouchertypexcompany()
				.getVouchertype();
		final String name = voucherType.getName();
		final long number = voucher.getVouchernumber();
		final Date date = voucher.getVoucherdate();
		final long debt = voucherItem.getDebtvalue();
		final long credit = voucherItem.getCreditvalue();
		return new LedgerItem(0, name, number, date, debt, credit);
	}

	private LedgerItem getAccountLedgerItem(
			final Set<LedgerItem> ledgerItemSet, final Uap accountUap) {
		LedgerItem accountLedgerItem = this.findLedgerItem(ledgerItemSet,
				accountUap);
		if (accountLedgerItem == null) {
			accountLedgerItem = new LedgerItem(accountUap.getCode(),
					accountUap.getName());
		}
		return accountLedgerItem;
	}

	private LedgerItem findLedgerItem(final Set<LedgerItem> ledgerItemSet,
			final Uap accountUap) {
		for (final LedgerItem ledgerItem : ledgerItemSet) {
			if (ledgerItem.getCode() == accountUap.getCode()) {
				return ledgerItem;
			}
		}
		return null;
	}

	private void addAccountLedgerItemToSet(final Set<LedgerItem> ledgerItemSet,
			final LedgerItem accountLedgerItem) {
		if (ledgerItemSet.contains(accountLedgerItem)) {
			ledgerItemSet.remove(accountLedgerItem);
		}
		ledgerItemSet.add(accountLedgerItem);
	}

	private void calculateTotals(final Set<LedgerItem> ledgerItemSet) {
		for (final LedgerItem ledgerItem : ledgerItemSet) {
			final LedgerItem totalItem = new LedgerItem(0,
					"Total de la cuenta", 0, null, 0, 0);
			long subTotalDebt = 0;
			long subTotalCredit = 0;
			for (final LedgerItem childrenItem : ledgerItem.getLedgerItemList()) {
				final long childrenDebt = childrenItem.getDebt();
				final long childrenCredit = childrenItem.getCredit();
				totalItem.addToDebt(childrenDebt);
				totalItem.addToCredit(childrenCredit);
				subTotalDebt += childrenDebt;
				subTotalCredit += childrenCredit;
				if (subTotalDebt > subTotalCredit) {
					subTotalDebt -= subTotalCredit;
					subTotalCredit = 0;
					this.setSubTotalValue(childrenItem, totalItem,
							subTotalDebt, subTotalCredit);

				} else if (subTotalDebt < subTotalCredit) {
					subTotalCredit -= subTotalDebt;
					subTotalDebt = 0;
					this.setSubTotalValue(childrenItem, totalItem,
							subTotalDebt, subTotalCredit);
				} else {
					subTotalCredit = 0;
					subTotalDebt = 0;
					this.setSubTotalValue(childrenItem, totalItem,
							subTotalDebt, subTotalCredit);
				}
			}
			ledgerItem.addLedgerItemToList(totalItem);
		}
	}

	private void setSubTotalValue(final LedgerItem childrenItem,
			final LedgerItem totalItem, final long subTotalDebt,
			final long subTotalCredit) {
		childrenItem.setSubTotalDebt(subTotalDebt);
		childrenItem.setSubTotalCredit(subTotalCredit);
		totalItem.setSubTotalDebt(subTotalDebt);
		totalItem.setSubTotalCredit(subTotalCredit);
	}
}