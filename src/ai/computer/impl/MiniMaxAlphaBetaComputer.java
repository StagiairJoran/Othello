package ai.computer.impl;

import ai.Zet;
import ai.computer.ObservableAI;
import ai.computer.api.Computer;
import ai.heuristic.api.HeuristicCalculator;
import ai.heuristic.impl.CompleteHeuristicCalculator;
import ai.heuristic.impl.SimpleHeuristicCalculator;
import model.Bord;
import model.Kleur;
import model.OngeldigeZet;

import java.util.List;
import java.util.Observable;

/**
 * Created by jorandeboever
 * on 6/10/15.
 * MiniMaxComputer past het minimax-algoritme toe met alpha-beta pruning
 */
public class MiniMaxAlphaBetaComputer extends  Computer {
    private Zet ultiemeZet;



    public MiniMaxAlphaBetaComputer(Kleur kleur) {
        super(kleur);

    }


    public Zet berekenZet(Bord bord) {

        ultiemeZet = new Zet(100, 100);
        ultiemeZet.setWaarde(Double.NEGATIVE_INFINITY);
        alphaBeta(bord, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, computerKleur, aantalStappen);
        return ultiemeZet;
    }


    /*
     * Minimax-algoritme met alpha-beta pruning
     */
    private double alphaBeta(Bord bord, double alpha, double beta, Kleur kleur, int aantalStappen) {
        double besteWaarde;
        if (0 == aantalStappen || bord.geefGeldigeZetten(kleur).size() == 0) {
            //stopconditie
            besteWaarde = heuristicCalculator.getHeuristicValue(bord, computerKleur);

        } else if (kleur == computerKleur) {
            //max
            besteWaarde = alpha;
            List<Zet> zetten = bord.geefGeldigeZetten(kleur);
            int i = 0;

            for (Zet zet : zetten) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = alphaBeta(duplicaat, besteWaarde, beta, Kleur.andereKleur(kleur), aantalStappen - 1);
                besteWaarde = Math.max(besteWaarde, childWaarde);
                if (aantalStappen == this.aantalStappen ) {
                    if (ultiemeZet.getWaarde() < besteWaarde) {
                        ultiemeZet = zet;
                        ultiemeZet.setWaarde(childWaarde);
                    }
                    update(++i, zetten.size());
                }

                zet.setWaarde(childWaarde);
                duplicaat.setUserObject(zet);
                bord.add(duplicaat);
                if (beta <= besteWaarde) break;
            }
        } else {
            //min
            besteWaarde = Double.POSITIVE_INFINITY;
            for (Zet zet : bord.geefGeldigeZetten(kleur)) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = alphaBeta(duplicaat, alpha, besteWaarde, Kleur.andereKleur(kleur), aantalStappen - 1);
                besteWaarde = Math.min(besteWaarde, childWaarde);

                zet.setWaarde(childWaarde);
                duplicaat.setUserObject(zet);
                bord.add(duplicaat);
                if (besteWaarde <= alpha) break;

            }
        }


        return besteWaarde;
    }




}
