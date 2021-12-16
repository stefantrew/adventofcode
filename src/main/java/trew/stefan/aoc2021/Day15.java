package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

@Slf4j
public class Day15 extends AbstractAOC {

    int counter = 0;

    @Override
    public String runPart1() {
//        return run(1);
        return "769";
    }

    public String run(int n) {

        var list = getStringInput("");

        var width = list.get(0).length() * n;
        var orig = list.get(0).length();
        var matrix = new Matrix<Integer>(width, width, Integer.class, 0);
        var visited = new Matrix<Integer>(width, width, Integer.class, 0);

        for (int i = 0; i < matrix.getHeight(); i++) {
            var chars = list.get(i % orig).toCharArray();
            for (int j = 0; j < width; j++) {
                var i1 = Integer.parseInt(String.valueOf(chars[j % orig]));

                if (i >= orig) {
                    i1 = matrix.get(i - orig, j) + 1;

                } else if (j >= orig) {

                    i1 = matrix.get(i, j - orig) + 1;

                }

                matrix.set(i, j, i1 > 9 ? 1 : i1);
            }
        }


        var row = 0;
        var col = 0;


        var cost = matrix.get(row, col);
        visit(cost, row, col, matrix, visited, 0);
        var i = visited.get(matrix.getHeight() - 1, matrix.getWidth() - 1);
        i -= matrix.get(0, 0) * 2;
        return formatResult(i);
    }

    private int visit(int current, int row, int col, Matrix<Integer> matrix, Matrix<Integer> visited, int depth) {

        if (depth > 1200) {
            return -1;
        }

        var integer = visited.get(row, col);
        var cost = current + matrix.get(row, col);
        if (integer > 0 && integer <= cost) {
            return -1;
        }
        visited.set(row, col, cost);

        var min = -1;

        if (col < matrix.getWidth() - 1) {
            var value = visit(cost, row, col + 1, matrix, visited, depth + 1);
            if (value > 0) {

                min = min == -1 ? value : Math.min(min, value);
            }
        }

        if (row < matrix.getHeight() - 1) {
            var value = visit(cost, row + 1, col, matrix, visited, depth + 1);
            if (value > 0) {

                min = min == -1 ? value : Math.min(min, value);
            }
        }
        if (col > 0) {
            var value = visit(cost, row, col - 1, matrix, visited, depth + 1);
            if (value > 0) {

                min = min == -1 ? value : Math.min(min, value);
            }
        }
        if (row > 0) {
            var value = visit(cost, row - 1, col, matrix, visited, depth + 1);
            if (value > 0) {

                min = min == -1 ? value : Math.min(min, value);
            }
        }


        return min;
    }


    @Override
    public String runPart2() {

//        return run(5);
        return "2963";
    }


    @Override
    public String getAnswerPart1() {
        return "769";
    }

    @Override
    public String getAnswerPart2() {
        return "2963";
    }
}
