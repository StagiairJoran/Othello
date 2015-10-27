package model;

import ai.computer.api.Computer;
import ai.Zet;
import ai.computer.impl.MiniMaxComputer;

/**
 * Created by jorandeboever
 * on 27/09/15.
 * Othello rules: http://www.site-constructor.com/othello/othellorules.html
 */
public class Spel {
    private Bord bord;
    private Kleur kleurAanDeBeurt;
    private boolean isSpelGedaan = false;

    public Spel() {
        this.bord = new Bord();
        this.kleurAanDeBeurt = Kleur.WIT;
    }

    public Kleur getWinnaar(){
        if(bord.getScore(Kleur.WIT) > bord.getScore(Kleur.ZWART)){
            return Kleur.WIT;
        }else if(bord.getScore(Kleur.WIT)< bord.getScore(Kleur.ZWART)){
            return Kleur.ZWART;
        }else {
            return Kleur.LEEG;
        }
    }

    public String getWinnaarTekst(){
        String winnaar = "";
        winnaar += "Wit heeft " + bord.getScore(Kleur.WIT) + " vakjes, ";
        winnaar += "Zwart heeft " + bord.getScore(Kleur.ZWART) + " vakjes. ";

        if(bord.getScore(Kleur.WIT) > bord.getScore(Kleur.ZWART)){
            winnaar += "\nDe winnaar is wit.";
        }else if(bord.getScore(Kleur.WIT)< bord.getScore(Kleur.ZWART)){
            winnaar += "\nDe winnaar is zwart.";
        }else {
            winnaar += "\nEr is geen winnaar.";
        }

        return winnaar;
    }

    public Kleur getKleurAanDeBeurt() {
        return kleurAanDeBeurt;
    }

    public int getScore(Kleur kleur) {
        return bord.getScore(kleur);
    }

    public void zetPion(int rij, int kolom) throws OngeldigeZet {
        bord.zetPion(rij, kolom, kleurAanDeBeurt);
        veranderSpeler();
        if (!bord.zijnErGeldigeZetten(kleurAanDeBeurt)) {
            veranderSpeler();
        }
        controleerOfSpelGedaanIs();
    }

    public void zetPion(Zet zet) throws OngeldigeZet {
        bord.zetPion(zet.getRij(), zet.getKolom(), kleurAanDeBeurt);
        veranderSpeler();
        if (!bord.zijnErGeldigeZetten(kleurAanDeBeurt)) {
            veranderSpeler();
        }
        controleerOfSpelGedaanIs();
    }

    private void controleerOfSpelGedaanIs() {
        if (!bord.zijnErGeldigeZetten()) {
            isSpelGedaan = true;
        }
    }

    private void veranderSpeler() {
        if (kleurAanDeBeurt == Kleur.WIT) {
            kleurAanDeBeurt = Kleur.ZWART;
        } else {
            kleurAanDeBeurt = Kleur.WIT;
        }
    }





    public Bord getBord() {
        return bord;
    }

    public boolean isSpelGedaan() {
        return isSpelGedaan;
    }
}
