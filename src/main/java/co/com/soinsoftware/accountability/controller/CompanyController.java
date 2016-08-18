package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.CompanyBLL;
import co.com.soinsoftware.accountability.bll.CompanyTypeBLL;
import co.com.soinsoftware.accountability.bll.DocumentTypeBLL;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Companytype;
import co.com.soinsoftware.accountability.entity.Documenttype;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class CompanyController {

	private static final String COMPANY_TYPE_NATURAL = "Persona natural";

	private static final String COMPANY_TYPE_JURIDICA = "Persona jurídica";

	private static final String DOCUMENT_TYPE_CC = "Cedula de ciudadanía";

	private static final String DOCUMENT_TYPE_CE = "Cedula de extranjería";

	private static final String DOCUMENT_TYPE_NIT = "NIT";

	private final CompanyBLL companyBLL;

	private final CompanyTypeBLL companyTypeBLL;

	private final DocumentTypeBLL documentTypeBLL;

	public CompanyController() {
		super();
		this.companyBLL = CompanyBLL.getInstance();
		this.companyTypeBLL = CompanyTypeBLL.getInstance();
		this.documentTypeBLL = DocumentTypeBLL.getInstance();
	}

	public List<Company> selectCompanies() {
		List<Company> companyList = new ArrayList<>();
		final Set<Company> companySet = this.companyBLL.select();
		if (companySet != null) {
			companyList = new ArrayList<>(companySet);
			if (companyList.size() > 0) {
				Collections.sort(companyList);
			}
		}
		return companyList;
	}

	public void saveCompany(final Companytype companyType,
			final Documenttype documentType, final String name,
			final String document, final String nameCEO, final Long documentCEO) {
		final Date currentDate = new Date();
		final Company company = new Company(companyType, documentType, name,
				document, nameCEO, documentCEO, currentDate, currentDate, true);
		this.saveCompany(company);
	}

	public void saveCompany(final Company company) {
		this.companyBLL.save(company);
	}

	public List<Companytype> selectCompanyTypes() {
		List<Companytype> companyTypeList = new ArrayList<>();
		final Set<Companytype> companyTypeSet = this.companyTypeBLL.select();
		if (companyTypeSet != null) {
			companyTypeList = new ArrayList<>(companyTypeSet);
			if (companyTypeList.size() > 0) {
				Collections.sort(companyTypeList);
			}
		}
		return companyTypeList;
	}

	public List<Documenttype> selectDocumentTypes() {
		List<Documenttype> documentTypeList = new ArrayList<>();
		final Set<Documenttype> documentTypeSet = this.documentTypeBLL.select();
		if (documentTypeSet != null) {
			documentTypeList = new ArrayList<>(documentTypeSet);
			if (documentTypeList.size() > 0) {
				Collections.sort(documentTypeList);
			}
		}
		return documentTypeList;
	}

	public Companytype selectCompanyTypeNatural() {
		return this.selectCompanyType(COMPANY_TYPE_NATURAL);
	}

	public Companytype selectCompanyTypeJuridica() {
		return this.selectCompanyType(COMPANY_TYPE_JURIDICA);
	}

	private Companytype selectCompanyType(final String name) {
		return this.companyTypeBLL.select(name);
	}

	public Documenttype selectDocumentTypeCc() {
		return this.selectDocumentType(DOCUMENT_TYPE_CC);
	}

	public Documenttype selectDocumentTypeCe() {
		return this.selectDocumentType(DOCUMENT_TYPE_CE);
	}

	public Documenttype selectDocumentTypeNit() {
		return this.selectDocumentType(DOCUMENT_TYPE_NIT);
	}

	private Documenttype selectDocumentType(final String name) {
		return this.documentTypeBLL.select(name);
	}
}