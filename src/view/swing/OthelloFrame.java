package view.swing;

import ai.computer.impl.HeuristicComputer;
import ai.computer.impl.MiniMaxAlphaBetaComputer;
import ai.computer.impl.MiniMaxComputer;
import ai.computer.impl.NewMiniMaxComputer;
import ai.heuristic.impl.SimpleHeuristicCalculator;
import model.Kleur;
import model.Spel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class OthelloFrame extends JFrame {

    Spel spel;
    JLabel spelerLabel;
    OthelloBord othelloBord;
    JProgressBar computerProgressBar = new JProgressBar();
    JComboBox comboBox;
    JLabel comboLabel;
    ComputerWorker computerWorker;
    TreeFrame treeFrame;
    int teller = 0;

    public OthelloFrame() throws HeadlessException {
        this.spel = new Spel();
        this.spelerLabel = new JLabel("Witte speler aan de beurt");
        othelloBord = new OthelloBord(spel, this);
        this.comboBox = maakComboBox();
        this.comboLabel = new JLabel("Aantal stappen:");
        computerWorker = createComputerWorker();
        init();

    }


    private void init() {
        this.setTitle("Othello");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel informationPanel = new JPanel();
        informationPanel.add(spelerLabel);
        this.add(maakMenu(), BorderLayout.NORTH);
        this.add(othelloBord, BorderLayout.CENTER);

        JPanel paneel = new JPanel();
        paneel.add(computerProgressBar, BorderLayout.NORTH);
        paneel.add(informationPanel, BorderLayout.SOUTH);
        paneel.add(comboLabel, BorderLayout.SOUTH);
        paneel.add(comboBox, BorderLayout.SOUTH);
        this.add(paneel, BorderLayout.SOUTH);

        this.pack();
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        herlaad();
        toonComputerOpties();

    }

    private JComboBox maakComboBox() {
        Integer[] getallen = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox comboBox = new JComboBox(getallen);
        comboBox.setSelectedItem(spel.getComputer().getAantalStappen());
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spel.getComputer().setAantalStappen((Integer) comboBox.getSelectedItem());
            }
        });
        return comboBox;
    }

    /*
     * Maakt klein menubar met menu-items
     */
    private JMenuBar maakMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Spel");

        JMenuItem nieuw = new JMenuItem("Nieuw");
        nieuw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spel = new Spel();
                teller = 0;
                othelloBord.setSpel(spel);
                herlaad();
                toonComputerOpties();
            }
        });
        JMenuItem exit = new JMenuItem("Afsluiten");
        exit.addActionListener(e -> System.exit(0));
        file.add(nieuw);
        file.add(exit);
        menuBar.add(file);

        return menuBar;

    }


    /*
     * Toont een scherm met keuzes voor computertegenstanders
     */
    private void toonComputerOpties() {
        Object[] computerOpties = {"MiniMax AlphaBeta", "MiniMax", "Heuristic", "NewMiniMax"};

        comboBox.setVisible(true);
        comboLabel.setVisible(true);
        int aantalStappen = spel.getComputer().getAantalStappen();

        int keuze = JOptionPane.showOptionDialog(this,
                "Tegen welke computer wil je spelen?",
                "Computerkeuze",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                computerOpties, computerOpties[0]);
        switch (keuze) {
            case 0:
                spel.setComputer(new MiniMaxAlphaBetaComputer(Kleur.ZWART));
                this.setTitle("Othello MiniMax Alpha Beta");
                break;
            case 1:
                spel.setComputer(new MiniMaxComputer(Kleur.ZWART));
                this.setTitle("Othello MiniMax");
                break;
            case 3:
                spel.setComputer(new NewMiniMaxComputer(Kleur.ZWART));
                this.setTitle("Othello NewMiniMax");

                break;
            default:
                comboBox.setVisible(false);
                comboLabel.setVisible(false);
                spel.setComputer(new HeuristicComputer(Kleur.ZWART));
                this.setTitle("Othello MiniMax Heuristic");

                break;
        }
        spel.getComputer().setAantalStappen(aantalStappen);
    }


    /*
     * Tekent alles opnieuw waneer er een wijziging gebeurt in het model
     */
    public void herlaad() {
        othelloBord.herlaad();

        if (!spel.isSpelGedaan()) {
            if (spel.getKleurAanDeBeurt() == Kleur.WIT) {
                spelerLabel.setText("Witte speler aan de beurt");
            } else {
                spelerLabel.setText("Zwarte speler aan de beurt");
            }
        } else {
            spelerLabel.setText(spel.getWinnaarTekst());
        }

        this.revalidate();
        this.repaint();
        if (spel.isSpelGedaan()) {
            toonWinVenster();
        } else if (spel.getKleurAanDeBeurt() == Kleur.ZWART) {

            int aantalStappen = spel.getComputer().getAantalStappen();
            spel.setComputer(new MiniMaxComputer(Kleur.ZWART));
            spel.getComputer().setAantalStappen(aantalStappen);
            startComputerWorker();

        } else if (spel.getKleurAanDeBeurt() == Kleur.WIT && teller++ > 0) {
            // Computer tegen computer
           /* int aantalStappen = spel.getComputer().getAantalStappen();
            spel.setComputer(new MiniMaxComputer(Kleur.WIT));
            spel.getComputer().setHeuristicCalculator(new SimpleHeuristicCalculator());
            spel.getComputer().setAantalStappen(aantalStappen);
            startComputerWorker();*/
        }

        this.toFront();

    }

    /*
     * Start de computerworker die op de achtergrond de zet voor de computer zal berekenen
     */
    private void startComputerWorker() {
        System.out.println("Start computerworker");
        comboBox.setEnabled(false);
        computerWorker = createComputerWorker();
        computerWorker.execute();
    }

    private ComputerWorker createComputerWorker() {

        ComputerWorker computerWorker = new ComputerWorker(spel, computerProgressBar);
        computerWorker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())
                        && (SwingWorker.StateValue.DONE.equals(evt.getNewValue()))) {
                    comboBox.setEnabled(true);

                    if (treeFrame != null) {
                        treeFrame.dispose();
                    }
                 //   treeFrame = new TreeFrame(spel.getBord());
                    System.out.printf("herladen...");
                    herlaad();

                }
            }
        });
        return computerWorker;

    }


    /*
         * Toont een venster bij het einde van het spel
         */
    private void toonWinVenster() {

        JOptionPane.showMessageDialog(null, spel.getWinnaarTekst());

    }


    public static void main(String[] args) {
        OthelloFrame frame = new OthelloFrame();
    }
}
