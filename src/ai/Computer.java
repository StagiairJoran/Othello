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
    private Kleur computerKleur;

    public Computer() {
        this.heuristicCalculator = new CompleteHeuristicCalculator();
    }

    public Computer(Kleur kleur) {
        this();
        this.computerKleur = kleur;
    }

    public Zet doeZet(Bord bord, Kleur kleur){
        List<Zet> zetten = bord.geefGeldigeZetten(kleur);

        Zet besteZet = null;
        double bestHeuristicValue = Double.NEGATIVE_INFINITY;
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

    public Zet doeMinimaxZet(Bord bord){
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

            zet.setHeuristicValue(miniMax(duplicaat, Kleur.andereKleur(computerKleur)));
            //System.out.println("Zet rij= " + zet.getRij() + ", kolom = " + zet.getKolom() + "Minimax Value = " + zet.getHeuristicValue());
            if(zet.getHeuristicValue() > bestHeuristicValue || besteZet == null){
                besteZet = zet;
                bestHeuristicValue = zet.getHeuristicValue();
            }


        }
        return besteZet;
    }

    private double miniMax(Bord bord, Kleur kleur){
        double besteWaarde;
        if(bord.getNodeDiepte() == 4 || bord.geefGeldigeZetten(kleur).size() == 0){
            besteWaarde = heuristicCalculator.getHeuristicValue(bord, computerKleur);

        }else if(kleur == computerKleur){
            besteWaarde = Double.NEGATIVE_INFINITY;
            for(Zet zet : bord.geefGeldigeZetten(kleur)){
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = miniMax(duplicaat, Kleur.andereKleur(kleur));
                besteWaarde = Math.max(besteWaarde, childWaarde);
            }
        }else {
            besteWaarde = Double.POSITIVE_INFINITY;
            for(Zet zet : bord.geefGeldigeZetten(kleur)){
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = miniMax(duplicaat, Kleur.andereKleur(kleur));
                besteWaarde = Math.min(besteWaarde, childWaarde);
            }
        }




        return besteWaarde;
    }


}
