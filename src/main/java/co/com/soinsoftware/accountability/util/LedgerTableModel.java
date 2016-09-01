package co.com.soinsoftware.accountability.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;

import co.com.soinsoftware.accountability.entity.Ledger;
import co.com.soinsoftware.accountability.entity.LedgerItem;

/**
 * @author Carlos Rodriguez
 * @since 01/09/2016
 * @version 1.0
 */
public class LedgerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Código", "Nombre", "#",
			"Fecha", "Debito", "Credito", "S debito", "S credito" };

	private final Ledger report;

	private Object[][] data;

	public LedgerTableModel(final Ledger report) {
		super();
		this.report = report;
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

	public Ledger getLedgerReport() {
		return this.report;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][8];
		if (this.report != null && this.report.getLedgerItemSet() != null) {
			int index = 0;
			final Set<LedgerItem> ledgerItemSet = this.report
					.getLedgerItemSet();
			final List<LedgerItem> ledgerItemList = this
					.sortLedgerItemSet(ledgerItemSet);
			this.addLedgerItemListToTableData(ledgerItemList, index);
		}
	}

	private int addLedgerItemListToTableData(
			final List<LedgerItem> ledgerItemList, int index) {
		for (final LedgerItem item : ledgerItemList) {
			this.calculateTotals(item);
			data[index][0] = (item.getCode() == 0) ? "" : String.valueOf(item
					.getCode());
			data[index][1] = item.getName();
			data[index][2] = (item.getNumber() == 0) ? "" : String.valueOf(item
					.getNumber());
			data[index][3] = (item.getDate() == null) ? "" : this
					.formatDate(item.getDate());
			data[index][4] = (item.getDebt() == 0) ? "" : this.formatValue(item
					.getDebt());
			data[index][5] = (item.getCredit() == 0) ? "" : this
					.formatValue(item.getCredit());
			data[index][6] = (item.getSubTotalDebt() == 0) ? "" : this
					.formatValue(item.getSubTotalDebt());
			data[index][7] = (item.getSubTotalCredit() == 0) ? "" : this
					.formatValue(item.getSubTotalCredit());
			index = this.addLedgerItemListToTableData(item.getLedgerItemList(),
					++index);
		}
		return index;
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.report.getLedgerItemSet() != null) {
			rowSize = this.getChildrenSize(this.report.getLedgerItemSet());
		}
		return rowSize;
	}

	private int getChildrenSize(final Collection<LedgerItem> reportItemSet) {
		int rowSize = reportItemSet.size();
		for (final LedgerItem item : reportItemSet) {
			if (item.getLedgerItemList() != null
					&& item.getLedgerItemList().size() > 0) {
				rowSize += this.getChildrenSize(item.getLedgerItemList());
			}
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

	private List<LedgerItem> sortLedgerItemSet(
			final Set<LedgerItem> ledgerItemSet) {
		List<LedgerItem> sortLedgerItemList = new ArrayList<>();
		if (ledgerItemSet != null && ledgerItemSet.size() > 0) {
			final List<LedgerItem> ledgerItemList = new ArrayList<>(
					ledgerItemSet);
			final Comparator<LedgerItem> byCode = (item1, item2) -> Long
					.compare(item1.getCode(), item2.getCode());
			final Comparator<LedgerItem> byDate = (item1, item2) -> item1
					.getDate().compareTo(item2.getDate());
			sortLedgerItemList = ledgerItemList.stream()
					.sorted(byCode.thenComparing(byDate))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return sortLedgerItemList;
	}

	private String formatDate(final Date date) {
		final SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd");
		return formatter.format(date);
	}

	private void calculateTotals(final LedgerItem item) {
		if (item.getCode() == 0 && item.getNumber() == 0) {
			final long newTotalDebt = this.report.getTotalDebt()
					+ item.getDebt();
			final long newTotalCredit = this.report.getTotalCredit()
					+ item.getCredit();
			this.report.setTotalDebt(newTotalDebt);
			this.report.setTotalCredit(newTotalCredit);
		}
	}
}