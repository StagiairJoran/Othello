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
            System.out.println(game.getBord());
            doeZet();
        }
    }

    private void doeZet(){
        int rij = -1;
        int kolom = -1;
        while (rij < 0 || kolom < 0) {
        System.out.println(game.getKleurAanDeBeurt() + " is aan de beurt.");
        System.out.println("Welke positie wilt u een pion plaatsen? (N to exit)");
        String positie = scanner.nextLine();

            try {
                kolom = zetKolomLetterOmNaarGetal(positie.charAt(0));
                rij = Integer.parseInt(positie.substring(1, 2)) - 1;
            } catch (OngeldigeZet ongeldigeZet) {
                System.err.println(ongeldigeZet);
            }
        }
        game.zetPion(rij, kolom);

    }

    private int zetKolomLetterOmNaarGetal(char letter) throws OngeldigeZet {

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
                throw new OngeldigeZet("Alleen letters van a tot h toegestaan.");
        }
    }
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        consoleView.startSpel();

    }
}
