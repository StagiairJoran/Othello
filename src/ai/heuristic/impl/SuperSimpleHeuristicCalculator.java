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
public class SuperSimpleHeuristicCalculator extends HeuristicCalculator {
    public double getHeuristicValue(Bord bord, Kleur kleur) {


        return getCoinParityHeuristicValue(bord, kleur) ;
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



}
