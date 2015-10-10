package view.swing;

import model.Kleur;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class OthelloButton extends JLabel {
    private int rij;
    private int kolom;
    private Kleur kleur = Kleur.LEEG;

    public OthelloButton(int rij, int kolom) {
        this.setOpaque(true);
        this.rij = rij;
        this.kolom = kolom;
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        this.setHorizontalAlignment(CENTER);
        this.setForeground(Color.RED);
        this.setFont(new Font("Arial", Font.BOLD, 32));
        this.setOpaque(true);

    }



    public void setKleur(Kleur kleur) {
        this.kleur = kleur;
        if (kleur == Kleur.ZWART) {
            this.setBackground(Color.BLACK);
        } else if (kleur == Kleur.WIT) {
            this.setBackground(Color.WHITE);
        } else {
            setBackground(Color.LIGHT_GRAY);
        }
    }

    public int getRij() {
        return rij;
    }

    public void setRij(int rij) {
        this.rij = rij;
    }

    public int getKolom() {
        return kolom;
    }

    public void setKolom(int kolom) {
        this.kolom = kolom;
    }
}
