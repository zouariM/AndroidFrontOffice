package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter.Constants;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter.MainPresenter;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments.FragmentViewAdapter;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments.GlobalStatsFragment;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments.LoginFragment;

/**
 * Main Activity
 * contient deux fragments : Login et GlobalStats
 * Un seul framgement est affiché selon l'état d'utilisateur
 * connecté ==> GlobalStats sinon Login
 */
public class MainActivity extends AppCompatActivity implements MainPresenter.View{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    // Mapping option, activity
    private Map<Integer, Intent> menuActivities;
    // Presenter
    private MainPresenter presenter;

    // Gestion de fragments
    private ViewPager container;
    private FragmentViewAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setup layout
        Log.i(this.getClass().getSimpleName(), "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE);
        presenter = new MainPresenter(this, preferences);

        //setup action bar
        setTitle(R.string.app_name);
        //setup menu
        setMenuActivities();

        //setup container as view pager
        setContainer();

        //setup user data
        presenter.setCurrentUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(presenter.isConnected())
            container.setCurrentItem(1);

    }

    private void setContainer(){
        container = findViewById(R.id.main_container);
        fragmentAdapter = new FragmentViewAdapter(getSupportFragmentManager());

        fragmentAdapter.addFragment(new LoginFragment());
        fragmentAdapter.addFragment(new GlobalStatsFragment());
        container.setAdapter(fragmentAdapter);

        container.setCurrentItem(0);
    }

    // Afficher GEneralStatsFragments
    public void displayGeneralStatsFragment(){
        container.setCurrentItem(1);
        ((GlobalStatsFragment)fragmentAdapter.getItem(1)).init();
        invalidateOptionsMenu();
    }

    // Afficher LoginFragment
    public void displayLoginFragment(){
        container.setCurrentItem(0);
        invalidateOptionsMenu();
    }

    // Mapping option, activity
    private void setMenuActivities(){
        menuActivities = new HashMap<>();
        menuActivities.put(R.id.action_add_run, new Intent(this, RunActivity.class));
        menuActivities.put(R.id.action_edit_settings, new Intent(this, SettingActivity.class));
        menuActivities.put(R.id.fetch_all_running_stats, new Intent(this, RunStatsActivity.class));
        menuActivities.put(R.id.add_user, new Intent(this, InscriptionActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(this.getClass().getSimpleName(), "onCreateOptionsMenu");

        if(presenter.isConnected() == false)
            getMenuInflater().inflate(R.menu.menu_disconnected, menu);
        else
            getMenuInflater().inflate(R.menu.menu_connected, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        intent = menuActivities.get(item.getItemId());

        if(intent != null){
            startActivity(intent);
            return true;
        }

        else if(item.getItemId() == R.id.connected_logout){
            presenter.logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        Log.i(this.getClass().getSimpleName(), "onDestroy");
        super.onDestroy();

    }

    // Méthode appelé par presenter suite à la déconnexion de user
    @Override
    public void onLogoutSuccess() {
        Toast.makeText(this, "Déconnexion", Toast.LENGTH_SHORT).show();
        invalidateOptionsMenu();
        displayLoginFragment();
    }
}