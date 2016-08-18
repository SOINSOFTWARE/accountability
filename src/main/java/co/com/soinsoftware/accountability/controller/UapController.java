package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.UapBLL;
import co.com.soinsoftware.accountability.entity.Uap;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class UapController {

	private static final int ACCOUNT_CLASS_LEVEL = 1;

	private final UapBLL uapBLL;

	public UapController() {
		super();
		this.uapBLL = UapBLL.getInstance();
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

	private List<Uap> sortUapSet(final Set<Uap> uapSet) {
		List<Uap> uapList = new ArrayList<>();
		if (uapSet != null && uapSet.size() > 0) {
			uapList = new ArrayList<>(uapSet);
			Collections.sort(uapList);
		}
		return uapList;
	}
}