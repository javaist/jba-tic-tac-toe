package tictactoe;

public class Field {
    private final int fieldWidth;
    private String fieldString;

    public Field(String fieldString) {
        this.fieldString = fieldString;
        this.fieldWidth = (int) Math.sqrt(fieldString.length());
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public String getFieldString() {
        return fieldString;
    }

//    public void setFieldString(String fieldString) {
//        this.fieldString = fieldString;
//    }

    public boolean hit(int index, char letter) {
        if (fieldString.charAt(index) == 'X' || fieldString.charAt(index) == 'O') {
            return false;
        } else {
            char[] chars = fieldString.toCharArray();
            chars[index] = letter;
            fieldString = String.valueOf(chars);
            return true;
        }
    }

    private boolean winner(char letter) {
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

    public int logic() {
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
        boolean win_x = winner('X');
        boolean win_o = winner('O');
        if (win_x && win_o) {
            return 5;
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
}
