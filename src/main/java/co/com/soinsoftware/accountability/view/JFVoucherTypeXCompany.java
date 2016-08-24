/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.CompanyController;
import co.com.soinsoftware.accountability.controller.VoucherTypeController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Vouchertype;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;
import co.com.soinsoftware.accountability.util.VoucherTypesXCompaniesTableModel;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class JFVoucherTypeXCompany extends JDialog {

	private static final long serialVersionUID = 3504640238077217283L;

	private static final String MSG_COMPANY_REQUIRED = "Seleccione una empresa";

	private static final String MSG_INITIAL_RANGE_REQUIRED = "El rango inicial debe ser mayor a 0";

	private static final String MSG_FINAL_RANGE_REQUIRED = "El rango final debe ser mayor al rango inicial";

	private static final String MSG_VOUCHER_TYPE_REQUIRED = "Seleccione un comprobante";

	private final VoucherTypeController voucherTypeController;

	private final CompanyController companyController;

	private List<Company> companyList;

	private List<Vouchertype> voucherTypeList;

	public JFVoucherTypeXCompany() {
		this.companyController = new CompanyController();
		this.voucherTypeController = new VoucherTypeController();
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 500),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
	}

	public void refresh() {
		this.jtfNumberFrom.setText("0");
		this.jtfNumberTo.setText("0");
		this.setCompanyModel();
		this.setCompanySelectedItem();
		this.setVoucherTypeModel();
		this.jcbVoucherType.setSelectedIndex(0);
		this.refreshTableData();
	}

	private void setCompanyModel() {
		this.companyList = this.companyController.selectCompanies();
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione uno...");
		if (this.companyList != null && this.companyList.size() > 0) {
			for (final Company company : this.companyList) {
				model.addElement(company.getName());
			}
		}
		this.jcbCompany.setModel(model);
	}

	private void setVoucherTypeModel() {
		final Company company = this.getCompanySelected();
		this.voucherTypeList = this.voucherTypeController
				.selectVoucherTypes(company);
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione uno...");
		if (this.voucherTypeList != null && this.voucherTypeList.size() > 0) {
			for (final Vouchertype vouchertype : this.voucherTypeList) {
				model.addElement(vouchertype.getName());
			}
		}
		this.jcbVoucherType.setModel(model);
	}

	private void setCompanySelectedItem() {
		if (this.jcbCompany.getItemCount() == 2) {
			this.jcbCompany.setSelectedIndex(1);
			this.jcbCompany.setEnabled(false);
		} else {
			this.jcbCompany.setSelectedIndex(0);
			this.jcbCompany.setEnabled(true);
		}
	}

	private void refreshTableData() {
		final List<Vouchertypexcompany> voucherTypeXCompList = this.voucherTypeController
				.selectVoucherTypesXCompany();
		final TableModel model = new VoucherTypesXCompaniesTableModel(
				voucherTypeXCompList);
		this.jtbVoucherTypeList.setModel(model);
		this.jtbVoucherTypeList.setFillsViewportHeight(true);
	}

	private Company getCompanySelected() {
		Company company = null;
		final int index = this.jcbCompany.getSelectedIndex();
		if (index > 0) {
			company = this.companyList.get(index - 1);
		}
		return company;
	}

	private Vouchertype getVoucherTypeSelected() {
		Vouchertype voucherType = null;
		final int index = this.jcbVoucherType.getSelectedIndex();
		if (index > 0) {
			voucherType = this.voucherTypeList.get(index - 1);
		}
		return voucherType;
	}

	private long getNumberFromValue() {
		String valStr = this.jtfNumberFrom.getText().replace(".", "")
				.replace(",", "");
		if (valStr.trim().equals("")) {
			valStr = "0";
		}
		return Long.valueOf(valStr);
	}

	private long getNumberToValue() {
		String valStr = this.jtfNumberTo.getText().replace(".", "")
				.replace(",", "");
		if (valStr.trim().equals("")) {
			valStr = "0";
		}
		return Long.valueOf(valStr);
	}

	private boolean validateDataForSave() {
		boolean valid = true;
		final Company company = this.getCompanySelected();
		final Vouchertype voucherType = this.getVoucherTypeSelected();
		final long numberFrom = this.getNumberFromValue();
		final long numberTo = this.getNumberToValue();
		if (company == null) {
			valid = false;
			ViewUtils.showMessage(this, MSG_COMPANY_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (voucherType == null) {
			valid = false;
			ViewUtils.showMessage(this, MSG_VOUCHER_TYPE_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (numberFrom == 0) {
			valid = false;
			ViewUtils.showMessage(this, MSG_INITIAL_RANGE_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (numberTo <= numberFrom) {
			valid = false;
			ViewUtils.showMessage(this, MSG_FINAL_RANGE_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
		return valid;
	}

	private List<Vouchertypexcompany> getVoucherTypeXCompListFromTable() {
		final TableModel model = this.jtbVoucherTypeList.getModel();
		return ((VoucherTypesXCompaniesTableModel) model)
				.getVoucherTypeXCompList();
	}

	private boolean hasVoucherTypeXCompanyToBeDeleted(
			final List<Vouchertypexcompany> voucherTypeXCompList) {
		boolean hasElements = false;
		for (final Vouchertypexcompany voucherTypeXComp : voucherTypeXCompList) {
			if (voucherTypeXComp.isDelete()) {
				hasElements = true;
				break;
			}
		}
		return hasElements;
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
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		lbImage = new javax.swing.JLabel();
		jpNewVoucherType = new javax.swing.JPanel();
		jbtSave = new javax.swing.JButton();
		jlbCompany = new javax.swing.JLabel();
		jcbCompany = new javax.swing.JComboBox<String>();
		jlbVoucherType = new javax.swing.JLabel();
		jcbVoucherType = new javax.swing.JComboBox<String>();
		jlbNumberFrom = new javax.swing.JLabel();
		jlbNumberTo = new javax.swing.JLabel();
		jtfNumberFrom = new javax.swing.JFormattedTextField();
		jtfNumberTo = new javax.swing.JFormattedTextField();
		jpVoucherTypeList = new javax.swing.JPanel();
		jpActionButtons = new javax.swing.JPanel();
		jbtDelete = new javax.swing.JButton();
		jspVoucherTypeList = new javax.swing.JScrollPane();
		jtbVoucherTypeList = new javax.swing.JTable();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();

		setTitle("Comprobantes por empresa");

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Comprobantes por empresa");

		javax.swing.GroupLayout jpTitleLayout = new javax.swing.GroupLayout(
				jpTitle);
		jpTitle.setLayout(jpTitleLayout);
		jpTitleLayout.setHorizontalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlbTitle)
						.addContainerGap(773, Short.MAX_VALUE)));
		jpTitleLayout.setVerticalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addGap(32, 32, 32)
						.addComponent(jlbTitle)
						.addContainerGap(34, Short.MAX_VALUE)));

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jpNewVoucherType.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "Nuevo comprobante",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jbtSave.setBackground(new java.awt.Color(16, 135, 221));
		jbtSave.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtSave.setForeground(new java.awt.Color(255, 255, 255));
		jbtSave.setText("Guardar");
		jbtSave.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtSave.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtSaveActionPerformed(evt);
			}
		});

		jlbCompany.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCompany.setText("Empresa:");

		jcbCompany.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jcbCompany.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jcbCompanyActionPerformed(evt);
			}
		});

		jlbVoucherType.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbVoucherType.setText("Comprobante:");

		jcbVoucherType.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbNumberFrom.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbNumberFrom.setText("Rango inicial:");

		jlbNumberTo.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbNumberTo.setText("Rango final:");

		jtfNumberFrom
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0"))));
		jtfNumberFrom.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jtfNumberTo
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0"))));
		jtfNumberTo.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		javax.swing.GroupLayout jpNewVoucherTypeLayout = new javax.swing.GroupLayout(
				jpNewVoucherType);
		jpNewVoucherType.setLayout(jpNewVoucherTypeLayout);
		jpNewVoucherTypeLayout
				.setHorizontalGroup(jpNewVoucherTypeLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewVoucherTypeLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpNewVoucherTypeLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jtfNumberTo)
														.addGroup(
																jpNewVoucherTypeLayout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)
																		.addComponent(
																				jbtSave,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpNewVoucherTypeLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpNewVoucherTypeLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING)
																						.addComponent(
																								jlbCompany,
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlbVoucherType,
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlbNumberFrom,
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlbNumberTo,
																								javax.swing.GroupLayout.Alignment.LEADING))
																		.addGap(0,
																				119,
																				Short.MAX_VALUE))
														.addComponent(
																jtfNumberFrom)
														.addComponent(
																jcbVoucherType,
																0,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jcbCompany,
																0,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jpNewVoucherTypeLayout
				.setVerticalGroup(jpNewVoucherTypeLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewVoucherTypeLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbCompany)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbCompany,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
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
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbNumberFrom)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfNumberFrom,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(11, 11, 11)
										.addComponent(jlbNumberTo)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfNumberTo,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												jbtSave,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		jpVoucherTypeList.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "Listado de comprobantes",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jpActionButtons.setBorder(javax.swing.BorderFactory
				.createTitledBorder(""));

		jbtDelete.setBackground(new java.awt.Color(16, 135, 221));
		jbtDelete.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtDelete.setForeground(new java.awt.Color(255, 255, 255));
		jbtDelete.setText("Eliminar");
		jbtDelete.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtDeleteActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jpActionButtonsLayout = new javax.swing.GroupLayout(
				jpActionButtons);
		jpActionButtons.setLayout(jpActionButtonsLayout);
		jpActionButtonsLayout.setHorizontalGroup(jpActionButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionButtonsLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jbtDelete,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jpActionButtonsLayout.setVerticalGroup(jpActionButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionButtonsLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jbtDelete,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(216, Short.MAX_VALUE)));

		jtbVoucherTypeList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspVoucherTypeList.setViewportView(jtbVoucherTypeList);

		javax.swing.GroupLayout jpVoucherTypeListLayout = new javax.swing.GroupLayout(
				jpVoucherTypeList);
		jpVoucherTypeList.setLayout(jpVoucherTypeListLayout);
		jpVoucherTypeListLayout
				.setHorizontalGroup(jpVoucherTypeListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpVoucherTypeListLayout
										.createSequentialGroup()
										.addComponent(
												jspVoucherTypeList,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												0, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpActionButtons,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpVoucherTypeListLayout.setVerticalGroup(jpVoucherTypeListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspVoucherTypeList,
						javax.swing.GroupLayout.PREFERRED_SIZE, 0,
						Short.MAX_VALUE)
				.addComponent(jpActionButtons,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

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
						.addComponent(jbtClose,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(22, 22, 22)));
		jpActionLayout.setVerticalGroup(jpActionLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpActionLayout
						.createSequentialGroup()
						.addGap(23, 23, 23)
						.addComponent(jbtClose,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

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
																		jpAction,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addContainerGap())
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jpNewVoucherType,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addGap(0,
																										360,
																										Short.MAX_VALUE)
																								.addComponent(
																										lbImage,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										388,
																										javax.swing.GroupLayout.PREFERRED_SIZE))
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										jpVoucherTypeList,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE)
																								.addContainerGap()))))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jpTitle,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(11, 11, 11)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jpNewVoucherType,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jpVoucherTypeList,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addGap(0,
																		0,
																		Short.MAX_VALUE)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpAction,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										35,
										javax.swing.GroupLayout.PREFERRED_SIZE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jcbCompanyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbCompanyActionPerformed
		this.setVoucherTypeModel();
		this.jcbVoucherType.setSelectedIndex(0);
	}// GEN-LAST:event_jcbCompanyActionPerformed

	private void jbtDeleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtDeleteActionPerformed
		final List<Vouchertypexcompany> voucherTypeXCompList = this
				.getVoucherTypeXCompListFromTable();
		if (voucherTypeXCompList != null
				&& this.hasVoucherTypeXCompanyToBeDeleted(voucherTypeXCompList)) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_DELETE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				for (final Vouchertypexcompany voucherTypeXComp : voucherTypeXCompList) {
					if (voucherTypeXComp.isDelete()) {
						voucherTypeXComp.setEnabled(false);
						voucherTypeXComp.setUpdated(new Date());
						this.voucherTypeController
								.saveVoucherTypeXCompany(voucherTypeXComp);
					}
				}
				ViewUtils.showMessage(this, ViewUtils.MSG_DELETED,
						ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
				this.refresh();
			}
		} else {
			ViewUtils.showMessage(this, ViewUtils.MSG_UNSELECTED,
					ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbtDeleteActionPerformed

	private void jbtSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSaveActionPerformed
		if (this.validateDataForSave()) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_SAVE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				final Company company = this.getCompanySelected();
				final Vouchertype voucherType = this.getVoucherTypeSelected();
				final long numberFrom = this.getNumberFromValue();
				final long numberTo = this.getNumberToValue();
				this.voucherTypeController.saveVoucherTypeXCompany(company,
						voucherType, numberFrom, numberTo);
				ViewUtils.showMessage(this, ViewUtils.MSG_SAVED,
						ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
				this.refresh();
			}
		}
	}// GEN-LAST:event_jbtSaveActionPerformed

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtDelete;
	private javax.swing.JButton jbtSave;
	private javax.swing.JComboBox<String> jcbCompany;
	private javax.swing.JComboBox<String> jcbVoucherType;
	private javax.swing.JLabel jlbCompany;
	private javax.swing.JLabel jlbNumberFrom;
	private javax.swing.JLabel jlbNumberTo;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JLabel jlbVoucherType;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionButtons;
	private javax.swing.JPanel jpNewVoucherType;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JPanel jpVoucherTypeList;
	private javax.swing.JScrollPane jspVoucherTypeList;
	private javax.swing.JTable jtbVoucherTypeList;
	private javax.swing.JFormattedTextField jtfNumberFrom;
	private javax.swing.JFormattedTextField jtfNumberTo;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
