package ai.computer.impl;

import ai.Zet;
import ai.computer.api.Computer;
import ai.heuristic.api.HeuristicCalculator;
import ai.heuristic.impl.CompleteHeuristicCalculator;
import model.Bord;
import model.Kleur;
import model.OngeldigeZet;

import java.util.List;

/**
 * Created by jorandeboever
 * on 6/10/15.
 * MiniMaxComputer past het minimax-algoritme toe met alpha-beta pruning
 */
public class MiniMaxAlphaBetaComputer implements Computer{
    private HeuristicCalculator heuristicCalculator;
    private Kleur computerKleur;
    private int aantalStappen;


    public MiniMaxAlphaBetaComputer(Kleur kleur) {
        this.heuristicCalculator = new CompleteHeuristicCalculator();
        this.aantalStappen = 6;
        this.computerKleur = kleur;

    }


    public Zet berekenZet(Bord bord) {
        //Haal mogelijke zetten op
        List<Zet> zetten = bord.geefGeldigeZetten(computerKleur);

        //kies beste zet
        Zet besteZet = null;
        double besteWaarde = Double.NEGATIVE_INFINITY;
        for (Zet zet : zetten) {
            Bord duplicaat = new Bord(bord);
            try {
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), computerKleur);
            } catch (OngeldigeZet ongeldigeZet) {
                ongeldigeZet.printStackTrace();
            }

            zet.setHeuristicValue(alphaBeta(duplicaat, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Kleur.andereKleur(computerKleur)));
            if (zet.getHeuristicValue() > besteWaarde || besteZet == null) {
                besteZet = zet;
                besteWaarde = zet.getHeuristicValue();
            }


        }
        return besteZet;
    }

    /*
     * Minimax-algoritme met alpha-beta pruning
     */
    private double alphaBeta(Bord bord, double alpha, double beta, Kleur kleur) {
        double besteWaarde;
        if (bord.getNodeDiepte() == aantalStappen || bord.geefGeldigeZetten(kleur).size() == 0) {
            besteWaarde = heuristicCalculator.getHeuristicValue(bord, computerKleur);

        } else if (kleur == computerKleur) {
            besteWaarde = alpha;
            for (Zet zet : bord.geefGeldigeZetten(kleur)) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = alphaBeta(duplicaat, besteWaarde, beta, Kleur.andereKleur(kleur));
                besteWaarde = Math.max(besteWaarde, childWaarde);
                if(beta <= besteWaarde) break;
            }
        } else {
            besteWaarde = Double.POSITIVE_INFINITY;
            for (Zet zet : bord.geefGeldigeZetten(kleur)) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = alphaBeta(duplicaat, alpha, besteWaarde, Kleur.andereKleur(kleur));
                besteWaarde = Math.min(besteWaarde, childWaarde);
                if(besteWaarde <= alpha) break;

            }
        }


        return besteWaarde;
    }

    public void setAantalStappen(int aantalStappen) {
        this.aantalStappen = aantalStappen;
    }
}
