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
	private JPanel mainPanel; //This holds top, middle, and bottom panels. Border layout
	
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
	private JLabel closestStationLable;
	private JLabel stationNameFromAPILabel;
	private JPanel bikeLocPanel;
	
	//for map with bike location; Center on middlePanel
	private URL urlBikeLocation;
	private BufferedImage imgBikeLocation;
	private JLabel mapBikeLocationLabel;
		
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

	//South on bottomPanel
	private JTextArea placesInterestTextArea;
	
	
	//constructor with helper methods
	TourNY(){		
		createGoButton();
		createComponents();
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
			mapLabelName.setIcon(icon); //puts the map into the specified JLabel
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void createGoButton() {
		goButton = new JButton("Go");
		goButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String startLocation = startAddressTextField.getText();
					
				//Start Geocoding API
				GoogleURLCreator gc = new GoogleURLCreator();
				String googleURL = gc.createURL(startLocation);
						
				APICaller ac = new APICaller();
				String gResponse = ac.callAPI(googleURL);
				
				GeocodingParser gp = new GeocodingParser();
				gp.parseGeocodingAPIResponse(gResponse);
				
				Double latStartLocation = gp.getOriginLocation().getLatitude();
				Double lngStartLocation = gp.getOriginLocation().getLongitude();
				String startLocationLatLng = latStartLocation.toString() + "," + lngStartLocation.toString();
				
				getMap(startLocationLatLng, mapStartLocLabel, 15);
				
				formatAddressfromGoogleLabel.setText(gp.getOriginLocation().getAddress());
				
				
				//Start Citibank API
				 
				//Get name of closest Citibike location
				//Change second map to closest Citibike station
				//Get num bikes avail
				//Get num spaces available
				
				
				//Start SquareSpace API
				 			
				//Get places of interest
					
			
			}
		});
		
	}
	
	private void createComponents() {
		
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

		//startLocPanel:
		startAddressLabel = new JLabel("Starting Address:  ");
		formatAddressfromGoogleLabel = new JLabel("");
		startLocPanel = new JPanel();
		startLocPanel.add(startAddressLabel);
		startLocPanel.add(formatAddressfromGoogleLabel);

		//assemble topPanel
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(inputPanel, BorderLayout.NORTH);
		topPanel.add(mapStartLocLabel, BorderLayout.CENTER);
		topPanel.add(startLocPanel, BorderLayout.SOUTH);
		
		
		
		//Create Middle Panel: BikeLocPanel; Map; NumBikesPanel
		
				
		//Create Bottom Panel: NumSpacesPanel; PlacesOfInterestLabel; PlacesOfInterestTextArea
		
				
		//add Top, Middle and Bottom Panels to Main Panel
		mainPanel = new JPanel();
		mainPanel.add(topPanel);
//		mainPanel.add(middlePanel);
//		mainPanel.add(bottomPanel);
		
		
		//add Main Panel to Frame
		add(mainPanel);
		
		setTitle("Customize Your Active Tour of Manhattan");
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
