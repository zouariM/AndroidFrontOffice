package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.repository;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.gson.Gson;
import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;

/**
 * Repository pour User model
 */
public class UserRepository extends AsyncTask<Object, Integer ,StringBuffer> {

    public static final String USER_URL = "http://192.168.43.108:8081/users";
    private Handler handler;

    public UserRepository(Handler handler){
        super();
        this.handler = handler;
    }

    // Réccupération de user par id
    private StringBuffer findUserById(String login){
        return HttpUtils.getHttpResponse(USER_URL + "/" + login);
    }

    // Authentification
    private StringBuffer login(User user){
        String url = USER_URL + "/" + "login";
        return HttpUtils.postHttpResponse(url, user);
    }

    // Inscription
    private  StringBuffer addUser(User user){
        return HttpUtils.postHttpResponse(USER_URL, user);
    }

    // Chaque requete est identifié par un id
    @Override
    protected StringBuffer doInBackground(Object... strings) {
        int idOp = (int) strings[0];
        User user;

        switch (idOp){
            case R.id.findUserByLogin:
                String login = (String)strings[1];
                Log.i(this.getClass().getSimpleName(), "Fecth user with : " + login);
                return findUserById(login);

            case R.id.addUser:
                user = (User)strings[1];
                Log.i(this.getClass().getSimpleName(), "add user : " + user);
                return addUser(user);

            case R.id.login:
                user = (User)strings[1];
                Log.i(this.getClass().getSimpleName(), "login user : " + user);
                return login(user);

        }

        return null;
    }

    // L'interprétation de la réponse
    @Override
    protected void onPostExecute(StringBuffer stringBuffer) {
        super.onPostExecute(stringBuffer);
        Message message = Message.obtain();
        message.obj = null;

        if(stringBuffer != null) {
            Log.i(this.getClass().getSimpleName(), "Reponse : " + stringBuffer.toString());

            // Désirialisation de l'objet JSON (--> user)
            Gson gson = new Gson();
            User user = gson.fromJson(stringBuffer.toString(), User.class);
            Log.i(this.getClass().getSimpleName(), user.toString());
            message.obj = user;
        }

        if(handler != null)
            handler.sendMessage(message);
    }
}