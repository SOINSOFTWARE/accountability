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
 * @version 1.0
 */
public class JMBAppMenu extends JMenuBar implements ActionListener {

	private static final long serialVersionUID = 2306440560901177958L;

	private static final String MENU_CONFIGURATION = "Configuración";

	private static final String MENU_CONFIGURATION_COMPANY = "Empresa";

	private static final String MENU_CONFIGURATION_USER = "Usuario";

	private final MenuController controller;

	public JMBAppMenu(final MenuController controller) {
		super();
		this.addMenuConfiguration();
		this.controller = controller;
	}

	@Override
	public void actionPerformed(final ActionEvent evt) {
		final String actionCommand = evt.getActionCommand();
		switch (actionCommand) {
		case MENU_CONFIGURATION_COMPANY:
			this.showCompanyFrame();
			break;
		case MENU_CONFIGURATION_USER:
			this.showUserFrame();
			break;
		}
	}

	private void addMenuConfiguration() {
		final JMenu menu = new JMenu(MENU_CONFIGURATION);
		menu.setMnemonic(KeyEvent.VK_C);
		final JMenuItem miCompany = ViewUtils.createJMenuItem(
				MENU_CONFIGURATION_COMPANY, KeyEvent.VK_E,
				KeyStroke.getKeyStroke(KeyEvent.VK_6, ActionEvent.ALT_MASK));
		final JMenuItem miUser = ViewUtils.createJMenuItem(
				MENU_CONFIGURATION_USER, KeyEvent.VK_U,
				KeyStroke.getKeyStroke(KeyEvent.VK_7, ActionEvent.ALT_MASK));
		miCompany.addActionListener(this);
		miUser.addActionListener(this);
		menu.add(miCompany);
		menu.add(miUser);
		this.add(menu);
	}

	private void showCompanyFrame() {
		if (!this.controller.getCompanyFrame().isVisible()) {
			this.controller.getCompanyFrame().refresh();
			this.controller.getCompanyFrame().setVisible(true);
		}
	}

	private void showUserFrame() {
		if (!this.controller.getUserFrame().isVisible()) {
			this.controller.getUserFrame().refresh();
			this.controller.getUserFrame().setVisible(true);
		}
	}
}