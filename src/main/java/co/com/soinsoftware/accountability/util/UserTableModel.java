package co.com.soinsoftware.accountability.util;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import co.com.soinsoftware.accountability.entity.Rol;
import co.com.soinsoftware.accountability.entity.User;

/**
 * @author Carlos Rodriguez
 * @since 01/08/2016
 * @version 1.0
 */
public class UserTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 8408209589620109955L;

	private static final String[] COLUMN_NAMES = { "Rol", "Apellido", "Nombre",
			"# Documento", "Login", "Password", "Eliminar" };

	private final List<User> userList;

	private Object[][] data;

	public UserTableModel(final List<User> userList) {
		super();
		this.userList = userList;
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
		return (col > 0 && col != 4 && col != 5);
	}

	@Override
	public void setValueAt(final Object value, final int row, final int col) {
		final User user = this.userList.get(row);
		if (col == 1) {
			user.setNewLastname((String) value);
		}else if (col == 2) {
			user.setNewName((String) value);
		} else if (col == 3) {
			user.setNewIdentification((Long) value);
		} else {
			user.setDelete((Boolean) value);
		}
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Class getColumnClass(final int col) {
		return getValueAt(0, col).getClass();
	}

	public List<User> getUserList() {
		return this.userList;
	}

	private void buildData() {
		final int rowSize = this.getRowSizeToBuild();
		data = new Object[rowSize][7];
		if (this.userList != null) {
			int index = 0;
			for (final User user : this.userList) {
				final Rol rol = user.getRol();
				user.setNewIdentification(user.getIdentification());
				data[index][0] = rol.getName();
				data[index][1] = user.getLastname();
				data[index][2] = user.getName();
				data[index][3] = user.getIdentification();
				data[index][4] = user.getLogin();
				data[index][5] = user.getPassword();
				data[index][6] = new Boolean(false);
				index++;
			}
		}
	}

	private int getRowSizeToBuild() {
		int rowSize = 0;
		if (this.userList != null) {
			rowSize = this.userList.size();
		}
		return rowSize;
	}
}