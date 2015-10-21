package ai.heuristic.impl;

import ai.heuristic.api.HeuristicCalculator;
import model.Bord;
import model.Kleur;

/**
 * Created by jorandeboever
 * on 6/10/15.
 * SimpelHeuristicCalculator berekent een heuristische waarde van een spelbord
 * Gebaseerd op 3 factoren:
 ** Aantal vakken elke speler bezit
 ** Aantal zetten dat elke speler kan doen
 ** Aantal hoeken van een spelbord dat elke speler bezit
 */
public class SimpleHeuristicCalculator extends HeuristicCalculator {
    public double getHeuristicValue(Bord bord, Kleur kleur) {
        double coinParityHeuristicValue = getCoinParityHeuristicValue(bord, kleur);
        double mobilityHeuristicValue = getMobilityHeuristicValue(bord, kleur);
        double cornerHeuristicValue = getCornerHeuristicValue(bord, kleur);

        return coinParityHeuristicValue + mobilityHeuristicValue + cornerHeuristicValue;
    }

    /*
     * Berekent een heuristische waarde gebaseerd de hoeken van een spelbord
     * Kijkt naar hoeveel hoeken elke speler bevat
     */
    private double getCornerHeuristicValue(Bord bord, Kleur kleur) {
        int aantalMax = 0;
        int aantalMin = 0;

        if (bord.getKleurOpPositie(0, 0) == kleur) {
            aantalMax++;
        }
        if (bord.getKleurOpPositie(0, bord.getGrootteBord() - 1) == kleur) {
            aantalMax++;
        }
        if (bord.getKleurOpPositie(bord.getGrootteBord() - 1, 0) == kleur) {
            aantalMax++;
        }
        if (bord.getKleurOpPositie(bord.getGrootteBord() - 1, bord.getGrootteBord() - 1) == kleur) {
            aantalMax++;
        }
        if (bord.getKleurOpPositie(0, 0) == Kleur.andereKleur(kleur)) {
            aantalMin++;
        }
        if (bord.getKleurOpPositie(0, bord.getGrootteBord() - 1) == Kleur.andereKleur(kleur)) {
            aantalMin++;
        }
        if (bord.getKleurOpPositie(bord.getGrootteBord() - 1, 0) == Kleur.andereKleur(kleur)) {
            aantalMin++;
        }
        if (bord.getKleurOpPositie(bord.getGrootteBord() - 1, bord.getGrootteBord() - 1) == Kleur.andereKleur(kleur)) {
            aantalMin++;
        }

        if (aantalMax + aantalMin != 0) {
            return 100 * (aantalMax - aantalMin) / (aantalMax + aantalMin);
        }
        return 0;
    }

    /*
     * Berekent heuristische waarde gebaseerd op aantal vakken
     * Kijkt naar hoeveel vakken elke speler heeft
     */
    private double getCoinParityHeuristicValue(Bord bord, Kleur kleur) {
        int aantalMax = 0;
        int aantalMin = 0;

        for (int i = 0; i < bord.getGrootteBord() - 1; i++) {
            for (int j = 0; j < bord.getGrootteBord() - 1; j++) {
                if (bord.getKleurOpPositie(i, j) == kleur) {
                    aantalMax++;
                } else if (bord.getKleurOpPositie(i, j) != Kleur.LEEG) {
                    aantalMin++;
                }
            }
        }

        return 100 * (aantalMax - aantalMin) / (aantalMax + aantalMin);


    }

    /*
     * Berekent een heuristische waarde gebaseerd op mobility
     * Kijkt naar hoeveel zetten elke speler kan doen
     */
    private double getMobilityHeuristicValue(Bord bord, Kleur kleur) {
        int aantalMovesMax = 0;
        int aantalMovesMin = 0;
        for (int i = 0; i < bord.getGrootteBord() - 1; i++) {
            for (int j = 0; j < bord.getGrootteBord() - 1; j++) {
                if (bord.isGeldigeZet(i, j, kleur)) {
                    aantalMovesMax++;
                }
                if (bord.isGeldigeZet(i, j, Kleur.andereKleur(kleur))) {
                    aantalMovesMin++;
                }
            }
        }

        if (aantalMovesMax + aantalMovesMin != 0) {
            return 100 * (aantalMovesMax - aantalMovesMin) / (aantalMovesMax + aantalMovesMin);
        }
        return 0;
    }

}
