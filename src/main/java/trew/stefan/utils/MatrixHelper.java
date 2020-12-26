package trew.stefan.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Console;

@Slf4j
public class MatrixHelper {

    public static void printMatrix(Matrix matrix) throws Exception {
        long max = 0;
        for (int i = 0; i < matrix.getHeight(); i++) {
            for (int j = 0; j < matrix.getWidth(); j++) {
                max = Math.max(max, matrix.getValue(i, j));
            }
        }

        int strLen = Long.valueOf(max).toString().length();
        for (int i = 0; i < matrix.getHeight(); i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < matrix.getWidth(); j++) {
                builder.append(String.format("%" + strLen + "s ", Long.valueOf(matrix.getValue(i, j)).toString()));
            }
            log.info(builder.toString());
        }

    }
}


