package kimononet.simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

public class Simulation {

	private JFrame frame;
	private JTable table;

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

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{350, 0, 0};
		gridBagLayout.rowHeights = new int[]{300, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		// TODO: Just for demo only.
		table = new JTable(new String[][] {	{"Name", "UAV-137"},
											{"Address", "0F:F1:CE:13:37:AB"},
											{"Longitude", "12.34567890"},
											{"Latitude", "-23.4567890"}}, new String[] {"Property", "Value"});
		scrollPane.setViewportView(table);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 1;
		frame.getContentPane().add(scrollPane_1, gbc_scrollPane_1);

		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		textArea.setEditable(false);
		scrollPane_1.setViewportView(textArea);
		// TODO: Just for demo only.
		textArea.setText("Packets Lost: 123\nPacket Delivery Percentage: 98.7%\nRouting Packet Overhead: 0.1%\nAverage Latency: 1 ms");

	}

}
