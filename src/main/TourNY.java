package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
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
	

/* We opted not to display a second map. 
 * TO DO: remove these instance vars when we clean up code.
	//for map with bike location; Center on middlePanel
	private URL urlBikeLocation;
	private BufferedImage imgBikeLocation;
	private JLabel mapBikeLocationLabel;
*/	
		
	//for NumBikesPanel (flow layout); Center on middlePanel
	private JLabel bikesAvailLabel;
	private JLabel numBikesAvailLabel;
	private JPanel numBikesPanel;

	
	//for NumSpacesPanel (flow layout); South on middlePanel
	private JLabel spacesAvailLabel;
	private JLabel numSpacesAvailLabel;
	private JPanel numSpacesPanel;


	//North on bottomPanel
	private JLabel placesInterestLabel;

	//Center on bottomPanel
	private JTextArea placesInterestTextArea;
	
	
	//constructor with helper methods
	TourNY(){		
		createGoButton();
		createPrimaryComponents();		
	}

	
	private void getMap (String location, JLabel mapLabelName, int mapZoomNum) {
		
		try {		
			String center = "center=";
			String zoom = "&zoom=";
			int zoomNum = mapZoomNum;
			String size = "&size=300x300&key=";
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
	
	private void createGoButton() {
		goButton = new JButton("Go");
		goButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				//Create remaining components:
				createSecondaryComponents();

				//Retrieve user input
				String startLocation = startAddressTextField.getText();
					
				//Access Geocoding API
				GoogleURLCreator gc = new GoogleURLCreator();
				String googleURL = gc.createURL(startLocation);						
				APICaller ac = new APICaller();
				String gResponse = ac.callAPI(googleURL);				
				GeocodingParser gp = new GeocodingParser();
				gp.parseGeocodingAPIResponse(gResponse);
				
				Double latStartLocation = gp.getOriginLocation().getLatitude();
				Double lngStartLocation = gp.getOriginLocation().getLongitude();
				String startLocationLatLng = latStartLocation.toString() + "," + lngStartLocation.toString();
								
				//fills Starting Address
				formatAddressfromGoogleLabel.setText(gp.getOriginLocation().getAddress());
				
				
				
				//Access Citibike API
				 
				//Get name of closest Citibike location. Set text for JLabel stationNameFromAPILabel
				//   ex: stationNameFromAPILabel.setText( insert String here);
				
				//Get num bikes avail. Set text for JLabel numBikesAvailLabel
				//Get num spaces available. Set text for JLabel numSpacesAvailLabel
				
				
				
				//Start SquareSpace API
				
				//Get places of interest. Store in JTextArea placesInterestTextArea.
				
				
				
				//Update map with starting location, bike station location,
				//and places of interest
				getMap(startLocationLatLng, mapStartLocLabel, 15);
					
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
		getMap(locationDefault, mapStartLocLabel, 12);

		//assemble topPanel
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(inputPanel, BorderLayout.NORTH);
		topPanel.add(mapStartLocLabel, BorderLayout.CENTER);
		
				
		//add Top Panel to Main Panel
		mainPanel = new JPanel();
		mainPanel.add(topPanel);
				
		//add Main Panel to Frame
		add(mainPanel);
		
		setTitle("Customize Your Active Tour of Manhattan");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setVisible(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void createSecondaryComponents() {
		
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
		middlePanel.add(numBikesPanel,BorderLayout.CENTER);
		middlePanel.add(numSpacesPanel,BorderLayout.SOUTH);
		
		
		//Create Bottom Panel: PlacesOfInterestLabel; PlacesOfInterestTextArea
		
				
		
		
		
		
		
		//add Top, Middle and Bottom Panels to Main Panel		
		mainPanel.add(topPanel);
		mainPanel.add(middlePanel);
		mainPanel.add(bottomPanel);
		
		
		add(mainPanel);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setVisible(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
	}
	
	public static void main(String[] args) {
		
		 SwingUtilities.invokeLater(new Runnable() {
		      public void run() {
		    	  TourNY frame = new TourNY();
		      }
		    });
		    		
	}
	
}
