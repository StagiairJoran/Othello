package view.swing;

import ai.Zet;
import model.Bord;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Created by Joachim De Schryver & Joran De Boever
 * on 14/10/15.
 */
public class TreeFrame extends JFrame {
    JTree tree;

    public TreeFrame(Bord bord) {

        changeTree(bord, 0);
        tree = new JTree(bord);

        JScrollPane scrollPane = new JScrollPane(tree);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.setSize(500, 500);
        this.setVisible(true);


    }

    private void changeTree(DefaultMutableTreeNode root, int level){
        Enumeration en = root.children();
        double besteWaarde;
        if(level%2 == 0){
            besteWaarde = Double.NEGATIVE_INFINITY;
        }else {
            besteWaarde = Double.POSITIVE_INFINITY;
        }

        while (en.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
            Zet zet = (Zet) node.getUserObject();
            if(level%2 == 0){
                besteWaarde = Math.max(besteWaarde, zet.getWaarde());
            }else {
                besteWaarde = Math.min(besteWaarde, zet.getWaarde());
            }
        }

        en = root.children();
        boolean besteWaardeGevonden = false;
        while (en.hasMoreElements()) {
            StringBuilder stringBuilder = new StringBuilder();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
            Zet zet = (Zet) node.getUserObject();

            stringBuilder.append(Math.round(zet.getWaarde() ));
            stringBuilder.append(zet.toString());

            if(zet.getWaarde() == besteWaarde && !besteWaardeGevonden){
                stringBuilder.append(" *");
                besteWaardeGevonden = true;
            }


            node.setUserObject(stringBuilder.toString());
            changeTree(node, level + 1);
        }
    }







}
