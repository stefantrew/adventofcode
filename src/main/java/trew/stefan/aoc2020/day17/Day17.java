package trew.stefan.aoc2020.day17;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class Day17 implements AOCDay {

    private int day = 17;

    @Data
    class Matrix implements Comparable {

        boolean[][][] map;
        boolean[][][] map2;
        Integer height;
        Integer width;
        Integer depth;
        Integer level;
        public Matrix upper;
        public Matrix lower;

        public Matrix(Integer height, Integer width, Integer depth, int level) {
            this.height = height;
            this.width = width;
            this.level = level;
            this.depth = depth;

            map = new boolean[height][width][depth];
            map2 = new boolean[height][width][depth];
        }

        private boolean validateDimensions(int row, int col, int depth) {
            if (row >= height || row < 0) {
                return false;
            }
            if (col >= width || col < 0) {
                return false;
            }
            if (depth >= this.depth || depth < 0) {
                return false;
            }

            return true;

        }

        public void expandMatrix() {
            this.height += 2;
            this.width += 2;
            this.depth += 2;

            boolean[][][] temp = new boolean[height][width][depth];
            map2 = new boolean[height][width][depth];
            for (int i = 0; i < height - 2; i++) {
                for (int j = 0; j < width - 2; j++) {
                    for (int w = 0; w < depth - 2; w++) {

                        try {
                            temp[i + 1][j + 1][w + 1] = map[i][j][w];
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            map = temp;

        }

        public boolean getValue(int row, int col, int depth) {
            if (!validateDimensions(row, col, depth)) {
                return false;
            }
            return map[row][col][depth];
        }

        public Matrix setValue(int row, int col, int depth, boolean value) throws Exception {
            if (!validateDimensions(row, col, depth)) {
                return this;
            }
            map[row][col][depth] = value;
            return this;
        }

        public void setValue2(int row, int col, int depth, boolean value) {

            map2[row][col][depth] = value;

        }

        public int countActive() {
            int count = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    for (int w = 0; w < depth; w++) {
                        if (map[i][j][w]) count++;
                    }
                }
            }
            return count;
        }

        public void swap() {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    for (int w = 0; w < depth; w++) {
                        try {
                            map[i][j][w] = map2[i][j][w];
                            map2[i][j][w] = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        @Override
        public int compareTo(Object o) {
            return -(((Matrix) o).level - level);
        }
    }

    public static void printMatrix(Matrix matrix) throws Exception {

        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                StringBuilder builder = new StringBuilder();
                for (int w = 0; w < matrix.depth; w++) {
                    builder.append(matrix.getValue(i, j, w) ? '#' : '.');
                }
                log.info(builder.toString());
            }
        }

    }

    private List<Matrix> list = new ArrayList<>();

    @Override
    public String runPart1() {
        try {
            return String.valueOf(run2(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String runPart2() {
        try {
            return String.valueOf(run2(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Matrix createLevel(int height, int width, int depth, int level) {
        return new Matrix(height, width, depth, level);
    }

    public int run2(boolean is4D) throws Exception {
        List<String> lines = InputReader2020.readStrings(day, "");


        Matrix matrix = new Matrix(lines.size(), lines.get(0).length(), 3, 0);

        for (int i = 0; i < matrix.height; i++) {
            String line = lines.get(i);
            for (int j = 0; j < matrix.width; j++) {

                try {
                    matrix.setValue(i, j, 1, line.charAt(j) == '#');

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        list.add(matrix);

        for (int i = 0; i < 6; i++) {

            buildStack();
            processStack(is4D);
        }

        int count = 0;
        for (Matrix matrix2 : list) {
            count += matrix2.countActive();
        }
        return count;
    }

    private void buildStack() {

        List<Matrix> newStack = new ArrayList<>();

        for (Matrix matrix : list) {
            if (matrix.upper == null) {
                matrix.upper = createLevel(matrix.height, matrix.width, matrix.depth, matrix.level + 1);
                matrix.upper.lower = matrix;
                newStack.add(matrix.upper);
            }

            if (matrix.lower == null) {
                matrix.lower = createLevel(matrix.height, matrix.width, matrix.depth, matrix.level - 1);
                matrix.lower.upper = matrix;
                newStack.add(matrix.lower);
            }


        }
        list.addAll(newStack);
        Collections.sort(list);
    }

    private void processStack(boolean is4D) {

        int wStart = is4D ? -1 : 0;
        int wEnd = is4D ? 2 : 1;

        for (Matrix matrix : list) {


            for (int i = 0; i < matrix.getHeight(); i++) {
                for (int j = 0; j < matrix.getWidth(); j++) {
                    for (int k = 0; k < matrix.getDepth(); k++) {

                        int count = 0;
                        for (int x = -1; x < 2; x++) {
                            for (int y = -1; y < 2; y++) {
                                for (int z = -1; z < 2; z++) {
                                    for (int w = wStart; w < wEnd; w++) {
                                        if (x == 0 && y == 0 && z == 0 && w == 0) {
                                            continue;
                                        }
                                        switch (z) {
                                            case -1:
                                                if (matrix.lower == null) {
                                                    continue;
                                                }
                                                if (matrix.lower.getValue(i + x, j + y, k + w)) {
                                                    count++;
                                                }

                                                break;
                                            case 0:
                                                if (matrix.getValue(i + x, j + y, k + w)) {
                                                    count++;
                                                }

                                                break;
                                            case 1:
                                                if (matrix.upper == null) {
                                                    continue;
                                                }
                                                if (matrix.upper.getValue(i + x, j + y, k + w)) {
                                                    count++;
                                                }

                                                break;
                                        }

                                    }
                                }
                            }
                        }


                        try {
                            matrix.setValue2(i, j, k, matrix.getValue(i, j, k));
                            if (matrix.getValue(i, j, k)) {
                                if (!(count == 2 || count == 3)) {

                                    matrix.setValue2(i, j, k, false);
                                }

                            } else {
                                if (count == 3) {

                                    matrix.setValue2(i, j, k, true);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
