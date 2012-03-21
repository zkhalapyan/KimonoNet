package kimononet.simulation;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kimononet.peer.Peer;

public class PeerList extends JList {

	private DefaultListModel model;
	private Simulation sim;

	private String composeListItem(Peer peer) {
		if (peer == null)
			return null;
		return peer.getName() + " (" + peer.getAddress().toString() + ")";
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
		if (i >= 0)
			model.remove(i);
	}

	public void append(Peer peer) {
		if (peer != null) {
			String string = composeListItem(peer);
			if (string != null)
				model.addElement(string);
		}
	}

	public void clear() {
		model.clear();
	}

	public void refresh() {
		String string = composeListItem(sim.getCurrentPeer());
		if (string != null)
			model.set(sim.getCurrentPeerIndex(), string);
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
