package ai.computer.impl;

import ai.Zet;
import ai.computer.api.Computer;
import ai.heuristic.api.HeuristicCalculator;
import model.Bord;
import model.Kleur;

/**
 * Created by JoachimDs on 19/10/2015.
 */
public class NewMiniMaxAlphaBetaComputer implements Computer {
    private Zet besteZet;
    private int diepte;
    private HeuristicCalculator calculator;
    private double allerBestHeuristicValue;

    public NewMiniMaxAlphaBetaComputer() {
        allerBestHeuristicValue = Double.NEGATIVE_INFINITY;
    }

    @Override
    public Zet berekenZet(Bord bord) {
        return null;
    }

    private double alphaBeta(Bord bord, double alpha, double beta, Kleur kleurAanZet) {
        double bestHeuristicValue;
        Kleur volgendeKleurAanZet = Kleur.andereKleur(kleurAanZet);

        if (diepte == 0) {
            bestHeuristicValue = calculator.getHeuristicValue(bord, kleurAanZet);
            diepte++;
        } else if (kleurAanZet == Kleur.WIT) {
            bestHeuristicValue = alpha;
            for (int i=0; i<bord.geefGeldigeZetten(kleurAanZet).size(); i++){
                diepte--;
                Bord tijdelijBord = bord;
                tijdelijBord.zetPion(bord.geefGeldigeZetten(kleurAanZet).get(i), kleurAanZet);
                bestHeuristicValue = Math.max(alphaBeta(tijdelijBord, bestHeuristicValue, beta, volgendeKleurAanZet), bestHeuristicValue);
                if (beta <= bestHeuristicValue){
                    break;
                }
            }
        } else {
            bestHeuristicValue = beta;
            for (int i=0; i<bord.geefGeldigeZetten(kleurAanZet).size(); i++){
                diepte--;
                Bord tijdelijBord = bord;
                tijdelijBord.zetPion(bord.geefGeldigeZetten(kleurAanZet).get(i), kleurAanZet);
                bestHeuristicValue = Math.min(alphaBeta(tijdelijBord, alpha, bestHeuristicValue, volgendeKleurAanZet), bestHeuristicValue);
                if (diepte == 4 && allerBestHeuristicValue < bestHeuristicValue){
                    besteZet = bord.geefGeldigeZetten(kleurAanZet).get(i);
                }
                if (bestHeuristicValue <= alpha){
                    break;
                }
            }
        }

        return bestHeuristicValue;
    }

}
