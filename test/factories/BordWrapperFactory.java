package factories;

import model.BordWrapper;
import model.Kleur;
import model.Speelvak;

/**
 * Created by jorandeboever
 * on 7/10/15.
 */
public class BordWrapperFactory {
    public static BordWrapper getVolBord(Kleur kleur){
        BordWrapper bordWrapper = new BordWrapper();
        Speelvak[][] speelvakken = new Speelvak[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Speelvak speelvak = new Speelvak(i, j);
                speelvak.setKleur(kleur);
                speelvakken[i][j] = speelvak;

            }
        }
        bordWrapper.setSpeelVakken(speelvakken);
        return bordWrapper;
    }
}
