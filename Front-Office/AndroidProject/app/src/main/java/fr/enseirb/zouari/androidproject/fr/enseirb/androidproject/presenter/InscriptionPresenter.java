package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.repository.UserRepository;

/**
 *  Presenter l'intermédiare ente User (Model) et InscriptionActivity (View)
 */
public class InscriptionPresenter {

    // débogage
    private static final String LOG_TAG = InscriptionPresenter.class.getSimpleName();

    private final User user;
    private final View view;

    public InscriptionPresenter(View view){
        this.view = view;
        // User étant un singleton partagé entre les différents composants
        this.user = User.getCurrentUser();
    }

    // Méthode appelé par Vue suite au soumettre de formulaire
    public void addUser(String firstName, String lastName, String login, String password){
        User user = User.getCurrentUser();

        user.setLogin(login);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // Envoie de requète d'ajout d'utilisateur
        new UserRepository(new AddUserHandler()).execute(R.id.addUser, user);
    }

    // Contrat d'interface
    public interface View{
        //  jout d'utilsateur avec succées
        public void onAddUserSuccess();
        // Echec d'ajout
        public void onAddUserFailure();
    }

    // Traitent de la réponse (msg.obj) envoyé par userRepository
    private class AddUserHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            User user = (User) msg.obj;

            if (user != null) {
                Log.i(LOG_TAG, user.toString());
                // Notification de view (Success)
                view.onAddUserSuccess();
            }
            else
                // Notification de view (Failure)
                view.onAddUserFailure();
        }

    }
}
