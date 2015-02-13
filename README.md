# Caffein-Locator

## Used IDE
Android Studio 1.0.1

## Instructions on how to Run the Applictaion from Android Studio
1. Run Gradle Task clean
2. Run Gradle Task build
3. Go to Debug/Run Configuration and create Android Application
4. Run created Android Application
5. Window will popup to ask whether to use real device or emulated device (tested on Nexus 4)
6. If the Application is loaded with emulated device then 
    1. open a terminal
    2. type "telnet localhost <port>" (port can be found on the header of the window of the emulator ie <port>: Nexus_4_API_21)
    3. set the lat and log by typing "geo fix <lng> <lat>"

    Sample values that can be used to test:
        geo fix 151.212925 -33.890646
        geo fix 151.206939 -33.870593
        geo fix 151.134539 -34.850550
        geo fix 151.179963 -33.896549
        geo fix 151.206239 -33.881372

## Implementation Approach
    - Assumptions were made to achieve the requirements without complicating too much such are assumed that GPS was always avaliable, time between refresh and level of accuracy
    - Since I was new to the android app development, TDD wasn't exercised but instead used log and ran the application frequently
    - MainActivity is responsible to call LocationService and FoursquareExploreCoffeeNearbyTask
    - LocationService is responsible only to interact with Location Manager 
    - FoursquareExploreCoffeeNearbyTask is responsible to call the foursquare api to get the JSON response and also to parse the JSON to Venue Objects
    - Error Handling wasn't handled in a user friendly way since it's just logging but that can be the extension point
    - Failed to have unit tests since I wasn't familiar with the Android application structure initially and was moving things around. I was using this project as spike and was planning to create new project base on this but didn't have enough time.
