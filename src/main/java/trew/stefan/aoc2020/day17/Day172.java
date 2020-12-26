package trew.stefan.aoc2020.day17;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Day172 implements Day {

    private int day = 17;

    @Data
    class Matrix implements Comparable {

        boolean[][] map;
        boolean[][] map2;
        Integer height;
        Integer width;
        Integer level;
        boolean processed = false;
        public Matrix upper;
        public Matrix lower;

        public Matrix(Integer height, Integer width, int level) {
            this.height = height;
            this.width = width;
            this.level = level;

            map = new boolean[height][width];
            map2 = new boolean[height][width];
        }

        private boolean validateDimensions(int row, int col) {
            if (row >= height || row < 0) {
                return false;
            }
            if (col >= width || col < 0) {
                return false;
            }

            return true;

        }

        public void expandMatrix() {
            this.height += 2;
            this.width += 2;

            boolean[][] temp = new boolean[height][width];
            map2 = new boolean[height][width];
            for (int i = 0; i < height - 2; i++) {
                for (int j = 0; j < width - 2; j++) {

                    try {
                        temp[i + 1][j + 1] = map[i][j];
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            map = temp;

        }

        public boolean getValue(int row, int col) {
            if (!validateDimensions(row, col)) {
                return false;
            }
            return map[row][col];
        }

        public Matrix setValue(int row, int col, boolean value) throws Exception {
            if (!validateDimensions(row, col)) {
                return this;
            }
            map[row][col] = value;
            return this;
        }

        public void setValue2(int row, int col, boolean value) {

            map2[row][col] = value;

        }

        public int countActive() {
            int count = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    if (map[i][j]) count++;
                }
            }
            return count;
        }

        public void swap() {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {

                    try {
                        map[i][j] = map2[i][j];
                        map2[i][j] = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            processed = false;
        }

        @Override
        public int compareTo(Object o) {
            return -(((Matrix) o).level - level);
        }
    }

    public static void printMatrix(Matrix matrix) throws Exception {

        log.info("--- printing level {} ---", matrix.level);

        for (int i = 0; i < matrix.getHeight(); i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < matrix.getWidth(); j++) {
                builder.append(matrix.getValue(i, j) ? '#' : '.');
            }
            log.info(builder.toString());
        }

    }

    private List<Matrix> list = new ArrayList<>();

    public void run() {

        try {
            run2();
        } catch (Exception e) {

        }
    }

    public Matrix createLevel(int height, int width, int level) {
        Matrix matrix = new Matrix(height, width, level);


        return matrix;
    }

    public void run2() throws Exception {
        List<String> lines = InputReader2020.readStrings(day, "_sample");


        Matrix matrix = new Matrix(lines.size(), lines.get(0).length(), 0);

        for (int i = 0; i < matrix.height; i++) {
            String line = lines.get(i);
            for (int j = 0; j < matrix.width; j++) {

                try {
                    matrix.setValue(i, j, line.charAt(j) == '#');

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        list.add(matrix);

        for (int i = 0; i < 6; i++) {

            printMatrix(matrix);
            buildStack();
            processStack();
        }

        int count = 0;
        for (Matrix matrix2 : list) {
            count += matrix2.countActive();
            printMatrix(matrix2);
        }
        log.info("{}", count);
    }

    private void buildStack() {

        List<Matrix> newStack = new ArrayList<>();

        for (Matrix matrix : list) {
            if (matrix.upper == null) {
                matrix.upper = createLevel(matrix.height, matrix.width, matrix.level + 1);
                matrix.upper.lower = matrix;
                newStack.add(matrix.upper);
            }

            if (matrix.lower == null) {
                matrix.lower = createLevel(matrix.height, matrix.width, matrix.level - 1);
                matrix.lower.upper = matrix;
                newStack.add(matrix.lower);
            }
        }
        list.addAll(newStack);
        Collections.sort(list);
    }

    private void processStack() {


        for (Matrix matrix : list) {

            log.info("---- {} ----", matrix.level);

            for (int i = 0; i < matrix.getHeight(); i++) {
                for (int j = 0; j < matrix.getWidth(); j++) {

                    int count = 0;
                    for (int x = -1; x < 2; x++) {
                        for (int y = -1; y < 2; y++) {
                            for (int z = -1; z < 2; z++) {
                                if (x == 0 && y == 0 && z == 0) {
                                    continue;
                                }

                                switch (z) {
                                    case -1:
                                        if (matrix.lower == null) {
                                            continue;
                                        }
                                        if (matrix.lower.getValue(i + x, j + y)) {
                                            count++;
                                        }

                                        break;
                                    case 0:
                                        if (matrix.getValue(i + x, j + y)) {
                                            count++;
                                        }

                                        break;
                                    case 1:
                                        if (matrix.upper == null) {
                                            continue;
                                        }
                                        if (matrix.upper.getValue(i + x, j + y)) {
                                            count++;
                                        }

                                        break;
                                }

                            }
                        }
                    }

                    try {
                        matrix.setValue2(i, j, matrix.getValue(i, j));
                        if (matrix.getValue(i, j)) {
                            if (!(count == 2 || count == 3)) {

                                matrix.setValue2(i, j, false);
                            }

                        } else {
                            if (count == 3) {

                                matrix.setValue2(i, j, true);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (Matrix matrix : list) {
            matrix.swap();
            matrix.expandMatrix();
        }
    }
}
