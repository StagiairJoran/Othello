package view.swing;

import ai.computer.impl.HeuristicComputer;
import ai.computer.impl.MiniMaxAlphaBetaComputer;
import ai.computer.impl.MiniMaxComputer;
import model.Kleur;
import model.OngeldigeZet;
import model.Spel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class OthelloFrame extends JFrame {

    Spel spel = new Spel();
    java.util.List<OthelloButton> othelloButtonList = new ArrayList<>();
    JLabel spelerLabel = new JLabel("Witte speler aan de beurt");

    public OthelloFrame() throws HeadlessException {
        this.setTitle("Othellooo");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(spel.getBord().getGrootteBord(), spel.getBord().getGrootteBord()));
        for (int i = 0; i < spel.getBord().getGrootteBord(); i++) {
            for (int j = 0; j < spel.getBord().getGrootteBord(); j++) {
                OthelloButton button = new OthelloButton(i, j);
                button.addMouseListener(new OthelloButtonListener());
                button.setEnabled(false);
                panel.add(button);
                othelloButtonList.add(button);
            }
        }
        JPanel informationPanel = new JPanel();
        informationPanel.add(spelerLabel);
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Bestand");

        JMenuItem nieuw = new JMenuItem("Nieuw");
        nieuw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spel = new Spel();
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
        this.add(menuBar, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
        this.add(informationPanel, BorderLayout.SOUTH);
        this.pack();

        this.setSize(600, 600);
        this.setLocationRelativeTo(null);

        this.setVisible(true);

        herlaad();
        toonComputerOpties();

    }

    private void toonComputerOpties() {
        Object[] computerOpties = {"MiniMax AlphaBeta", "MiniMax", "Heuristic"};

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
            default:
                spel.setComputer(new HeuristicComputer(Kleur.ZWART));
                break;
        }
    }


    private void herlaad() {

        for (OthelloButton button : othelloButtonList) {
            Kleur kleur = spel.getBord().getKleurOpPositie(button.getRij(), button.getKolom());
            button.setKleur(kleur);
            button.setText("");
            if (spel.getBord().isGeldigeZet(button.getRij(), button.getKolom(), spel.getKleurAanDeBeurt())) {

                button.setText("X");


            }

        }

        if (spel.getKleurAanDeBeurt() == Kleur.WIT) {
            spelerLabel.setText("Witte speler aan de beurt");
        } else {
            spelerLabel.setText("Zwarte speler aan de beurt");
        }
        this.revalidate();
        this.repaint();


        if (spel.isSpelGedaan()) {
            if (spel.getWinnaar() == Kleur.WIT) {
                JOptionPane.showMessageDialog(null, "De witte speler heeft gewonnen");
            } else if (spel.getWinnaar() == Kleur.ZWART) {
                JOptionPane.showMessageDialog(null, "De zwarte speler heeft gewonnen");

            } else {
                JOptionPane.showMessageDialog(null, "Gelijkstand!");

            }
        }
    }

    class OthelloButtonListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {
            OthelloButton othelloButton = (OthelloButton) e.getSource();
            try {
                spel.zetPion(othelloButton.getRij(), othelloButton.getKolom());
                herlaad();
                if (spel.getKleurAanDeBeurt() == Kleur.ZWART) {
                    spel.doeComputerZet();
                    herlaad();
                }
            } catch (OngeldigeZet ongeldigeZet) {
                System.err.println("Ongeldige zet");
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public static void main(String[] args) {
        OthelloFrame frame = new OthelloFrame();
    }
}
