package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Carlos Rodriguez
 * @since 11/07/2016
 * @version 1.0
 */
public class Uap implements Serializable {

	private static final long serialVersionUID = -3246500608853506551L;

	private Integer id;

	private long code;

	private String name;

	private boolean debt;

	private boolean credit;

	private boolean editable;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<VoucherItem> voucherItems = new HashSet<>(0);

	public Uap() {
		super();
	}

	public Uap(final long code, final String name, final boolean debt,
			final boolean credit, final boolean editable, final Date creation,
			final Date updated, final boolean enabled) {
		this.code = code;
		this.name = name;
		this.debt = debt;
		this.credit = credit;
		this.editable = editable;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Uap(final long code, final String name, final boolean debt,
			final boolean credit, final boolean editable, final Date creation,
			final Date updated, final boolean enabled,
			final Set<VoucherItem> voucherItems) {
		this.code = code;
		this.name = name;
		this.debt = debt;
		this.credit = credit;
		this.editable = editable;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.voucherItems = voucherItems;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public long getCode() {
		return this.code;
	}

	public void setCode(final long code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public boolean isDebt() {
		return this.debt;
	}

	public void setDebt(final boolean debt) {
		this.debt = debt;
	}

	public boolean isCredit() {
		return this.credit;
	}

	public void setCredit(final boolean credit) {
		this.credit = credit;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public void setEditable(final boolean editable) {
		this.editable = editable;
	}

	public Date getCreation() {
		return this.creation;
	}

	public void setCreation(final Date creation) {
		this.creation = creation;
	}

	public Date getUpdated() {
		return this.updated;
	}

	public void setUpdated(final Date updated) {
		this.updated = updated;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public Set<VoucherItem> getVoucherItems() {
		return this.voucherItems;
	}

	public void setVoucherItems(final Set<VoucherItem> voucherItems) {
		this.voucherItems = voucherItems;
	}
}