package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.Matrix;

import java.util.regex.Pattern;

@Slf4j
public class Day15 extends AbstractAOC {

    int counter = 0;

    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


//        var list = getStringInput().stream().map(this::mapper).collect(Collectors.toList());

        var list = getStringInput("_sample");
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();
        var width = list.get(0).length();
        var matrix = new Matrix<Integer>(width, width, Integer.class, 0);
        var visited = new Matrix<Integer>(width, width, Integer.class, 0);

        for (int i = 0; i < matrix.getHeight(); i++) {
            var chars = list.get(i).toCharArray();
            for (int j = 0; j < width; j++) {

                matrix.set(i, j, Integer.parseInt(String.valueOf(chars[j])));
            }
        }

        matrix.printMatrix(false);

        var row = 0;
        var col = 0;


        var cost = matrix.get(row, col);
//        visited.set(row, col, cost);
        total = visit(cost, row, col, matrix, visited, 0);
        visited.printMatrix(true);
        var i = visited.get(matrix.getHeight() - 1, matrix.getWidth() - 1);
        i -= matrix.get(0, 0) * 2;
        return formatResult(i);
    }

    private int visit(int current, int row, int col, Matrix<Integer> matrix, Matrix<Integer> visited, int depth) {
//        if (row == matrix.getHeight() - 1 && col == matrix.getWidth() - 1) {
//            return current;
//        }

//        if (counter++ < 10) {
//            visited.printMatrix(false);
//        } else {
//            return 0;
//        }


        var integer = visited.get(row, col);
        var cost = current + matrix.get(row, col);
//        log.info("{} {} {} {}", row, col, integer, cost);
        if (integer > 0 && integer < cost) {
            return integer;
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
//        if (col > 0) {
//            var value = visit(cost, row, col - 1, matrix, visited, depth + 1);
//            if (value > 0) {
//
//                min = min == -1 ? value : Math.min(min, value);
//            }
//        }
//        if (row > 0) {
//            var value = visit(cost, row - 1, col, matrix, visited, depth + 1);
//            if (value > 0) {
//
//                min = min == -1 ? value : Math.min(min, value);
//            }
//        }


        return min;
    }


    @AllArgsConstructor
    class Item {

    }

    Item mapper(String input) {

        var p = Pattern.compile("");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Item();
        }
        return null;
    }


    @Override
    public String runPart2() {


        var list = getStringInput();

        return formatResult("");
    }

    @Override
    public String getAnswerPart1() {
        return "";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
