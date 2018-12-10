package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foursquare.APICaller;
import util.APIKeys;

/**
 * Tests APICaller, used in Google and FourSquare Packages
 * 
 * @author Catherine Weiss
 *
 */
public class APICallerTest {

	APICaller apiCaller;

	@BeforeEach
	void setUp() {
		apiCaller = new APICaller();
	}

	// Test Purpose: give the APICaller a URL and check that a JSON file received
	@Test
	void testCallerURL() {

		String url = "https://maps.googleapis.com/maps/api/geocode/"
				+ "json?address=20+W+34th+St,+New+York&region=new+york&country:US" + "&key=" + APIKeys.GOOGLE_API_KEY;

		String expectedResponseRaw = "{\n" + "   \"results\" : [\n" + "      {\n"
				+ "         \"address_components\" : [\n" + "            {\n"
				+ "               \"long_name\" : \"20\",\n" + "               \"short_name\" : \"20\",\n"
				+ "               \"types\" : [ \"street_number\" ]\n" + "            },\n" + "            {\n"
				+ "               \"long_name\" : \"West 34th Street\",\n"
				+ "               \"short_name\" : \"W 34th St\",\n" + "               \"types\" : [ \"route\" ]\n"
				+ "            },\n" + "            {\n" + "               \"long_name\" : \"Manhattan\",\n"
				+ "               \"short_name\" : \"Manhattan\",\n"
				+ "               \"types\" : [ \"political\", \"sublocality\", \"sublocality_level_1\" ]\n"
				+ "            },\n" + "            {\n" + "               \"long_name\" : \"New York\",\n"
				+ "               \"short_name\" : \"New York\",\n"
				+ "               \"types\" : [ \"locality\", \"political\" ]\n" + "            },\n"
				+ "            {\n" + "               \"long_name\" : \"New York County\",\n"
				+ "               \"short_name\" : \"New York County\",\n"
				+ "               \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" + "            },\n"
				+ "            {\n" + "               \"long_name\" : \"New York\",\n"
				+ "               \"short_name\" : \"NY\",\n"
				+ "               \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" + "            },\n"
				+ "            {\n" + "               \"long_name\" : \"United States\",\n"
				+ "               \"short_name\" : \"US\",\n"
				+ "               \"types\" : [ \"country\", \"political\" ]\n" + "            },\n" + "            {\n"
				+ "               \"long_name\" : \"10001\",\n" + "               \"short_name\" : \"10001\",\n"
				+ "               \"types\" : [ \"postal_code\" ]\n" + "            }\n" + "         ],\n"
				+ "         \"formatted_address\" : \"20 W 34th St, New York, NY 10001, USA\",\n"
				+ "         \"geometry\" : {\n" + "            \"location\" : {\n"
				+ "               \"lat\" : 40.7487836,\n" + "               \"lng\" : -73.98615769999999\n"
				+ "            },\n" + "            \"location_type\" : \"ROOFTOP\",\n"
				+ "            \"viewport\" : {\n" + "               \"northeast\" : {\n"
				+ "                  \"lat\" : 40.75013258029149,\n" + "                  \"lng\" : -73.9848087197085\n"
				+ "               },\n" + "               \"southwest\" : {\n"
				+ "                  \"lat\" : 40.74743461970849,\n" + "                  \"lng\" : -73.9875066802915\n"
				+ "               }\n" + "            }\n" + "         },\n"
				+ "         \"place_id\" : \"ChIJr92XCKlZwokRIy2j_7Rb2V8\",\n" + "         \"plus_code\" : {\n"
				+ "            \"compound_code\" : \"P2X7+GG New York, United States\",\n"
				+ "            \"global_code\" : \"87G8P2X7+GG\"\n" + "         },\n"
				+ "         \"types\" : [ \"street_address\" ]\n" + "      }\n" + "   ],\n" + "   \"status\" : \"OK\"\n"
				+ "}\n" + "";
		String expectedResponse = expectedResponseRaw.replaceAll("\n", "");

		assertEquals(expectedResponse, apiCaller.callAPI(url));
	}

}
