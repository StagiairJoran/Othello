import ai.Computer;
import model.Kleur;
import model.Spel;
import model.Zet;

/**
 * Created by jorandeboever
 * on 29/09/15.
 */
public class main {

    public static void main(String[] args) {

        Spel spel = new Spel();
        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < 13; i++) {
                boolean spelIsGedaan = spel.isSpelGedaan();
                if (spelIsGedaan) {
                    if (spel.getWinnaar() == Kleur.WIT) {
                        System.out.println("De witte computer heeft gewonnen. Witte Score = " + spel.getScore(Kleur.WIT) + ", Zwarte Score = " + spel.getScore(Kleur.ZWART));
                    } else if (spel.getWinnaar() == Kleur.ZWART) {
                        System.out.println("De zwarte computer heeft gewonnen");
                    } else {
                        System.out.println("Gelijkstand!");
                    }
                } else {
                    if (spel.getKleurAanDeBeurt() == Kleur.WIT) {
                        Computer ai = new Computer(Kleur.WIT);
                        Zet zet = ai.doeZet(spel.getBord());
                        if (zet != null) {
                            spel.zetPion(zet.getRij(), zet.getKolom());
                        }
                    } else {
                        Computer ai = new Computer(Kleur.ZWART);
                        Zet zet = ai.doeZet(spel.getBord());
                        if (zet != null) {
                            spel.zetPion(zet.getRij(), zet.getKolom());
                        }
                    }
                }
            }
        }
    }
}
