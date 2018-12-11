package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import citibike.Analyzer;
import citibike.Station;

import java.util.ArrayList;

public class CitiBikeTest {

	ArrayList<Station> stations;
	Analyzer analyzer;
	
	@BeforeEach
	public void setup() {
		stations = new ArrayList<>();	
		analyzer = new Analyzer(stations);
		analyzer.loadStations();
	}
	
	
	//Test Purpose: Check if station ID matches returned station name
	@Test
	public void stationIdMatchName() {
		
		int stationId = 3318;
		String expectedName = "2 Ave & E 96 St";	
		String outputName = analyzer.stationIdtoName(stationId);		
		assertEquals(expectedName, outputName);
		
	}
	
	
	//Test Purpose: Check if station name matches returned station ID
	@Test
	public void stationNameMatchId() {
		
		String stationName = "Broadway & W 56 St";
		int expectedId = 468;	
		int outputId = analyzer.stationNametoId(stationName);		
		assertEquals(expectedId, outputId);
		
	}
	
	//Test Purpose: Check if distance calculation between user and station is correct
	@Test
	public void stationToUserDistanceTest() {
		
		//~Stytown
		double userLat = 40.729701;
		double userLong = -73.978671;
		
		double distance = analyzer.analyzeCloseProximityDistance(userLat, userLong);
		double expectedDistance = 0.1; //between 0.1 and 0.2
		
		assertEquals(expectedDistance, distance);
		
	}
	
	//Test Purpose: Check if distance calculation between user and station is correct
	@Test
	public void stationToUserDistanceTest2() {
		
		//~HudsonYards
		double userLat = 40.755864;
		double userLong = -74.006066;
		
		double distance = analyzer.analyzeCloseProximityDistance(userLat, userLong);
		double expectedDistance = 0.21; //between 0.2 and 0.3
		
		assertEquals(expectedDistance, distance);
		
	}
	
	
	//Test Purpose: Check to see if returned "closest CitiBike Station" is correct
	@Test
	public void proximityTest1LowerManhattan() {
		
		//Test GPS Coordinates
		double userLat = 40.704304;
		double userLong = -74.014290;
		
		//Expected
		String expectedName = "Broadway & Battery Pl";	
		int expectedStationId = analyzer.stationNametoId(expectedName);
		
		//Test Output
		int stationId = analyzer.analyzeCloseProximity(userLat, userLong);
		String outputName = analyzer.stationIdtoName(stationId);		
		assertEquals(expectedStationId, expectedStationId);
		assertEquals(expectedName, outputName);
		
	}
	
	//Test Purpose: Check to see if returned "closest CitiBike Station" is correct
	@Test
	public void proximityTest2EastVillage() {
		
		//Test GPS Coordinates
		double userLat = 40.730296;
		double userLong = -73.974475;
		
		//Expected
		String expectedName = "E 20 St & FDR Drive";	
		int expectedStationId = analyzer.stationNametoId(expectedName);
		
		//Test Output
		int stationId = analyzer.analyzeCloseProximity(userLat, userLong);
		String outputName = analyzer.stationIdtoName(stationId);		
		assertEquals(expectedStationId, expectedStationId);
		assertEquals(expectedName, outputName);
		
	}
	
	//Test Purpose: Check to see if returned "closest CitiBike Station" is correct
	@Test
	public void proximityTest3UpperEastSide() {
		
		//Test GPS Coordinates
		double userLat = 40.782486;
		double userLong = -73.946949;
		
		//Expected
		String expectedName = "1 Ave & E 94 St";	
		int expectedStationId = analyzer.stationNametoId(expectedName);
		
		//Test Output
		int stationId = analyzer.analyzeCloseProximity(userLat, userLong);
		String outputName = analyzer.stationIdtoName(stationId);		
		assertEquals(expectedStationId, expectedStationId);
		assertEquals(expectedName, outputName);
		
	}
	
	//Test Purpose: Check to see if returned "closest CitiBike Station" is correct (this tests a valet station https://www.citibikenyc.com/valet)
	@Test
	public void proximityTest4Midtown() {
		
		//Test GPS Coordinates
		double userLat = 40.759939;
		double userLong = -73.980560;
		
		//Expected
		String expectedName = "W 52 St & 6 Ave";	
		int expectedStationId = analyzer.stationNametoId(expectedName);
		
		//Test Output
		int stationId = analyzer.analyzeCloseProximity(userLat, userLong);
		String outputName = analyzer.stationIdtoName(stationId);		
		assertEquals(expectedStationId, expectedStationId);
		assertEquals(expectedName, outputName);
		
	}
	
	
	
	
	
	
}
