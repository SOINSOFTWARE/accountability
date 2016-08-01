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
public class Uap implements Serializable, Comparable<Uap> {

	private static final long serialVersionUID = 7873071882028195499L;

	private Integer id;

	private Uap uap;

	private long code;

	private String name;

	private boolean debt;

	private boolean credit;

	private boolean editable;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<Uap> uaps = new HashSet<>(0);

	private Set<Voucheritem> voucheritems = new HashSet<>(0);

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

	public Uap(final Uap uap, final long code, final String name,
			final boolean debt, final boolean credit, final boolean editable,
			final Date creation, final Date updated, final boolean enabled,
			final Set<Uap> uaps, final Set<Voucheritem> voucheritems) {
		this.uap = uap;
		this.code = code;
		this.name = name;
		this.debt = debt;
		this.credit = credit;
		this.editable = editable;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.uaps = uaps;
		this.voucheritems = voucheritems;
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

	public Set<Uap> getUaps() {
		return this.uaps;
	}

	public void setUaps(final Set<Uap> uaps) {
		this.uaps = uaps;
	}

	public Set<Voucheritem> getVoucheritems() {
		return this.voucheritems;
	}

	public void setVoucheritems(final Set<Voucheritem> voucheritems) {
		this.voucheritems = voucheritems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (code ^ (code >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Uap other = (Uap) obj;
		if (code != other.code)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(final Uap other) {
		return Long.compare(this.code, other.code);
	}
}