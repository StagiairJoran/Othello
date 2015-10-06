package ai;

import model.Bord;
import model.Kleur;

/**
 * Created by jorandeboever
 * on 6/10/15.
 */
public class Computer {
    private double getHeuristic(Bord bord, Kleur kleur) {
        double coinParityHeuristicValue = getCoinParityHeuristicValue(bord, kleur);

        return 0;
    }

    private double getCoinParityHeuristicValue(Bord bord, Kleur kleur) {
        int aantalMax = 0;
        int aantalMin = 0;

        for (int i = 0; i < bord.getGrootteBord(); i++) {
            for (int j = 0; j < bord.getGrootteBord(); j++) {
                if (bord.getKleurOpPositie(i, j) == kleur) {
                    aantalMax++;
                }else if (bord.getKleurOpPositie(i, j) != Kleur.LEEG) {
                    aantalMin++;
                }
            }
        }


        return 100 * (aantalMax - aantalMin) / (aantalMax + aantalMin);

    }
}
