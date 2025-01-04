package trew.stefan.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DirectionalRCPoint extends RCPoint {
    Direction direction;

    public DirectionalRCPoint(int row, int col) {
        super(row, col);
    }

    public DirectionalRCPoint(int row, int col, Direction direction) {
        super(row, col);
        this.direction = direction;
    }

    public DirectionalRCPoint(RCPoint rcPoint, Direction direction) {
        super(rcPoint.row, rcPoint.col);
        this.direction = direction;
    }

    public DirectionalRCPoint move(Direction dir) {
        var rcPoint = super.move(dir);
        return new DirectionalRCPoint(rcPoint.row, rcPoint.col, dir);
    }

    public DirectionalRCPoint getParent() {
        return (DirectionalRCPoint) super.getParent();
    }


    @Override
    public int hashCode() {
        return Objects.hash(row, col, direction);
    }
}
