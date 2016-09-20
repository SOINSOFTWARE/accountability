package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carlos Rodriguez
 * @since 20/09/2016
 * @version 1.0
 */
public class Appxmodule implements Serializable {

	private static final long serialVersionUID = 7620834064248786298L;

	private Integer id;

	private App app;

	private Module module;

	private Date creation;

	private Date updated;

	private boolean enabled;

	public Appxmodule() {
		super();
	}

	public Appxmodule(final App app, final Module module, final Date creation,
			final Date updated, final boolean enabled) {
		this.app = app;
		this.module = module;
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

	public App getApp() {
		return this.app;
	}

	public void setApp(final App app) {
		this.app = app;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(final Module module) {
		this.module = module;
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