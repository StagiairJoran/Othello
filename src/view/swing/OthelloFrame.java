package view.swing;

import ai.computer.api.Computer;
import ai.computer.impl.HeuristicComputer;
import ai.computer.impl.MiniMaxAlphaBetaComputer;
import ai.computer.impl.MiniMaxComputer;
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
    ComputerProgressBar computerProgressBar = new ComputerProgressBar();
    Computer computer;
    JComboBox comboBox;
    JLabel comboLabel;

    public OthelloFrame() throws HeadlessException {
        this.spel = new Spel();
        this.spelerLabel = new JLabel("Witte speler aan de beurt");
        othelloBord = new OthelloBord(spel, this);
        computer = new MiniMaxComputer(Kleur.ZWART);
        this.comboBox = maakComboBox();
        this.comboLabel = new JLabel("Aantal stappen:");
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

    private JComboBox maakComboBox(){
        Integer[] getallen = {1,2,3,4,5,6,7,8,9,10};
        JComboBox comboBox = new JComboBox(getallen);
        comboBox.setSelectedItem(computer.getAantalStappen());
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                computer.setAantalStappen((Integer) comboBox.getSelectedItem());
            }
        });
        return comboBox;
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
        Object[] computerOpties = {"MiniMax AlphaBeta", "MiniMax", "Heuristic"};

        comboBox.setVisible(true);
        comboLabel.setVisible(true);
        int aantalStappen = computer.getAantalStappen();

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
                this.computer = new MiniMaxAlphaBetaComputer(Kleur.ZWART);
                this.setTitle("Othello MiniMax Alpha Beta");
                break;
            case 1:
                spel.setComputer(new MiniMaxComputer(Kleur.ZWART));
                this.computer = new MiniMaxComputer(Kleur.ZWART);
                this.setTitle("Othello MiniMax");

                break;
            default:
                comboBox.setVisible(false);
                comboLabel.setVisible(false);
                spel.setComputer(new HeuristicComputer(Kleur.ZWART));
                this.computer = new HeuristicComputer(Kleur.ZWART);
                this.setTitle("Othello MiniMax Heuristic");

                break;
        }
        computer.setAantalStappen(aantalStappen);
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
            startComputerWorker();
        }


    }

    /*
     * Start de computerworker die op de achtergrond de zet voor de computer zal berekenen
     */
    private void startComputerWorker() {
        comboBox.setEnabled(false);

        ComputerWorker computerWorker = new ComputerWorker(spel, computerProgressBar, computer);
        computerWorker.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName())
                        && (SwingWorker.StateValue.DONE.equals(evt.getNewValue()))) {
                    System.out.println("updating!");
                    comboBox.setEnabled(true);

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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException e) {
            System.err.println("Stijl niet gelukt");
        }
        OthelloFrame frame = new OthelloFrame();
    }
}
