package foursquare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Calls the API using a specified URL and saves response.
 * 
 * @author Ruthie Fields
 *
 */
public class APICaller {

	public static String callAPI(String urlForAPI) {

		String response = "";

		try {
			URL url = new URL(urlForAPI);
			URLConnection connection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response += inputLine;
			}
			in.close();

		} catch (IOException e) {
			System.out.println("The url wasn't able to open");
		}

		return response;
	}

}
