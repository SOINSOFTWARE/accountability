package co.com.soinsoftware.accountability.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Uap;

/**
 * @author Carlos Rodriguez
 * @since 11/08/2016
 * @version 1.0
 */
public class UapListTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Código", "Nombre" };

	private final List<Uap> uapList;

	private Object[][] data;

	public UapListTableModel(final List<Uap> uapList) {
		super();
		this.uapList = new ArrayList<>();
		this.unpackageChildrenList(uapList);
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
		for (final Uap uap : uapList) {
			this.uapList.add(uap);
			final Set<Uap> uapSet = uap.getUaps();
			if (uapSet != null && uapSet.size() > 0) {
				List<Uap> uapChildrenList = new ArrayList<>(uapSet);
				Collections.sort(uapChildrenList);
				this.unpackageChildrenList(uapChildrenList);
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
}