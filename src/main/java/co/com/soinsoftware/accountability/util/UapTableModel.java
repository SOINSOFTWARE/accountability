package co.com.soinsoftware.accountability.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Uap;

/**
 * @author Carlos Rodriguez
 * @since 04/08/2016
 * @version 1.0
 */
public class UapTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Código", "Nombre",
			"Eliminar" };

	private final List<Uap> uapList;

	private Object[][] data;

	public UapTableModel(final List<Uap> uapList) {
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

	@Override
	public boolean isCellEditable(final int row, final int col) {
		final Uap uap = this.uapList.get(row);
		return (col > 0 && uap.isEditable());
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		final Uap uap = this.uapList.get(row);
		if (col == 1) {
			uap.setNewName((String) value);
		} else {
			uap.setDelete((Boolean) value);
		}
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(final int col) {
		return getValueAt(0, col).getClass();
	}

	public List<Uap> getUapList() {
		return this.uapList;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][3];
		if (this.uapList != null) {
			int index = 0;
			for (final Uap uap : this.uapList) {
				data[index][0] = String.valueOf(uap.getCode());
				data[index][1] = uap.getName();
				data[index][2] = new Boolean(false);
				index++;
			}
		}
	}

	private void unpackageChildrenList(final List<Uap> uapList) {
		for (final Uap uap : uapList) {
			if (uap.isEnabled()) {
				this.uapList.add(uap);
				final Set<Uap> uapSet = uap.getUaps();
				if (uapSet != null && uapSet.size() > 0) {
					List<Uap> uapChildrenList = new ArrayList<>(uapSet);
					Collections.sort(uapChildrenList);
					this.unpackageChildrenList(uapChildrenList);
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
}