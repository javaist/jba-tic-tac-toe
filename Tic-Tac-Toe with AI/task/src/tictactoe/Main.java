package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static int fieldWidth;
    static String fieldString = "_________";
    static Scanner scanner = new Scanner(System.in);
    static char letter = 'X';
    static Player playerX;
    static Player playerO;


    public static void main(String[] args) {
        boolean trigger = true;
        while (trigger) {
            System.out.println("Input command: ");
            String command = scanner.nextLine();
            String[] pars = command.split(" ");

            if ("exit".equals(pars[0])) {
                return;
            } else if (pars.length == 3) {
                if ("start".equals(pars[0]
                ) && (
                        "easy".equals(pars[1]) || "user".equals(pars[1])
                ) && (
                        "easy".equals(pars[2]) || "user".equals(pars[2]))
                ) {
                    playerX = "easy".equals(pars[1]) ? new Easy('X') : new Human('X');
                    playerO = "easy".equals(pars[2]) ? new Easy('O') : new Human('O');
                    trigger = false;
                } else {
                    System.out.println("Bad parameters!");
                }
            } else {
                System.out.println("Bad parameters!");
            }
        }

        fieldWidth = (int) Math.sqrt(fieldString.length());

        gui();

        boolean in_game;
        int result;

        do {
            do {
                in_game = letter == 'X' ? playerX.turn() : playerO.turn();
            } while (in_game);
            gui();
            result = logic();
            letter = 'X' == letter ? 'O' : 'X';
        } while (result < 2);

        message(result);

    }

    static boolean winner(char letter) {
        String strike = String.valueOf(letter).repeat(Math.max(0, fieldWidth));

        // Turn to left
        char[] turned = new char[fieldString.length()];
        int left2right = 0;
        int right2left = 0;
        for (int i = 0; i < fieldWidth; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                turned[i + j * fieldWidth] = fieldString.charAt(j + i * fieldWidth);

                // check diagonal
                if (fieldWidth - 1 - i == j && fieldString.charAt(j + i * fieldWidth) == letter) {
                    right2left += 1;
                    if (right2left == fieldWidth) {
                        return true;
                    }
                }
                if (i == j && fieldString.charAt(j + i * fieldWidth) == letter) {
                    left2right += 1;
                    if (left2right == fieldWidth) {
                        return true;
                    }
                }
            }
        }

        // checking rows & columns
        for (int i = 0; i < fieldString.length(); i += fieldWidth) {
            if (String.valueOf(turned).substring(i, i + fieldWidth).equals(strike)) {
                return true;
            } else if (fieldString.substring(i, i + fieldWidth).equals(strike)) {
                return true;
            }
        }
        return false;
    }

    static int logic() {
        int x_counter = 0;
        int o_counter = 0;
        int space_counter = 0;

        for (int i = 0; i < fieldString.length(); i++) {
            switch (fieldString.charAt(i)) {
                case 'X':
                    x_counter += 1;
                    break;
                case 'O':
                    o_counter += 1;
                    break;
                case '_':
                    space_counter += 1;
                    break;
                default:
                    System.out.println("Wrong input!");
            }
        }
        if (x_counter - o_counter > 1 || o_counter - x_counter > 1) {
            return 5;
        }
        boolean win_x = winner('X');
        boolean win_o = winner('O');
        if (win_x && win_o) {
            return letter == 'X' ? 3 : 4;
//            return 5;
        } else if (win_x) {
            return 3;
        } else if (win_o) {
            return 4;
        } else if (x_counter + o_counter == fieldString.length()) {
            return 2;
        } else if (space_counter > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    static void gui() {
        String border = "-".repeat(Math.max(0, fieldWidth * 2 + 3));
        String left = "| ";
        String right = "|";

        System.out.println(border);
        for (int i = 0; i < fieldWidth; i++) {
            System.out.print(left);
            for (int j = 0; j < fieldWidth; j++) {
                System.out.print(fieldString.charAt(j + i * fieldWidth));
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

interface Player {
    boolean turn();
}

class Human implements Player {
    final char letter;

    Human(char letter) {
        this.letter = letter;
    }

    private static int coords2index(int x, int y) {
        x = x - 1;
        y = Main.fieldWidth - y;
        return y * Main.fieldWidth + x;
    }

    @Override
    public boolean turn() {
        int x;
        int y;
        System.out.println("Enter the coordinates: ");

        String move_x = Main.scanner.next();
        try {
            x = Integer.parseInt(move_x);
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return true;
        }
        String move_y = Main.scanner.next();
        try {
            y = Integer.parseInt(move_y);
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return true;
        }
        if (x > Main.fieldWidth || y > Main.fieldWidth) {
            System.out.println("Coordinates should be from 1 to " + Main.fieldWidth + "!");
            return true;
        }
        int hit = coords2index(x, y);
        System.out.println(x + " " + y + " " + hit);
        if (Main.fieldString.charAt(hit) == 'X' || Main.fieldString.charAt(hit) == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            return true;
        } else {
            char[] chars = Main.fieldString.toCharArray();
            chars[hit] = this.letter;
            Main.fieldString = String.valueOf(chars);
        }
        return false;
    }
}

class Easy implements Player {
    final char letter;
    private static final Random random = new Random();

    Easy(char letter) {
        this.letter = letter;
    }

    @Override
    public boolean turn() {
        int[] empty = new int[Main.fieldString.length()];
        int counter = 0;
        for (int i = 0; i < empty.length; i++) {
            if (Main.fieldString.charAt(i) == '_') {
                empty[counter] = i;
                counter++;
            }
        }
        int hit = empty[random.nextInt(counter)];
        if (Main.fieldString.charAt(hit) == 'X' || Main.fieldString.charAt(hit) == 'O') {
            System.out.println("Something went totally wrong!");
            return true;
        } else {
            char[] chars = Main.fieldString.toCharArray();
            chars[hit] = this.letter;
            Main.fieldString = String.valueOf(chars);
            System.out.println("Making move level \"easy\"");
        }
        return false;
    }
}