package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.user;

import android.util.Log;
import com.google.gson.annotations.JsonAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.observer.ModelObserver;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;

/**
 * Model User
 * Le patron de desing Singleton est implémenté
 */
@JsonAdapter(UserJsonAdapter.class)
public class User implements Serializable, ModelObserver<RunningTrack> {

    // Le courant utilisateur
    private static User currentUser = null;
    private static final String LOG_TAG = User.class.getSimpleName();

    private String id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;

    private List<RunningTrack> runningTracks;

    private User(){
        super();
    }

    private User(String firstName, String lastName, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
    }

    static public User getCurrentUser(){
        if(currentUser == null){
            currentUser = new User();
        }

        return currentUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public static void logout(){
        if(currentUser.isConnected() == false)
            throw new IllegalStateException("User is disconnected");

        currentUser = null;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword(){
        return password;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void addRunningTrack(RunningTrack run){
        this.runningTracks.add(run);
    }

    public RunningTrack getRunningTrackByIndex(int i){
        if(runningTracks.size() <= i || i<0)
            return null;
        return runningTracks.get(i);
    }

    @Override
    public String toString(){
        return String.format("User { id: %s, login: %s,  firstName: %s, lastName: %s }",
                id, login, firstName, lastName);
    }

    @Override
    public void onFindAllListener(List<RunningTrack> runningTracks) {
        if(currentUser == null)
            throw new IllegalStateException("Current user in null");

        Log.d(LOG_TAG, "onFindAllListener call, list size : " + runningTracks.size());
        currentUser.runningTracks = new ArrayList<>();
        for(RunningTrack r: runningTracks) {
            if (r.getPositions().size() > 1)
                currentUser.runningTracks.add(r);
            else
                Log.e(LOG_TAG, "empty run");
        }
    }

    public List<RunningTrack> getRunningTracks() {
        return runningTracks;
    }

    public boolean isConnected(){
        return this.getId() != null;
    }

    @Override
    public void onAddListener(RunningTrack runningTrack) {
        if(currentUser == null)
            throw new IllegalStateException("Current user in null");

        if(currentUser.runningTracks != null) {
            if(runningTrack.getPositions().size() > 1)
                currentUser.runningTracks.add(runningTrack);
        }
    }
}
