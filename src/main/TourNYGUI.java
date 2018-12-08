package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
	 * GUI Methods for TourNY, a geo-location based recommendation application
	 * for Manhattan 
	 * @author Catherine Weiss, Fred Chang
	 *
	 */
	public class TourNYGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 1200;
	private static final String locationDefault = "New+York,NY";
	
	//Instance Variables:
	
	private double userLat; //user start location (latitude)
	private double userLong; //user start location (longitude)
	private double stationLat; //closest station location (latitude)
	private double stationLong; //closest station location (longitude)
    private ArrayList<Location> pointsOfInterest; //this is what Ruthie returns
    private String startLocationAsString;	
    private String closestBikeLocationAsString;
    private String placesOfInterestAsString;

	//panels:
	private JPanel topPanel; //border layout
	private JPanel middlePanel; //border layout
	private JPanel bottomPanel; //border layout
	private JPanel mainPanel; //holds top, middle, bottom panels. Border layout
	
	//for inputPanel (flow layout); position North on topPanel.
	private JLabel inputRequestLabel;
	private JTextField startAddressTextField;
	private JButton goButton;
	private JPanel inputPanel;
	
	//for map with starting location; Center on topPanel
	private URL url;
	private BufferedImage img;
	private ImageIcon icon;
	private JLabel mapStartLocLabel;
	
	//for StartLocPanel (flow layout); South on topPanel
	private JLabel startAddressLabel;
	private JLabel formatAddressfromGoogleLabel;
	private JPanel startLocPanel;
	
	//for BikeLocPanel (flow layout); North on middlePanel
	private JLabel closestStationLabel; 
	private JLabel stationNameFromAPILabel;
	private JPanel bikeLocPanel;
	
	//for DistFromUserPanel (flow layout); Center on middlePanle
	private JLabel distFromUserLabel;
	private JLabel actualDistFromUserFromAPILabel;
	private JLabel milesFromUserLabel;
	private JPanel distFromUserPanel;	
	
	//for NumBikesPanel (flow layout); South on middlePanel
	private JLabel bikesAvailLabel;
	private JLabel numBikesAvailLabel;
	private JPanel numBikesPanel;
	
	//for NumSpacesPanel (flow layout); North on bottomPanel
	private JLabel spacesAvailLabel;
	private JLabel numSpacesAvailLabel;
	private JPanel numSpacesPanel;


	//Center on bottomPanel
	private JLabel placesInterestLabel;

	//for placesInterestPanel (flow layout); South on bottomPanel
	private JTextArea placesInterestTextArea;
	private JPanel placesInterestPanel;
	
	
	//constructor with helper methods
	TourNYGUI(){		
		createGoButton();
		createPrimaryComponents();		
	}

	
	private void getMap (String location, JLabel mapLabelName, int mapZoomNum) {
		
		try {		
			String center = "center="; //TO DO: Change center of map to bike station location
			String zoom = "&zoom=";
			int zoomNum = mapZoomNum;
			String size = "&size=800x600";
			String key = "&key=" + APIKeys.GOOGLE_API_KEY;
			
			String queryParams = center + location + zoom + zoomNum + size + key;
			String mapsURL = "https://maps.googleapis.com/maps/api/staticmap?" + queryParams;		
			System.out.println(mapsURL);
			url = new URL(mapsURL);					
			img = ImageIO.read(url);
			icon = new ImageIcon(img);
			mapLabelName.setIcon(icon); 
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	private String placesOfInterestAsStringBuilder(ArrayList<Location> pointsOfInterest) {
		
		String placesOfInterestAsString = "";
		for (Location l : pointsOfInterest) {
			placesOfInterestAsString = placesOfInterestAsString + l.getLatLongString() + "|";
		}
		String substring = placesOfInterestAsString.substring(0, placesOfInterestAsString.length()-1); //removes final |		
		return substring;
	}
	
	
	private void getMap (String location, JLabel mapLabelName, int mapZoomNum,
						String startLocLatLng, String bikeStationLatLng, String placesOfInterestLatLng) {
		
		try {	
			
			String center = "center=";
			String zoom = "&zoom";
			int zoomNum = 12;
			String size = "&size=800x600";
			
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
			img = ImageIO.read(url);
			icon = new ImageIcon(img);
			mapLabelName.setIcon(icon); 
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
		
	
	private void createGoButton(){
		goButton = new JButton("Go");
		goButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Retrieve user input
				String startLocation = startAddressTextField.getText();
				
				//Access Geocoding API
				GoogleURLCreator gc = new GoogleURLCreator();
				String googleURL = gc.createURL(startLocation);						
				APICaller ac = new APICaller();
				String gResponse = ac.callAPI(googleURL);				
				GeocodingParser gp = new GeocodingParser();
				gp.parseGeocodingAPIResponse(gResponse);
			    userLat = gp.getOriginLocation().getLatitude();
				userLong = gp.getOriginLocation().getLongitude();
				startLocationAsString = gp.getOriginLocation().getLatLongString();
								
				//fills Starting Address
				formatAddressfromGoogleLabel.setText(gp.getOriginLocation().getAddress());
				
				
				
				//*** CITIBIKE FUNCTIONALITY BEGINS HERE ***
				
				//Constructors
				ArrayList<Station>stations = new ArrayList<>();
				Analyzer analyzer = new Analyzer(stations);
				
				//Reads in stations csv file
				analyzer.loadStations();
				
				//Gets closest stationID
				int closestStationId = analyzer.analyzeCloseProximity(userLat, userLong);
				
				//Gets closest stationName Set text for JLabel stationNameFromAPILabel
				String stationName = analyzer.stationIdtoName(closestStationId);
				stationNameFromAPILabel.setText(stationName);
				
				//Gets distance from closest station to user
				double distanceFromUser = analyzer.analyzeCloseProximityDistance(userLat, userLong);
				actualDistFromUserFromAPILabel.setText(Double.toString(distanceFromUser));
				
				//Gets number bikes avail. Set text for JLabel numBikesAvailLabel
				try {
					int citiAPINumBikes = analyzer.getCitiAPINumBikes(closestStationId);
					numBikesAvailLabel.setText(Integer.toString(citiAPINumBikes));
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//Gets number spaces available. Set text for JLabel numSpacesAvailLabel
				try {
					int citiAPINumSpaces = analyzer.getCitiAPINumSpaces(closestStationId);
					numSpacesAvailLabel.setText(Integer.toString(citiAPINumSpaces));
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//Create closest bike location object
				stationLat = analyzer.getClosestStationLat(closestStationId);
				stationLong = analyzer.getClosestStationLong(closestStationId);
				Location closestBikeLocation = new Location(stationName, stationLat, stationLong, stationName);
				closestBikeLocationAsString = closestBikeLocation.getLatLongString();
				

				//*** 	SQUARE SPACE FUNCTIONALITY BEGINS HERE ***
				
				String foursquareurl = FourSquareURLCreator.createURL(stationLat, stationLong, "coffee");
				FourSquareLocationParser parser = new FourSquareLocationParser(APICaller.callAPI(foursquareurl));
				pointsOfInterest = parser.getLocations();
				String poi = "";
				for(Location l: pointsOfInterest) {
					poi += l.getName() + "\n";
				}
				placesOfInterestAsString = poi; 
				placesInterestTextArea.setLineWrap(true);
				placesInterestTextArea.setText(placesOfInterestAsString);
				
				
				//Update map with markers for start location, bike station, and places of interest
				getMap(closestBikeLocationAsString, mapStartLocLabel, 15,
						startLocationAsString, closestBikeLocationAsString, placesOfInterestAsString);		
			}
		});
	}
	
	private void createPrimaryComponents() {
		
		//Create topPanel: inputPanel; map(defaultLocation); startLocPanel
		
		//inputPanel:
		inputRequestLabel = new JLabel("Enter starting location");
		startAddressTextField = new JTextField(30);
		inputPanel = new JPanel();
		inputPanel.add(inputRequestLabel);
		inputPanel.add(startAddressTextField);
		inputPanel.add(goButton);

		//map
		mapStartLocLabel = new JLabel();
        mapStartLocLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getMap(locationDefault, mapStartLocLabel, 12);

		//assemble topPanel
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(inputPanel, BorderLayout.NORTH);
		topPanel.add(mapStartLocLabel, BorderLayout.CENTER);
		
		//update startLocPanel and add it to topPanel:
		startAddressLabel = new JLabel("Starting Address:  ");
		formatAddressfromGoogleLabel = new JLabel("");
		startLocPanel = new JPanel();
		startLocPanel.add(startAddressLabel);
		startLocPanel.add(formatAddressfromGoogleLabel);
		topPanel.add(startLocPanel, BorderLayout.SOUTH);
		
		//Create Middle Panel: BikeLocPanel; NumBikesPanel; NumSpacesPanel
		
		//BikeLocPanel
		closestStationLabel = new JLabel("Closest Citibike Station:  ");  
		stationNameFromAPILabel = new JLabel();//TO DO: Insert Station Name as String
		bikeLocPanel = new JPanel();
		bikeLocPanel.add(closestStationLabel);
		bikeLocPanel.add(stationNameFromAPILabel);

		distFromUserLabel = new JLabel("You are  ");
		actualDistFromUserFromAPILabel = new JLabel(); //TO DO: Insert distance as a String		
		milesFromUserLabel = new JLabel(" miles from the closest station");
		distFromUserPanel = new JPanel();
		distFromUserPanel.add(distFromUserLabel);
		distFromUserPanel.add(actualDistFromUserFromAPILabel);
		distFromUserPanel.add(milesFromUserLabel);
				
		//NumBikesPanel
		bikesAvailLabel = new JLabel("Number of Bikes Available:  ");
		numBikesAvailLabel = new JLabel(); //TO DO: Insert # bikes available
		numBikesPanel = new JPanel();
		numBikesPanel.add(bikesAvailLabel);
		numBikesPanel.add(numBikesAvailLabel);
		
		//NumSpacesPanel
		spacesAvailLabel = new JLabel("Number of Spaces Available:  ");
		numSpacesAvailLabel = new JLabel();
		numSpacesPanel = new JPanel();
		numSpacesPanel.add(spacesAvailLabel);
		numSpacesPanel.add(numSpacesAvailLabel);
				
		//assemble middlePanel
		middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(bikeLocPanel,BorderLayout.NORTH);
		middlePanel.add(distFromUserPanel,BorderLayout.CENTER);		
		middlePanel.add(numBikesPanel,BorderLayout.SOUTH);
		
		
		//Create Bottom Panel: PlacesOfInterestLabel; PlacesOfInterestTextArea

		placesInterestLabel = new JLabel("Places of Interest within 1/4 mile: "); //TO DO: check that this is desired text
        placesInterestLabel.setHorizontalAlignment(SwingConstants.CENTER);

		//South on bottomPanel
		placesInterestTextArea = new JTextArea(); //TO DO: insert string/data into Text Area
		placesInterestPanel = new JPanel();
		placesInterestPanel.add(placesInterestTextArea);
		
		//assemble bottomPanel
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(numSpacesPanel,BorderLayout.NORTH);
		bottomPanel.add(placesInterestLabel,BorderLayout.CENTER);
		bottomPanel.add(placesInterestTextArea,BorderLayout.SOUTH);
		
				
		//add Top, Middle, Bottom Panels to Main Panel
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(topPanel,BorderLayout.NORTH);
		mainPanel.add(middlePanel,BorderLayout.CENTER);
		mainPanel.add(bottomPanel,BorderLayout.SOUTH);
				
		//add Main Panel to Frame
		add(mainPanel);
		
		setTitle("Customize Your Active Tour of Manhattan");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setVisible(true);
//		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
