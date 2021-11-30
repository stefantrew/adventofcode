package trew.stefan.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Data
@Slf4j
@Accessors(chain = true)
@NoArgsConstructor
public class Simple2DPoint {

    public int X;
    public int Y;

    public Simple2DPoint(int y, int x) {
        Y = y;
        X = x;
    }

    @Override
    public String toString() {
        return String.format("<x= %3d, y=%3d>", X, Y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Simple2DPoint point = (Simple2DPoint) o;
        return Objects.equals(X, point.X) &&
                Objects.equals(Y, point.Y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

}
