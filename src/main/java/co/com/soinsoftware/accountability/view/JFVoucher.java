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
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import co.com.soinsoftware.accountability.controller.VoucherController;
import co.com.soinsoftware.accountability.controller.VoucherTypeController;
import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Voucheritem;
import co.com.soinsoftware.accountability.entity.Vouchertype;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;
import co.com.soinsoftware.accountability.util.VoucherItemTableModel;

import com.toedter.calendar.JTextFieldDateEditor;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class JFVoucher extends JDialog {

	private static final long serialVersionUID = -1929668527565962771L;

	private static final String MSG_TOTAL_CREDIT_REQUIRED = "El total de la columna credito es mayor a la columna debito";

	private static final String MSG_TOTAL_DEBT_REQUIRED = "El total de la columna debito es mayor a la columna credito";

	private static final String MSG_VALUE_REQUIRED = "El valor de la columna debito y credito debe ser mayor a 0";

	private static final String MSG_VOUCHER_DATE_REQUIRED = "Seleccione la fecha del comprobante";

	private static final String MSG_VOUCHER_ITEM_REQUIRED = "Agregue cuentas, subcuentas o auxiliares al asiento contable";

	private static final String MSG_VOUCHER_ITEM_MIN_REQUIRED = "El asiento contable no es valido, agregue al menos otra cuenta, subcuenta o auxiliar";

	private static final String MSG_VOUCHER_TYPE_COMPANY_REQUIRED = "Seleccione un comprobante usando el boton buscar";

	private final JFMain mainFrame;

	private final JFUapList uapListFrame;

	private final JFVoucherTypeList voucherTypeListFrame;

	private final VoucherController voucherController;

	private final VoucherTypeController voucherTypeController;

	private Company company;

	private JFVoucherList voucherListFrame;

	private Voucher voucher;

	private Set<Voucheritem> voucherItemSet;

	private Vouchertypexcompany voucherTypeXCompany;

	public JFVoucher(final JFMain mainFrame) {
		this.mainFrame = mainFrame;
		this.voucherController = new VoucherController();
		this.voucherTypeController = new VoucherTypeController();
		this.uapListFrame = new JFUapList(this);
		this.voucherTypeListFrame = new JFVoucherTypeList(this);
		this.initComponents();
		final Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		this.setLocation((int) (screenSize.getWidth() / 2 - 390),
				(int) (screenSize.getHeight() / 2 - 370));
		this.setModal(true);
		final JTextFieldDateEditor dateEditor = (JTextFieldDateEditor) this.jdcDate
				.getDateEditor();
		dateEditor.setEditable(false);
	}

	public void refresh(final Company company, final Voucher voucher) {
		this.company = company;
		this.jtfCompanyName.setText(this.company.getName());
		this.voucherTypeXCompany = null;
		this.voucherItemSet = new HashSet<>();
		this.addVoucherItem();
		this.setVoucherTypeXCompanyData("", "", "");
		this.jdcDate.setDate(new Date());
		this.jdcDate.setMaxSelectableDate(new Date());
		this.jtfTotalDebt.setText("0");
		this.jtfTotalCredit.setText("0");
		this.showVoucherData(voucher);
		this.refreshTableData();
	}

	public void setVoucherTypeXCompanyData(
			final Vouchertypexcompany voucherTypeXComp) {
		this.voucherTypeXCompany = voucherTypeXComp;
		final Vouchertype voucherType = this.voucherTypeXCompany
				.getVouchertype();
		final String number = String.valueOf(this.voucherTypeXCompany
				.getNumbercurrent());
		this.setVoucherTypeXCompanyData(voucherType.getCode(),
				voucherType.getName(), number);
		this.jtfNumber.requestFocus();
		this.jdcDate.requestFocus();
	}

	public void setTextToJtfTotalDebt(final String value) {
		this.jtfTotalDebt.setText(value);

	}

	public void setTextToJtfTotalCredit(final String value) {
		this.jtfTotalCredit.setText(value);
	}

	public void addVoucherItem(final Uap uap) {
		final Date currentDate = new Date();
		final Voucheritem voucherItem = new Voucheritem(uap, null, "", "", 0,
				0, currentDate, currentDate, true);
		if (uap.getLevel() > 3) {
			this.removeParentVoucherItem(voucherItem);
		} else {
			this.removeChildrenVoucherItem(voucherItem);
		}
		this.voucherItemSet.add(voucherItem);
		this.refreshTableData();
	}

	public Uap getUap(final long code) {
		this.uapListFrame.refresh(this.company);
		return this.uapListFrame.getUap(code);
	}

	private void addVoucherItem() {
		final Date currentDate = new Date();
		final Uap uap = new Uap(0, "", 3, true, true, true, currentDate,
				currentDate, true);
		final Voucheritem voucherItem = new Voucheritem(uap, null, "", "", 0,
				0, currentDate, currentDate, true);
		this.voucherItemSet.add(voucherItem);
	}

	public void setVoucherListFrame(final JFVoucherList voucherListFrame) {
		this.voucherListFrame = voucherListFrame;
	}

	private void setVoucherTypeXCompanyData(final String code,
			final String name, final String number) {
		this.jtfCode.setText(code);
		this.jtfName.setText(name);
		this.jtfNumber.setText(number);
	}

	private void refreshTableData() {
		final List<Voucheritem> voucherItemList = this.buildVoucherItemList();
		final TableModel model = new VoucherItemTableModel(voucherItemList,
				this);
		this.jtbUapList.setModel(model);
		this.jtbUapList.setFillsViewportHeight(true);
		this.setTableColumnDimensions();
	}

	private void setTableColumnDimensions() {
		for (int i = 0; i < 7; i++) {
			final TableColumn column = this.jtbUapList.getColumnModel()
					.getColumn(i);
			column.setResizable(false);
			if (i == 1) {
				column.setPreferredWidth(211);
			} else {
				column.setPreferredWidth(80);
			}
		}
	}

	private List<Voucheritem> buildVoucherItemList() {
		List<Voucheritem> voucherItemList = new ArrayList<>();
		if (this.voucherItemSet != null && this.voucherItemSet.size() > 0) {
			voucherItemList = new ArrayList<>(this.voucherItemSet);
			Collections.sort(voucherItemList);
		}
		return voucherItemList;
	}

	private void removeParentVoucherItem(final Voucheritem newVoucherItem) {
		final Uap newVIUap = newVoucherItem.getUap();
		if (this.voucherItemSet != null && this.voucherItemSet.size() > 0) {
			final Iterator<Voucheritem> voucherItemIterator = this.voucherItemSet
					.iterator();
			while (voucherItemIterator.hasNext()) {
				final Voucheritem voucheritem = voucherItemIterator.next();
				final Uap uap = voucheritem.getUap();
				if (uap.getLevel() < newVIUap.getLevel()) {
					if (newVIUap.getLevel() == 5) {
						final Uap subAccount = newVIUap.getUap();
						final Uap account = subAccount.getUap();
						if (uap.equals(subAccount) || uap.equals(account)) {
							voucherItemIterator.remove();
						}
					} else {
						final Uap account = newVIUap.getUap();
						if (uap.equals(account)) {
							voucherItemIterator.remove();
						}
					}
				}
			}
		}
	}

	private void removeChildrenVoucherItem(final Voucheritem newVoucherItem) {
		final Uap newVIUap = newVoucherItem.getUap();
		if (this.voucherItemSet != null && this.voucherItemSet.size() > 0) {
			final Iterator<Voucheritem> voucherItemIterator = this.voucherItemSet
					.iterator();
			while (voucherItemIterator.hasNext()) {
				final Voucheritem voucheritem = voucherItemIterator.next();
				final Uap uap = voucheritem.getUap();
				if (uap.getLevel() > newVIUap.getLevel()) {
					if (uap.getLevel() == 5) {
						final Uap subAccount = uap.getUap();
						final Uap account = uap.getUap();
						if (newVIUap.equals(subAccount)
								|| newVIUap.equals(account)) {
							voucherItemIterator.remove();
						}
					} else {
						final Uap account = uap.getUap();
						if (newVIUap.equals(account)) {
							voucherItemIterator.remove();
						}
					}
				}
			}
		}
	}

	private List<Voucheritem> getVoucherItemListFromTable() {
		final TableModel model = this.jtbUapList.getModel();
		return ((VoucherItemTableModel) model).getVoucherItemList();
	}

	private boolean hasVoucherItemToBeDeleted(
			final List<Voucheritem> voucherItemList) {
		boolean hasElements = false;
		for (final Voucheritem voucherItem : voucherItemList) {
			if (voucherItem.isDelete()) {
				hasElements = true;
				break;
			}
		}
		return hasElements;
	}

	private long getTotalDebtValue() {
		String value = this.jtfTotalDebt.getText().replace(",", "")
				.replace(".", "");
		if (value.equals("")) {
			value = "0";
		}
		return Long.valueOf(value);
	}

	private long getTotalCreditValue() {
		String value = this.jtfTotalCredit.getText().replace(",", "")
				.replace(".", "");
		if (value.equals("")) {
			value = "0";
		}
		return Long.valueOf(value);
	}

	private boolean validateDataForSave() {
		boolean valid = true;
		final Date voucherDate = this.jdcDate.getDate();
		final long totalDebt = this.getTotalDebtValue();
		final long totalCredit = this.getTotalCreditValue();
		if (this.voucherTypeXCompany == null) {
			valid = false;
			ViewUtils.showMessage(this, MSG_VOUCHER_TYPE_COMPANY_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (voucherDate == null) {
			valid = false;
			ViewUtils.showMessage(this, MSG_VOUCHER_DATE_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (this.voucherItemSet == null
				|| this.voucherItemSet.size() == 0) {
			valid = false;
			ViewUtils.showMessage(this, MSG_VOUCHER_ITEM_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (this.voucherItemSet != null
				&& this.voucherItemSet.size() == 1) {
			valid = false;
			ViewUtils.showMessage(this, MSG_VOUCHER_ITEM_MIN_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (totalDebt == 0 && totalCredit == 0) {
			valid = false;
			ViewUtils.showMessage(this, MSG_VALUE_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (totalDebt > totalCredit) {
			valid = false;
			ViewUtils.showMessage(this, MSG_TOTAL_DEBT_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		} else if (totalCredit > totalDebt) {
			valid = false;
			ViewUtils.showMessage(this, MSG_TOTAL_CREDIT_REQUIRED,
					ViewUtils.TITLE_REQUIRED_FIELDS, JOptionPane.ERROR_MESSAGE);
		}
		return valid;
	}

	private void showVoucherData(final Voucher voucher) {
		if (voucher != null) {
			final Vouchertype voucherType = voucher.getVouchertypexcompany()
					.getVouchertype();
			final String number = String.valueOf(voucher.getVouchernumber());
			this.setVoucherTypeXCompanyData(voucherType.getCode(),
					voucherType.getName(), number);
			this.jdcDate.setDate(voucher.getVoucherdate());
			this.voucherItemSet = voucher.getVoucheritems();
		}
		this.jbtDeleteVoucher.setVisible((voucher != null));
		this.jtbUapList.setEnabled((voucher == null));
		this.jdcDate.setEnabled((voucher == null));
		this.showActionButtons((voucher == null));
		this.voucher = voucher;
	}

	private void showActionButtons(final boolean visible) {
		this.jbtSearchVoucherType.setVisible(visible);
		this.jpActionButtons.setVisible(visible);
		this.jbtSave.setVisible(visible);
		this.jbtClean.setVisible(visible);
	}

	private void removeEmptyUap() {
		if (this.voucherItemSet != null && this.voucherItemSet.size() > 0) {
			final Iterator<Voucheritem> itemIterator = this.voucherItemSet
					.iterator();
			while (itemIterator.hasNext()) {
				final Voucheritem item = itemIterator.next();
				final Uap uap = item.getUap();
				if (uap.getCode() == 0) {
					itemIterator.remove();
					break;
				}
			}
		}
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
		jpTitle = new javax.swing.JPanel();
		jlbTitle = new javax.swing.JLabel();
		jpVoucherType = new javax.swing.JPanel();
		jlbCode = new javax.swing.JLabel();
		jtfCode = new javax.swing.JTextField();
		jlbName = new javax.swing.JLabel();
		jtfName = new javax.swing.JTextField();
		jbtSearchVoucherType = new javax.swing.JButton();
		jlbNumber = new javax.swing.JLabel();
		jtfNumber = new javax.swing.JFormattedTextField();
		jdcDate = new com.toedter.calendar.JDateChooser();
		jlbDate = new javax.swing.JLabel();
		jtfCompanyName = new javax.swing.JTextField();
		jlbCompanyName = new javax.swing.JLabel();
		jpUapList = new javax.swing.JPanel();
		jspUapList = new javax.swing.JScrollPane();
		jtbUapList = new javax.swing.JTable();
		jpActionButtons = new javax.swing.JPanel();
		jbtAdd = new javax.swing.JButton();
		jbtDelete = new javax.swing.JButton();
		jpTotalSection = new javax.swing.JPanel();
		jtfTotalCredit = new javax.swing.JFormattedTextField();
		jtfTotalDebt = new javax.swing.JFormattedTextField();
		jpAction = new javax.swing.JPanel();
		jbtClose = new javax.swing.JButton();
		jbtSave = new javax.swing.JButton();
		jbtClean = new javax.swing.JButton();
		jbtDeleteVoucher = new javax.swing.JButton();

		setTitle("Asiento contable");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/accountability.png")));

		lbImage.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/images/soin.png"))); // NOI18N

		jpTitle.setBackground(new java.awt.Color(255, 255, 255));

		jlbTitle.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
		jlbTitle.setText("Asiento contable");

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

		jpVoucherType.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Comprobante",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jlbCode.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCode.setText("Código:");

		jtfCode.setEditable(false);
		jtfCode.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbName.setText("Nombre:");

		jtfName.setEditable(false);
		jtfName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jbtSearchVoucherType.setBackground(new java.awt.Color(16, 135, 221));
		jbtSearchVoucherType.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtSearchVoucherType.setForeground(new java.awt.Color(255, 255, 255));
		jbtSearchVoucherType.setText("Buscar");
		jbtSearchVoucherType.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtSearchVoucherType
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jbtSearchVoucherTypeActionPerformed(evt);
					}
				});

		jlbNumber.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbNumber.setText("Número:");

		jtfNumber.setEditable(false);
		jtfNumber
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0"))));
		jtfNumber.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jdcDate.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbDate.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbDate.setText("Fecha:");

		jtfCompanyName.setEditable(false);
		jtfCompanyName.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jlbCompanyName.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jlbCompanyName.setText("Empresa:");

		javax.swing.GroupLayout jpVoucherTypeLayout = new javax.swing.GroupLayout(
				jpVoucherType);
		jpVoucherType.setLayout(jpVoucherTypeLayout);
		jpVoucherTypeLayout
				.setHorizontalGroup(jpVoucherTypeLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpVoucherTypeLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpVoucherTypeLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jtfCompanyName,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																199,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jpVoucherTypeLayout
																		.createSequentialGroup()
																		.addComponent(
																				jbtSearchVoucherType,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jpVoucherTypeLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlbCode)
																						.addComponent(
																								jtfCode,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								100,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jpVoucherTypeLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlbName)
																						.addComponent(
																								jtfName,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								200,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jpVoucherTypeLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jlbNumber)
																						.addComponent(
																								jtfNumber,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								100,
																								javax.swing.GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																		.addGroup(
																				jpVoucherTypeLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jdcDate,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								130,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jlbDate)))
														.addComponent(
																jlbCompanyName))
										.addContainerGap(42, Short.MAX_VALUE)));
		jpVoucherTypeLayout
				.setVerticalGroup(jpVoucherTypeLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpVoucherTypeLayout
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
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jpVoucherTypeLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																jpVoucherTypeLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlbDate)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jdcDate,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE))
														.addGroup(
																jpVoucherTypeLayout
																		.createSequentialGroup()
																		.addComponent(
																				jlbNumber)
																		.addGap(26,
																				26,
																				26))
														.addGroup(
																jpVoucherTypeLayout
																		.createSequentialGroup()
																		.addGroup(
																				jpVoucherTypeLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jlbCode)
																						.addComponent(
																								jlbName))
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addGroup(
																				jpVoucherTypeLayout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jtfCode,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jtfName,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jbtSearchVoucherType,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jtfNumber,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								javax.swing.GroupLayout.DEFAULT_SIZE,
																								javax.swing.GroupLayout.PREFERRED_SIZE))))
										.addContainerGap()));

		jpUapList.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
				"Cuentas",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtbUapList.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N
		jspUapList.setViewportView(jtbUapList);

		jpActionButtons.setBorder(javax.swing.BorderFactory
				.createTitledBorder(""));

		jbtAdd.setBackground(new java.awt.Color(16, 135, 221));
		jbtAdd.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtAdd.setForeground(new java.awt.Color(255, 255, 255));
		jbtAdd.setText("Agregar");
		jbtAdd.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtAddActionPerformed(evt);
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
		jpActionButtonsLayout.setHorizontalGroup(jpActionButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						jpActionButtonsLayout
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(jbtAdd,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(jbtDelete,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										100,
										javax.swing.GroupLayout.PREFERRED_SIZE)
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
										.addGroup(
												jpActionButtonsLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jbtAdd,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jbtDelete,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
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
						.addGroup(
								jpUapListLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpUapListLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jspUapList,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																jpActionButtons,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap()));
		jpUapListLayout
				.setVerticalGroup(jpUapListLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpUapListLayout
										.createSequentialGroup()
										.addComponent(
												jpActionButtons,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jspUapList,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												191, Short.MAX_VALUE)));

		jpTotalSection.setBorder(javax.swing.BorderFactory.createTitledBorder(
				null, "Totales",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Verdana", 1, 12))); // NOI18N

		jtfTotalCredit.setEditable(false);
		jtfTotalCredit
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0"))));
		jtfTotalCredit.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		jtfTotalDebt.setEditable(false);
		jtfTotalDebt
				.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
						new javax.swing.text.NumberFormatter(
								new java.text.DecimalFormat("#,##0"))));
		jtfTotalDebt.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

		javax.swing.GroupLayout jpTotalSectionLayout = new javax.swing.GroupLayout(
				jpTotalSection);
		jpTotalSection.setLayout(jpTotalSectionLayout);
		jpTotalSectionLayout
				.setHorizontalGroup(jpTotalSectionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jpTotalSectionLayout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												jtfTotalDebt,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jtfTotalCredit,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												100,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		jpTotalSectionLayout
				.setVerticalGroup(jpTotalSectionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpTotalSectionLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jpTotalSectionLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jtfTotalCredit,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jtfTotalDebt,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

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

		jbtClean.setBackground(new java.awt.Color(16, 135, 221));
		jbtClean.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtClean.setForeground(new java.awt.Color(255, 255, 255));
		jbtClean.setText("Limpiar");
		jbtClean.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtClean.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtCleanActionPerformed(evt);
			}
		});

		jbtDeleteVoucher.setBackground(new java.awt.Color(16, 135, 221));
		jbtDeleteVoucher.setFont(new java.awt.Font("Verdana", 1, 10)); // NOI18N
		jbtDeleteVoucher.setForeground(new java.awt.Color(255, 255, 255));
		jbtDeleteVoucher.setText("Eliminar");
		jbtDeleteVoucher.setPreferredSize(new java.awt.Dimension(89, 23));
		jbtDeleteVoucher.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jbtDeleteVoucherActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jpActionLayout = new javax.swing.GroupLayout(
				jpAction);
		jpAction.setLayout(jpActionLayout);
		jpActionLayout
				.setHorizontalGroup(jpActionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpActionLayout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												jbtClean,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jbtDeleteVoucher,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jbtSave,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												jbtClose,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		jpActionLayout
				.setVerticalGroup(jpActionLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jpActionLayout
										.createSequentialGroup()
										.addContainerGap()
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
																jbtClean,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jbtSave,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jbtDeleteVoucher,
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
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGap(0, 0, Short.MAX_VALUE)
								.addComponent(lbImage,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										388,
										javax.swing.GroupLayout.PREFERRED_SIZE))
				.addComponent(jpTitle, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jpVoucherType,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jpUapList,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jpTotalSection,
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
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(jpTitle,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jpVoucherType,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpUapList,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpTotalSection,
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

	private void jbtDeleteVoucherActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtDeleteVoucherActionPerformed
		if (this.voucher != null && this.voucherListFrame != null) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_DELETE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				final Date currentDate = new Date();
				final Set<Voucheritem> voucherItemSet = this.voucher
						.getVoucheritems();
				for (final Voucheritem voucherItem : voucherItemSet) {
					voucherItem.setUpdated(currentDate);
					voucherItem.setEnabled(false);
					this.voucherController.saveVoucherItem(voucherItem);
				}
				this.voucher.setUpdated(currentDate);
				this.voucher.setEnabled(false);
				this.voucherController.saveVoucher(this.voucher);
				this.voucherListFrame.refreshTableData();
				this.mainFrame.refresh();
				this.setVisible(false);
			}
		}
	}// GEN-LAST:event_jbtDeleteVoucherActionPerformed

	private void jbtCleanActionPerformed(java.awt.event.ActionEvent evt) {
		this.refresh(this.company, null);
	}// GEN-LAST:event_jbtCleanActionPerformed

	private void jbtAddActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtAddActionPerformed
		this.uapListFrame.refresh(this.company);
		this.uapListFrame.setVisible(true);
	}// GEN-LAST:event_jbtAddActionPerformed

	private void jbtDeleteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtDeleteActionPerformed
		final List<Voucheritem> voucherItemList = this
				.getVoucherItemListFromTable();
		if (voucherItemList != null
				&& this.hasVoucherItemToBeDeleted(voucherItemList)) {
			for (final Voucheritem voucherItem : voucherItemList) {
				if (voucherItem.isDelete()) {
					this.voucherItemSet.remove(voucherItem);
				}
			}
			this.refreshTableData();
		} else {
			ViewUtils.showMessage(this, ViewUtils.MSG_UNSELECTED,
					ViewUtils.TITLE_REQUIRED_FIELDS,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}// GEN-LAST:event_jbtDeleteActionPerformed

	private void jbtSearchVoucherTypeActionPerformed(
			java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSearchVoucherTypeActionPerformed
		this.voucherTypeListFrame.refresh(this.company);
		this.voucherTypeListFrame.setVisible(true);
	}// GEN-LAST:event_jbtSearchVoucherTypeActionPerformed

	private void jbtCloseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtCloseActionPerformed
		this.setVisible(false);
	}// GEN-LAST:event_jbtCloseActionPerformed

	private void jbtSaveActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jbtSaveActionPerformed
		this.removeEmptyUap();
		if (this.validateDataForSave()) {
			final int confirmation = ViewUtils.showConfirmDialog(this,
					ViewUtils.MSG_SAVE_QUESTION, ViewUtils.TITLE_SAVED);
			if (confirmation == JOptionPane.OK_OPTION) {
				final Date voucherDate = this.jdcDate.getDate();
				this.voucherController.saveVoucher(this.voucherTypeXCompany,
						voucherDate, this.voucherItemSet);
				final long numberCurrent = this.voucherTypeXCompany
						.getNumbercurrent() + 1;
				this.voucherTypeXCompany.setNumbercurrent(numberCurrent);
				this.voucherTypeController
						.saveVoucherTypeXCompany(this.voucherTypeXCompany);
				ViewUtils.showMessage(this, ViewUtils.MSG_SAVED,
						ViewUtils.TITLE_SAVED, JOptionPane.INFORMATION_MESSAGE);
				this.refresh(this.company, null);
				this.mainFrame.refresh();
			} else {
				this.addVoucherItem();
			}
		} else {
			this.addVoucherItem();
		}
	}// GEN-LAST:event_jbtSaveActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jbtAdd;
	private javax.swing.JButton jbtClean;
	private javax.swing.JButton jbtClose;
	private javax.swing.JButton jbtDelete;
	private javax.swing.JButton jbtDeleteVoucher;
	private javax.swing.JButton jbtSave;
	private javax.swing.JButton jbtSearchVoucherType;
	private com.toedter.calendar.JDateChooser jdcDate;
	private javax.swing.JLabel jlbCode;
	private javax.swing.JLabel jlbCompanyName;
	private javax.swing.JLabel jlbDate;
	private javax.swing.JLabel jlbName;
	private javax.swing.JLabel jlbNumber;
	private javax.swing.JLabel jlbTitle;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionButtons;
	private javax.swing.JPanel jpTitle;
	private javax.swing.JPanel jpTotalSection;
	private javax.swing.JPanel jpUapList;
	private javax.swing.JPanel jpVoucherType;
	private javax.swing.JScrollPane jspUapList;
	private javax.swing.JTable jtbUapList;
	private javax.swing.JTextField jtfCode;
	private javax.swing.JTextField jtfCompanyName;
	private javax.swing.JTextField jtfName;
	private javax.swing.JFormattedTextField jtfNumber;
	private javax.swing.JFormattedTextField jtfTotalCredit;
	private javax.swing.JFormattedTextField jtfTotalDebt;
	private javax.swing.JLabel lbImage;
	// End of variables declaration//GEN-END:variables
}
