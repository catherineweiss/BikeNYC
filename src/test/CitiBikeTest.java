package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import citibike.Analyzer;
import citibike.Station;
import citibike.StationReader;

public class CitiBikeTest {

	Analyzer analyzer;
	ArrayList<Station> stations = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		analyzer = new Analyzer(stations);
	}
	
	
	
	
}
