package ai.computer.impl;

import ai.Zet;
import ai.computer.ObservableAI;
import ai.computer.api.Computer;
import ai.heuristic.api.HeuristicCalculator;
import ai.heuristic.impl.CompleteHeuristicCalculator;
import model.Bord;
import model.Kleur;

import java.util.List;

/**
 * Created by jorandeboever
 * on 6/10/15.
 * MiniMaxComputer past het minimax-algoritme toe
 */
public class MiniMaxComputer extends Computer {

    private Zet ultiemeZet;


    public MiniMaxComputer(Kleur kleur) {
        super(kleur);

    }


    public Zet berekenZet(Bord bord) {
    /*    //Haal mogelijke zetten op
        List<Zet> zetten = bord.geefGeldigeZetten(computerKleur);
        this.setDuur(zetten.size());
        //kies beste zet
        int i = 0;
        Zet besteZet = null;
        double besteWaarde = Double.NEGATIVE_INFINITY;
        for (Zet zet : zetten) {
            Bord duplicaat = new Bord(bord);
            duplicaat.zetPion(zet.getRij(), zet.getKolom(), computerKleur);

            zet.setWaarde(miniMax(duplicaat, Kleur.andereKleur(computerKleur), aantalStappen - 1));
            if (zet.getWaarde() > besteWaarde || besteZet == null) {
                besteZet = zet;
                besteWaarde = zet.getWaarde();
            }
            this.setProgress(++i);
            setChanged();
            notifyObservers();

        }
        return besteZet;*/
        ultiemeZet = new Zet(100, 100);
        ultiemeZet.setWaarde(Double.NEGATIVE_INFINITY);
        miniMax(bord, computerKleur, aantalStappen);
        return ultiemeZet;
    }


    /*
     * Minimax-algoritme
     */
    private double miniMax(Bord bord, Kleur kleur, int aantalStappen) {
        double besteWaarde;
        if (0 == aantalStappen || bord.geefGeldigeZetten(kleur).size() == 0) {
            //stopconditie
            besteWaarde = heuristicCalculator.getHeuristicValue(bord, computerKleur);

        } else if (kleur == computerKleur) {
            // Max
            List<Zet> zetten = bord.geefGeldigeZetten(kleur);
            int i = 0;

            besteWaarde = Double.NEGATIVE_INFINITY;
            for (Zet zet : zetten) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur);
                double childWaarde = miniMax(duplicaat, Kleur.andereKleur(kleur), aantalStappen - 1);
                besteWaarde = Math.max(besteWaarde, childWaarde);

                if (aantalStappen == this.aantalStappen ) {
                    if (ultiemeZet.getWaarde() < childWaarde) {
                        ultiemeZet = zet;
                        ultiemeZet.setWaarde(childWaarde);
                    }
                    update(++i, zetten.size());
                }
                if (besteWaarde == childWaarde) {
                    duplicaat.setUserObject("! " + Math.round(childWaarde)  + zet);
                }
                else {
                    duplicaat.setUserObject("  " + Math.round(childWaarde)  + zet);

                }
                bord.add(duplicaat);
            }
        } else {
            // Min
            besteWaarde = Double.POSITIVE_INFINITY;
            for (Zet zet : bord.geefGeldigeZetten(kleur)) {
                Bord duplicaat = new Bord(bord);
                duplicaat.zetPion(zet.getRij(), zet.getKolom(), kleur); //TODO gebruik zet
                double childWaarde = miniMax(duplicaat, Kleur.andereKleur(kleur), aantalStappen - 1);
                besteWaarde = Math.min(besteWaarde, childWaarde);


                if (besteWaarde == childWaarde) {
                    duplicaat.setUserObject("! " + Math.round(childWaarde)  + zet);
                }
                else {
                    duplicaat.setUserObject("  " + Math.round(childWaarde)  + zet);

                }

                bord.add(duplicaat);
            }
        }


        return besteWaarde;
    }


}
