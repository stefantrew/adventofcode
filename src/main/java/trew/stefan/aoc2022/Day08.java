package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Day08 extends AbstractAOC {


    @Override
    public String runPart1() {
        Matrix<Integer> matrix = getMatrix();
        int count;


        for (var i = 0; i < matrix.getWidth(); i++) {

            var row = 0;
            var current = matrix.get(row, i);
            matrix.setVisited(row, i, true);
            while (row < matrix.getHeight() - 1) {
                row++;
                var next = matrix.get(row, i);
                if (next > current) {
                    matrix.setVisited(row, i, true);
                } else if (next < current) {
                }
                current = Math.max(current, next);
            }

            row = matrix.getHeight() - 1;
            current = matrix.get(row, i);
            matrix.setVisited(row, i, true);
            while (row > 0) {
                row--;
                var next = matrix.get(row, i);
                if (next > current) {
                    matrix.setVisited(row, i, true);
                } else if (next < current) {
                }
                current = Math.max(current, next);
            }
        }

        for (var i = 0; i < matrix.getHeight(); i++) {

            var col = 0;
            var current = matrix.get(i, col);
            matrix.setVisited(i, col, true);
            while (col < matrix.getWidth() - 1) {
                col++;
                var next = matrix.get(i, col);
                if (next > current) {
                    matrix.setVisited(i, col, true);
                } else if (next < current) {
                }
                current = Math.max(current, next);
            }

            col = matrix.getWidth() - 1;
            current = matrix.get(i, col);
            matrix.setVisited(i, col, true);
            while (col > 0) {
                col--;
                var next = matrix.get(i, col);
                if (next > current) {
                    matrix.setVisited(i, col, true);
                } else if (next < current) {
                }
                current = Math.max(current, next);
            }
        }


        count = matrix.getVisited().size();
        return String.valueOf(count);
    }

    private Matrix<Integer> getMatrix() {
        var s = getStringInput("");

        var count = 0;

        var matrix = new Matrix<Integer>(s.size(), s.get(0).length(), Integer.class, 0);
        int row1 = 0;
        for (String s1 : s) {

            for (int i = 0; i < s1.toCharArray().length; i++) {

                matrix.set(row1, i, Integer.parseInt(String.valueOf(s1.charAt(i))));
            }
            row1++;
        }
        return matrix;
    }

    @Override
    public String runPart2() {
        var matrix = getMatrix();

        var best = 0;
        for (int r = 0; r < matrix.getHeight(); r++) {
            for (int c = 0; c < matrix.getWidth(); c++) {

                var result = compute(r, c, matrix);
//
                best = Math.max(best, result);
            }
        }
//        var result = compute(3, 2, matrix);
        return String.valueOf(best);
    }

    private int compute(int r, int c, Matrix<Integer> matrix) {
        var start = matrix.get(r, c);
        var count = 0;
        var total = 1;

        var row = r;
        while (row-- > 0) {
            count++;
            var value = matrix.get(row, c);
            if (value >= start) {
                break;
            }
        }
        total *= count;
        count = 0;

        row = r;
        while (row++ < matrix.getHeight() - 1) {
            count++;
            var value = matrix.get(row, c);
            if (value >= start) {
                break;
            }
        }
        total *= count;
        count = 0;

        var col = c;
        while (col-- > 0) {
            count++;
            var value = matrix.get(r, col);
            if (value >= start) {
                break;
            }
        }
        total *= count;
        count = 0;
        col = c;
        while (col++ < matrix.getHeight() - 1) {
            count++;
            var value = matrix.get(r, col);
            if (value >= start) {
                break;
            }
        }
        total *= count;
        return total;
    }


    @Override
    public String getAnswerPart1() {
        return "1543";
    }

    @Override
    public String getAnswerPart2() {
        return "595080";
    }
}
