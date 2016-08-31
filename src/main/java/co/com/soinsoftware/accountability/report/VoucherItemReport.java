package co.com.soinsoftware.accountability.report;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Voucheritem;

/**
 * @author Carlos Rodriguez
 * @since 31/08/2016
 * @version 1.0
 */
public class VoucherItemReport {

	private static final String REPORT_NAME = "/reports/voucherItemReport.jasper";

	private static final String REPORT_TITLE = "LIBRO DIARIO";

	private final Company company;

	private final List<Voucheritem> voucherItemList;

	private final String formattedDate;

	public VoucherItemReport(final Company company, final String formattedDate,
			final List<Voucheritem> voucherItemList) {
		super();
		this.company = company;
		this.formattedDate = formattedDate;
		this.voucherItemList = voucherItemList;
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
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put(ReportGenerator.PARAM_COMPANY, this.company.getName());
		parameters.put(ReportGenerator.PARAM_DOCUMENT,
				this.company.getDocument());
		parameters.put(ReportGenerator.PARAM_REPORT_DATE, this.formattedDate);
		parameters.put(ReportGenerator.PARAM_REPORT_NAME, REPORT_TITLE);
		String nameCEO = this.company.getName();
		String documentCEO = this.company.getDocument();
		if (this.company.getNameceo() != null
				&& !this.company.getNameceo().equals("")) {
			nameCEO = this.company.getNameceo();
			documentCEO = String.valueOf(this.company.getDocumentceo());
		}
		parameters.put(ReportGenerator.PARAM_IS_JURIDICA, this.company
				.getCompanytype().getName().equals("Persona jurídica"));
		parameters.put(ReportGenerator.PARAM_CEO, nameCEO);
		parameters.put(ReportGenerator.PARAM_DOCUMENT_CEO, documentCEO);
		return parameters;
	}

	private JRBeanCollectionDataSource createDataSource() {
		return new JRBeanCollectionDataSource(this.voucherItemList);
	}
}