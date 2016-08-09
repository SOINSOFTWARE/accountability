package co.com.soinsoftware.accountability.util;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Vouchertype;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class VoucherTypeTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Código", "Nombre",
			"Eliminar" };

	private final List<Vouchertype> voucherTypeList;

	private Object[][] data;

	public VoucherTypeTableModel(final List<Vouchertype> voucherTypeList) {
		super();
		this.voucherTypeList = voucherTypeList;
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
		return col > 0;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		final Vouchertype voucherType = this.voucherTypeList.get(row);
		if (col == 1) {
			voucherType.setNewName((String) value);
		} else {
			voucherType.setDelete((Boolean) value);
		}
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(final int col) {
		return getValueAt(0, col).getClass();
	}

	public List<Vouchertype> getVoucherTypeList() {
		return this.voucherTypeList;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][3];
		if (this.voucherTypeList != null) {
			int index = 0;
			for (final Vouchertype voucherType : this.voucherTypeList) {
				data[index][0] = voucherType.getCode();
				data[index][1] = voucherType.getName();
				data[index][2] = new Boolean(false);
				index++;
			}
		}
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.voucherTypeList != null) {
			rowSize = this.voucherTypeList.size();
		}
		return rowSize;
	}
}