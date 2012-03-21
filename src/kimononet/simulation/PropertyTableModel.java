package kimononet.simulation;

import javax.swing.table.DefaultTableModel;

public class PropertyTableModel extends DefaultTableModel {

	public PropertyTableModel() {
		super();
	}

	public boolean isCellEditable(int row, int col) {
		if (col == 0)
			return false;
		return true;
	}

}
