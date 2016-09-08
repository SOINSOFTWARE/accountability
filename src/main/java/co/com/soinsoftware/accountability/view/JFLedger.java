/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.NumberFormatter;

import co.com.soinsoftware.accountability.controller.LedgerReportController;
import co.com.soinsoftware.accountability.controller.VoucherController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Ledger;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.report.ReportGenerator;
import co.com.soinsoftware.accountability.util.LedgerTableModel;

/**
 * @author Carlos Rodriguez
 * @since 01/09/2016
 * @version 1.0
 */
public class JFLedger extends JDialog {

	public static final String RANGE_MONTH = "Mensual";

	public static final String RANGE_YEAR = "Anual";

	private static final long serialVersionUID = -3460235812032255314L;

	private final Company company;

	private final VoucherController voucherController;

	private final LedgerReportController ledgerReportController;

	public JFLedger(final Company company) {
		this.company = company;
		this.voucherController = new VoucherController();
		this.ledgerReportController = new LedgerReportController();
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 515),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
		this.setRangeModel();
		this.setMonthModel();
	}

	public void refresh() {
		this.jtfCompanyName.setText(this.company.getName());
		this.jtfYear.setText(String.valueOf(Calendar.getInstance().get(
				Calendar.YEAR)));
		this.jcbRange.setSelectedIndex(0);
		this.setJlsMonthToCurrentMonth();
		this.refreshTableData();
	}

	public void refreshTableData() {
		final Ledger ledgerReport = this.builReport();
		this.refreshTableData(ledgerReport);
		this.setTotalValuesAndDescription();
	}

	private Ledger builReport() {
		final int year = this.getYear();
		final String rangeSel = (String) this.jcbRange.getSelectedItem();
		final int month = (rangeSel.equals(RANGE_MONTH)) ? this.jlsMonth
				.getSelectedIndex() + 1 : -1;
		final String monthName = this.jlsMonth.getSelectedValue();
		final List<Voucher> voucherList = this.voucherController.select(year,
				month, this.company, null);
		final String description = this.ledgerReportController
				.getReportDateDescription(rangeSel, year, month, monthName);
		final Ledger report = this.ledgerReportController.buildReport(
				this.company, voucherList, description);
		return report;
	}

	private void setJlsMonthToCurrentMonth() {
		final Calendar calendar = Calendar.getInstance();
		final int month = calendar.get(Calendar.MONTH);
		this.jlsMonth.setSelectedIndex(month);
	}

	private void setMonthModel() {
		final String[] months = { "Enero", "Febrero", "Marzo", "Abril", "Mayo",
				"Junio", "Julio", "Agosto", "Septiembre", "Octubre",
				"Noviembre", "Diciembre" };
		this.jlsMonth.setListData(months);
	}

	private void setRangeModel() {
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement(RANGE_MONTH);
		model.addElement(RANGE_YEAR);
		this.jcbRange.setModel(model);
	}

	private void setMonthObjectEditable(final boolean enabled) {
		this.jlsMonth.setEnabled(enabled);
	}

	private void refreshTableData(final Ledger report) {
		final TableModel model = new LedgerTableModel(report);
		this.jtbReport.setModel(model);
		this.jtbReport.setFillsViewportHeight(true);
		this.setTableColumnDimensions();
	}

	private void setTableColumnDimensions() {
		final TableColumnModel columnModel = this.jtbReport.getColumnModel();
		final int columnCount = columnModel.getColumnCount();
		for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			final TableColumn column = columnModel.getColumn(columnIndex);
			column.setResizable(false);
			if (columnIndex == 3) {
				column.setPreferredWidth(100);
			} else if (columnIndex >= 4) {
				column.setPreferredWidth(100);
			} else if (columnIndex == 0 || columnIndex == 2) {
				column.setPreferredWidth(50);
			} else if (columnIndex == 1) {
				column.setPreferredWidth(208);
			}
		}
	}

	private int getYear() {
		final String yearStr = this.jtfYear.getText().replace(".", "")
				.replace(",", "");
		return Integer.parseInt(yearStr);
	}

	private void setTotalValuesAndDescription() {
		final Ledger report = this.getLedgerFromTableModel();
		this.jlbTotalDebt.setText(this.formatValue(report.getTotalDebt()));
		this.jlbTotalCredit.setText(this.formatValue(report.getTotalCredit()));
		this.jlbReportRange.setText(report.getFormattedDate());
	}

	private String formatValue(final long value) {
		final NumberFormatter formatter = new NumberFormatter(
				new DecimalFormat("$#,##0"));
		try {
			return formatter.valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(value);
	}

	private Ledger getLedgerFromTableModel() {
		final TableModel model = this.jtbReport.getModel();
		return ((LedgerTableModel) model).getLedgerReport();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		jpBuild = new javax.swing.JPanel();
		jlbYear = new javax.swing.JLabel();
		jtfYear = new javax.swing.JFormattedTextField();
		jlbMonth = new javax.swing.JLabel();
		jspMonth = new javax.swing.JScrollPane();
		jlsMonth = new javax.swing.JList<String>();
		jbtGenerate = new javax.swing.JButton();
		jcbRange = new javax.swing.JComboBox<String>();
		jlbRange = new javax.swing.JLabel();
		jtfCompanyName = new javax.swing.JTextField();
		jlbCompanyName = new javax.swing.JLabel();
		jpReport = new javax.swing.JPanel();
		jspReport = new javax.swing.JScrollPane();
		jtbReport = new javax.swing.JTable();
		jlbReportRange = new javax.swing.JLabel();
		jlbTotalDebt = new javax.swing.JLabel();
		jlbTotalCredit = new javax.swing.JLabel();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();
		jbtPrint = new javax.swing.JButton();
		lbImage = new javax.swing.JLabel();

		setTitle("Libro mayor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/accountability.png")));

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Libro mayor");

		javax.swing.GroupLayout jpTitleLayout = new javax.swing.GroupLayout(
				jpTitle);
		jpTitle.setLayout(jpTitleLayout);
		jpTitleLayout.setHorizontalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jlbTitle)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		jpTitleLayout.setVerticalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addGap(32, 32, 32)
						.addComponent(jlbTitle)
						.addContainerGap(34, Short.MAX_VALUE)));

		jpBuild.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Generar",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jlbYear.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbYear.setText("Año:");

		jtfYear.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
				new javax.swing.text.NumberFormatter(
						new java.text.DecimalFormat("####0"))));
		jtfYear.setText(String.valueOf(Calendar.getInstance()
				.get(Calendar.YEAR)));
		jtfYear.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbMonth.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbMonth.setText("Mes:");

		jspMonth.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlsMonth.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspMonth.setViewportView(jlsMonth);

		jbtGenerate.setBackground(new java.awt.Color(16, 135, 221));
		jbtGenerate.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtGenerate.setForeground(new java.awt.Color(255, 255, 255));
		jbtGenerate.setText("Generar");
		jbtGenerate.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtGenerate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtGenerateActionPerformed(evt);
			}
		});

		jcbRange.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jcbRange.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jcbRangeActionPerformed(evt);
			}
		});

		jlbRange.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbRange.setText("Rango:");

		jtfCompanyName.setEditable(false);
		jtfCompanyName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbCompanyName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCompanyName.setText("Empresa:");

		javax.swing.GroupLayout jpBuildLayout = new javax.swing.GroupLayout(
				jpBuild);
		jpBuild.setLayout(jpBuildLayout);
		jpBuildLayout
				.setHorizontalGroup(jpBuildLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpBuildLayout
										.createSequentialGroup()
										.addGroup(
												jpBuildLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jpBuildLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				jpBuildLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jtfCompanyName)
																						.addComponent(
																								jlbCompanyName)))
														.addGroup(
																jpBuildLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpBuildLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jpBuildLayout
																										.createSequentialGroup()
																										.addGap(81,
																												81,
																												81)
																										.addComponent(
																												jbtGenerate,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								jpBuildLayout
																										.createSequentialGroup()
																										.addContainerGap()
																										.addGroup(
																												jpBuildLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																jlbYear)
																														.addComponent(
																																jtfYear,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																80,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																jlbRange)
																														.addComponent(
																																jcbRange,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																160,
																																javax.swing.GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																jlbMonth)
																														.addComponent(
																																jspMonth,
																																javax.swing.GroupLayout.PREFERRED_SIZE,
																																160,
																																javax.swing.GroupLayout.PREFERRED_SIZE))))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		jpBuildLayout
				.setVerticalGroup(jpBuildLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpBuildLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbCompanyName)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfCompanyName,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbYear)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfYear,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbRange)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbRange,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbMonth)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jspMonth,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												102,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jbtGenerate,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(28, Short.MAX_VALUE)));

		jpReport.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Libro mayor",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbReport.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspReport.setViewportView(jtbReport);

		jlbReportRange.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbReportRange.setText("Rango seleccionado + mes");

		jlbTotalDebt.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbTotalDebt.setText("Total debito:");

		jlbTotalCredit.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbTotalCredit.setText("Total credito:");

		javax.swing.GroupLayout jpReportLayout = new javax.swing.GroupLayout(
				jpReport);
		jpReport.setLayout(jpReportLayout);
		jpReportLayout
				.setHorizontalGroup(jpReportLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpReportLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpReportLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jspReport,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																766,
																Short.MAX_VALUE)
														.addGroup(
																jpReportLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlbReportRange)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap())
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpReportLayout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(jlbTotalDebt)
										.addGap(18, 18, 18)
										.addComponent(jlbTotalCredit)
										.addGap(234, 234, 234)));
		jpReportLayout
				.setVerticalGroup(jpReportLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpReportLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbReportRange)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jspReport,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												293, Short.MAX_VALUE)
										.addGap(17, 17, 17)
										.addGroup(
												jpReportLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jlbTotalCredit)
														.addComponent(
																jlbTotalDebt))));

		jpAction.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 0, 11))); // NOI18N

		jbtClose.setBackground(new java.awt.Color(16, 135, 221));
		jbtClose.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtClose.setForeground(new java.awt.Color(255, 255, 255));
		jbtClose.setText("Cerrar");
		jbtClose.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtClose.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtCloseActionPerformed(evt);
			}
		});

		jbtPrint.setBackground(new java.awt.Color(16, 135, 221));
		jbtPrint.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtPrint.setForeground(new java.awt.Color(255, 255, 255));
		jbtPrint.setText("Imprimir");
		jbtPrint.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtPrint.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtPrintActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jpActionLayout = new javax.swing.GroupLayout(
				jpAction);
		jpAction.setLayout(jpActionLayout);
		jpActionLayout.setHorizontalGroup(jpActionLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				jpActionLayout
						.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(jbtPrint,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jbtClose,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(22, 22, 22)));
		jpActionLayout
				.setVerticalGroup(jpActionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpActionLayout
										.createSequentialGroup()
										.addGap(23, 23, 23)
										.addGroup(
												jpActionLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jbtClose,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jbtPrint,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpTitle, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jpBuild,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		jpReport,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE))
												.addComponent(
														jpAction,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap())
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										388,
										javax.swing.GroupLayout.PREFERRED_SIZE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jpTitle,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jpReport,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jpBuild,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpAction,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										35,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jbtGenerateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtGenerateActionPerformed
		this.refreshTableData();
	}// GEN-LAST:event_jbtGenerateActionPerformed

	private void jcbRangeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbRangeActionPerformed
		final String valueSel = (String) this.jcbRange.getSelectedItem();
		this.setMonthObjectEditable(valueSel.equals(RANGE_MONTH));
	}// GEN-LAST:event_jcbRangeActionPerformed

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	private void jbtPrintActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtPrintActionPerformed
		final Ledger report = this.getLedgerFromTableModel();
		final ReportGenerator generator = new ReportGenerator(report);
		generator.start();
		this.setVisible(false);
	}// GEN-LAST:event_jbtPrintActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtGenerate;
	private javax.swing.JButton jbtPrint;
	private javax.swing.JComboBox<String> jcbRange;
	private javax.swing.JLabel jlbCompanyName;
	private javax.swing.JLabel jlbMonth;
	private javax.swing.JLabel jlbRange;
	private javax.swing.JLabel jlbReportRange;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JLabel jlbTotalCredit;
	private javax.swing.JLabel jlbTotalDebt;
	private javax.swing.JLabel jlbYear;
	private javax.swing.JList<String> jlsMonth;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpBuild;
	private javax.swing.JPanel jpReport;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JScrollPane jspMonth;
	private javax.swing.JScrollPane jspReport;
	private javax.swing.JTable jtbReport;
	private javax.swing.JTextField jtfCompanyName;
	private javax.swing.JFormattedTextField jtfYear;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
