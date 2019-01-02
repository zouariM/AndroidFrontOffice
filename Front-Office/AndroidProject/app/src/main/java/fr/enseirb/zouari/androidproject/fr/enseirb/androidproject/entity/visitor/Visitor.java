package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.visitor;

import java.util.List;

import fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.RunningTrack;

/**
 * Le contrat d'un visiteur
 */
public interface Visitor {

    public void visit(RunningTrack elt);
    public void visit(List<RunningTrack> elts);
}
