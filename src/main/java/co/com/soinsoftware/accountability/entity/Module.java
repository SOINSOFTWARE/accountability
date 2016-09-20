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
public class Module implements Serializable {

	private static final long serialVersionUID = -6007717209478926853L;

	private Integer id;

	private String code;

	private String name;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<Appxmodule> appxmodules = new HashSet<>(0);

	public Module() {
		super();
	}

	public Module(final String code, final String name, final Date creation,
			final Date updated, final boolean enabled) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Module(final String code, final String name, final Date creation,
			final Date updated, final boolean enabled,
			final Set<Appxmodule> appxmodules) {
		this.code = code;
		this.name = name;
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

	public Set<Appxmodule> getAppxmodules() {
		return this.appxmodules;
	}

	public void setAppxmodules(final Set<Appxmodule> appxmodules) {
		this.appxmodules = appxmodules;
	}
}