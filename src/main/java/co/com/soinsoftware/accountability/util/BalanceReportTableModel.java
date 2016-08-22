package co.com.soinsoftware.accountability.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;
import javax.swing.text.NumberFormatter;

import co.com.soinsoftware.accountability.entity.Report;
import co.com.soinsoftware.accountability.entity.ReportItem;

/**
 * @author Carlos Rodriguez
 * @since 18/08/2016
 * @version 1.0
 */
public class BalanceReportTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Cuenta", "Total cuenta",
			"Total" };

	private final Report balanceReport;

	private Object[][] data;

	public BalanceReportTableModel(final Report balanceReport) {
		super();
		this.balanceReport = balanceReport;
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

	public Report getBalanceReport() {
		return this.balanceReport;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][3];
		if (this.balanceReport != null
				&& this.balanceReport.getReportItemSet() != null) {
			int index = 0;
			final Set<ReportItem> reportItemSet = this.balanceReport
					.getReportItemSet();
			final List<ReportItem> reportItemList = this
					.sortReportItemSet(reportItemSet);
			this.addReportItemListToTableData(reportItemList, index);
		}
	}

	private List<ReportItem> sortReportItemSet(
			final Set<ReportItem> reportItemSet) {
		List<ReportItem> reportItemList = new ArrayList<>();
		if (reportItemSet != null && reportItemSet.size() > 0) {
			reportItemList = new ArrayList<>(reportItemSet);
			Collections.sort(reportItemList);
		}
		return reportItemList;
	}

	private int addReportItemListToTableData(
			final List<ReportItem> reportItemList, int index) {
		for (final ReportItem item : reportItemList) {
			final boolean isPositive = (item.getValue() == null || item
					.getValue() >= 0);
			final Long value = (item.getValue() != null) ? ((isPositive) ? item
					.getValue() : item.getValue() * -1) : null;
			final String formattedValue = this.formatValue(value);
			final String valueStr = (value != null) ? ((isPositive) ? "$"
					+ formattedValue : "($" + formattedValue + ")") : "";
			data[index][0] = item.getName();
			if (item.getLevel() == 3) {
				data[index][1] = valueStr;
				data[index][2] = "";
			} else {
				data[index][1] = "";
				data[index][2] = valueStr;
			}
			final List<ReportItem> childrenItemSet = this
					.sortReportItemSet(item.getReportItemSet());
			index = this.addReportItemListToTableData(childrenItemSet, ++index);
		}
		return index;
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.balanceReport.getReportItemSet() != null) {
			rowSize = this.getChildrenSize(this.balanceReport
					.getReportItemSet());
		}
		return rowSize;
	}

	private int getChildrenSize(final Set<ReportItem> reportItemSet) {
		int rowSize = reportItemSet.size();
		for (final ReportItem item : reportItemSet) {
			if (item.getReportItemSet() != null
					&& item.getReportItemSet().size() > 0) {
				rowSize += this.getChildrenSize(item.getReportItemSet());
			}
		}
		return rowSize;
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
}