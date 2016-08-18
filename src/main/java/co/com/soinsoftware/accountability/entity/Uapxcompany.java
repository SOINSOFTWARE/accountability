package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class Uapxcompany implements Serializable {

	private static final long serialVersionUID = -1066837084117495273L;

	private Integer id;

	private Uap uap;

	private Company company;

	private Date creation;

	private Date updated;

	private boolean enabled;

	public Uapxcompany() {
		super();
	}

	public Uapxcompany(final Uap uap, final Company company,
			final Date creation, final Date updated, final boolean enabled) {
		this.uap = uap;
		this.company = company;
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
		return uap;
	}

	public void setUap(Uap uap) {
		this.uap = uap;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((uap == null) ? 0 : uap.hashCode());
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
		Uapxcompany other = (Uapxcompany) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (uap == null) {
			if (other.uap != null)
				return false;
		} else if (!uap.equals(other.uap))
			return false;
		return true;
	}
}