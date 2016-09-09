package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.1
 */
public class User implements Serializable, Comparable<User> {

	private static final long serialVersionUID = 7214829810505786645L;

	private Integer id;

	private Rol rol;

	private long identification;

	private String name;

	private String lastname;

	private String login;

	private String password;

	private String validator;

	private Date finaldate;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private volatile long newIdentification;

	private volatile String newName;

	private volatile String newLastname;

	private volatile boolean delete;

	public User() {
		super();
		this.delete = false;
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
		this.delete = false;
	}

	public User(final Rol rol, final long identification, final String name,
			final String lastname, final String login, final String password,
			final String validator, final Date finaldate, final Date creation,
			final Date updated, final boolean enabled) {
		this.rol = rol;
		this.identification = identification;
		this.name = name;
		this.lastname = lastname;
		this.login = login;
		this.password = password;
		this.validator = validator;
		this.finaldate = finaldate;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.delete = false;
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

	public String getValidator() {
		return validator;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public Date getFinaldate() {
		return finaldate;
	}

	public void setFinaldate(Date finaldate) {
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

	public long getNewIdentification() {
		return newIdentification;
	}

	public void setNewIdentification(long newIdentification) {
		this.newIdentification = newIdentification;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewLastname() {
		return newLastname;
	}

	public void setNewLastname(String newLastname) {
		this.newLastname = newLastname;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ (int) (identification ^ (identification >>> 32));
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identification != other.identification)
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(final User other) {
		final String firstLastName = (this.lastname != null) ? this.lastname
				: "";
		final String secondLastName = (other.lastname != null) ? other.name
				: "";
		final String firstName = (this.name != null) ? this.name : "";
		final String secondName = (other.name != null) ? other.name : "";
		if (firstLastName.compareToIgnoreCase(secondLastName) == 0) {
			return firstName.compareToIgnoreCase(secondName);
		} else {
			return firstLastName.compareToIgnoreCase(secondLastName);
		}
	}
}