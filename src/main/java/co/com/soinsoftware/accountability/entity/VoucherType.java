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
public class VoucherType implements Serializable {

	private static final long serialVersionUID = 6493161125701577855L;

	private Integer id;

	private String code;

	private String name;

	private long numberfrom;

	private long numberto;

	private long numbercurrent;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<Voucher> vouchers = new HashSet<>(0);

	public VoucherType() {
		super();
	}

	public VoucherType(final String code, final String name,
			final long numberfrom, final long numberto,
			final long numbercurrent, final Date creation, final Date updated,
			final boolean enabled) {
		this.code = code;
		this.name = name;
		this.numberfrom = numberfrom;
		this.numberto = numberto;
		this.numbercurrent = numbercurrent;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public VoucherType(final String code, final String name,
			final long numberfrom, final long numberto,
			final long numbercurrent, final Date creation, final Date updated,
			final boolean enabled, final Set<Voucher> vouchers) {
		this.code = code;
		this.name = name;
		this.numberfrom = numberfrom;
		this.numberto = numberto;
		this.numbercurrent = numbercurrent;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.vouchers = vouchers;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
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
}