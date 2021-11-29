package trew.stefan.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatrixHelper {

    public static void printMatrix(Matrix<?> matrix) throws Exception {
        long max = 0;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                max = Math.max(max, matrix.get(i, j).toString().length());
            }
        }

        int strLen = Long.valueOf(max).toString().length();
        for (int i = 0; i < matrix.getHeight(); i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < matrix.getWidth(); j++) {
                builder.append(String.format("%" + strLen + "s ", matrix.get(i, j).toString()));
            }
            log.info(builder.toString());
        }

    }


    public static Integer[][] initMap(int height, int width, int defaultValue) {
        var map = new Integer[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = defaultValue;
            }
        }
        return map;
    }

}


