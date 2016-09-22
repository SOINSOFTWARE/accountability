package co.com.soinsoftware.accountability.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import co.com.soinsoftware.accountability.controller.MenuController;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.3
 */
public class JMBAppMenu extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 2306440560901177958L;

	private static final String MENU_ACTION = "Movimientos";

	private static final String MENU_ACTION_NEW_VOUCHER = "Nuevo asiento";

	private static final String MENU_ACTION_VIEW_VOUCHER = "Ver asientos";
	
	private static final String MENU_ACTION_ACCOUNT_MONTH = "Cuentas mes actual";

	private static final String MENU_CONFIGURATION = "Configuración";

	private static final String MENU_CONFIGURATION_UNIQUE_ACCOUNT_PLAN = "Plan único de cuentas";

	private static final String MENU_CONFIGURATION_USER = "Usuarios";

	private static final String MENU_CONFIGURATION_VOUCHER_TYPE = "Comprobantes";

	private static final String MENU_CONFIGURATION_VOUCHER_TYPE_COMPANY = "Comprobantes por empresa";

	private static final String MENU_REPORTS = "Informes";

	private static final String MENU_REPORTS_BALANCE = "Balance general";

	private static final String MENU_REPORTS_DIARY_BOOK = "Libro diario";

	private static final String MENU_REPORTS_LEDGER = "Libro mayor";

	private static final String MENU_REPORTS_RESULT_STATE = "Estado de resultado";

	private final MenuController controller;

	public JMBAppMenu(final MenuController controller) {
		super();
		this.controller = controller;
		final boolean hasAccountabilityMod = this.controller
				.hasAccountabilityModule();
		if (hasAccountabilityMod) {
			this.addMenuAction();
			this.addMenuReports();
		}
		this.addMenuConfiguration(hasAccountabilityMod);
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		final String actionCommand = evt.getActionCommand();
		switch (actionCommand) {
		case MENU_ACTION_NEW_VOUCHER:
			this.controller.showVoucherFrame();
			break;
		case MENU_ACTION_VIEW_VOUCHER:
			this.controller.showVoucherListFrame();
			break;
		case MENU_ACTION_ACCOUNT_MONTH:
			this.controller.showAccountabilityFrame();
			break;
		case MENU_CONFIGURATION_UNIQUE_ACCOUNT_PLAN:
			this.controller.showUapFrame();
			break;
		case MENU_CONFIGURATION_USER:
			this.controller.showUserFrame();
			break;
		case MENU_CONFIGURATION_VOUCHER_TYPE:
			this.controller.showVoucherTypeFrame();
			break;
		case MENU_CONFIGURATION_VOUCHER_TYPE_COMPANY:
			this.controller.showVoucherTypeXCompanyFrame();
			break;
		case MENU_REPORTS_BALANCE:
			this.controller.showBalanceFrame();
			break;
		case MENU_REPORTS_DIARY_BOOK:
			this.controller.showDailyBookFrame();
			break;
		case MENU_REPORTS_LEDGER:
			this.controller.showLedgerFrame();
			break;
		case MENU_REPORTS_RESULT_STATE:
			this.controller.showResultStateFrame();
			break;
		}
	}

	private void addMenuAction() {
		final JMenu menu = new JMenu(MENU_ACTION);
		menu.setMnemonic(KeyEvent.VK_M);
		final JMenuItem miNewVoucher = ViewUtils.createJMenuItem(
				MENU_ACTION_NEW_VOUCHER, KeyEvent.VK_N,
				KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		miNewVoucher.addActionListener(this);
		menu.add(miNewVoucher);
		final JMenuItem miViewVoucher = ViewUtils.createJMenuItem(
				MENU_ACTION_VIEW_VOUCHER, KeyEvent.VK_V,
				KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
		miViewVoucher.addActionListener(this);
		menu.add(miViewVoucher);
		final JMenuItem miViewAccountability = ViewUtils.createJMenuItem(
				MENU_ACTION_ACCOUNT_MONTH, KeyEvent.VK_M,
				KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
		miViewAccountability.addActionListener(this);
		menu.add(miViewAccountability);
		this.add(menu);
	}

	private void addMenuConfiguration(final boolean hasAccountabilityMod) {
		final JMenu menu = new JMenu(MENU_CONFIGURATION);
		menu.setMnemonic(KeyEvent.VK_C);
		if (hasAccountabilityMod) {
			if (!this.controller.isAuxRol()) {
				final JMenuItem miVoucherType = ViewUtils.createJMenuItem(
						MENU_CONFIGURATION_VOUCHER_TYPE, KeyEvent.VK_C, null);
				miVoucherType.addActionListener(this);
				menu.add(miVoucherType);
			}
			final JMenuItem miVoucherTypeXComp = ViewUtils.createJMenuItem(
					MENU_CONFIGURATION_VOUCHER_TYPE_COMPANY, KeyEvent.VK_X,
					null);
			miVoucherTypeXComp.addActionListener(this);
			menu.add(miVoucherTypeXComp);
			final JMenuItem miUap = ViewUtils
					.createJMenuItem(MENU_CONFIGURATION_UNIQUE_ACCOUNT_PLAN,
							KeyEvent.VK_P, null);
			miUap.addActionListener(this);
			menu.add(miUap);
		}
		if (this.controller.isAdminRol()) {
			final JMenuItem miUser = ViewUtils.createJMenuItem(
					MENU_CONFIGURATION_USER, KeyEvent.VK_U, null);
			miUser.addActionListener(this);
			menu.add(miUser);
		}
		this.add(menu);
	}

	private void addMenuReports() {
		if (!this.controller.isAuxRol()) {
			final JMenu menu = new JMenu(MENU_REPORTS);
			menu.setMnemonic(KeyEvent.VK_I);
			final JMenuItem miBalance = ViewUtils
					.createJMenuItem(MENU_REPORTS_BALANCE, KeyEvent.VK_B,
							KeyStroke.getKeyStroke(KeyEvent.VK_4,
									ActionEvent.ALT_MASK));
			miBalance.addActionListener(this);
			menu.add(miBalance);
			final JMenuItem miResultState = ViewUtils
					.createJMenuItem(MENU_REPORTS_RESULT_STATE, KeyEvent.VK_E,
							KeyStroke.getKeyStroke(KeyEvent.VK_5,
									ActionEvent.ALT_MASK));
			miResultState.addActionListener(this);
			menu.add(miResultState);
			final JMenuItem miDiaryBook = ViewUtils
					.createJMenuItem(MENU_REPORTS_DIARY_BOOK, KeyEvent.VK_D,
							KeyStroke.getKeyStroke(KeyEvent.VK_6,
									ActionEvent.ALT_MASK));
			miDiaryBook.addActionListener(this);
			menu.add(miDiaryBook);
			final JMenuItem miLedger = ViewUtils
					.createJMenuItem(MENU_REPORTS_LEDGER, KeyEvent.VK_M,
							KeyStroke.getKeyStroke(KeyEvent.VK_7,
									ActionEvent.ALT_MASK));
			miLedger.addActionListener(this);
			menu.add(miLedger);
			this.add(menu);
		}
	}
}