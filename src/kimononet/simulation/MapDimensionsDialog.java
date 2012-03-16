package kimononet.simulation;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import kimononet.geo.GeoLocation;
import kimononet.geo.GeoLocationException;

public class MapDimensionsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private JTextField textFieldUpperLeftLong;
	private JTextField textFieldUpperLeftLat;
	private JTextField textFieldLowerRightLong;
	private JTextField textFieldLowerRightLat;

	private MapDimensions mapDim;

	/**
	 * Create the dialog.
	 */
	public MapDimensionsDialog(MapDimensions md) {
		mapDim = md;

		setTitle("Edit Map Dimensions");
		setBounds(100, 100, 330, 150);
		setModal(true);
		setResizable(false);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[] {0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[] {0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[] {0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);

		JLabel lblLongitude = new JLabel("Longitude");
		GridBagConstraints gbc_lblLongitude = new GridBagConstraints();
		gbc_lblLongitude.gridx = 1;
		gbc_lblLongitude.gridy = 0;
		gbc_lblLongitude.insets = new Insets(0, 0, 5, 5);
		contentPanel.add(lblLongitude, gbc_lblLongitude);

		JLabel lblLatitude = new JLabel("Latitude");
		GridBagConstraints gbc_lblLatitude = new GridBagConstraints();
		gbc_lblLatitude.gridx = 2;
		gbc_lblLatitude.gridy = 0;
		gbc_lblLatitude.insets = new Insets(0, 0, 5, 0);
		contentPanel.add(lblLatitude, gbc_lblLatitude);

		JLabel lblUpperLeft = new JLabel("Upper Left:");
		GridBagConstraints gbc_lblUpperLeft = new GridBagConstraints();
		gbc_lblUpperLeft.anchor = GridBagConstraints.EAST;
		gbc_lblUpperLeft.gridx = 0;
		gbc_lblUpperLeft.gridy = 1;
		gbc_lblUpperLeft.insets = new Insets(0, 0, 5, 5);
		contentPanel.add(lblUpperLeft, gbc_lblUpperLeft);

		textFieldUpperLeftLong = new JTextField();
		textFieldUpperLeftLong.setColumns(10);
		GridBagConstraints gbc_textFieldUpperLeftLong = new GridBagConstraints();
		gbc_textFieldUpperLeftLong.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUpperLeftLong.gridx = 1;
		gbc_textFieldUpperLeftLong.gridy = 1;
		gbc_textFieldUpperLeftLong.insets = new Insets(0, 0, 5, 5);
		contentPanel.add(textFieldUpperLeftLong, gbc_textFieldUpperLeftLong);

		textFieldUpperLeftLat = new JTextField();
		textFieldUpperLeftLat.setColumns(10);
		GridBagConstraints gbc_textFieldUpperLeftLat = new GridBagConstraints();
		gbc_textFieldUpperLeftLat.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldUpperLeftLat.gridx = 2;
		gbc_textFieldUpperLeftLat.gridy = 1;
		gbc_textFieldUpperLeftLat.insets = new Insets(0, 0, 5, 0);
		contentPanel.add(textFieldUpperLeftLat, gbc_textFieldUpperLeftLat);

		JLabel lblLowerRight = new JLabel("Lower Right:");
		GridBagConstraints gbc_lblLowerRight = new GridBagConstraints();
		gbc_lblLowerRight.anchor = GridBagConstraints.EAST;
		gbc_lblLowerRight.gridx = 0;
		gbc_lblLowerRight.gridy = 2;
		gbc_lblLowerRight.insets = new Insets(0, 0, 0, 5);
		contentPanel.add(lblLowerRight, gbc_lblLowerRight);

		textFieldLowerRightLong = new JTextField();
		textFieldLowerRightLong.setColumns(10);
		GridBagConstraints gbc_textFieldLowerRightLong = new GridBagConstraints();
		gbc_textFieldLowerRightLong.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLowerRightLong.gridx = 1;
		gbc_textFieldLowerRightLong.gridy = 2;
		gbc_textFieldLowerRightLong.insets = new Insets(0, 0, 0, 5);
		contentPanel.add(textFieldLowerRightLong, gbc_textFieldLowerRightLong);

		textFieldLowerRightLat = new JTextField();
		textFieldLowerRightLat.setColumns(10);
		GridBagConstraints gbc_textFieldLowerRightLat = new GridBagConstraints();
		gbc_textFieldLowerRightLat.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldLowerRightLat.gridx = 2;
		gbc_textFieldLowerRightLat.gridy = 2;
		gbc_textFieldLowerRightLong.insets = new Insets(0, 0, 0, 5);
		contentPanel.add(textFieldLowerRightLat, gbc_textFieldLowerRightLat);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					GeoLocation upperLeft = new GeoLocation(Double.parseDouble(textFieldUpperLeftLong.getText()), Double.parseDouble(textFieldUpperLeftLat.getText()), 0f);
					GeoLocation lowerRight = new GeoLocation(Double.parseDouble(textFieldLowerRightLong.getText()), Double.parseDouble(textFieldLowerRightLat.getText()), 0f);
					mapDim.setDimensions(upperLeft, lowerRight);
					MapDimensionsDialog.this.dispose();
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(MapDimensionsDialog.this, "Please enter only numbers.");
				} catch (GeoLocationException gle) {
					JOptionPane.showMessageDialog(MapDimensionsDialog.this, gle.getMessage());
				} catch (MapDimensionsException mde) {
					JOptionPane.showMessageDialog(MapDimensionsDialog.this, mde.getMessage());
				}
			}
		});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				MapDimensionsDialog.this.dispose();
			}
		});
		buttonPane.add(cancelButton);

		textFieldUpperLeftLong.setText(Double.toString(mapDim.getUpperLeft().getLongitude()));
		textFieldUpperLeftLat.setText(Double.toString(mapDim.getUpperLeft().getLatitude()));
		textFieldLowerRightLong.setText(Double.toString(mapDim.getLowerRight().getLongitude()));
		textFieldLowerRightLat.setText(Double.toString(mapDim.getLowerRight().getLatitude()));
	}

}
