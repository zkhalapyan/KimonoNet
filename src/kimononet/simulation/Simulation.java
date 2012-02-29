package kimononet.simulation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import javax.swing.JTextArea;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;

public class Simulation {

	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	 * Create the application.
	 */
	public Simulation() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			// If non-Windows system, should default back to Java L&F.
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame = new JFrame("KimonoNet Simulator");
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(500);
		frame.getContentPane().add(splitPane, "cell 0 0,grow");
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane.setRightComponent(scrollPane_1);

		// TODO: Just for demo only.
		table = new JTable(new String[][] {	{"Name", "UAV-137"},
											{"Address", "0F:F1:CE:13:37:AB"},
											{"Longitude", "12.34567890"},
											{"Latitude", "-23.4567890"}}, new String[] {"Property", "Value"});
		scrollPane_1.setViewportView(table);
		
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 0 1,growx,aligny bottom");
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setRows(5);
		textArea.setColumns(80);
		scrollPane.setViewportView(textArea);
		
		JLabel lblStatistics = new JLabel("Statistics");
		scrollPane.setColumnHeaderView(lblStatistics);

		// TODO: Just for demo only.
		textArea.setText("Packets Lost: 123\nPacket Delivery Percentage: 98.7%\nRouting Packet Overhead: 0.1%\nAverage Latency: 1 ms");
		
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
		
	}

}
