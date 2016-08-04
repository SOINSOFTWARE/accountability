/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.soinsoftware.accountability.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.UapController;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.util.UapTableModel;

/**
 * @author Carlos Rodriguez
 * @since 03/08/2016
 * @version 1.0
 */
public class JFUap extends JDialog {

	private static final long serialVersionUID = -2905273704835020301L;

	private final UapController uapController;

	private List<Uap> uapClassList;

	private List<Uap> uapGroupList;

	private List<Uap> uapAccountList;

	private List<Uap> uapSubAccountList;

	public JFUap() {
		this.uapController = new UapController();
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 500),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
		this.setTextFieldLimits();
		this.setUapClassModel();
		this.setUapGroupModel(null);
		this.setUapAccountModel(null);
		this.setUapSubAccountModel(null);
		this.refreshTableData();
	}

	public void refresh() {
		this.jcbUapClass.setSelectedIndex(0);
		this.jcbUapGroup.setSelectedIndex(0);
		this.jcbUapAccount.setSelectedIndex(0);
		this.jcbUapSubAccount.setSelectedIndex(0);
		this.setTextToUapFields("");
	}

	private void refreshTableData() {
		final TableModel model = new UapTableModel(this.uapClassList);
		this.jtbUapList.setModel(model);
		this.jtbUapList.setFillsViewportHeight(true);
		this.setTableColumnDimensions();
	}

	private void setTableColumnDimensions() {
		for (int i = 0; i < 3; i++) {
			final TableColumn column = this.jtbUapList.getColumnModel()
					.getColumn(i);
			column.setResizable(false);
			if (i == 0 || i == 2) {
				column.setPreferredWidth(80);
			} else if (i == 1) {
				column.setPreferredWidth(316);
			}
		}
	}

	private void setTextFieldLimits() {
		this.jtfName.setDocument(new JTextFieldLimit(100));
	}

	private void setUapClassModel() {
		this.uapClassList = this.uapController.selectUapClassLevel();
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
				model.addElement(uap.getCode() + " - " + uap.getName());
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
				model.addElement(uap.getCode() + " - " + uap.getName());
			}
		}
		this.jcbUapSubAccount.setModel(model);
	}

	private Uap getUapClassSelected() {
		Uap uap = null;
		final int index = this.jcbUapClass.getSelectedIndex();
		if (index > 0) {
			uap = this.uapClassList.get(index - 1);
		}
		return uap;
	}

	private Uap getUapGroupSelected() {
		Uap uap = null;
		final int index = this.jcbUapGroup.getSelectedIndex();
		if (index > 0) {
			uap = this.uapGroupList.get(index - 1);
		}
		return uap;
	}

	private Uap getUapAccountSelected() {
		Uap uap = null;
		final int index = this.jcbUapAccount.getSelectedIndex();
		if (index > 0) {
			uap = this.uapAccountList.get(index - 1);
		}
		return uap;
	}

	private Uap getUapSubAccountSelected() {
		Uap uap = null;
		final int index = this.jcbUapSubAccount.getSelectedIndex();
		if (index > 0) {
			uap = this.uapSubAccountList.get(index - 1);
		}
		return uap;
	}

	private void setTextToUapFields(final String code) {
		this.jtfCode.setText(code);
		this.jtfName.setText("");
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
		lbImage = new javax.swing.JLabel();
		jpNewUap = new javax.swing.JPanel();
		jlbName = new javax.swing.JLabel();
		jtfName = new javax.swing.JTextField();
		jlbCode = new javax.swing.JLabel();
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
		jpUapList = new javax.swing.JPanel();
		jpActionButtons = new javax.swing.JPanel();
		jbtUpdate = new javax.swing.JButton();
		jbtDelete = new javax.swing.JButton();
		jspUapList = new javax.swing.JScrollPane();
		jtbUapList = new javax.swing.JTable();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();

		setTitle("Contabilidad");

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Plan único de cuentas");

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

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jpNewUap.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Nueva cuenta",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jlbName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbName.setText("Nombre:");

		jtfName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbCode.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCode.setText("Codigo:");

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
														.addGroup(
																jpNewUapLayout
																		.createSequentialGroup()
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
																								jlbCode)
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
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		jpNewUapLayout
				.setVerticalGroup(jpNewUapLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewUapLayout
										.createSequentialGroup()
										.addContainerGap()
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
										.addComponent(jlbCode)
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
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												jtfName,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(27, 27, 27)
										.addComponent(
												jbtSave,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		jpUapList.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Listado de cuentas",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jpActionButtons.setBorder(javax.swing.BorderFactory
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

		javax.swing.GroupLayout jpActionButtonsLayout = new javax.swing.GroupLayout(
				jpActionButtons);
		jpActionButtons.setLayout(jpActionButtonsLayout);
		jpActionButtonsLayout
				.setHorizontalGroup(jpActionButtonsLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpActionButtonsLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpActionButtonsLayout
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
		jpActionButtonsLayout
				.setVerticalGroup(jpActionButtonsLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpActionButtonsLayout
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

		jtbUapList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspUapList.setViewportView(jtbUapList);

		javax.swing.GroupLayout jpUapListLayout = new javax.swing.GroupLayout(
				jpUapList);
		jpUapList.setLayout(jpUapListLayout);
		jpUapListLayout
				.setHorizontalGroup(jpUapListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpUapListLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jspUapList,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												582, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jpActionButtons,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpUapListLayout.setVerticalGroup(jpUapListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jspUapList,
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
								.addComponent(jpNewUap,
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
																		0,
																		Short.MAX_VALUE)
																.addComponent(
																		lbImage,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		388,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(
														jpAction,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		jpUapList,
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
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														jpNewUap,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		jpUapList,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jpAction,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))
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

	private void jbtSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSaveActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jbtSaveActionPerformed

	private void jcbUapClassActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbUapClassActionPerformed
		final Uap uap = this.getUapClassSelected();
		List<Uap> uapList = new ArrayList<>();
		if (uap != null && uap.getUaps() != null && uap.getUaps().size() > 0) {
			uapList = new ArrayList<>(uap.getUaps());
			Collections.sort(uapList);
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
			uapList = new ArrayList<>(uap.getUaps());
			Collections.sort(uapList);
		}
		this.setUapAccountModel(uapList);
		this.setUapSubAccountModel(null);
		this.setTextToUapFields("");
	}// GEN-LAST:event_jcbUapGroupActionPerformed

	private void jcbUapAccountActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbUapAccountActionPerformed
		final Uap uap = this.getUapAccountSelected();
		List<Uap> uapList = new ArrayList<>();
		if (uap != null && uap.getUaps() != null && uap.getUaps().size() > 0) {
			uapList = new ArrayList<>(uap.getUaps());
			Collections.sort(uapList);
		}
		this.setUapSubAccountModel(uapList);
		this.setTextToUapFields("");
	}// GEN-LAST:event_jcbUapAccountActionPerformed

	private void jcbUapSubAccountActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jcbUapSubAccountActionPerformed
		final Uap uap = this.getUapSubAccountSelected();
		if (uap != null) {
			this.setTextToUapFields(String.valueOf(uap.getCode()));
			this.jtfCode.requestFocus();
		}
	}// GEN-LAST:event_jcbUapSubAccountActionPerformed

	private void jbtUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtUpdateActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jbtUpdateActionPerformed

	private void jbtDeleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtDeleteActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_jbtDeleteActionPerformed

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtDelete;
	private javax.swing.JButton jbtSave;
	private javax.swing.JButton jbtUpdate;
	private javax.swing.JComboBox<String> jcbUapAccount;
	private javax.swing.JComboBox<String> jcbUapClass;
	private javax.swing.JComboBox<String> jcbUapGroup;
	private javax.swing.JComboBox<String> jcbUapSubAccount;
	private javax.swing.JLabel jlbCode;
	private javax.swing.JLabel jlbName;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JLabel jlbUapAccount;
	private javax.swing.JLabel jlbUapClass;
	private javax.swing.JLabel jlbUapGroup;
	private javax.swing.JLabel jlbUapSubAccount;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionButtons;
	private javax.swing.JPanel jpNewUap;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JPanel jpUapList;
	private javax.swing.JScrollPane jspUapList;
	private javax.swing.JTable jtbUapList;
	private javax.swing.JTextField jtfCode;
	private javax.swing.JTextField jtfName;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
