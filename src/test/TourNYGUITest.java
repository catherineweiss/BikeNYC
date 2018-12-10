package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.TourNYGUI;
import shared.Location;

/**
 * Tests method within TourNYGUI: String
 * placesOfInterestAsStringBuilder(ArrayList<Location>)
 * 
 * @author Catherine Weiss
 *
 */
public class TourNYGUITest {

	TourNYGUI tourNYGUI;

	@BeforeEach
	void setUp() {
		tourNYGUI = new TourNYGUI();
	}

	// Test Purpose: test method that creates a string from latitude,longitude pairs
	// in an ArrayList<Location>
	@Test
	void testPlacesInterestStringBuilder() {
		Location l1 = new Location("one", 40.1, -74.21, "one");
		Location l2 = new Location("one", 40.2, -74.20, "one");
		Location l3 = new Location("one", 40.3, -74.29, "one");
		Location l4 = new Location("one", 40.4, -74.26, "one");
		Location l5 = new Location("one", 40.5, -74.23, "one");
		Location l6 = new Location("one", 40.6, -74.22, "one");
		Location l7 = new Location("one", 40.7, -74.24, "one");
		ArrayList<Location> locations = new ArrayList<>();
		locations.add(l1);
		locations.add(l2);
		locations.add(l3);
		locations.add(l4);
		locations.add(l5);
		locations.add(l6);
		locations.add(l7);

		String placesOfInterestAsString = tourNYGUI.placesOfInterestAsStringBuilder(locations);

		String expectedString = "40.1,-74.21|40.2,-74.2|40.3,-74.29|40.4,-74.26|40.5,-74.23|40.6,-74.22|40.7,-74.24";

		assertEquals(expectedString, placesOfInterestAsString);

	}

}
