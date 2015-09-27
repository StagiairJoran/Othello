package view.swing;

import javafx.scene.layout.Border;
import model.Bord;
import model.Kleur;
import model.OngeldigeZet;
import model.Spel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class OthelloFrame extends JFrame {

    Spel spel = new Spel();
    java.util.List<OthelloButton> othelloButtonList = new ArrayList<>();
    JLabel spelerLabel = new JLabel("Zwarte speler aan de beurt");

    public OthelloFrame() throws HeadlessException {
        this.setTitle("Othello");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                OthelloButton button = new OthelloButton(i, j);
                button.addMouseListener(new OthelloButtonListener());


                button.setEnabled(false);
                if (spel.getBord().getKleurOpPositie(i, j) == Kleur.WIT) {

                } else if (spel.getBord().getKleurOpPositie(i, j) == Kleur.ZWART) {


                } else {
                    if (spel.getBord().isGeldigeZet(i, j, spel.getKleurAanDeBeurt())) {
                    }

                }

                panel.add(button);
                othelloButtonList.add(button);
            }
        }
        JPanel informationPanel = new JPanel();
        informationPanel.add(spelerLabel);

        this.add(panel, BorderLayout.CENTER);
        this.add(informationPanel, BorderLayout.SOUTH);
        this.pack();
        this.setSize(600, 600);
        this.setVisible(true);
        herlaad();
    }

    public void herlaad() {
        if(spel.isSpelGedaan()){
            if(spel.getWinnaar() == Kleur.WIT){
                JOptionPane.showMessageDialog(null, "De witte speler heeft gewonnen");
            }else if(spel.getWinnaar() == Kleur.ZWART){
                JOptionPane.showMessageDialog(null, "De zwarte speler heeft gewonnen");

            }else {
                JOptionPane.showMessageDialog(null, "Gelijkstand!");

            }
        }else {
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
            this.repaint();
        }
    }


    class OthelloButtonListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {
            OthelloButton othelloButton = (OthelloButton) e.getSource();
            try {
                spel.zetPion(othelloButton.getRij(), othelloButton.getKolom());
                herlaad();
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
