package co.com.soinsoftware.accountability.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.UapBLL;
import co.com.soinsoftware.accountability.entity.Uap;

/**
 * @author Carlos Rodriguez
 * @since 04/08/2016
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
		List<Uap> uapList = new ArrayList<>();
		final Set<Uap> uapSet = this.uapBLL.select(ACCOUNT_CLASS_LEVEL);
		if (uapSet != null && uapSet.size() > 0) {
			uapList = new ArrayList<>(uapSet);
			Collections.sort(uapList);
		}
		return uapList;
	}
}