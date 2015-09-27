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

        getSpeelvak(3, 3).setKleur(Kleur.WIT);
        getSpeelvak(3, 4).setKleur(Kleur.ZWART);
        getSpeelvak(4, 3).setKleur(Kleur.ZWART);
        getSpeelvak(4, 4).setKleur(Kleur.WIT);

    }

    public void zetPion(int rij, int kolom, Kleur kleur) throws OngeldigeZet {
        Speelvak speelvak = getSpeelvak(rij, kolom);
        if (!isGeldigeZet(rij, kolom, kleur)) {
            throw new OngeldigeZet("Ongeldige zet");
        }

        speelvak.setKleur(kleur);
        wijzigGeflankteSpelvakken(rij, kolom, kleur);
    }

    private void wijzigSpelVakkenInRichting(int rij, int kolom, int rijRichting, int kolomRichting, Kleur kleurDieMoetGewijzigdWorden){
        Kleur nieuweKleur;
        if (kleurDieMoetGewijzigdWorden == Kleur.WIT) {
            nieuweKleur = Kleur.ZWART;
        } else {
            nieuweKleur = Kleur.WIT;
        }
        Speelvak speelvak = getSpeelvak(rij, kolom);
        if(speelvak.getKleur() == kleurDieMoetGewijzigdWorden){
            speelvak.setKleur(nieuweKleur);
            wijzigSpelVakkenInRichting(rij + rijRichting, kolom + kolomRichting, rijRichting, kolomRichting, kleurDieMoetGewijzigdWorden);
        }
    }

    private void wijzigGeflankteSpelvakken(int rij, int kolom, Kleur kleur){
        Kleur vijandigeKleur;
        if (kleur == Kleur.WIT) {
            vijandigeKleur = Kleur.ZWART;
        } else {
            vijandigeKleur = Kleur.WIT;
        }
        //controleer of er een veld van de andere kleur aangrenst
        if (controleerOfPositieVijandigeKleurBevat(rij, kolom - 1, vijandigeKleur)) {
            if(controleerOfRichtingEindigtOpAndereKleur(rij, kolom -1, 0, -1, vijandigeKleur)){
                wijzigSpelVakkenInRichting(rij, kolom -1, 0, -1, vijandigeKleur);
            }

        }
        if (controleerOfPositieVijandigeKleurBevat(rij, kolom + 1, vijandigeKleur)) {
            if( controleerOfRichtingEindigtOpAndereKleur(rij, kolom +1, 0, +1, vijandigeKleur)){
                wijzigSpelVakkenInRichting(rij, kolom +1, 0, +1, vijandigeKleur);
            }

        }
        if (controleerOfPositieVijandigeKleurBevat(rij - 1, kolom, vijandigeKleur)) {
           if(controleerOfRichtingEindigtOpAndereKleur(rij -1, kolom ,-1, 0, vijandigeKleur)){
               wijzigSpelVakkenInRichting(rij -1, kolom ,-1, 0, vijandigeKleur);
           }

        }
        if (controleerOfPositieVijandigeKleurBevat(rij + 1, kolom, vijandigeKleur)) {
           if(  controleerOfRichtingEindigtOpAndereKleur(rij +1, kolom ,+1, 0, vijandigeKleur)){
               wijzigSpelVakkenInRichting(rij +1, kolom ,+1, 0, vijandigeKleur);
           }

        }
        if (controleerOfPositieVijandigeKleurBevat(rij - 1, kolom - 1, vijandigeKleur)) {
            if( controleerOfRichtingEindigtOpAndereKleur(rij - 1, kolom -1, -1, -1, vijandigeKleur)){
                wijzigSpelVakkenInRichting(rij - 1, kolom -1, -1, -1, vijandigeKleur);
            }

        }
        if (controleerOfPositieVijandigeKleurBevat(rij + 1, kolom + 1, vijandigeKleur)) {
            if( controleerOfRichtingEindigtOpAndereKleur(rij +1, kolom +1,+1, +1, vijandigeKleur)){
                wijzigSpelVakkenInRichting(rij +1, kolom +1,+1, +1, vijandigeKleur);
            }

        }
        if (controleerOfPositieVijandigeKleurBevat(rij + 1, kolom - 1, vijandigeKleur)) {
            if( controleerOfRichtingEindigtOpAndereKleur(rij+1, kolom -1, +1, -1, vijandigeKleur)){
                wijzigSpelVakkenInRichting(rij+1, kolom -1, +1, -1, vijandigeKleur);
            }

        }
        if (controleerOfPositieVijandigeKleurBevat(rij - 1, kolom + 1, vijandigeKleur)) {
           if(  controleerOfRichtingEindigtOpAndereKleur(rij -1, kolom +1, -1, +1, vijandigeKleur)){
               wijzigSpelVakkenInRichting(rij -1, kolom +1, -1, +1, vijandigeKleur);
           }

        }
    }

    private Speelvak getSpeelvak(int rij, int kolom) {
        HashMap row = (HashMap) rijen.get(rij);
        return (Speelvak) row.get(kolom);
    }

    private boolean controleerOfRichtingEindigtOpAndereKleur(int rij, int kolom, int rijRichting, int kolomRichting, Kleur andereKleur){
        Kleur kleur;
        if(andereKleur == Kleur.WIT){
            kleur = Kleur.ZWART;
        }else {
            kleur = Kleur.WIT;
        }
        if(this.getSpeelvak(rij + rijRichting, kolom + kolomRichting).getKleur() == kleur){
            return true;
        }else if(this.getSpeelvak(rij + rijRichting, kolom + kolomRichting).getKleur() == andereKleur){
            return controleerOfRichtingEindigtOpAndereKleur(rij + rijRichting, kolom + kolomRichting, rijRichting, kolomRichting, andereKleur);
        }else {
            return false;
        }

    }

    private boolean isGeldigeZet(int rij, int kolom, Kleur kleur) {
        Speelvak speelvak = getSpeelvak(rij, kolom);
        //controleer of positie wel leeg is
        if (speelvak.getKleur() != Kleur.LEEG) {
            return false;
        }

        Kleur vijandigeKleur;
        if (kleur == Kleur.WIT) {
            vijandigeKleur = Kleur.ZWART;
        } else {
            vijandigeKleur = Kleur.WIT;
        }
        //controleer of er een veld van de andere kleur aangrenst
        if (controleerOfPositieVijandigeKleurBevat(rij, kolom - 1, vijandigeKleur)) {
           return controleerOfRichtingEindigtOpAndereKleur(rij, kolom -1, 0, -1, vijandigeKleur);

        }
        if (controleerOfPositieVijandigeKleurBevat(rij, kolom + 1, vijandigeKleur)) {
            return controleerOfRichtingEindigtOpAndereKleur(rij, kolom +1, 0, +1, vijandigeKleur);

        }
        if (controleerOfPositieVijandigeKleurBevat(rij - 1, kolom, vijandigeKleur)) {
            return controleerOfRichtingEindigtOpAndereKleur(rij -1, kolom ,-1, 0, vijandigeKleur);

        }
        if (controleerOfPositieVijandigeKleurBevat(rij + 1, kolom, vijandigeKleur)) {
            return controleerOfRichtingEindigtOpAndereKleur(rij +1, kolom ,+1, 0, vijandigeKleur);

        }
        if (controleerOfPositieVijandigeKleurBevat(rij - 1, kolom - 1, vijandigeKleur)) {
            return controleerOfRichtingEindigtOpAndereKleur(rij - 1, kolom -1, -1, -1, vijandigeKleur);

        }
        if (controleerOfPositieVijandigeKleurBevat(rij + 1, kolom + 1, vijandigeKleur)) {
            return controleerOfRichtingEindigtOpAndereKleur(rij +1, kolom +1,+1, +1, vijandigeKleur);

        }
        if (controleerOfPositieVijandigeKleurBevat(rij + 1, kolom - 1, vijandigeKleur)) {
            return controleerOfRichtingEindigtOpAndereKleur(rij+1, kolom -1, +1, -1, vijandigeKleur);

        }
        if (controleerOfPositieVijandigeKleurBevat(rij - 1, kolom + 1, vijandigeKleur)) {
            return controleerOfRichtingEindigtOpAndereKleur(rij -1, kolom +1, -1, +1, vijandigeKleur);

        }
        return false;

    }

    private boolean controleerOfPositieVijandigeKleurBevat(int rij, int kolom, Kleur vijandigeKleur) {
        try {
            Speelvak speelvak = getSpeelvak(rij, kolom);
            if (speelvak.getKleur() == vijandigeKleur) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            //positie bestaat niet
            return false;
        }

    }

    @Override
    public String toString() {
        String hetBord = ("\n      +-----+-----+-----+-----+-----+-----+-----+-----+\n");
        for (int rij = 0; rij < 8; rij++) {
            hetBord += String.format("Rij %d | ", rij + 1);
            for (int kolom = 0; kolom < 8; kolom++) {

                Speelvak speelvak = this.getSpeelvak(rij, kolom);
                switch (speelvak.getKleur()) {
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

    public String geefBordMetHints(Kleur kleur){
        String hetBord = ("\n      +-----+-----+-----+-----+-----+-----+-----+-----+\n");
        for (int rij = 0; rij < 8; rij++) {
            hetBord += String.format("Rij %d | ", rij + 1);
            for (int kolom = 0; kolom < 8; kolom++) {

                Speelvak speelvak = this.getSpeelvak(rij, kolom);
                switch (speelvak.getKleur()) {
                    case WIT:
                        hetBord += " W  | ";
                        break;
                    case ZWART:
                        hetBord += " Z  | ";
                        break;
                    default:
                        if(isGeldigeZet(rij, kolom, kleur)){
                            hetBord += " .  | ";
                        }else{
                            hetBord += "    | ";
                        }
                        break;

                }

            }
            hetBord += "\n      +-----+-----+-----+-----+-----+-----+-----+-----+\n";
        }
        hetBord += "\nKolom    A     B     C     D     E     F     G     H   ";
        return hetBord;
    }
}
