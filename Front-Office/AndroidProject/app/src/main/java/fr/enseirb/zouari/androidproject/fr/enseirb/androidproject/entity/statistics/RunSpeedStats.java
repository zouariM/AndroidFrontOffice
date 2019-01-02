package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics;

import android.util.Log;

import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.visitor.Visitor;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.Position;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;

/**
 * Les statistiques de vitesse pour un parcours et une liste de parcorus
 * Le patron de design Visitor est implémentée
 */
public class RunSpeedStats implements Visitor {

    private static final String LOG_TAG = RunSpeedStats.class.getSimpleName();

    private NumberFormat speedFormat;
    private Float moySpeed;
    private Float maxSpeed;
    private Float minSpeed;

    private Float globalMaxSpeed;
    private Float globalMinSpeed;
    // globalMoySpeed = Sum(distance)/Sum(duration)
    private Float globalMoySpeed;

    private RunDistanceStats distanceStats;
    private RunTimeStats runTimeStats;

    public RunSpeedStats() {
        super();
        Log.i(LOG_TAG, "RunSpeedStats instantiation");


    }

    public void init(){
        distanceStats = new RunDistanceStats();
        runTimeStats = new RunTimeStats();

        speedFormat = NumberFormat.getNumberInstance();
        speedFormat.setMaximumFractionDigits(2);

        maxSpeed = 0f;
        minSpeed = null;
        moySpeed = 0f;

        globalMaxSpeed = 0f;
        globalMinSpeed = null;
        globalMoySpeed = 0f;
    }

    public void setSpeedFormat(NumberFormat sppedFormat){
        this.speedFormat = sppedFormat;
    }

    @Override
    public void visit(RunningTrack elt) {
        TreeSet<Position> positions = elt.getPositionsSorted();

        setSpeedBounds(positions);
        setMoySpeed(elt);
    }

    @Override
    public void visit(List<RunningTrack> elts) {
        RunSpeedStats visitor = new RunSpeedStats();

        for(RunningTrack r:elts){
            visitor.init();
            visitor.visit(r);

            globalMaxSpeed = Math.max(globalMaxSpeed, visitor.getMaxSpeed());
            // mode test
            if(visitor.getMinSpeed() != null) {
                float speed = visitor.getMinSpeed();
                globalMinSpeed = (globalMinSpeed == null) ? speed : Math.min(globalMinSpeed, speed);
            }
        }

        distanceStats.init();
        runTimeStats.init();
        runTimeStats.visit(elts);
        distanceStats.visit(elts);

        globalMoySpeed = distanceStats.getSumDistance() / (runTimeStats.getSumDuration() / 1000f);
        Log.d(LOG_TAG, "distanceSum = " +distanceStats.getSumDistance() + " // durationSum = "  + runTimeStats.getSumDuration());
    }

    private void setSpeedBounds(TreeSet<Position> positions){
        maxSpeed = 0f;
        minSpeed = null;

        Iterator<Position> it = positions.iterator();
        while(it.hasNext()){
            Position p1 = it.next();
            if(it.hasNext()){
                Position p2 = it.next();
                long t = (p2.getTime() - p1.getTime()) / 1000l;
                float d = p1.getDistanceTo(p2);

                if(t > 0) {
                    float speed = d/t;
                    maxSpeed = Math.max(maxSpeed, speed);
                    minSpeed = (minSpeed == null) ? speed : Math.min(this.minSpeed, speed);
                }
                else
                    Log.e(LOG_TAG, "duration < 0 " + t);
            }
        }

        Log.d(LOG_TAG, "minSpeed = " + this.minSpeed);
    }

    private void setMoySpeed(RunningTrack runningTrack){
        distanceStats.init();
        distanceStats.visit(runningTrack);
        runTimeStats.init();
        runTimeStats.visit(runningTrack);

        long duration = runTimeStats.getDuration() / 1000l;
        if(duration > 0)
            moySpeed = distanceStats.getDistance() / duration;
        else
            moySpeed = 0f;
    }

    public float getMoySpeed(){
        return moySpeed;
    }

    public float getMaxSpeed(){
        return maxSpeed;
    }

    public Float getMinSpeed(){
        return minSpeed;
    }

    private String getSpeedFormated(float speed, SpeedUnity unity){
        switch (unity){
            case M_S:
                return speedFormat.format(speed);
            case KM_H:
                speed = speed * 3.6f;
                return speedFormat.format(speed);
            default:
                throw new IllegalArgumentException("Check your unity");
        }
    }

    public String getMoySpeedFormated(SpeedUnity unity){
        return getSpeedFormated(moySpeed, unity);
    }

    public String getMaxSpeedFormated(SpeedUnity unity){
        return getSpeedFormated(maxSpeed, unity);
    }

    public String getMinSpeedFormated(SpeedUnity unity){
        return getSpeedFormated(minSpeed, unity);
    }

    public String getGlobalMaxSpeed(SpeedUnity unity){
        return getSpeedFormated(globalMaxSpeed, unity);
    }

    public String getGlobalMinSpeed(SpeedUnity unity){
        return getSpeedFormated(globalMinSpeed, unity);
    }

    public String getGlobalMoySpeed(SpeedUnity unity){
        return getSpeedFormated(globalMoySpeed, unity);
    }
}
