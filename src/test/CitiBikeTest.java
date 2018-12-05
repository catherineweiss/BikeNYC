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
	
	//Test Purpose: Check if station Id matches returned station name
	@Test
	public void stationIdMatchName() {
		
		int stationId = 3318;
		String expectedName = "2 Ave & E 96 St";	
		String outputName = analyzer.stationIdtoName(stationId);		
		assertEquals(expectedName, outputName);
		
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
