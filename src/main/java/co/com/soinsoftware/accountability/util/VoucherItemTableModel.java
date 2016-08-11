package co.com.soinsoftware.accountability.util;

import java.util.List;

import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucheritem;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherItemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Código", "Nombre",
			"Concepto", "Fuente", "Debito", "Credito", "Eliminar" };

	private final JTextField jtfDebtValue;

	private final JTextField jtfCreditValue;

	private final List<Voucheritem> voucherItemList;

	private Object[][] data;

	public VoucherItemTableModel(final List<Voucheritem> voucherItemList,
			final JTextField jtfDebtValue, final JTextField jtfCreditValue) {
		super();
		this.voucherItemList = voucherItemList;
		this.jtfDebtValue = jtfDebtValue;
		this.jtfCreditValue = jtfCreditValue;
		this.buildData();
	}

	@Override
	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	@Override
	public int getRowCount() {
		return this.data.length;
	}

	@Override
	public Object getValueAt(final int row, final int col) {
		return this.data[row][col];
	}

	@Override
	public String getColumnName(final int col) {
		return COLUMN_NAMES[col];
	}

	@Override
	public boolean isCellEditable(final int row, final int col) {
		return col > 1;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		final Voucheritem voucherItem = this.voucherItemList.get(row);
		if (col == 2) {
			voucherItem.setConcept((String) value);
		} else if (col == 3) {
			voucherItem.setSource((String) value);
		} else if (col == 4) {
			voucherItem.setDebtvalue((Long) value);
		} else if (col == 5) {
			voucherItem.setCreditvalue((Long) value);
		} else {
			voucherItem.setDelete((Boolean) value);
		}
		data[row][col] = value;
		fireTableCellUpdated(row, col);
		this.calculateTotalSection();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(final int col) {
		return getValueAt(0, col).getClass();
	}

	public List<Voucheritem> getVoucherItemList() {
		return this.voucherItemList;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][7];
		if (this.voucherItemList != null) {
			int index = 0;
			for (final Voucheritem voucherItem : this.voucherItemList) {
				final Uap uap = voucherItem.getUap();
				data[index][0] = String.valueOf(uap.getCode());
				data[index][1] = uap.getName();
				data[index][2] = voucherItem.getConcept();
				data[index][3] = voucherItem.getSource();
				data[index][4] = voucherItem.getDebtvalue();
				data[index][5] = voucherItem.getCreditvalue();
				data[index][6] = new Boolean(false);
				index++;
			}
			this.calculateTotalSection();
		}
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.voucherItemList != null) {
			rowSize = this.voucherItemList.size();
		}
		return rowSize;
	}

	private void calculateTotalSection() {
		long totalDebt = 0;
		long totalCredit = 0;
		for (final Voucheritem voucherItem : this.voucherItemList) {
			totalDebt += voucherItem.getDebtvalue();
			totalCredit += voucherItem.getCreditvalue();
		}
		this.jtfDebtValue.setText(String.valueOf(totalDebt));
		this.jtfCreditValue.setText(String.valueOf(totalCredit));
		this.jtfDebtValue.requestFocus();
		this.jtfCreditValue.requestFocus();
	}
}