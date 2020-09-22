package tictactoe.player;

import tictactoe.Main;

import java.util.Random;

public class Easy implements tictactoe.player.Player {
    final char letter;
    private static final Random random = new Random();

    public Easy(char letter) {
        this.letter = letter;
    }

    @Override
    public boolean turn() {
        int[] empty = new int[Main.field.getFieldString().length()];
        int counter = 0;
        for (int i = 0; i < empty.length; i++) {
            if (Main.field.getFieldString().charAt(i) == '_') {
                empty[counter] = i;
                counter++;
            }
        }

        if (Main.field.hit(empty[random.nextInt(counter)], this.letter)) {
            System.out.println("Making move level \"easy\"");
            return false;
        } else {
            System.out.println("Something went totally wrong!");
            return true;
        }
    }
}