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
    private ComputerProgressBar computerProgressBar;
    private Computer computer;

    public ComputerWorker(Spel spel, ComputerProgressBar computerProgressBar, Computer computer) {
        this.spel = spel;
        this.computerProgressBar = computerProgressBar;
        computerProgressBar.setValue(0);
        this.computer = computer;
        Observable observable = (Observable) this.computer;
        observable.addObserver(this);
    }

    @Override
    protected Object doInBackground() throws Exception {
        spel.getBord().removeAllChildren();
        Zet zet =  computer.berekenZet(spel.getBord());
        spel.zetPion(zet.getRij(), zet.getKolom());

        JFrame frame = new JFrame("Tree");
       /* DefaultMutableTreeNode root = new DefaultMutableTreeNode(1);

        DefaultMutableTreeNode mercury = new DefaultMutableTreeNode(2);
        root.add(mercury);
        DefaultMutableTreeNode venus = new DefaultMutableTreeNode(3);
        root.add(venus);
        DefaultMutableTreeNode mars = new DefaultMutableTreeNode(4);
        root.add(mars);*/
        JTree tree = new JTree(spel.getBord());
        JScrollPane scrollPane = new JScrollPane(tree);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);
        return 1;
    }

    @Override
    public void update(Observable o, Object arg) {
        computerProgressBar.setMaximum(computer.getDuur());
        computerProgressBar.setValue(computer.getProgress());
        computerProgressBar.repaint();

    }
}
