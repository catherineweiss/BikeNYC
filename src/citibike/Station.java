package citibike;

/**
 * This class represents a Station object, with a station ID, station name,
 * latitude, and longitude.
 * 
 * @author Fred Chang
 *
 */
public class Station {

	// define instance variables
	private int stationId;
	private String stationName;
	private double stationLat;
	private double stationLong;

	/**
	 * 
	 * Constructs Station object that takes in:
	 * 
	 * @param stationId
	 * @param stationName
	 * @param stationLat
	 * @param stationLong
	 */
	Station(int stationId, String stationName, double stationLat, double stationLong) {
		this.stationId = stationId;
		this.stationName = stationName;
		this.stationLat = stationLat;
		this.stationLong = stationLong;
	}

	// GETTERS

	public int getStationId() {
		return stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public double getStationLat() {
		return stationLat;
	}

	public double getStationLong() {
		return stationLong;
	}

}
