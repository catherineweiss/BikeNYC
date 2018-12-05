package citibike;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * This class contains all methods used in CitiBike main method
 * @author Fred Chang
 *
 */
public class Analyzer {

	ArrayList<Station> stations;

	public Analyzer(ArrayList<Station> stations) {
		this.stations = stations;
	}
	
	/**
	 * 
	 * Method reads the station CSV file and uses station reader to put each into an arraylist object with GPS coordinates
	 */
	public void loadStations() {
		//System.out.println("Loading stations into database...");
		StationReader stationReader = new StationReader("data/station_gps.csv");	
		try {
			stations = stationReader.readStationFile();		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * 
	 * Method takes in station ID and returns station name
	 * @param stationId
	 * @return
	 */
	public String stationIdtoName(int stationId) {
		String stationName = null;

		for (Station s : stations) {
			if (s.getStationId() == stationId) {
				stationName = s.getStationName();
			}
		}

		return stationName;
	}
	
	/**
	 * 
	 * Method takes in station name and returns station ID
	 * @param stationName
	 * @return
	 */
	public int stationNametoId(String stationName) {
		int stationId = 0;

		for (Station s : stations) {
			if (s.getStationName().equals(stationName)) {
				stationId = s.getStationId();
			}
		}

		return stationId;
	}

	/**
	 * 
	 * Method takes in coordinates and returns closest Citibike Station
	 * @param userLat
	 * @param userLong
	 * @return
	 */
	public int analyzeCloseProximity(double userLat, double userLong) {
		int closestStation = 9999;
		double closestDistance = 99999;

		// For all trips
		for (Station s : stations) {
			int station = s.getStationId();

			double stationLat = s.getStationLat();
			double stationLong = s.getStationLong();

			double diffStartLat = Math.abs(userLat - stationLat);

			double diffStartLong = Math.abs(userLong - stationLong);

			double two = 2;
			double difference = (diffStartLat + diffStartLong) / two;

			if (difference < closestDistance) {
				closestDistance = difference;
				closestStation = s.getStationId();
			}

		}

		return closestStation;
	}
	
	/**
	 * 
	 * Method takes in stationId and returns real-time information about the station using CitiBike API
	 * @param stationId
	 * @throws IOException
	 * @throws ParseException
	 * @throws FileNotFoundException
	 */
	public void getStationRealTime(int stationId) throws IOException, ParseException, FileNotFoundException {
		URL url = new URL("https://gbfs.citibikenyc.com/gbfs/en/station_status.json");
		Scanner scan = new Scanner(url.openStream());
		String str = new String();

		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();
		JSONObject obj = new JSONObject(str);
		
		//JSONObject station = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(2);
		
		
		//User can request which station to retrieve info from
		int totalnumStations = obj.getJSONObject("data").getJSONArray("stations").length();
		System.out.println("");
		System.out.println("Searching for station info using CitiBike API...");
		
		for (int i=0; i<totalnumStations; i++) {
			JSONObject stationSearch = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(i);
			if (stationSearch.getInt("station_id") == stationId) {
				System.out.println("Station ID: " + stationSearch.getInt("station_id"));
				System.out.println("Bikes Available: " + stationSearch.getInt("num_bikes_available"));
				System.out.println("Docks Available: " + stationSearch.getInt("num_docks_available"));
			}
		}		
	}

}