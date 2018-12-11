package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import foursquare.APICaller;
import google.GeocodingParser;
import google.GoogleURLCreator;
import shared.Location;

/**
 * Tests GeocodingParser
 * 
 * @author Catherine Weiss
 *
 */
public class GeocodingParserTest {

	GeocodingParser gParser;

	@BeforeEach
	void setup() {
		gParser = new GeocodingParser();
	}

	// Test Purpose: For Address1 within New York, does parser get latitude
	// correctly?
	@Test
	void parseTestLatitude1() {
		Double expectedLat = 40.7487836;

		String apiResponse = "{   \"results\" : [      {         \"address_components\" : [            {               \"long_name\" : \"20\",               \"short_name\" : \"20\",               \"types\" : [ \"street_number\" ]            },            {               \"long_name\" : \"West 34th Street\",               \"short_name\" : \"W 34th St\",               \"types\" : [ \"route\" ]            },            {               \"long_name\" : \"Manhattan\",               \"short_name\" : \"Manhattan\",               \"types\" : [ \"political\", \"sublocality\", \"sublocality_level_1\" ]            },            {               \"long_name\" : \"New York\",               \"short_name\" : \"New York\",               \"types\" : [ \"locality\", \"political\" ]            },            {               \"long_name\" : \"New York County\",               \"short_name\" : \"New York County\",               \"types\" : [ \"administrative_area_level_2\", \"political\" ]            },            {               \"long_name\" : \"New York\",               \"short_name\" : \"NY\",               \"types\" : [ \"administrative_area_level_1\", \"political\" ]            },            {               \"long_name\" : \"United States\",               \"short_name\" : \"US\",               \"types\" : [ \"country\", \"political\" ]            },            {               \"long_name\" : \"10001\",               \"short_name\" : \"10001\",               \"types\" : [ \"postal_code\" ]            }         ],         \"formatted_address\" : \"20 W 34th St, New York, NY 10001, USA\",         \"geometry\" : {            \"location\" : {               \"lat\" : 40.7487836,               \"lng\" : -73.98615769999999            },            \"location_type\" : \"ROOFTOP\",            \"viewport\" : {               \"northeast\" : {                  \"lat\" : 40.75013258029149,                  \"lng\" : -73.9848087197085               },               \"southwest\" : {                  \"lat\" : 40.74743461970849,                  \"lng\" : -73.9875066802915               }            }         },         \"place_id\" : \"ChIJr92XCKlZwokRIy2j_7Rb2V8\",         \"plus_code\" : {            \"compound_code\" : \"P2X7+GG New York, United States\",            \"global_code\" : \"87G8P2X7+GG\"         },         \"types\" : [ \"street_address\" ]      }   ],   \"status\" : \"OK\"}";
		try {
			gParser.parseGeocodingAPIResponse(apiResponse);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		Location l = gParser.getOriginLocation();
		Double lat = l.getLatitude();

		assertEquals(expectedLat, lat);

	}

	// Test Purpose: For Address1 within New York, does parser get longitude
	// correctly?
	@Test
	void parseTestLongitude1() {
		Double expectedLng = -73.98615769999999;

		String apiResponse = "{   \"results\" : [      {         \"address_components\" : [            {               \"long_name\" : \"20\",               \"short_name\" : \"20\",               \"types\" : [ \"street_number\" ]            },            {               \"long_name\" : \"West 34th Street\",               \"short_name\" : \"W 34th St\",               \"types\" : [ \"route\" ]            },            {               \"long_name\" : \"Manhattan\",               \"short_name\" : \"Manhattan\",               \"types\" : [ \"political\", \"sublocality\", \"sublocality_level_1\" ]            },            {               \"long_name\" : \"New York\",               \"short_name\" : \"New York\",               \"types\" : [ \"locality\", \"political\" ]            },            {               \"long_name\" : \"New York County\",               \"short_name\" : \"New York County\",               \"types\" : [ \"administrative_area_level_2\", \"political\" ]            },            {               \"long_name\" : \"New York\",               \"short_name\" : \"NY\",               \"types\" : [ \"administrative_area_level_1\", \"political\" ]            },            {               \"long_name\" : \"United States\",               \"short_name\" : \"US\",               \"types\" : [ \"country\", \"political\" ]            },            {               \"long_name\" : \"10001\",               \"short_name\" : \"10001\",               \"types\" : [ \"postal_code\" ]            }         ],         \"formatted_address\" : \"20 W 34th St, New York, NY 10001, USA\",         \"geometry\" : {            \"location\" : {               \"lat\" : 40.7487836,               \"lng\" : -73.98615769999999            },            \"location_type\" : \"ROOFTOP\",            \"viewport\" : {               \"northeast\" : {                  \"lat\" : 40.75013258029149,                  \"lng\" : -73.9848087197085               },               \"southwest\" : {                  \"lat\" : 40.74743461970849,                  \"lng\" : -73.9875066802915               }            }         },         \"place_id\" : \"ChIJr92XCKlZwokRIy2j_7Rb2V8\",         \"plus_code\" : {            \"compound_code\" : \"P2X7+GG New York, United States\",            \"global_code\" : \"87G8P2X7+GG\"         },         \"types\" : [ \"street_address\" ]      }   ],   \"status\" : \"OK\"}";
		try {
			gParser.parseGeocodingAPIResponse(apiResponse);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		Location l = gParser.getOriginLocation();
		Double lng = l.getLongitude();

		assertEquals(expectedLng, lng);

	}

	// Test Purpose: For Address2 within New York, does parser get latitude
	// correctly?
	@Test
	void parseTestLatitude2() {
		Double expectedLat = 40.7068661;

		String apiResponse = "{\"results\":[{\"address_components\":[{\"long_name\":\"11\",\"short_name\":\"11\",\"types\":[\"street_number\"]},{\"long_name\":\"Wall Street\",\"short_name\":\"Wall St\",\"types\":[\"route\"]},{\"long_name\":\"Manhattan\",\"short_name\":\"Manhattan\",\"types\":[\"political\",\"sublocality\",\"sublocality_level_1\"]},{\"long_name\":\"New York\",\"short_name\":\"New York\",\"types\":[\"locality\",\"political\"]},{\"long_name\":\"New York County\",\"short_name\":\"New York County\",\"types\":[\"administrative_area_level_2\",\"political\"]},{\"long_name\":\"New York\",\"short_name\":\"NY\",\"types\":[\"administrative_area_level_1\",\"political\"]},{\"long_name\":\"United States\",\"short_name\":\"US\",\"types\":[\"country\",\"political\"]},{\"long_name\":\"10005\",\"short_name\":\"10005\",\"types\":[\"postal_code\"]},{\"long_name\":\"1905\",\"short_name\":\"1905\",\"types\":[\"postal_code_suffix\"]}],\"formatted_address\":\"11 Wall St, New York, NY 10005, USA\",\"geometry\":{\"location\":{\"lat\":40.7068661,\"lng\":-74.01131889999999},\"location_type\":\"ROOFTOP\",\"viewport\":{\"northeast\":{\"lat\":40.7082150802915,\"lng\":-74.00996991970848},\"southwest\":{\"lat\":40.7055171197085,\"lng\":-74.0126678802915}}},\"place_id\":\"ChIJ6Va1IRdawokRhlzzoCgBbzI\",\"plus_code\":{\"compound_code\":\"PX4Q+PF New York, United States\",\"global_code\":\"87G7PX4Q+PF\"},\"types\":[\"street_address\"]}],\"status\":\"OK\"}";
		try {
			gParser.parseGeocodingAPIResponse(apiResponse);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		Location l = gParser.getOriginLocation();
		Double lat = l.getLatitude();

		assertEquals(expectedLat, lat);

	}

	// Test Purpose: For Address2 within New York, does parser get longitude
	// correctly?
	@Test
	void parseTestLongitude2() {
		Double expectedLng = -74.01131889999999;

		String apiResponse = "{\"results\":[{\"address_components\":[{\"long_name\":\"11\",\"short_name\":\"11\",\"types\":[\"street_number\"]},{\"long_name\":\"Wall Street\",\"short_name\":\"Wall St\",\"types\":[\"route\"]},{\"long_name\":\"Manhattan\",\"short_name\":\"Manhattan\",\"types\":[\"political\",\"sublocality\",\"sublocality_level_1\"]},{\"long_name\":\"New York\",\"short_name\":\"New York\",\"types\":[\"locality\",\"political\"]},{\"long_name\":\"New York County\",\"short_name\":\"New York County\",\"types\":[\"administrative_area_level_2\",\"political\"]},{\"long_name\":\"New York\",\"short_name\":\"NY\",\"types\":[\"administrative_area_level_1\",\"political\"]},{\"long_name\":\"United States\",\"short_name\":\"US\",\"types\":[\"country\",\"political\"]},{\"long_name\":\"10005\",\"short_name\":\"10005\",\"types\":[\"postal_code\"]},{\"long_name\":\"1905\",\"short_name\":\"1905\",\"types\":[\"postal_code_suffix\"]}],\"formatted_address\":\"11 Wall St, New York, NY 10005, USA\",\"geometry\":{\"location\":{\"lat\":40.7068661,\"lng\":-74.01131889999999},\"location_type\":\"ROOFTOP\",\"viewport\":{\"northeast\":{\"lat\":40.7082150802915,\"lng\":-74.00996991970848},\"southwest\":{\"lat\":40.7055171197085,\"lng\":-74.0126678802915}}},\"place_id\":\"ChIJ6Va1IRdawokRhlzzoCgBbzI\",\"plus_code\":{\"compound_code\":\"PX4Q+PF New York, United States\",\"global_code\":\"87G7PX4Q+PF\"},\"types\":[\"street_address\"]}],\"status\":\"OK\"}";
		try {
			gParser.parseGeocodingAPIResponse(apiResponse);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		Location l = gParser.getOriginLocation();
		Double lng = l.getLongitude();

		assertEquals(expectedLng, lng);

	}

}
