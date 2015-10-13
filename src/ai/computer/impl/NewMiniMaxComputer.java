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
    private int diepte;
    private HeuristicCalculator calculator;
    private double allerBestHeuristicValue;

    public NewMiniMaxComputer() {
        //besteZet = ;
        diepte = 4;
        calculator = new CompleteHeuristicCalculator();
    }

    @Override
    public Zet berekenZet(Bord bord) {
        return besteZet;
    }

    private double miniMax(Bord bord, Kleur kleurAanZet) {
        double bestHeuristicValue;
        Kleur volgendeKleurAanZet = Kleur.andereKleur(kleurAanZet);

        if (diepte == 0) {
            bestHeuristicValue = calculator.getHeuristicValue(bord, kleurAanZet);

            diepte++;
        } else if (kleurAanZet == Kleur.WIT) {
            bestHeuristicValue = Double.POSITIVE_INFINITY;
            for (int i=0; i<bord.geefGeldigeZetten(kleurAanZet).size(); i++){
                diepte--;
                Bord tijdelijBord = bord;
                tijdelijBord.zetPion(bord.geefGeldigeZetten(kleurAanZet).get(i), kleurAanZet);
                bestHeuristicValue = Math.max(miniMax(tijdelijBord, volgendeKleurAanZet), bestHeuristicValue);
            }
        } else {
            bestHeuristicValue = Double.NEGATIVE_INFINITY;
            for (int i=0; i<bord.geefGeldigeZetten(kleurAanZet).size(); i++){
                diepte--;
                Bord tijdelijBord = bord;
                tijdelijBord.zetPion(bord.geefGeldigeZetten(kleurAanZet).get(i), kleurAanZet);
                bestHeuristicValue = Math.min(miniMax(tijdelijBord, volgendeKleurAanZet), bestHeuristicValue);
                if (diepte == 4 && allerBestHeuristicValue < bestHeuristicValue){
                    besteZet = bord.geefGeldigeZetten(kleurAanZet).get(i);
                }
            }
        }

        return bestHeuristicValue;
    }

}

