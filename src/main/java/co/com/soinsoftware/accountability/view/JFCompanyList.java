/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.CompanyController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.util.CompanyListTableModel;

/**
 * @author Carlos Rodriguez
 * @since 10/08/2016
 * @version 1.0
 */
public class JFCompanyList extends JDialog {

	private static final long serialVersionUID = 6702110008108578490L;

	private static final String MSG_COMPANY_REQUIRED = "Seleccione una empresa del listado para continuar";

	private final CompanyController companyController;

	private final JFBalance balanceFrame;

	private final JFVoucher voucherFrame;

	private final JFVoucherList voucherListFrame;

	private final JFUap uapFrame;

	private int frameSelected;

	public static final int VOUCHER_FRAME = 0;

	public static final int VOUCHER_LIST_FRAME = 1;

	public static final int UAP_FRAME = 2;

	public static final int BALANCE_FRAME = 3;

	public JFCompanyList(final JFBalance balanceFrame,
			final JFVoucher voucherFrame, final JFVoucherList voucherListFrame,
			final JFUap uapFrame) {
		this.companyController = new CompanyController();
		this.balanceFrame = balanceFrame;
		this.voucherFrame = voucherFrame;
		this.voucherListFrame = voucherListFrame;
		this.uapFrame = uapFrame;
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 310),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
	}

	public void refresh(final int frameSelected) {
		this.frameSelected = frameSelected;
		this.refreshTableData();
	}

	private void refreshTableData() {
		final List<Company> companyList = this.companyController
				.selectCompanies();
		final TableModel model = new CompanyListTableModel(companyList);
		this.jtbCompanyList.setModel(model);
		this.jtbCompanyList.setFillsViewportHeight(true);
	}

	private Company getSelectedCompany() {
		final int index = this.jtbCompanyList.getSelectedRow();
		final TableModel model = this.jtbCompanyList.getModel();
		return ((CompanyListTableModel) model).getSelectedCompany(index);
	}

	private void showVoucherFrame(final Company company) {
		this.voucherFrame.refresh(company, null);
		this.voucherFrame.setVisible(true);
	}

	private void showVoucherListFrame(final Company company) {
		this.voucherListFrame.refresh(company);
		this.voucherListFrame.setVisible(true);
	}

	private void showUapFrame(final Company company) {
		this.uapFrame.refresh(company);
		this.uapFrame.setVisible(true);
	}

	private void showBalanceFrame(final Company company) {
		this.uapFrame.refresh(company);
		this.balanceFrame.refresh(company);
		this.balanceFrame.setVisible(true);
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
		lbImage = new javax.swing.JLabel();
		jpCompanyList = new javax.swing.JPanel();
		jspCompanyList = new javax.swing.JScrollPane();
		jtbCompanyList = new javax.swing.JTable();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();
		jbtSelect = new javax.swing.JButton();

		setTitle("Contabilidad");

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Seleccione una empresa");

		javax.swing.GroupLayout jpTitleLayout = new javax.swing.GroupLayout(
				jpTitle);
		jpTitle.setLayout(jpTitleLayout);
		jpTitleLayout.setHorizontalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlbTitle)
						.addContainerGap(404, Short.MAX_VALUE)));
		jpTitleLayout.setVerticalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addGap(32, 32, 32)
						.addComponent(jlbTitle)
						.addContainerGap(34, Short.MAX_VALUE)));

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jpCompanyList.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Listado de empresas",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbCompanyList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspCompanyList.setViewportView(jtbCompanyList);

		javax.swing.GroupLayout jpCompanyListLayout = new javax.swing.GroupLayout(
				jpCompanyList);
		jpCompanyList.setLayout(jpCompanyListLayout);
		jpCompanyListLayout.setHorizontalGroup(jpCompanyListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspCompanyList));
		jpCompanyListLayout.setVerticalGroup(jpCompanyListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspCompanyList,
						javax.swing.GroupLayout.DEFAULT_SIZE, 255,
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

		jbtSelect.setBackground(new java.awt.Color(16, 135, 221));
		jbtSelect.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtSelect.setForeground(new java.awt.Color(255, 255, 255));
		jbtSelect.setText("Seleccionar");
		jbtSelect.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtSelect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtSelectActionPerformed(evt);
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
						.addComponent(jbtSelect,
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
																jbtSelect,
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
												.addComponent(
														jpCompanyList,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jpAction,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
								.addContainerGap()));
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
								.addComponent(jpCompanyList,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
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

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	private void jbtSelectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSelectActionPerformed
		final Company company = this.getSelectedCompany();
		if (company != null) {
			this.setVisible(false);
			if (this.frameSelected == VOUCHER_FRAME) {
				this.showVoucherFrame(company);
			} else if (this.frameSelected == VOUCHER_LIST_FRAME) {
				this.showVoucherListFrame(company);
			} else if (this.frameSelected == UAP_FRAME) {
				this.showUapFrame(company);
			} else if (this.frameSelected == BALANCE_FRAME) {
				this.showBalanceFrame(company);
			}
		} else {
			ViewUtils.showMessage(this, MSG_COMPANY_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_jbtSelectActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtSelect;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpCompanyList;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JScrollPane jspCompanyList;
	private javax.swing.JTable jtbCompanyList;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
