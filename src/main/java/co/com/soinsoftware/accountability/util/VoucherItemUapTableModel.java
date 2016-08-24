package co.com.soinsoftware.accountability.util;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucheritem;

/**
 * @author Carlos Rodriguez
 * @since 24/08/2016
 * @version 1.0
 */
public class VoucherItemUapTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Nombre", "Debito",
			"Credito" };

	private final List<Voucheritem> voucherItemList;

	private Object[][] data;

	public VoucherItemUapTableModel(final List<Voucheritem> voucherItemList) {
		super();
		this.voucherItemList = voucherItemList;
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

	public Voucheritem getSelectedVoucherItem(final int index) {
		Voucheritem voucherItem = null;
		if (index > -1) {
			voucherItem = this.voucherItemList.get(index);
		}
		return voucherItem;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][3];
		if (this.voucherItemList != null) {
			int index = 0;
			for (final Voucheritem voucherItem : this.voucherItemList) {
				final Uap uap = voucherItem.getUap();
				data[index][0] = uap.getName();
				data[index][1] = voucherItem.getDebtvalue();
				data[index][2] = voucherItem.getCreditvalue();
				index++;
			}
		}
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.voucherItemList != null) {
			rowSize = this.voucherItemList.size();
		}
		return rowSize;
	}
}