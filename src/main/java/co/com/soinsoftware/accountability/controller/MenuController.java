package co.com.soinsoftware.accountability.controller;

import java.util.Set;

import co.com.soinsoftware.accountability.bll.AppXModuleBLL;
import co.com.soinsoftware.accountability.entity.Appxmodule;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Module;
import co.com.soinsoftware.accountability.entity.User;
import co.com.soinsoftware.accountability.view.JFUser;
import co.com.soinsoftware.accountability.view.accountability.JFAccountability;
import co.com.soinsoftware.accountability.view.accountability.JFBalance;
import co.com.soinsoftware.accountability.view.accountability.JFDailyBook;
import co.com.soinsoftware.accountability.view.accountability.JFLedger;
import co.com.soinsoftware.accountability.view.accountability.JFResultState;
import co.com.soinsoftware.accountability.view.accountability.JFUap;
import co.com.soinsoftware.accountability.view.accountability.JFVoucher;
import co.com.soinsoftware.accountability.view.accountability.JFVoucherList;
import co.com.soinsoftware.accountability.view.accountability.JFVoucherType;
import co.com.soinsoftware.accountability.view.accountability.JFVoucherTypeXCompany;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.3
 */
public class MenuController {

	private static final String ACCOUNTABILITY_MODULE = "ACCOU";

	private final Set<Appxmodule> appXModuleSet;

	private final Company company;
	
	private final JFAccountability accountabilityFrame;

	private final JFBalance balanceFrame;

	private final JFDailyBook dailyBookFrame;

	private final JFLedger ledgerFrame;

	private final JFResultState resultStateFrame;

	private final JFUap uapFrame;

	private final JFUser userFrame;

	private final JFVoucher voucherFrame;

	private final JFVoucherList voucherListFrame;

	private final JFVoucherType voucherTypeFrame;

	private final JFVoucherTypeXCompany voucherTypeXCompFrame;

	private final User loggedUser;

	public MenuController(final Company company, final User user) {
		super();
		this.appXModuleSet = this.selectAppXModuleSet();
		this.company = company;
		this.loggedUser = user;
		this.accountabilityFrame = new JFAccountability(this.company);
		this.balanceFrame = new JFBalance();
		this.dailyBookFrame = new JFDailyBook(this.company);
		this.ledgerFrame = new JFLedger(this.company);
		this.resultStateFrame = new JFResultState();
		this.voucherFrame = new JFVoucher(this.loggedUser, this.company);
		this.uapFrame = new JFUap(this.loggedUser, this.company);
		this.voucherListFrame = new JFVoucherList(this.voucherFrame);
		this.userFrame = new JFUser();
		this.voucherTypeFrame = new JFVoucherType();
		this.voucherTypeXCompFrame = new JFVoucherTypeXCompany(this.loggedUser,
				this.company);
	}
	
	public void showAccountabilityFrame() {
		this.accountabilityFrame.refresh();
		this.accountabilityFrame.setVisible(true);
	}

	public void showBalanceFrame() {
		this.balanceFrame.refresh(company);
		this.balanceFrame.setVisible(true);
	}

	public void showDailyBookFrame() {
		this.dailyBookFrame.refresh();
		this.dailyBookFrame.setVisible(true);
	}

	public void showLedgerFrame() {
		this.ledgerFrame.refresh();
		this.ledgerFrame.setVisible(true);
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

	public boolean hasAccountabilityModule() {
		boolean hasAccess = false;
		if (this.appXModuleSet != null) {
			for (final Appxmodule appXMod : this.appXModuleSet) {
				final Module module = appXMod.getModule();
				if (appXMod.isEnabled() && module.isEnabled()
						&& module.getCode().equals(ACCOUNTABILITY_MODULE)) {
					hasAccess = true;
					break;
				}
			}
		}
		return hasAccess;
	}

	private Set<Appxmodule> selectAppXModuleSet() {
		final AppXModuleBLL appXModBLL = AppXModuleBLL.getInstance();
		return appXModBLL.select();
	}
}