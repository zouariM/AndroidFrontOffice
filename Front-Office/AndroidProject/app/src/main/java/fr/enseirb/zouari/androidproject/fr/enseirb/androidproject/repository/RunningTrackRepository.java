package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.repository;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.observer.AsyncTaskObservable;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;

/**
 * Repository of RunningTrack
 * Le patron de design Observer est implémenté
 * Une instance de cette classe est observée par le singleton User (currentUser)
 * Pour réduire la connexion au Back-Office ==> optimiser la consommation de la bande passante
 */
public class RunningTrackRepository extends AsyncTaskObservable<RunningTrack> {

    public static final String URL = "http://192.168.43.108:8081/runningTracks";
    public static final String URL_ADD = "http://192.168.43.108:8081/run";

    private Handler handler;
    // Chaque requete est identifié par un id
    private int idOp;

    public RunningTrackRepository(Handler handler){
        super();
        this.handler = handler;

        if(User.getCurrentUser() == null)
          throw new IllegalStateException("Current User is null");

        // Aout de l'observeur currentUser
        this.addObserver(User.getCurrentUser());
    }

    // Réccupération des parcours (exécuté une seule fois par session active == ouverture de l'application)
    private StringBuffer findAll(String id){
        String url = URL + "/" + id;
        return HttpUtils.getHttpResponse(url);
    }

    // Enregistrement de parcours
    private StringBuffer finishRun(String id, RunningTrack positions){
        long finishTime = System.currentTimeMillis();
        String url = URL_ADD + "/" + id ;

        return HttpUtils.postHttpResponse(url, positions);
    }

    private List<RunningTrack> onPostFindAll(StringBuffer stringBuffer){
        if(stringBuffer == null)
            return new ArrayList<>();

        Gson gson = new Gson();
        RunningTrack tab[] = gson.fromJson(stringBuffer.toString(), RunningTrack[].class);
        List<RunningTrack> l = new ArrayList<>(Arrays.asList(tab));
        // nofification des observateure
        this.notifyOnFindAll(l);

        return l;
    }

    private RunningTrack onPostFinishRun(StringBuffer stringBuffer){
        Gson gson = new Gson();
        RunningTrack run = gson.fromJson(stringBuffer.toString(), RunningTrack.class);
        // notification des observateurs
        this.notifyOnAdd(run);

        return run;
    }

    @Override
    protected StringBuffer doInBackground(Object... objects) {
        idOp = (int)objects[0];
        String id = (String) objects[1];

        switch (idOp){
            case R.id.findAllRunning:
                return findAll(id);

            case R.id.finishRun:
                RunningTrack positions = (RunningTrack) objects[2];
                return finishRun(id, positions);
        }

        return null;
    }

    // Interprétation de la réponse

    /**
     * @ see this.onPostFinishRun && this.onPostFindAll
     * @param stringBuffer la réponse à interpréter
     */
    @Override
    protected void onPostExecute(StringBuffer stringBuffer) {
        Message message = Message.obtain();
        Gson gson = new Gson();
        Object obj = null;

        Log.i(this.getClass().getSimpleName(), "id of operation = " + idOp);

        switch (idOp){
            case R.id.findAllRunning:
                obj = onPostFindAll(stringBuffer);
                break;
            case R.id.finishRun:
                obj = onPostFinishRun(stringBuffer);
                break;
        }

        if(handler != null) {
            message.obj = obj;
            handler.sendMessage(message);
        }
    }
}
