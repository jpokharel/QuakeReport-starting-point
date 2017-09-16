package com.example.android.quakereport;

/**
 * Created by jiwanpokharel89 on 9/4/2017.
 */

public class EarthquakeData {
    private double magnitude;
    private String place;
    private long dateOfEvent;
    private String url;

    public EarthquakeData(double magnitude, String place, long dateOfEvent, String url) {
        this.magnitude = magnitude;
        this.place = place;
        this.dateOfEvent = dateOfEvent;
        this.url = url;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public String getPlace() {
        return place;
    }

    public long getDateOfEvent() {
        return dateOfEvent;
    }

    public String getUrl(){return url;}

    @Override
    public String toString() {
        return "EarthquakeData{" +
                "magnitude=" + magnitude +
                ", place='" + place + '\'' +
                ", dateOfEvent=" + dateOfEvent +
                ", URL=" + url+
                '}';
    }
}
