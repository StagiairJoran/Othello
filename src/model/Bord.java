package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class Bord {
    private HashMap rijen;
    private int aantalKerenZwartWint;
    private int aantalKerenWitWint;
    private int grootteBord = 4;
    public static int aantalBorden = 0;

    public Bord() {
        rijen = new HashMap();
        for (int i = 0; i < grootteBord; i++) {
            HashMap rij = new HashMap();
            for (int j = 0; j < grootteBord; j++) {
                rij.put(j, new Speelvak());
            }
            rijen.put(i, rij);
        }

        getSpeelvak((grootteBord / 2) - 1, (grootteBord / 2) - 1).setKleur(Kleur.WIT);
        getSpeelvak((grootteBord / 2), (grootteBord / 2)).setKleur(Kleur.WIT);
        getSpeelvak((grootteBord / 2), (grootteBord / 2) - 1).setKleur(Kleur.ZWART);
        getSpeelvak((grootteBord / 2) - 1, (grootteBord / 2)).setKleur(Kleur.ZWART);

        aantalBorden++;
    }

    public Bord(Bord anderBord) {
        rijen = new HashMap();
        for (int i = 0; i < grootteBord; i++) {
            HashMap rij = new HashMap();
            for (int j = 0; j < grootteBord; j++) {
                rij.put(j, new Speelvak(anderBord.getSpeelvak(i, j).getKleur()));
            }
            rijen.put(i, rij);
        }
        aantalBorden++;
    }


    public Kleur getKleurOpPositie(int rij, int kolom) {
        return getSpeelvak(rij, kolom).getKleur();
    }

    public HashMap getRijen() {
        return rijen;
    }

    public int getAantalKerenKleurWint(Kleur kleur) {
        if (kleur == Kleur.WIT) {
            return aantalKerenWitWint;
        } else {
            return aantalKerenZwartWint;
        }
    }

    public void verhoogAantalKerenKleurWint(Kleur kleur) {
        if (kleur == Kleur.WIT) {
            aantalKerenWitWint++;
        } else {
            aantalKerenZwartWint++;
        }
    }

    public void setAantalKerenWitWint(int aantalKerenWitWint) {
        this.aantalKerenWitWint = aantalKerenWitWint;
    }

    public int getAantalKerenZwartWint() {
        return aantalKerenZwartWint;
    }

    public void setAantalKerenZwartWint(int aantalKerenZwartWint) {
        this.aantalKerenZwartWint = aantalKerenZwartWint;
    }

    public int getScore(Kleur kleur) {
        int score = 0;
        for (int rij = 0; rij < grootteBord; rij++) {
            for (int kolom = 0; kolom < grootteBord; kolom++) {
                if (getSpeelvak(rij, kolom).getKleur() == kleur) {
                    score++;
                }
            }
        }
        return score;
    }

    public boolean zijnErGeldigeZetten() {
        for (int rij = 0; rij < grootteBord; rij++) {
            for (int kolom = 0; kolom < grootteBord; kolom++) {
                if (isGeldigeZet(rij, kolom, Kleur.WIT)) {
                    return true;
                }
            }
        }
        for (int rij = 0; rij < grootteBord; rij++) {
            for (int kolom = 0; kolom < grootteBord; kolom++) {
                if (isGeldigeZet(rij, kolom, Kleur.ZWART)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean zijnErGeldigeZetten(Kleur kleur) {
        for (int rij = 0; rij < grootteBord; rij++) {
            for (int kolom = 0; kolom < grootteBord; kolom++) {
                if (isGeldigeZet(rij, kolom, kleur)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void zetPion(int rij, int kolom, Kleur kleur) throws OngeldigeZet {
        Speelvak speelvak = getSpeelvak(rij, kolom);
        if (!isGeldigeZet(rij, kolom, kleur)) {
            throw new OngeldigeZet("Ongeldige zet");
        }

        speelvak.setKleur(kleur);
        wijzigGeflankteSpelvakken(rij, kolom, kleur);
    }

    private void wijzigSpelVakkenInRichting(int rij, int kolom, Richting richting, Kleur kleurDieMoetGewijzigdWorden) {
        Kleur nieuweKleur;
        if (kleurDieMoetGewijzigdWorden == Kleur.WIT) {
            nieuweKleur = Kleur.ZWART;
        } else {
            nieuweKleur = Kleur.WIT;
        }
        Speelvak speelvak = getSpeelvak(rij, kolom);
        if (speelvak.getKleur() == kleurDieMoetGewijzigdWorden) {
            speelvak.setKleur(nieuweKleur);
            wijzigSpelVakkenInRichting(rij + richting.y, kolom + richting.x, richting, kleurDieMoetGewijzigdWorden);
        }
    }

    private void wijzigGeflankteSpelvakken(int rij, int kolom, Kleur kleur) {
        Kleur vijandigeKleur;
        if (kleur == Kleur.WIT) {
            vijandigeKleur = Kleur.ZWART;
        } else {
            vijandigeKleur = Kleur.WIT;
        }

        //controleer of er een veld van de andere kleur aangrenst

        List<Richting> mogelijkeRichtingen = Richting.getMogelijkeRichtingen();
        for (Richting richting : mogelijkeRichtingen) {
            if (controleerOfPositieVijandigeKleurBevat(rij + richting.y, kolom + richting.x, vijandigeKleur)) {
                if (controleerOfRichtingEindigtOpAndereKleur(rij + richting.y, kolom + richting.x, richting, vijandigeKleur)) {
                    wijzigSpelVakkenInRichting(rij + richting.y, kolom + richting.x, richting, vijandigeKleur);
                }

            }
        }

    }

    private Speelvak getSpeelvak(int rij, int kolom) {
        HashMap row = (HashMap) rijen.get(rij);
        return (Speelvak) row.get(kolom);
    }

    private boolean controleerOfRichtingEindigtOpAndereKleur(int rij, int kolom, Richting richting, Kleur andereKleur) {
        Kleur kleur;
        if (andereKleur == Kleur.WIT) {
            kleur = Kleur.ZWART;
        } else {
            kleur = Kleur.WIT;
        }
        try {
            Speelvak speelvak = this.getSpeelvak(rij + richting.y, kolom + richting.x);

            if (speelvak.getKleur() == kleur) {
                return true;
            } else if (speelvak.getKleur() == andereKleur) {
                return controleerOfRichtingEindigtOpAndereKleur(rij + richting.y, kolom + richting.x, richting, andereKleur);
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }

    }

    public boolean isGeldigeZet(int rij, int kolom, Kleur kleur) {
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
        List<Richting> mogelijkeRichtingen = Richting.getMogelijkeRichtingen();

        for (Richting richting : mogelijkeRichtingen) {
            if (controleerOfPositieVijandigeKleurBevat(rij + richting.y, kolom + richting.x, vijandigeKleur)) {
                if (controleerOfRichtingEindigtOpAndereKleur(rij + richting.y, kolom + richting.x, richting, vijandigeKleur)) {
                    return true;
                }

            }
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

    public List<Zet> geefGeldigeZetten(Kleur kleur) {
        List<Zet> geldigeZetten = new ArrayList<>();
        for (int i = 0; i < getGrootteBord(); i++) {
            for (int j = 0; j < getGrootteBord(); j++) {
                if (this.isGeldigeZet(i, j, kleur)) {
                    geldigeZetten.add(new Zet(i, j));
                }
            }
        }
        return geldigeZetten;
    }

    @Override
    public String toString() {
        String hetBord = ("\n      +-----+-----+-----+-----+-----+-----+-----+-----+\n");
        for (int rij = 0; rij < grootteBord; rij++) {
            hetBord += String.format("Rij %d | ", rij + 1);
            for (int kolom = 0; kolom < grootteBord; kolom++) {

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

    public int getGrootteBord() {
        return grootteBord;
    }

    public String geefBordMetHints(Kleur kleur) {
        String hetBord = ("\n      +-----+-----+-----+-----+-----+-----+-----+-----+\n");
        for (int rij = 0; rij < grootteBord; rij++) {
            hetBord += String.format("Rij %d | ", rij + 1);
            for (int kolom = 0; kolom < grootteBord; kolom++) {

                Speelvak speelvak = this.getSpeelvak(rij, kolom);
                switch (speelvak.getKleur()) {
                    case WIT:
                        hetBord += " W  | ";
                        break;
                    case ZWART:
                        hetBord += " Z  | ";
                        break;
                    default:
                        if (isGeldigeZet(rij, kolom, kleur)) {
                            hetBord += " .  | ";
                        } else {
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
