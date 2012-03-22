package kimononet.simulation;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kimononet.peer.PeerAgent;

public class PeerList extends JList {

	private DefaultListModel model;
	private Simulation sim;

	private String composeListItem(PeerAgent agent) {
		if (agent == null)
			return null;
		return (agent.getPeer().getName() + " (" + agent.getPeer().getAddress().toString() + ")" + (sim.isReceiver(agent) ? " [RECEIVER]" : ""));
	}

	public boolean isEmpty() {
		return model.isEmpty();
	}

	public int getCurrentlySelectedItemIndex() {
		return getSelectionModel().getMinSelectionIndex();
	}

	public void setCurrentlySelectedItemIndex(int i) {
		getSelectionModel().setSelectionInterval(i, i);
	}

	public void deleteItemAt(int i) {
		if (i >= 0 && i < model.size())
			model.remove(i);
	}

	public void append(PeerAgent agent) {
		if (agent != null) {
			String string = composeListItem(agent);
			if (string != null)
				model.addElement(string);
		}
	}

	public void clear() {
		model.clear();
	}

	public void refresh() {
		refresh(sim.getCurrentPeerAgentIndex());
	}

	public void refresh(int i) {
		if (i >= 0 && i < model.size()) {
			String string = composeListItem(sim.getPeerAgentAt(i));
			if (string != null)
				model.set(i, string);
		}
	}

	public PeerList(Simulation s) {
		super();

		sim = s;

		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		(getSelectionModel()).addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				// Automatically refresh UI upon peer selection.
				ListSelectionModel listSelectionModel = (ListSelectionModel)event.getSource();
				if (!listSelectionModel.isSelectionEmpty())
					sim.refresh();
			}
		});

		model = new DefaultListModel();

		setModel(model);
	}

}
