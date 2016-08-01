package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class Voucher implements Serializable, Comparable<Voucher> {

	private static final long serialVersionUID = 9215534973517337662L;

	private Integer id;

	private Vouchertype vouchertype;

	private Date voucherdate;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<Voucheritem> voucheritems = new HashSet<>(0);

	public Voucher() {
		super();
	}

	public Voucher(final Vouchertype vouchertype, final Date voucherdate,
			final Date creation, final Date updated, final boolean enabled) {
		this.vouchertype = vouchertype;
		this.voucherdate = voucherdate;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Voucher(final Vouchertype vouchertype, final Date voucherdate,
			final Date creation, final Date updated, final boolean enabled,
			final Set<Voucheritem> voucheritems) {
		this.vouchertype = vouchertype;
		this.voucherdate = voucherdate;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.voucheritems = voucheritems;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Vouchertype getVouchertype() {
		return this.vouchertype;
	}

	public void setVouchertype(final Vouchertype vouchertype) {
		this.vouchertype = vouchertype;
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

	public Set<Voucheritem> getVoucheritems() {
		return this.voucheritems;
	}

	public void setVoucheritems(Set<Voucheritem> voucheritems) {
		this.voucheritems = voucheritems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((voucherdate == null) ? 0 : voucherdate.hashCode());
		result = prime * result
				+ ((vouchertype == null) ? 0 : vouchertype.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voucher other = (Voucher) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (voucherdate == null) {
			if (other.voucherdate != null)
				return false;
		} else if (!voucherdate.equals(other.voucherdate))
			return false;
		if (vouchertype == null) {
			if (other.vouchertype != null)
				return false;
		} else if (!vouchertype.equals(other.vouchertype))
			return false;
		return true;
	}

	@Override
	public int compareTo(final Voucher other) {
		final Date firstDate = (this.voucherdate != null) ? this.voucherdate
				: new Date();
		final Date secondDate = (other.voucherdate != null) ? other.voucherdate
				: new Date();
		return firstDate.compareTo(secondDate);
	}
}