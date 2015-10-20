package ai.computer.impl;

import ai.Zet;
import ai.computer.ObservableAI;
import ai.computer.api.Computer;
import ai.heuristic.api.HeuristicCalculator;
import ai.heuristic.impl.CompleteHeuristicCalculator;
import model.Bord;
import model.Kleur;

/**
 * Created by JoachimDs on 13/10/2015.
 * <p>
 * Computer is zwart en speelt als tweede.
 * Max heeft als kleur zwart
 */
public class NewMiniMaxComputer  extends Computer {
    private Zet ultiemeZet;
    private int aantalStappen;
    private HeuristicCalculator calculator;
    private double ultiemeHeuristicValue;

    public NewMiniMaxComputer(Kleur kleur) {
        super(kleur);
        ultiemeHeuristicValue = Double.NEGATIVE_INFINITY;
    }

    @Override
    public Zet berekenZet(Bord bord) {
        miniMax(bord, Kleur.ZWART);
        return ultiemeZet;
    }



    private double miniMax(Bord bord, Kleur kleurAanZet) {
        double besteWaarde;
        Kleur volgendeKleurAanZet = Kleur.andereKleur(kleurAanZet);

        if (aantalStappen == 0) {
            besteWaarde = calculator.getHeuristicValue(bord, kleurAanZet);
        } else if (kleurAanZet == Kleur.WIT) {
            besteWaarde = Double.POSITIVE_INFINITY;
            for (Zet zet : bord.geefGeldigeZetten(kleurAanZet)){
                Bord duplicaat = new Bord(bord);
                aantalStappen--;
                duplicaat.zetPion(zet, kleurAanZet);
                besteWaarde = Math.min(miniMax(duplicaat, volgendeKleurAanZet), besteWaarde);

                duplicaat.setUserObject(" " + Math.round(besteWaarde) + zet);
                bord.add(duplicaat);
            }
        } else {
            besteWaarde = Double.NEGATIVE_INFINITY;
            for (Zet zet : bord.geefGeldigeZetten(kleurAanZet)){
                Bord duplicaat = new Bord(bord);
                aantalStappen--;
                duplicaat.zetPion(zet, kleurAanZet);
                besteWaarde = Math.max(miniMax(duplicaat, volgendeKleurAanZet), besteWaarde);
                if (aantalStappen == 4 && ultiemeHeuristicValue < besteWaarde){
                    ultiemeZet = zet;
                    ultiemeHeuristicValue = besteWaarde;
                }
                duplicaat.setUserObject(" " + Math.round(besteWaarde) + zet);
                bord.add(duplicaat);
            }
        }
        aantalStappen++;
        return besteWaarde;
    }

}
