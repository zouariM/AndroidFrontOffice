package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.RunStats;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.adapters.RunStatsArrayAdapter;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;

/**
 * View affichage la liste des statistiques pour chaque parcours
 * La liste contient duréé, date début, date finale et distance
 * Plus, des options de Tri
 * TODO implémenter un Presenter pour cette Vue
 * L'impélmentation de Presenter pour cette Vue n'est indispensable
 * En effet, Le flux entre cette View et Model est en lecture seulement ++ pas d'actions d'user
 */
public class StatsListFragment extends Fragment {

    private static final String LOG_TAG = StatsListFragment.class.getSimpleName();
    // Adapatateur
    private RunStatsArrayAdapter statsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats_list, container, false);
        String userId = User.getCurrentUser().getId();
        this.statsAdapter = new RunStatsArrayAdapter(getActivity(), R.layout.item_stats, new ArrayList<>());

        ListView listView = (ListView)view.findViewById(R.id.list_run_stats);
        listView.setAdapter(statsAdapter);
        setData();

        return view;
    }

    private void setData(){
        User user = User.getCurrentUser();
        notifyAdapter(User.getCurrentUser().getRunningTracks());
    }

    private void notifyAdapter(List<RunningTrack> data){
        Log.i(this.getClass().getSimpleName(), "Nbr of Run : " + data.size());
        for(RunningTrack r:data){
            if(r.getPositions() != null && r.getPositions().size() > 0)
                statsAdapter.add(r);
        }

        statsAdapter.notifyDataSetChanged();
    }

    public void handleTriCriteria(Integer idCriteria) {
        Comparator<RunStats> comparator;
        String criteria;

        switch(idCriteria){
            case R.id.stats_sort_by_distance:
                criteria = "distance";
                comparator = (s1, s2)->{
                    Float d1 = s1.getDistance();
                    Float d2 = s2.getDistance();

                    return d1.compareTo(d2);
                };
                break;


            case R.id.stats_sort_by_duration:
                criteria = "duration";
                comparator = (s1, s2)->{
                    Long d1 = s1.getDuration();
                    Long d2 = s2.getDuration();

                    return d1.compareTo(d2);
                };
                break;

            case R.id.stats_sort_by_start:
                criteria = "date début";
                comparator = (s1, s2)->{
                    Long st1 = s1.getStartTime();
                    Long st2 = s2.getStartTime();

                    return st1.compareTo(st2);
                };
                break;

            default:
                return;
        }

        statsAdapter.sort(comparator);
        statsAdapter.notifyDataSetChanged();

        Toast.makeText(getActivity(), "Liste bien triée (>>) selon " +criteria, Toast.LENGTH_LONG).show();
    }

    public void inverseList(){
        statsAdapter.inverse();
        statsAdapter.notifyDataSetChanged();
    }

}
