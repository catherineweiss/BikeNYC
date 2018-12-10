package citibike;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads and parses a StationFile (csv format)
 * 
 * @author Fred Chang
 *
 */
public class StationReader {

	File stationFile;

	/**
	 * 
	 * @param fileName the stationFile csv file
	 */
	StationReader(String stationFile) {

		this.stationFile = new File(stationFile);

	}

	/**
	 * Reads and parses stationFile csv file Creates Station objects
	 * 
	 * @return ArrayList of Station objects
	 */
	public ArrayList<Station> readStationFile() throws FileNotFoundException {
		ArrayList<Station> stations = new ArrayList<>();

		try {
			Scanner scan = new Scanner(this.stationFile);
			// read in first line with column headers

			while (scan.hasNext()) {
				scan.nextLine();
				// read in each token of the file
				scan.useDelimiter(",|\n|\r");
				int stationId = scan.nextInt();
				// System.out.println("stationID = " + stationId);
				String stationName = scan.next();
				// System.out.println("statioName = " + stationName);
				double stationLat = scan.nextDouble();
				// System.out.println("stationLat = " + stationLat);
				double stationLong = scan.nextDouble();
				// System.out.println("stationLong = " + stationLong);

				Station s = new Station(stationId, stationName, stationLat, stationLong);
				stations.add(s);
			}
			scan.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stations;
	}

}
