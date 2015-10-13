package ai.computer;

import java.util.Observable;

/**
 * Created by Joachim De Schryver & Joran De Boever
 * on 13/10/15.
 */
public class ObservableAI extends Observable {
    private int progress = 0;
    private int duur;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getDuur() {
        return duur;
    }

    public void setDuur(int duur) {
        this.duur = duur;
    }
}
