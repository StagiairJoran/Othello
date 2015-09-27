package view;

import model.Kleur;
import model.Spel;
import model.OngeldigeZet;

import java.util.Scanner;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class ConsoleView {
    private Scanner scanner = new Scanner(System.in);
    private Spel spel = new Spel();

    private void startSpel(){
        System.out.println("Welkom bij Othello! Wit mag beginnen!");
        boolean spelIsGedaan = spel.isSpelGedaan();
        while (!spelIsGedaan){
            System.out.print("\t\tWIT: " + spel.getScore(Kleur.WIT) + "\t\t\t ZWART: " + spel.getScore(Kleur.ZWART) + "\n");
            System.out.println(spel.getBord().geefBordMetHints(spel.getKleurAanDeBeurt()));

            try {
                doeZet();
            } catch (OngeldigeZet ongeldigeZet) {
                System.err.println("Ongeldige zet: Probeer alsjeblieft opnieuw.");
            }
            spelIsGedaan = spel.isSpelGedaan();
        }
        eindigSpel();
    }

    private void eindigSpel(){
        int scoreWit = spel.getScore(Kleur.WIT);
        int scoreZwart = spel.getScore(Kleur.ZWART);
        if(scoreWit > scoreZwart){
            System.out.println("Wit heeft gewonnen!");
        }else {
            System.out.println("Zwart heeft gewonnen!");

        }
    }

    private void doeZet() throws OngeldigeZet {
        System.out.println(spel.getKleurAanDeBeurt() + " is aan de beurt.");
        int rij = -1;
        int kolom = -1;
        while (rij < 0 || kolom < 0) {


        System.out.println("Welke positie wilt u een pion plaatsen? ");
        String positie = scanner.nextLine();
            
            try {
                kolom = zetKolomLetterOmNaarGetal(positie.toLowerCase().charAt(0));
                rij = Integer.parseInt(positie.substring(1, 2)) - 1;
            } catch (OngeldigeKolomLetter ongeldigeKolomLetter) {
                System.err.println(ongeldigeKolomLetter);
            }
        }
        spel.zetPion(rij, kolom);

    }

    private int zetKolomLetterOmNaarGetal(char letter) throws OngeldigeKolomLetter {

        switch (letter) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                throw new OngeldigeKolomLetter("Alleen letters van a tot h toegestaan.");
        }
    }
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        consoleView.startSpel();

    }
}
