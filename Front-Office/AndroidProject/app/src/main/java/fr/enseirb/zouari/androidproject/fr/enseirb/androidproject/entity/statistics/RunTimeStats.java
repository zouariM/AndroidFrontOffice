package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.visitor.Visitor;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.Position;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;

/**
 *  Les statistiques de temps pour un parcours et une liste de parcorus
 *  Le patron de design Visitor est implémentée
 */
public class RunTimeStats implements Visitor {

    private static final String LOG_TAG = RunTimeStats.class.getSimpleName();

    private SimpleDateFormat dateFormat;
    private long startTime;
    private long finishTime;
    private long duration;
    private Long maxDuration;
    private Long minDuration;
    private Long sumDuration;

    public RunTimeStats(){
        super();
    }

    public void init(){
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        this.sumDuration = null;
        this.maxDuration = null;
        this.minDuration = null;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }


    @Override
    public void visit(RunningTrack elt) {
        if (elt == null || elt.getPositions() == null ||  elt.getPositions().size() == 0)
            return;

        Comparator<Position> comparator = (p1, p2)->
                (p1.getTime() > p2.getTime()) ? 1 : ((p1.getTime() == p2.getTime()) ? 0 : -1);

        TreeSet<Position> positions = new TreeSet<>(comparator);
        positions.addAll(elt.getPositions());

        setTimes(positions);
        setDuration();
    }

    @Override
    public void visit(List<RunningTrack> elts) {
        if(elts == null)
            return;

        sumDuration = 0l;
        RunTimeStats visitor = new RunTimeStats();
        visitor.init();

        for(RunningTrack run:elts){
            visitor.init();
            visitor.visit(run);

            Long duration = visitor.getDuration();
            setDurationsBounds(duration);

            sumDuration+=duration;
        }
    }

    public double getStartTime() {
        return startTime;
    }

    public String getSartTimeFormated(){
        return dateFormat.format(startTime);
    }

    private void setTimes(TreeSet<Position> positions) {
        startTime = positions.first().getTime();
        finishTime = positions.last().getTime();
    }

    public double getFinishTime() {
        return finishTime;
    }

    public String getFinishTimeFormated(){
        return dateFormat.format(finishTime);
    }

    public Long getDuration() {
        return duration;
    }

    private void setDuration() {

        duration = finishTime - startTime;
    }

    private String getDurationFormated(Long duration){
        StringBuilder builder = new StringBuilder(":");

        long second = duration/1000%60;
        long minutes = duration/(1000*60)%60;
        long hours = duration/(1000*3600);

        return  String.format("%s:%s:%s", hours, minutes, second);
    }

    public String getDurationFormated(){
        return getDurationFormated(duration);
    }

    public double getMaxDuration() {
        return maxDuration;
    }

    public String getMaxDurationFormatted(){
        return getDurationFormated(maxDuration);
    }

    private void setDurationsBounds(Long duration) {
        maxDuration = (maxDuration != null) ? (Math.max(maxDuration, duration)) : duration;
        minDuration = (minDuration != null) ? (Math.min(minDuration, duration)) : duration;
    }

    public double getMinDuration() {
        return minDuration;
    }

    public String getMinDurationFormatted(){
        return getDurationFormated(minDuration);
    }

    public long getSumDuration() {
        return sumDuration;
    }

    public String getSumDurationFormatted(){
        return getDurationFormated(sumDuration);
    }

}
