package co.com.soinsoftware.accountability.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Company;
import co.com.soinsoftware.accountability.entity.Uap;
import co.com.soinsoftware.accountability.entity.Uapxcompany;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class UapListTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Código", "Nombre" };

	private final Company company;

	private final List<Uap> uapList;

	private Object[][] data;

	public UapListTableModel(final Company company, final List<Uap> uapList, final boolean mustUnpackge) {
		super();
		this.company = company;
		this.uapList = (mustUnpackge) ? new ArrayList<>() : uapList;
		if (mustUnpackge) {
			this.unpackageChildrenList(uapList);
		}
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

	public Uap getSelectedUap(final int index) {
		Uap uap = null;
		if (index > -1) {
			uap = this.uapList.get(index);
		}
		return uap;
	}

	public List<Uap> getUapList() {
		return this.uapList;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][2];
		if (this.uapList != null) {
			int index = 0;
			for (final Uap uap : this.uapList) {
				data[index][0] = String.valueOf(uap.getCode());
				data[index][1] = uap.getName();
				index++;
			}
		}
	}

	private void unpackageChildrenList(final List<Uap> uapList) {
		if (uapList != null && uapList.size() > 0) {
			for (final Uap uap : uapList) {
				if (uap.isEnabled()) {
					boolean isInCompany = this.validateUapIsInCompany(uap);
					if (isInCompany) {
						this.uapList.add(uap);
						final List<Uap> uapChildrenList = this.sortUapSet(uap
								.getUaps());
						this.unpackageChildrenList(uapChildrenList);
					}
				}
			}
		}
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.uapList != null) {
			rowSize = this.uapList.size();
		}
		return rowSize;
	}

	private boolean validateUapIsInCompany(final Uap uap) {
		boolean isInCompany = false;
		final Set<Uapxcompany> uapXCompSet = uap.getUapxcompanies();
		if (uapXCompSet != null) {
			for (final Uapxcompany uapXComp : uapXCompSet) {
				if (uapXComp.getCompany().equals(this.company)
						&& uapXComp.isEnabled()) {
					isInCompany = true;
					break;
				}
			}
		}
		return isInCompany;
	}

	private List<Uap> sortUapSet(final Set<Uap> uapSet) {
		List<Uap> uapList = new ArrayList<>(uapSet);
		List<Uap> sortedUapList = new ArrayList<>();
		if (uapList != null && uapList.size() > 0) {
			uapList = new ArrayList<>(uapSet);
			final Comparator<Uap> byCode = (uap1, uap2) -> Long.compare(
					uap1.getCode(), uap2.getCode());
			sortedUapList = uapList.stream().sorted(byCode)
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return sortedUapList;
	}
}