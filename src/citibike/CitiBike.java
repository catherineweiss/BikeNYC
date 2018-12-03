package citibike;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This is the temporary main method that takes a pair of GPS coordinates, returns closest Citibike Station and pulls down real-time station info using Citibike API
 * @author fredchang
 *
 */
class CitiBike{

	public static void main(String[] args) throws IOException, ParseException, FileNotFoundException{

		
		//Enter GPS Coordinates
		double userLat = 40.782486;
		double userLong = -73.946949;
		
		
		//Reads in stations csv file
		StationReader stationReader = new StationReader("data/station_gps.csv");
		ArrayList<Station> stations = new ArrayList<>();
		try {
			stations = stationReader.readStationFile();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//System.out.println(stations.get(1).getStationName());
		
		Analyzer analyzer = new Analyzer(stations);
		
		int closestStationId = analyzer.analyzeCloseProximity(userLat, userLong);
		
		System.out.println("User Location: " + userLat + "," + userLong);
		System.out.println("Closest Station ID: " + closestStationId);
		System.out.println("Closest Station: " + analyzer.stationIdtoName(closestStationId));
		System.out.println("");
		
		
		
		//CitiBike API
		
		
		URL url = new URL("https://gbfs.citibikenyc.com/gbfs/en/station_status.json");
		Scanner scan = new Scanner(url.openStream());
		String str = new String();

		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();
		JSONObject obj = new JSONObject(str);
		
		JSONObject station = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(2);
		
		
		//User can request which station to retrieve info from
		int stationID = closestStationId;
		int totalnumStations = obj.getJSONObject("data").getJSONArray("stations").length();
		System.out.println("");
		System.out.println("Searching for station info using CitiBike API...");
		
		for (int i=0; i<totalnumStations; i++) {
			JSONObject stationSearch = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(i);
			if (stationSearch.getInt("station_id") == stationID) {
				System.out.println("Station ID: " + stationSearch.getInt("station_id"));
				System.out.println("Bikes Available: " + stationSearch.getInt("num_bikes_available"));
				System.out.println("Docks Available: " + stationSearch.getInt("num_docks_available"));
			}
		}
		
		
		

	}
}