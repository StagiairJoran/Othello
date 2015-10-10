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
    private Computer computer;

    public Spel() {
        this.bord = new Bord();
        this.kleurAanDeBeurt = Kleur.WIT;
        this.computer = new MiniMaxComputer(Kleur.ZWART);
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

    public void doeComputerZet(){
        Zet computerZet = computer.berekenZet(bord);
        this.zetPion(computerZet.getRij(), computerZet.getKolom());
        System.out.println("Computer doet zet naar: rij=" + computerZet.getRij() + ", kolom=" + computerZet.getKolom());

    }

    public void setComputer(Computer computer) {
        this.computer = computer;
        System.out.println(computer);
    }

    public Bord getBord() {
        return bord;
    }

    public boolean isSpelGedaan() {
        return isSpelGedaan;
    }
}
