package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Serivce de localisation par L'API standard de Android
 */
public class RunServiceAndroidAPI extends RunService implements LocationListener {

    private static final String LOG_TAG = RunServiceAndroidAPI.class.getSimpleName();

    private LocationManager locationManager;

    @Override
    protected void setLocationManager(){
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void stopLocationManager() {
        locationManager.removeUpdates(this);
    }

    @Override
    protected void startLocationManger(int updatesIntervall) {
        Log.d(LOG_TAG, "startLOcation Manager");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            Log.d(LOG_TAG, "disabled permissions");
        else
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, updatesIntervall, 0, this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(LOG_TAG, "onProviderEnabled " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i(LOG_TAG, "onProviderDisable " + provider);
    }


}
