package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.Position;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.RunSpeedStats;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.SpeedUnity;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;

/**
 * Presenter User, RunSpeedStats, Run : Model et RunRouteFragment: View
 */
public class RunRoutePresenter {

    private View view;
    private User user;
    private RunSpeedStats runSpeedStats;
    private RunningTrack run;

    public RunRoutePresenter(View view){
        this.view = view;
        user = User.getCurrentUser();
        runSpeedStats = new RunSpeedStats();
        run = null;
    }

    public List<LatLng> getRunningTrackByIndex(int index){
        run = user.getRunningTrackByIndex(index);
        List<LatLng> points = new ArrayList<>();

        for(Position p:run.getPositions())
            points.add(new LatLng(p.getLatitude(), p.getLongitude()));

        return points;
    }

    // TODO minimiser le couplage entre presenter et view concernant l'interprétation de tableau
    public String[] getSpeedStats(SpeedUnity unity){
        String stats[] = {"", "", ""};
        if(run != null){
            runSpeedStats.init();
            runSpeedStats.visit(run);

            stats[0] = runSpeedStats.getMaxSpeedFormated(unity);
            stats[1] = runSpeedStats.getMoySpeedFormated(unity);
            stats[2] = runSpeedStats.getMinSpeedFormated(unity);
        }

        return stats;
    }

    public interface View{

    }
}
