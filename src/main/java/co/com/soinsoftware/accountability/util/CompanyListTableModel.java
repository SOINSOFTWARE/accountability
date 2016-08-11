package co.com.soinsoftware.accountability.util;

import co.com.soinsoftware.accountability.entity.Company;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class CompanyListTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Tipo", "Nombre",
			"Documento", "# Documento" };

	private final List<Company> companyList;

	private Object[][] data;

	public CompanyListTableModel(final List<Company> companyList) {
		super();
		this.companyList = companyList;
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

	public Company getSelectedCompany(final int index) {
		Company company = null;
		if (index > -1) {
			company = this.companyList.get(index);
		}
		return company;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][4];
		if (this.companyList != null) {
			int index = 0;
			for (final Company company : this.companyList) {
				data[index][0] = company.getCompanytype().getName();
				data[index][1] = company.getName();
				data[index][2] = company.getDocumenttype().getName();
				data[index][3] = company.getDocument();
				index++;
			}
		}
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.companyList != null) {
			rowSize = this.companyList.size();
		}
		return rowSize;
	}
}