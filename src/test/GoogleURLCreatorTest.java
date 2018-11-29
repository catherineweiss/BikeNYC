package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.GoogleURLCreator;
import main.GoogleAPIKey;

public class GoogleURLCreatorTest {
	
	GoogleURLCreator urlCreator;

	
	@BeforeEach
	public void setup() {
		urlCreator = new GoogleURLCreator();
	}

	//Test purpose: tests method with a normally formatted address	
	@Test
	public void createURLtest() {
		
		String testString = "20 W 34th St, New York";
		String predictedString = "https://maps.googleapis.com/maps/api/geocode/"
				+"json?address=20+W+34th+St,+New+York&region=new+york&country:US"
				+"&key=" + GoogleAPIKey.key;
		assertEquals(predictedString, urlCreator.createURL(testString));
	}
	
	//Test purpose: tests method with a poorly formatted address
	@Test
	public void createURLtest2() {
		
		String testString = "20W34St";
		String predictedString = "https://maps.googleapis.com/maps/api/geocode/"
				+"json?address=20W34St&region=new+york&country:US"
				+"&key=" + GoogleAPIKey.key;
		assertEquals(predictedString, urlCreator.createURL(testString));
	}
	

}


