package model;

import ai.Zet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class Bord {
    private int grootteBord = 8;
    private int nodeDiepte = 0;
    protected Speelvak[][] speelvakken ;


    public Bord() {
        speelvakken = new Speelvak[grootteBord][grootteBord];

        for (int i = 0; i < grootteBord; i++) {
            for (int j = 0; j < grootteBord; j++) {
                speelvakken[i][j] = new Speelvak(i, j);
            }
        }

        initBord();
    }

    public Bord(Bord oudBord) {
        this();
        for (int i = 0; i < grootteBord; i++) {
            for (int j = 0; j < grootteBord; j++) {
                speelvakken[i][j].setKleur(oudBord.speelvakken[i][j].getKleur());
            }
        }
        this.nodeDiepte = oudBord.nodeDiepte + 1;
    }

    public int getNodeDiepte() {
        return nodeDiepte;
    }

    private void initBord() {
        //De 4 eerste zetten die er altijd moeten zijn
        speelvakken[(grootteBord/2)-1][(grootteBord / 2) -1].setKleur(Kleur.WIT);
        speelvakken[(grootteBord/2)][(grootteBord / 2)].setKleur(Kleur.WIT);
        speelvakken[(grootteBord/2)][(grootteBord / 2) -1].setKleur(Kleur.ZWART);
        speelvakken[(grootteBord/2)-1][(grootteBord / 2)].setKleur(Kleur.ZWART);
    }

    public Kleur getKleurOpPositie(int rij, int kolom) {
        return speelvakken[rij][kolom].getKleur();
    }

    public int getScore(Kleur kleur) {
        int score = 0;
        for (int rij = 0; rij < grootteBord; rij++) {
            for (int kolom = 0; kolom < grootteBord; kolom++) {
                if (speelvakken[rij][kolom].getKleur() == kleur) {
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
        Speelvak speelvak = speelvakken[rij][kolom];
        if (!isGeldigeZet(rij, kolom, kleur)) {
            throw new OngeldigeZet("Ongeldige zet");
        }

        speelvak.setKleur(kleur);
        wijzigGeflankteSpelvakken(rij, kolom, kleur);
    }

    private void wijzigSpelVakkenInRichting(int rij, int kolom, Richting richting, Kleur kleurDieMoetGewijzigdWorden) {
        Kleur nieuweKleur = Kleur.andereKleur(kleurDieMoetGewijzigdWorden);

        Speelvak speelvak = speelvakken[rij][kolom];
        if (speelvak.getKleur() == kleurDieMoetGewijzigdWorden) {
            speelvak.setKleur(nieuweKleur);
            wijzigSpelVakkenInRichting(rij + richting.y, kolom + richting.x, richting, kleurDieMoetGewijzigdWorden);
        }
    }

    private void wijzigGeflankteSpelvakken(int rij, int kolom, Kleur kleur) {
        Kleur vijandigeKleur = Kleur.andereKleur(kleur);

        //controleer of er een veld van de andere kleur aangrenst
        List<Richting> mogelijkeRichtingen = Richting.getMogelijkeRichtingen();
        for(Richting richting: mogelijkeRichtingen){
            if (controleerOfPositieVijandigeKleurBevat(rij + richting.y, kolom + richting.x, vijandigeKleur)) {
                if (controleerOfRichtingEindigtOpAndereKleur(rij + richting.y, kolom + richting.x, richting, vijandigeKleur)) {
                    wijzigSpelVakkenInRichting(rij + richting.y, kolom + richting.x, richting, vijandigeKleur);
                }
            }
        }

    }

    private boolean controleerOfRichtingEindigtOpAndereKleur(int rij, int kolom, Richting richting, Kleur andereKleur) {
        Kleur kleur = Kleur.andereKleur(andereKleur);

        try {
            Speelvak speelvak = speelvakken[rij + richting.y][kolom + richting.x];

            if (speelvak.getKleur() == kleur) {
                return true;
            } else if (speelvak.getKleur() == andereKleur) {
                return controleerOfRichtingEindigtOpAndereKleur(rij + richting.y, kolom + richting.x, richting, andereKleur);
            } else {
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }

    }

    public boolean isGeldigeZet(int rij, int kolom, Kleur kleur) {
        Speelvak speelvak = speelvakken[rij][kolom];
        //controleer of positie wel leeg is
        if (speelvak.getKleur() != Kleur.LEEG) {
            return false;
        }

        Kleur vijandigeKleur = Kleur.andereKleur(kleur);

        //controleer of er een veld van de andere kleur aangrenst
        List<Richting> mogelijkeRichtingen = Richting.getMogelijkeRichtingen();

        for(Richting richting: mogelijkeRichtingen){
            if (controleerOfPositieVijandigeKleurBevat(rij + richting.y, kolom + richting.x, vijandigeKleur)) {
                if(controleerOfRichtingEindigtOpAndereKleur(rij + richting.y, kolom + richting.x, richting, vijandigeKleur)){
                    return true;
                }

            }
        }
        return false;
    }

    private boolean controleerOfPositieVijandigeKleurBevat(int rij, int kolom, Kleur vijandigeKleur) {
        try {
            Speelvak speelvak = speelvakken[rij][kolom];
            return speelvak.getKleur() == vijandigeKleur;
        } catch (ArrayIndexOutOfBoundsException e) {
            //positie bestaat niet
            return false;
        }

    }

    public int getGrootteBord() {
        return grootteBord;
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

    public String geefBordMetHints(Kleur kleur) {
        String hetBord = ("\n      +-----+-----+-----+-----+-----+-----+-----+-----+\n");
        for (int rij = 0; rij < grootteBord; rij++) {
            hetBord += String.format("Rij %d | ", rij + 1);
            for (int kolom = 0; kolom < grootteBord; kolom++) {

                Speelvak speelvak = speelvakken[rij][kolom];
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
