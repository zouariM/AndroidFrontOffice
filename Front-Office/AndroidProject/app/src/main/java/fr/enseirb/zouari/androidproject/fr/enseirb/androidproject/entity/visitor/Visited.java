package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.visitor;

/**
 * Le contrat de la classe acceptrice
 */
public interface Visited {

    public void accept(Visitor v);
}
