package co.com.soinsoftware.accountability.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.CompanyController;
import co.com.soinsoftware.accountability.controller.RoleController;
import co.com.soinsoftware.accountability.controller.UapController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Companytype;
import co.com.soinsoftware.accountability.entity.Documenttype;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Uapxcompany;
import co.com.soinsoftware.accountability.entity.User;
import co.com.soinsoftware.accountability.util.CompanyListTableModel;
import co.com.soinsoftware.accountability.util.CompanyTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class JFCompany extends JDialog {

	private static final long serialVersionUID = 372651084892210851L;

	private static final String MSG_COMPANY_REQUIRED = "Seleccione una empresa del listado para continuar";

	private static final String MSG_NAME_REQUIRED = "Complete el campo nombre";

	private static final String MSG_NAME_CEO_REQUIRED = "Complete el campo nombre del representante legal";

	private static final String MSG_DOCUMENT_REQUIRED = "Complete el campo número de documento";

	private static final String MSG_DOCUMENT_CEO_REQUIRED = "Complete el campo cedula del representante legal";

	private final CompanyController companyController;

	private final Companytype companyTypeNatural;

	private final Companytype companyTypeJuridica;

	private final Documenttype docTypeCc;

	private final Documenttype docTypeCe;

	private final Documenttype docTypeNit;

	private final JFLogin loginFrame;

	private final UapController uapController;

	private final User loggedUser;

	private List<Companytype> companyTypeList;

	private List<Documenttype> documentTypeList;

	public JFCompany(final JFLogin loginFrame, final User loggedUser) {
		this.loginFrame = loginFrame;
		this.loggedUser = loggedUser;
		this.companyController = new CompanyController();
		this.companyTypeNatural = this.companyController
				.selectCompanyTypeNatural();
		this.companyTypeJuridica = this.companyController
				.selectCompanyTypeJuridica();
		this.docTypeCc = this.companyController.selectDocumentTypeCc();
		this.docTypeCe = this.companyController.selectDocumentTypeCe();
		this.docTypeNit = this.companyController.selectDocumentTypeNit();
		this.uapController = new UapController();
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 500),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
		this.setTextFieldLimits();
		this.setCompanyTypeModel();
		this.refresh();
	}

	public void refresh() {
		this.showCompanyEditionTab();
		this.setEditableCeoData(false);
		this.jcbCompanyType.setSelectedIndex(0);
		this.jcbDocumentType.setSelectedIndex(0);
		this.refreshCompanyData();
		this.refreshCeoData();
		this.refreshTableDataForSelection();
		this.refreshTableDataForEdition();
		this.loginFrame.setVisible(true);
	}

	private void showCompanyEditionTab() {
		final int indexOfTab = this.jtpCompany.indexOfTab("Editar empresas");
		if (this.canEditCompanies()) {
			if (indexOfTab == -1) {
				this.jtpCompany.addTab("Editar empresas", jpCompanyTab);
			}
		} else {
			if (indexOfTab > -1) {
				this.jtpCompany.removeTabAt(indexOfTab);
			}
		}
	}

	private boolean canEditCompanies() {
		final RoleController roleController = RoleController.getInstance();
		return roleController.isAdminRol(this.loggedUser)
				|| roleController.isAccountRol(this.loggedUser);
	}

	private void setCompanyTypeModel() {
		this.companyTypeList = this.companyController.selectCompanyTypes();
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (final Companytype companyType : this.companyTypeList) {
			model.addElement(companyType.getName());
		}
		this.jcbCompanyType.setModel(model);
	}

	private void validateCompanyTypeSelected() {
		final Companytype companyType = this.getCompanyTypeSelected();
		final List<Documenttype> docTypeList = new ArrayList<>();
		boolean editableCEO = false;
		if (companyType.equals(this.companyTypeNatural)) {
			docTypeList.add(this.docTypeCc);
			docTypeList.add(this.docTypeCe);
		} else if (companyType.equals(this.companyTypeJuridica)) {
			docTypeList.add(this.docTypeNit);
			editableCEO = true;
		}
		this.setDocumentTypeModel(docTypeList);
		this.refreshCompanyData();
		this.setEditableCeoData(editableCEO);
		this.refreshCeoData();
	}

	private void setDocumentTypeModel(final List<Documenttype> documentTypeList) {
		if (this.documentTypeList != null) {
			this.documentTypeList.clear();
		}
		this.documentTypeList = documentTypeList;
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		for (final Documenttype documentType : this.documentTypeList) {
			model.addElement(documentType.getName());
		}
		this.jcbDocumentType.setModel(model);
	}

	private void setTextFieldLimits() {
		this.jtfCompanyName.setDocument(new JTextFieldLimit(60));
		this.jtfCompanyDocument.setDocument(new JTextFieldLimit(45));
	}

	private void refreshTableDataForSelection() {
		final List<Company> companyList = this.companyController
				.selectCompanies();
		final TableModel model = new CompanyListTableModel(companyList);
		this.jtbCompanyListForSelection.setModel(model);
		this.jtbCompanyListForSelection.setFillsViewportHeight(true);
	}

	private void refreshTableDataForEdition() {
		final List<Company> companyList = this.companyController
				.selectCompanies();
		final TableModel model = new CompanyTableModel(companyList);
		this.jtbCompanyListForEdition.setModel(model);
		this.jtbCompanyListForEdition.setFillsViewportHeight(true);
	}

	private void setEditableCeoData(final boolean editable) {
		this.jtfNameCEO.setEditable(editable);
		this.jtfIdentificationCEO.setEditable(editable);
	}

	private void refreshCompanyData() {
		this.jtfCompanyName.setText("");
		this.jtfCompanyDocument.setText("");
	}

	private void refreshCeoData() {
		this.jtfNameCEO.setText("");
		this.jtfIdentificationCEO.setText("");
	}

	private long getIdentificationValue() {
		String identification = this.jtfIdentificationCEO.getText()
				.replace(",", "").replace(".", "");
		if (identification.equals("")) {
			identification = "0";
		}
		return Long.valueOf(identification);
	}

	private boolean validateDataForSave() {
		boolean valid = true;
		final String name = this.jtfCompanyName.getText();
		final String nit = this.jtfCompanyDocument.getText();
		final Companytype companyType = this.getCompanyTypeSelected();
		if (name.trim().equals("")) {
			valid = false;
			ViewUtils.showMessage(this, MSG_NAME_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (nit.trim().equals("")) {
			valid = false;
			ViewUtils.showMessage(this, MSG_DOCUMENT_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (companyType.equals(this.companyTypeJuridica)) {
			final String nameCEO = this.jtfNameCEO.getText();
			final long identificationCEO = this.getIdentificationValue();
			if (nameCEO.trim().equals("")) {
				valid = false;
				ViewUtils.showMessage(this, MSG_NAME_CEO_REQUIRED,
						ViewUtils.TITLE_REQUIRED_FIELDS,
						JOptionPane.ERROR_MESSAGE);
			} else if (identificationCEO == 0) {
				valid = false;
				ViewUtils.showMessage(this, MSG_DOCUMENT_CEO_REQUIRED,
						ViewUtils.TITLE_REQUIRED_FIELDS,
						JOptionPane.ERROR_MESSAGE);
			}
		}
		return valid;
	}

	private List<Company> getCompanyListFromTable() {
		final TableModel model = this.jtbCompanyListForEdition.getModel();
		return ((CompanyTableModel) model).getCompanyList();
	}

	private boolean hasCompanyToBeUpdated(final List<Company> companyList) {
		boolean hasElements = false;
		for (final Company company : companyList) {
			if ((company.getNewName() != null
					&& !company.getNewName().equals("") && !company
					.getNewName().equals(company.getName()))
					|| (company.getNewDocument() != null
							&& !company.getNewDocument().equals("") && !company
							.getNewDocument().equals(company.getDocument()))
					|| ((company.getNewDocumentCeo() != null
							&& company.getDocumentceo() != null && !company
							.getNewDocumentCeo().equals(
									company.getDocumentceo())) || (company
							.getNewDocumentCeo() != null && company
							.getDocumentceo() == null))
					|| (company.getNewNameCeo() != null
							&& !company.getNewNameCeo().equals("") && !company
							.getNewNameCeo().equals(company.getNameceo()))) {
				hasElements = true;
				break;
			}
		}
		return hasElements;
	}

	private boolean hasCompanyToBeDeleted(final List<Company> companyList) {
		boolean hasElements = false;
		for (final Company company : companyList) {
			if (company.isDelete()) {
				hasElements = true;
				break;
			}
		}
		return hasElements;
	}

	private Companytype getCompanyTypeSelected() {
		final int index = this.jcbCompanyType.getSelectedIndex();
		return this.companyTypeList.get(index);
	}

	private Documenttype getDocumentTypeSelected() {
		final int index = this.jcbDocumentType.getSelectedIndex();
		return this.documentTypeList.get(index);
	}

	private void saveDefaultUap(final Company company) {
		final Set<Uapxcompany> defaultUapXCompSet = this.uapController
				.selectUapXCompany();
		final Set<Uap> uapSet = uapController.selectUapDefault();
		final Set<Uapxcompany> uapXCompSet = new HashSet<>();
		if (uapSet != null && uapSet.size() > 0) {
			final Date currentDate = new Date();
			for (final Uap uap : uapSet) {
				final Uapxcompany uapXComp = new Uapxcompany(uap, company,
						currentDate, currentDate, true);
				uapXCompSet.add(uapXComp);
			}
			this.uapController.saveUapXCompanySet(uapXCompSet);
			defaultUapXCompSet.addAll(uapXCompSet);
		}
	}

	private Company getSelectedCompany() {
		final int index = this.jtbCompanyListForSelection.getSelectedRow();
		final TableModel model = this.jtbCompanyListForSelection.getModel();
		return ((CompanyListTableModel) model).getSelectedCompany(index);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		lbImage = new javax.swing.JLabel();
		jtpCompany = new javax.swing.JTabbedPane();
		jpCompanyListTab = new javax.swing.JPanel();
		jpTitleCompanyList = new javax.swing.JPanel();
		jlbTitleCompanyList = new javax.swing.JLabel();
		jpCompanyListForSelection = new javax.swing.JPanel();
		jspCompanyListForSelection = new javax.swing.JScrollPane();
		jtbCompanyListForSelection = new javax.swing.JTable();
		jpActionForSelect = new javax.swing.JPanel();
		jbtSelect = new javax.swing.JButton();
		jpCompanyTab = new javax.swing.JPanel();
		jpTitleCompanyEdition = new javax.swing.JPanel();
		jlbTitleCompanyEdition = new javax.swing.JLabel();
		jpNewCompany = new javax.swing.JPanel();
		jlbCompanyName = new javax.swing.JLabel();
		jtfCompanyName = new javax.swing.JTextField();
		jlbCompanyDocument = new javax.swing.JLabel();
		jtfCompanyDocument = new javax.swing.JTextField();
		jbtSave = new javax.swing.JButton();
		jcbCompanyType = new javax.swing.JComboBox<String>();
		jlbCompanyType = new javax.swing.JLabel();
		jlbDocumentType = new javax.swing.JLabel();
		jcbDocumentType = new javax.swing.JComboBox<String>();
		jlbNameCEO = new javax.swing.JLabel();
		jtfNameCEO = new javax.swing.JTextField();
		jlbIdentificationCEO = new javax.swing.JLabel();
		jtfIdentificationCEO = new javax.swing.JFormattedTextField();
		jpCompanyListForEdition = new javax.swing.JPanel();
		jspCompanyListForEdition = new javax.swing.JScrollPane();
		jtbCompanyListForEdition = new javax.swing.JTable();
		jpActionForEdition = new javax.swing.JPanel();
		jbtUpdate = new javax.swing.JButton();
		jbtDelete = new javax.swing.JButton();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();

		setTitle("Empresas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/accountability.png")));
		setResizable(false);

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jtpCompany.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N

		jpTitleCompanyList.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitleCompanyList.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitleCompanyList.setText("Seleccione una empresa");

		javax.swing.GroupLayout jpTitleCompanyListLayout = new javax.swing.GroupLayout(
				jpTitleCompanyList);
		jpTitleCompanyList.setLayout(jpTitleCompanyListLayout);
		jpTitleCompanyListLayout.setHorizontalGroup(jpTitleCompanyListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpTitleCompanyListLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jlbTitleCompanyList)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jpTitleCompanyListLayout.setVerticalGroup(jpTitleCompanyListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpTitleCompanyListLayout.createSequentialGroup()
								.addGap(32, 32, 32)
								.addComponent(jlbTitleCompanyList)
								.addContainerGap(34, Short.MAX_VALUE)));

		jpCompanyListForSelection.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "Listado de empresas",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbCompanyListForSelection.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspCompanyListForSelection.setViewportView(jtbCompanyListForSelection);

		jpActionForSelect.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
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

		javax.swing.GroupLayout jpActionForSelectLayout = new javax.swing.GroupLayout(
				jpActionForSelect);
		jpActionForSelect.setLayout(jpActionForSelectLayout);
		jpActionForSelectLayout.setHorizontalGroup(jpActionForSelectLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						jpActionForSelectLayout
								.createSequentialGroup()
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(jbtSelect,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										97,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		jpActionForSelectLayout.setVerticalGroup(jpActionForSelectLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionForSelectLayout
								.createSequentialGroup()
								.addGap(23, 23, 23)
								.addComponent(jbtSelect,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		javax.swing.GroupLayout jpCompanyListForSelectionLayout = new javax.swing.GroupLayout(
				jpCompanyListForSelection);
		jpCompanyListForSelection.setLayout(jpCompanyListForSelectionLayout);
		jpCompanyListForSelectionLayout
				.setHorizontalGroup(jpCompanyListForSelectionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpCompanyListForSelectionLayout
										.createSequentialGroup()
										.addComponent(
												jspCompanyListForSelection,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												836, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpActionForSelect,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpCompanyListForSelectionLayout
				.setVerticalGroup(jpCompanyListForSelectionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jspCompanyListForSelection,
								javax.swing.GroupLayout.DEFAULT_SIZE, 365,
								Short.MAX_VALUE)
						.addComponent(jpActionForSelect,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE));

		javax.swing.GroupLayout jpCompanyListTabLayout = new javax.swing.GroupLayout(
				jpCompanyListTab);
		jpCompanyListTab.setLayout(jpCompanyListTabLayout);
		jpCompanyListTabLayout.setHorizontalGroup(jpCompanyListTabLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpTitleCompanyList,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						jpCompanyListTabLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jpCompanyListForSelection,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addContainerGap()));
		jpCompanyListTabLayout
				.setVerticalGroup(jpCompanyListTabLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpCompanyListTabLayout
										.createSequentialGroup()
										.addComponent(
												jpTitleCompanyList,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpCompanyListForSelection,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		jtpCompany.addTab("Listado de empresas", jpCompanyListTab);

		jpTitleCompanyEdition.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitleCompanyEdition.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitleCompanyEdition.setText("Editar empresas");

		javax.swing.GroupLayout jpTitleCompanyEditionLayout = new javax.swing.GroupLayout(
				jpTitleCompanyEdition);
		jpTitleCompanyEdition.setLayout(jpTitleCompanyEditionLayout);
		jpTitleCompanyEditionLayout
				.setHorizontalGroup(jpTitleCompanyEditionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpTitleCompanyEditionLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbTitleCompanyEdition)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jpTitleCompanyEditionLayout
				.setVerticalGroup(jpTitleCompanyEditionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpTitleCompanyEditionLayout
										.createSequentialGroup()
										.addGap(32, 32, 32)
										.addComponent(jlbTitleCompanyEdition)
										.addContainerGap(34, Short.MAX_VALUE)));

		jpNewCompany.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Nueva empresa",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jlbCompanyName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCompanyName.setText("Nombre:");

		jtfCompanyName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbCompanyDocument.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCompanyDocument.setText("Número de documento:");

		jtfCompanyDocument.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

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

		jcbCompanyType.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jcbCompanyType.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jcbCompanyTypeActionPerformed(evt);
			}
		});

		jlbCompanyType.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCompanyType.setText("Tipo de empresa:");

		jlbDocumentType.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbDocumentType.setText("Tipo de documento:");

		jcbDocumentType.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbNameCEO.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbNameCEO.setText("Representante legal:");

		jtfNameCEO.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbIdentificationCEO.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbIdentificationCEO.setText("Cedula representante legal:");

		jtfIdentificationCEO
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0"))));
		jtfIdentificationCEO.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		javax.swing.GroupLayout jpNewCompanyLayout = new javax.swing.GroupLayout(
				jpNewCompany);
		jpNewCompany.setLayout(jpNewCompanyLayout);
		jpNewCompanyLayout
				.setHorizontalGroup(jpNewCompanyLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewCompanyLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpNewCompanyLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jpNewCompanyLayout
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
																jtfCompanyDocument)
														.addComponent(
																jtfCompanyName)
														.addComponent(
																jtfNameCEO)
														.addGroup(
																jpNewCompanyLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpNewCompanyLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jcbCompanyType,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								190,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jcbDocumentType,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								190,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jlbCompanyDocument)
																						.addComponent(
																								jlbCompanyName)
																						.addComponent(
																								jlbCompanyType)
																						.addComponent(
																								jlbDocumentType)
																						.addComponent(
																								jlbNameCEO)
																						.addComponent(
																								jlbIdentificationCEO))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE))
														.addComponent(
																jtfIdentificationCEO))
										.addContainerGap()));
		jpNewCompanyLayout
				.setVerticalGroup(jpNewCompanyLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewCompanyLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbCompanyType)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbCompanyType,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
										.addComponent(jlbDocumentType)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbDocumentType,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbCompanyDocument)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfCompanyDocument,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbNameCEO)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfNameCEO,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbIdentificationCEO)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfIdentificationCEO,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												25, Short.MAX_VALUE)
										.addComponent(
												jbtSave,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		jpCompanyListForEdition.setBorder(javax.swing.BorderFactory
				.createTitledBorder(null, "Listado de empresas",
						javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
						javax.swing.border.TitledBorder.DEFAULT_POSITION,
						new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbCompanyListForEdition.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspCompanyListForEdition.setViewportView(jtbCompanyListForEdition);
		if (jtbCompanyListForEdition.getColumnModel().getColumnCount() > 0) {
			jtbCompanyListForEdition.getColumnModel().getColumn(0)
					.setResizable(false);
			jtbCompanyListForEdition.getColumnModel().getColumn(1)
					.setResizable(false);
			jtbCompanyListForEdition.getColumnModel().getColumn(2)
					.setResizable(false);
		}

		jpActionForEdition.setBorder(javax.swing.BorderFactory
				.createTitledBorder(""));

		jbtUpdate.setBackground(new java.awt.Color(16, 135, 221));
		jbtUpdate.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtUpdate.setForeground(new java.awt.Color(255, 255, 255));
		jbtUpdate.setText("Actualizar");
		jbtUpdate.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtUpdate.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtUpdateActionPerformed(evt);
			}
		});

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

		javax.swing.GroupLayout jpActionForEditionLayout = new javax.swing.GroupLayout(
				jpActionForEdition);
		jpActionForEdition.setLayout(jpActionForEditionLayout);
		jpActionForEditionLayout
				.setHorizontalGroup(jpActionForEditionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpActionForEditionLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpActionForEditionLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jbtUpdate,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																100,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jbtDelete,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																100,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		jpActionForEditionLayout
				.setVerticalGroup(jpActionForEditionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpActionForEditionLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jbtUpdate,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jbtDelete,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout jpCompanyListForEditionLayout = new javax.swing.GroupLayout(
				jpCompanyListForEdition);
		jpCompanyListForEdition.setLayout(jpCompanyListForEditionLayout);
		jpCompanyListForEditionLayout
				.setHorizontalGroup(jpCompanyListForEditionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpCompanyListForEditionLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jspCompanyListForEdition,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												591, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jpActionForEdition,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpCompanyListForEditionLayout
				.setVerticalGroup(jpCompanyListForEditionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jspCompanyListForEdition,
								javax.swing.GroupLayout.PREFERRED_SIZE, 0,
								Short.MAX_VALUE)
						.addComponent(jpActionForEdition,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE));

		javax.swing.GroupLayout jpCompanyTabLayout = new javax.swing.GroupLayout(
				jpCompanyTab);
		jpCompanyTab.setLayout(jpCompanyTabLayout);
		jpCompanyTabLayout
				.setHorizontalGroup(jpCompanyTabLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpCompanyTabLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jpNewCompany,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jpCompanyListForEdition,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addContainerGap())
						.addComponent(jpTitleCompanyEdition,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE));
		jpCompanyTabLayout
				.setVerticalGroup(jpCompanyTabLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpCompanyTabLayout
										.createSequentialGroup()
										.addComponent(
												jpTitleCompanyEdition,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												jpCompanyTabLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jpNewCompany,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jpCompanyListForEdition,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addGap(0, 4, Short.MAX_VALUE)));

		jtpCompany.addTab("Editar empresas", jpCompanyTab);

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
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										388,
										javax.swing.GroupLayout.PREFERRED_SIZE))
				.addComponent(jtpCompany)
				.addComponent(jpAction, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(jtpCompany,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										512,
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

		jtpCompany.getAccessibleContext().setAccessibleName("jpCompanyListTab");

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void jbtSelectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSelectActionPerformed
		final Company company = this.getSelectedCompany();
		if (company != null) {
			final JFMain mainFrame = new JFMain(this, company, loggedUser);
			mainFrame.setVisible(true);
			this.setVisible(false);
			this.loginFrame.setVisible(false);
		} else {
			ViewUtils.showMessage(this, MSG_COMPANY_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
	}// GEN-LAST:event_jbtSelectActionPerformed

	private void jcbCompanyTypeActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbCompanyTypeActionPerformed
		this.validateCompanyTypeSelected();
	}// GEN-LAST:event_jcbCompanyTypeActionPerformed

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.loginFrame.refresh();
		this.loginFrame.setVisible(true);
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	private void jbtSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSaveActionPerformed
		if (this.validateDataForSave()) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_SAVE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				final Companytype companyType = this.getCompanyTypeSelected();
				final Documenttype documentType = this
						.getDocumentTypeSelected();
				final String name = this.jtfCompanyName.getText();
				final String document = this.jtfCompanyDocument.getText();
				String nameCEO = null;
				Long identificationCEO = null;
				if (companyType.equals(this.companyTypeJuridica)) {
					nameCEO = this.jtfNameCEO.getText();
					identificationCEO = this.getIdentificationValue();
				}
				final Company company = this.companyController.saveCompany(
						companyType, documentType, name, document, nameCEO,
						identificationCEO);
				this.saveDefaultUap(company);
				ViewUtils.showMessage(this, ViewUtils.MSG_SAVED,
						ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
				this.refresh();
			}
		}
	}// GEN-LAST:event_jbtSaveActionPerformed

	private void jbtUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtUpdateActionPerformed
		final List<Company> companyList = this.getCompanyListFromTable();
		if (companyList != null && this.hasCompanyToBeUpdated(companyList)) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_UPDATE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				for (final Company company : companyList) {
					boolean edited = false;
					if (company.getNewName() != null
							&& !company.getNewName().equals("")
							&& !company.getNewName().equals(company.getName())) {
						company.setName(company.getNewName());
						edited = true;
					}
					if (company.getNewDocument() != null
							&& !company.getNewDocument().equals("")
							&& !company.getNewDocument().equals(
									company.getDocument())) {
						company.setDocument(company.getNewDocument());
						edited = true;
					}
					if ((company.getNewDocumentCeo() != null
							&& company.getDocumentceo() != null && !company
							.getNewDocumentCeo().equals(
									company.getDocumentceo()))
							|| (company.getNewDocumentCeo() != null && company
									.getDocumentceo() == null)) {
						company.setDocumentceo(company.getNewDocumentCeo());
						edited = true;
					}
					if (company.getNewNameCeo() != null
							&& !company.getNewNameCeo().equals("")
							&& !company.getNewNameCeo().equals(
									company.getNameceo())) {
						company.setNameceo(company.getNewNameCeo());
						edited = true;
					}
					if (edited) {
						company.setUpdated(new Date());
						this.companyController.saveCompany(company);
					}
				}
				ViewUtils.showMessage(this, ViewUtils.MSG_UPDATED,
						ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
				this.refresh();
			}
		} else {
			ViewUtils.showMessage(this, ViewUtils.MSG_UNEDITED,
					ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbtUpdateActionPerformed

	private void jbtDeleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtDeleteActionPerformed
		final List<Company> companyList = this.getCompanyListFromTable();
		if (companyList != null && this.hasCompanyToBeDeleted(companyList)) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_DELETE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				for (final Company company : companyList) {
					if (company.isDelete()) {
						company.setEnabled(false);
						company.setUpdated(new Date());
						this.companyController.saveCompany(company);
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

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtDelete;
	private javax.swing.JButton jbtSave;
	private javax.swing.JButton jbtSelect;
	private javax.swing.JButton jbtUpdate;
	private javax.swing.JComboBox<String> jcbCompanyType;
	private javax.swing.JComboBox<String> jcbDocumentType;
	private javax.swing.JLabel jlbCompanyDocument;
	private javax.swing.JLabel jlbCompanyName;
	private javax.swing.JLabel jlbCompanyType;
	private javax.swing.JLabel jlbDocumentType;
	private javax.swing.JLabel jlbIdentificationCEO;
	private javax.swing.JLabel jlbNameCEO;
	private javax.swing.JLabel jlbTitleCompanyEdition;
	private javax.swing.JLabel jlbTitleCompanyList;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionForEdition;
	private javax.swing.JPanel jpActionForSelect;
	private javax.swing.JPanel jpCompanyListForEdition;
	private javax.swing.JPanel jpCompanyListForSelection;
	private javax.swing.JPanel jpCompanyListTab;
	private javax.swing.JPanel jpCompanyTab;
	private javax.swing.JPanel jpNewCompany;
	private javax.swing.JPanel jpTitleCompanyEdition;
	private javax.swing.JPanel jpTitleCompanyList;
	private javax.swing.JScrollPane jspCompanyListForEdition;
	private javax.swing.JScrollPane jspCompanyListForSelection;
	private javax.swing.JTable jtbCompanyListForEdition;
	private javax.swing.JTable jtbCompanyListForSelection;
	private javax.swing.JTextField jtfCompanyDocument;
	private javax.swing.JTextField jtfCompanyName;
	private javax.swing.JFormattedTextField jtfIdentificationCEO;
	private javax.swing.JTextField jtfNameCEO;
	private javax.swing.JTabbedPane jtpCompany;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
