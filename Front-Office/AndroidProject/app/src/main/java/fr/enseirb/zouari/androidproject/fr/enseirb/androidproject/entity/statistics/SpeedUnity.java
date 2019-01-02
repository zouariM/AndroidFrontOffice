package fr.enseirb.zouari.androidproject.fr.enseirb.androidproject.entity.statistics;

/**
 * Enumération pour les unités de vitesse
 */
public enum SpeedUnity {
    KM_H("Km/h"),
    M_S("m/s");

    private String unity;

    private SpeedUnity(String unity){
        this.unity = unity;
    }

    @Override
    public String toString() {
        return unity;
    }
}

