package ai.heuristic.impl;

import factories.BordWrapperFactory;
import model.Bord;
import model.Kleur;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by jorandeboever
 * on 7/10/15.
 */
public class CompleteHeuristicCalculatorTest {
    CompleteHeuristicCalculator simpleHeuristicCalculator = new CompleteHeuristicCalculator();

    @Test
    public void testCompleetBord() {

        Bord bord = BordWrapperFactory.getVolBord(Kleur.WIT);
        assertTrue(simpleHeuristicCalculator.getHeuristicValue(bord, Kleur.WIT) > simpleHeuristicCalculator.getHeuristicValue(bord, Kleur.ZWART));


        bord = BordWrapperFactory.getVolBord(Kleur.ZWART);
        assertTrue(simpleHeuristicCalculator.getHeuristicValue(bord, Kleur.ZWART) > simpleHeuristicCalculator.getHeuristicValue(bord, Kleur.WIT));

    }
}
