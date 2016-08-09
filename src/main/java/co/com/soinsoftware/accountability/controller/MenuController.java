package co.com.soinsoftware.accountability.controller;

import co.com.soinsoftware.accountability.view.JFCompany;
import co.com.soinsoftware.accountability.view.JFUap;
import co.com.soinsoftware.accountability.view.JFUser;
import co.com.soinsoftware.accountability.view.JFVoucherType;
import co.com.soinsoftware.accountability.view.JFVoucherTypeXCompany;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class MenuController {

	private final JFCompany companyFrame;

	private final JFUap uapFrame;

	private final JFUser userFrame;

	private final JFVoucherType voucherTypeFrame;

	private final JFVoucherTypeXCompany voucherTypeXCompFrame;

	public MenuController(final JFCompany companyFrame, final JFUap uapFrame,
			final JFUser userFrame, final JFVoucherType voucherTypeFrame,
			final JFVoucherTypeXCompany voucherTypeXCompFrame) {
		super();
		this.companyFrame = companyFrame;
		this.uapFrame = uapFrame;
		this.userFrame = userFrame;
		this.voucherTypeFrame = voucherTypeFrame;
		this.voucherTypeXCompFrame = voucherTypeXCompFrame;
	}

	public JFCompany getCompanyFrame() {
		return companyFrame;
	}

	public JFUap getUapFrame() {
		return uapFrame;
	}

	public JFUser getUserFrame() {
		return userFrame;
	}

	public JFVoucherType getVoucherTypeFrame() {
		return voucherTypeFrame;
	}

	public JFVoucherTypeXCompany getVoucherTypeXCompFrame() {
		return voucherTypeXCompFrame;
	}
}