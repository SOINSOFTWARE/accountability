package co.com.soinsoftware.accountability.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import co.com.soinsoftware.accountability.controller.BalanceReportController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Report;
import co.com.soinsoftware.accountability.entity.ReportItem;

public class AccountabilityReport {

	private static final String REPORT_NAME = "/reports/accountabilityReport.jasper";

	private static final String PARAM_CEO = "CEO";
	private static final String PARAM_COMPANY = "Company";
	private static final String PARAM_DOCUMENT = "Document";
	private static final String PARAM_DOCUMENT_CEO = "DocumentCEO";
	private static final String PARAM_IS_JURIDICA = "IsJuridica";
	private static final String PARAM_IS_BALANCE_REPORT = "IsBalanceReport";
	private static final String PARAM_REPORT_DATE = "ReportDate";
	private static final String PARAM_REPORT_NAME = "ReportName";
	private static final String PARAM_VALUE_PASS_PLUS_PAT = "ValuePassPat";

	private final Report report;

	private final boolean isBalanceReport;

	private final BalanceReportController balanceReportController;

	public AccountabilityReport(final Report balanceReport,
			final boolean isBalanceReport) {
		super();
		this.report = balanceReport;
		this.isBalanceReport = isBalanceReport;
		this.balanceReportController = new BalanceReportController();
	}

	public boolean generate() {
		boolean generated = false;
		try {
			System.out.println("Loading jasper file");
			final JasperReport jasReport = this.loadJasperReport();
			final Map<String, Object> parameters = this.createParameters();
			final JRBeanCollectionDataSource dataSource = this
					.createDataSource();
			System.out.println("Filling report");
			final JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasReport, parameters, dataSource);
			System.out.println("Starting show");
			JasperViewer.viewReport(jasperPrint, false);
		} catch (JRException ex) {
			System.out.println(ex);
		}
		return generated;
	}

	private JasperReport loadJasperReport() throws JRException {
		final InputStream resourceIS = this.getClass().getResourceAsStream(
				REPORT_NAME);
		return (JasperReport) JRLoader.loadObject(resourceIS);
	}

	private Map<String, Object> createParameters() {
		final Company company = this.report.getCompany();
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put(PARAM_COMPANY, company.getName());
		parameters.put(PARAM_DOCUMENT, company.getDocument());
		parameters.put(PARAM_REPORT_DATE, this.report.getFormattedDate());
		parameters.put(PARAM_REPORT_NAME, this.report.getName());
		String nameCEO = company.getName();
		String documentCEO = company.getDocument();
		if (company.getNameceo() != null && !company.getNameceo().equals("")) {
			nameCEO = company.getNameceo();
			documentCEO = String.valueOf(company.getDocumentceo());
		}
		parameters.put(PARAM_IS_JURIDICA, company.getCompanytype().getName()
				.equals("Persona jurídica"));
		parameters.put(PARAM_IS_BALANCE_REPORT, this.isBalanceReport);
		parameters.put(PARAM_CEO, nameCEO);
		parameters.put(PARAM_DOCUMENT_CEO, documentCEO);
		if (isBalanceReport) {
			parameters.put(PARAM_VALUE_PASS_PLUS_PAT,
					this.getValueForPassivePlusPatrimonio());
		}
		return parameters;
	}

	private long getValueForPassivePlusPatrimonio() {
		final long valueForPasivo = this.balanceReportController
				.getReportItemValue(this.report,
						BalanceReportController.CLASS_PASIVO);
		final long valueForPatrimonio = this.balanceReportController
				.getReportItemValue(this.report,
						BalanceReportController.CLASS_PATRIMONIO);
		return valueForPasivo + valueForPatrimonio;
	}

	private JRBeanCollectionDataSource createDataSource() {
		final Set<ReportItem> itemSet = this.report.getReportItemSet();
		final List<ReportItem> itemList = this.buildReportItemList(this
				.sortReportItemSet(itemSet));
		return new JRBeanCollectionDataSource(itemList);
	}

	private List<ReportItem> buildReportItemList(
			final List<ReportItem> childrenItemList) {
		final List<ReportItem> itemList = new ArrayList<ReportItem>();
		for (final ReportItem item : childrenItemList) {
			itemList.add(item);
			itemList.addAll(this.buildReportItemList(this
					.sortReportItemSet(item.getReportItemSet())));
		}
		return itemList;
	}

	private List<ReportItem> sortReportItemSet(
			final Set<ReportItem> reportItemSet) {
		List<ReportItem> reportItemList = new ArrayList<>();
		if (reportItemSet != null && reportItemSet.size() > 0) {
			reportItemList = new ArrayList<>(reportItemSet);
			Collections.sort(reportItemList);
		}
		return reportItemList;
	}
}