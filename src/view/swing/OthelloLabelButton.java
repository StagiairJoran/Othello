package view.swing;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Joachim De Schryver & Joran De Boever
 * on 14/10/15.
 */
public class OthelloLabelButton extends JLabel {

    public OthelloLabelButton(String text) {
        super(text);
        init();
    }

    private void init(){
        this.setHorizontalAlignment(CENTER);
        this.setFont(new Font("Arial", Font.PLAIN, 21));

    }
}
