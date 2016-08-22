package co.com.soinsoftware.accountability.entity;

import java.util.Set;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class Report {

	private final Company company;

	private final String name;

	private final String formattedDate;

	private final Set<ReportItem> reportItemSet;

	public Report(final Company company, final String name,
			final String formattedDate, final Set<ReportItem> reportItemSet) {
		super();
		this.company = company;
		this.name = name;
		this.formattedDate = formattedDate;
		this.reportItemSet = reportItemSet;
	}

	public Company getCompany() {
		return company;
	}

	public String getName() {
		return name;
	}

	public String getFormattedDate() {
		return formattedDate;
	}

	public Set<ReportItem> getReportItemSet() {
		return reportItemSet;
	}
}