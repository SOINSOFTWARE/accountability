/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.UapController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Uapxcompany;
import co.com.soinsoftware.accountability.util.UapListTableModel;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class JFUapList extends JDialog {

	private static final long serialVersionUID = 596980064093453786L;

	private static final String MSG_UAP_REQUIRED = "Seleccione una cuenta, subcuenta o auxiliar del listado para continuar";

	private final UapController uapController;

	private final JFVoucher voucherFrame;

	private final Company company;

	private List<Uap> uapList;

	private List<Uap> uapClassList;

	private List<Uap> uapGroupList;

	private List<Uap> uapAccountList;

	private List<Uap> uapSubAccountList;

	public JFUapList(final JFVoucher voucherFrame, final Company company) {
		this.uapController = new UapController();
		this.voucherFrame = voucherFrame;
		this.company = company;
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 290),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
		this.addDocumentListenerToJtfCode();
		this.buildButtonGroup();
	}

	public void refresh() {
		this.uapList = null;
		this.jtfCodeForSearch.setText("");
		final List<Uap> uapList = this.uapController
				.selectUapClassLevel(this.company);
		this.refreshTableData(uapList, true);
		this.refreshForNewUap();
	}

	public Uap getUap(final long code) {
		Uap uapWithCode = null;
		for (final Uap uap : this.uapList) {
			if (uap.getCode() == code) {
				uapWithCode = uap;
				break;
			}
		}
		return uapWithCode;
	}

	private void refreshTableData(final List<Uap> uapList,
			final boolean mustUnpackage) {
		final TableModel model = new UapListTableModel(this.company, uapList,
				mustUnpackage);
		this.jtbUapList.setModel(model);
		this.jtbUapList.setFillsViewportHeight(true);
		this.setTableColumnDimensions();
		if (this.uapList == null) {
			this.uapList = this.getUapListFromTable();
		}
	}

	private void setTableColumnDimensions() {
		for (int i = 0; i < 2; i++) {
			final TableColumn column = this.jtbUapList.getColumnModel()
					.getColumn(i);
			column.setResizable(false);
			if (i == 0) {
				column.setPreferredWidth(100);
			} else {
				column.setPreferredWidth(368);
			}
		}
	}

	private Uap getSelectedUap() {
		final int index = this.jtbUapList.getSelectedRow();
		final TableModel model = this.jtbUapList.getModel();
		return ((UapListTableModel) model).getSelectedUap(index);
	}

	private List<Uap> getUapListFromTable() {
		final TableModel model = this.jtbUapList.getModel();
		return ((UapListTableModel) model).getUapList();
	}

	private void addDocumentListenerToJtfCode() {
		this.jtfCodeForSearch.getDocument().addDocumentListener(
				new DocumentListener() {
					@Override
					public void insertUpdate(DocumentEvent e) {
						filter();
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						filter();
					}

					@Override
					public void changedUpdate(DocumentEvent e) {
					}

					private void filter() {
						final String filter = jtfCodeForSearch.getText();
						try {
							if (!filter.equals("")) {
								Long.parseLong(filter);
							}
							filterModel(filter);
						} catch (final NumberFormatException ex) {
							System.out.println(ex.getMessage());
						}
					}
				});
	}

	private void filterModel(final String filter) {
		final List<Uap> newUapList = (filter.equals("")) ? this.uapList
				: new ArrayList<>();
		if (!filter.equals("")) {
			final int filterLength = filter.length();
			for (final Uap uap : this.uapList) {
				final String code = String.valueOf(uap.getCode());
				if (code.length() >= filterLength
						&& code.substring(0, filterLength).equals(filter)) {
					newUapList.add(uap);
				}
			}
		}
		this.refreshTableData(newUapList, false);
	}

	private void buildButtonGroup() {
		final ButtonGroup bgAccountClass = new ButtonGroup();
		bgAccountClass.add(this.jrbEmpHelper);
		bgAccountClass.add(this.jrbSubAccount);
		bgAccountClass.add(this.jrbAccount);
	}

	private void refreshForNewUap() {
		this.refreshUapAccountSelection();
		this.jrbEmpHelper.setSelected(true);
		this.jcbUapClass.setSelectedIndex(0);
		this.jcbUapGroup.setSelectedIndex(0);
		this.jcbUapAccount.setSelectedIndex(0);
		this.jcbUapSubAccount.setSelectedIndex(0);
		this.setTextToUapFields("");
	}

	private void refreshUapAccountSelection() {
		this.setUapClassModel();
		this.setUapGroupModel(null);
		this.setUapAccountModel(null);
		this.setUapSubAccountModel(null);
		this.setTextToUapFields("");
	}

	private void changeAccountComboData() {
		this.jcbUapClass.setSelectedIndex(0);
		this.jcbUapAccount.setSelectedIndex(0);
		this.jcbUapSubAccount.setSelectedIndex(0);
		this.setTextToUapFields("");
	}

	private void setTextToUapFields(final String code) {
		this.jtfCode.setText(code);
		this.jtfName.setText("");
	}

	private void setUapCode(final Uap uap) {
		if (uap != null) {
			this.setTextToUapFields(String.valueOf(uap.getCode()));
			this.jtfCode.requestFocus();
		}
	}

	private void setUapClassModel() {
		this.uapClassList = this.uapController
				.selectUapClassLevel(this.company);
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione uno...");
		if (this.uapClassList != null && this.uapClassList.size() > 0) {
			for (final Uap uap : this.uapClassList) {
				model.addElement(uap.getCode() + " - " + uap.getName());
			}
		}
		this.jcbUapClass.setModel(model);
	}

	private void setUapGroupModel(final List<Uap> uapList) {
		this.uapGroupList = uapList;
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione uno...");
		if (this.uapGroupList != null && this.uapGroupList.size() > 0) {
			for (final Uap uap : this.uapGroupList) {
				model.addElement(uap.getCode() + " - " + uap.getName());
			}
		}
		this.jcbUapGroup.setModel(model);
	}

	private void setUapAccountModel(final List<Uap> uapList) {
		this.uapAccountList = uapList;
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione uno...");
		if (this.uapAccountList != null && this.uapAccountList.size() > 0) {
			for (final Uap uap : this.uapAccountList) {
				if (uap.isEnabled() && this.validateUapIsInCompany(uap)) {
					model.addElement(uap.getCode() + " - " + uap.getName());
				}
			}
		}
		this.jcbUapAccount.setModel(model);
	}

	private void setUapSubAccountModel(final List<Uap> uapList) {
		this.uapSubAccountList = uapList;
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione uno...");
		if (this.uapSubAccountList != null && this.uapSubAccountList.size() > 0) {
			for (final Uap uap : this.uapSubAccountList) {
				if (uap.isEnabled() && this.validateUapIsInCompany(uap)) {
					model.addElement(uap.getCode() + " - " + uap.getName());
				}
			}
		}
		this.jcbUapSubAccount.setModel(model);
	}

	private boolean validateUapIsInCompany(final Uap uap) {
		boolean isInCompany = false;
		final Set<Uapxcompany> uapXCompSet = uap.getUapxcompanies();
		if (uapXCompSet != null) {
			for (final Uapxcompany uapXComp : uapXCompSet) {
				if (uapXComp.getCompany().equals(this.company)
						&& uapXComp.isEnabled()) {
					isInCompany = true;
					break;
				}
			}
		}
		return isInCompany;
	}

	private Uap getUapClassSelected() {
		Uap uap = null;
		final int index = this.jcbUapClass.getSelectedIndex();
		if (index > 0) {
			final String value = (String) this.jcbUapClass.getSelectedItem();
			final long code = Long.parseLong(value.substring(0,
					value.indexOf("-")).trim());
			uap = this.getUapFromList(this.uapClassList, code);
		}
		return uap;
	}

	private Uap getUapGroupSelected() {
		Uap uap = null;
		final int index = this.jcbUapGroup.getSelectedIndex();
		if (index > 0) {
			final String value = (String) this.jcbUapGroup.getSelectedItem();
			final long code = Long.parseLong(value.substring(0,
					value.indexOf("-")).trim());
			uap = this.getUapFromList(this.uapGroupList, code);
		}
		return uap;
	}

	private Uap getUapAccountSelected() {
		Uap uap = null;
		final int index = this.jcbUapAccount.getSelectedIndex();
		if (index > 0) {
			final String value = (String) this.jcbUapAccount.getSelectedItem();
			final long code = Long.parseLong(value.substring(0,
					value.indexOf("-")).trim());
			uap = this.getUapFromList(this.uapAccountList, code);
		}
		return uap;
	}

	private Uap getUapSubAccountSelected() {
		Uap uap = null;
		final int index = this.jcbUapSubAccount.getSelectedIndex();
		if (index > 0) {
			final String value = (String) this.jcbUapSubAccount
					.getSelectedItem();
			final long code = Long.parseLong(value.substring(0,
					value.indexOf("-")).trim());
			uap = this.getUapFromList(this.uapSubAccountList, code);
		}
		return uap;
	}

	private Uap getUapFromList(final List<Uap> uapList, final long code) {
		Uap uapSelected = null;
		for (final Uap uap : uapList) {
			if (uap.getCode() == code && uap.isEnabled()) {
				uapSelected = uap;
				break;
			}
		}
		return uapSelected;
	}

	private boolean validateDataForSave() {
		boolean valid = true;
		final Uap uapGroup = this.getUapGroupSelected();
		final Uap uapAccount = this.getUapAccountSelected();
		final Uap uapSubAccount = this.getUapSubAccountSelected();
		final long code = this.getCodeValue();
		final String name = this.jtfName.getText();
		if (this.jrbAccount.isSelected() && uapGroup == null) {
			valid = false;
			ViewUtils.showMessage(this, JFUap.MSG_SELECT_ONE_GROUP_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (this.jrbSubAccount.isSelected() && uapAccount == null) {
			valid = false;
			ViewUtils.showMessage(this, JFUap.MSG_SELECT_ONE_ACCOUNT_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (this.jrbEmpHelper.isSelected() && uapSubAccount == null) {
			valid = false;
			ViewUtils.showMessage(this,
					JFUap.MSG_SELECT_ONE_SUBACCOUNT_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (code == 0) {
			valid = false;
			ViewUtils.showMessage(this, JFUap.MSG_CODE_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (name.trim().equals("")) {
			valid = false;
			ViewUtils.showMessage(this, JFUap.MSG_NAME_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (code > 0) {
			valid = this.validateCode();
		}
		return valid;
	}

	private long getCodeValue() {
		String code = this.jtfCode.getText().replace(",", "").replace(".", "");
		if (code.equals("")) {
			code = "0";
		}
		return Long.valueOf(code);
	}

	private boolean validateCode() {
		boolean valid = true;
		final Uap uapGroup = this.getUapGroupSelected();
		final Uap uapAccount = this.getUapAccountSelected();
		final Uap uapSubAccount = this.getUapSubAccountSelected();
		final long code = this.getCodeValue();
		String parentCode = "";
		if (this.jrbAccount.isSelected()) {
			parentCode = String.valueOf(uapGroup.getCode());
		} else if (this.jrbSubAccount.isSelected()) {
			parentCode = String.valueOf(uapAccount.getCode());
		} else if (this.jrbEmpHelper.isSelected()) {
			parentCode = String.valueOf(uapSubAccount.getCode());
		}
		int parentLength = parentCode.length();
		final String codeStr = String.valueOf(code);
		if (!parentCode.equals(codeStr.substring(0, parentLength))) {
			valid = false;
			ViewUtils.showMessage(this, JFUap.MSG_INVALID_CODE_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (codeStr.length() != (parentLength + 2)) {
			valid = false;
			ViewUtils.showMessage(this, JFUap.MSG_INVALID_CODE_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else {
			final Uap uapWithCode = this.uapController.selectUapByCode(code);
			if (uapWithCode != null && this.validateUapIsInCompany(uapWithCode)) {
				valid = false;
				ViewUtils.showMessage(this, JFUap.MSG_USED_CODE_REQUIRED,
						ViewUtils.TITLE_REQUIRED_FIELDS,
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return valid;
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

		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();
		lbImage = new javax.swing.JLabel();
		jtpUapList = new javax.swing.JTabbedPane();
		jpUapListTab = new javax.swing.JPanel();
		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		jpUapList = new javax.swing.JPanel();
		jspUapList = new javax.swing.JScrollPane();
		jtbUapList = new javax.swing.JTable();
		jpFilter = new javax.swing.JPanel();
		jlbCode = new javax.swing.JLabel();
		jtfCodeForSearch = new javax.swing.JTextField();
		jpActionSelect = new javax.swing.JPanel();
		jbtSelect = new javax.swing.JButton();
		jpNewUapTab = new javax.swing.JPanel();
		jpNewUap = new javax.swing.JPanel();
		jlbName = new javax.swing.JLabel();
		jtfName = new javax.swing.JTextField();
		jlbCode1 = new javax.swing.JLabel();
		jtfCode = new javax.swing.JTextField();
		jbtSave = new javax.swing.JButton();
		jcbUapClass = new javax.swing.JComboBox<String>();
		jlbUapClass = new javax.swing.JLabel();
		jlbUapGroup = new javax.swing.JLabel();
		jcbUapGroup = new javax.swing.JComboBox<String>();
		jcbUapAccount = new javax.swing.JComboBox<String>();
		jlbUapAccount = new javax.swing.JLabel();
		jlbUapSubAccount = new javax.swing.JLabel();
		jcbUapSubAccount = new javax.swing.JComboBox<String>();
		jrbEmpHelper = new javax.swing.JRadioButton();
		jrbSubAccount = new javax.swing.JRadioButton();
		jrbAccount = new javax.swing.JRadioButton();

		setTitle("Seleccione una cuenta");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/accountability.png")));

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
								javax.swing.GroupLayout.PREFERRED_SIZE, 97,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
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

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jtpUapList.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Seleccione una cuenta");

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

		jpUapList.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Listado de cuentas",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbUapList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspUapList.setViewportView(jtbUapList);

		jpFilter.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jlbCode.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCode.setText("Codigo:");

		jtfCodeForSearch.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		javax.swing.GroupLayout jpFilterLayout = new javax.swing.GroupLayout(
				jpFilter);
		jpFilter.setLayout(jpFilterLayout);
		jpFilterLayout
				.setHorizontalGroup(jpFilterLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpFilterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpFilterLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jlbCode)
														.addComponent(
																jtfCodeForSearch,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																140,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(401, Short.MAX_VALUE)));
		jpFilterLayout
				.setVerticalGroup(jpFilterLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpFilterLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbCode)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfCodeForSearch,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jpActionSelect.setBorder(javax.swing.BorderFactory
				.createTitledBorder(""));

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

		javax.swing.GroupLayout jpActionSelectLayout = new javax.swing.GroupLayout(
				jpActionSelect);
		jpActionSelect.setLayout(jpActionSelectLayout);
		jpActionSelectLayout.setHorizontalGroup(jpActionSelectLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jpActionSelectLayout
								.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jbtSelect,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										97,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		jpActionSelectLayout.setVerticalGroup(jpActionSelectLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionSelectLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jbtSelect,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		javax.swing.GroupLayout jpUapListLayout = new javax.swing.GroupLayout(
				jpUapList);
		jpUapList.setLayout(jpUapListLayout);
		jpUapListLayout
				.setHorizontalGroup(jpUapListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jpFilter,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addGroup(
								jpUapListLayout
										.createSequentialGroup()
										.addComponent(
												jspUapList,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												0, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpActionSelect,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpUapListLayout
				.setVerticalGroup(jpUapListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpUapListLayout
										.createSequentialGroup()
										.addComponent(
												jpFilter,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jpUapListLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jspUapList,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																330,
																Short.MAX_VALUE)
														.addComponent(
																jpActionSelect,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))));

		javax.swing.GroupLayout jpUapListTabLayout = new javax.swing.GroupLayout(
				jpUapListTab);
		jpUapListTab.setLayout(jpUapListTabLayout);
		jpUapListTabLayout.setHorizontalGroup(jpUapListTabLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpTitle, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						jpUapListTabLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jpUapList,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jpUapListTabLayout
				.setVerticalGroup(jpUapListTabLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpUapListTabLayout
										.createSequentialGroup()
										.addComponent(
												jpTitle,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpUapList,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jtpUapList.addTab("Plan único de cuentas", jpUapListTab);

		jpNewUap.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Nueva cuenta",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jlbName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbName.setText("Nombre:");

		jtfName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbCode1.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCode1.setText("Codigo:");

		jtfCode.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

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

		jcbUapClass.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jcbUapClass.setMaximumSize(new java.awt.Dimension(200, 20));
		jcbUapClass.setMinimumSize(new java.awt.Dimension(200, 20));
		jcbUapClass.setPreferredSize(new java.awt.Dimension(200, 20));
		jcbUapClass.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jcbUapClassActionPerformed(evt);
			}
		});

		jlbUapClass.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbUapClass.setText("Clase:");

		jlbUapGroup.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbUapGroup.setText("Grupo:");

		jcbUapGroup.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jcbUapGroup.setMaximumSize(new java.awt.Dimension(200, 20));
		jcbUapGroup.setPreferredSize(new java.awt.Dimension(200, 20));
		jcbUapGroup.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jcbUapGroupActionPerformed(evt);
			}
		});

		jcbUapAccount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jcbUapAccount.setMaximumSize(new java.awt.Dimension(200, 20));
		jcbUapAccount.setPreferredSize(new java.awt.Dimension(200, 20));
		jcbUapAccount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jcbUapAccountActionPerformed(evt);
			}
		});

		jlbUapAccount.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbUapAccount.setText("Cuenta:");

		jlbUapSubAccount.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbUapSubAccount.setText("Sub-Cuenta:");

		jcbUapSubAccount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jcbUapSubAccount.setMaximumSize(new java.awt.Dimension(200, 20));
		jcbUapSubAccount.setPreferredSize(new java.awt.Dimension(200, 20));
		jcbUapSubAccount.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jcbUapSubAccountActionPerformed(evt);
			}
		});

		jrbEmpHelper.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jrbEmpHelper.setText("Auxiliar empresarial");
		jrbEmpHelper.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				jrbEmpHelperItemStateChanged(evt);
			}
		});

		jrbSubAccount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jrbSubAccount.setText("Sub-cuenta");
		jrbSubAccount.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				jrbSubAccountItemStateChanged(evt);
			}
		});

		jrbAccount.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jrbAccount.setText("Cuenta");
		jrbAccount.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				jrbAccountItemStateChanged(evt);
			}
		});

		javax.swing.GroupLayout jpNewUapLayout = new javax.swing.GroupLayout(
				jpNewUap);
		jpNewUap.setLayout(jpNewUapLayout);
		jpNewUapLayout
				.setHorizontalGroup(jpNewUapLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewUapLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpNewUapLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jpNewUapLayout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)
																		.addComponent(
																				jbtSave,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addComponent(
																jrbAccount)
														.addComponent(
																jrbSubAccount)
														.addComponent(
																jrbEmpHelper)
														.addGroup(
																jpNewUapLayout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING,
																				false)
																		.addComponent(
																				jtfCode)
																		.addComponent(
																				jtfName)
																		.addComponent(
																				jlbUapClass)
																		.addComponent(
																				jlbUapGroup)
																		.addComponent(
																				jlbUapAccount)
																		.addComponent(
																				jlbCode1)
																		.addComponent(
																				jlbUapSubAccount)
																		.addComponent(
																				jlbName)
																		.addComponent(
																				jcbUapSubAccount,
																				0,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jcbUapAccount,
																				0,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jcbUapGroup,
																				0,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addComponent(
																				jcbUapClass,
																				0,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(14, 14, 14)));
		jpNewUapLayout
				.setVerticalGroup(jpNewUapLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewUapLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jrbEmpHelper)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jrbSubAccount)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jrbAccount)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbUapClass)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbUapClass,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbUapGroup)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbUapGroup,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbUapAccount)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbUapAccount,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbUapSubAccount)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbUapSubAccount,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbCode1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfCode,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbName)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfName,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jbtSave,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout jpNewUapTabLayout = new javax.swing.GroupLayout(
				jpNewUapTab);
		jpNewUapTab.setLayout(jpNewUapTabLayout);
		jpNewUapTabLayout.setHorizontalGroup(jpNewUapTabLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpNewUapTabLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jpNewUap,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(341, Short.MAX_VALUE)));
		jpNewUapTabLayout.setVerticalGroup(jpNewUapTabLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpNewUapTabLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jpNewUap,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(59, Short.MAX_VALUE)));

		jtpUapList.addTab("Nueva cuenta", jpNewUapTab);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jtpUapList)
				.addComponent(jpAction, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
								.addComponent(jtpUapList,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										546,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
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

	private void jbtSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSaveActionPerformed
		if (this.validateDataForSave()) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_SAVE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				Uap parentUap = null;
				if (this.jrbAccount.isSelected()) {
					parentUap = this.getUapGroupSelected();
				} else if (this.jrbSubAccount.isSelected()) {
					parentUap = this.getUapAccountSelected();
				} else {
					parentUap = this.getUapSubAccountSelected();
				}
				final long code = this.getCodeValue();
				final String name = this.jtfName.getText();
				final Uap uap = this.uapController.saveUap(parentUap, code,
						name);
				final Uapxcompany uapXComp = this.uapController
						.saveUapXCompany(this.company, uap);
				uap.getUapxcompanies().add(uapXComp);
				parentUap.getUaps().add(uap);
				ViewUtils.showMessage(this, ViewUtils.MSG_SAVED,
						ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
				this.refresh();
			}
		}
	}// GEN-LAST:event_jbtSaveActionPerformed

	private void jcbUapClassActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbUapClassActionPerformed
		final Uap uap = this.getUapClassSelected();
		List<Uap> uapList = new ArrayList<>();
		if (uap != null && uap.getUaps() != null && uap.getUaps().size() > 0) {
			uapList = this.uapController.sortUapSet(uap.getUaps());
		}
		this.setUapGroupModel(uapList);
		this.setUapAccountModel(null);
		this.setUapSubAccountModel(null);
		this.setTextToUapFields("");
	}// GEN-LAST:event_jcbUapClassActionPerformed

	private void jcbUapGroupActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbUapGroupActionPerformed
		final Uap uap = this.getUapGroupSelected();
		List<Uap> uapList = new ArrayList<>();
		if (uap != null && uap.getUaps() != null && uap.getUaps().size() > 0) {
			uapList = this.uapController.sortUapSet(uap.getUaps());
		}
		this.setUapAccountModel(uapList);
		this.setUapSubAccountModel(null);
		this.setTextToUapFields("");
		if (this.jrbAccount.isSelected()) {
			this.setUapCode(uap);
		}
	}// GEN-LAST:event_jcbUapGroupActionPerformed

	private void jcbUapAccountActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbUapAccountActionPerformed
		final Uap uap = this.getUapAccountSelected();
		List<Uap> uapList = new ArrayList<>();
		if (uap != null && uap.getUaps() != null && uap.getUaps().size() > 0) {
			uapList = this.uapController.sortUapSet(uap.getUaps());
		}
		this.setUapSubAccountModel(uapList);
		this.setTextToUapFields("");
		if (this.jrbSubAccount.isSelected()) {
			this.setUapCode(uap);
		}
	}// GEN-LAST:event_jcbUapAccountActionPerformed

	private void jcbUapSubAccountActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbUapSubAccountActionPerformed
		final Uap uap = this.getUapSubAccountSelected();
		if (this.jrbEmpHelper.isSelected()) {
			this.setUapCode(uap);
		}
	}// GEN-LAST:event_jcbUapSubAccountActionPerformed

	private void jrbEmpHelperItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jrbEmpHelperItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			this.jlbUapAccount.setVisible(true);
			this.jcbUapAccount.setVisible(true);
			this.jlbUapSubAccount.setVisible(true);
			this.jcbUapSubAccount.setVisible(true);
			this.changeAccountComboData();
		}
	}// GEN-LAST:event_jrbEmpHelperItemStateChanged

	private void jrbSubAccountItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jrbSubAccountItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			this.jlbUapAccount.setVisible(true);
			this.jcbUapAccount.setVisible(true);
			this.jlbUapSubAccount.setVisible(false);
			this.jcbUapSubAccount.setVisible(false);
			this.changeAccountComboData();
		}
	}// GEN-LAST:event_jrbSubAccountItemStateChanged

	private void jrbAccountItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_jrbAccountItemStateChanged
		if (evt.getStateChange() == ItemEvent.SELECTED) {
			this.jlbUapAccount.setVisible(false);
			this.jcbUapAccount.setVisible(false);
			this.jlbUapSubAccount.setVisible(false);
			this.jcbUapSubAccount.setVisible(false);
			this.changeAccountComboData();
		}
	}// GEN-LAST:event_jrbAccountItemStateChanged

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	private void jbtSelectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSelectActionPerformed
		final Uap uap = this.getSelectedUap();
		if (uap != null && uap.getLevel() > 2) {
			this.setVisible(false);
			this.voucherFrame.addVoucherItem(uap);
		} else {
			ViewUtils.showMessage(this, MSG_UAP_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_jbtSelectActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtSave;
	private javax.swing.JButton jbtSelect;
	private javax.swing.JComboBox<String> jcbUapAccount;
	private javax.swing.JComboBox<String> jcbUapClass;
	private javax.swing.JComboBox<String> jcbUapGroup;
	private javax.swing.JComboBox<String> jcbUapSubAccount;
	private javax.swing.JLabel jlbCode;
	private javax.swing.JLabel jlbCode1;
	private javax.swing.JLabel jlbName;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JLabel jlbUapAccount;
	private javax.swing.JLabel jlbUapClass;
	private javax.swing.JLabel jlbUapGroup;
	private javax.swing.JLabel jlbUapSubAccount;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionSelect;
	private javax.swing.JPanel jpFilter;
	private javax.swing.JPanel jpNewUap;
	private javax.swing.JPanel jpNewUapTab;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JPanel jpUapList;
	private javax.swing.JPanel jpUapListTab;
	private javax.swing.JRadioButton jrbAccount;
	private javax.swing.JRadioButton jrbEmpHelper;
	private javax.swing.JRadioButton jrbSubAccount;
	private javax.swing.JScrollPane jspUapList;
	private javax.swing.JTable jtbUapList;
	private javax.swing.JTextField jtfCode;
	private javax.swing.JTextField jtfCodeForSearch;
	private javax.swing.JTextField jtfName;
	private javax.swing.JTabbedPane jtpUapList;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
