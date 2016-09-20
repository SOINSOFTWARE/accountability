package co.com.soinsoftware.accountability.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import co.com.soinsoftware.accountability.bll.AppBLL;
import co.com.soinsoftware.accountability.encryption.AESencrp;
import co.com.soinsoftware.accountability.entity.App;

/**
 * @author Carlos Rodriguez
 * @since 08/09/2016
 * @version 1.1
 */
public class LicenseController {

	private final AppBLL appBLL;

	private final App app;

	public LicenseController() {
		super();
		this.appBLL = AppBLL.getInstance();
		this.app = this.selectApp();
	}

	public boolean isEmptyLicense() {
		return (this.app == null || this.app.getFinaldate() == null);
	}

	public boolean isExpiredLicense() {
		final Date finalDate = this.app.getFinaldate();
		return (finalDate != null && finalDate.before(new Date()));
	}

	public boolean isValidLicense(final String license) {
		boolean valid = false;
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final String decLicense = AESencrp.decrypt(license);
		final int lastPad = decLicense.lastIndexOf("|");
		try {
			final String validator = this.app.getValidator();
			final String licenseYearStr = decLicense.substring(lastPad + 1);
			final int licenseYear = Integer.parseInt(licenseYearStr);
			valid = decLicense.substring(0, lastPad).equals(validator)
					&& licenseYear > year;
		} catch (IndexOutOfBoundsException | NumberFormatException
				| NullPointerException ex) {
			System.out.println("Invalid license: " + ex.getMessage());
		}
		return valid;
	}

	public String encryptLicenseData(final String data) {
		return AESencrp.encrypt(data);
	}

	public void saveLicense(final String license) {
		final String decLicense = AESencrp.decrypt(license);
		final int lastPad = decLicense.lastIndexOf("|");
		final String licenseYearStr = decLicense.substring(lastPad + 1);
		final int licenseYear = Integer.parseInt(licenseYearStr);
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, licenseYear);
		this.app.setFinaldate(calendar.getTime());
		this.app.setUpdated(new Date());
		this.appBLL.save(this.app);
	}

	private App selectApp() {
		App app = null;
		final Set<App> appSet = this.appBLL.select();
		if (appSet != null && appSet.size() > 0) {
			for (final App appInSet : appSet) {
				if (appInSet.isEnabled()) {
					app = appInSet;
					break;
				}
			}
		}
		return app;
	}
}