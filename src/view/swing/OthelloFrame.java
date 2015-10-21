package view.swing;

import ai.computer.api.Computer;
import ai.computer.impl.HeuristicComputer;
import ai.computer.impl.MiniMaxAlphaBetaComputer;
import ai.computer.impl.MiniMaxComputer;
import ai.computer.impl.NewMiniMaxComputer;
import ai.heuristic.api.HeuristicCalculator;
import ai.heuristic.impl.CompleteHeuristicCalculator;
import model.Kleur;
import model.Spel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    MenuBar menuBar;
    TreeFrame treeFrame;
    int teller = 0;
    protected boolean toonTreeFrame = false;
    protected Computer witteComputer;
    protected Computer zwarteComputer = new MiniMaxComputer(Kleur.ZWART);

    public OthelloFrame() throws HeadlessException {
        this.spel = new Spel();
        this.spelerLabel = new JLabel("Witte speler aan de beurt");
        othelloBord = new OthelloBord(spel, this);
        this.comboBox = maakComboBox();
        this.comboLabel = new JLabel("Aantal stappen:");
        init();

    }


    private void init() {
        this.setTitle("Othello");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel informationPanel = new JPanel();
        informationPanel.add(spelerLabel);
        this.menuBar = new MenuBar(this);
        this.add(menuBar, BorderLayout.NORTH);
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

        toonComputerOpties();
        herlaad();


    }

    private JComboBox maakComboBox() {
        Integer[] getallen = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        JComboBox comboBox = new JComboBox(getallen);
        comboBox.setSelectedItem(zwarteComputer.getAantalStappen());
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zwarteComputer.setAantalStappen((Integer) comboBox.getSelectedItem());
            }
        });
        return comboBox;
    }

    /*
     * Maakt klein menubar met menu-items
     */
    private JMenuBar maakMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu spelMenu = new JMenu("Spel");

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
        spelMenu.add(nieuw);
        spelMenu.add(exit);
        menuBar.add(spelMenu);


        JMenu debugMenu = new JMenu("Debug");
        JMenuItem jtreeCheckBoxItem = new JCheckBoxMenuItem("Jtree");
        jtreeCheckBoxItem.addActionListener(e -> {
            AbstractButton aButton = (AbstractButton) e.getSource();
            toonTreeFrame = aButton.getModel().isSelected();
        });
        debugMenu.add(jtreeCheckBoxItem);
        menuBar.add(debugMenu);


        // Menu voor Witte speler
        JMenu witteSpelerMenu = new JMenu("Witte speler");
        JMenu computerItem = new JMenu("Computer");
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButtonMenuItem leeg = new JRadioButtonMenuItem("geen");
        leeg.addActionListener(e -> {
            witteComputer = null;
        });
        leeg.setSelected(true);
        buttonGroup.add(leeg);
        computerItem.add(leeg);
        for (Computer c : Computer.geefAlleComputers()){
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(c.toString());
            buttonGroup.add(menuItem);
            menuItem.addActionListener(e -> {
                witteComputer = c;
                witteComputer.setKleur(Kleur.WIT);
            });
            computerItem.add(menuItem);
        }
        witteSpelerMenu.add(computerItem);


        JMenu heuristicCalculatorMenu = new JMenu("Heuristic");
        ButtonGroup heuristicButtonGroup = new ButtonGroup();
        for(HeuristicCalculator calculator : HeuristicCalculator.geefAlleCalculators()){
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(calculator.toString());
            heuristicButtonGroup.add(menuItem);
            menuItem.addActionListener(e -> {
                witteComputer.setHeuristicCalculator(calculator);
            });
            try  {
                if(witteComputer.getHeuristicCalculator().getClass() == calculator.getClass()){
                    menuItem.setSelected(true);
                }
            }catch (NullPointerException ignored){

            }

            heuristicCalculatorMenu.add(menuItem);
        }
        witteSpelerMenu.add(heuristicCalculatorMenu);
        menuBar.add(witteSpelerMenu);


        // Menu voor Zwarte speler
        JMenu zwarteSpelerMenu = new JMenu("Zwarte speler");
        JMenu zwarteComputerMenu = new JMenu("Computer");
        ButtonGroup zwarteButtonGroup = new ButtonGroup();

        for (Computer c : Computer.geefAlleComputers()){
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(c.toString());
            zwarteButtonGroup.add(menuItem);
            menuItem.addActionListener(e -> {
                zwarteComputer = c;
                zwarteComputer.setKleur(Kleur.ZWART);
            });
            if (c.getClass() == zwarteComputer.getClass()){
                menuItem.setSelected(true);
            }
            zwarteComputerMenu.add(menuItem);
        }
        zwarteSpelerMenu.add(zwarteComputerMenu);


        JMenu heuristicCalculatorMenuZwart = new JMenu("Heuristic");
        ButtonGroup heuristicButtonGroupZwart = new ButtonGroup();
        for(HeuristicCalculator calculator : HeuristicCalculator.geefAlleCalculators()){
            JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(calculator.toString());
            heuristicButtonGroupZwart.add(menuItem);
            menuItem.addActionListener(e -> {
                zwarteComputer.setHeuristicCalculator(calculator);
            });
            try  {
                if(zwarteComputer.getHeuristicCalculator().getClass() == calculator.getClass()){
                    menuItem.setSelected(true);
                }
            }catch (NullPointerException ignored){

            }
            heuristicCalculatorMenuZwart.add(menuItem);

        }
        zwarteSpelerMenu.add(heuristicCalculatorMenuZwart);
        menuBar.add(zwarteSpelerMenu);


        return menuBar;

    }


    /*
     * Toont een scherm met keuzes voor computertegenstanders
     */
    protected void toonComputerOpties() {
        Object[] computerOpties = {"MiniMax AlphaBeta", "MiniMax", "Heuristic", "NewMiniMax"};

        comboBox.setVisible(true);
        comboLabel.setVisible(true);
        int aantalStappen = zwarteComputer.getAantalStappen();

        int keuze = JOptionPane.showOptionDialog(this,
                "Tegen welke computer wil je spelen?",
                "Computerkeuze",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                computerOpties, computerOpties[0]);
        switch (keuze) {
            case 0:
                zwarteComputer = new MiniMaxAlphaBetaComputer(Kleur.ZWART);
                this.setTitle("Othello MiniMax Alpha Beta");

                break;
            case 1:
                zwarteComputer = new MiniMaxComputer(Kleur.ZWART);
                this.setTitle("Othello MiniMax");
                break;
            case 3:
                zwarteComputer = new NewMiniMaxComputer(Kleur.ZWART);
                this.setTitle("Othello NewMiniMax");

                break;
            default:
                comboBox.setVisible(false);
                comboLabel.setVisible(false);
                zwarteComputer = new HeuristicComputer(Kleur.ZWART);
                this.setTitle("Othello MiniMax Heuristic");

                break;
        }
        zwarteComputer.setAantalStappen(aantalStappen);

    }


    /*
     * Tekent alles opnieuw waneer er een wijziging gebeurt in het model
     */
    public void herlaad() {
        othelloBord.herlaad();
        menuBar.herlaad(this);

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


            doeComputerZet();

        } else if (spel.getKleurAanDeBeurt() == Kleur.WIT && witteComputer != null) {
            // Computer tegen computer


            doeComputerZet();
        }

        this.toFront();

    }


    /*
     * Start de computerworker die op de achtergrond de zet voor de computer zal berekenen
     */
    private void doeComputerZet() {
        comboBox.setEnabled(false);

        ComputerWorker computerWorker;
        if (spel.getKleurAanDeBeurt() == Kleur.ZWART){
            computerWorker  = new ComputerWorker(spel, computerProgressBar, zwarteComputer);
        }else {
            computerWorker = new ComputerWorker(spel, computerProgressBar, witteComputer);
        }
        computerWorker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && (SwingWorker.StateValue.DONE.equals(evt.getNewValue()))) {
                comboBox.setEnabled(true);

                if (treeFrame != null) {
                    treeFrame.dispose();
                }
                if (toonTreeFrame) treeFrame = new TreeFrame(spel.getBord());

                herlaad();

            }
        });

        computerWorker.execute();
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
