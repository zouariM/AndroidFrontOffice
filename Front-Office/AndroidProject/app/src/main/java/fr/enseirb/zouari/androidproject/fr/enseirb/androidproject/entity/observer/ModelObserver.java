package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.observer;

import java.util.List;

/**
 * Contrat d'un observateur de T
 * @param <T> Classe générique de Repository
 */
public interface ModelObserver<T> {
    // Traitement de la notification de récupération de model T, envoyée par l'observable
    public default void onFindAllListener(List<T> l){};
    // Tratiement de la notification d'ajout d'un model T, envoyée par l'observable
    public default void onAddListener(T elt){};
}
