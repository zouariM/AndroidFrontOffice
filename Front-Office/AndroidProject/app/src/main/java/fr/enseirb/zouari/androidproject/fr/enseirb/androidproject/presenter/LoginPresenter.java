package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.repository.UserRepository;

/**
 * Prensenter pour (User, SharedPreferences): Model et LoginFragment: View
 */
public class LoginPresenter {

    private static final String LOG_TAG = LoginPresenter.class.getSimpleName();

    private User user;
    private View view;
    //
    private SharedPreferences preferences;

    public LoginPresenter(View view, SharedPreferences preferences){
        this.view = view;
        // On récuppère l'instance Singleton
        this.user = User.getCurrentUser();
        this.preferences = preferences;
    }

    // Envoie de requète de login, cette méthode est appelée par view
    public void login(String login, String password){
        user.setLogin(login);
        user.setPassword(password);

        new UserRepository(new LoginHandler()).execute(R.id.login, user);
    }

    // Le contrat de view
    public interface View{
        // Cette méthode est appelé si l'utilisateur est authentifié
        void onLoginSuccess();
        // Cette méthode est appelé dans le cas d'echec d'authentification
        void onLoginFailure();
    }

    // Handler (traitant) de réponse (msg.obj) de la requete de login
    private class LoginHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            User user = (User)msg.obj;

            // Success
            if(user != null) {
                Log.i(LOG_TAG, "Login as " + (User) msg.obj);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString(Constants.USER_ID_KEY, user.getId());
                editor.putString(Constants.USER_FIRST_NAME_KEY, user.getFirstName());
                editor.putString(Constants.USER_LAST_NAME_KEY, user.getLastName());
                editor.commit();

                view.onLoginSuccess();
            }

            // Echec
            else {
                Log.e(LOG_TAG, "Check of login");
                view.onLoginFailure();
            }

            super.handleMessage(msg);
        }
    }
}
