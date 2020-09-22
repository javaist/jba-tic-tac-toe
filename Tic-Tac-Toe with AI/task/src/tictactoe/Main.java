package tictactoe;

import java.util.Scanner;

public class Main {
    static int field;
    static String input;
    static Scanner scanner = new Scanner(System.in);
    static char letter;

    public static void main(String[] args) {

        System.out.println("Enter cells: ");
        input = scanner.next();
//        input = "_________";
        field = (int) Math.sqrt(input.length());

        gui();

        boolean in_game;
        int result;
        int x_counter = 0;
        int o_counter = 0;
        for (int i = 0; i < input.length(); i++) {
            x_counter += input.charAt(i) == 'X' ? 1 : 0;
            o_counter += input.charAt(i) == 'O' ? 1 : 0;
//            System.out.println("x_counter: " + x_counter + "; o_counter: " + o_counter);
        }
        letter = x_counter == o_counter ? 'X' : 'O';
//        System.out.println(letter);
        do {
            in_game = turn();
        } while (in_game);
        gui();
        result = logic();

//        do {
//            do {
//                in_game = turn(letter);
//            } while (in_game);
//            gui();
//            result = logic();
//            letter = 'X' == letter ? 'O' : 'X';
//        } while (result < 2);


        message(result);

    }


    static boolean turn() {
        int x;
        int y;
        System.out.println("Enter the coordinates: ");

        String move_x = scanner.next();
        try {
            x = Integer.parseInt(move_x);
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return true;
        }
        String move_y = scanner.next();
        try {
            y = Integer.parseInt(move_y);
        } catch (NumberFormatException e) {
            System.out.println("You should enter numbers!");
            return true;
        }
        if (x > field || y > field) {
            System.out.println("Coordinates should be from 1 to " + field + "!");
            return true;
        }
        int hit = coords2index(x, y);
        if (input.charAt(hit) == 'X' || input.charAt(hit) == 'O') {
            System.out.println("This cell is occupied! Choose another one!");
            return true;
        } else {
            char[] chars = input.toCharArray();
            chars[hit] = letter;
            input = String.valueOf(chars);
        }

        return false;
    }

    private static int coords2index(int x, int y) {
        x = x - 1;
        y = field - y;
        return y * field + x;
    }

    static boolean winner(char letter) {
        String strike = String.valueOf(letter).repeat(Math.max(0, field));

        // Turn to left
        char[] turned = new char[input.length()];
        int left2right = 0;
        int right2left = 0;
        for (int i = 0; i < field; i++) {
            for (int j = 0; j < field; j++) {
                turned[i + j * field] = input.charAt(j + i * field);

                // check diagonal
                if (field - 1 - i == j && input.charAt(j + i * field) == letter) {
                    right2left += 1;
                    if (right2left == field) {
                        return true;
                    }
                }
                if (i == j && input.charAt(j + i * field) == letter) {
                    left2right += 1;
                    if (left2right == field) {
                        return true;
                    }
                }
            }
        }

        // checking rows & columns
        for (int i = 0; i < input.length(); i += field) {
            if (String.valueOf(turned).substring(i, i + field).equals(strike)) {
                return true;
            } else if (input.substring(i, i + field).equals(strike)) {
                return true;
            }
        }
        return false;
    }

    static int logic() {
        int x_counter = 0;
        int o_counter = 0;
        int space_counter = 0;

        for (int i = 0; i < input.length(); i++) {
            switch (input.charAt(i)) {
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
        } else if (x_counter + o_counter == input.length()) {
            return 2;
        } else if (space_counter > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    static void gui() {
        String border = "-".repeat(Math.max(0, field * 2 + 3));
        String left = "| ";
        String right = "|";

        System.out.println(border);
        for (int i = 0; i < field; i++) {
            System.out.print(left);
            for (int j = 0; j < field; j++) {
                System.out.print(input.charAt(j + i * field));
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