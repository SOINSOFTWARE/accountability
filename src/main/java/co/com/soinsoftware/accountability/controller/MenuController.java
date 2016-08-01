package co.com.soinsoftware.accountability.controller;

import co.com.soinsoftware.accountability.view.JFCompany;
import co.com.soinsoftware.accountability.view.JFUser;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class MenuController {

	private final JFCompany companyFrame;

	private final JFUser userFrame;

	public MenuController(final JFCompany companyFrame, final JFUser userFrame) {
		super();
		this.companyFrame = companyFrame;
		this.userFrame = userFrame;
	}

	public JFCompany getCompanyFrame() {
		return companyFrame;
	}

	public JFUser getUserFrame() {
		return userFrame;
	}
}