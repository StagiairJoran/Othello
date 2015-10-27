package ai.computer.impl;

import ai.Zet;
import ai.computer.api.Computer;
import model.Bord;
import model.Kleur;

/**
 * Created by JoachimDs on 19/10/2015.
 */
public class MiniMaxAlphaBetaComputerJoachim extends Computer {
    private Zet ultiemeZet;

    public MiniMaxAlphaBetaComputerJoachim(Kleur kleur) {
        super(kleur);
    }

    @Override
    public Zet berekenZet(Bord bord) {
        ultiemeZet = new Zet(9, 9);
        ultiemeZet.setWaarde(Double.NEGATIVE_INFINITY);
        alphaBeta(bord, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, computerKleur, aantalStappen);
        return ultiemeZet;

    }


    private double alphaBeta(Bord bord, double alpha, double beta, Kleur kleurAanZet, int aantalStappen) {
        double kindWaarde;

        if (aantalStappen == 0 || bord.geefGeldigeZetten(kleurAanZet).size() == 0) {
            kindWaarde = heuristicCalculator.getHeuristicValue(bord, computerKleur);
        } else if (kleurAanZet == computerKleur) {
            kindWaarde = alpha;
            for (Zet zet : bord.geefGeldigeZetten(kleurAanZet)) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet, kleurAanZet);
                double kleinKindWaarde = alphaBeta(duplicaat, kindWaarde, beta, Kleur.andereKleur(kleurAanZet), aantalStappen - 1);
                kindWaarde = Math.max(kleinKindWaarde, kindWaarde);

                if (aantalStappen == this.aantalStappen) {
                    if (ultiemeZet.getWaarde() < kleinKindWaarde) {
                        ultiemeZet = zet;
                        ultiemeZet.setWaarde(kleinKindWaarde);
                    }
                    //iets voor prograssbar
                }
                duplicaat.setUserObject(" " + Math.round(kleinKindWaarde) + zet);
                bord.add(duplicaat);
            }
        } else {
            kindWaarde = beta;
            for (Zet zet : bord.geefGeldigeZetten(kleurAanZet)) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet, kleurAanZet);
                double kleinKindWaarde = alphaBeta(duplicaat, alpha, kindWaarde, Kleur.andereKleur(kleurAanZet), aantalStappen - 1);
                kindWaarde = Math.min(kleinKindWaarde, kindWaarde);

                duplicaat.setUserObject(" " + Math.round(kleinKindWaarde) + zet);
                bord.add(duplicaat);
            }
        }

        return kindWaarde;
    }

    @Override
    public String toString() {
        return "Joachim: MiniMax met AlphaBeta";
    }
}
