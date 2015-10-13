package ai.computer.api;

import ai.Zet;
import model.Bord;

import java.util.Observable;

/**
 * Created by jorandeboever
 * on 7/10/15.
 */
public interface Computer {
    Zet berekenZet(Bord bord);

    int getDuur();
    int getProgress();
}
