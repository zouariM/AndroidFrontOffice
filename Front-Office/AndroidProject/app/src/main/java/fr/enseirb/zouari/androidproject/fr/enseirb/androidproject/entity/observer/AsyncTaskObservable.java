package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.observer;

import android.os.AsyncTask;
import android.util.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Repository Observable
 * @param <T> Classe générique pour les Repository
 * Le patron de design Observer est implémenté
 * Extends AsyncTask comme Java interdit l'héritage multiple
 * Sinon, RepositoryModel extends AsyncTask, Observable<Model>
 */
public abstract class AsyncTaskObservable<T> extends AsyncTask<Object, Integer, StringBuffer> {

    // La liste des observateurs est de type set pour éviter les duplications
    private Set<ModelObserver<T>> observers;
    private static final String LOG_TAG = AsyncTaskObservable.class.getSimpleName();

    public AsyncTaskObservable(){
        observers = new HashSet<>();
    }

    // Ajouter un observateur === inscription
    public boolean addObserver(ModelObserver<T> observer){
        boolean res = observers.add(observer);
        Log.d(LOG_TAG, "add of observer => " + res);

        return res;
    }

    // Suppression d'un observateurs
    public boolean removeObserver(ModelObserver<T> observer){
        return observers.remove(observer);
    }

    // Notification de la récuppération de model T
    public void notifyOnFindAll(List<T> l){
        for(ModelObserver o:observers)
            o.onFindAllListener(l);
    }

    // Notification de l'ajout de model T
    public void notifyOnAdd(T elt){
        for(ModelObserver o:observers)
            o.onAddListener(elt);
    }

    // Méthode abstraite héritée par AsyncTask
    @Override
    abstract protected void onPostExecute(StringBuffer stringBuffer);
}
