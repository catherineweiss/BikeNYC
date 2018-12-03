package main;

public class ProgramStarter {

	public static void main(String[] args) {
		
//		String searchAddress = "20 W 34th St, New York, NY 10001";
		String searchAddress = "11 Wall Street, New York";
		
		
		GoogleURLCreator gc = new GoogleURLCreator();
		String googleURL = gc.createURL(searchAddress);
				
		APICaller ac = new APICaller();
		String gResponse = ac.callAPI(googleURL);
		
		GeocodingParser gp = new GeocodingParser();
		gp.parseGeocodingAPIResponse(gResponse);
		
		System.out.println("Output from Geocoding API is an instance of Location class:");
		System.out.println("   Name: "+ gp.getOriginLocation().getName());
		System.out.println("   Lat: "+ gp.getOriginLocation().getLatitude());
		System.out.println("   Long: "+ gp.getOriginLocation().getLongitude());
		System.out.println("   Address: "+ gp.getOriginLocation().getAddress());		
				
	}
}
