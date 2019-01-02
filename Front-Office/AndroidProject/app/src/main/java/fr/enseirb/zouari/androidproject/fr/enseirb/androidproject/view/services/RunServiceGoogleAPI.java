package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.services;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * Service de localsiation par l'API de Google Play Service
 */
public class RunServiceGoogleAPI extends RunService {

    private static final String LOG_TAG = RunServiceGoogleAPI.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void setLocationManager() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void stopLocationManager() {
        mFusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void startLocationManger(int updatesIntervall) {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(updatesIntervall);
        locationRequest.setFastestInterval(updatesIntervall);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        RunServiceGoogleAPI.this.onLocationChanged(location);
                    }
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(LOG_TAG, "no permissions");
            return;
        }

        else
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}