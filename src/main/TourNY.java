package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLEncoder;
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
	/**
	 * GUI for TourNY, a geo-location based recommendation application
	 * for Manhattan 
	 * @author Catherine Weiss
	 *
	 */
	public class TourNY extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 1200;
	private static final String locationDefault = "New+York,NY";
	
	//Instance Variables:
	
	private double latStartLocation;
	private double lngStartLocation;
	private double stationLat;
	private double stationLong;
	private ArrayList<Location> pointsOfInterest; //this is what Ruthie returns
	
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
	TourNY(){		
		createGoButton();
		createPrimaryComponents();		
	}

	
	private void getDefaultMap (String location, JLabel mapLabelName, int mapZoomNum) {
		
		try {		
			String center = "center="; //TO DO: Change center of map to bike station location
			String zoom = "&zoom=";
			int zoomNum = mapZoomNum;
			String size = "&size=800x600&key=";
			String key = GoogleAPIKey.key;
			
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

/*	private void getMapWithTourData (String location, JLabel mapLabelName, int mapZoomNum,
						String startLocLatLng, String bikeStationLatLng, String placesOfInterestLatLng) {
		
		try {		// to do: center on bike station location
				// make bike station and places of interest as instance variables
				//create method "string builder" to take lats and longs into strings
			
			String center = "center=";
			String zoom = "&zoom";
			int zoomNum = 12;
			String size = "&size=800x600&key=";
			
			String markerStart = "&markers=size:mid|color:gray|startLocLatLng"; //TODO add |icon:person.png
			String markerBikeStation = "&markers=size:mid|color:blue|bikeStationLatLng"; //TODO add |icon.bike.png
			String markerPlacesOfInterest = "&markers=size:mid|color:green|placesOfInterestLatLng";
			
			String markers = markerStart + markerBikeStation + markerPlacesOfInterest;
			
			
			String key = GoogleAPIKey.key;
			
			String queryParams = center + location + zoom + zoomNum + size + markers + key;
			String mapsURL = "https://maps.googleapis.com/maps/api/staticmap?" + queryParams;		
			mapsURL=URLEncoder.encode(mapsURL, "UTF-8");
			System.out.println(mapsURL);
			url = new URL(mapsURL);					
			img = ImageIO.read(url);
			icon = new ImageIcon(img);
			mapLabelName.setIcon(icon); 
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
*/	
		
	
	private void createGoButton() {
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
				
				latStartLocation = gp.getOriginLocation().getLatitude();
				lngStartLocation = gp.getOriginLocation().getLongitude();
				String startLocationLatLng = gp.getOriginLocation().getLatLongString();
								
				//fills Starting Address
				formatAddressfromGoogleLabel.setText(gp.getOriginLocation().getAddress());
				
				
				
				//Access Citibike API
				 
				//Get name of closest Citibike location. Set text for JLabel stationNameFromAPILabel
				//   ex: stationNameFromAPILabel.setText( insert String here);
				
				//Get num bikes avail. Set text for JLabel numBikesAvailLabel
				//Get num spaces available. Set text for JLabel numSpacesAvailLabel
				
//				Location stationLoc = new Location (String name, Double lat, Double lng, String address);
				
				
				//Start SquareSpace API
				
				//Get places of interest. Store in JTextArea placesInterestTextArea.
				
				
				
				//Update map with starting location, bike station location,
				//and places of interest
				
				getDefaultMap(startLocationLatLng, mapStartLocLabel, 15);
					
				//TO DO: Add markers for starting location, bike station,
				//and places of interest to the map
			
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
		getDefaultMap(locationDefault, mapStartLocLabel, 12);

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
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
/*	public void createSecondaryComponents() {
		
//		//update startLocPanel and add it to topPanel:
//		startAddressLabel = new JLabel("Starting Address:  ");
//		formatAddressfromGoogleLabel = new JLabel("");
//		startLocPanel = new JPanel();
//		startLocPanel.add(startAddressLabel);
//		startLocPanel.add(formatAddressfromGoogleLabel);
		
		
		
//		topPanel.add(startLocPanel, BorderLayout.SOUTH);
		
//		//Create Middle Panel: BikeLocPanel; NumBikesPanel; NumSpacesPanel
//		
//		//BikeLocPanel
//		closestStationLabel = new JLabel("Closest Citibike Station:  ");  
//		stationNameFromAPILabel = new JLabel();//TO DO: Insert Station Name as String
//		bikeLocPanel = new JPanel();
//		bikeLocPanel.add(closestStationLabel);
//		bikeLocPanel.add(stationNameFromAPILabel);
//		
//		//NumBikesPanel
//		bikesAvailLabel = new JLabel("Number of Bikes Available:  ");
//		numBikesAvailLabel = new JLabel(); //TO DO: Insert # bikes available
//		numBikesPanel = new JPanel();
//		numBikesPanel.add(bikesAvailLabel);
//		
//		//NumSpacesPanel
//		spacesAvailLabel = new JLabel("Number of Spaces Available:  ");
//		numSpacesAvailLabel = new JLabel();
//		numSpacesPanel = new JPanel();
//		numSpacesPanel.add(spacesAvailLabel);
//		numSpacesPanel.add(numSpacesAvailLabel);
//				
//		//assemble middlePanel
//		middlePanel = new JPanel();
//		middlePanel.setLayout(new BorderLayout());
//		middlePanel.add(bikeLocPanel,BorderLayout.NORTH);
//		middlePanel.add(numBikesPanel,BorderLayout.CENTER);
//		middlePanel.add(numSpacesPanel,BorderLayout.SOUTH);
//		
//		
//		//Create Bottom Panel: PlacesOfInterestLabel; PlacesOfInterestTextArea
//		
//				
//		
//		
//		
//		
		
		//add Top, Middle and Bottom Panels to Main Panel		
		mainPanel.add(topPanel);
		mainPanel.add(middlePanel);
		mainPanel.add(bottomPanel);
		
		
		add(mainPanel);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setVisible(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
	}*/
	
	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  TourNY frame = new TourNY();
		      }
		    });
		    		
	}
	
}
