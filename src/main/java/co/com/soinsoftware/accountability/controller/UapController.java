package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import co.com.soinsoftware.accountability.bll.UapBLL;
import co.com.soinsoftware.accountability.bll.UapXCompanyBLL;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Uapxcompany;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class UapController {

	private static final int ACCOUNT_CLASS_LEVEL = 1;

	private final UapBLL uapBLL;

	private final UapXCompanyBLL uapXCompBLL;

	public UapController() {
		super();
		this.uapBLL = UapBLL.getInstance();
		this.uapXCompBLL = UapXCompanyBLL.getInstance();
	}

	public List<Uap> selectUapDefault() {
		final Set<Uap> uapSet = this.uapBLL.select();
		return this.sortUapSet(uapSet);
	}

	public List<Uap> selectUapClassLevel() {
		final Set<Uap> uapSet = this.uapBLL.select(ACCOUNT_CLASS_LEVEL);
		return this.sortUapSet(uapSet);
	}

	public List<Uap> selectUapChildren(final Uap uapParent) {
		final Set<Uap> uapSet = this.uapBLL.select(uapParent);
		return this.sortUapSet(uapSet);
	}

	public Uap selectUapByCode(final long code) {
		return this.uapBLL.select(code);
	}

	public Uap saveUap(final Uap parentUap, final long code, final String name) {
		final Date currentDate = new Date();
		final Uap uap = new Uap(code, name, (parentUap.getLevel() + 1),
				parentUap.isDebt(), parentUap.isCredit(), true, currentDate,
				currentDate, true);
		uap.setUap(parentUap);
		this.saveUap(uap);
		return uap;
	}

	public void saveUap(final Uap uap) {
		this.uapBLL.save(uap);
	}

	public Uapxcompany saveUapXCompany(final Company company, final Uap uap) {
		final Date currentDate = new Date();
		final Uapxcompany uapXComp = new Uapxcompany(uap, company, currentDate,
				currentDate, true);
		this.saveUapXCompany(uapXComp);
		return uapXComp;
	}

	public void saveUapXCompany(final Uapxcompany uapXCompany) {
		this.uapXCompBLL.save(uapXCompany);
	}

	public void saveUapXCompanySet(final Set<Uapxcompany> uapXCompanySet) {
		this.uapXCompBLL.save(uapXCompanySet);
	}

	public List<Uap> sortUapSet(final Set<Uap> uapSet) {
		List<Uap> sortedUapList = new ArrayList<>();
		if (uapSet != null && uapSet.size() > 0) {
			final List<Uap> uapList = new ArrayList<>(uapSet);
			final Comparator<Uap> byCode = (uap1, uap2) -> Long.compare(
					uap1.getCode(), uap2.getCode());
			sortedUapList = uapList.stream().sorted(byCode)
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return sortedUapList;
	}
}