package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Carlos Rodriguez
 * @since 20/09/2016
 * @version 1.0
 */
public class App implements Serializable {

	private static final long serialVersionUID = -4601963509493433478L;

	private Integer id;

	private String validator;

	private Date finaldate;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<Appxmodule> appxmodules = new HashSet<>(0);

	public App() {
		super();
	}

	public App(final String validator, final Date creation, final Date updated,
			final boolean enabled) {
		this.validator = validator;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public App(final String validator, final Date finaldate,
			final Date creation, final Date updated, final boolean enabled,
			final Set<Appxmodule> appxmodules) {
		this.validator = validator;
		this.finaldate = finaldate;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.appxmodules = appxmodules;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getValidator() {
		return this.validator;
	}

	public void setValidator(final String validator) {
		this.validator = validator;
	}

	public Date getFinaldate() {
		return this.finaldate;
	}

	public void setFinaldate(final Date finaldate) {
		this.finaldate = finaldate;
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

	public Set<Appxmodule> getAppxmodules() {
		return this.appxmodules;
	}

	public void setAppxmodules(final Set<Appxmodule> appxmodules) {
		this.appxmodules = appxmodules;
	}
}