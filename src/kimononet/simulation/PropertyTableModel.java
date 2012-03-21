package kimononet.simulation;

import javax.swing.table.DefaultTableModel;

public class PropertyTableModel extends DefaultTableModel {

	public PropertyTableModel() {
		super();
		addColumn("Property");
		addColumn("Value");
	}

	public boolean isCellEditable(int row, int col) {
		// Make property name column not editable.
		if (col == 0)
			return false;
		return true;
	}

}
