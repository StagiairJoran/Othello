package ai.heuristic.api;

import ai.heuristic.impl.CompleteHeuristicCalculator;
import ai.heuristic.impl.SimpleHeuristicCalculator;
import ai.heuristic.impl.SuperSimpleHeuristicCalculator;
import model.Bord;
import model.Kleur;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorandeboever
 * on 6/10/15.
 * HeuristicCalculator berekent een heuristische waarde van een spelbord
 */
public abstract class HeuristicCalculator {
    public abstract double getHeuristicValue(Bord bord, Kleur kleur);

    public   static List<HeuristicCalculator> geefAlleCalculators(){
         List<HeuristicCalculator> calculators = new ArrayList<>();
         calculators.add(new SuperSimpleHeuristicCalculator());
         calculators.add(new SimpleHeuristicCalculator());
         calculators.add(new CompleteHeuristicCalculator());
        return calculators;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
