package com.caffeinlocator;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service {

    /**
     * LocationService gets LocationManager from the given context and allows to requiter listener
     * when location is updated.
     * For simplicity purpose the Min distance change for updates and min time between updates are
     * set as constant which can be replaced as variables when it's needed.
     */

    private final Context mContext;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;


    private LocationManager locationManager;

    public LocationService(Context context) {
        this.mContext = context;
        locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
    }

    /**
     * Function to register listener to GPS provider which will be notified when there is location
     * changes
     *
     * @return boolean
     */
    public void requestLocationChanges(final LocationChangedListener locationChangedListener) {

        Log.d(getClass().getSimpleName(), "requestLocationChanges - registering LocationChangedListener");
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        locationChangedListener.onLocationChanged(location);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
    }

    /**
     * Function to check GPS is enabled
     *
     * @return boolean
     */
    public boolean isGPSEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    /**
     * Interface to register location change listener
     */
    public interface LocationChangedListener {
        void onLocationChanged(final Location location);
    }
}
