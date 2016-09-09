package co.com.soinsoftware.accountability.controller;

import java.util.Calendar;
import java.util.Date;

import co.com.soinsoftware.accountability.bll.UserBLL;
import co.com.soinsoftware.accountability.encryption.AESencrp;
import co.com.soinsoftware.accountability.entity.User;

/**
 * @author Carlos Rodriguez
 * @since 08/09/2016
 * @version 1.0
 */
public class LicenseController {

	private final UserBLL userBLL;

	private final User soinUser;

	public LicenseController() {
		super();
		this.userBLL = UserBLL.getInstance();
		this.soinUser = this.selectSoinUser();
	}

	public boolean isEmptyLicense() {
		return (this.soinUser.getFinaldate() == null);
	}

	public boolean isExpiredLicense() {
		final Date finalDate = this.soinUser.getFinaldate();
		return (finalDate != null && finalDate.before(new Date()));
	}

	public boolean isValidLicense(final String license) {
		final int year = Calendar.getInstance().get(Calendar.YEAR);
		final String validator = this.soinUser.getValidator();
		final String decLicense = AESencrp.decrypt(license);
		final int lastPad = decLicense.lastIndexOf("|");
		try {
			final String licenseYearStr = decLicense.substring(lastPad + 1);
			final int licenseYear = Integer.parseInt(licenseYearStr);
			return decLicense.substring(0, lastPad).equals(validator)
					&& licenseYear > year;
		} catch (IndexOutOfBoundsException | NumberFormatException ex) {
			System.out.println("Invalid license: " + ex.getMessage());
			return false;
		}
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
		this.soinUser.setFinaldate(calendar.getTime());
		this.soinUser.setUpdated(new Date());
		this.userBLL.save(this.soinUser);
	}

	private User selectSoinUser() {
		return this.userBLL.select("admin", "Pyme#2016");
	}
}