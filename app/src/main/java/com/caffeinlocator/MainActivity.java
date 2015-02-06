package com.caffeinlocator;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private VenueListAdapter venueListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(getLocalClassName(), "onCreate - Setting View");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.custom_list);
        venueListAdapter = new VenueListAdapter(MainActivity.this, new ArrayList<Venue>());
        listView.setAdapter(venueListAdapter);


        final LocationService locationService = new LocationService(this);
        Log.d(getLocalClassName(), "onCreate - Created Location Service");

        if (locationService.isGPSEnabled()) {
            locationService.requestLocationChanges(new LocationChangedListener());
            Log.d(this.getLocalClassName(), "onCreate - Requested Location Changes");
        } else {
            Log.e(this.getLocalClassName(), "onCreate - GPS is not enabled");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private class LocationChangedListener implements LocationService.LocationChangedListener {

        @Override
        public void onLocationChanged(final Location location) {
            FoursquareExploreCoffeeNearbyTask foursquareExploreCoffeeNearbyTask = new FoursquareExploreCoffeeNearbyTask(new FourSquareServiceListener());
            Log.d(getLocalClassName(), "onLocationChanged - Created foursquareExploreCoffeeNearby task");
            Log.d(getLocalClassName(), "onLocationChanged - Executing foursquareExploreCoffeeNearby task with lat and lng " + location.getLatitude() + " " + location.getLongitude());
            foursquareExploreCoffeeNearbyTask.execute(location);
        }
    }

    private class FourSquareServiceListener implements FoursquareExploreCoffeeNearbyTask.Listener {

        @Override
        public void onVenueRetrieved(final ArrayList<Venue> venues) {
            venueListAdapter.setVenues(venues);
            Log.d(getLocalClassName(), "onVenueRetrieved - Updated venue with new list");
        }

        @Override
        public void onError(Exception e) {
            Log.e(getLocalClassName(), "onLocationChanged - " + e.getMessage());
        }
    }
}
