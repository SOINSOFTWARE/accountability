package co.com.soinsoftware.accountability.util;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Vouchertype;
import co.com.soinsoftware.accountability.entity.Vouchertypexcompany;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherTypeListTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Código", "Nombre" };

	private final List<Vouchertypexcompany> voucherTypeXCompList;

	private Object[][] data;

	public VoucherTypeListTableModel(
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(final int col) {
		return getValueAt(0, col).getClass();
	}

	public Vouchertypexcompany getSelectedVoucherTypeXCompany(final int index) {
		Vouchertypexcompany voucherTypeXComp = null;
		if (index > -1) {
			voucherTypeXComp = this.voucherTypeXCompList.get(index);
		}
		return voucherTypeXComp;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][2];
		if (this.voucherTypeXCompList != null) {
			int index = 0;
			for (final Vouchertypexcompany voucherTypeXComp : this.voucherTypeXCompList) {
				final Vouchertype voucherType = voucherTypeXComp
						.getVouchertype();
				data[index][0] = voucherType.getCode();
				data[index][1] = voucherType.getName();
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