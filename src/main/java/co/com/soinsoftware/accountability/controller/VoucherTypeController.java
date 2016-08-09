package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.VoucherTypeBLL;
import co.com.soinsoftware.accountability.bll.VoucherTypeXCompanyBLL;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Vouchertype;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class VoucherTypeController {

	private final VoucherTypeBLL voucherTypeBLL;

	private final VoucherTypeXCompanyBLL voucherTypeXCompBLL;

	public VoucherTypeController() {
		super();
		this.voucherTypeBLL = VoucherTypeBLL.getInstance();
		this.voucherTypeXCompBLL = VoucherTypeXCompanyBLL.getInstance();
	}

	public List<Vouchertype> selectVoucherTypes() {
		List<Vouchertype> voucherTypeList = new ArrayList<>();
		final Set<Vouchertype> voucherTypeSet = this.voucherTypeBLL.select();
		if (voucherTypeSet != null) {
			voucherTypeList = new ArrayList<>(voucherTypeSet);
			if (voucherTypeList.size() > 0) {
				Collections.sort(voucherTypeList);
			}
		}
		return voucherTypeList;
	}

	public List<Vouchertype> selectVoucherTypes(final Company company) {
		List<Vouchertype> voucherTypeList = new ArrayList<>();
		if (company != null) {
			final List<Vouchertype> completeVoucherTypeList = this
					.selectVoucherTypes();
			if (completeVoucherTypeList != null
					&& completeVoucherTypeList.size() > 0) {
				for (final Vouchertype voucherType : completeVoucherTypeList) {
					if (!this.hasVoucherTypeXCompany(company, voucherType)) {
						voucherTypeList.add(voucherType);
					}
				}
			}
		}
		return voucherTypeList;
	}

	public Vouchertype selectVoucherType(final String code) {
		return this.voucherTypeBLL.select(code);
	}

	public Vouchertype saveVoucherType(final String code, final String name) {
		final Date currentDate = new Date();
		final Vouchertype voucherType = new Vouchertype(code, name,
				currentDate, currentDate, true);
		this.saveVoucherType(voucherType);
		return voucherType;
	}

	public void saveVoucherType(final Vouchertype voucherType) {
		this.voucherTypeBLL.save(voucherType);
	}

	public List<Vouchertypexcompany> selectVoucherTypesXCompany() {
		List<Vouchertypexcompany> voucherTypeXCompList = new ArrayList<>();
		final Set<Vouchertypexcompany> voucherTypeXCompSet = this.voucherTypeXCompBLL
				.select();
		if (voucherTypeXCompSet != null) {
			voucherTypeXCompList = new ArrayList<>(voucherTypeXCompSet);
			if (voucherTypeXCompList.size() > 0) {
				Collections.sort(voucherTypeXCompList);
			}
		}
		return voucherTypeXCompList;
	}

	public Vouchertypexcompany saveVoucherTypeXCompany(final Company company,
			final Vouchertype voucherType, final long numberFrom,
			final long numberTo) {
		final Date currentDate = new Date();
		final Vouchertypexcompany voucherTypeXComp = new Vouchertypexcompany(
				company, voucherType, numberFrom, numberTo, numberFrom,
				currentDate, currentDate, true);
		this.saveVoucherTypeXCompany(voucherTypeXComp);
		return voucherTypeXComp;
	}

	public void saveVoucherTypeXCompany(
			final Vouchertypexcompany voucherTypeXComp) {
		this.voucherTypeXCompBLL.save(voucherTypeXComp);
	}

	private boolean hasVoucherTypeXCompany(final Company company,
			final Vouchertype voucherType) {
		final Vouchertypexcompany voucherTypeXCompany = this.voucherTypeXCompBLL
				.select(voucherType, company);
		return (voucherTypeXCompany != null);
	}
}