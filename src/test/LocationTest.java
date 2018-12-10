package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shared.Location;

/**
 * Tests Location class
 * 
 * @author Catherine Weiss
 *
 */
public class LocationTest {

	Location loc;

	@BeforeEach
	public void setUp() {
		loc = new Location(null, 0, 0, null);
	}

	@Test
	public void setNameTest1() {
		String newName = "Grand Central Station";
		loc.setName(newName);
		assertEquals("Grand Central Station", loc.getName());
	}

	@Test
	public void setAddressTest1() {
		String newAddress = "20 W 34th St, New York, NY 10001";
		loc.setAddress(newAddress);
		assertEquals("20 W 34th St, New York, NY 10001", loc.getAddress());
	}

	@Test
	public void setLatitudeTest1() {
		loc.setLatitude(40.7);
		assertEquals(40.7, loc.getLatitude());
	}

	@Test
	public void setLongitudeTest1() {
		double newLng = -73.986;
		loc.setLongitude(newLng);
		assertEquals(-73.986, loc.getLongitude());
	}

}
