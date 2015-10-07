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
 * MiniMaxComputer past het minimax-algoritme toe
 */
public class MiniMaxComputer implements Computer{
    private HeuristicCalculator heuristicCalculator;
    private Kleur computerKleur;
    private int aantalStappen;


    public MiniMaxComputer(Kleur kleur) {
        this.heuristicCalculator = new CompleteHeuristicCalculator();
        this.aantalStappen = 4;
        this.computerKleur = kleur;

    }


    public Zet berekenZet(Bord bord) {
        //Haal mogelijke zetten op
        List<Zet> zetten = bord.geefGeldigeZetten(computerKleur);

        //kies beste zet
        Zet besteZet = null;
        double besteWaarde = Double.NEGATIVE_INFINITY;
        for (Zet zet : zetten) {
            Bord duplicaat = new Bord(bord);
            try {
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), computerKleur);
            } catch (OngeldigeZet ongeldigeZet) {
                ongeldigeZet.printStackTrace();
            }

            zet.setHeuristicValue(miniMax(duplicaat, Kleur.andereKleur(computerKleur)));
            if (zet.getHeuristicValue() > besteWaarde || besteZet == null) {
                besteZet = zet;
                besteWaarde = zet.getHeuristicValue();
            }


        }
        return besteZet;
    }

    /*
     * Minimax-algoritme
     */
    private double miniMax(Bord bord, Kleur kleur) {
        double besteWaarde;
        if (bord.getNodeDiepte() == aantalStappen || bord.geefGeldigeZetten(kleur).size() == 0) {
            besteWaarde = heuristicCalculator.getHeuristicValue(bord, computerKleur);

        } else if (kleur == computerKleur) {
            besteWaarde = Double.NEGATIVE_INFINITY;
            for (Zet zet : bord.geefGeldigeZetten(kleur)) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = miniMax(duplicaat, Kleur.andereKleur(kleur));
                besteWaarde = Math.max(besteWaarde, childWaarde);
            }
        } else {
            besteWaarde = Double.POSITIVE_INFINITY;
            for (Zet zet : bord.geefGeldigeZetten(kleur)) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = miniMax(duplicaat, Kleur.andereKleur(kleur));
                besteWaarde = Math.min(besteWaarde, childWaarde);
            }
        }


        return besteWaarde;
    }

    public void setAantalStappen(int aantalStappen) {
        this.aantalStappen = aantalStappen;
    }
}
