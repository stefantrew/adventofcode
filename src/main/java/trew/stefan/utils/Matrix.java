package trew.stefan.utils;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Matrix {

    private long[][] map;
    private Integer height;
    private Integer width;

    public Matrix(Integer height, Integer width) {
        this.height = height;
        this.width = width;

        map = new long[height][width];
    }

    private void validateDimentions(int row, int col) throws Exception {
        if (row >= height || row < 0) {
            throw new Exception("Row out of bounds: " + row);
        }
        if (col >= width || col < 0) {
            throw new Exception("Col out of bounds: " + col);
        }

    }

    public long getValue(int row, int col) throws Exception {
        validateDimentions(row, col);
        return map[row][col];
    }

    public Matrix setValue(int row, int col, long value) throws Exception {
        validateDimentions(row, col);
        map[row][col] = value;
        return this;
    }
}
