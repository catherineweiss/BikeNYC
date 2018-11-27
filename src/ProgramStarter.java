
public class ProgramStarter {

	public static void main(String[] args) {
		
		String searchAddress = "80 Henry Street, Brooklyn";
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
