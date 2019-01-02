package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter.GlobalStatsPresenter;

/**
 * View de statistiques globales des parcours
 */
public class GlobalStatsFragment extends Fragment implements GlobalStatsPresenter.View{

    private static final String LOG_TAG = GlobalStatsFragment.class.getSimpleName();

    // Full name
    private TextView name;
    private View view;

    // Presenter
    private GlobalStatsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_general_stats, container, false);
        // initialisation
        init();

        return view;
    }

    // Instantiation de Presenter
    public void init(){
        presenter = new GlobalStatsPresenter(this);
        // Réccupération de parcours
        presenter.findAllRuns();

        setUserData();
    }

    // Mettre à jours les statistiques
    // TODO  à optimiser la miste à jour == éviter de refaire tout
    @Override
    public void onResume() {
        super.onResume();
        if(presenter != null)
            setViewsFields();
        Log.d(LOG_TAG, "onResume");
    }

    // Affichage de statistiques
    private void setViewsFields(){
        setDistanceFields();
        setTimeFields();
        setSpeedFields();;
    }

    // Statistiques de vitesse
    private void setSpeedFields(){
        // Vitesse Max
        TextView max = (TextView)view.findViewById(R.id.general_stats_speed_max);
        // Vitesse Min
        TextView min = (TextView)view.findViewById(R.id.general_stats_speed_min);
        // Vitesse Moyenne
        TextView moy = (TextView)view.findViewById(R.id.general_stats_speed_moy);
        String[] stats = presenter.getSpeedStats();

        max.setText(stats[0]);
        moy.setText(stats[1]);
        min.setText(stats[2]);
    }

    // Statistiques de durée
    private void setTimeFields(){
        // Durée Max
        TextView max = (TextView)view.findViewById(R.id.general_stats_time_max);
        // Durée Min
        TextView min = (TextView)view.findViewById(R.id.general_stats_time_min);
        // Somme de durées
        TextView sum = (TextView)view.findViewById(R.id.general_stats_time_sum);
        String[] stats = presenter.getTimeFields();

        max.setText(stats[0]);
        min.setText(stats[1]);
        sum.setText(stats[2]);
    }

    // Statistiques de distances
    private void setDistanceFields(){
        // Distance Max
        TextView max = (TextView)view.findViewById(R.id.general_stats_distance_max);
        // Distance Min
        TextView min = (TextView)view.findViewById(R.id.general_stats_distance_min);
        // Somme de distance
        TextView sum = (TextView)view.findViewById(R.id.general_stats_distance_sum);
        String[] stats = presenter.getDistanceStats();

        max.setText(stats[0]);
        min.setText(stats[1]);
        sum.setText(stats[2]);
    }

    // Traitement de parcours suite à évenement déclenché (appel de cette méthode) par le presenter
    @Override
    public void onFindAllRunsSuccess() {
        setViewsFields();
    }

    // Affichage de données d'utilisateur
    // TODO développer cet affichage (+fullname) == exp date de dernière connexion
    public void setUserData(){
        name = (TextView)view.findViewById(R.id.general_stats_name);
        name.setText(getUserName());
    }

    // Réccupération de fullname
    private String getUserName(){
        String firstName = presenter.getUserFirstName();
        String lastName = presenter.getUserLastName();

        return String.format("%s %s", firstName, lastName);
    }

}
