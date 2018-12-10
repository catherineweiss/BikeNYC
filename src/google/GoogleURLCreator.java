package google;

import util.APIKeys;

/**
 * Creates URL for Google geocoding API
 * 
 * @author Catherine Weiss
 *
 */
public class GoogleURLCreator {

	/**
	 * 
	 * 
	 * @return URL for Google Geocoding API
	 */

	public String createURL(String searchAddress) {

		searchAddress = searchAddress.replaceAll(" ", "+");
		String path = "https://maps.googleapis.com/maps/api/geocode/";
		String outputFormat = "json";
		String queryParams = "?address=" + searchAddress;
		String regionBias = "&region=new+york";
		String components = "&country:US";
		String apiKey = "&key=" + APIKeys.GOOGLE_API_KEY;
		String geocodeURL = path + outputFormat + queryParams + regionBias + components + apiKey;
		System.out.println(geocodeURL);

		return geocodeURL;
	}

}
