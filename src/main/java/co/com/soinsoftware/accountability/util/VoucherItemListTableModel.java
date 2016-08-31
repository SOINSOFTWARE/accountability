package co.com.soinsoftware.accountability.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;

import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucher;
import co.com.soinsoftware.accountability.entity.Voucheritem;

/**
 * @author Carlos Rodriguez
 * @since 31/08/2016
 * @version 1.0
 */
public class VoucherItemListTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Fecha", "Código", "Nombre",
			"Concepto", "Debito", "Credito" };

	private final List<Voucher> voucherList;

	private List<Voucheritem> voucherItemList;

	private Object[][] data;

	private long totalDebt;

	private long totalCredit;

	public VoucherItemListTableModel(final List<Voucher> voucherList) {
		super();
		this.voucherList = voucherList;
		this.totalDebt = 0;
		this.totalCredit = 0;
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

	public List<Voucheritem> getVoucherItemList() {
		return this.voucherItemList;
	}

	public String getTotalDebt() {
		return this.formatValue(this.totalDebt);
	}

	public String getTotalCredit() {
		return this.formatValue(this.totalCredit);
	}

	private void buildData() {
		this.buildVoucherItemList();
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][7];
		if (this.voucherItemList != null) {
			int index = 0;
			for (final Voucheritem voucherItem : this.voucherItemList) {
				final Uap uap = voucherItem.getUap();
				final Voucher voucher = voucherItem.getVoucher();
				data[index][0] = this.formatDate(voucher.getVoucherdate());
				data[index][1] = String.valueOf(uap.getCode());
				data[index][2] = uap.getName();
				data[index][3] = voucherItem.getConcept();
				data[index][4] = (voucherItem.getDebtvalue() == 0) ? "" : this
						.formatValue(voucherItem.getDebtvalue());
				data[index][5] = (voucherItem.getCreditvalue() == 0) ? ""
						: this.formatValue(voucherItem.getCreditvalue());
				this.totalDebt += voucherItem.getDebtvalue();
				this.totalCredit += voucherItem.getCreditvalue();
				index++;
			}
		}
	}

	private void buildVoucherItemList() {
		if (this.voucherList != null) {
			this.voucherItemList = new ArrayList<>();
			for (final Voucher voucher : this.voucherList) {
				final List<Voucheritem> newItemList = new ArrayList<>(
						voucher.getVoucheritems());
				for (final Voucheritem voucherItem : newItemList) {
					voucherItem.setVoucher(voucher);
				}
				Collections.sort(newItemList);
				this.voucherItemList.addAll(newItemList);
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

	private String formatValue(final Long value) {
		if (value != null) {
			final NumberFormatter formatter = new NumberFormatter(
					new DecimalFormat("$#,##0"));
			try {
				return formatter.valueToString(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return (value != null) ? String.valueOf(value) : "";
	}

	private String formatDate(final Date date) {
		final SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd");
		return formatter.format(date);
	}
}