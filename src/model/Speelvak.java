package model;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class Speelvak {

    private Kleur kleur;
    private final int rij;
    private final int kolom;

    public Speelvak(int rij, int kolom) {
        this.rij = rij;
        this.kolom = kolom;
        this.kleur = Kleur.LEEG;
    }

    public void setKleur(Kleur kleur) {
        this.kleur = kleur;
    }

    public Kleur getKleur() {
        return kleur;
    }
}
