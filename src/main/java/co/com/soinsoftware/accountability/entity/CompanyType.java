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
public class CompanyType implements Serializable {

	private static final long serialVersionUID = 1415707187808468638L;

	private Integer id;

	private String name;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<Company> companies = new HashSet<>(0);

	public CompanyType() {
		super();
	}

	public CompanyType(final String name, final Date creation,
			final Date updated, final boolean enabled) {
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public CompanyType(final String name, final Date creation,
			final Date updated, final boolean enabled,
			final Set<Company> companies) {
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.companies = companies;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
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

	public Set<Company> getCompanies() {
		return this.companies;
	}

	public void setCompanies(final Set<Company> companies) {
		this.companies = companies;
	}
}