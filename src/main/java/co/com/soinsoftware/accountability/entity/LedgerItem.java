package co.com.soinsoftware.accountability.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Carlos Rodriguez
 * @since 01/09/2016
 * @version 1.0
 */
public class LedgerItem {

	private long code;

	private String name;

	private long number;

	private Date date;

	private long debt;

	private long credit;

	private long subTotalDebt;

	private long subTotalCredit;

	private final List<LedgerItem> ledgerItemList;

	public LedgerItem(final long code, final String name) {
		super();
		this.code = code;
		this.name = name;
		this.ledgerItemList = new ArrayList<>();
	}

	public LedgerItem(final long code, final String name, final long number,
			final Date date, final long debt, final long credit) {
		this(code, name);
		this.number = number;
		this.date = date;
		this.debt = debt;
		this.credit = credit;
	}

	public long getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public long getNumber() {
		return this.number;
	}

	public Date getDate() {
		return this.date;
	}

	public long getDebt() {
		return this.debt;
	}

	public void addToDebt(final long debt) {
		this.debt += debt;
	}

	public long getCredit() {
		return this.credit;
	}

	public void addToCredit(final long credit) {
		this.credit += credit;
	}

	public long getSubTotalDebt() {
		return this.subTotalDebt;
	}

	public void setSubTotalDebt(long subTotalDebt) {
		this.subTotalDebt = subTotalDebt;
	}

	public long getSubTotalCredit() {
		return subTotalCredit;
	}

	public void setSubTotalCredit(long subTotalCredit) {
		this.subTotalCredit = subTotalCredit;
	}

	public List<LedgerItem> getLedgerItemList() {
		return this.ledgerItemList;
	}

	public void addLedgerItemToList(final LedgerItem ledgerItem) {
		this.ledgerItemList.add(ledgerItem);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (code ^ (code >>> 32));
		result = prime * result + (int) (number ^ (number >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LedgerItem other = (LedgerItem) obj;
		if (code != other.code)
			return false;
		if (number != other.number)
			return false;
		return true;
	}
}