package main;

import javax.swing.SwingUtilities;

/**
 * Main method for TourNY, a geo-location based recommendation application for
 * Manhattan
 * 
 * @author Catherine Weiss and Fred Chang
 *
 */
public class BikeNYC {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BikeNYCGUI frame = new BikeNYCGUI();
			}
		});

	}
}
