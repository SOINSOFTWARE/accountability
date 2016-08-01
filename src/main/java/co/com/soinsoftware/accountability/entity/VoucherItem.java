package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carlos Rodriguez
 * @since 11/07/2016
 * @version 1.0
 */
public class VoucherItem implements Serializable {

	private static final long serialVersionUID = 4883869463797105443L;

	private Integer id;

	private Uap uap;

	private Voucher voucher;

	private String concept;

	private String source;

	private long debtvalue;

	private long creditvalue;

	private Date creation;

	private Date updated;

	private boolean enabled;

	public VoucherItem() {
		super();
	}

	public VoucherItem(final Uap uap, final Voucher voucher,
			final long debtvalue, final long creditvalue, final Date creation,
			final Date updated, final boolean enabled) {
		this.uap = uap;
		this.voucher = voucher;
		this.debtvalue = debtvalue;
		this.creditvalue = creditvalue;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public VoucherItem(final Uap uap, final Voucher voucher,
			final String concept, final String source, final long debtvalue,
			final long creditvalue, final Date creation, final Date updated,
			final boolean enabled) {
		this.uap = uap;
		this.voucher = voucher;
		this.concept = concept;
		this.source = source;
		this.debtvalue = debtvalue;
		this.creditvalue = creditvalue;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Uap getUap() {
		return this.uap;
	}

	public void setUap(final Uap uap) {
		this.uap = uap;
	}

	public Voucher getVoucher() {
		return this.voucher;
	}

	public void setVoucher(final Voucher voucher) {
		this.voucher = voucher;
	}

	public String getConcept() {
		return this.concept;
	}

	public void setConcept(final String concept) {
		this.concept = concept;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(final String source) {
		this.source = source;
	}

	public long getDebtvalue() {
		return this.debtvalue;
	}

	public void setDebtvalue(final long debtvalue) {
		this.debtvalue = debtvalue;
	}

	public long getCreditvalue() {
		return this.creditvalue;
	}

	public void setCreditvalue(final long creditvalue) {
		this.creditvalue = creditvalue;
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
}