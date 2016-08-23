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
import javax.swing.table.TableModel;
import javax.swing.text.NumberFormatter;

import co.com.soinsoftware.accountability.controller.BalanceReportController;
import co.com.soinsoftware.accountability.controller.VoucherController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Report;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.report.ThreadGenerator;
import co.com.soinsoftware.accountability.util.BalanceReportTableModel;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class JFBalance extends JDialog {

	public static final String RANGE_MONTH = "Mensual";

	public static final String RANGE_YEAR = "Anual";

	private static final long serialVersionUID = -3460235812032255314L;

	private final VoucherController voucherController;

	private final BalanceReportController balanceReportController;

	private Company company;

	public JFBalance() {
		this.voucherController = new VoucherController();
		this.balanceReportController = new BalanceReportController();
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 470),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
		this.setRangeModel();
		this.setMonthModel();
	}

	public void refresh(final Company company) {
		this.company = company;
		this.jtfCompanyName.setText(this.company.getName());
		this.jtfYear.setText(String.valueOf(Calendar.getInstance().get(
				Calendar.YEAR)));
		this.jcbRange.setSelectedIndex(0);
		this.setJlsMonthToCurrentMonth();
		this.refreshTableData();
	}

	public void refreshTableData() {
		final Report balanceReport = this.buildBalanceReport();
		this.refreshTableData(balanceReport);
		this.setBalanceValuesAndDescription(balanceReport);
	}

	private Report buildBalanceReport() {
		final int year = this.getYear();
		final String rangeSel = (String) this.jcbRange.getSelectedItem();
		final int month = (rangeSel.equals(RANGE_MONTH)) ? this.jlsMonth
				.getSelectedIndex() + 1 : -1;
		final String monthName = this.jlsMonth.getSelectedValue();
		final List<Voucher> voucherList = this.voucherController.select(year,
				month, this.company, null);
		final String description = this.balanceReportController
				.getBalanceReportDateDescription(rangeSel, year, month,
						monthName);
		final Report balanceReport = this.balanceReportController.buildBalance(
				this.company, voucherList, description);
		return balanceReport;
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

	private void refreshTableData(final Report balanceReport) {
		final TableModel model = new BalanceReportTableModel(balanceReport);
		this.jtbBalance.setModel(model);
		this.jtbBalance.setFillsViewportHeight(true);
	}

	private int getYear() {
		final String yearStr = this.jtfYear.getText().replace(".", "")
				.replace(",", "");
		return Integer.parseInt(yearStr);
	}

	private void setBalanceValuesAndDescription(final Report balanceReport) {
		final long valueForActivo = this.balanceReportController.getClassValue(
				balanceReport, BalanceReportController.CLASS_ACTIVO);
		final long valueForPasivo = this.balanceReportController.getClassValue(
				balanceReport, BalanceReportController.CLASS_PASIVO);
		final long valueForPatrimonio = this.balanceReportController
				.getClassValue(balanceReport,
						BalanceReportController.CLASS_PATRIMONIO);
		final long valueForPasAndPat = valueForPasivo + valueForPatrimonio;
		this.jlbTotalActivo.setText("Total Activo:"
				+ this.formatTotalValue(valueForActivo));
		this.jlbTotalPasivo.setText("Total Pasivo:"
				+ this.formatTotalValue(valueForPasivo));
		this.jlbTotalPatrimonio.setText("Total Patrimonio:"
				+ this.formatTotalValue(valueForPatrimonio));
		this.jlbTotalPasivoPatrimonio.setText("Total Pasivo + Patrimonio:"
				+ this.formatTotalValue(valueForPasAndPat));
		this.jlbBalanceRange.setText(balanceReport.getFormattedDate());
	}

	private String formatTotalValue(final long totalValue) {
		final boolean isPositive = (totalValue >= 0);
		final long value = (isPositive) ? totalValue : totalValue * -1;
		final String formattedValue = this.formatValue(value);
		final String valueStr = (isPositive) ? "$" + formattedValue : "($"
				+ formattedValue + ")";
		return valueStr;
	}

	private String formatValue(final long value) {
		final NumberFormatter formatter = new NumberFormatter(
				new DecimalFormat("#,##0"));
		try {
			return formatter.valueToString(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(value);
	}

	private Report getBalanceFromTableModel() {
		final TableModel model = this.jtbBalance.getModel();
		return ((BalanceReportTableModel) model).getBalanceReport();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		jpBuildBalance = new javax.swing.JPanel();
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
		jpBalance = new javax.swing.JPanel();
		jspBalance = new javax.swing.JScrollPane();
		jtbBalance = new javax.swing.JTable();
		jlbBalanceRange = new javax.swing.JLabel();
		jlbTotalActivo = new javax.swing.JLabel();
		jlbTotalPasivo = new javax.swing.JLabel();
		jlbTotalPatrimonio = new javax.swing.JLabel();
		jlbTotalPasivoPatrimonio = new javax.swing.JLabel();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();
		jbtPrint = new javax.swing.JButton();
		lbImage = new javax.swing.JLabel();

		setTitle("Balance general");

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Balance general");

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

		jpBuildBalance.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Generar",
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

		javax.swing.GroupLayout jpBuildBalanceLayout = new javax.swing.GroupLayout(
				jpBuildBalance);
		jpBuildBalance.setLayout(jpBuildBalanceLayout);
		jpBuildBalanceLayout
				.setHorizontalGroup(jpBuildBalanceLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpBuildBalanceLayout
										.createSequentialGroup()
										.addGroup(
												jpBuildBalanceLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jpBuildBalanceLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				jpBuildBalanceLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jtfCompanyName)
																						.addComponent(
																								jlbCompanyName)))
														.addGroup(
																jpBuildBalanceLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpBuildBalanceLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								jpBuildBalanceLayout
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
																								jpBuildBalanceLayout
																										.createSequentialGroup()
																										.addContainerGap()
																										.addGroup(
																												jpBuildBalanceLayout
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
		jpBuildBalanceLayout
				.setVerticalGroup(jpBuildBalanceLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpBuildBalanceLayout
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

		jpBalance.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Balance general",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbBalance.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspBalance.setViewportView(jtbBalance);

		jlbBalanceRange.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbBalanceRange.setText("Rango seleccionado + mes");

		jlbTotalActivo.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbTotalActivo.setText("Total Activo:");

		jlbTotalPasivo.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbTotalPasivo.setText("Total Pasivo:");

		jlbTotalPatrimonio.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbTotalPatrimonio.setText("Total Patrimonio:");

		jlbTotalPasivoPatrimonio.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbTotalPasivoPatrimonio.setText("Total Pasivo + Patrimonio:");

		javax.swing.GroupLayout jpBalanceLayout = new javax.swing.GroupLayout(
				jpBalance);
		jpBalance.setLayout(jpBalanceLayout);
		jpBalanceLayout
				.setHorizontalGroup(jpBalanceLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpBalanceLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpBalanceLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jpBalanceLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlbTotalPatrimonio)
																		.addGap(18,
																				18,
																				18)
																		.addComponent(
																				jlbTotalPasivoPatrimonio)
																		.addGap(0,
																				0,
																				Short.MAX_VALUE))
														.addGroup(
																jpBalanceLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpBalanceLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jspBalance,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								696,
																								Short.MAX_VALUE)
																						.addGroup(
																								jpBalanceLayout
																										.createSequentialGroup()
																										.addGroup(
																												jpBalanceLayout
																														.createParallelGroup(
																																javax.swing.GroupLayout.Alignment.LEADING)
																														.addComponent(
																																jlbBalanceRange)
																														.addComponent(
																																jlbTotalPasivo)
																														.addComponent(
																																jlbTotalActivo))
																										.addGap(0,
																												0,
																												Short.MAX_VALUE)))
																		.addContainerGap()))));
		jpBalanceLayout
				.setVerticalGroup(jpBalanceLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpBalanceLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbBalanceRange)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jspBalance,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												0, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jlbTotalActivo)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jlbTotalPasivo)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jpBalanceLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jlbTotalPatrimonio)
														.addComponent(
																jlbTotalPasivoPatrimonio))
										.addContainerGap()));

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
																		jpBuildBalance,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		jpBalance,
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
														jpBalance,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jpBuildBalance,
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
		final Report balanceReport = this.getBalanceFromTableModel();
		final ThreadGenerator generator = new ThreadGenerator(balanceReport);
		generator.start();
		this.setVisible(false);
	}// GEN-LAST:event_jbtPrintActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtGenerate;
	private javax.swing.JButton jbtPrint;
	private javax.swing.JComboBox<String> jcbRange;
	private javax.swing.JLabel jlbBalanceRange;
	private javax.swing.JLabel jlbCompanyName;
	private javax.swing.JLabel jlbMonth;
	private javax.swing.JLabel jlbRange;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JLabel jlbTotalActivo;
	private javax.swing.JLabel jlbTotalPasivo;
	private javax.swing.JLabel jlbTotalPasivoPatrimonio;
	private javax.swing.JLabel jlbTotalPatrimonio;
	private javax.swing.JLabel jlbYear;
	private javax.swing.JList<String> jlsMonth;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpBalance;
	private javax.swing.JPanel jpBuildBalance;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JScrollPane jspBalance;
	private javax.swing.JScrollPane jspMonth;
	private javax.swing.JTable jtbBalance;
	private javax.swing.JTextField jtfCompanyName;
	private javax.swing.JFormattedTextField jtfYear;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
