package view.swing;

import ai.computer.impl.HeuristicComputer;
import ai.computer.impl.MiniMaxAlphaBetaComputer;
import ai.computer.impl.MiniMaxComputer;
import ai.computer.impl.NewMiniMaxComputer;
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

    public OthelloFrame() throws HeadlessException {
        this.spel = new Spel();
        this.spelerLabel = new JLabel("Witte speler aan de beurt");
        othelloBord = new OthelloBord(spel, this);
        init();
    }

    private void init() {
        this.setTitle("Othello");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel informationPanel = new JPanel();
        informationPanel.add(spelerLabel);
        this.add(maakMenu(), BorderLayout.NORTH);
        this.add(othelloBord, BorderLayout.CENTER);
        this.add(informationPanel, BorderLayout.SOUTH);
        this.pack();

        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


        herlaad();
        toonComputerOpties();
    }

    /*
     * Maakt klein menubar met menu-items
     */
    private JMenuBar maakMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Bestand");

        JMenuItem nieuw = new JMenuItem("Nieuw");
        nieuw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spel = new Spel();
                othelloBord.setSpel(spel);
                herlaad();
                toonComputerOpties();
            }
        });
        JMenuItem exit = new JMenuItem("Afsluiten");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        file.add(nieuw);
        file.add(exit);
        menuBar.add(file);

        return menuBar;

    }

    /*
     * Toont een scherm met keuzes voor computertegenstanders
     */
    private void toonComputerOpties() {
        Object[] computerOpties = {"MiniMax AlphaBeta", "MiniMax", "Heuristic", "MiniMax (Chim)"};

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
                break;
            case 1:
                spel.setComputer(new MiniMaxComputer(Kleur.ZWART));
                break;
            case 2:
                spel.setComputer(new HeuristicComputer(Kleur.ZWART));
                break;
            case 3:
                spel.setComputer(new NewMiniMaxComputer());
                break;

        }
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
            /*if (spel.getWinnaar() == Kleur.WIT) {
                spelerLabel.setText("De witte speler heeft gewonnen");
            } else if (spel.getWinnaar() == Kleur.ZWART) {
                spelerLabel.setText("De zwarte speler heeft gewonnen");

            } else {
                spelerLabel.setText("Gelijkstand!");

            }*/
            spelerLabel.setText(spel.getWinnaarTekst());
        }

        this.revalidate();
        this.repaint();
        if (spel.isSpelGedaan()) {
            toonWinVenster();
        } else if (spel.getKleurAanDeBeurt() == Kleur.ZWART) {
            startComputerWorker();
        }


    }

    /*
     * Start de computerworker die op de achtergrond de zet voor de computer zal berekenen
     */
    private void startComputerWorker() {
        ComputerWorker computerWorker = new ComputerWorker(spel);
        computerWorker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())
                        && (SwingWorker.StateValue.DONE.equals(evt.getNewValue()))) {
                    System.out.println("updating!");
                    herlaad();
                }

            }
        });
        computerWorker.execute();

    }

    /*
     * Toont een venster bij het einde van het spel
     */
    private void toonWinVenster() {

        /*if (spel.getWinnaar() == Kleur.WIT) {
            JOptionPane.showMessageDialog(null, "De witte speler heeft gewonnen");
        } else if (spel.getWinnaar() == Kleur.ZWART) {
            JOptionPane.showMessageDialog(null, "De zwarte speler heeft gewonnen");

        } else {
            JOptionPane.showMessageDialog(null, "Gelijkstand!");

        }*/
        JOptionPane.showMessageDialog(null, spel.getWinnaarTekst());

    }


    public static void main(String[] args) {
        OthelloFrame frame = new OthelloFrame();
    }
}
