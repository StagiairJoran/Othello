package view.swing;

import ai.computer.api.Computer;
import ai.heuristic.api.HeuristicCalculator;
import model.Kleur;
import model.Spel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Joachim De Schryver & Joran De Boever
 * on 21/10/15.
 */
public class MenuBar extends JMenuBar {
    private List<JRadioButtonMenuItem> witteCalculatorItems = new ArrayList<>();
    private List<JRadioButtonMenuItem> zwarteCalculatorItems = new ArrayList<>();
    private List<JRadioButtonMenuItem> witteComputerItems = new ArrayList<>();
    private List<JRadioButtonMenuItem> zwarteComputerItems = new ArrayList<>();

    public MenuBar(OthelloFrame frame) {

        maakSpelMenu(frame);
        maakDebugMenu(frame);
    //    maakSpelersMenu(frame);
        herlaad(frame);
    }

    public void herlaad(OthelloFrame frame) {
        for (JRadioButtonMenuItem item : witteCalculatorItems) {
            if (frame.witteComputer != null && frame.witteComputer.getHeuristicCalculator().getClass().getSimpleName().equals(item.getText())) {
                item.setSelected(true);
            }
        }
        for (JRadioButtonMenuItem item : zwarteCalculatorItems) {
            if (frame.zwarteComputer != null && frame.zwarteComputer.getHeuristicCalculator().getClass().getSimpleName().equals(item.getText())) {
                item.setSelected(true);
            }
        }
        for (JRadioButtonMenuItem item : witteComputerItems) {

            if (frame.witteComputer != null && frame.witteComputer.getClass().getSimpleName().equals(item.getText())) {
                item.setSelected(true);
            }
        }
        for (JRadioButtonMenuItem item : zwarteComputerItems) {
            if (frame.zwarteComputer != null && frame.zwarteComputer.getClass().getSimpleName().equals(item.getText())) {
                item.setSelected(true);
            }
        }
    }

    private void maakSpelersMenu(OthelloFrame frame) {
        // Menu voor Witte speler
        JMenu witteSpelerMenu = new JMenu("Witte speler");
        JMenu witteComputerMenu = new JMenu("Computer");
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButtonMenuItem geenComputerItem = new JRadioButtonMenuItem("geen");
        geenComputerItem.addActionListener(e -> {
            frame.witteComputer = null;
            this.herlaad(frame);

        });
        geenComputerItem.setSelected(true);
        buttonGroup.add(geenComputerItem);
        witteComputerMenu.add(geenComputerItem);
        witteComputerItems.add(geenComputerItem);
        for (Computer c : Computer.geefAlleComputers()) {
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(c.toString());
            buttonGroup.add(menuItem);
            menuItem.addActionListener(e -> {
                frame.witteComputer = c;
                frame.witteComputer.setKleur(Kleur.WIT);
                this.herlaad(frame);

            });
            witteComputerItems.add(menuItem);
            witteComputerMenu.add(menuItem);
        }
        witteSpelerMenu.add(witteComputerMenu);


        JMenu heuristicCalculatorMenu = new JMenu("Heuristic");
        ButtonGroup heuristicButtonGroup = new ButtonGroup();
        for (HeuristicCalculator calculator : HeuristicCalculator.geefAlleCalculators()) {
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(calculator.toString());
            heuristicButtonGroup.add(menuItem);
            menuItem.addActionListener(e -> {
                frame.witteComputer.setHeuristicCalculator(calculator);
                this.herlaad(frame);
            });

            witteCalculatorItems.add(menuItem);
            heuristicCalculatorMenu.add(menuItem);
        }
        witteSpelerMenu.add(heuristicCalculatorMenu);
        this.add(witteSpelerMenu);


        // Menu voor Zwarte speler
        JMenu zwarteSpelerMenu = new JMenu("Zwarte speler");
        JMenu zwarteComputerMenu = new JMenu("Computer");
        ButtonGroup zwarteButtonGroup = new ButtonGroup();

        for (Computer c : Computer.geefAlleComputers()) {
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(c.toString());
            zwarteButtonGroup.add(menuItem);
            menuItem.addActionListener(e -> {
                frame.zwarteComputer = c;
                frame.zwarteComputer.setKleur(Kleur.ZWART);
                this.herlaad(frame);
            });

            zwarteComputerMenu.add(menuItem);
            zwarteComputerItems.add(menuItem);

        }
        zwarteSpelerMenu.add(zwarteComputerMenu);


        JMenu heuristicCalculatorMenuZwart = new JMenu("Heuristic");
        ButtonGroup heuristicButtonGroupZwart = new ButtonGroup();
        for (HeuristicCalculator calculator : HeuristicCalculator.geefAlleCalculators()) {
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(calculator.toString());
            heuristicButtonGroupZwart.add(menuItem);
            menuItem.addActionListener(e -> {
                frame.zwarteComputer.setHeuristicCalculator(calculator);
            });
            heuristicCalculatorMenuZwart.add(menuItem);
            zwarteCalculatorItems.add(menuItem);


        }
        zwarteSpelerMenu.add(heuristicCalculatorMenuZwart);
        this.add(zwarteSpelerMenu);
    }

    private void maakDebugMenu(OthelloFrame frame) {
        JMenu debugMenu = new JMenu("Debug");
        JMenuItem jtreeCheckBoxItem = new JCheckBoxMenuItem("Jtree");
        jtreeCheckBoxItem.addActionListener(e -> {
            AbstractButton aButton = (AbstractButton) e.getSource();
            frame.toonTreeFrame = aButton.getModel().isSelected();
        });
        debugMenu.add(jtreeCheckBoxItem);
        this.add(debugMenu);
    }

    private void maakSpelMenu(final OthelloFrame frame) {
        JMenu spelMenu = new JMenu("Spel");

        JMenuItem nieuw = new JMenuItem("Nieuw");
        nieuw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.spel = new Spel();
                frame.teller = 0;
                frame.othelloBord.setSpel(frame.spel);
                new InstellingenFrame(frame);
                frame.herlaad();
                frame.witteComputer = null;
            }
        });
        JMenuItem exit = new JMenuItem("Afsluiten");
        exit.addActionListener(e -> System.exit(0));

        JMenuItem instellingen = new JMenuItem("Instellingen");
        instellingen.addActionListener(e -> new InstellingenFrame(frame));

        spelMenu.add(nieuw);
        spelMenu.add(instellingen);
        spelMenu.add(exit);
        this.add(spelMenu);
    }
}
