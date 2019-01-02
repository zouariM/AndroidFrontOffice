package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.repository;

import android.os.Handler;
import android.util.Log;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.observer.AsyncTaskObservable;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.Position;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;

/**
 * Repository of Positon Model
 */
public class PositionRepository extends AsyncTaskObservable<RunningTrack> {


    public static final String POSITION_URL = "http://192.168.43.108:8081/runningTracks";
    private Handler handler;

    public PositionRepository(Handler handler){
        super();
        this.handler = handler;
    }

    // Ajout d'une nouvelle position

    /**
     * @deprecated le parcours est ajouté en entier
     * @param id identifiant d'utilisateur
     * @param p la postion à ajouter
     * @return la réponse (objet json de la position ajoutée)
     */
    private StringBuffer addPosition(String id, Position p){
        String url = POSITION_URL + "/" + id;
        Log.d(this.getClass().getSimpleName(), url);
        return HttpUtils.postHttpResponse(url, p);
    }

    // Chaque commande (requete) est identifiée par un id
    @Override
    protected StringBuffer doInBackground(Object... objects) {
;       Log.d(this.getClass().getSimpleName(), "doInBackground");

        try{
            int idOp = (int)objects[0];
            String id = (String) objects[1];
            Position p = (Position) objects[2];
            switch (idOp){
                case R.id.addPosition:
                    return addPosition(id, p);

                default:
                    throw new IllegalArgumentException("Check your request");

            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(StringBuffer stringBuffer) {
        if(stringBuffer == null)
            Log.i(this.getClass().getSimpleName(), "Echec");
        else
            Log.i(this.getClass().getSimpleName(), "Success");
    }
}
