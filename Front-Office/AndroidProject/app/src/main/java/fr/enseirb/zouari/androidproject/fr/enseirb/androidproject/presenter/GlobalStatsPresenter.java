package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter;

import android.os.Handler;
import android.os.Message;

import java.util.List;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.RunDistanceStats;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.RunSpeedStats;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.RunTimeStats;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.SpeedUnity;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.repository.RunningTrackRepository;

/**
 * Presenter User, RunSpeedStats, RunTimeStats, RunDistanceStats : Module, et GlobalStatsView : View
 */
public class GlobalStatsPresenter {

    private User user;
    private RunSpeedStats runSpeedStats;
    private RunTimeStats runTimeStats;
    private RunDistanceStats runDistanceStats;
    private View view;

    public GlobalStatsPresenter(View view){
        this.view = view;
        runDistanceStats = new RunDistanceStats();
        runSpeedStats = new RunSpeedStats();
        runTimeStats = new RunTimeStats();
        user = User.getCurrentUser();
    }

    // Envoie de données d'utilisateur vers View
    public String getUserFirstName(){
        return user.getFirstName();
    }
    public String getUserLastName(){
        return user.getLastName();
    }

    // Réccupération de parcours et notification de View en conséquence
    public void findAllRuns(){
        // User est unn Oberver de RunningTrackRepository
        if(user.isConnected() && user.getRunningTracks() == null)
            new RunningTrackRepository(new FetchDataHandler()).execute(R.id.findAllRunning, user.getId());
        else
            view.onFindAllRunsSuccess();
    }

    // Calcul de statistqiues pour View

    /**
     *
     TODO les données sont envoyés sous forme d'un tableau ==> fort couplage entre View et Presenter
     TODO return Map (par exp)
     TODO Utilisation de la réflèxivité de Java pour éviter la duplication de code
     car les trois méthodes implémente le meme algorithme
     * @return statistiques selon un ordre déterminé pour interpréter les informations correctement
     */
    public String[] getSpeedStats(){
        String[] stats = {"", "", ""};
        List<RunningTrack> runs = user.getRunningTracks();

        if(runs != null && runs.size() > 0){
            runSpeedStats.init();
            runSpeedStats.visit(runs);
            SpeedUnity unity = SpeedUnity.KM_H;

            stats[0] = runSpeedStats.getGlobalMaxSpeed(unity);
            stats[1] = runSpeedStats.getGlobalMoySpeed(unity);
            stats[2] = runSpeedStats.getGlobalMinSpeed(unity);
        }

        return stats;
    }

    public String[] getTimeFields(){
        String[] stats = {"", "", ""};
        List<RunningTrack> runs = user.getRunningTracks();

        if(runs != null && runs.size() > 0){
            runTimeStats.init();
            runTimeStats.visit(runs);

            stats[0] = runTimeStats.getMaxDurationFormatted();
            stats[1] = runTimeStats.getMinDurationFormatted();
            stats[2] = runTimeStats.getSumDurationFormatted();
        }

        return stats;
    }

    public String[] getDistanceStats(){
        String[] stats = {"", "", ""};
        List<RunningTrack> runs = user.getRunningTracks();

        if(runs != null && runs.size() > 0){
            runDistanceStats.init();
            runDistanceStats.visit(runs);

            stats[0] = runDistanceStats.getMaxDistanceFromated();
            stats[1] = runDistanceStats.getMinDistanceFormated();
            stats[2] = runDistanceStats.getSumDistanceFormated();
        }

        return stats;
    }

    // Contrat de View
    public interface  View{
        void onFindAllRunsSuccess();
    }

    // Handler de réccupération de parcours
    private class FetchDataHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // Notification de View
            view.onFindAllRunsSuccess();
            super.handleMessage(msg);
        }
    }

}
