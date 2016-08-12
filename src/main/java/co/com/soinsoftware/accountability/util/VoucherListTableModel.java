package co.com.soinsoftware.accountability.util;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Vouchertype;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherListTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Número", "Comprobante",
			"Fecha" };

	private final List<Voucher> voucherList;

	private Object[][] data;

	public VoucherListTableModel(final List<Voucher> voucherList) {
		super();
		this.voucherList = voucherList;
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

	public Voucher getSelectedVoucher(final int index) {
		Voucher voucher = null;
		if (index > -1) {
			voucher = this.voucherList.get(index);
		}
		return voucher;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][3];
		if (this.voucherList != null) {
			int index = 0;
			for (final Voucher voucher : this.voucherList) {
				final Vouchertype voucherType = voucher
						.getVouchertypexcompany().getVouchertype();
				data[index][0] = String.valueOf(voucher.getVouchernumber());
				data[index][1] = voucherType.getName();
				data[index][2] = voucher.getVoucherdate();
				index++;
			}
		}
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.voucherList != null) {
			rowSize = this.voucherList.size();
		}
		return rowSize;
	}
}