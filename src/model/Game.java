package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorandeboever
 * on 27/09/15.
 * Othello rules: http://www.site-constructor.com/othello/othellorules.html
 */
public class Game {
    private Bord bord;
    private Kleur kleurAanDeBeurt;

    public Game() {
        this.bord = new Bord();
        this.kleurAanDeBeurt = Kleur.ZWART;
    }

    public Kleur getKleurAanDeBeurt() {
        return kleurAanDeBeurt;
    }

    public void zetPion(int rij, int kolom) throws OngeldigeZet {
        bord.zetPion(rij, kolom, kleurAanDeBeurt);
        if(kleurAanDeBeurt == Kleur.WIT){
            kleurAanDeBeurt = Kleur.ZWART;
        }else {
            kleurAanDeBeurt = Kleur.WIT;
        }
    }

    public Bord getBord(){
        return bord;
    }


}
