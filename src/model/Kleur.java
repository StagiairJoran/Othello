package model;

/**
 * Created by Jorandeboever
 * 29/09/2015.
 */
public enum Kleur {
    WIT, ZWART, LEEG;

    public static Kleur andereKleur(Kleur kleur){
        if (kleur == Kleur.WIT) {
            return Kleur.ZWART;
        } else {
            return Kleur.WIT;
        }
    }
}
