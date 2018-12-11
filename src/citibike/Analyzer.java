package citibike;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONObject;

/**
 * This is an actor class that takes a pair of GPS coordinates, returns closest
 * Citibike Station and pulls down real-time station info using Citibike API
 * 
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
	 * Method reads the station CSV file and uses station reader to put each into an
	 * arraylist object with GPS coordinates Note: Because there is no JSON feed for
	 * real time station with coordinates. Parsing of stations and linking with
	 * appropriate GPS coordinates are needed on our end to match station IDs with
	 * coordinate location
	 */
	public void loadStations() {
		// System.out.println("Loading stations into database...");
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
	 * 
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
	 * 
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
	 * Method takes in station ID of closest CitiBike station and returns its
	 * latitude coordinates
	 * 
	 * @param closestStationId
	 * @return
	 */
	public double getClosestStationLat(int closestStationId) {
		double stationLat = 0;
		for (Station s : stations) {
			if (s.getStationId() == closestStationId) {
				stationLat = s.getStationLat();
			}
		}

		return stationLat;
	}

	/**
	 * 
	 * Method takes in station ID of closest CitiBike station and returns its
	 * latitude coordinates
	 * 
	 * @param closestStationId
	 * @return
	 */
	public double getClosestStationLong(int closestStationId) {
		double stationLong = 0;
		for (Station s : stations) {
			if (s.getStationId() == closestStationId) {
				stationLong = s.getStationLong();
			}
		}

		return stationLong;
	}

	/**
	 * 
	 * Method takes in two pairs of coordinates and returns distance in miles Note:
	 * Distance calculation formula using Haversine, src:
	 * https://rosettacode.org/wiki/Haversine_formula#Java
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
	public static double getDistanceHaversine(double lat1, double lon1, double lat2, double lon2) {
		double radius = 6372.8; // earth radius in kilometers
		double latDiff = Math.toRadians(lat2 - lat1);
		double lonDiff = Math.toRadians(lon2 - lon1);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);

		double a = Math.pow(Math.sin(latDiff / 2), 2)
				+ Math.pow(Math.sin(lonDiff / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		double distanceKM = radius * c;
		double distanceMiles = Math.round(distanceKM * 0.62137 * 100.0) / 100.0; // return distance in miles with 2
																					// decimal places precision

		return distanceMiles;
	}

	/**
	 * 
	 * Method takes in coordinates and returns distance to closest Citibike Station (a double value)
	 * 
	 * @param userLat
	 * @param userLong
	 * @return
	 */
	public double analyzeCloseProximityDistance(double userLat, double userLong) {
		int closestStation = 9999;
		double closestDistance = 99999; //artib

		// For all trips
		for (Station s : stations) {
			int station = s.getStationId();

			double stationLat = s.getStationLat();
			double stationLong = s.getStationLong();

			double difference = getDistanceHaversine(stationLat, stationLong, userLat, userLong);

			if (difference < closestDistance) {
				closestDistance = difference;
			}
		}

		if (closestDistance == 99999 || closestStation == 99999) {
			System.out.println("Error executing analyzeCloseProximityDistance() ");
			return -1;
		}

		return closestDistance;
	}
	
	/**
	 * 
	 * Method takes in coordinates and returns closest Citibike Station (as closestStationId)
	 * 
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

			double difference = getDistanceHaversine(stationLat, stationLong, userLat, userLong);

			if (difference < closestDistance) {
				closestDistance = difference;
				closestStation = s.getStationId();
			}

			if (closestDistance == 99999 || closestStation == 99999) {
				System.out.println("Error executing analyzeCloseProximityDistance() ");
				return -1;
			}

		}

		if (closestDistance == 99999 || closestStation == 99999) {
			System.out.println("Error executing analyzeCloseProximity() ");
			return -1;
		}

		return closestStation;
	}


	/**
	 * 
	 * Method takes in stationId and returns real-time information on number of
	 * bikes available at station using CitiBike API
	 * 
	 * @param stationId
	 * @throws IOException
	 * @throws ParseException
	 */
	public int getCitiAPINumBikes(int stationId) throws IOException, ParseException {
		URL url = new URL("https://gbfs.citibikenyc.com/gbfs/en/station_status.json");
		Scanner scan = new Scanner(url.openStream());
		String str = new String();

		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();
		JSONObject obj = new JSONObject(str);

		// User can request which station to retrieve info from
		int totalnumStations = obj.getJSONObject("data").getJSONArray("stations").length();

		for (int i = 0; i < totalnumStations; i++) {
			JSONObject stationSearch = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(i);
			if (stationSearch.getInt("station_id") == stationId) {
				return stationSearch.getInt("num_bikes_available");
			}
		}
		return 0;
	}

	/**
	 * 
	 * Method takes in stationId and returns real-time information on number of
	 * empty spaces available at station using CitiBike API
	 * 
	 * @param stationId
	 * @throws IOException
	 * @throws ParseException
	 */
	public int getCitiAPINumSpaces(int stationId) throws IOException, ParseException {
		URL url = new URL("https://gbfs.citibikenyc.com/gbfs/en/station_status.json");
		Scanner scan = new Scanner(url.openStream());
		String str = new String();

		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();
		JSONObject obj = new JSONObject(str);

		// User can request which station to retrieve info from
		int totalnumStations = obj.getJSONObject("data").getJSONArray("stations").length();

		for (int i = 0; i < totalnumStations; i++) {
			JSONObject stationSearch = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(i);
			if (stationSearch.getInt("station_id") == stationId) {
				return stationSearch.getInt("num_docks_available");
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * Method compares all .csv station objects with currently active ones (on Citibike RestAPI) and removes inactive stations
	 *
	 * @throws IOException
	 * @throws ParseException
	 */
	public void removeInactive() throws IOException, ParseException {
		URL url = new URL("https://gbfs.citibikenyc.com/gbfs/en/station_status.json");
		Scanner scan = new Scanner(url.openStream());
		String str = new String();

		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();
		JSONObject obj = new JSONObject(str);

		// User can request which station to retrieve info from
		int totalnumStations = obj.getJSONObject("data").getJSONArray("stations").length();

		//ArrayList of Stations
		ArrayList<Integer> activeStations = new ArrayList<Integer>();
		
		for (int i = 0; i < totalnumStations; i++) {
			JSONObject stationSearch = obj.getJSONObject("data").getJSONArray("stations").getJSONObject(i);
			activeStations.add(stationSearch.getInt("station_id"));
		}
	
		//Temporary array to iterate through
		ArrayList<Station> temp = stations;	
		
		//for every element in temp array
		for (int i = 0; i < temp.size() ; i++) {
			int tempId = temp.get(i).getStationId();
			
			Boolean isMatch = false;
			//for every element in JSON list
			for (int j = 0; j < activeStations.size(); j++){
				//if temp station ID matches JSON list
				if (tempId == activeStations.get(j)) {
					isMatch = true;
				}
			}
			
			//if not found in list, remove object
			if (isMatch == false) {
				//System.out.println("inactive, must remove: " + tempId + " at index " + i);
				stations.remove(i);
			}
		}
	
		
	}

}
