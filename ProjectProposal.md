# 591-geo-data-viz
Our repo is at: https://github.com/princessruthie/591-geo-data-viz

## For Part One Submission
App Name: BikeNYC

Team Members: Fred Chang, Catherine Weiss

Project Idea: A geo-location based recommendation application

Project Description:
Citibike is America’s largest and most successful bike-share program. Given the density of bike-share stations in Manhattan, you can almost guarantee that you can find one within a block or two’s walk. However, they may not always be in the most obvious of locations. 

Therefore, we’re introducing BikeNYC, an application that takes in the user’s location, provides the user with the closest available station, station information, as well as recommendations for nearby attractions.

Many employers and institutions now offer their employees annual passes, therefore, this application is not only useful for tourists, but also for locals who may want to take advantage of their pass, travel faster and more sustainably, or skip the crowded subways.


## Goals and keywords
- Use the google api to turn addresses into coordinates.
- Use those coordinates and a landmark api to determine nearby locations
- Use those same coordinates to also determine nearby bike share kiosks
- Suggest nearby points of interest and nearby bikes to take to further points of interest
- UI Basic components:
  - Input: Ask for user address through text field
  - Return: "Closest CitiBike Station: XXXX, Distance from you: 0.25mi."
  - Return: "Destinations within 0.5mi radius of Station: ......"

## 2.0 (aka dreams/reaches/hopes!)
- Use weather to intelligently not suggest biking in the rain
- Plan a route between points of interest
- Perhaps suggest an entire itinerary of points of interest and bike kiosks, like design a whole day.
- Additional UI components:
  - Google Maps image showing CitiBike station, potentially with other info overlays
  - Slider allowing user to dynamically adjust bike radius


## Data sources
Google api for address to geocode: 
https://developers.google.com/maps/documentation/geocoding/start
Citi bike stations:	
- https://groups.google.com/forum/#!topic/citibike-hackers/ZkoqjkTIOuw	
- http://citibike-stations.herokuapp.com/
Foursquare api for turning locations into points of interest
- https://developer.foursquare.com/places-api
Open weather api for checking current conditions
- https://openweathermap.org/api

## Breakdown of roles
- Parsing data in Java:1 api per developer
- Analyzer (methods): 1 per developer
- Visualization: part of 2.0

## What makes this 'advanced java'
- Using three REST apis
- Parsing JSON

## Meeting with Yuming Qin:
- weather would be easier
- confirmed: there will be a user interface. doesn't have to be super complex. 
- with a ui, the project would be enough
- she will be making sure everyone has programming contributions on github
- try and do it full stack independent to reduce merge conflicts and reduce interdependence

## To Do
First Priority (DUE end of day Friday) (By end of day Friday, our program should be running!)
- (COMPLETE --> Fred merged all into main) 
- (COMPLETE) Ruthie: divide into packages
- (COMPLETE) Fred: have tourNY.java call citibike
- (COMPLETE) Ruthie: have tourNY.java call foursquare
- (COMPLETE) Fred: return closest bike station as a Location object
- (COMPLETE) Fred: fill in JPanels
- (COMPLETE) Catherine: finish getMap method
- (COMPLETE) Fred: take all GUI methods and create new class for them

Second Priority (DUE end of day Saturday):
- (COMPLETE) Catherine: put Times Square as default address
- (COMPLETE) Catherine: add Javadocs for TourNYGUI
- (COMPLETE) break the program return isn't wired; empty searchbox cuases excepting in geocode parser
- (COMPLETE) Catherine: can we wire up the return key to be go?
- (COMPLETE) Catherine: if user enters an address outside of NY, it currently makes map go there. Check Starting Address for zip codes in Manhattan and then make the user enter a new address.
- done maybe write query string builder
- (COMPLETE) Catherine: Message thrown by GoogleAPI Code: "More than one location is associated with that address. Please verify that your starting location is correct." What to do about this?

Third Priority (DUE end of day MONDAY):
1. (COMPLETE) user manual w/ screenshots - Fred
2. (COMPLETE) summary.txt (name and description of project, work breakdown, link to github repo)
3. (COMPLETE) JUnit testing (15 total tests over 3 different classes)
4. (COMPLETE) Are errors handled well?
5. (COMPLETE) Are exceptions handled well?
6. (COMPLETE) check casting with JSON parsing (all three API callers)
7. (COMPLETE) check all files for javadoc comments, indenting
8. submit in a zip file: code, user manual, summary.txt



## Running the app:
- copy util/APIKeysSample.java to util/APIKeys.java and fill in the strings
- from main package, run BikeNY.java's main method


