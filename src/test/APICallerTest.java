package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.APICaller;
import main.GoogleAPIKey;

public class APICallerTest {
	
	APICaller apiCaller;
	
	@BeforeEach
	void setUp() {
		apiCaller = new APICaller();
	}
	 
	//Test Purpose: give the APICaller a URL and check that a JSON file received
	@Test
	void testCallerGoodURL() {
		
		String goodURL = "https://maps.googleapis.com/maps/api/geocode/"
				+"json?address=20+W+34th+St,+New+York&region=new+york&country:US"
				+"&key=" + GoogleAPIKey.key;
		String expectedResponse = "{\"results\":[{\"address_components\":[{\"long_name\":\"20\",\"short_name\":\"20\",\"types\":[\"street_number\"]},{\"long_name\":\"West 34th Street\",\"short_name\":\"W 34th St\",\"types\":[\"route\"]},{\"long_name\":\"Manhattan\",\"short_name\":\"Manhattan\",\"types\":[\"political\",\"sublocality\",\"sublocality_level_1\"]},{\"long_name\":\"New York\",\"short_name\":\"New York\",\"types\":[\"locality\",\"political\"]},{\"long_name\":\"New York County\",\"short_name\":\"New York County\",\"types\":[\"administrative_area_level_2\",\"political\"]},{\"long_name\":\"New York\",\"short_name\":\"NY\",\"types\":[\"administrative_area_level_1\",\"political\"]},{\"long_name\":\"United States\",\"short_name\":\"US\",\"types\":[\"country\",\"political\"]},{\"long_name\":\"10001\",\"short_name\":\"10001\",\"types\":[\"postal_code\"]}],\"formatted_address\":\"20 W 34th St, New York, NY 10001, USA\",\"geometry\":{\"location\":{\"lat\":40.7487836,\"lng\":-73.98615769999999},\"location_type\":\"ROOFTOP\",\"viewport\":{\"northeast\":{\"lat\":40.75013258029149,\"lng\":-73.9848087197085},\"southwest\":{\"lat\":40.74743461970849,\"lng\":-73.9875066802915}}},\"place_id\":\"ChIJr92XCKlZwokRIy2j_7Rb2V8\",\"plus_code\":{\"compound_code\":\"P2X7+GG New York, United States\",\"global_code\":\"87G8P2X7+GG\"},\"types\":[\"street_address\"]}],\"status\":\"OK\"}";
		assertEquals(expectedResponse, apiCaller.callAPI(goodURL));		
	}

	
/*	//Test Purpose: give the APICaller a bad URL and check error
	@Test
	void testCallerBadURL() {
		
		String badURL = "https://maps.apis.com/maps/api/geocode/"
				+"json?address=20+W+34th+St,+New+York&region=new+york&country:US"
				+"&key=" + GoogleAPIKey.key;
		
		assertEquals(,);

	}
*/	
	
}
