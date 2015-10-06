package model;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class Speelvak {

    private Kleur kleur;

    public Speelvak() {

        this.kleur = Kleur.LEEG;
    }

    public Speelvak(Kleur kleur) {

        this.kleur = kleur;
    }
    public void setKleur(Kleur kleur) {
        this.kleur = kleur;
    }

    public Kleur getKleur() {
        return kleur;
    }
}
