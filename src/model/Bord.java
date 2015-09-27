package model;

import java.util.HashMap;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class Bord {
    private HashMap rijen = new HashMap();


    public Bord() {
        for (int i = 0; i < 8; i++) {
            HashMap rij = new HashMap();
            for (int j = 0; j < 8; j++) {
                rij.put(j, new Speelvak());
            }
            rijen.put(i, rij);
        }

        getSpeelvak(3,3).setKleur(Kleur.WIT);
        getSpeelvak(3,4).setKleur(Kleur.ZWART);
        getSpeelvak(4,3).setKleur(Kleur.ZWART);
        getSpeelvak(4,4).setKleur(Kleur.WIT);

    }

    public void zetPion(int rij, int kolom, Kleur kleur){
        Speelvak speelvak = getSpeelvak(rij, kolom);
        if(speelvak.getKleur() == Kleur.LEEG){
            speelvak.setKleur(kleur);
        }
    }

    private Speelvak getSpeelvak(int rij, int kolom) {
        HashMap row = (HashMap) rijen.get(rij);
        return (Speelvak) row.get(kolom);
    }

    @Override
    public String toString() {
        String hetBord = ("\n      +-----+-----+-----+-----+-----+-----+-----+-----+\n");
        for (int rij = 0; rij < 8; rij++) {
            hetBord += String.format("Rij %d | ", rij + 1);
            for (int kolom = 0; kolom < 8; kolom++) {

                Speelvak speelvak = this.getSpeelvak(rij, kolom);
                switch (speelvak.getKleur()){
                    case WIT:
                        hetBord += " W  | ";
                        break;
                    case ZWART:
                        hetBord += " Z  | ";
                        break;
                    default:
                        hetBord += "    | ";
                        break;

                }

            }
            hetBord += "\n      +-----+-----+-----+-----+-----+-----+-----+-----+\n";
        }
        hetBord += "\nKolom    A     B     C     D     E     F     G     H   ";
        return hetBord;
    }
}
