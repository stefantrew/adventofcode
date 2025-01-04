package trew.stefan.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
public class RCPoint {
    protected int row;
    protected int col;

    protected RCPoint parent;

    public RCPoint(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public RCPoint move(Direction dir) {
        return move(dir, 1);
    }

    public RCPoint move(Direction dir, int i) {

        var point = new RCPoint(row, col);
        switch (dir) {

            case UP, NORTH -> point.row -= i;
            case DOWN, SOUTH -> point.row += i;
            case LEFT, WEST -> point.col -= i;
            case RIGHT, EAST -> point.col += i;
            case NORTH_EAST -> {
                point.row -= i;
                point.col += i;
            }
            case SOUTH_EAST -> {
                point.row += i;
                point.col += i;
            }
            case NORTH_WEST -> {
                point.row -= i;
                point.col -= i;
            }
            case SOUTH_WEST -> {
                point.row += i;
                point.col -= i;
            }
            case NONE -> {

            }
        }
        point.setParent(this);

        return point;
    }

    @Override
    public String toString() {
        return "row=" + row +
               ", col=" + col;
    }

    public RCPoint clonePoint() {
        return new RCPoint(row, col);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPoint rcPoint = (RCPoint) o;
        return row == rcPoint.row && col == rcPoint.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
