package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.LinkedList;


@Slf4j
public class Day12 extends AbstractAOC {

    int[][] offsets = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

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
        return b - a;
    }


    @Override
    public String runPart1() {
        var matrix = getCharacterMatrix();
        var start = matrix.find('S').get(0);
        return String.valueOf(doWalk(start, matrix));
    }

    @Override
    public String runPart2() {
        Matrix<Character> matrix = getCharacterMatrix();
        var point = matrix.find('S').get(0);
        matrix.set(point, 'a');

        Integer best = null;
        var points = matrix.find('a');
        for (Matrix<Character>.MatrixPoint start : points) {

            var result = doWalk(start, matrix);
            if (result != null) {
                best = best == null ? result : Math.min(best, result);
            }
        }

        return String.valueOf(best);
    }

    private Matrix<Character> getCharacterMatrix() {

        var list = getStringInput("");
        var matrix = new Matrix<>(list.size(), list.get(0).length(), Character.class, 'a');

        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            for (int i1 = 0; i1 < s.toCharArray().length; i1++) {
                matrix.set(i, i1, s.charAt(i1));

            }
        }
        return matrix;
    }

    private Integer doWalk(Matrix<Character>.MatrixPoint start, Matrix<Character> matrix) {
        var visitedMatrix = new Matrix<>(matrix.getHeight(), matrix.getWidth(), Integer.class, 0);

        matrix.resetVisited();
        var queue = new LinkedList<Matrix<Character>.MatrixPoint>();

        queue.add(start);
        while (!queue.isEmpty()) {
            var current = queue.remove();

            var visit = visitedMatrix.get(current.getRow(), current.getCol());
            if (matrix.hasVisited(current.getRow(), current.getCol())) {
                continue;
            }
            matrix.setVisited(current.getRow(), current.getCol(), true);


            if (current.getValue() == 'E') {
                return visit;
            }

            for (int[] ints : offsets) {
                if (matrix.checkDimensions(current.getRow() + ints[0], current.getCol() + ints[1])) {

                    var top = matrix.getPoint(current.getRow() + ints[0], current.getCol() + ints[1]);

                    if (getDistance(current.getValue(), top.getValue()) <= 1) {
                        visitedMatrix.set(top.getRow(), top.getCol(), visit + 1);
                        queue.add(top);
                    }
                }
            }
        }

        return null;
    }


    @Override
    public String getAnswerPart1() {
        return "490";
    }

    @Override
    public String getAnswerPart2() {
        return "488";
    }
}
