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

    public Matrix<T> copy() {

        var temp = new Matrix<T>(height, width, cls, null);

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                temp.setVisited(row, col, visitedMap[row][col]);
                temp.set(row, col, map[row][col]);
            }
        }
        return temp;
    }

    public Matrix<T> flipVertically() {
        var temp = new Matrix<T>(height, width, cls, null);

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                temp.setVisited(height - row - 1, col, visitedMap[row][col]);
                temp.set(height - row - 1, col, map[row][col]);
            }
        }
        return temp;
    }

    public Matrix<T> flipHorizontally() {
        var temp = new Matrix<T>(height, width, cls, null);

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                temp.setVisited(row, width - col - 1, visitedMap[row][col]);
                temp.set(row, width - col - 1, map[row][col]);
            }
        }
        return temp;
    }

    @SuppressWarnings("all")
    public Matrix<T> transpose() {
        var temp = new Matrix<T>(width, height, cls, null);

        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                temp.setVisited(col, row, visitedMap[row][col]);
                temp.set(col, row, map[row][col]);
            }
        }
        return temp;
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

    public List<MatrixPoint> find(Predicate<? super T> inc) {
        var temp = new ArrayList<MatrixPoint>();
        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                var value = get(row, col);
                if (inc.test(value)) {
                    temp.add(new MatrixPoint(value, row, col));
                }
            }
        }

        return temp;
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

    public void resetVisited() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                visitedMap[i][j] = false;
            }
        }

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

    public boolean hasVisited(MatrixPoint item) {
        return hasVisited(item.row, item.col);
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

    public void setVisited(int row, int col, boolean value) {
        validateDimensions(row, col);
        visitedMap[row][col] = value;
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

    public void applyAll(Function<? super T, ? extends T> inc) {
        for (int row = 0; row < height; row++) {

            for (int col = 0; col < width; col++) {
                apply(row, col, inc);
            }
        }
    }

    public T apply(MatrixPoint item, Function<? super T, ? extends T> inc) {
        return apply(item.row, item.col, inc);
    }

    public T apply(int row, int col, Function<? super T, ? extends T> inc) {
        var value = get(row, col);

        var value2 = inc.apply(value);
        set(row, col, value2);

        return value2;
    }

    public void applyAround(MatrixPoint item, Function<? super T, ? extends T> function) {
        applyAround(item.row, item.col, function);
    }

    public void applyAround(int row, int col, Function<? super T, ? extends T> function) {

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                try {
                    apply(row + i, col + j, function);
                } catch (ArrayIndexOutOfBoundsException e) {
                    //do nothing
                }

            }
        }

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
