package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Carlos Rodriguez
 * @since 05/08/2016
 * @version 1.0
 */
public class Vouchertypexcompany implements Serializable,
		Comparable<Vouchertypexcompany> {

	private static final long serialVersionUID = -1066837084117495273L;

	private Integer id;

	private Company company;

	private Vouchertype vouchertype;

	private long numberfrom;

	private long numberto;

	private long numbercurrent;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<Voucher> vouchers = new HashSet<>(0);

	private boolean delete;

	public Vouchertypexcompany() {
		super();
		this.delete = false;
	}

	public Vouchertypexcompany(final Company company,
			final Vouchertype voucherType, final long numberfrom,
			final long numberto, final long numbercurrent, final Date creation,
			final Date updated, final boolean enabled) {
		this.company = company;
		this.vouchertype = voucherType;
		this.numberfrom = numberfrom;
		this.numberto = numberto;
		this.numbercurrent = numbercurrent;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.delete = false;
	}

	public Vouchertypexcompany(final Company company,
			final Vouchertype voucherType, final long numberfrom,
			final long numberto, final long numbercurrent, final Date creation,
			final Date updated, final boolean enabled,
			final Set<Voucher> vouchers) {
		this.company = company;
		this.vouchertype = voucherType;
		this.numberfrom = numberfrom;
		this.numberto = numberto;
		this.numbercurrent = numbercurrent;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.vouchers = vouchers;
		this.delete = false;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Vouchertype getVouchertype() {
		return vouchertype;
	}

	public void setVouchertype(Vouchertype vouchertype) {
		this.vouchertype = vouchertype;
	}

	public long getNumberfrom() {
		return this.numberfrom;
	}

	public void setNumberfrom(final long numberfrom) {
		this.numberfrom = numberfrom;
	}

	public long getNumberto() {
		return this.numberto;
	}

	public void setNumberto(final long numberto) {
		this.numberto = numberto;
	}

	public long getNumbercurrent() {
		return this.numbercurrent;
	}

	public void setNumbercurrent(final long numbercurrent) {
		this.numbercurrent = numbercurrent;
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

	public Set<Voucher> getVouchers() {
		return this.vouchers;
	}

	public void setVouchers(final Set<Voucher> vouchers) {
		this.vouchers = vouchers;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((vouchertype == null) ? 0 : vouchertype.hashCode());
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
		Vouchertypexcompany other = (Vouchertypexcompany) obj;
		if (enabled != other.enabled)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (vouchertype == null) {
			if (other.vouchertype != null)
				return false;
		} else if (!vouchertype.equals(other.vouchertype))
			return false;
		return true;
	}

	@Override
	public int compareTo(final Vouchertypexcompany other) {
		final String compFirstName = (this.company.getName() != null) ? this.company
				.getName() : "";
		final String compSecondName = (other.company.getName() != null) ? other.company
				.getName() : "";
		final String firstName = (this.vouchertype.getName() != null) ? this.vouchertype
				.getName() : "";
		final String secondName = (other.vouchertype.getName() != null) ? other.vouchertype
				.getName() : "";
		if (compFirstName.compareToIgnoreCase(compSecondName) == 0) {
			return firstName.compareToIgnoreCase(secondName);
		} else {
			return compFirstName.compareToIgnoreCase(compSecondName);
		}

	}
}