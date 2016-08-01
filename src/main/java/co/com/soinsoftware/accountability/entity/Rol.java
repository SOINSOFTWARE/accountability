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
public class Rol implements Serializable, Comparable<Rol> {

	private static final long serialVersionUID = 9160088837716858613L;

	private Integer id;

	private String code;

	private String name;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private Set<User> users = new HashSet<>(0);

	public Rol() {
		super();
	}

	public Rol(final String code, final String name, final Date creation,
			final Date updated, final boolean enabled) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
	}

	public Rol(final String code, final String name, final Date creation,
			final Date updated, final boolean enabled, final Set<User> users) {
		this.code = code;
		this.name = name;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.users = users;
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

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(final Set<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		Rol other = (Rol) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
	public int compareTo(final Rol other) {
		final String firstName = (this.name != null) ? this.name : "";
		final String secondName = (other.name != null) ? other.name : "";
		return firstName.compareToIgnoreCase(secondName);
	}
}