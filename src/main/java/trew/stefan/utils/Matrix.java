package trew.stefan.utils;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


@Slf4j
@Data
@Accessors(chain = true)
public class Matrix<T> {


    @Data
    public class MatrixPoint {

        T value;
        int row;
        int col;

        public MatrixPoint(T value, int row, int col) {
            this.value = value;
            this.row = row;
            this.col = col;
        }
    }

    private T[][] map;
    private Boolean[][] visitedMap;
    private Integer height;
    private Integer width;
    private final Class<? extends T> cls;
    private int id;

    public Matrix(Integer height, Integer width, Class<? extends T> cls, T initialValue) {
        this.height = height;
        this.width = width;
        this.cls = cls;

        map = (T[][]) Array.newInstance(cls, height, width);
        visitedMap = new Boolean[height][width];
        setAll(initialValue);
    }

    public Matrix<T> setAll(T defaultValue) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = defaultValue;
                visitedMap[i][j] = false;
            }
        }

        return this;
    }


    protected void validateDimensions(int row, int col) {
        validateRowDimensions(row);
        validateColDimensions(col);
    }

    protected void validateRowDimensions(int row) {
        if (row >= height || row < 0) {
            throw new ArrayIndexOutOfBoundsException("Row out of bounds: " + row);
        }
    }

    protected void validateColDimensions(int col) {
        if (col >= width || col < 0) {
            throw new ArrayIndexOutOfBoundsException("Col out of bounds: " + col);
        }

    }

    public List<MatrixPoint> find(T target) {
        var temp = new ArrayList<MatrixPoint>();
        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                T value = get(row, col);
                if (target.equals(value)) {
                    temp.add(new MatrixPoint(value, row, col));
                }
            }
        }

        return temp;
    }

    public T get(int row, int col) {
        validateDimensions(row, col);
        return map[row][col];
    }

    public List<T> getRow(int row) {
        validateRowDimensions(row);
        return Arrays.asList(map[row]);
    }

    public List<T> getCol(int col) {
        validateColDimensions(col);
        var temp = new ArrayList<T>();
        for (int i = 0; i < height; i++) {
            temp.add(get(i, col));
        }
        return temp;
    }


    public List<T> getUnvisited() {
        var temp = new ArrayList<T>();
        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                if (!hasVisited(row, col)) {
                    temp.add(get(row, col));
                }
            }
        }

        return temp;
    }

    public List<T> getVisited() {
        var temp = new ArrayList<T>();
        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                if (hasVisited(row, col)) {
                    temp.add(get(row, col));
                }
            }
        }

        return temp;
    }

    public List<Boolean> getVisitedRow(int row) {
        validateRowDimensions(row);
        return Arrays.asList(visitedMap[row]);
    }

    public List<Boolean> getVisitedCol(int col) {
        validateColDimensions(col);
        var temp = new Boolean[height];
        for (int i = 0; i < height; i++) {
            temp[i] = hasVisited(i, col);
        }
        return Arrays.asList(temp);
    }

    public boolean hasVisited(int row, int col) {
        validateDimensions(row, col);
        return visitedMap[row][col];
    }

    public Matrix<T> set(int row, int col, T value) {
        validateDimensions(row, col);
        map[row][col] = value;
        return this;
    }

    public Matrix<T> setRow(int row, List<T> values) {
        validateRowDimensions(row);
        if (values.size() != width) {
            throw new ArrayIndexOutOfBoundsException("Data does not match width for : " + row);
        }
        values.toArray(map[row]);
        return this;
    }

    public Matrix<T> setCol(int col, List<T> values) {
        validateColDimensions(col);
        if (values.size() != height) {
            throw new ArrayIndexOutOfBoundsException("Data does not match height for : " + col);
        }
        for (int i = 0; i < getHeight(); i++) {
            map[i][col] = values.get(i);
        }
        return this;
    }

    public void setVisited(MatrixPoint point, boolean value) {
        setVisited(point.row, point.col, value);
    }

    public Matrix<T> setVisited(int row, int col, boolean value) {
        validateDimensions(row, col);
        visitedMap[row][col] = value;
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
