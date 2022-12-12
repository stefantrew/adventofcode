package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;


@Slf4j
public class Day12 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");
        var count = 0;

        log.info("{}", getDistance('a', 'E'));
        log.info("{}", getDistance('a', 'c'));
        log.info("{}", getDistance('d', 'c'));

        var matrix = new Matrix<>(list.size(), list.get(0).length(), Character.class, 'a');
        var visitedMatrix = new Matrix<>(list.size(), list.get(0).length(), Integer.class, 0);

        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            for (int i1 = 0; i1 < s.toCharArray().length; i1++) {
                matrix.set(i, i1, s.charAt(i1));

            }
        }
        var start = matrix.find('S').get(0);
        var end = matrix.find('E').get(0);
        log.info("S {}", start);
        log.info("E {}", end);
        matrix.printMatrix(false);

        Integer result = doWalk(matrix, start, visitedMatrix, 0);

        visitedMatrix.printMatrix(true);

        return String.valueOf(result);
    }

    private int getDistance(char a, char b) {
        if (a == 'S') {
            return 0;
        }
        if (b == 'S') {
            return 1000;
        }
        if (b == 'E') {
            b = 'z';
        }
        var i = (int) b - (int) a;
        return i;
    }

    private Integer doWalk(Matrix<Character> matrix, Matrix<Character>.MatrixPoint current, Matrix<Integer> visitedMatrix, int i) {
        Integer best = null;


        if (current.getValue() == 'E') {
            return i;
        }
//        if (i == 35) {
//            return null;
//        }
        var visit = visitedMatrix.get(current.getRow(), current.getCol());
        if (visit != 0 && i > visit) {
//            log.info("========= {} {} {}", visit, i, current);
            return null;
        }
        visitedMatrix.set(current.getRow(), current.getCol(), i);
        matrix.setVisited(current.getRow(), current.getCol(), true);

        if (matrix.validateColDimensions(current.getRow() + 1, current.getCol())) {

            var top = matrix.getPoint(current.getRow() + 1, current.getCol());

            if (getDistance(current.getValue(), top.getValue()) <= 1) {

                var result = doWalk(matrix, top, visitedMatrix, i + 1);

                if (result != null) {
                    best = best == null ? result : Math.min(best, result);
                }
            }
        }

        if (matrix.validateColDimensions(current.getRow() - 1, current.getCol())) {
            var top = matrix.getPoint(current.getRow() - 1, current.getCol());
            if (getDistance(current.getValue(), top.getValue()) <= 1) {

                var result = doWalk(matrix, top, visitedMatrix, i + 1);

                if (result != null) {
                    best = best == null ? result : Math.min(best, result);
                }
            }
        }

        if (matrix.validateColDimensions(current.getRow(), current.getCol() + 1)) {
            var top = matrix.getPoint(current.getRow(), current.getCol() + 1);
            if (getDistance(current.getValue(), top.getValue()) <= 1) {

                var result = doWalk(matrix, top, visitedMatrix, i + 1);

                if (result != null) {
                    best = best == null ? result : Math.min(best, result);
                }
            }
        }
        if (matrix.validateColDimensions(current.getRow(), current.getCol() - 1)) {
            var top = matrix.getPoint(current.getRow(), current.getCol() - 1);
            if (getDistance(current.getValue(), top.getValue()) <= 1) {

                var result = doWalk(matrix, top, visitedMatrix, i + 1);

                if (result != null) {
                    best = best == null ? result : Math.min(best, result);
                }
            }
        }

//        log.info("best {}", best);
        return best;
    }


    @Override
    public String runPart2() {
        var list = getStringInput("");
        var count = 0;

        return String.valueOf(count);
    }


    @Override
    public String getAnswerPart1() {
        return " ";
    }

    @Override
    public String getAnswerPart2() {
        return " ";
    }
}
