/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.CompanyController;
import co.com.soinsoftware.accountability.controller.MenuController;
import co.com.soinsoftware.accountability.controller.UapResumeController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.User;
import co.com.soinsoftware.accountability.entity.Voucheritem;
import co.com.soinsoftware.accountability.util.CompanyListTableModel;
import co.com.soinsoftware.accountability.util.VoucherItemUapTableModel;

/**
 *
 * @author Carlos Rodriguez
 * @since 24/08/2016
 * @version 1.0
 */
public class JFMain extends JFrame {

	private static final long serialVersionUID = 1911600408651182901L;

	private static final String MSG_COMPANY_REQUIRED = "Seleccione una empresa del listado";

	private final CompanyController companyController;

	private final User loggedUser;

	private final UapResumeController uapResumeController;

	private Company companySelected;

	private List<Voucheritem> accountItemList;

	private List<Voucheritem> classItemList;

	private List<Voucheritem> groupItemList;

	public JFMain(final User user) {
		this.loggedUser = user;
		this.companyController = new CompanyController();
		this.uapResumeController = new UapResumeController();
		this.accountItemList = new ArrayList<>();
		this.classItemList = new ArrayList<>();
		this.groupItemList = new ArrayList<>();
		this.initComponents();
		this.createMenuBar();
		this.refresh();
	}

	public void refresh() {
		this.companySelected = null;
		this.classItemList = new ArrayList<>();
		this.refreshCompanyTableData();
		this.refreshVoucherItemUapTables();
		this.refreshGroupUapTable();
		this.refreshAccountUapTable();
	}

	private void createMenuBar() {
		final MenuController controller = new MenuController(this,
				this.loggedUser);
		final JMenuBar menuBar = new JMBAppMenu(controller);
		this.setJMenuBar(menuBar);
	}

	private void refreshCompanyTableData() {
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

	private void refreshVoucherItemUapTables() {
		final Calendar calendar = Calendar.getInstance();
		final int year = calendar.get(Calendar.YEAR);
		final int month = calendar.get(Calendar.MONTH) + 1;
		if (this.companySelected != null) {
			this.accountItemList = this.uapResumeController
					.selectAccountUapCurrentState(this.companySelected, year,
							month);
			this.groupItemList = this.uapResumeController
					.selectGroupUapCurrentState(this.accountItemList);
			this.classItemList = this.uapResumeController
					.selectClassUapCurrentState(this.groupItemList);
		}
		this.refreshClassUapTable();
		this.refreshGroupUapTable();
		this.refreshAccountUapTable();
	}

	private void refreshClassUapTable() {
		final TableModel model = new VoucherItemUapTableModel(
				this.classItemList);
		this.jtbClassUapList.setModel(model);
		this.jtbClassUapList.setFillsViewportHeight(true);
	}

	private void refreshGroupUapTable(final List<Voucheritem> voucherItemList) {
		final TableModel model = new VoucherItemUapTableModel(voucherItemList);
		this.jtbGroupUapList.setModel(model);
		this.jtbGroupUapList.setFillsViewportHeight(true);
	}

	private void refreshAccountUapTable(final List<Voucheritem> voucherItemList) {
		final TableModel model = new VoucherItemUapTableModel(voucherItemList);
		this.jtbAccountUapList.setModel(model);
		this.jtbAccountUapList.setFillsViewportHeight(true);
	}

	private Voucheritem getSelectedClassVoucherItem() {
		final int index = this.jtbClassUapList.getSelectedRow();
		final TableModel model = this.jtbClassUapList.getModel();
		return ((VoucherItemUapTableModel) model).getSelectedVoucherItem(index);
	}

	private Voucheritem getSelectedGroupVoucherItem() {
		final int index = this.jtbGroupUapList.getSelectedRow();
		final TableModel model = this.jtbGroupUapList.getModel();
		return ((VoucherItemUapTableModel) model).getSelectedVoucherItem(index);
	}

	private void refreshGroupUapTable() {
		final Voucheritem classItem = this.getSelectedClassVoucherItem();
		final List<Voucheritem> groupList = new ArrayList<Voucheritem>();
		if (classItem != null) {
			for (final Voucheritem groupItem : this.groupItemList) {
				final Uap classUap = classItem.getUap();
				final Uap parentUap = groupItem.getUap().getUap();
				if (classUap.getCode() == parentUap.getCode()) {
					groupList.add(groupItem);
				}
			}
		}
		this.refreshGroupUapTable(groupList);
	}

	private void refreshAccountUapTable() {
		final Voucheritem groupItem = this.getSelectedGroupVoucherItem();
		final List<Voucheritem> accountList = new ArrayList<Voucheritem>();
		if (groupItem != null) {
			for (final Voucheritem accountItem : this.accountItemList) {
				final Uap groupUap = groupItem.getUap();
				final Uap parentUap = accountItem.getUap().getUap();
				if (groupUap.getCode() == parentUap.getCode()) {
					accountList.add(accountItem);
				}
			}
		}
		this.refreshAccountUapTable(accountList);
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

		lbImage = new javax.swing.JLabel();
		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		jpCompanyList = new javax.swing.JPanel();
		jspCompanyList = new javax.swing.JScrollPane();
		jtbCompanyList = new javax.swing.JTable();
		jpAction = new javax.swing.JPanel();
		jbtSelect = new javax.swing.JButton();
		jpClassUapList = new javax.swing.JPanel();
		jspClassUapList = new javax.swing.JScrollPane();
		jtbClassUapList = new javax.swing.JTable();
		jpActionClass = new javax.swing.JPanel();
		jbtSelectClass = new javax.swing.JButton();
		jpGroupUapList = new javax.swing.JPanel();
		jspGroupUapList = new javax.swing.JScrollPane();
		jtbGroupUapList = new javax.swing.JTable();
		jpActionGroup = new javax.swing.JPanel();
		jbtSelectGroup = new javax.swing.JButton();
		jpAccountUapList = new javax.swing.JPanel();
		jspAccountUapList = new javax.swing.JScrollPane();
		jtbAccountUapList = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("CPMIPYMES");
		setExtendedState(6);

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Estado de las cuentas mes actual");

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

		jpCompanyList.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Listado de empresas",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbCompanyList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspCompanyList.setViewportView(jtbCompanyList);

		jpAction.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 0, 11))); // NOI18N

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
				jpActionLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jbtSelect,
								javax.swing.GroupLayout.PREFERRED_SIZE, 97,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		jpActionLayout.setVerticalGroup(jpActionLayout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jpActionLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(jbtSelect,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(156, Short.MAX_VALUE)));

		javax.swing.GroupLayout jpCompanyListLayout = new javax.swing.GroupLayout(
				jpCompanyList);
		jpCompanyList.setLayout(jpCompanyListLayout);
		jpCompanyListLayout
				.setHorizontalGroup(jpCompanyListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpCompanyListLayout
										.createSequentialGroup()
										.addComponent(
												jspCompanyList,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												486, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpAction,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpCompanyListLayout.setVerticalGroup(jpCompanyListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspCompanyList,
						javax.swing.GroupLayout.PREFERRED_SIZE, 0,
						Short.MAX_VALUE)
				.addComponent(jpAction, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jpClassUapList.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Clase",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbClassUapList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspClassUapList.setViewportView(jtbClassUapList);

		jpActionClass.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 0, 11))); // NOI18N

		jbtSelectClass.setBackground(new java.awt.Color(16, 135, 221));
		jbtSelectClass.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtSelectClass.setForeground(new java.awt.Color(255, 255, 255));
		jbtSelectClass.setText(">>");
		jbtSelectClass.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtSelectClass.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtSelectClassActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jpActionClassLayout = new javax.swing.GroupLayout(
				jpActionClass);
		jpActionClass.setLayout(jpActionClassLayout);
		jpActionClassLayout.setHorizontalGroup(jpActionClassLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionClassLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jbtSelectClass,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jpActionClassLayout.setVerticalGroup(jpActionClassLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionClassLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jbtSelectClass,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(206, Short.MAX_VALUE)));

		javax.swing.GroupLayout jpClassUapListLayout = new javax.swing.GroupLayout(
				jpClassUapList);
		jpClassUapList.setLayout(jpClassUapListLayout);
		jpClassUapListLayout
				.setHorizontalGroup(jpClassUapListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpClassUapListLayout
										.createSequentialGroup()
										.addComponent(
												jspClassUapList,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												338, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpActionClass,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpClassUapListLayout.setVerticalGroup(jpClassUapListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspClassUapList,
						javax.swing.GroupLayout.PREFERRED_SIZE, 0,
						Short.MAX_VALUE)
				.addComponent(jpActionClass,
						javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jpGroupUapList.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Grupo",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbGroupUapList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspGroupUapList.setViewportView(jtbGroupUapList);

		jpActionGroup.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 0, 11))); // NOI18N

		jbtSelectGroup.setBackground(new java.awt.Color(16, 135, 221));
		jbtSelectGroup.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtSelectGroup.setForeground(new java.awt.Color(255, 255, 255));
		jbtSelectGroup.setText(">>");
		jbtSelectGroup.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtSelectGroup.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtSelectGroupActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jpActionGroupLayout = new javax.swing.GroupLayout(
				jpActionGroup);
		jpActionGroup.setLayout(jpActionGroupLayout);
		jpActionGroupLayout.setHorizontalGroup(jpActionGroupLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionGroupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jbtSelectGroup,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										60,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jpActionGroupLayout.setVerticalGroup(jpActionGroupLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionGroupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jbtSelectGroup,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(206, Short.MAX_VALUE)));

		javax.swing.GroupLayout jpGroupUapListLayout = new javax.swing.GroupLayout(
				jpGroupUapList);
		jpGroupUapList.setLayout(jpGroupUapListLayout);
		jpGroupUapListLayout
				.setHorizontalGroup(jpGroupUapListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpGroupUapListLayout
										.createSequentialGroup()
										.addComponent(
												jspGroupUapList,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												338,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpActionGroup,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpGroupUapListLayout.setVerticalGroup(jpGroupUapListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspGroupUapList,
						javax.swing.GroupLayout.PREFERRED_SIZE, 0,
						Short.MAX_VALUE)
				.addComponent(jpActionGroup,
						javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		jpAccountUapList.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "Cuenta",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbAccountUapList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspAccountUapList.setViewportView(jtbAccountUapList);

		javax.swing.GroupLayout jpAccountUapListLayout = new javax.swing.GroupLayout(
				jpAccountUapList);
		jpAccountUapList.setLayout(jpAccountUapListLayout);
		jpAccountUapListLayout.setHorizontalGroup(jpAccountUapListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspAccountUapList,
						javax.swing.GroupLayout.DEFAULT_SIZE, 347,
						Short.MAX_VALUE));
		jpAccountUapListLayout.setVerticalGroup(jpAccountUapListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspAccountUapList,
						javax.swing.GroupLayout.DEFAULT_SIZE, 245,
						Short.MAX_VALUE));

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
												.addComponent(
														jpCompanyList,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jpClassUapList,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		jpGroupUapList,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jpAccountUapList,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 18, Short.MAX_VALUE))
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
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(jpTitle,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jpCompanyList,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jpClassUapList,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jpGroupUapList,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jpAccountUapList,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
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

	private void jbtSelectGroupActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSelectGroupActionPerformed
		this.refreshAccountUapTable();
	}// GEN-LAST:event_jbtSelectGroupActionPerformed

	private void jbtSelectClassActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSelectClassActionPerformed
		this.refreshGroupUapTable();
	}// GEN-LAST:event_jbtSelectClassActionPerformed

	private void jbtSelectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSelectActionPerformed
		this.companySelected = this.getSelectedCompany();
		if (this.companySelected != null) {
			this.refreshVoucherItemUapTables();
		} else {
			ViewUtils.showMessage(this, MSG_COMPANY_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_jbtSelectActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtSelect;
	private javax.swing.JButton jbtSelectClass;
	private javax.swing.JButton jbtSelectGroup;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JPanel jpAccountUapList;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionClass;
	private javax.swing.JPanel jpActionGroup;
	private javax.swing.JPanel jpClassUapList;
	private javax.swing.JPanel jpCompanyList;
	private javax.swing.JPanel jpGroupUapList;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JScrollPane jspAccountUapList;
	private javax.swing.JScrollPane jspClassUapList;
	private javax.swing.JScrollPane jspCompanyList;
	private javax.swing.JScrollPane jspGroupUapList;
	private javax.swing.JTable jtbAccountUapList;
	private javax.swing.JTable jtbClassUapList;
	private javax.swing.JTable jtbCompanyList;
	private javax.swing.JTable jtbGroupUapList;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
