package com.caffeinlocator;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FoursquareExploreCoffeeNearbyTask extends AsyncTask<Location, Integer, ArrayList<Venue>> {

    private static final String CLIENT_ID = "ACAO2JPKM1MXHQJCK45IIFKRFR2ZVL0QASMCBCG5NPJQWF2G";
    private static final String CLIENT_SECRET = "YZCKUYJ1WHUV2QICBXUBEILZI1DMPUIDP5SHV043O04FKBHL";
    private static final String LIMIT = "10";
    private static final String VERSION = "201408205";
    private static final String SECTION_COFFEE = "coffee";

    private Listener mListener;

    public FoursquareExploreCoffeeNearbyTask(Listener listener) {
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Venue> doInBackground(Location... location) {

        ArrayList<Venue> venues = new ArrayList<Venue>();
        String latitude = String.valueOf(location[0].getLatitude());
        String longitude = String.valueOf(location[0].getLongitude());
        Log.d(getClass().getSimpleName(), "doInBackground - Retrieved lat and lng " + latitude + " " + longitude);

        try {
            String uri = buildUri(latitude, longitude);
            Log.d(getClass().getSimpleName(), "doInBackground - Executing HTTP Get with uri " + uri);
            JSONObject venuesJson = executeHttpGet(uri);

            int returnCode = Integer.parseInt(venuesJson.getJSONObject("meta").getString("code"));
            Log.d(getClass().getSimpleName(), "doInBackground - Executed HTTP Get with code " + returnCode);
            if (returnCode == HttpStatus.SC_OK) {
                Log.d(getClass().getSimpleName(), "doInBackground - Parsing json to Venue Objects");
                venues = asVenueList(venuesJson);
            } else {
                String errorDetail = venuesJson.getJSONObject("meta").getString("errorDetail");
                mListener.onError(new Exception(errorDetail));
                Log.e(getClass().getSimpleName(), "doInBackground - Executed with error detail " + errorDetail);
            }

        } catch (Exception e) {
            mListener.onError(e);
            Log.e(getClass().getSimpleName(), "doInBackground - Executed with error detail " + e.getMessage());
        }

        return venues;
    }

    private String buildUri(String latitude, String longitude) {
        return "https://api.foursquare.com/v2/venues/explore"
                + "?ll=" + latitude + "," + longitude
                + "&limit=" + LIMIT
                + "&v=" + VERSION
                + "&sortByDistance=1"
                + "&section=" + SECTION_COFFEE
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET +
                "&m=foursquare";
    }

    private ArrayList<Venue> asVenueList(JSONObject venuesJson) throws JSONException {

        ArrayList<Venue> venues = new ArrayList<Venue>();
        JSONArray json = venuesJson
                .getJSONObject("response")
                .getJSONArray("groups").getJSONObject(0)
                .getJSONArray("items");

        for (int i = 0; i < json.length(); i++) {
            JSONObject venue = json.getJSONObject(i).getJSONObject("venue");

            String id = venue.getString("id");
            String name = venue.getString("name");

            JSONObject location = venue.getJSONObject("location");
            String address = location.optString("address");
            String city = location.optString("city");
            String state = location.optString("state");
            double distance = location.getDouble("distance");
            double lat = location.getDouble("lat");
            double lng = location.getDouble("lng");

            venues.add(new Venue(id, name, address, city, state, distance, lat, lng));
        }

        return venues;
    }

    private JSONObject executeHttpGet(String uri) throws Exception {
        HttpGet req = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();
        HttpResponse resLogin = client.execute(req);
        BufferedReader r = new BufferedReader(new InputStreamReader(resLogin
                .getEntity().getContent()));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = r.readLine()) != null) {
            sb.append(s);
        }

        return new JSONObject(sb.toString());
    }

    @Override
    protected void onPostExecute(ArrayList<Venue> venues) {
        mListener.onVenueRetrieved(venues);
        super.onPostExecute(venues);
    }

    public interface Listener {
        void onVenueRetrieved(ArrayList<Venue> venues);
        void onError(Exception e);
    }
}
