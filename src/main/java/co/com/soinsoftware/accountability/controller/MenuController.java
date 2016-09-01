package co.com.soinsoftware.accountability.controller;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.User;
import co.com.soinsoftware.accountability.view.JFBalance;
import co.com.soinsoftware.accountability.view.JFDailyBook;
import co.com.soinsoftware.accountability.view.JFMain;
import co.com.soinsoftware.accountability.view.JFResultState;
import co.com.soinsoftware.accountability.view.JFUap;
import co.com.soinsoftware.accountability.view.JFUser;
import co.com.soinsoftware.accountability.view.JFVoucher;
import co.com.soinsoftware.accountability.view.JFVoucherList;
import co.com.soinsoftware.accountability.view.JFVoucherType;
import co.com.soinsoftware.accountability.view.JFVoucherTypeXCompany;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.2
 */
public class MenuController {

	private final Company company;

	private final JFBalance balanceFrame;

	private final JFDailyBook dailyBookFrame;

	private final JFResultState resultStateFrame;

	private final JFUap uapFrame;

	private final JFUser userFrame;

	private final JFVoucher voucherFrame;

	private final JFVoucherList voucherListFrame;

	private final JFVoucherType voucherTypeFrame;

	private final JFVoucherTypeXCompany voucherTypeXCompFrame;

	private final User loggedUser;

	public MenuController(final JFMain mainFrame, final Company company,
			final User user) {
		super();
		this.company = company;
		this.loggedUser = user;
		this.balanceFrame = new JFBalance();
		this.dailyBookFrame = new JFDailyBook(this.company);
		this.resultStateFrame = new JFResultState();
		this.voucherFrame = new JFVoucher(mainFrame, this.loggedUser,
				this.company);
		this.uapFrame = new JFUap(this.loggedUser, this.company);
		this.voucherListFrame = new JFVoucherList(this.voucherFrame);
		this.userFrame = new JFUser();
		this.voucherTypeFrame = new JFVoucherType();
		this.voucherTypeXCompFrame = new JFVoucherTypeXCompany(this.loggedUser,
				this.company);
	}

	public void showBalanceFrame() {
		this.balanceFrame.refresh(company);
		this.balanceFrame.setVisible(true);
	}

	public void showDailyBookFrame() {
		this.dailyBookFrame.refresh();
		this.dailyBookFrame.setVisible(true);
	}

	public void showResultStateFrame() {
		this.resultStateFrame.refresh(company);
		this.resultStateFrame.setVisible(true);
	}

	public void showUserFrame() {
		this.userFrame.refresh();
		this.userFrame.setVisible(true);
	}

	public void showUapFrame() {
		this.uapFrame.refresh();
		this.uapFrame.setVisible(true);
	}

	public void showVoucherTypeFrame() {
		this.voucherTypeFrame.refresh();
		this.voucherTypeFrame.setVisible(true);
	}

	public void showVoucherTypeXCompanyFrame() {
		this.voucherTypeXCompFrame.refresh();
		this.voucherTypeXCompFrame.setVisible(true);
	}

	public void showVoucherFrame() {
		this.voucherFrame.refresh(null);
		this.voucherFrame.setVisible(true);
	}

	public void showVoucherListFrame() {
		this.voucherListFrame.refresh(company);
		this.voucherListFrame.setVisible(true);
	}

	public boolean isAccountRol() {
		final RoleController roleController = RoleController.getInstance();
		return roleController.isAccountRol(this.loggedUser);
	}

	public boolean isAdminRol() {
		final RoleController roleController = RoleController.getInstance();
		return roleController.isAdminRol(this.loggedUser);
	}

	public boolean isAuxRol() {
		final RoleController roleController = RoleController.getInstance();
		return roleController.isAuxRol(this.loggedUser);
	}
}