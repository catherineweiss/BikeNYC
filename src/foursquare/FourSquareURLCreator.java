package foursquare;

import java.util.HashMap;
import shared.QueryStringBuilder;
import util.APIKeys;

/**
 * An actor class that constructs URL for the FourSquare API.
 * 
 * @author fieldsruthie
 *
 */
public class FourSquareURLCreator {

	/**
	 * 
	 * @return URL for squarespace API
	 */

	private static final String FOUR_SQUARE_VERSION = "20181101";

	public static String createURL(double lat, double lon, String query) {

		String limit = "10";
		String path = "https://api.foursquare.com/v2/venues/explore/";

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("client_id", APIKeys.FOUR_SQUARE_CLIENT_ID);
		params.put("client_secret", APIKeys.FOUR_SQUARE_CLIENT_SECRET);
		params.put("v", FOUR_SQUARE_VERSION);
		params.put("ll", lat + "," + lon);
		params.put("query", query);
		params.put("limit", limit);

		return path + QueryStringBuilder.createQueryString(params);
	}
}
