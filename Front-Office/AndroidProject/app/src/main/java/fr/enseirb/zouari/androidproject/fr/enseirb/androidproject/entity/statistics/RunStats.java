package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics;

import android.location.Location;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeSet;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.Position;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;

/**
 * Cette classe est obsolète aprés l'implémentation de Visitor pour les calculs des statistiques
 * TODO remove this class and update view
 */
public class RunStats {

    private SimpleDateFormat dateFormat;
    private TreeSet<Position> positions;
    private  NumberFormat distanceFormat;

    public RunStats(RunningTrack runningTrack){
        this.setPositions(runningTrack.getPositions());
        this.setDateFormat();
        this.setDistanceFormat();
    }

    public void setDateFormat(String pattern){
        this.dateFormat = new SimpleDateFormat(pattern);
    }

    private void setDateFormat(){
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    private void setPositions(Collection<Position> l){
        Comparator<Position>comparator = (p1,p2)->
            (p1.getTime() > p2.getTime()) ? 1 : ((p1.getTime() == p2.getTime()) ? 0 : -1)
        ;

        positions = new TreeSet<>(comparator);
        positions.addAll(l);
    }

    public long getDuration(){
        if(positions.size() == 0)
            return 0l;

        long duration = positions.last().getTime() - positions.first().getTime();
        return duration;
    }

    public String getDurationString(){
        long duration = getDuration();
        StringBuilder builder = new StringBuilder(":");

        long second = duration/1000%60;
        long minutes = duration/(1000*60)%60;
        long hours = duration/(1000*3600);

        return  String.format("%s h : %s m : %s s", hours, minutes, second);
    }

    public Long getStartTime(){
        if(positions.size() == 0)
            return 0l;

        long startTime = positions.first().getTime();
        return  startTime;
    }

    public String getStartTimeString(){
        Date date = new Date(getStartTime());
        return dateFormat.format(date);
    }

    public String getFinsihTime(){
        if(positions.size() == 0)
            return "";

        long finishTime = positions.last().getTime();
        Date date = new Date(finishTime);

        return dateFormat.format(date);
    }

    public float getDistance(){
        Iterator<Position> it = positions.iterator();
        Position p1 = (it.hasNext()) ? it.next() : null;
        Position p2;
        float distance = 0f;

        while(it.hasNext()){
            p2 = it.next();
            distance += caluclateDistance(p1, p2);
            p1 = p2;
        }

        return distance;
    }


    public String getDistanceString(){
        return distanceFormat.format(getDistance());
    }

    private void setDistanceFormat(){
        distanceFormat = NumberFormat.getNumberInstance();
        distanceFormat.setMaximumFractionDigits(2);
    }

    private float caluclateDistance(Position p1, Position p2){
        Location l1 = p1.getLocationInstance();
        Location l2 = p2.getLocationInstance();

        return l1.distanceTo(l2);
    }

}
