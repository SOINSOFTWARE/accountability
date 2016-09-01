package co.com.soinsoftware.accountability.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Ledger;
import co.com.soinsoftware.accountability.entity.LedgerItem;

/**
 * @author Carlos Rodriguez
 * @since 01/09/2016
 * @version 1.0
 */
public class LedgerReport {

	private static final String PARAM_TOTAL_CREDIT = "TotalCredit";

	private static final String PARAM_TOTAL_DEBT = "TotalDebt";

	private static final String REPORT_NAME = "/reports/ledgerReport.jasper";

	private final Ledger report;

	public LedgerReport(final Ledger report) {
		super();
		this.report = report;
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
		parameters.put(ReportGenerator.PARAM_COMPANY, company.getName());
		parameters.put(ReportGenerator.PARAM_DOCUMENT, company.getDocument());
		parameters.put(ReportGenerator.PARAM_REPORT_DATE,
				this.report.getFormattedDate());
		parameters
				.put(ReportGenerator.PARAM_REPORT_NAME, this.report.getName());
		String nameCEO = company.getName();
		String documentCEO = company.getDocument();
		if (company.getNameceo() != null && !company.getNameceo().equals("")) {
			nameCEO = company.getNameceo();
			documentCEO = String.valueOf(company.getDocumentceo());
		}
		parameters.put(ReportGenerator.PARAM_IS_JURIDICA, company
				.getCompanytype().getName().equals("Persona jurídica"));
		parameters.put(ReportGenerator.PARAM_CEO, nameCEO);
		parameters.put(ReportGenerator.PARAM_DOCUMENT_CEO, documentCEO);
		parameters.put(PARAM_TOTAL_DEBT, this.report.getTotalDebt());
		parameters.put(PARAM_TOTAL_CREDIT, this.report.getTotalCredit());
		return parameters;
	}

	private JRBeanCollectionDataSource createDataSource() {
		final Set<LedgerItem> itemSet = this.report.getLedgerItemSet();
		final List<LedgerItem> itemList = this.buildLedgerItemList(this
				.sortLedgerItemSet(itemSet));
		return new JRBeanCollectionDataSource(itemList);
	}

	private List<LedgerItem> buildLedgerItemList(
			final List<LedgerItem> childrenItemList) {
		final List<LedgerItem> itemList = new ArrayList<LedgerItem>();
		for (final LedgerItem item : childrenItemList) {
			itemList.add(item);
			itemList.addAll(item.getLedgerItemList());
		}
		return itemList;
	}

	private List<LedgerItem> sortLedgerItemSet(
			final Set<LedgerItem> ledgerItemSet) {
		List<LedgerItem> sortLedgerItemList = new ArrayList<>();
		if (ledgerItemSet != null && ledgerItemSet.size() > 0) {
			final List<LedgerItem> ledgerItemList = new ArrayList<>(
					ledgerItemSet);
			final Comparator<LedgerItem> byCode = (item1, item2) -> Long
					.compare(item1.getCode(), item2.getCode());
			final Comparator<LedgerItem> byDate = (item1, item2) -> item1
					.getDate().compareTo(item2.getDate());
			sortLedgerItemList = ledgerItemList.stream()
					.sorted(byCode.thenComparing(byDate))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return sortLedgerItemList;
	}
}