package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.Position;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.repository.RunningTrackRepository;

/**
 * Abstract Service de localisation Tracking
 * voir les deux implémentations
 * Ce service communique directement avec Model (RunTracking) sans l'intermédiaire d'un Presenter
 * Deux solutions sont implémentées afin de choisir en <<production>> la plus optimale
 *
 * TODO traitement de permissions (le cas d'echec) + (API >= 23) demande de permissions
 */

public abstract class RunService extends Service {

    // liste de position à enregister
    private RunningTrack run;
    // Débogage
    private static final String LOG_TAG = RunService.class.getSimpleName();

    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "onCreate");
        //set location manager
        setLocationManager();
        run = new RunningTrack();

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        // envoie de requete pour enregister le nouveau parcours en entier
        run.setFinishTime(System.currentTimeMillis());
        String id = User.getCurrentUser().getId();
        new RunningTrackRepository(new AddPositionsHandler()).execute(R.id.finishRun, id, run);

        // arret de tracking
        stopLocationManager();

        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand {flags: " + flags + " , startId: " + startId);
        run.init();

        // Réccupération de updateIntervall personnalisé par User (voir Préférences)
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int updatesIntervall = (Integer.parseInt(preferences.getString("updates_intervall", "30"))) * 1000;
        Log.d(LOG_TAG, "updatesIntervall :" + updatesIntervall);
        startLocationManger(updatesIntervall);

        return super.onStartCommand(intent, flags, startId);

    }

    // Méthodes abstraites qui dépend de type de serivce (provider = Adnroid API/ Google Play Service)
    protected abstract void setLocationManager();
    protected abstract void stopLocationManager();
    protected abstract void startLocationManger(int updatesIntervall);

    private class AddPositionsHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.i(LOG_TAG, "finish " + (RunningTrack)msg.obj);
            super.handleMessage(msg);
        }
    }

    // Réccupération de la nouvelle position et mettre à jour run (parcours)
    protected void onLocationChanged(Location location){
        Position p = new Position(location);
        Log.d(LOG_TAG, "Position : " + p);
        run.addPosition(p);
    }


}
