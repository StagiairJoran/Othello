package view.swing;

import ai.computer.api.Computer;
import ai.computer.impl.HeuristicComputer;
import ai.computer.impl.MiniMaxAlphaBetaComputer;
import ai.computer.impl.MiniMaxComputer;
import ai.computer.impl.NewMiniMaxComputer;
import ai.heuristic.api.HeuristicCalculator;
import com.sun.tools.javac.comp.Flow;
import model.Bord;
import model.Kleur;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.*;

/**
 * Created by Joachim De Schryver & Joran De Boever
 * on 21/10/15.
 */
public class InstellingenFrame extends JFrame {
    JRadioButton witteSpelerButton;
    private java.util.List<JRadioButton> witteCalculatorItems = new ArrayList<>();
    private java.util.List<JRadioButton> zwarteCalculatorItems = new ArrayList<>();

    private java.util.List<JRadioButton> witteComputerItems = new ArrayList<>();
    private java.util.List<JRadioButton> zwarteComputerItems = new ArrayList<>();


    public InstellingenFrame(OthelloFrame frame) {
        frame.comboBox.setVisible(true);
        frame.comboLabel.setVisible(true);

        JPanel wit = new JPanel(new BorderLayout());
        JLabel witteSpelerLabel = new JLabel("Witte speler");
        witteSpelerLabel.setFont(new Font("Jokerman", Font.BOLD, 18));
        wit.add(witteSpelerLabel, BorderLayout.NORTH);
        wit.add(maakComputerOpties(frame, Kleur.WIT), BorderLayout.CENTER);
        wit.add(maakCalculatorPanel(frame, Kleur.WIT), BorderLayout.SOUTH);
        this.add(wit, BorderLayout.WEST);


        JPanel zwart = new JPanel(new BorderLayout());
        JLabel zwarteSpelerLabel = new JLabel("Zwarte speler");
        zwarteSpelerLabel.setFont(new Font("Jokerman", Font.BOLD, 18));
        zwart.add(zwarteSpelerLabel, BorderLayout.NORTH);
        zwart.add(maakComputerOpties(frame, Kleur.ZWART), BorderLayout.CENTER);
        zwart.add(maakCalculatorPanel(frame, Kleur.ZWART), BorderLayout.SOUTH);
        this.add(zwart, BorderLayout.EAST);

        JButton exitButton = new JButton("Bevestig");
        exitButton.addActionListener(e -> {
            this.dispose();
            frame.setEnabled(true);
        });
        this.add(exitButton, BorderLayout.SOUTH);

        herlaad(frame);

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                frame.setEnabled(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });

        this.setSize(500, 300);

        frame.setEnabled(false);
        this.setAlwaysOnTop(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void herlaad(OthelloFrame frame){
        for (JRadioButton radioButton : witteCalculatorItems){
          try {
              radioButton.setEnabled(true);
              if (radioButton.getText().equals(frame.witteComputer.getHeuristicCalculator().getClass().getSimpleName()))
                  radioButton.setSelected(true);
          }catch (NullPointerException e){
              radioButton.setEnabled(false);
          }
        }
        for (JRadioButton radioButton : zwarteCalculatorItems){
            if(radioButton.getText().equals(frame.zwarteComputer.getHeuristicCalculator().getClass().getSimpleName())) radioButton.setSelected(true);
        }
        for (JRadioButton radioButton : witteComputerItems){
            try {
                if(radioButton.getText().equals(frame.witteComputer.getClass().getSimpleName())) radioButton.setSelected(true);

            }catch (NullPointerException ignored){


            }
        }

        for (JRadioButton radioButton : zwarteComputerItems){
            if(radioButton.getText().equals(frame.zwarteComputer.getClass().getSimpleName())) radioButton.setSelected(true);
        }
        this.repaint();
    }

    private JPanel maakCalculatorPanel(OthelloFrame frame, Kleur kleur) {
        ButtonGroup witteCalculatorGroup = new ButtonGroup();
        JPanel witteCalculatorOpties = new JPanel(new GridLayout(HeuristicCalculator.geefAlleCalculators().size() + 1,0));
        JLabel label = new JLabel("Calculator keuze");
        label.setFont(new Font("Jokerman", Font.PLAIN, 13));
        witteCalculatorOpties.add(label);

        for(HeuristicCalculator calculator : HeuristicCalculator.geefAlleCalculators()){
            JRadioButton computerButton = new JRadioButton(calculator.getClass().getSimpleName());

            if(kleur == Kleur.WIT){
                witteCalculatorItems.add(computerButton);
            }else {
                zwarteCalculatorItems.add(computerButton);
            }

            computerButton.addActionListener(e -> {
                frame.getComputer(kleur).setHeuristicCalculator(calculator);
                herlaad(frame);
            });
            witteCalculatorGroup.add(computerButton);
            witteCalculatorOpties.add(computerButton);
        }
        return witteCalculatorOpties;
    }

    private JPanel maakComputerOpties(OthelloFrame frame, Kleur kleur)  {
        ButtonGroup buttonGroup = new ButtonGroup();


        JPanel computerOpties = new JPanel(new GridLayout(Computer.geefAlleComputers().size() + 2,0));

        JLabel label = new JLabel("Computer keuze");
        label.setFont(new Font("Jokerman", Font.PLAIN, 12));
        computerOpties.add(label);
        witteSpelerButton = new JRadioButton("Speler");
        if(kleur == Kleur.WIT){
            witteSpelerButton.addActionListener(e -> {
                frame.setComputer(kleur, null);
                herlaad(frame);
            });
        }else {
            witteSpelerButton.setEnabled(false);
        }
        witteSpelerButton.setSelected(true);

        buttonGroup.add(witteSpelerButton);
        computerOpties.add(witteSpelerButton);
        for(Computer computer : Computer.geefAlleComputers()){
            JRadioButton computerButton = new JRadioButton(computer.getClass().getSimpleName());

            if(kleur == Kleur.WIT){
                witteComputerItems.add(computerButton);
            }else {
                zwarteComputerItems.add(computerButton);
            }

            computerButton.addActionListener(e -> {
                if(kleur == Kleur.WIT) {
                    computer.setKleur(Kleur.WIT);
                    frame.witteComputer = computer;

                }else {
                    computer.setKleur(Kleur.ZWART);
                    frame.zwarteComputer = computer;
                }
                herlaad(frame);
            });
            buttonGroup.add(computerButton);
            computerOpties.add(computerButton);


        }
        return computerOpties;
    }


}
