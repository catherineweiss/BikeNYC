import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

class CitiBike{

	public static void main(String[] args) throws IOException {

		URL url = new URL("https://gbfs.citibikenyc.com/gbfs/en/station_status.json");
		Scanner scan = new Scanner(url.openStream());
		String str = new String();

		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();
		JSONObject obj = new JSONObject(str);
		
		
		
		
		
		
		
		JSONObject station = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(2);
		System.out.println("Station ID: " + station.getInt("station_id"));
		System.out.println("Bikes Available: " + station.getInt("num_bikes_available"));
		
		//System.out.println(obj.getJSONObject("data").getJSONArray("stations").length()); //Total Number of Stations
		
		
		
		//User can request which station to retrieve info from
		int stationID = 82;
		int totalnumStations = obj.getJSONObject("data").getJSONArray("stations").length();
		System.out.println("");
		System.out.println("Searching for Station: " + stationID + "...");
		
		for (int i=0; i<totalnumStations; i++) {
			JSONObject stationSearch = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(i);
			if (stationSearch.getInt("station_id") == stationID) {
				System.out.println("Station ID: " + stationSearch.getInt("station_id"));
				System.out.println("Bikes Available: " + stationSearch.getInt("num_bikes_available"));
			}
		}
		
		
		

	}
}