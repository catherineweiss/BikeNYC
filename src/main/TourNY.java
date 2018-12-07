package main;

import javax.swing.SwingUtilities;

/**
 * GUI and main method for TourNY, a geo-location based recommendation application
 * for Manhattan 
 * @author Catherine Weiss, Fred Chang, Ruthie Fields
 *
 */
public class TourNY {

	public static void main(String[] args) {

		
		 SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  TourNYGUI frame = new TourNYGUI();
		      }
		    });
		
	}
}
