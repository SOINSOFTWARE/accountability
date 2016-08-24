package co.com.soinsoftware.accountability.controller;

import java.util.List;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Rol;
import co.com.soinsoftware.accountability.entity.User;
import co.com.soinsoftware.accountability.view.JFBalance;
import co.com.soinsoftware.accountability.view.JFCompany;
import co.com.soinsoftware.accountability.view.JFCompanyList;
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
 * @version 1.0
 */
public class MenuController {

	private final CompanyController companyController;

	private final JFBalance balanceFrame;

	private final JFCompany companyFrame;

	private final JFCompanyList companyListFrame;

	private final JFResultState resultStateFrame;

	private final JFUap uapFrame;

	private final JFUser userFrame;

	private final JFVoucher voucherFrame;

	private final JFVoucherList voucherListFrame;

	private final JFVoucherType voucherTypeFrame;

	private final JFVoucherTypeXCompany voucherTypeXCompFrame;

	private final User loggedUser;

	public MenuController(final User user) {
		super();
		this.loggedUser = user;
		this.companyController = new CompanyController();
		this.balanceFrame = new JFBalance();
		this.resultStateFrame = new JFResultState();
		this.companyFrame = new JFCompany();
		this.voucherFrame = new JFVoucher();
		this.uapFrame = new JFUap();
		this.voucherListFrame = new JFVoucherList(this.voucherFrame);
		this.companyListFrame = new JFCompanyList(this.balanceFrame,
				this.resultStateFrame, this.voucherFrame,
				this.voucherListFrame, this.uapFrame);
		this.userFrame = new JFUser();
		this.voucherTypeFrame = new JFVoucherType();
		this.voucherTypeXCompFrame = new JFVoucherTypeXCompany();
	}

	public void showBalanceFrame() {
		final List<Company> companyList = this.companyController
				.selectCompanies();
		if (companyList == null || companyList.size() > 1) {
			this.companyListFrame.refresh(JFCompanyList.BALANCE_FRAME);
			this.companyListFrame.setVisible(true);
		} else {
			this.uapFrame.refresh(companyList.get(0));
			this.balanceFrame.refresh(companyList.get(0));
			this.balanceFrame.setVisible(true);
		}
	}

	public void showCompanyFrame() {
		this.companyFrame.refresh();
		this.companyFrame.setVisible(true);
	}

	public void showResultStateFrame() {
		final List<Company> companyList = this.companyController
				.selectCompanies();
		if (companyList == null || companyList.size() > 1) {
			this.companyListFrame.refresh(JFCompanyList.RESULT_STATE_FRAME);
			this.companyListFrame.setVisible(true);
		} else {
			this.uapFrame.refresh(companyList.get(0));
			this.resultStateFrame.refresh(companyList.get(0));
			this.resultStateFrame.setVisible(true);
		}
	}

	public void showUserFrame() {
		this.userFrame.refresh();
		this.userFrame.setVisible(true);
	}

	public void showUapFrame() {
		final List<Company> companyList = this.companyController
				.selectCompanies();
		if (companyList == null || companyList.size() > 1) {
			this.companyListFrame.refresh(JFCompanyList.UAP_FRAME);
			this.companyListFrame.setVisible(true);
		} else {
			this.uapFrame.refresh(companyList.get(0));
			this.uapFrame.setVisible(true);
		}
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
		final List<Company> companyList = this.companyController
				.selectCompanies();
		if (companyList == null || companyList.size() > 1) {
			this.companyListFrame.refresh(JFCompanyList.VOUCHER_FRAME);
			this.companyListFrame.setVisible(true);
		} else {
			this.voucherFrame.refresh(companyList.get(0), null);
			this.voucherFrame.setVisible(true);
		}
	}

	public void showVoucherListFrame() {
		final List<Company> companyList = this.companyController
				.selectCompanies();
		if (companyList == null || companyList.size() > 1) {
			this.companyListFrame.refresh(JFCompanyList.VOUCHER_LIST_FRAME);
			this.companyListFrame.setVisible(true);
		} else {
			this.voucherListFrame.refresh(companyList.get(0));
			this.voucherListFrame.setVisible(true);
		}
	}

	public boolean isAdminRol() {
		final Rol rol = this.loggedUser.getRol();
		return rol.getCode().equals("ADMIN");
	}

	public boolean isAccountRol() {
		final Rol rol = this.loggedUser.getRol();
		return rol.getCode().equals("CONT");
	}
}