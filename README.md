# BikeNYC README

Authors: Fred (Pei-Jan) Chang and Catherine Weiss

******************************************************************************************

# 1.0    Project Description
BikeNYC is an interactive geolocation-based application with a graphical user interface; it is designed to help people interested in touring Manhattan by bicycle. The application prompts the user for a starting address within Manhattan. The program finds the latitude and longitude associated with that address using Google's geocoding API and then finds the closest Citibike station to this location using the Citibike API. Real time information about the particular bike station is also returned from the Citibike API. The bike station coordinates are used as input to the FourSquare Spaces API to search for the five nearest landmarks. All locations are plotted on a static map on the user interface.  


# 2.0    Work Breakdown
- Design and implementation of Google Geocoding and FourSquare stacks: Catherine
- Design and implementation of Citibike stack: Fred
- Design of GUI: Fred
- Implementation of GUI: Catherine and Fred
- Project structure (e.g., packages) and file management on Github: Fred
- Junit testing: Catherine and Fred
- User manual and graphics design: Fred
- Summary: Catherine


# 3.0    Link to GitHub Repo
Our repo is at: https://github.com/princessruthie/591-geo-data-viz


# 4.0    Application Design
We chose to implement the project using full stack development in order to maximize team member productivity. Accordingly, the finished design includes one package for each of the three APIs. Additionally, we have a shared package that includes a Location class used in all stacks, a package for Junit tests, and a main package that includes the GUI and the program starter. See attached class diagram.


# 5.0    Source of Citibike Station Data
Real-time data regarding a specific Citibike station is sourced from here: https://www.citibikenyc.com/system-data. The system publishes real-time system data in General Bikeshare Feed Specification format, and can be accessed as a usual JSON API.

The main challenge is accessing a list of stations with coordinates. As no such data set is readily available for the public, our solution was to download historical data and piece together station IDs and their associated coordinates. With that complete, we check against a list of currently active stations to ensure that the station object does indeed retrieve an active station later in the program. A limitation of this approach is that a user would need to periodically download historical data in order to ensure that the list of stations is up-to-date.


# 6.0    Error Detection and Exception Handling
Error detection in our application is critical because everything the program does depends on user input. There are two user types of  input errors we identified: the user not entering any address; and the user entering an address that is not in Manhattan. Both situations are detected and handled within the GUI class. In addition, within the GoogleGeocoding class, there is a possibility that the Google server is unsuccessful in returning a response to the API request. The potential errors are handled using the “status” of the API request which is returned as part of the JSON response.          

The application is designed to handle IO exceptions (including FileNotFound and MalformedURL exceptions) and Parse exceptions. In the some classes, the checked exception is handled with a try-catch block within the original method. In other classes, a method throws an exception and it is handled later in the program by the GUI class. Because there does not appear to be a clear advantage to one method of exception handling over another, we have retained the coding style of the team member who authored the particular class. 


# 7.0    Unit Testing with JUnit
Unit testing was performed with Junit tests across six classes. A total of 24 tests were created and successfully run. The purposes of the tests include evaluation of proper URL construction, calling the APIs, parsing the API responses, and proper error handling of user input.


# 8.0    Usage of Git/Github
Throughout development, we used Github as a repository and version control system for our code. Our use of branches and commit messages improved as the project went on. We ended up coordinating our work in such a way that we had relatively few merge conflicts to resolve.
