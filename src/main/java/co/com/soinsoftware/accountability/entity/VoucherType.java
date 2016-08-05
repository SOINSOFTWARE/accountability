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
public class Vouchertype implements Serializable, Comparable<Vouchertype> {

	private static final long serialVersionUID = -1066837084117495273L;

	private Integer id;

	private String code;

	private String name;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<Vouchertypexcompany> vouchertypexcompanies = new HashSet<>(0);

	public Vouchertype() {
		super();
	}

	public Vouchertype(final String code, final String name,
			final Date creation, final Date updated, final boolean enabled) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Vouchertype(final String code, final String name,
			final Date creation, final Date updated, final boolean enabled,
			final Set<Vouchertypexcompany> vouchertypexcompanies) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.vouchertypexcompanies = vouchertypexcompanies;
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

	public Set<Vouchertypexcompany> getVouchertypexcompanies() {
		return vouchertypexcompanies;
	}

	public void setVouchertypexcompanies(
			Set<Vouchertypexcompany> vouchertypexcompanies) {
		this.vouchertypexcompanies = vouchertypexcompanies;
	}

	@Override
	public int compareTo(final Vouchertype other) {
		final String firstName = (this.name != null) ? this.name : "";
		final String secondName = (other.name != null) ? other.name : "";
		return firstName.compareToIgnoreCase(secondName);
	}
}