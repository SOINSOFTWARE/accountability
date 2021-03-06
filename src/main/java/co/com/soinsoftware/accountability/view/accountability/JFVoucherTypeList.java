/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view.accountability;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.VoucherTypeController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;
import co.com.soinsoftware.accountability.util.VoucherTypeListTableModel;
import co.com.soinsoftware.accountability.view.ViewUtils;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class JFVoucherTypeList extends JDialog {

	private static final long serialVersionUID = 7882194692454949246L;

	private static final String MSG_VOUCHER_REQUIRED = "Seleccione una comprobante del listado para continuar";

	private final VoucherTypeController voucherTypeController;

	private final JFVoucher voucherFrame;

	public JFVoucherTypeList(final JFVoucher voucherFrame) {
		this.voucherTypeController = new VoucherTypeController();
		this.voucherFrame = voucherFrame;
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 250),
				(int) (screenSize.getHeight() / 2 - 300));
		this.setModal(true);
	}

	public void refresh(final Company company) {
		this.refreshTableData(company);
	}

	private void refreshTableData(final Company company) {
		final List<Vouchertypexcompany> voucherTypeXCompList = this.voucherTypeController
				.selectVoucherTypesXCompany(company);
		final TableModel model = new VoucherTypeListTableModel(
				voucherTypeXCompList);
		this.jtbVoucherTypeList.setModel(model);
		this.jtbVoucherTypeList.setFillsViewportHeight(true);
		this.setTableColumnDimensions();
	}

	private void setTableColumnDimensions() {
		for (int i = 0; i < 2; i++) {
			final TableColumn column = this.jtbVoucherTypeList.getColumnModel()
					.getColumn(i);
			column.setResizable(false);
			if (i == 0) {
				column.setPreferredWidth(100);
			} else {
				column.setPreferredWidth(368);
			}
		}
	}

	private Vouchertypexcompany getSelectedVoucherTypeXCompany() {
		final int index = this.jtbVoucherTypeList.getSelectedRow();
		final TableModel model = this.jtbVoucherTypeList.getModel();
		return ((VoucherTypeListTableModel) model)
				.getSelectedVoucherTypeXCompany(index);
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
		jpVoucherTypeList = new javax.swing.JPanel();
		jspVoucherTypeList = new javax.swing.JScrollPane();
		jtbVoucherTypeList = new javax.swing.JTable();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();
		jbtSelect = new javax.swing.JButton();

		setTitle("Seleccione un comprobante");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/accountability.png")));

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Seleccione un comprobante");

		javax.swing.GroupLayout jpTitleLayout = new javax.swing.GroupLayout(
				jpTitle);
		jpTitle.setLayout(jpTitleLayout);
		jpTitleLayout.setHorizontalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlbTitle)
						.addContainerGap(276, Short.MAX_VALUE)));
		jpTitleLayout.setVerticalGroup(jpTitleLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpTitleLayout.createSequentialGroup().addGap(32, 32, 32)
						.addComponent(jlbTitle)
						.addContainerGap(34, Short.MAX_VALUE)));

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jpVoucherTypeList.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "Listado de comprobantes",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbVoucherTypeList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspVoucherTypeList.setViewportView(jtbVoucherTypeList);

		javax.swing.GroupLayout jpVoucherTypeListLayout = new javax.swing.GroupLayout(
				jpVoucherTypeList);
		jpVoucherTypeList.setLayout(jpVoucherTypeListLayout);
		jpVoucherTypeListLayout.setHorizontalGroup(jpVoucherTypeListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspVoucherTypeList));
		jpVoucherTypeListLayout.setVerticalGroup(jpVoucherTypeListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspVoucherTypeList,
						javax.swing.GroupLayout.DEFAULT_SIZE, 256,
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
														jpVoucherTypeList,
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
								.addComponent(jpVoucherTypeList,
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
		final Vouchertypexcompany voucherTypeXComp = this
				.getSelectedVoucherTypeXCompany();
		if (voucherTypeXComp != null) {
			this.setVisible(false);
			this.voucherFrame.setVoucherTypeXCompanyData(voucherTypeXComp);
		} else {
			ViewUtils.showMessage(this, MSG_VOUCHER_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_jbtSelectActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtSelect;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JPanel jpVoucherTypeList;
	private javax.swing.JScrollPane jspVoucherTypeList;
	private javax.swing.JTable jtbVoucherTypeList;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
