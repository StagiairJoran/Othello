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
 * HeuristicComputer kiest de zet die uitkomt bij het bord met de beste heuristische waarde
 */
public class HeuristicComputer implements Computer {
    private HeuristicCalculator heuristicCalculator;
    private Kleur computerKleur;


    public HeuristicComputer(Kleur kleur) {
        this.heuristicCalculator = new CompleteHeuristicCalculator();

        this.computerKleur = kleur;
    }


    @Override
    public Zet berekenZet(Bord bord){
        List<Zet> zetten = bord.geefGeldigeZetten(computerKleur);

        Zet besteZet = null;
        double bestHeuristicValue = Double.NEGATIVE_INFINITY;
        System.out.println("----------------------------------------------");

        for(Zet zet: zetten){
            Bord duplicaat = new Bord(bord);
            try {
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), computerKleur);
            } catch (OngeldigeZet ongeldigeZet) {
                ongeldigeZet.printStackTrace();
            }

            zet.setHeuristicValue(heuristicCalculator.getHeuristicValue(duplicaat, computerKleur));
            System.out.println("Zet rij= " + zet.getRij() + ", kolom = " + zet.getKolom() + "Heuristic Value = " + zet.getHeuristicValue());
            if(zet.getHeuristicValue() > bestHeuristicValue || besteZet == null){
                besteZet = zet;
                bestHeuristicValue = zet.getHeuristicValue();
            }


        }
        return besteZet;
    }



}
