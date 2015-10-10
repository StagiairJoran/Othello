package view.swing;

import ai.Zet;
import ai.computer.api.Computer;
import ai.computer.impl.MiniMaxAlphaBetaComputer;
import model.Bord;
import model.Kleur;
import model.Spel;

import javax.swing.*;

/**
 * Created by jorandeboever
 * on 10/10/15.
 */
public class ComputerWorker extends SwingWorker {
    private Spel spel;

    public ComputerWorker(Spel spel) {
        this.spel = spel;
    }

    @Override
    protected Object doInBackground() throws Exception {
        spel.doeComputerZet();
        return 1;
    }
}
