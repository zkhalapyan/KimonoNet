package kimononet.simulation;

import java.awt.Font;

import javax.swing.JTextArea;

import kimononet.stat.StatResults;

public class StatDisplay extends JTextArea {

	public void copyToClipboard() {
		selectAll();
		copy();
		setCaretPosition(0);
	}

	public void refresh(StatResults results) {
		setText((results == null) ? "" : results.toString());
		setCaretPosition(0);
	}

	public StatDisplay() {
		super();

		setFont(new Font("Monospaced", Font.PLAIN, 13));
		setEditable(false);
	}

}
