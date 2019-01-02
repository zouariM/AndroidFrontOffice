package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view;

import android.preference.PreferenceActivity;
import android.os.Bundle;

import fr.enseirb.zouari.androidproject.R;

/**
 * Préférence
 * User peut modifier l'intervalle de updates ==> le choix est important pour les statistiques (Vmax et Vmin)
 * User peut sélectionner l'API de localisation (mode de dév)
 * User peut annuler la connexion ==> cas à traiter TODO
 */
public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
