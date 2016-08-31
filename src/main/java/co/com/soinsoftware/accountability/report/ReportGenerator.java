package co.com.soinsoftware.accountability.report;

import java.util.List;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Report;
import co.com.soinsoftware.accountability.entity.Voucheritem;

/**
 * @author Carlos Rodriguez
 * @since 24/08/2016
 * @version 1.1
 */
public class ReportGenerator extends Thread {

	public static final String PARAM_CEO = "CEO";

	public static final String PARAM_COMPANY = "Company";

	public static final String PARAM_DOCUMENT = "Document";

	public static final String PARAM_DOCUMENT_CEO = "DocumentCEO";

	public static final String PARAM_IS_JURIDICA = "IsJuridica";

	public static final String PARAM_IS_BALANCE_REPORT = "IsBalanceReport";

	public static final String PARAM_REPORT_DATE = "ReportDate";

	public static final String PARAM_REPORT_NAME = "ReportName";

	public static final String PARAM_VALUE_PASS_PLUS_PAT = "ValuePassPat";

	private AccountabilityReport accountabilityReport;

	private VoucherItemReport voucherItemReport;

	public ReportGenerator() {
		super();
		this.setPriority(Thread.MAX_PRIORITY);
		this.setName("Report Generator");
	}

	public ReportGenerator(final Report report, final boolean isBalanceReport) {
		this();
		this.accountabilityReport = new AccountabilityReport(report,
				isBalanceReport);

	}

	public ReportGenerator(final Company company, final String formattedDate,
			final List<Voucheritem> voucherItemList) {
		this();
		this.voucherItemReport = new VoucherItemReport(company, formattedDate,
				voucherItemList);
	}

	@Override
	public void run() {
		System.out.println("Starting report generation");
		if (this.accountabilityReport != null) {
			this.accountabilityReport.generate();
		} else if (this.voucherItemReport != null) {
			this.voucherItemReport.generate();
		}
		System.out.println("Finishing report generation");
	}
}