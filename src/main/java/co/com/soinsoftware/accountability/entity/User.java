package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carlos Rodriguez
 * @since 11/07/2016
 * @version 1.0
 */
public class User implements Serializable {

	private static final long serialVersionUID = -1079034577450056890L;

	private Integer id;

	private Rol rol;

	private long identification;

	private String name;

	private String lastname;

	private String login;

	private String password;

	private Date creation;

	private Date updated;

	private boolean enabled;

	public User() {
		super();
	}

	public User(final Rol rol, final long identification, final String name,
			final String lastname, final String login, final String password,
			final Date creation, final Date updated, final boolean enabled) {
		this.rol = rol;
		this.identification = identification;
		this.name = name;
		this.lastname = lastname;
		this.login = login;
		this.password = password;
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

	public Rol getRol() {
		return this.rol;
	}

	public void setRol(final Rol rol) {
		this.rol = rol;
	}

	public long getIdentification() {
		return this.identification;
	}

	public void setIdentification(final long identification) {
		this.identification = identification;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(final String lastname) {
		this.lastname = lastname;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(final String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
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