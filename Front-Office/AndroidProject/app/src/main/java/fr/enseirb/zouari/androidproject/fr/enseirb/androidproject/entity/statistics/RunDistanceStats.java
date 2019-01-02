package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics;

import android.location.Location;
import android.util.Log;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.visitor.Visitor;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.Position;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;

/**
 * Les statistiques de distance pour un parcours et une liste de parcorus
 * Le patron de design Visitor est implémentée
 */
public class RunDistanceStats implements Visitor {

    private NumberFormat distanceFormat;
    private float distance;
    private Float maxDistance;
    private float sumDistance;
    private Float minDistance;

    public RunDistanceStats(){
        super();
    }

    @Override
    public void visit(RunningTrack elt) {
        if(elt.getPositions().size() == 0)
            return;

        TreeSet<Position> positions = elt.getPositionsSorted();
        setDistance(positions);
    }

    private void setDistance(Collection<Position> positions){
        Iterator<Position> it = positions.iterator();
        Position p1 = (it.hasNext()) ? it.next() : null;
        Position p2;
        distance = 0f;

        while(it.hasNext()){
            p2 = it.next();

            Location l1 = p1.getLocationInstance();
            Location l2 = p2.getLocationInstance();
            distance += l1.distanceTo(l2);

            p1 = p2;
        }
    }

    @Override
    public void visit(List<RunningTrack> elts) {
        if(elts == null)
            return;

        sumDistance = 0f;
        RunDistanceStats visitor = new RunDistanceStats();

        for(RunningTrack run:elts){
            visitor.init();
            visitor.visit(run);
            setDistanceBounds(visitor.getDistance());
            sumDistance+= visitor.getDistance();
        }

    }

    public float getDistance() {
        return distance;
    }

    public float getMaxDistance() {
        return maxDistance;
    }

    public float getSumDistance() {
        return sumDistance;
    }

    public float getMinDistance() {
        return minDistance;
    }

    public String getDistanceFormated(){
        return distanceFormat.format(distance);
    }

    public String getMaxDistanceFromated(){
        Log.d(this.getClass().getSimpleName(), "maxDistance = " + maxDistance);
        return distanceFormat.format(maxDistance);
    }

    public String getSumDistanceFormated(){
        return distanceFormat.format(sumDistance);
    }

    public String getMinDistanceFormated(){
        return distanceFormat.format(minDistance);
    }

    public void init(){
        distanceFormat = NumberFormat.getInstance();
        distanceFormat.setMaximumFractionDigits(2);
    }

    private void setDistanceBounds(float distance){
        maxDistance = (maxDistance == null) ? distance : Math.max(maxDistance, distance);
        minDistance = (minDistance == null) ? distance : Math.min(minDistance, distance);
    }
}
