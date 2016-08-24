/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.VoucherController;
import co.com.soinsoftware.accountability.controller.VoucherTypeController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Vouchertype;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;
import co.com.soinsoftware.accountability.util.VoucherListTableModel;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class JFVoucherList extends JDialog {

	private static final long serialVersionUID = -8645458407822046871L;

	private static final String MSG_VOUCHER_REQUIRED = "Seleccione un asiento contable del listado para ver el detalle";

	private final JFVoucher voucherFrame;

	private final VoucherController voucherController;

	private final VoucherTypeController voucherTypeController;

	private Company company;

	private List<Vouchertypexcompany> voucherTypeXCompList;

	public JFVoucherList(final JFVoucher voucherFrame) {
		this.voucherFrame = voucherFrame;
		this.voucherController = new VoucherController();
		this.voucherTypeController = new VoucherTypeController();
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 350),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
		this.setMonthModel();
	}

	public void refresh(final Company company) {
		this.company = company;
		this.jtfCompanyName.setText(this.company.getName());
		this.jtfYear.setText(String.valueOf(Calendar.getInstance().get(
				Calendar.YEAR)));
		this.setJlsMonthToCurrentMonth();
		this.setVoucherTypeModel();
		this.refreshTableData();
	}

	public void refreshTableData() {
		final int year = this.getYear();
		final int month = this.jlsMonth.getSelectedIndex() + 1;
		final Vouchertypexcompany voucherTypeXComp = this
				.getVoucherTypeXCompanySelected();
		final List<Voucher> voucherList = this.voucherController.select(year,
				month, this.company, voucherTypeXComp);
		this.refreshTableData(voucherList);
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

	private void setVoucherTypeModel() {
		this.voucherTypeXCompList = this.voucherTypeController
				.selectVoucherTypesXCompany(company);
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione uno...");
		for (final Vouchertypexcompany voucherTypeXComp : this.voucherTypeXCompList) {
			final Vouchertype voucherType = voucherTypeXComp.getVouchertype();
			model.addElement(voucherType.getName());
		}
		this.jcbVoucherType.setModel(model);
	}

	private void refreshTableData(final List<Voucher> voucherList) {
		final TableModel model = new VoucherListTableModel(voucherList);
		this.jtbVoucherList.setModel(model);
		this.jtbVoucherList.setFillsViewportHeight(true);
	}

	private int getYear() {
		final String yearStr = this.jtfYear.getText().replace(".", "")
				.replace(",", "");
		return Integer.parseInt(yearStr);
	}

	private Vouchertypexcompany getVoucherTypeXCompanySelected() {
		Vouchertypexcompany voucherTypeXComp = null;
		final int index = this.jcbVoucherType.getSelectedIndex();
		if (index > 0) {
			voucherTypeXComp = this.voucherTypeXCompList.get(index - 1);
		}
		return voucherTypeXComp;
	}

	private Voucher getSelectedVoucher() {
		final int index = this.jtbVoucherList.getSelectedRow();
		final TableModel model = this.jtbVoucherList.getModel();
		return ((VoucherListTableModel) model).getSelectedVoucher(index);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		jpSearch = new javax.swing.JPanel();
		jlbYear = new javax.swing.JLabel();
		jtfYear = new javax.swing.JFormattedTextField();
		jlbMonth = new javax.swing.JLabel();
		jspMonth = new javax.swing.JScrollPane();
		jlsMonth = new javax.swing.JList<String>();
		jlbVoucherType = new javax.swing.JLabel();
		jbtSearch = new javax.swing.JButton();
		jcbVoucherType = new javax.swing.JComboBox<String>();
		jlbCompanyName = new javax.swing.JLabel();
		jtfCompanyName = new javax.swing.JTextField();
		lbImage = new javax.swing.JLabel();
		jpVoucherList = new javax.swing.JPanel();
		jspVoucherList = new javax.swing.JScrollPane();
		jtbVoucherList = new javax.swing.JTable();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();
		jbtView = new javax.swing.JButton();

		setTitle("Asientos contables");

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Asientos contables");

		javax.swing.GroupLayout jpTitleLayout = new javax.swing.GroupLayout(
				jpTitle);
		jpTitle.setLayout(jpTitleLayout);
		jpTitleLayout.setHorizontalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlbTitle)
						.addContainerGap(545, Short.MAX_VALUE)));
		jpTitleLayout.setVerticalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addGap(32, 32, 32)
						.addComponent(jlbTitle)
						.addContainerGap(34, Short.MAX_VALUE)));

		jpSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Busqueda",
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

		jlbVoucherType.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbVoucherType.setText("Comprobante:");

		jbtSearch.setBackground(new java.awt.Color(16, 135, 221));
		jbtSearch.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtSearch.setForeground(new java.awt.Color(255, 255, 255));
		jbtSearch.setText("Buscar");
		jbtSearch.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtSearch.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtSearchActionPerformed(evt);
			}
		});

		jcbVoucherType.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbCompanyName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCompanyName.setText("Empresa:");

		jtfCompanyName.setEditable(false);
		jtfCompanyName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		javax.swing.GroupLayout jpSearchLayout = new javax.swing.GroupLayout(
				jpSearch);
		jpSearch.setLayout(jpSearchLayout);
		jpSearchLayout
				.setHorizontalGroup(jpSearchLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpSearchLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpSearchLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jtfCompanyName)
														.addGroup(
																jpSearchLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpSearchLayout
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
																								jlbMonth)
																						.addComponent(
																								jspMonth,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								160,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jlbVoucherType)
																						.addGroup(
																								jpSearchLayout
																										.createParallelGroup(
																												javax.swing.GroupLayout.Alignment.TRAILING)
																										.addComponent(
																												jbtSearch,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addComponent(
																												jcbVoucherType,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												160,
																												javax.swing.GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								jlbCompanyName))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		jpSearchLayout
				.setVerticalGroup(jpSearchLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpSearchLayout
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
										.addComponent(jlbMonth)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jspMonth,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												102,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbVoucherType)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbVoucherType,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jbtSearch,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jpVoucherList.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Listado de asientos contables",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbVoucherList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspVoucherList.setViewportView(jtbVoucherList);

		javax.swing.GroupLayout jpVoucherListLayout = new javax.swing.GroupLayout(
				jpVoucherList);
		jpVoucherList.setLayout(jpVoucherListLayout);
		jpVoucherListLayout.setHorizontalGroup(jpVoucherListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspVoucherList));
		jpVoucherListLayout.setVerticalGroup(jpVoucherListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspVoucherList,
						javax.swing.GroupLayout.PREFERRED_SIZE, 0,
						Short.MAX_VALUE));

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

		jbtView.setBackground(new java.awt.Color(16, 135, 221));
		jbtView.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtView.setForeground(new java.awt.Color(255, 255, 255));
		jbtView.setText("Ver");
		jbtView.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtView.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtViewActionPerformed(evt);
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
						.addComponent(jbtView,
								javax.swing.GroupLayout.PREFERRED_SIZE, 97,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18)
						.addComponent(jbtClose,
								javax.swing.GroupLayout.PREFERRED_SIZE, 97,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
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
																jbtView,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpTitle, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										388,
										javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jpSearch,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jpVoucherList,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addGap(12, 12,
																		12))
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		jpAction,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addContainerGap()))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jpTitle,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jpVoucherList,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jpSearch,
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
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										35,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jbtSearchActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSearchActionPerformed
		this.refreshTableData();
	}// GEN-LAST:event_jbtSearchActionPerformed

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	private void jbtViewActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtViewActionPerformed
		final Voucher voucher = this.getSelectedVoucher();
		if (voucher != null) {
			this.voucherFrame.refresh(this.company, voucher);
			this.voucherFrame.setVoucherListFrame(this);
			this.voucherFrame.setVisible(true);
		} else {
			ViewUtils.showMessage(this, MSG_VOUCHER_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_jbtViewActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtSearch;
	private javax.swing.JButton jbtView;
	private javax.swing.JComboBox<String> jcbVoucherType;
	private javax.swing.JLabel jlbCompanyName;
	private javax.swing.JLabel jlbMonth;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JLabel jlbVoucherType;
	private javax.swing.JLabel jlbYear;
	private javax.swing.JList<String> jlsMonth;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpSearch;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JPanel jpVoucherList;
	private javax.swing.JScrollPane jspMonth;
	private javax.swing.JScrollPane jspVoucherList;
	private javax.swing.JTable jtbVoucherList;
	private javax.swing.JTextField jtfCompanyName;
	private javax.swing.JFormattedTextField jtfYear;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
