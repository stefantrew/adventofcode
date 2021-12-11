package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.Matrix;

import java.util.regex.Pattern;

@Slf4j
public class Day11 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        Matrix<Integer> matrix = getIntegerMatrix("");

        var a = 100;
        while (a-- > 0) {
            total += run(matrix);
        }

        return formatResult(total);
    }

    private int run(Matrix<Integer> matrix) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                matrix.apply(i, j, integer -> integer + 1);
            }
        }

        while (true) {
            if (!iterate(matrix)) {
                break;
            }
        }
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                matrix.apply(i, j, integer -> integer > 9 ? 0 : integer);

                if (matrix.hasVisited(i, j)) {
                    count++;
                }
                matrix.setVisited(i, j, false);

            }
        }
        return count;
    }

    private boolean iterate(Matrix<Integer> matrix) {
        var found = false;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                if (matrix.get(i, j) > 9 && !matrix.hasVisited(i, j)) {
                    matrix.setVisited(i, j, true);
                    found = true;
                    for (int dy = -1; dy < 2; dy++) {
                        for (int dx = -1; dx < 2; dx++) {
                            if (dy == 0 && dx == 0) {
                                continue;
                            }
                            try {

                                matrix.apply(i + dy, j + dx, integer -> integer + 1);
                            } catch (ArrayIndexOutOfBoundsException e) {

                            }
                        }
                    }
                }
            }

        }
        return found;
    }


    @Override
    public String runPart2() {


        Matrix<Integer> matrix = getIntegerMatrix("");

        var a = 1;
        while (true) {
            if (run(matrix) == 100) {
                return formatResult(a);
            }
            a++;
        }
    }

    private Matrix<Integer> getIntegerMatrix(String s) {
        var list = getStringInput(s);


        var matrix = new Matrix<Integer>(10, 10, Integer.class, 0);

        for (int i = 0; i < 10; i++) {
            var j = 0;
            for (char c : list.get(i).toCharArray()) {
                matrix.set(i, j, Integer.parseInt(String.valueOf(c)));
                j++;

            }
        }
        return matrix;
    }

    @Override
    public String getAnswerPart1() {
        return "1613";
    }

    @Override
    public String getAnswerPart2() {
        return "510";
    }
}
