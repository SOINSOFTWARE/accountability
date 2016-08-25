package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
		final Set<Vouchertype> voucherTypeSet = this.voucherTypeBLL.select();
		return this.sortVoucherTypeSet(voucherTypeSet);
	}

	public List<Vouchertype> selectVoucherTypes(final Company company) {
		List<Vouchertype> voucherTypeList = new ArrayList<>();
		if (company != null) {
			final Set<Vouchertype> voucherTypeSet = this.voucherTypeBLL
					.select();
			final Set<Vouchertype> voucherTypeXCompSet = new HashSet<>();
			if (voucherTypeSet != null && voucherTypeSet.size() > 0) {
				for (final Vouchertype voucherType : voucherTypeSet) {
					if (!this.hasVoucherTypeXCompany(company, voucherType)) {
						voucherTypeXCompSet.add(voucherType);
					}
				}
				voucherTypeList = this.sortVoucherTypeSet(voucherTypeXCompSet);
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
		final Set<Vouchertypexcompany> voucherTypeXCompSet = this.voucherTypeXCompBLL
				.select();
		return this.sortVoucherTypeXCompanySet(voucherTypeXCompSet);
	}

	public List<Vouchertypexcompany> selectVoucherTypesXCompany(
			final Company company) {
		final Set<Vouchertypexcompany> voucherTypeXCompSet = this.voucherTypeXCompBLL
				.select(company);
		return this.sortVoucherTypeXCompanySet(voucherTypeXCompSet);
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

	public List<Vouchertype> sortVoucherTypeSet(
			final Set<Vouchertype> voucherTypeSet) {
		List<Vouchertype> sortedVoucherTypeList = new ArrayList<>();
		if (voucherTypeSet != null && voucherTypeSet.size() > 0) {
			final List<Vouchertype> voucherTypeList = new ArrayList<>(
					voucherTypeSet);
			final Comparator<Vouchertype> byName = (vt1, vt2) -> vt1.getName()
					.compareToIgnoreCase(vt2.getName());
			sortedVoucherTypeList = voucherTypeList.stream().sorted(byName)
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return sortedVoucherTypeList;
	}

	public List<Vouchertypexcompany> sortVoucherTypeXCompanySet(
			final Set<Vouchertypexcompany> vtXCompSet) {
		List<Vouchertypexcompany> sortedVtXCompList = new ArrayList<>();
		if (vtXCompSet != null && vtXCompSet.size() > 0) {
			final List<Vouchertypexcompany> vtXCompList = new ArrayList<>(
					vtXCompSet);
			final Comparator<Vouchertypexcompany> byCompanyName = (vtXComp1,
					vtXComp2) -> vtXComp1.getCompany().getName()
					.compareToIgnoreCase(vtXComp2.getCompany().getName());
			final Comparator<Vouchertypexcompany> byTypeName = (vtXComp1,
					vtXComp2) -> vtXComp1.getVouchertype().getName()
					.compareToIgnoreCase(vtXComp2.getVouchertype().getName());
			sortedVtXCompList = vtXCompList.stream()
					.sorted(byCompanyName.thenComparing(byTypeName))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return sortedVtXCompList;
	}
}