package view.swing;

import ai.Zet;
import javafx.beans.Observable;
import model.Kleur;
import model.OngeldigeZet;
import model.Spel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by jorandeboever
 * on 10/10/15.
 */
public class OthelloBord extends JPanel {
    OthelloFrame frame;
    Spel spel;
    java.util.List<OthelloButton> othelloButtonList = new ArrayList<>();


    public OthelloBord(Spel spel, OthelloFrame othelloFrame) {
        this.spel = spel;
        this.frame = othelloFrame;
        init();
    }

    public void setSpel(Spel spel) {
        this.spel = spel;
    }

    private void init() {
        this.setLayout(new GridLayout(spel.getBord().getGrootteBord() + 1 , spel.getBord().getGrootteBord() + 1));
        this.add(new OthelloLabelButton(" "));

        for (int x = 1; x < 9; x++){
            this.add(new OthelloLabelButton(String.valueOf(x)));

        }

        for (int i = 0; i < spel.getBord().getGrootteBord(); i++) {
            this.add(new OthelloLabelButton(String.format("%c", Zet.zetRijOmNaarLetter(i + 1))));
            for (int j = 0; j < spel.getBord().getGrootteBord(); j++) {
                OthelloButton button = new OthelloButton(i, j);
                button.addMouseListener(new OthelloButtonListener());
                button.setEnabled(false);
                this.add(button);
                othelloButtonList.add(button);
            }
        }
    }

    public void herlaad() {
        for (OthelloButton button : othelloButtonList) {
            Kleur kleur = spel.getBord().getKleurOpPositie(button.getRij(), button.getKolom());
            button.setKleur(kleur);
            button.setText("");
            if (spel.getBord().isGeldigeZet(button.getRij(), button.getKolom(), spel.getKleurAanDeBeurt())) {
                if (spel.getKleurAanDeBeurt() == Kleur.WIT) {
                    button.setText("X");
                }


            }

        }
    }

    class OthelloButtonListener implements MouseListener {


        @Override
        public void mouseClicked(MouseEvent e) {
            if (spel.getKleurAanDeBeurt() == Kleur.WIT){
                OthelloButton othelloButton = (OthelloButton) e.getSource();
                try {
                    spel.zetPion(othelloButton.getRij(), othelloButton.getKolom());
                    frame.herlaad();

                } catch (OngeldigeZet ongeldigeZet) {
                    System.err.println("Ongeldige zet");
                }
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


}
