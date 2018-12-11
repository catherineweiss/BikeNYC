package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foursquare.APICaller;
import google.GeocodingParser;
import google.GoogleURLCreator;
import main.BikeNYCGUI;
import shared.Location;

/**
 * Tests method within TourNYGUI: String
 * placesOfInterestAsStringBuilder(ArrayList<Location>)
 * 
 * @author Catherine Weiss
 *
 */
public class BikeNYCGUITest {

	BikeNYCGUI tourNYGUI;

	@BeforeEach
	void setUp() {
		tourNYGUI = new BikeNYCGUI();
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
	
	//Test user input and ensures correct status message is returned. Test Reason: input without zipcode
	@Test
	void testStatusMessageNoZip() {
		String statusMessage;
		
		String startLocation = "Central Park"; //User input value
		
		GoogleURLCreator gc = new GoogleURLCreator();
		String googleURL = gc.createURL(startLocation);
		APICaller ac = new APICaller();
		String gResponse = ac.callAPI(googleURL);
		GeocodingParser gp = new GeocodingParser();
		try {
			gp.parseGeocodingAPIResponse(gResponse);
		} catch (IllegalArgumentException | IOException e2) {
			e2.printStackTrace();
		}
		double userLat = gp.getOriginLocation().getLatitude();
		double userLong = gp.getOriginLocation().getLongitude();
		String startLocationAsString = gp.getOriginLocation().getLatLongString();

		// fills Starting Address
		String formattedAddress = gp.getOriginLocation().getAddress();
		
		System.out.println(formattedAddress);
		
		if (!(formattedAddress.contains("New York, NY"))) {
//			System.out.println(formattedAddress);
			statusMessage = "Enter an address in Manhattan:";
		}
		
		else {
			statusMessage = "We found you!";
		}	
		
		assertEquals(statusMessage, "We found you!");
		
	}
	
	
	//Test user input and ensures correct status message is returned. Test Reason: input on the boundary of Manhattan and neighbors
	@Test
	void testStatusMessageBoundary() {
		String statusMessage;
		
		String startLocation = "Brookyln Bridge"; //User input value
		
		GoogleURLCreator gc = new GoogleURLCreator();
		String googleURL = gc.createURL(startLocation);
		APICaller ac = new APICaller();
		String gResponse = ac.callAPI(googleURL);
		GeocodingParser gp = new GeocodingParser();
		try {
			gp.parseGeocodingAPIResponse(gResponse);
		} catch (IllegalArgumentException | IOException e2) {
			e2.printStackTrace();
		}
		double userLat = gp.getOriginLocation().getLatitude();
		double userLong = gp.getOriginLocation().getLongitude();
		String startLocationAsString = gp.getOriginLocation().getLatLongString();

		// fills Starting Address
		String formattedAddress = gp.getOriginLocation().getAddress();
		
		System.out.println(formattedAddress);
		
		if (!(formattedAddress.contains("New York, NY"))) {
//			System.out.println(formattedAddress);
			statusMessage = "Enter an address in Manhattan:";
		}
		
		else {
			statusMessage = "We found you!";
		}	
		
		assertEquals(statusMessage, "We found you!");
		
	}
	
	
	//Test user input and ensures correct status message is returned. Test Reason: input as a pair of coordinates
	@Test
	void testStatusMessageCoordinates() {
		String statusMessage;
		
		String startLocation = "40.729578, -73.978287"; //User input value
		
		GoogleURLCreator gc = new GoogleURLCreator();
		String googleURL = gc.createURL(startLocation);
		APICaller ac = new APICaller();
		String gResponse = ac.callAPI(googleURL);
		GeocodingParser gp = new GeocodingParser();
		try {
			gp.parseGeocodingAPIResponse(gResponse);
		} catch (IllegalArgumentException | IOException e2) {
			e2.printStackTrace();
		}
		double userLat = gp.getOriginLocation().getLatitude();
		double userLong = gp.getOriginLocation().getLongitude();
		String startLocationAsString = gp.getOriginLocation().getLatLongString();

		// fills Starting Address
		String formattedAddress = gp.getOriginLocation().getAddress();
		
		System.out.println(formattedAddress);
		
		if (!(formattedAddress.contains("New York, NY"))) {
//			System.out.println(formattedAddress);
			statusMessage = "Enter an address in Manhattan:";
		}
		
		else {
			statusMessage = "We found you!";
		}	
		
		assertEquals(statusMessage, "We found you!");
		
	}

}
