package co.com.soinsoftware.accountability;

import java.awt.EventQueue;

import javax.swing.UIManager;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import co.com.soinsoftware.accountability.controller.LicenseController;
import co.com.soinsoftware.accountability.dao.SessionController;
import co.com.soinsoftware.accountability.view.JFLicense;
import co.com.soinsoftware.accountability.view.JFLogin;
import co.com.soinsoftware.accountability.view.JFLogo;

/**
 * @author Carlos Rodriguez
 * @since 11/07/2016
 * @version 1.0
 */
public class AccountabilityApp {

	private static final String LOOK_AND_FEEL = "com.jtattoo.plaf.luna.LunaLookAndFeel";

	public static void main(String[] args) {
		final JFLogo logo = new JFLogo();
		final LicenseController controller = new LicenseController();
		logo.setVisible(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SessionController.getInstance();
					System.out.println("Initializing jasper report context");
					DefaultJasperReportsContext.getInstance();
					System.out
							.println("Finalizing initialization of jasper report context");
					UIManager.setLookAndFeel(LOOK_AND_FEEL);
					if (controller.isEmptyLicense()
							|| controller.isExpiredLicense()) {
						final JFLicense license = new JFLicense();
						license.setVisible(true);
					} else {
						final JFLogin login = new JFLogin();
						login.setVisible(true);
					}
					logo.setVisible(false);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}
