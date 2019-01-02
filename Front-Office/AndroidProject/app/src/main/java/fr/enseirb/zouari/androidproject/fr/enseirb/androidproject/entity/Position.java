package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity;

import android.location.Location;
import android.location.LocationManager;
import java.io.Serializable;
import java.util.Date;

/**
 * Model Position
 */
public class Position implements Serializable {

    // Données GPS
    private double latitude;
    private double longitude;
    // Instant de localisation
    private long time;

    public Position() {
        super();
    }

    public Position(Location l){
        latitude = l.getLatitude();
        longitude = l.getLongitude();
        time = System.currentTimeMillis();
    }

    public Position(double latitude, double longitude, long time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = System.currentTimeMillis();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Location getLocationInstance(){
        Location location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return location;

    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        Date date = new Date(time);
        return String.format("Postion : {latitude: %s, longitude: %s, time: %s",
                            latitude, longitude, date.toString());
    }

    public float getDistanceTo(Position p2){
        Location l1 = this.getLocationInstance();
        Location l2 = p2.getLocationInstance();

        return l1.distanceTo(l2);
    }

}
