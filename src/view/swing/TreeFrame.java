package view.swing;

import model.Bord;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Joachim De Schryver & Joran De Boever
 * on 14/10/15.
 */
public class TreeFrame extends JFrame {
    JTree tree;

    public TreeFrame(Bord bord) {

        tree = new JTree(bord);

        JScrollPane scrollPane = new JScrollPane(tree);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.setSize(500, 500);
        this.setVisible(true);


    }




}
