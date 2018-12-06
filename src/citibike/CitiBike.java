package citibike;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import main.TourNY;

/**
 * This is the temporary main method that takes a pair of GPS coordinates, returns closest Citibike Station and pulls down real-time station info using Citibike API
 * @author Fred Chang
 *
 */
class CitiBike{

	public static void main(String[] args) throws IOException, ParseException, FileNotFoundException{
		
		//Constructors
		ArrayList<Station> stations = new ArrayList<>();	
		Analyzer analyzer = new Analyzer(stations);
		//TourNY tourNY = new TourNY();
		
		//Enter GPS Coordinates
		double userLat = 40.782486;
		double userLong = -73.946949;
		
		//Reads in stations csv file
		analyzer.loadStations();
	
		//Finds closest station
		int closestStationId = analyzer.analyzeCloseProximity(userLat, userLong);
		
		System.out.println("User Location: " + userLat + "," + userLong);
		System.out.println("Closest Station ID: " + closestStationId);
		System.out.println("Closest Station: " + analyzer.stationIdtoName(closestStationId));
		System.out.println("Distance from user: " + analyzer.analyzeCloseProximityDistance(userLat, userLong) + " mi");
		System.out.println("");

		
		//Gets real-time data from CitiBike API
		analyzer.getCitiAPIAll(closestStationId);
		System.out.println("");
		
		//Test individual components
		String stationName = analyzer.stationIdtoName(closestStationId);
		System.out.println("API Station Name: " + stationName);
		
		double stationLat = analyzer.getClosestStationLat(closestStationId);
		double stationLong = analyzer.getClosestStationLong(closestStationId);
		System.out.println("Coordinates (Lat, Long): " + stationLat + ", " + stationLong);
		
		double distanceFromUser = analyzer.analyzeCloseProximityDistance(userLat, userLong);
		System.out.println("Distance from user: " + distanceFromUser);
		
		int citiAPINumBikes = analyzer.getCitiAPINumBikes(closestStationId);
		System.out.println("API Number of Bikes Available: " + citiAPINumBikes);
		
		int citiAPINumSpaces = analyzer.getCitiAPINumSpaces(closestStationId);
		System.out.println("API Number of Spaces Available: " + citiAPINumSpaces);
		
		

	}
}