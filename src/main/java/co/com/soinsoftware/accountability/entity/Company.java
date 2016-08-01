package co.com.soinsoftware.accountability.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Carlos Rodriguez
 * @since 11/07/2016
 * @version 1.0
 */
public class Company implements Serializable {

	private static final long serialVersionUID = -6987969008968183645L;

	private Integer id;

	private CompanyType companyType;

	private DocumentType documentType;

	private String name;

	private String document;

	private Date creation;

	private Date updated;

	private boolean enabled;

	public Company() {
		super();
	}

	public Company(final CompanyType companyType,
			final DocumentType documentType, final String name,
			final String document, final Date creation, final Date updated,
			final boolean enabled) {
		this.companyType = companyType;
		this.documentType = documentType;
		this.name = name;
		this.document = document;
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

	public CompanyType getCompanyType() {
		return this.companyType;
	}

	public void setCompanyType(final CompanyType companyType) {
		this.companyType = companyType;
	}

	public DocumentType getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(final DocumentType documentType) {
		this.documentType = documentType;
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