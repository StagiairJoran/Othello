package view;

import model.Game;
import model.OngeldigeZet;

import java.util.Scanner;

/**
 * Created by jorandeboever
 * on 27/09/15.
 */
public class ConsoleView {
    private Scanner scanner = new Scanner(System.in);
    private Game game = new Game();

    private void startSpel(){
        System.out.println("Welkom bij Othello! Wit mag beginnen!");

        while (true){
            System.out.println(game.getBord().geefBordMetHints(game.getKleurAanDeBeurt()));

            try {
                doeZet();
            } catch (OngeldigeZet ongeldigeZet) {
                System.err.println("Ongeldige zet: Probeer alsjeblieft opnieuw.");
            }
        }
    }

    private void doeZet() throws OngeldigeZet {
        System.out.println(game.getKleurAanDeBeurt() + " is aan de beurt.");
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
        game.zetPion(rij, kolom);

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
