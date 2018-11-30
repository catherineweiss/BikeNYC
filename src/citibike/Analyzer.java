package citibike;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Analyzer {

	ArrayList<Station> stations;


	Analyzer(ArrayList<Station>stations){
		this.stations = stations;
	}
	
	public String stationIdtoName(int stationId) {
		String stationName = null;
		
		for (Station s : stations) {
			if (s.getStationId() == stationId) {
				stationName = s.getStationName();
			}
		}
		
		
		return stationName;
	}


	public int analyzeCloseProximity(double userLat, double userLong){	
		int closestStation = 9999;
		double closestDistance = 99999;

		//For all trips
		for (Station s: stations) {
			int station = s.getStationId();

			double stationLat = s.getStationLat();
			double stationLong = s.getStationLong();

			double diffStartLat = Math.abs(userLat - stationLat);

			double diffStartLong = Math.abs(userLong - stationLong);

			double two = 2.000000;
			double difference = (diffStartLat + diffStartLong) / two;
			
			if (difference < closestDistance) {
				closestDistance = difference;
				closestStation = s.getStationId();
			}
	
		}	
	


	return closestStation;
}

}
