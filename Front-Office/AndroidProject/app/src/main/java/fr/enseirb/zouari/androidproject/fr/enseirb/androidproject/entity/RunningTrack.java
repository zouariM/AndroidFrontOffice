package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.visitor.Visited;
import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.visitor.Visitor;

/**
 * RunningTrack Model (parcours)
 */
public class RunningTrack implements Visited {
    // TODO remplacer par TreeSet
    /**
     * @see this.getPositionsSorted()
     */
    private List<Position> positions;
    private Long startTime;
    private Long finishTime;

    public RunningTrack(){}

    public void init(){
        startTime = System.currentTimeMillis();
        finishTime = null;
        positions = new ArrayList<>();
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void addPosition(Position p){
        this.positions.add(p);
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Long finishTime) {
        this.finishTime = finishTime;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }

    // Il est indispansable de trier la liste selon l'instant de localisation
    // Comme l'ordre de stockage peut etre aléatoire (meme si ce n'est pas le cas!)
    public TreeSet<Position> getPositionsSorted(){
        Comparator<Position> comparator = (p1, p2)->
                (p1.getTime() > p2.getTime()) ? 1 : ((p1.getTime() == p2.getTime()) ? 0 : -1);

        TreeSet<Position> positions = new TreeSet<>(comparator);
        positions.addAll(this.positions);
        return positions;
    }
}
