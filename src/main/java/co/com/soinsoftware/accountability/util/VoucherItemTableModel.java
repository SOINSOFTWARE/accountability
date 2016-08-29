package co.com.soinsoftware.accountability.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;

import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Voucheritem;
import co.com.soinsoftware.accountability.view.JFVoucher;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class VoucherItemTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Código", "Nombre",
			"Concepto", "Fuente", "Debito", "Credito", "Eliminar" };

	private final JFVoucher voucherFrame;

	private final List<Voucheritem> voucherItemList;

	private Object[][] data;

	public VoucherItemTableModel(final List<Voucheritem> voucherItemList,
			final JFVoucher voucherFrame) {
		super();
		this.voucherItemList = voucherItemList;
		this.voucherFrame = voucherFrame;
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
		final int lastRow = this.voucherItemList.size() - 1;
		return (col == 0 && row == lastRow) || (col > 1 && row < lastRow);
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		final Voucheritem voucherItem = this.voucherItemList.get(row);
		if (col == 0) {
			final Uap uap = this.getUap((String) value);
			if (uap != null) {
				this.voucherFrame.addVoucherItem(uap);
				return;
			} else {
				return;
			}
		} else if (col == 2) {
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
				data[index][0] = (uap.getCode() == 0) ? "" : String.valueOf(uap
						.getCode());
				data[index][1] = uap.getName();
				data[index][2] = voucherItem.getConcept();
				data[index][3] = voucherItem.getSource();
				data[index][4] = (uap.getCode() == 0) ? "" : voucherItem
						.getDebtvalue();
				data[index][5] = (uap.getCode() == 0) ? "" : voucherItem
						.getCreditvalue();
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
		this.voucherFrame.setTextToJtfTotalDebt(this.formatValue(totalDebt));
		this.voucherFrame
				.setTextToJtfTotalCredit(this.formatValue(totalCredit));
	}

	private String formatValue(final Long value) {
		if (value != null) {
			final NumberFormatter formatter = new NumberFormatter(
					new DecimalFormat("#,##0"));
			try {
				return formatter.valueToString(value);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return (value != null) ? String.valueOf(value) : "";
	}

	private Uap getUap(final String codeStr) {
		Uap uap = null;
		try {
			final long code = Long.parseLong(codeStr);
			uap = this.voucherFrame.getUap(code);
		} catch (NumberFormatException ex) {
			System.out.println(ex.getMessage());
		}
		return uap;
	}
}