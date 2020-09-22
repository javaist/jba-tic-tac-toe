package tictactoe;

import tictactoe.player.Medium;
import tictactoe.player.Easy;
import tictactoe.player.Human;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    static char letter = 'X';
    static tictactoe.player.Player playerX;
    static tictactoe.player.Player playerO;
    public static Field field;


    public static void main(String[] args) {
        boolean trigger = true;
        while (trigger) {
            System.out.println("Input command: ");
            String command = scanner.nextLine();
            String[] pars = command.split(" ");

            if ("exit".equals(pars[0])) {
                return;
            } else if (pars.length == 3) {
                if ("start".equals(pars[0]) &&
                        "user easy medium".contains(pars[1]) &&
                        "user easy medium".contains(pars[2])
                ) {
                    switch (pars[1]) {
                        case "user":
                            playerX = new Human('X');
                            break;
                        case "easy":
                            playerX = new Easy('X');
                            break;
                        case "medium":
                            playerX = new Medium('X');
                            break;
                    }
                    switch (pars[2]) {
                        case "user":
                            playerO = new Human('O');
                            break;
                        case "easy":
                            playerO = new Easy('O');
                            break;
                        case "medium":
                            playerO = new Medium('O');
                            break;
                    }
                    trigger = false;
                } else {
                    System.out.println("Bad parameters!");
                }
            } else {
                System.out.println("Bad parameters!");
            }
        }

        field = new Field("_________");


        gui();

        boolean in_game;
        int result;

        do {
            do {
                in_game = letter == 'X' ? playerX.turn() : playerO.turn();
            } while (in_game);
            gui();
            result = field.logic();
            letter = 'X' == letter ? 'O' : 'X';
        } while (result < 2);

        message(result);

    }


    static void gui() {
        String border = "-".repeat(Math.max(0, field.getFieldWidth() * 2 + 3));
        String left = "| ";
        String right = "|";

        System.out.println(border);
        for (int i = 0; i < field.getFieldWidth(); i++) {
            System.out.print(left);
            for (int j = 0; j < field.getFieldWidth(); j++) {
                System.out.print(field.getFieldString().charAt(j + i * field.getFieldWidth()));
                System.out.print(' ');
            }
            System.out.println(right);
        }
        System.out.println(border);
    }

    static void message(int choice) {
        switch (choice) {
            case 1:
                System.out.println("Game not finished");
                break;
            case 2:
                System.out.println("Draw");
                break;
            case 3:
                System.out.println("X wins");
                break;
            case 4:
                System.out.println("O wins");
                break;
            case 5:
                System.out.println("Impossible");
                break;
            default:
                System.out.println("Impossible status");
                break;
        }
    }
}



