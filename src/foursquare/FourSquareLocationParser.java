package foursquare;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import shared.Location;

import java.util.ArrayList;

/**
 * Actor class that parses JSON response from FourSquare Spaces API
 * 
 * @author Ruthie Fields
 *
 */
public class FourSquareLocationParser {
	private ArrayList<Location> locations;
	public static final int MAX_LOCATIONS = 5;

	public FourSquareLocationParser(String apiResponse) {

		locations = new ArrayList<Location>();

		JsonObject jObject = new JsonObject();
		JsonElement jElement = new JsonParser().parse(apiResponse);
		if (jElement instanceof JsonObject) {
			jObject = (JsonObject) jElement;
		}

		// get the groups array
		JsonArray groups = (JsonArray) jObject.getAsJsonObject("response").get("groups");
		JsonObject itemsContainer = (JsonObject) groups.get(0);
		// items.get(0);
		JsonArray items = (JsonArray) itemsContainer.get("items");
		for (int i = 0; i < items.size() && i < MAX_LOCATIONS; i++) {
			JsonObject item0 = (JsonObject) items.get(i);
			JsonObject jsonVenue = (JsonObject) item0.get("venue");
			JsonElement jsonName = jsonVenue.get("name");

			String name = jsonName.getAsString();
//			System.out.println(name);

			JsonObject jsonLocation = (JsonObject) jsonVenue.get("location");
			if (jsonLocation != null && jsonLocation.get("address") != null) {
				JsonElement jsonAddress = jsonLocation.get("address");
				JsonElement jsonLat = jsonLocation.get("lat");
				JsonElement jsonLng = jsonLocation.get("lng");
				String address = jsonAddress.getAsString();
				double lat = jsonLat.getAsDouble();
				double lng = jsonLng.getAsDouble();

				locations.add(new Location(name, lat, lng, address));
			}

		}

//		System.out.println("parsed");
	}

	/**
	 * 
	 * @return ArrayList of Location objects for nearby places of interest
	 */
	public ArrayList<Location> getLocations() {
		return this.locations;
	}
}
