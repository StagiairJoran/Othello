package ai.computer.impl;

import ai.Zet;
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
public class NewMiniMaxComputer implements Computer {
    private Zet besteZet;
    private int aantalStappen;
    private HeuristicCalculator calculator;
    private double allerBestHeuristicValue;

    public NewMiniMaxComputer() {
        //besteZet = ;
        aantalStappen = 2;
        calculator = new CompleteHeuristicCalculator();
        allerBestHeuristicValue = Double.NEGATIVE_INFINITY;

    }

    @Override
    public Zet berekenZet(Bord bord) {
        miniMax(bord, Kleur.ZWART);
        return besteZet;
    }

    private double miniMax(Bord bord, Kleur kleurAanZet) {
        double bestHeuristicValue;
        Kleur volgendeKleurAanZet = Kleur.andereKleur(kleurAanZet);

        if (aantalStappen == 0) {
            bestHeuristicValue = calculator.getHeuristicValue(bord, kleurAanZet);
        } else if (kleurAanZet == Kleur.WIT) {
            bestHeuristicValue = Double.POSITIVE_INFINITY;
            for (int i=0; i<bord.geefGeldigeZetten(kleurAanZet).size(); i++){
                Bord tijdelijBord = new Bord(bord);
                aantalStappen--;
                tijdelijBord.zetPion(bord.geefGeldigeZetten(kleurAanZet).get(i), kleurAanZet);
                bestHeuristicValue = Math.min(miniMax(tijdelijBord, volgendeKleurAanZet), bestHeuristicValue);
            }
        } else {
            bestHeuristicValue = Double.NEGATIVE_INFINITY;
            for (int i=0; i<bord.geefGeldigeZetten(kleurAanZet).size(); i++){
                Bord tijdelijBord = new Bord(bord);
                aantalStappen--;
                tijdelijBord.zetPion(bord.geefGeldigeZetten(kleurAanZet).get(i), kleurAanZet);
                bestHeuristicValue = Math.max(miniMax(tijdelijBord, volgendeKleurAanZet), bestHeuristicValue);
                if (aantalStappen == 4 && allerBestHeuristicValue < bestHeuristicValue){
                    besteZet = bord.geefGeldigeZetten(kleurAanZet).get(i);
                    allerBestHeuristicValue = bestHeuristicValue;
                }
            }
        }
        aantalStappen++;
        return bestHeuristicValue;
    }

}
