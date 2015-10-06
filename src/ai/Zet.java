package ai;

/**
 * Created by jorandeboever
 * on 6/10/15.
 */
public class Zet {
    private int rij;
    private int kolom;
    private double heuristicValue;

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

    public void setKolom(int kolom) {
        this.kolom = kolom;
    }

    public int getKolom() {

        return kolom;
    }

    public double getHeuristicValue() {
        return heuristicValue;
    }

    public void setHeuristicValue(double heuristicValue) {
        this.heuristicValue = heuristicValue;
    }
}
