package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class Richting {
    public int x;
    public int y;

    public Richting(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static List<Richting> getMogelijkeRichtingen(){
        List<Richting> mogelijkeRichtingen = new ArrayList<>();
        mogelijkeRichtingen.add(new Richting(-1, -1));
        mogelijkeRichtingen.add(new Richting(-1, 0));
        mogelijkeRichtingen.add(new Richting(-1, 1));
        mogelijkeRichtingen.add(new Richting(0, -1));
        mogelijkeRichtingen.add(new Richting(0, 1));
        mogelijkeRichtingen.add(new Richting(1, -1));
        mogelijkeRichtingen.add(new Richting(1, 0));
        mogelijkeRichtingen.add(new Richting(1, 1));
        return mogelijkeRichtingen;
    }
}
