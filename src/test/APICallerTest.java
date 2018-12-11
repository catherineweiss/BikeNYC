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

	// Test Purpose: give the APICaller a Google URL and check that a JSON file is received
	@Test
	void testCallerGoogleURL() {

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

	
	
	//Test Purpose: give the APICaller a FourSquare url and check that a JSON file is received
	//NOTE: because the requestID changes every time, therefore checking length() is a much more accurate way of determining if returned value is in correct format
	@Test
	void testCallerFourSquareURL() {
		
		String url = "https://api.foursquare.com/v2/venues/explore/?ll=40.75,-73.99&client_id=WW4GNJVY52WPXSFIMTOJ52DPGORJQ04VOZ2SKXLVC3LIULWF&client_secret=VG041DDEZZJQN2B5OGU3HKOVLEQC5RFYE0YV3AIKQ1E5YGZ0&v=20181101&query=landmark&limit=1";
		String api = apiCaller.callAPI(url);
		
		String expectedResponseRaw = "{\"meta\":{\"code\":200,\"requestId\":\"5c0df90ff594df19019ff5a5\"},\"response\":{\"suggestedFilters\":{\"header\":\"Tap to show:\",\"filters\":[{\"name\":\"Open now\",\"key\":\"openNow\"}]},\"warning\":{\"text\":\"There aren't a lot of results for \\\"landmark.\\\" Try something more general, reset your filters, or expand the search area.\"},\"suggestedRadius\":600,\"headerLocation\":\"Chelsea\",\"headerFullLocation\":\"Chelsea, New York\",\"headerLocationGranularity\":\"neighborhood\",\"query\":\"landmark\",\"totalResults\":40,\"suggestedBounds\":{\"ne\":{\"lat\":40.74993057717285,\"lng\":-73.9872137164529},\"sw\":{\"lat\":40.7472309173874,\"lng\":-73.98450256030155}},\"groups\":[{\"type\":\"Recommended Places\",\"name\":\"recommended\",\"items\":[{\"reasons\":{\"count\":0,\"items\":[{\"summary\":\"This spot is popular\",\"type\":\"general\",\"reasonName\":\"globalInteractionReason\"}]},\"venue\":{\"id\":\"43695300f964a5208c291fe3\",\"name\":\"Empire State Building\",\"location\":{\"address\":\"350 5th Ave\",\"crossStreet\":\"btwn 33rd & 34th St\",\"lat\":40.74858074728012,\"lng\":-73.98585813837722,\"distance\":383,\"postalCode\":\"10118\",\"cc\":\"US\",\"neighborhood\":\"Midtown Manhattan, New York, NY\",\"city\":\"New York\",\"state\":\"NY\",\"country\":\"United States\",\"formattedAddress\":[\"350 5th Ave (btwn 33rd & 34th St)\",\"New York, NY 10118\",\"United States\"]},\"categories\":[{\"id\":\"4bf58dd8d48988d130941735\",\"name\":\"Building\",\"pluralName\":\"Buildings\",\"shortName\":\"Building\",\"icon\":{\"prefix\":\"https:\\/\\/ss3.4sqi.net\\/img\\/categories_v2\\/building\\/default_\",\"suffix\":\".png\"},\"primary\":true}],\"photos\":{\"count\":0,\"groups\":[]},\"venuePage\":{\"id\":\"64514349\"}},\"referralId\":\"e-0-43695300f964a5208c291fe3-0\"}]}]}}";		

		String parse1 = expectedResponseRaw.replaceAll("(\\\\{3})","***"); //3 back slashes, replace with ***
		String parse2 = parse1.replaceAll("(\\/{1})", "\\/");
		String parse3 = parse2.replaceAll("\\*{3}", "\\");

		System.out.println(parse3);
		System.out.println(api);
		
		assertEquals(parse3.length(), api.length());

		
		
		
	}
}
