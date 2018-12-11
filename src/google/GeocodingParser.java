package google;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.Location;

/**
 * Actor class that parses JSON response from Google Geocoding API
 * 
 * @author Catherine Weiss
 *
 */
public class GeocodingParser {

	private Location searchLocation;
	private String status;

	public void parseGeocodingAPIResponse(String apiResponse) {

		Gson gson = new Gson();

		// get the outermost JSON object returned from API
		JsonObject jObject = new JsonObject();
		JsonElement jElement = new JsonParser().parse(apiResponse);
		if (jElement instanceof JsonObject) {
			jObject = (JsonObject) jElement;
		}

		// get the JSON object called "status"
		status = jObject.get("status").toString();
		status = status.replaceAll("\"", "");

		if (status.equals("ZERO_RESULTS")) {
			System.out.println("Address doesn't exist. Please enter a new address.");
		} else if (!status.equals("OK")) {
			System.out.println("There was a problem accessing that location. Please try again.");
		} else {

			// get the JSON array called "results" and access first result in array
			JsonArray jArrayResults = jObject.getAsJsonArray("results");
			JsonObject result1 = (JsonObject) jArrayResults.get(0);

			// get the JSON object called "formatted_address"
			String formattedAddress = result1.get("formatted_address").toString();
			formattedAddress = formattedAddress.replaceAll("\"", "");

			// get the JSON object called "geometry"
			JsonObject jObjectGeometry = result1.getAsJsonObject("geometry");

			// get the JSON object called "location"
			JsonObject jObjectLocation = (JsonObject) jObjectGeometry.get("location");

			// get the strings called lat and lng
			// convert strings to doubles
			String lat = jObjectLocation.get("lat").toString();
			lat = lat.replaceAll(",", "").trim();
			String lng = jObjectLocation.get("lng").toString().trim();
			double latitude = Double.parseDouble(lat);
			double longitude = Double.parseDouble(lng);

			this.searchLocation = new Location("Search Location", latitude, longitude, formattedAddress);

		}
	}

	/**
	 * 
	 * @return Location object for user-specified starting location
	 */
	public Location getOriginLocation() {
		return searchLocation;
	}

	/**
	 * 
	 * @return Status of search that is returned by Google Geocoding API
	 */
	public String getStatus() {
		return status;
	}
}
