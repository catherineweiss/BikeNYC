package main;

import javax.swing.SwingUtilities;

public class TourNY {

	public static void main(String[] args) {

		
		 SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  TourNYGUI frame = new TourNYGUI();
		      }
		    });
		
	}
}
