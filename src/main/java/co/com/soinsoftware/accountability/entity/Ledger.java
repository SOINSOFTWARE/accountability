package co.com.soinsoftware.accountability.entity;

import java.util.Set;

/**
 * @author Carlos Rodriguez
 * @since 01/09/2016
 * @version 1.0
 */
public class Ledger {

	private final Company company;

	private final String name;

	private final String formattedDate;

	private final Set<LedgerItem> ledgerItemSet;
	
	private long totalDebt;
	
	private long totalCredit;

	public Ledger(final Company company, final String name,
			final String formattedDate, final Set<LedgerItem> ledgerItemSet) {
		super();
		this.company = company;
		this.name = name;
		this.formattedDate = formattedDate;
		this.ledgerItemSet = ledgerItemSet;
	}

	public Company getCompany() {
		return this.company;
	}

	public String getName() {
		return this.name;
	}

	public String getFormattedDate() {
		return this.formattedDate;
	}

	public Set<LedgerItem> getLedgerItemSet() {
		return this.ledgerItemSet;
	}

	public long getTotalDebt() {
		return totalDebt;
	}

	public void setTotalDebt(long totalDebt) {
		this.totalDebt = totalDebt;
	}

	public long getTotalCredit() {
		return totalCredit;
	}

	public void setTotalCredit(long totalCredit) {
		this.totalCredit = totalCredit;
	}
}