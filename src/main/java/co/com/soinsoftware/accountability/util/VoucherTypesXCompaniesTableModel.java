package co.com.soinsoftware.accountability.util;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;

/**
 * @author Carlos Rodriguez
 * @since 09/08/2016
 * @version 1.0
 */
public class VoucherTypesXCompaniesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Empresa", "Comprobante",
			"Inicial", "Final", "Eliminar" };

	private final List<Vouchertypexcompany> voucherTypeXCompList;

	private Object[][] data;

	public VoucherTypesXCompaniesTableModel(
			final List<Vouchertypexcompany> voucherTypeXCompList) {
		super();
		this.voucherTypeXCompList = voucherTypeXCompList;
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
		return col == 4;
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		final Vouchertypexcompany voucherType = this.voucherTypeXCompList
				.get(row);
		voucherType.setDelete((Boolean) value);
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(final int col) {
		return getValueAt(0, col).getClass();
	}

	public List<Vouchertypexcompany> getVoucherTypeXCompList() {
		return this.voucherTypeXCompList;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][5];
		if (this.voucherTypeXCompList != null) {
			int index = 0;
			for (final Vouchertypexcompany voucherTypeXComp : this.voucherTypeXCompList) {
				data[index][0] = voucherTypeXComp.getCompany().getName();
				data[index][1] = voucherTypeXComp.getVouchertype().getName();
				data[index][2] = voucherTypeXComp.getNumberfrom();
				data[index][3] = voucherTypeXComp.getNumberto();
				data[index][4] = new Boolean(false);
				index++;
			}
		}
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.voucherTypeXCompList != null) {
			rowSize = this.voucherTypeXCompList.size();
		}
		return rowSize;
	}
}