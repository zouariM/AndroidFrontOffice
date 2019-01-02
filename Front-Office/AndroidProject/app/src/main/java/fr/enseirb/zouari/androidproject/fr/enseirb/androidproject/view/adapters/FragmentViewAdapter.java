package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentViewAdapter
 */
public class FragmentViewAdapter extends FragmentStatePagerAdapter {

    // Les fragments à manipuler (afficher)
    private final List<Fragment> fragments = new ArrayList<>();
    // Identifier un run par sa position (voir RunRouteFragment et RunStatsFragment)
    public final static  String POSITION_KEY = "position";

    public FragmentViewAdapter(FragmentManager fm) {
        super(fm);
    }

    // Réccupérer un fragment
    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    // Ajouter un fragment
    public void addFragment(Fragment fragment){
        fragments.add(fragment);
    }

    // retourner le nombre de fragments
    @Override
    public int getCount() {
        return fragments.size();
    }
}
