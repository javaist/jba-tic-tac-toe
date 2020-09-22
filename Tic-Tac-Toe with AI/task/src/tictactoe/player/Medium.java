package tictactoe.player;

import tictactoe.Field;
import tictactoe.Main;

import java.util.Random;

public class Medium implements Player {
    final char letter;
    private Field innerField;
    private static final Random random = new Random();

    public Medium(char letter) {
        this.letter = letter;
    }

    @Override
    public boolean turn() {
        int[] empty = new int[Main.field.getFieldString().length()];
        int counter = 0;
        int counterX = 0;
        int counterO = 0;
        for (int i = 0; i < empty.length; i++) {
            switch (Main.field.getFieldString().charAt(i)) {
                case '_':
                    empty[counter] = i;
                    counter++;
                    break;
                case 'X':
                    counterX++;
                    break;
                case 'O':
                    counterO++;
                    break;
                default:
                    System.out.println("Impossible letter input");
                    throw new IllegalArgumentException();
            }
        }
        System.out.println("Making move level \"medium\"");
        if (isWinner(letter, empty, counter)) {
            return false;
        } else if (isWinner(letter == 'X' ? 'O' : 'X', empty, counter)) {
            return false;
        } else {
            if (Main.field.hit(empty[random.nextInt(counter)], this.letter)) {
                return false;
            } else {
                System.out.println("Something went totally wrong!");
                return true;
            }
        }
    }

    private boolean isWinner(char letter, int[] empty, int counter) {
        for (int i = 0; i < counter; i++) {
            innerField = new Field(Main.field.getFieldString());
            if (innerField.hit(empty[i], letter)) {
                int res = innerField.logic();
                if (res == 3 && letter == 'X') {
                    Main.field.hit(empty[i], this.letter);
                    return true;
                } else if (res == 4 && letter == 'O') {
                    Main.field.hit(empty[i], this.letter);
                    return true;
                }
            } else {
                throw new IllegalStateException("Something totally wrong, it should have hit empty cell");
            }

        }
        return false;
    }
}
