package kimononet.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;

import kimononet.geo.GeoLocation;
import kimononet.peer.Peer;
import kimononet.peer.PeerAddress;
import kimononet.peer.PeerAddressException;
import kimononet.peer.PeerAgent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class Simulation {

	private final String[] properties = {	"Name",				// Row 0
											"Address",			// Row 1
											"Longitude",		// Row 2
											"Latitude",			// Row 3
											"Accuracy",			// Row 4
											"Speed",			// Row 5
											"Initial Bearing",	// Row 6
											"Final Bearing"};	// Row 7

	private JFrame frame;
	private JTable tablePeerProperties;
	private int currentIndex = -1;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList<String> listPeerAgents = new JList<String>(listModel);
	private KimonoPanel panel;

	private ArrayList<PeerAgent> m_peerAgents = new ArrayList<PeerAgent>();

	private void addPeer() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[6];
		random.nextBytes(bytes);
		m_peerAgents.add(new PeerAgent(new Peer(new PeerAddress(bytes))));

		Peer peer = m_peerAgents.get(m_peerAgents.size() - 1).getPeer();
		listModel.add(listPeerAgents.getModel().getSize(), peer.getName() + " (" + peer.getAddress().toString() + ")");
		frame.repaint();
	}

	private void updateProperties() {
		if (currentIndex < 0)
			return;

		Peer peer = m_peerAgents.get(currentIndex).getPeer();

		String longitude = new String();
		String latitude = new String();
		String accuracy = new String();
		GeoLocation location = peer.getLocation();
		if (location != null) {
			longitude = Double.toString(location.getLongitude());
			latitude = Double.toString(location.getLatitude());
			accuracy = Float.toString(location.getAccuracy());
		}
		else {
			longitude = "n/a";
			latitude = "n/a";
			accuracy = "n/a";
		}

		tableModel.getDataVector().removeAllElements();
		tableModel.addRow(new Object[] {properties[0], peer.getName()});
		tableModel.addRow(new Object[] {properties[1], peer.getAddress().toString()});
		tableModel.addRow(new Object[] {properties[2], longitude});
		tableModel.addRow(new Object[] {properties[3], latitude});
		tableModel.addRow(new Object[] {properties[4], accuracy});
		tableModel.addRow(new Object[] {properties[5], Float.toString(peer.getVelocity().getSpeed())});
		tableModel.addRow(new Object[] {properties[6], Float.toString(peer.getVelocity().getInitialBearing())});
		tableModel.addRow(new Object[] {properties[7], Float.toString(peer.getVelocity().getBearing())});

		frame.repaint();
	}

	public ArrayList<PeerAgent> getPeerAgents() {
		return m_peerAgents;
	}

	/**
	 * Create the application.
	 */
	public Simulation() {
		initialize();
	}

	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Simulation window = new Simulation();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("KimonoNet Simulator");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{350, 0, 0};
		gridBagLayout.rowHeights = new int[]{28, 0, 269, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		BufferedImage imageUAV = null;
		try {
			imageUAV = ImageIO.read(new File("uav.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panel = new KimonoPanel(imageUAV, this);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridheight = 4;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);
		
		JButton buttonAddPeer = new JButton("Add Peer");
		GridBagConstraints gbc_buttonAddPeer = new GridBagConstraints();
		gbc_buttonAddPeer.insets = new Insets(0, 0, 5, 0);
		gbc_buttonAddPeer.gridx = 1;
		gbc_buttonAddPeer.gridy = 1;
		frame.getContentPane().add(buttonAddPeer, gbc_buttonAddPeer);
		buttonAddPeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addPeer();
			}
		});

		JScrollPane scrollPanePeerProperties = new JScrollPane();
		GridBagConstraints gbc_scrollPanePeerProperties = new GridBagConstraints();
		gbc_scrollPanePeerProperties.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPanePeerProperties.fill = GridBagConstraints.BOTH;
		gbc_scrollPanePeerProperties.gridx = 1;
		gbc_scrollPanePeerProperties.gridy = 2;
		frame.getContentPane().add(scrollPanePeerProperties, gbc_scrollPanePeerProperties);
		
		tablePeerProperties = new JTable(tableModel);
		tableModel.addColumn("Property");
		tableModel.addColumn("Value");
		scrollPanePeerProperties.setViewportView(tablePeerProperties);

		JScrollPane scrollPanePeerAgents = new JScrollPane();
		GridBagConstraints gbc_scrollPanePeerAgents = new GridBagConstraints();
		gbc_scrollPanePeerAgents.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPanePeerAgents.fill = GridBagConstraints.BOTH;
		gbc_scrollPanePeerAgents.gridx = 1;
		gbc_scrollPanePeerAgents.gridy = 0;
		frame.getContentPane().add(scrollPanePeerAgents, gbc_scrollPanePeerAgents);
		
		ListSelectionModel listSelectionModel = listPeerAgents.getSelectionModel();
		listSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				ListSelectionModel lsm = (ListSelectionModel)arg0.getSource();
				if (!lsm.isSelectionEmpty()) {
					for (int i = lsm.getMinSelectionIndex(); i <= lsm.getMaxSelectionIndex(); i++) {
						if (lsm.isSelectedIndex(i) && i != currentIndex) {
							currentIndex = i;
							updateProperties();
							break;
						}
					}
				}
			}
		});
		listPeerAgents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPanePeerAgents.setViewportView(listPeerAgents);
		
		JButton buttonApply = new JButton("Apply (Please hit Enter first)");
		buttonApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (currentIndex >= 0) {
					Peer peer = m_peerAgents.get(currentIndex).getPeer();
					peer.setName((String)tableModel.getValueAt(0, 1));
					try {
						double longitude = Double.parseDouble((String)tableModel.getValueAt(2, 1));
						double latitude = Double.parseDouble((String)tableModel.getValueAt(3, 1));
						float accuracy = Float.parseFloat((String)tableModel.getValueAt(4, 1));
						peer.setLocation(new GeoLocation(longitude, latitude, accuracy));
					} catch (NumberFormatException e) {
						
					}
					try {
						peer.setAddress(new PeerAddress((String)tableModel.getValueAt(1, 1)));
					} catch (PeerAddressException e) {
						
					}
					listModel.set(currentIndex, peer.getName() + " (" + peer.getAddress().toString() + ")");
					updateProperties();
				}
			}
		});
		GridBagConstraints gbc_buttonUpdate = new GridBagConstraints();
		gbc_buttonUpdate.insets = new Insets(0, 0, 5, 0);
		gbc_buttonUpdate.gridx = 1;
		gbc_buttonUpdate.gridy = 3;
		frame.getContentPane().add(buttonApply, gbc_buttonUpdate);

		JScrollPane scrollPaneStats = new JScrollPane();
		GridBagConstraints gbc_scrollPaneStats = new GridBagConstraints();
		gbc_scrollPaneStats.gridwidth = 2;
		gbc_scrollPaneStats.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneStats.gridx = 0;
		gbc_scrollPaneStats.gridy = 4;
		frame.getContentPane().add(scrollPaneStats, gbc_scrollPaneStats);

		JTextArea textAreaStats = new JTextArea();
		textAreaStats.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textAreaStats.setEditable(false);
		scrollPaneStats.setViewportView(textAreaStats);
		// TODO: Just for demo only.
		textAreaStats.setText("Packets Lost: 123\nPacket Delivery Percentage: 98.7%\nRouting Packet Overhead: 0.1%\nAverage Latency: 1 ms");

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnSimulation = new JMenu("Simulation");
		menuBar.add(mnSimulation);

		JMenuItem mntmStartSimulation = new JMenuItem("Start Simulation");
		mnSimulation.add(mntmStartSimulation);

		JMenuItem mntmStopSimulation = new JMenuItem("Stop Simulation");
		mnSimulation.add(mntmStopSimulation);

		JSeparator separator = new JSeparator();
		mnSimulation.add(separator);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		mnSimulation.add(mntmExit);

		JMenu mnPeer = new JMenu("Peer");
		menuBar.add(mnPeer);

		JMenuItem mntmNewPeer = new JMenuItem("New Peer");
		mntmNewPeer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addPeer();
			}
		});
		mnPeer.add(mntmNewPeer);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About...");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(frame, "KimonoNet Simulator\n\nCopyright (C) 2012 Eric Bollens, James Hung, Zorayr Khalapyan, Wade Norris.\nAll rights reserved.");
			}
		});
		mnHelp.add(mntmAbout);

	}

}
