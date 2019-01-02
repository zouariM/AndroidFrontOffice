package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.RunStats;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.RunStatsActivity;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;

/**
 * View affichage la liste des statistiques pour chaque parcours
 * La liste contient duréé, date début, date finale et distance
 * TODO implémenter un Presenter pour cette Vue
 * L'impélmentation de Presenter pour cette Vue n'est indispensable
 * En effet, Le flux entre cette View et Model est en lecture seulement ++ pas d'actions d'user
 */
public class RunStatsArrayAdapter extends ArrayAdapter<RunStats> {

    private Activity activity;
    private List<RunStats> runningTracks;
    private boolean inverse = false;


    public RunStatsArrayAdapter(Activity activity, int ressource, List<RunStats> stats){
        super(activity, ressource, stats);
        this.activity = activity;
        this.runningTracks = stats;

    }

    public void add(RunningTrack r){
        RunStats stat = new RunStats(r);
        this.runningTracks.add(stat);

        Log.i(this.getClass().getSimpleName(), "Date :" + stat.getStartTimeString());
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(this.getClass().getSimpleName(), "Position for view : "+position);
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_stats, parent,false);

        setViewFields(position, rowView);
        setViewAction(position, rowView);

        return rowView;
    }

    private void setViewFields(int position, View rowView){
        TextView starTime = (TextView)rowView.findViewById(R.id.run_start_time);
        TextView finishTime = (TextView)rowView.findViewById(R.id.run_finish_time);
        TextView duration = (TextView)rowView.findViewById(R.id.run_duration);
        TextView distance = (TextView)rowView.findViewById(R.id.run_distance);

        position = (inverse == true) ? (runningTracks.size()-position-1) : position;
        RunStats stats = runningTracks.get(position);

        starTime.setText(stats.getStartTimeString());
        finishTime.setText(stats.getFinsihTime());
        duration.setText(stats.getDurationString());
        distance.setText(String.valueOf(stats.getDistanceString()));
    }

    // Cliquer sur un item ==> afficher la route coorespondante sur Map
    private void setViewAction(int position, View rowView){
        rowView.setOnClickListener(v -> {
            Log.d(this.getClass().getSimpleName(), "view row click listener ** position : " + position);
            ((RunStatsActivity)activity).displayRouteFragment(position);
        });
    }

    @Override
    public void sort(Comparator<? super RunStats> comparator) {
        inverse = false;
        Collections.sort(this.runningTracks, comparator);
    }

    public void inverse(){
        this.inverse = !inverse;
    }

}
