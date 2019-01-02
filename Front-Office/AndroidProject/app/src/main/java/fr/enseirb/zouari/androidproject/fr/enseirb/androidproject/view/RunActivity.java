package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.services.RunService;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.services.RunServiceAndroidAPI;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.services.RunServiceGoogleAPI;

/**
 * View de commencer un nouveau parcours
 */
public class RunActivity extends AppCompatActivity {

    private static final String LOG_TAG = RunActivity.class.getSimpleName();
    // Constantes pour instancier le service en fonction de préférences
    private static final String LOCALISATION_API_KEY = "locatlisation_api";
    private static final String GOOGLE_API = "googleAPI";
    private static final String ANDROID_API = "androidAPI";

    // Button de début
    private Button startBtn;
    // Button de fin
    private Button finishBtn;

    // Service de localisation
    private Intent runService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(this.getClass().getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        // Initialisation
        setRunService();
        setViews();
    }

    // Set localisation service
    private void setRunService(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String api = preferences.getString(LOCALISATION_API_KEY, GOOGLE_API);
        Log.d(LOG_TAG, "api = " + api);

        Class<?extends RunService> serviceClass;

        if(api.equals(GOOGLE_API))
            // Goole Play Service
            serviceClass = RunServiceGoogleAPI.class;
        else if(api.equals(ANDROID_API))
            // Android API standard
            serviceClass = RunServiceAndroidAPI.class;

        else
            throw new IllegalStateException("Check API preferecens list");

        runService = new Intent(this, serviceClass);
        Log.i(LOG_TAG, "Service class = " + serviceClass.getSimpleName());
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        // L'arret de service
        stopService(runService);

        super.onDestroy();
    }

    // Traitement des actions d'utilisateur (start ==> finish)
    private void setViews() {
        startBtn = (Button) findViewById(R.id.start_run);
        finishBtn = (Button) findViewById(R.id.finish_run);

        startBtn.setOnClickListener(v -> {
            startBtn.setEnabled(false);
            finishBtn.setEnabled(true);
            startService(runService);

            Toast.makeText(RunActivity.this, "C'est parti", Toast.LENGTH_SHORT).show();
        });

        finishBtn.setOnClickListener(v -> {
            startBtn.setEnabled(true);
            finishBtn.setEnabled(false);
            stopService(runService);

            Toast.makeText(RunActivity.this, "C'est terminé", Toast.LENGTH_SHORT).show();
        });

    }

}
