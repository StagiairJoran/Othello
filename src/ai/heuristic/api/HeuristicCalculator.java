package ai.heuristic.api;

import model.Bord;
import model.Kleur;

/**
 * Created by jorandeboever
 * on 6/10/15.
 * HeuristicCalculator berekent een heuristische waarde van een spelbord
 */
public interface HeuristicCalculator {
    double getHeuristicValue(Bord bord, Kleur kleur);
}
