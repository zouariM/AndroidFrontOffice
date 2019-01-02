package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.services;

import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

/**
 * Service de localsiation par l'API de Google Play Service
 */
public class RunServiceGoogleAPI extends RunService
{
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
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setInterval(updatesIntervall);
        locationRequest.setFastestInterval(updatesIntervall);

        locationCallback = new LocationCallback(){
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
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}