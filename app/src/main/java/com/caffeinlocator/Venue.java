package com.caffeinlocator;

public class Venue {

    private String id;
    private String name;
    private String address;
    private String city;
    private String state;
    private double distance;
    private double latitude;
    private double longitude;

    public Venue(String id, String name, String address, String city, String state, double distance, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.distance = distance;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public double getDistance() {
        return distance;
    }
}
