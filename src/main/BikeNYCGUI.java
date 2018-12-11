package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import citibike.Analyzer;
import citibike.Station;
import foursquare.APICaller;
import foursquare.FourSquareLocationParser;
import foursquare.FourSquareURLCreator;
import google.GeocodingParser;
import google.GoogleURLCreator;
import shared.Location;
import util.APIKeys;

/**
 * GUI Methods for BikeNYC, a geolocation based recommendation application for
 * Manhattan
 * 
 * @author Catherine Weiss and Fred Chang
 *
 */
public class BikeNYCGUI extends JFrame {

	// Constants:
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 1200;
	private static final String locationDefault = "New+York,NY";

	// Instance Variables:

	// locations:
	private double userLat; // user start location (latitude)
	private double userLong; // user start location (longitude)
	private double stationLat; // closest station location (latitude)
	private double stationLong; // closest station location (longitude)
	private ArrayList<Location> pointsOfInterest; // points of interest as Location objects
	private String startLocationAsString;
	private String closestBikeLocationAsString;
	private String placesOfInterestAsString;
	private String statusMessage; // displays message if user input causes an error

	// panels:
	private JPanel topPanel; // border layout
	private JPanel middlePanel; // border layout
	private JPanel bottomPanel; // border layout
	private JPanel mainPanel; // holds top, middle, bottom panels. Border layout

	// for inputPanel (flow layout); position North on topPanel.
	private JLabel inputRequestLabel;
	private JTextField startAddressTextField;
	private JButton goButton;
	private JPanel inputPanel;

	// for map with starting location; position Center on topPanel
	private URL url;
	private BufferedImage img;
	private ImageIcon icon;
	private JLabel mapStartLocLabel;

	// for StartLocPanel (flow layout); position South on topPanel
	private JLabel startAddressLabel;
	private JLabel formatAddressfromGoogleLabel;
	private JPanel startLocPanel;

	// for BikeLocPanel (flow layout); position North on middlePanel
	private JLabel closestStationLabel;
	private JLabel stationNameFromAPILabel;
	private JPanel bikeLocPanel;

	// for DistFromUserPanel (flow layout); position Center on middlePanle
	private JLabel distFromUserLabel;
	private JLabel actualDistFromUserFromAPILabel;
	private JLabel milesFromUserLabel;
	private JPanel distFromUserPanel;

	// for NumBikesPanel (flow layout); position South on middlePanel
	private JLabel bikesAvailLabel;
	private JLabel numBikesAvailLabel;
	private JPanel numBikesPanel;

	// for NumSpacesPanel (flow layout); position North on bottomPanel
	private JLabel spacesAvailLabel;
	private JLabel numSpacesAvailLabel;
	private JPanel numSpacesPanel;

	// position Center on bottomPanel
	private JLabel placesInterestLabel;

	// for placesInterestPanel (flow layout); position South on bottomPanel
	private JTextArea placesInterestTextArea;
	private JScrollPane scrollPane;
	private JPanel placesInterestPanel;

	// constructor with helper methods
	public BikeNYCGUI() {
		createGoButton();
		createComponents();
	}

	/**
	 * Creates static map with specified parameters
	 * 
	 * @param location     is String address for center of map
	 * @param mapLabelName is JLabel where map will be displayed
	 * @param mapZoomNum   is an integer zoom level from 1 (world view) to 20
	 *                     (building view)
	 */
	private void getMap(String location, JLabel mapLabelName, int mapZoomNum) {

		try {
			String center = "center=";
			String zoom = "&zoom=";
			int zoomNum = mapZoomNum;
			String size = "&size=400x400";
			String key = "&key=" + APIKeys.GOOGLE_API_KEY;

			String queryParams = center + location + zoom + zoomNum + size + key;
			String mapsURL = "https://maps.googleapis.com/maps/api/staticmap?" + queryParams;
			System.out.println(mapsURL);
			url = new URL(mapsURL);
			try {
				img = ImageIO.read(url);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}			
			icon = new ImageIcon(img);
			mapLabelName.setIcon(icon);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Takes an array of Location objects and returns a String of
	 * latitudes-longitude pairs (separated by a comma), with a pipe separating the
	 * pairs.
	 * 
	 * @param pointsOfInterest
	 * @return String of latitudes-longitude pairs
	 */
	public String placesOfInterestAsStringBuilder(ArrayList<Location> pointsOfInterest) {

		String placesOfInterestAsString = "";
		for (Location l : pointsOfInterest) {
			placesOfInterestAsString = placesOfInterestAsString + l.getLatLongString() + "|";
		}
		// removes final "|"
		String substring = placesOfInterestAsString.substring(0, placesOfInterestAsString.length() - 1);
		return substring;
	}

	/**
	 * Creates static map with specified parameters
	 * 
	 * @param location               is String address for center of map
	 * @param mapLabelName           is JLabel where map will be displayed
	 * @param mapZoomNum             is an integer zoom level from 1 (world view) to
	 *                               20 (building view)
	 * @param startLocLatLng         is String of latitude and longitude of user
	 *                               specified location
	 * @param bikeStationLatLng      is String of latitude and longitude of closest
	 *                               bike station
	 * @param placesOfInterestLatLng is String of latitude-longitude pairs of places
	 *                               of interest
	 */
	private void getMap(String location, JLabel mapLabelName, int mapZoomNum, String startLocLatLng,
			String bikeStationLatLng, String placesOfInterestLatLng) {

		try {

			String center = "center=";
			String zoom = "&zoom";
			int zoomNum = 12;
			String size = "&size=400x400";
			String markerStart = "&markers=size:mid|color:green|label:S|" + startLocationAsString;
			String markerBikeStation = "&markers=size:mid|color:blue|label:B|" + closestBikeLocationAsString;
			String markerPlacesOfInterest = "&markers=size:mid|color:red|" + placesOfInterestAsString;
			String markers = markerStart + markerBikeStation + markerPlacesOfInterest;
			String key = "&key=" + APIKeys.GOOGLE_API_KEY;

			String queryParams = center + location + zoom + zoomNum + size + markers + key;
			String mapsURL = "https://maps.googleapis.com/maps/api/staticmap?" + queryParams;
//			mapsURL=URLEncoder.encode(mapsURL, "UTF-8");
			System.out.println(mapsURL);
			url = new URL(mapsURL);
			try {
				img = ImageIO.read(url);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			icon = new ImageIcon(img);
			mapLabelName.setIcon(icon);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Creates GoButton and defines actions that happen as a result of user clicking
	 * the goButton: -Google Geocoding API, Citibike API, and SquareSpace API are
	 * accessed. -New map is created. -Data is displayed on interface.
	 */
	private void createGoButton() {
		goButton = new JButton("Go");
		goButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Retrieve user input
				String startLocation = startAddressTextField.getText();

				// If text field is empty, ask user to enter address again and clears data
				// fields
				if (startLocation.equals("")) {
					inputRequestLabel.setText("Enter starting location");
					inputRequestLabel.setForeground(Color.RED);
					formatAddressfromGoogleLabel.setText("");
					stationNameFromAPILabel.setText("");
					actualDistFromUserFromAPILabel.setText("");
					numBikesAvailLabel.setText("");
					numSpacesAvailLabel.setText("");
					placesInterestTextArea.setText("");

				} else {

					inputRequestLabel.setText("Enter starting location");
					inputRequestLabel.setForeground(Color.BLACK);
					startAddressLabel.setText("Starting Address:  ");

					// Access Geocoding API
					GoogleURLCreator gc = new GoogleURLCreator();
					String googleURL = gc.createURL(startLocation);
					APICaller ac = new APICaller();
					String gResponse = ac.callAPI(googleURL);
					GeocodingParser gp = new GeocodingParser();
					try {
						gp.parseGeocodingAPIResponse(gResponse);
					} catch (IllegalArgumentException | IOException e2) {
						e2.printStackTrace();
					}
					
					userLat = gp.getOriginLocation().getLatitude();
					userLong = gp.getOriginLocation().getLongitude();
					startLocationAsString = gp.getOriginLocation().getLatLongString();

					// fills Starting Address
					String formattedAddress = gp.getOriginLocation().getAddress();
					formatAddressfromGoogleLabel.setText(formattedAddress);

					// Check if Starting Address is outside of Manhattan
					// If it is, ask user to enter address again

					if (!(formattedAddress.contains("New York, NY"))) {

						statusMessage = "Enter an address in Manhattan:";
						inputRequestLabel.setText(statusMessage);
						inputRequestLabel.setForeground(Color.RED);
						startAddressLabel.setText("Address Entered:  ");
						stationNameFromAPILabel.setText("");
						actualDistFromUserFromAPILabel.setText("");
						numBikesAvailLabel.setText("");
						numSpacesAvailLabel.setText("");
						placesInterestTextArea.setText("");
						pack();
					}

					else {
						statusMessage = "We found you!";
						inputRequestLabel.setText(statusMessage);

						// *** CITIBIKE FUNCTIONALITY BEGINS HERE ***

						// Constructors
						ArrayList<Station> stations = new ArrayList<>();
						Analyzer analyzer = new Analyzer(stations);

						// Reads in stations csv file
						analyzer.loadStations();

						// Gets closest stationID
						int closestStationId = analyzer.analyzeCloseProximity(userLat, userLong);

						// Gets closest stationName Set text for JLabel stationNameFromAPILabel
						String stationName = analyzer.stationIdtoName(closestStationId);
						stationNameFromAPILabel.setText(stationName);

						// Gets distance from closest station to user
						double distanceFromUser = analyzer.analyzeCloseProximityDistance(userLat, userLong);
						actualDistFromUserFromAPILabel.setText(Double.toString(distanceFromUser));

						// Gets number bikes avail. Set text for JLabel numBikesAvailLabel
						try {
							int citiAPINumBikes = analyzer.getCitiAPINumBikes(closestStationId);
							numBikesAvailLabel.setText(Integer.toString(citiAPINumBikes));
						} catch (IOException | ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						// Gets number spaces available. Set text for JLabel numSpacesAvailLabel
						try {
							int citiAPINumSpaces = analyzer.getCitiAPINumSpaces(closestStationId);
							numSpacesAvailLabel.setText(Integer.toString(citiAPINumSpaces));
						} catch (IOException | ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						// Create closest bike location object
						stationLat = analyzer.getClosestStationLat(closestStationId);
						stationLong = analyzer.getClosestStationLong(closestStationId);
						Location closestBikeLocation = new Location(stationName, stationLat, stationLong, stationName);
						closestBikeLocationAsString = closestBikeLocation.getLatLongString();

						// *** FOURSQUARE SPACES API FUNCTIONALITY BEGINS HERE ***

						String foursquareurl = FourSquareURLCreator.createURL(stationLat, stationLong, "landmark");
						FourSquareLocationParser parser = new FourSquareLocationParser(
								APICaller.callAPI(foursquareurl));
						pointsOfInterest = parser.getLocations();
						String poi = "";
						for (Location l : pointsOfInterest) {
							poi += l.getName() + "\n";
						}
						placesOfInterestAsString = placesOfInterestAsStringBuilder(pointsOfInterest);
						placesInterestTextArea.setLineWrap(true);
						placesInterestTextArea.setText(poi);

						// Update map with markers for start location, bike station, and places of
						// interest
						getMap(closestBikeLocationAsString, mapStartLocLabel, 15, startLocationAsString,
								closestBikeLocationAsString, placesOfInterestAsString);

						pack();
					}
				}

			}

		});
	}

	/**
	 * Components of user interface are created. Within JFrame, GUI consists of a
	 * Top Panel, Middle Panel and Bottom Panel. Each of these panels are made up of
	 * three sub-panels which are named based on the components that they hold.
	 */
	private void createComponents() {

		// Create topPanel: inputPanel; map(defaultLocation); startLocPanel

		// inputPanel:
		inputRequestLabel = new JLabel("Enter starting location");
		startAddressTextField = new JTextField(30);
		inputPanel = new JPanel();
		inputPanel.add(inputRequestLabel);
		inputPanel.add(startAddressTextField);
		inputPanel.add(goButton);

		// map
		mapStartLocLabel = new JLabel();
		mapStartLocLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getMap(locationDefault, mapStartLocLabel, 12);

		// startLocPanel:
		startAddressLabel = new JLabel("Starting Address:  ");
		formatAddressfromGoogleLabel = new JLabel("");
		startLocPanel = new JPanel();
		startLocPanel.add(startAddressLabel);
		startLocPanel.add(formatAddressfromGoogleLabel);

		// assemble topPanel
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(inputPanel, BorderLayout.NORTH);
		topPanel.add(mapStartLocLabel, BorderLayout.CENTER);
		topPanel.add(startLocPanel, BorderLayout.SOUTH);

		// Create Middle Panel: BikeLocPanel; NumBikesPanel; DistFromUserPanel

		// BikeLocPanel
		closestStationLabel = new JLabel("Closest Citibike Station:  ");
		stationNameFromAPILabel = new JLabel();//
		bikeLocPanel = new JPanel();
		bikeLocPanel.add(closestStationLabel);
		bikeLocPanel.add(stationNameFromAPILabel);

		// DistanceFromUserPanel
		distFromUserLabel = new JLabel("You are  ");
		actualDistFromUserFromAPILabel = new JLabel();
		milesFromUserLabel = new JLabel(" miles from the closest station");
		distFromUserPanel = new JPanel();
		distFromUserPanel.add(distFromUserLabel);
		distFromUserPanel.add(actualDistFromUserFromAPILabel);
		distFromUserPanel.add(milesFromUserLabel);

		// NumBikesPanel
		bikesAvailLabel = new JLabel("Number of Bikes Available:  ");
		numBikesAvailLabel = new JLabel(); //
		numBikesPanel = new JPanel();
		numBikesPanel.add(bikesAvailLabel);
		numBikesPanel.add(numBikesAvailLabel);

		// assemble middlePanel
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(bikeLocPanel, BorderLayout.NORTH);
		middlePanel.add(distFromUserPanel, BorderLayout.CENTER);
		middlePanel.add(numBikesPanel, BorderLayout.SOUTH);

		// Create Bottom Panel: NumSpacesPanel; PlacesOfInterestLabel;
		// PlacesOfInterestTextArea

		// NumSpacesPanel
		spacesAvailLabel = new JLabel("Number of Spaces Available:  ");
		numSpacesAvailLabel = new JLabel();
		numSpacesPanel = new JPanel();
		numSpacesPanel.add(spacesAvailLabel);
		numSpacesPanel.add(numSpacesAvailLabel);

		// Center on bottomPanel
		placesInterestLabel = new JLabel("Nearby Landmarks to Tour: ");
		placesInterestLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// South on bottomPanel
		placesInterestTextArea = new JTextArea(7, 30); // set rows=7 and columns=30
		scrollPane = new JScrollPane(placesInterestTextArea);
		placesInterestPanel = new JPanel();
		placesInterestPanel.add(scrollPane);

		// assemble bottomPanel
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(numSpacesPanel, BorderLayout.NORTH);
		bottomPanel.add(placesInterestLabel, BorderLayout.CENTER);
		bottomPanel.add(placesInterestPanel, BorderLayout.SOUTH);

		// add Top, Middle, Bottom Panels to Main Panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middlePanel, BorderLayout.CENTER);
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);
		JScrollPane scrollPane2 = new JScrollPane(mainPanel); // use ScrollPane to improve window formatting of GUI

		// add Main Panel to Frame
//		add(mainPanel);

		// add scrollPane2 to Frame
		add(scrollPane2);

		setTitle("Customize Your Active Tour of Manhattan");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setVisible(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getRootPane().setDefaultButton(goButton); // wires ENTER key

	}
}
