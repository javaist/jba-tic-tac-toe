package tictactoe.player;

import tictactoe.Main;

public class Human implements tictactoe.player.Player {
    final char letter;

    public Human(char letter) {
        this.letter = letter;
    }

    private static int coords2index(int x, int y) {
        x = x - 1;
        y = Main.field.getFieldWidth() - y;
        return y * Main.field.getFieldWidth() + x;
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
        if (x > Main.field.getFieldWidth() || y > Main.field.getFieldWidth()) {
            System.out.println("Coordinates should be from 1 to " + Main.field.getFieldWidth() + "!");
            return true;
        }
        int hit = coords2index(x, y);

        if (Main.field.hit(coords2index(x, y), this.letter)) {
            return false;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
            return true;
        }
    }
}
