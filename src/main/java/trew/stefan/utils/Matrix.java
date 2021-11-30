package trew.stefan.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


@Slf4j
@Data
@Accessors(chain = true)
public class Matrix<T> {

    private T[][] map;
    private Integer height;
    private Integer width;
    private final Class<? extends T> cls;

    public Matrix(Integer height, Integer width, Class<? extends T> cls, T initialValue) {
        this.height = height;
        this.width = width;
        this.cls = cls;

        map = (T[][]) Array.newInstance(cls, height, width);
        setAll(initialValue);
    }

    public Matrix<T> setAll(T defaultValue) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = defaultValue;
            }
        }

        return this;
    }


    protected void validateDimensions(int row, int col) {
        if (row >= height || row < 0) {
            throw new ArrayIndexOutOfBoundsException("Row out of bounds: " + row);
        }
        if (col >= width || col < 0) {
            throw new ArrayIndexOutOfBoundsException("Col out of bounds: " + col);
        }

    }

    public T get(int row, int col) {
        validateDimensions(row, col);
        return map[row][col];
    }

    public Matrix<T> set(int row, int col, T value) {
        validateDimensions(row, col);
        map[row][col] = value;
        return this;
    }

    public void printMatrix(boolean useSeparator) {
        long max = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                T t = get(i, j);
                if (t == null) {
                    continue;
                }
                max = Math.max(max, t.toString().length());
            }
        }
        var template = "%" + max + "s" + (useSeparator ? " " : "");
        for (int i = 0; i < getHeight(); i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < getWidth(); j++) {
                T t = get(i, j);
                builder.append(String.format(template, t != null ? t.toString() : "."));
            }
            log.info(builder.toString());
        }

    }

    public T apply(int row, int col, Function<? super T, ? extends T> inc) {
        var value = get(row, col);

        var value2 = inc.apply(value);
        set(row, col, value2);

        return value2;
    }


    public int count(Predicate<? super T> inc) {
        var count = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (inc.test(get(i, j))) {
                    count++;
                }
            }
        }

        return count;
    }
}
