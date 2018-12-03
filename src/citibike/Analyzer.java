package citibike;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * This class contains all methods used in CitiBike main method
 * @author fredchang
 *
 */
public class Analyzer {

	ArrayList<Station> stations;

	Analyzer(ArrayList<Station> stations) {
		this.stations = stations;
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

}
