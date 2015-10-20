package ai;

import java.security.SecureRandom;

/**
 * Created by jorandeboever
 * on 6/10/15.
 */
public class Zet {
    private int rij;
    private int kolom;
    private double Waarde;

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

    public double getWaarde() {
        return Waarde;
    }

    public void setWaarde(double waarde) {
        this.Waarde = waarde;
    }

    @Override
    public String toString() {
        return " " + Zet.zetRijOmNaarLetter(getRij() + 1)   + (getKolom() + 1);
    }

    public static char zetRijOmNaarLetter(int rij) {
        switch (rij) {
            case 1:
                return 'A';
            case 2:
                return 'B';
            case 3:
                return 'C';
            case 4:
                return 'D';
            case 5:
                return 'E';
            case 6:
                return 'F';
            case 7:
                return 'G';
            case 8:
                return 'H';
            default:
                return ' ';
        }
    }
}
