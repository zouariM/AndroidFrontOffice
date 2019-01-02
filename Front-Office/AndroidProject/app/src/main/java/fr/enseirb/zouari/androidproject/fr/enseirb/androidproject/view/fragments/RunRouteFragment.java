package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.fragments;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import java.util.List;
import fr.enseirb.zouari.androidproject.R;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics.SpeedUnity;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.presenter.RunRoutePresenter;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.view.adapters.FragmentViewAdapter;

/**
 * View affichage d'un parcours donnée sur le Map
 * Le parcours est dessiné par une ligne entre la position de départ -> position finale
 */
public class RunRouteFragment extends Fragment implements OnMapReadyCallback, RunRoutePresenter.View {

    private static final String LOG_TAG = RunRouteFragment.class.getSimpleName();

    // Adaptateur
    private MapView mapView;
    // Map
    private GoogleMap map;
    // Liste de points(View) == positions(Model)
    private List<LatLng> points;
    // Presenter
    private RunRoutePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        presenter = new RunRoutePresenter(this);

        // Initialisation
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return view;
    }

    private void setRun(){
        // Par défaut, on présente le premier parcours
        points = presenter.getRunningTrackByIndex(0);
    }

    // Cycle de vie Fragment
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    // Optimisation
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // Réccupération de l'index de parcours (envoyé par l'activité courante)
    @Override
    public void setArguments(@Nullable Bundle args) {
        int index = args.getInt(FragmentViewAdapter.POSITION_KEY);
        Log.d(LOG_TAG, "setArgument Fragment for position : " + index +" map is ready = " + !(map == null));
        points = presenter.getRunningTrackByIndex(index);
        addRoute();

        super.setArguments(args);
    }

    // Visualisation de la route
    private void addRoute(){
        map.clear();
        Log.d(LOG_TAG," addRoute for points nbr: " + points.size());
        if(points.size() < 2)
            return;

        // Construction de la ligne
        addPolyline(points);
        // AJout de Marker
        addMarkers(points.get(0), points.get(points.size() - 1));
        // Adaptation de caméra
        moveCamera(points.get(0), 17.0f);
    }

    // Réccupération de Map
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i(LOG_TAG, "onMapReady");
        map = googleMap;

        if(points != null)
            addRoute();

    }

    // Positionner Caméra
    private void moveCamera(LatLng point, float zoom){
        map.moveCamera(CameraUpdateFactory.newLatLng(point));
        map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    // Construction de la ligne

    /**
     *
     * @param points Liste de points constutant le parcours
     * TODO améliorer la précision de la ligne par API Direction de Google
     * La consistence de cette méthode dépend l'intervalle entre les différents positions
     * Plus l'intervalle est réduit, plus la ligne est précise
     */
    private void addPolyline(List<LatLng> points){
        PolylineOptions polylineOptions = new PolylineOptions();;
        polylineOptions.addAll(points);
        polylineOptions.width(10.0f);
        polylineOptions.color(Color.GREEN);
        map.addPolyline(polylineOptions);
    }

    // Ajout de deux markers
    private void addMarkers(LatLng origin, LatLng dest){
        // Marker de point de départ
        MarkerOptions originMarkerOp = new MarkerOptions();
        originMarkerOp.position(origin);
        originMarkerOp.title("Départ");
        originMarkerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        // Marker de point destinataire
        MarkerOptions destMarkerOp = new MarkerOptions();
        destMarkerOp.position(dest);
        destMarkerOp.title("Destination");
        destMarkerOp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        // Réccupération de stats
        SpeedUnity kmh = SpeedUnity.KM_H;
        String stats[] = presenter.getSpeedStats(kmh);

        String originInfo = String.format("Vmax = %s %s ** Vmin = %s %s",
                stats[0], kmh,
                stats[2], kmh);
        String destInfo = String.format("Vmoy = %s %s",
                stats[1], kmh);

        // Affichage de vitessMax et VitesseMin dans window info de marker origine
        originMarkerOp.snippet(originInfo);
        // Affichage de vitesseMoy dans window de marker dist
        destMarkerOp.snippet(destInfo);
        // TODO développement d'une fenetre personnalisé (WindowAdapter) pour améliorer l'afficahge

        map.addMarker(originMarkerOp);
        map.addMarker(destMarkerOp);
    }

    // Option de retour à la liste
    // TODO ajout d'autres options tel que réccupération des adresses de parcours (départ + arrivé)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

}
