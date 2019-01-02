package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.adapters.FragmentViewAdapter;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments.RunRouteFragment;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments.StatsListFragment;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments.TriStatsDialog;

/**
 * L'activité contenant les parcours
 * Contient deux fragments: Visualisation de stats + Visualisation sur Map pour chaque parcours
 */
public class RunStatsActivity extends AppCompatActivity{

    // Gestionnaire de fragments
    private ViewPager container;
    private FragmentViewAdapter fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_stats);

        setContainer();
    }

    private void setContainer(){
        container = findViewById(R.id.run_stats_container);
        fragmentAdapter = new FragmentViewAdapter(getSupportFragmentManager());
        // Fragment de statistiques
        fragmentAdapter.addFragment(new StatsListFragment());
        // Fragment de route
        fragmentAdapter.addFragment(new RunRouteFragment());

        container.setAdapter(fragmentAdapter);
        // Initialement le fragment de statistiques est affiché
        container.setCurrentItem(0);
    }

    // Afficher le framgent de Route suite à un clic sur un Item
    public void displayRouteFragment(int i){
        RunRouteFragment runRouteFragment = (RunRouteFragment)fragmentAdapter.getItem(1);
        Bundle bundle = new Bundle();
        bundle.putInt(FragmentViewAdapter.POSITION_KEY, i);
        runRouteFragment.setArguments(bundle);

        container.setCurrentItem(1);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(container.getCurrentItem() == 0)
            getMenuInflater().inflate(R.menu.menu_stats, menu);
        else
            getMenuInflater().inflate(R.menu.menu_map, menu);

        return  true;
    }

    // Réccupération de choix de Tri
    public void handle(int idCriteria){
        StatsListFragment fragment = (StatsListFragment) fragmentAdapter.getItem(0);
        fragment.handleTriCriteria(idCriteria);
    }

    private void dispalyStatsFragment(){
        container.setCurrentItem(0);
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.stats_options_tri:
                TriStatsDialog triDialog = new TriStatsDialog();
                triDialog.show(getFragmentManager(), "Trier par");

                return true;

            case R.id.stats_sort_inverse:
                StatsListFragment fragment = (StatsListFragment) fragmentAdapter.getItem(0);
                fragment.inverseList();

                return true;

            case R.id.map_back:
                this.dispalyStatsFragment();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
