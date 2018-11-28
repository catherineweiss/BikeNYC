/**
 * 
 * @author fieldsruthie
 *
 */
public class SquareSpaceURLCreator {

	/**
	 * 
	 * 
	 * @return URL for squarespace API
	 */

	public static final String FOUR_SQUARE_VERSION = "20181101";
	public static String createURL(double lat, double lon, String query) {
		
	    int limit = 10;
		String path = "https://api.foursquare.com/v2/venues/explore/";
		
		String url = path + "?";
		url += "client_id=" + PrivateVariables.FOUR_SQUARE_CLIENT_ID;
		url += "&client_secret=" + PrivateVariables.FOUR_SQUARE_CLIENT_SECRET;
		url += "&v=" + FOUR_SQUARE_VERSION;
		url += "&ll=" + lat + "," + lon;
		url += "&query=" +  query;
		url += "limit=" + limit;

		return url;
	}
}
