package view.swing;

import model.Spel;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by jorandeboever
 * on 10/10/15.
 */
public class ComputerWorker extends SwingWorker implements Observer {
    private Spel spel;
    private JProgressBar computerProgressBar;
    private JFrame treeFrame;

    public ComputerWorker(Spel spel, JProgressBar computerProgressBar) {
        this.spel = spel;
        this.computerProgressBar = computerProgressBar;
        computerProgressBar.setValue(0);
        Observable observable =  spel.getComputer();
        observable.addObserver(this);
    }

    @Override
    protected Object doInBackground() throws Exception {
        spel.getBord().removeAllChildren();
        System.out.printf("Background work..");
        spel.doeComputerZet();

        return 1;
    }

    @Override
    public void update(Observable o, Object arg) {
        computerProgressBar.setMaximum(spel.getComputer().getDuur());
        computerProgressBar.setValue(spel.getComputer().getProgress());
        computerProgressBar.repaint();

    }


}
