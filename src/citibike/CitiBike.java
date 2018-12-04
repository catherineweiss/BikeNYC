package citibike;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

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
		analyzer.getStationRealTime(closestStationId);	

	}
}