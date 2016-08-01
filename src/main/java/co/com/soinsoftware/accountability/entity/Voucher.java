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
public class Voucher implements Serializable {

	private static final long serialVersionUID = 3294069498667196432L;

	private Integer id;

	private VoucherType voucherType;

	private Date voucherdate;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<VoucherItem> voucherItems = new HashSet<>(0);

	public Voucher() {
		super();
	}

	public Voucher(final VoucherType voucherType, final Date voucherdate,
			final Date creation, final Date updated, final boolean enabled) {
		this.voucherType = voucherType;
		this.voucherdate = voucherdate;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Voucher(final VoucherType voucherType, final Date voucherdate,
			final Date creation, final Date updated, final boolean enabled,
			final Set<VoucherItem> voucherItems) {
		this.voucherType = voucherType;
		this.voucherdate = voucherdate;
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

	public VoucherType getVoucherType() {
		return this.voucherType;
	}

	public void setVoucherType(final VoucherType voucherType) {
		this.voucherType = voucherType;
	}

	public Date getVoucherdate() {
		return this.voucherdate;
	}

	public void setVoucherdate(final Date voucherdate) {
		this.voucherdate = voucherdate;
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