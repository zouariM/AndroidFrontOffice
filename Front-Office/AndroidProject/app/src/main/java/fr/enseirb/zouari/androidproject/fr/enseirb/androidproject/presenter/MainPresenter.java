package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user.User;

/**
 * Presenter User: Module et MainActivity: View
 */
public class MainPresenter {

    private static final String LOG_TAG = MainPresenter.class.getSimpleName();

    private User user;
    private View view;
    private SharedPreferences preferences;

    public MainPresenter(View view, SharedPreferences preferences){
        this.view = view;
        this.preferences = preferences;
        user = User.getCurrentUser();
    }

    // Gestion de la session
    public void setCurrentUser(){
        String userId = preferences.getString(Constants.USER_ID_KEY, null);

        if(userId != null) {
            User currentUser = User.getCurrentUser();
            currentUser.setId(userId);
            currentUser.setFirstName(preferences.getString(Constants.USER_FIRST_NAME_KEY, null));
            currentUser.setLastName(preferences.getString(Constants.USER_LAST_NAME_KEY, null));
            currentUser.setLogin(preferences.getString(Constants.USER_ID_KEY, null));
        }
    }

    public boolean isConnected(){
        return user.isConnected();
    }

    // Déconnexion et suppression de la session
    public void logout(){
        // Libération des ressources de la session
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        User.logout();
        user = User.getCurrentUser();

        // notification de vue
        view.onLogoutSuccess();
    }

    public interface View{
        void onLogoutSuccess();
    }
}
