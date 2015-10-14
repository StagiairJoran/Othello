package ai.computer.impl;

import ai.Zet;
import ai.computer.ObservableAI;
import ai.computer.api.Computer;
import ai.heuristic.api.HeuristicCalculator;
import ai.heuristic.impl.CompleteHeuristicCalculator;
import model.Bord;
import model.Kleur;
import model.OngeldigeZet;

import java.util.List;
import java.util.Observable;

/**
 * Created by jorandeboever
 * on 6/10/15.
 * HeuristicComputer kiest de zet die uitkomt bij het bord met de beste heuristische waarde
 */
public class HeuristicComputer extends ObservableAI implements Computer {
    private HeuristicCalculator heuristicCalculator;
    private Kleur computerKleur;


    public HeuristicComputer(Kleur kleur) {
        this.heuristicCalculator = new CompleteHeuristicCalculator();

        this.computerKleur = kleur;
    }


    @Override
    public Zet berekenZet(Bord bord) {
        List<Zet> zetten = bord.geefGeldigeZetten(computerKleur);
        setDuur(zetten.size());
        int i = 0;
        Zet besteZet = null;
        double bestHeuristicValue = Double.NEGATIVE_INFINITY;
        System.out.println("----------------------------------------------");

        for (Zet zet : zetten) {
            Bord duplicaat = new Bord(bord);
            try {
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), computerKleur);
            } catch (OngeldigeZet ongeldigeZet) {
                ongeldigeZet.printStackTrace();
            }

            zet.setWaarde(heuristicCalculator.getHeuristicValue(duplicaat, computerKleur));
            System.out.println("Zet rij= " + zet.getRij() + ", kolom = " + zet.getKolom() + "Heuristic Value = " + zet.getWaarde());
            if (zet.getWaarde() > bestHeuristicValue || besteZet == null) {
                besteZet = zet;
                bestHeuristicValue = zet.getWaarde();
            }
            this.setProgress(++i);
            setChanged();
            notifyObservers();

        }
        return besteZet;
    }

    @Override
    public void setAantalStappen(int aantalStappen) {
        System.err.println("Heuristic computer heeft geen aantal stappen");
    }

    public int getAantalStappen() {
        return 0;
    }



}
