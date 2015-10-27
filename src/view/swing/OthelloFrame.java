package view.swing;

import ai.computer.api.Computer;
import ai.computer.impl.MiniMaxComputerJoran;
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
    MenuBar menuBar;
    TreeFrame treeFrame;
    int teller = 0;
    protected boolean toonTreeFrame = false;
    protected Computer witteComputer;
    protected Computer zwarteComputer = new MiniMaxComputerJoran(Kleur.ZWART);

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
        this.menuBar = new MenuBar(this);
        this.add(menuBar, BorderLayout.NORTH);
        this.add(othelloBord, BorderLayout.CENTER);

        JPanel paneel = new JPanel();
        paneel.add(computerProgressBar, BorderLayout.NORTH);
        paneel.add(informationPanel, BorderLayout.SOUTH);
        this.add(paneel, BorderLayout.SOUTH);

        this.pack();
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

      //  toonComputerOpties();
        InstellingenFrame instellingenFrame = new InstellingenFrame(this);
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



    }


    /*
     * Start de computerworker die op de achtergrond de zet voor de computer zal berekenen
     */
    private void doeComputerZet() {

        ComputerWorker computerWorker;
        if (spel.getKleurAanDeBeurt() == Kleur.ZWART){
            computerWorker  = new ComputerWorker(spel, computerProgressBar, zwarteComputer);
        }else {
            computerWorker = new ComputerWorker(spel, computerProgressBar, witteComputer);
        }
        computerWorker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && (SwingWorker.StateValue.DONE.equals(evt.getNewValue()))) {

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

    public Computer getComputer(Kleur kleur) {
        if(kleur == Kleur.WIT){
            return witteComputer;
        }else {
            return zwarteComputer;
        }
    }

    public void setComputer(Kleur kleur, Computer computer) {
        if(kleur == Kleur.WIT){
           witteComputer = computer;
        }else {
             zwarteComputer = computer;
        }
    }

    public static void main(String[] args) {
        OthelloFrame frame = new OthelloFrame();
    }
}
