package model;

/**
 * Created by jorandeboever
 * on 29/09/15.
 */
public class Zet {
    private int rij;
    private int kolom;



    public Zet(int rij, int kolom) {
        this.rij = rij;
        this.kolom = kolom;
    }

    public int getRij() {
        return rij;
    }

    public void setRij(int rij) {
        this.rij = rij;
    }

    public int getKolom() {
        return kolom;
    }

    public void setKolom(int kolom) {
        this.kolom = kolom;
    }
}
