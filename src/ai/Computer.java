package ai;

import model.Bord;
import model.Kleur;
import model.Zet;

import java.util.List;

/**
 * Created by jorandeboever
 * on 29/09/15.
 */
public class Computer {
    private Kleur kleur;


    public Zet doeZet(Bord bord) {
        //bereken andere kleur voor recursie
        Kleur andereKleur;
        if (kleur == Kleur.WIT) {
            andereKleur = Kleur.ZWART;
        } else {
            andereKleur = Kleur.WIT;
        }

        // kijk of het spel gedaan is
        if (!bord.zijnErGeldigeZetten()) {
            if(bord.getScore(kleur) > bord.getScore(andereKleur)){
                bord.verhoogAantalKerenKleurWint(kleur);
            }else {
                bord.verhoogAantalKerenKleurWint(andereKleur);
            }
            return null;
        } else {
            int huidigeZetScore = -99999999;
            Zet besteZet = new Zet(1, 1);
            //loop alle mogelijke zetten af
            List<Zet> mogelijkeZetten = bord.geefGeldigeZetten(kleur);
            if (mogelijkeZetten.size() == 0) {
                //Er zijn geen zetten meer
                Bord duplicaat = new Bord(bord);
                Computer min = new Computer(andereKleur);
                min.doeZet(duplicaat);
            } else {
                for (Zet zet : mogelijkeZetten) {
                    Bord duplicaat = new Bord(bord);
                    duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                    Computer min = new Computer(andereKleur);
                    min.doeZet(duplicaat);
                    int zetScore = duplicaat.getAantalKerenKleurWint(kleur) - duplicaat.getAantalKerenKleurWint(andereKleur);
                    if (zetScore > huidigeZetScore) {
                        besteZet = zet;
                        huidigeZetScore = duplicaat.getAantalKerenKleurWint(kleur) - duplicaat.getAantalKerenKleurWint(andereKleur);
                    }
                }

                return besteZet;
            }
            return null;
        }
    }

    public Kleur getKleur() {
        return kleur;
    }

    public void setKleur(Kleur kleur) {
        this.kleur = kleur;
    }

    public Computer(Kleur kleur) {
        this.kleur = kleur;
    }
}
