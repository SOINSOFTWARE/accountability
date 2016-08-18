package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class Company implements Serializable, Comparable<Company> {

	private static final long serialVersionUID = 2021265874795485769L;

	private Integer id;

	private Companytype companytype;

	private Documenttype documenttype;

	private String name;

	private String document;

	private String nameceo;

	private Long documentceo;

	private Date creation;

	private Date updated;

	private boolean enabled;

	private volatile String newName;

	private volatile String newDocument;

	private volatile String newNameCeo;

	private volatile Long newDocumentCeo;

	private volatile boolean delete;

	public Company() {
		super();
		this.delete = false;
	}

	public Company(final Companytype companytype,
			final Documenttype documenttype, final String name,
			final String document, final Date creation, final Date updated,
			final boolean enabled) {
		this.companytype = companytype;
		this.documenttype = documenttype;
		this.name = name;
		this.document = document;
		this.creation = creation;
		this.updated = updated;
		this.enabled = enabled;
		this.delete = false;
	}

	public Company(final Companytype companytype,
			final Documenttype documenttype, final String name,
			final String document, final String nameceo,
			final Long documentceo, final Date creation, final Date updated,
			final boolean enabled) {
		this.companytype = companytype;
		this.documenttype = documenttype;
		this.name = name;
		this.document = document;
		this.nameceo = nameceo;
		this.documentceo = documentceo;
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

	public Companytype getCompanytype() {
		return this.companytype;
	}

	public void setCompanytype(final Companytype companytype) {
		this.companytype = companytype;
	}

	public Documenttype getDocumenttype() {
		return this.documenttype;
	}

	public void setDocumenttype(final Documenttype documenttype) {
		this.documenttype = documenttype;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDocument() {
		return this.document;
	}

	public void setDocument(final String document) {
		this.document = document;
	}

	public String getNameceo() {
		return nameceo;
	}

	public void setNameceo(String nameceo) {
		this.nameceo = nameceo;
	}

	public Long getDocumentceo() {
		return documentceo;
	}

	public void setDocumentceo(Long documentceo) {
		this.documentceo = documentceo;
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

	public String getNewName() {
		return this.newName;
	}

	public void setNewName(final String newName) {
		this.newName = newName;
	}

	public String getNewDocument() {
		return this.newDocument;
	}

	public void setNewDocument(final String newDocument) {
		this.newDocument = newDocument;
	}

	public String getNewNameCeo() {
		return newNameCeo;
	}

	public void setNewNameCeo(String newNameCeo) {
		this.newNameCeo = newNameCeo;
	}

	public Long getNewDocumentCeo() {
		return newDocumentCeo;
	}

	public void setNewDocumentCeo(Long newDocumentCeo) {
		this.newDocumentCeo = newDocumentCeo;
	}

	public boolean isDelete() {
		return this.delete;
	}

	public void setDelete(final boolean delete) {
		this.delete = delete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((document == null) ? 0 : document.hashCode());
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
		Company other = (Company) obj;
		if (document == null) {
			if (other.document != null)
				return false;
		} else if (!document.equals(other.document))
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
	public int compareTo(final Company other) {
		final String firstName = (this.name != null) ? this.name : "";
		final String secondName = (other.name != null) ? other.name : "";
		return firstName.compareToIgnoreCase(secondName);
	}
}