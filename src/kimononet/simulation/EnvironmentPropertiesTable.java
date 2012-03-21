package kimononet.simulation;

import java.util.Iterator;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class EnvironmentPropertiesTable extends JTable {

	private PropertyTableModel model;
	private Simulation sim;

	public void refresh() {
		model.getDataVector().clear();
	    Iterator it = sim.getPeerEnvironment().getHashMap().entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        model.addRow(new Object[] {pairs.getKey(), pairs.getValue()});
	    }
	}

	public EnvironmentPropertiesTable(Simulation s) {
		super();

		sim = s;

		model = new PropertyTableModel();

		model.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent tme) {
				if (tme.getType() == TableModelEvent.UPDATE) {
					// Automatically apply user-entered values upon table value change.
					for (int row = 0; row < model.getRowCount(); row++)
						sim.getPeerEnvironment().set((String)model.getValueAt(row, 0), (String)model.getValueAt(row, 1));
					sim.refresh();
				}
			}
		});

		setModel(model);
	}

}
