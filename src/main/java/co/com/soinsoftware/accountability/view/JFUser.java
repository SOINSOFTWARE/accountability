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

import co.com.soinsoftware.accountability.controller.UserController;
import co.com.soinsoftware.accountability.entity.Rol;
import co.com.soinsoftware.accountability.entity.User;
import co.com.soinsoftware.accountability.util.UserTableModel;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class JFUser extends JDialog {

	private static final long serialVersionUID = -7121309732386477581L;

	private static final String MSG_IDENTIFICATION_REQUIRED = "Complete el campo cedula";

	private static final String MSG_LAST_NAME_REQUIRED = "Complete el campo apellido(s)";

	private static final String MSG_LOGIN_REQUIRED = "Complete el campo login";

	private static final String MSG_LOGIN_USED = "El login ya está siendo usado por otro usuario";

	private static final String MSG_NAME_REQUIRED = "Complete el campo nombre(s)";

	private static final String MSG_PASSWORD_REQUIRED = "Complete el campo contraseña";

	private static final String MSG_ROL_REQUIRED = "Seleccione el perfil del usuario";

	private final UserController userController;

	private List<Rol> rolList;

	public JFUser() {
		this.userController = new UserController();
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 500),
				(int) (screenSize.getHeight() / 2 - 350));
		this.setModal(true);
		this.setTextFieldLimits();
		this.setRolModel();
	}

	private void setTextFieldLimits() {
		this.jtfName.setDocument(new JTextFieldLimit(45));
		this.jtfLastName.setDocument(new JTextFieldLimit(45));
		this.jtfLogin.setDocument(new JTextFieldLimit(10));
		this.jtfPassword.setDocument(new JTextFieldLimit(10));
	}

	public void refresh() {
		this.jtfIdentification.setText("");
		this.jtfName.setText("");
		this.jtfLastName.setText("");
		this.jtfLogin.setText("");
		this.jtfPassword.setText("");
		this.jcbRol.setSelectedIndex(0);
		this.refreshTableData();
	}

	private void refreshTableData() {
		final List<User> userList = this.userController.selectUsers();
		final TableModel model = new UserTableModel(userList);
		this.jtbUserList.setModel(model);
		this.jtbUserList.setFillsViewportHeight(true);
	}

	private void setRolModel() {
		this.rolList = this.userController.selectRoles();
		final DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
		model.addElement("Seleccione uno...");
		for (final Rol rol : this.rolList) {
			model.addElement(rol.getName());
		}
		this.jcbRol.setModel(model);
	}

	private Rol getRolSelected() {
		Rol rol = null;
		final int index = this.jcbRol.getSelectedIndex();
		if (index > 0) {
			rol = this.rolList.get(index - 1);
		}
		return rol;
	}

	private long getIdentificationValue() {
		String identification = this.jtfIdentification.getText()
				.replace(",", "").replace(".", "");
		if (identification.equals("")) {
			identification = "0";
		}
		return Long.valueOf(identification);
	}

	private boolean validateDataForSave() {
		boolean valid = true;
		final long identification = this.getIdentificationValue();
		final String name = this.jtfName.getText();
		final String lastName = this.jtfLastName.getText();
		final Rol rol = this.getRolSelected();
		final String login = this.jtfLogin.getText();
		final String password = this.jtfPassword.getText();
		if (identification == 0) {
			valid = false;
			ViewUtils.showMessage(this, MSG_IDENTIFICATION_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (name.trim().equals("")) {
			valid = false;
			ViewUtils.showMessage(this, MSG_NAME_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (lastName.trim().equals("")) {
			valid = false;
			ViewUtils.showMessage(this, MSG_LAST_NAME_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (rol == null) {
			valid = false;
			ViewUtils.showMessage(this, MSG_ROL_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (login.trim().equals("")) {
			valid = false;
			ViewUtils.showMessage(this, MSG_LOGIN_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (password.trim().equals("")) {
			valid = false;
			ViewUtils.showMessage(this, MSG_PASSWORD_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (this.userController.isLoginUsed(login)) {
			valid = false;
			ViewUtils.showMessage(this, MSG_LOGIN_USED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
		return valid;
	}

	private List<User> getUserListFromTable() {
		final TableModel model = this.jtbUserList.getModel();
		return ((UserTableModel) model).getUserList();
	}

	private boolean hasUserToBeUpdated(final List<User> userList) {
		boolean hasElements = false;
		for (final User user : userList) {
			if ((user.getNewName() != null && !user.getNewName().equals("") && !user
					.getNewName().equals(user.getName()))
					|| (user.getNewLastname() != null
							&& !user.getNewLastname().equals("") && !user
							.getNewLastname().equals(user.getLastname()))
					|| (user.getNewIdentification() != user.getIdentification())) {
				hasElements = true;
				break;
			}
		}
		return hasElements;
	}

	private boolean hasUserToBeDeleted(final List<User> userList) {
		boolean hasElements = false;
		for (final User user : userList) {
			if (user.isDelete()) {
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
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		lbImage = new javax.swing.JLabel();
		jpNewUser = new javax.swing.JPanel();
		jlbName = new javax.swing.JLabel();
		jtfName = new javax.swing.JTextField();
		jlbLastName = new javax.swing.JLabel();
		jtfLastName = new javax.swing.JTextField();
		jbtSave = new javax.swing.JButton();
		jtfIdentification = new javax.swing.JFormattedTextField();
		jlbIdentification = new javax.swing.JLabel();
		jlbRol = new javax.swing.JLabel();
		jcbRol = new javax.swing.JComboBox<String>();
		jlbLogin = new javax.swing.JLabel();
		jtfLogin = new javax.swing.JTextField();
		jlbPassword = new javax.swing.JLabel();
		jtfPassword = new javax.swing.JTextField();
		jpUserList = new javax.swing.JPanel();
		jpActionButtons = new javax.swing.JPanel();
		jbtUpdate = new javax.swing.JButton();
		jbtDelete = new javax.swing.JButton();
		jspUserList = new javax.swing.JScrollPane();
		jtbUserList = new javax.swing.JTable();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();

		setTitle("Contabilidad");

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Usuarios");

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

		jpNewUser.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Nuevo usuario",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jlbName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbName.setText("Nombre(s):");

		jtfName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbLastName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbLastName.setText("Apellido(s):");

		jtfLastName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

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

		jtfIdentification
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0"))));
		jtfIdentification.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbIdentification.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbIdentification.setText("Cedula:");

		jlbRol.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbRol.setText("Perfil:");

		jcbRol.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbLogin.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbLogin.setText("Login:");

		jtfLogin.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbPassword.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbPassword.setText("Contraseña:");

		jtfPassword.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		javax.swing.GroupLayout jpNewUserLayout = new javax.swing.GroupLayout(
				jpNewUser);
		jpNewUser.setLayout(jpNewUserLayout);
		jpNewUserLayout
				.setHorizontalGroup(jpNewUserLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewUserLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpNewUserLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																jpNewUserLayout
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
																jpNewUserLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpNewUserLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								false)
																						.addComponent(
																								jtfLastName)
																						.addComponent(
																								jtfName)
																						.addComponent(
																								jlbName)
																						.addComponent(
																								jlbIdentification)
																						.addComponent(
																								jlbRol)
																						.addComponent(
																								jlbLastName)
																						.addComponent(
																								jtfIdentification)
																						.addComponent(
																								jcbRol,
																								0,
																								190,
																								Short.MAX_VALUE)
																						.addComponent(
																								jlbLogin)
																						.addComponent(
																								jlbPassword)
																						.addComponent(
																								jtfLogin)
																						.addComponent(
																								jtfPassword))
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		jpNewUserLayout
				.setVerticalGroup(jpNewUserLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpNewUserLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(jlbIdentification)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfIdentification,
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
										.addComponent(jlbLastName)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfLastName,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbRol)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jcbRol,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbLogin)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfLogin,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jlbPassword)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfPassword,
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

		jpUserList.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Listado de usuarios",
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

		jtbUserList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jtbUserList.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] {

				}, new String[] {

				}));
		jspUserList.setViewportView(jtbUserList);

		javax.swing.GroupLayout jpUserListLayout = new javax.swing.GroupLayout(
				jpUserList);
		jpUserList.setLayout(jpUserListLayout);
		jpUserListLayout
				.setHorizontalGroup(jpUserListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpUserListLayout
										.createSequentialGroup()
										.addComponent(jspUserList)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jpActionButtons,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpUserListLayout.setVerticalGroup(jpUserListLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpActionButtons,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addComponent(jspUserList,
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
								.addGap(0, 612, Short.MAX_VALUE)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										388,
										javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jpNewUser,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jpAction,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jpUserList,
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
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING,
												false)
												.addComponent(
														jpNewUser,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		jpUserList,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
		if (this.validateDataForSave()) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_SAVE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				final long identification = this.getIdentificationValue();
				final String name = this.jtfName.getText();
				final String lastName = this.jtfLastName.getText();
				final Rol rol = this.getRolSelected();
				final String login = this.jtfLogin.getText();
				final String password = this.jtfPassword.getText();
				this.userController.saveUser(identification, name, lastName,
						rol, login, password);
				ViewUtils.showMessage(this, ViewUtils.MSG_SAVED,
						ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
				this.refresh();
			}
		}
	}// GEN-LAST:event_jbtSaveActionPerformed

	private void jbtUpdateActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtUpdateActionPerformed
		final List<User> userList = this.getUserListFromTable();
		if (userList != null && this.hasUserToBeUpdated(userList)) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_UPDATE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				for (final User user : userList) {
					boolean edited = false;
					if (user.getNewName() != null
							&& !user.getNewName().equals("")
							&& !user.getNewName().equals(user.getName())) {
						user.setName(user.getNewName());
						edited = true;
					}
					if (user.getNewLastname() != null
							&& !user.getNewLastname().equals("")
							&& !user.getNewLastname()
									.equals(user.getLastname())) {
						user.setLastname(user.getNewLastname());
						edited = true;
					}
					if (user.getNewIdentification() != user.getIdentification()) {
						user.setIdentification(user.getNewIdentification());
						edited = true;
					}
					if (edited) {
						user.setUpdated(new Date());
						this.userController.saveUser(user);
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
		final List<User> userList = this.getUserListFromTable();
		if (userList != null && this.hasUserToBeDeleted(userList)) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_DELETE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				for (final User user : userList) {
					if (user.isDelete()) {
						user.setEnabled(false);
						user.setUpdated(new Date());
						this.userController.saveUser(user);
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

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtDelete;
	private javax.swing.JButton jbtSave;
	private javax.swing.JButton jbtUpdate;
	private javax.swing.JComboBox<String> jcbRol;
	private javax.swing.JLabel jlbIdentification;
	private javax.swing.JLabel jlbLastName;
	private javax.swing.JLabel jlbLogin;
	private javax.swing.JLabel jlbName;
	private javax.swing.JLabel jlbPassword;
	private javax.swing.JLabel jlbRol;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionButtons;
	private javax.swing.JPanel jpNewUser;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JPanel jpUserList;
	private javax.swing.JScrollPane jspUserList;
	private javax.swing.JTable jtbUserList;
	private javax.swing.JFormattedTextField jtfIdentification;
	private javax.swing.JTextField jtfLastName;
	private javax.swing.JTextField jtfLogin;
	private javax.swing.JTextField jtfName;
	private javax.swing.JTextField jtfPassword;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
