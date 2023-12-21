package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Slf4j
public class Day14 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");
        var charMatrix = list.stream().map(s -> s.toCharArray()).toArray(char[][]::new);

        for (int i = 0; i < list.size(); i++) {
            charMatrix = process(charMatrix, Day10.Direction.NORTH);

        }

        total = getTotal(total, charMatrix);

        return formatResult(total);
    }

    private static int getTotal(int total, char[][] charMatrix) {
        for (int i = 0; i < charMatrix.length; i++) {
            var s = charMatrix[i];
            for (char c : s) {
                if (c == 'O') {
                    total += charMatrix.length - i;
                }
            }
        }
        return total;
    }

    private char[][] process(char[][] charMatrix, Day10.Direction direction) {


        while (true) {

            var change = 0;
            var newMatrix = new char[charMatrix.length][charMatrix[0].length];

            switch (direction) {
                case NORTH:
                    for (int col = 0; col < charMatrix[0].length; col++) {
                        newMatrix[0][col] = charMatrix[0][col];
                    }

                    for (int row = 1; row < charMatrix.length; row++) {
                        for (int col = 0; col < charMatrix[row].length; col++) {
                            var c = charMatrix[row][col];
                            var upper = newMatrix[row - 1][col];
                            if (c == '#' || c == '.') {
                                newMatrix[row][col] = c;
                            } else if (upper == '.') {
                                newMatrix[row][col] = '.';
                                newMatrix[row - 1][col] = c;
                                change++;
                            } else {
                                newMatrix[row][col] = c;
                            }
                        }
                    }
                    break;
                case SOUTH:
                    var lastRow = charMatrix.length - 1;
                    for (int col = 0; col < charMatrix[lastRow].length; col++) {
                        newMatrix[lastRow][col] = charMatrix[lastRow][col];
                    }

                    for (int row = lastRow - 1; row >= 0; row--) {
                        for (int col = 0; col < charMatrix[row].length; col++) {
                            var c = charMatrix[row][col];
                            var upper = newMatrix[row + 1][col];
                            if (c == '#' || c == '.') {
                                newMatrix[row][col] = c;
                            } else if (upper == '.') {
                                newMatrix[row][col] = '.';
                                newMatrix[row + 1][col] = c;
                                change++;

                            } else {
                                newMatrix[row][col] = c;
                            }
                        }
                    }
                    break;
                case EAST:
                    var lastCol = charMatrix[0].length - 1;
                    for (int row = 0; row < charMatrix[lastCol].length; row++) {
                        newMatrix[row][lastCol] = charMatrix[row][lastCol];
                    }

                    for (int col = lastCol - 1; col >= 0; col--) {
                        for (int row = 0; row < charMatrix[col].length; row++) {
                            var c = charMatrix[row][col];
                            var upper = newMatrix[row][col + 1];
                            if (c == '#' || c == '.') {
                                newMatrix[row][col] = c;
                            } else if (upper == '.') {
                                newMatrix[row][col] = '.';
                                newMatrix[row][col + 1] = c;
                                change++;

                            } else {
                                newMatrix[row][col] = c;
                            }
                        }
                    }
                    break;
                case WEST:
                    for (int row = 0; row < charMatrix[0].length; row++) {
                        newMatrix[row][0] = charMatrix[row][0];
                    }

                    for (int col = 1; col < charMatrix[0].length; col++) {
                        for (int row = 0; row < charMatrix[col].length; row++) {
                            var c = charMatrix[row][col];
                            var upper = newMatrix[row][col - 1];
                            if (c == '#' || c == '.') {
                                newMatrix[row][col] = c;
                            } else if (upper == '.') {
                                newMatrix[row][col] = '.';
                                newMatrix[row][col - 1] = c;
                                change++;

                            } else {
                                newMatrix[row][col] = c;
                            }
                        }
                    }
                    break;
            }
            charMatrix = newMatrix;

            if (change == 0) {
                return newMatrix;

            }
        }


    }


    @Override
    public String runPart2() {


        var total = 0;

        var list = getStringInput("");
        var charMatrix = list.stream().map(s -> s.toCharArray()).toArray(char[][]::new);


        var totals = new HashMap<Integer, Integer>();

        var xxx = 1000000000L - 142L;
        var temp = xxx % 28;

        for (int i = 0; i < 142 + temp; i++) {


            charMatrix = getChars(charMatrix, i);

            total = getTotal(0, charMatrix);
        }


        return formatResult(total);
    }

    private Map cache = new HashMap<Integer, Integer>();

    private char[][] getChars(char[][] charMatrix, int index) {

        charMatrix = process(charMatrix, Day10.Direction.NORTH);
        charMatrix = process(charMatrix, Day10.Direction.WEST);
        charMatrix = process(charMatrix, Day10.Direction.SOUTH);
        charMatrix = process(charMatrix, Day10.Direction.EAST);
        return charMatrix;
    }

    private int getHashCode(char[][] charMatrix) {

        var str = new StringBuilder();
        for (int i = 0; i < charMatrix.length; i++) {
            str.append(charMatrix[i]);
        }
        return str.toString().hashCode();
    }

    @Override
    public String getAnswerPart1() {
        return "109833";
    }

    @Override
    public String getAnswerPart2() {
        return "99875";
    }
}
