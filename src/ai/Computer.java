package ai;

import ai.heuristic.api.HeuristicCalculator;
import ai.heuristic.impl.CompleteHeuristicCalculator;
import ai.heuristic.impl.SimpleHeuristicCalculator;
import model.Bord;
import model.Kleur;
import model.OngeldigeZet;

import java.util.List;

/**
 * Created by jorandeboever
 * on 6/10/15.
 */
public class Computer {
    private HeuristicCalculator heuristicCalculator;

    public Computer() {
        this.heuristicCalculator = new CompleteHeuristicCalculator();
    }

    public Zet doeZet(Bord bord, Kleur kleur){
        List<Zet> zetten = bord.geefGeldigeZetten(kleur);

        Zet besteZet = null;
        double bestHeuristicValue = (double) -999999999;
        System.out.println("----------------------------------------------");

        for(Zet zet: zetten){
            Bord duplicaat = new Bord(bord);
            try {
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
            } catch (OngeldigeZet ongeldigeZet) {
                ongeldigeZet.printStackTrace();
            }

            zet.setHeuristicValue(heuristicCalculator.getHeuristicValue(duplicaat, kleur));
            System.out.println("Zet rij= " + zet.getRij() + ", kolom = " + zet.getKolom() + "Heuristic Value = " + zet.getHeuristicValue());
            if(zet.getHeuristicValue() > bestHeuristicValue || besteZet == null){
                besteZet = zet;
                bestHeuristicValue = zet.getHeuristicValue();
            }


        }
        return besteZet;
    }


}
