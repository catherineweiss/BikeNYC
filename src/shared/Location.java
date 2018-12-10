package shared;

/**
 * This class represents a location, with a name, latitude, longitude, and
 * street address.
 * 
 * @author Catherine Weiss
 *
 */
public class Location {

	private String name;
	private double latitude;
	private double longitude;
	private String address;

	public Location(String name, double latitude, double longitude, String address) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}

	/**
	 * 
	 * @return name of location
	 */
	public String getName() {
		return name;
	}

	/**
	 * a method to get the latitude and longitude concatenated and separated by a
	 * comma
	 * 
	 * @return comma-separated coordinates
	 */
	public String getLatLongString() {
		return latitude + "," + longitude;
	}

	/**
	 * 
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * 
	 * @return longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * 
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 
	 * @param name of location
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @param latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 
	 * @param longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

}
