package view.swing;

import ai.Zet;
import ai.computer.api.Computer;
import model.Spel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
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
        Observable observable = (Observable) spel.getComputer();
        observable.addObserver(this);
    }

    @Override
    protected Object doInBackground() throws Exception {
        spel.getBord().removeAllChildren();
        System.out.printf("Background work..");
        spel.doeComputerZet();

        //maak visualisatie van tree
       /*  treeFrame = new JFrame("Tree");
        JTree tree = new JTree(spel.getBord());
        JScrollPane scrollPane = new JScrollPane(tree);
        treeFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        treeFrame.setSize(300, 150);
        treeFrame.setVisible(true);*/

        return 1;
    }

    @Override
    public void update(Observable o, Object arg) {
        computerProgressBar.setMaximum(spel.getComputer().getDuur());
        computerProgressBar.setValue(spel.getComputer().getProgress());
        computerProgressBar.repaint();

    }


}
