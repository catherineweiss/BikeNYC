# 591-geo-data-viz
Our repo is at: https://github.com/princessruthie/591-geo-data-viz

## For Part One Submission
Team Name: Team Phoenix (from the ashes of our first idea, we rise!)

Team Members: Fred Chang, Ruthie Fields(PM), Catherine Weiss

Project Idea: A geo-location based recommendation application

Project Description: When the user enters an address, the program will find the Citi Bike station closest to the user and display the estimated walk time to the station, as well as number of bikes available. The program will also display recommendations for places of interest reachable from the station given a certain distance (e.g. the user can say I only wish to bike 1 mile).

Look at me editings for no reason!

## Goals and keywords
- Use the google api to turn addresses into coordinates.
- Use those coordinates and a landmark api to determine nearby locations
- Use those same coordinates to also determine nearby bike share kiosks
- Suggest nearby points of interest and nearby bikes to take to further points of interest

## 2.0 (aka dreams/reaches/hopes!)
- Use weather to intelligently not suggest biking in the rain
- Plan a route between points of interest
- Perhaps suggest an entire itinerary of points of interest and bike kiosks, like design a whole day.


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
