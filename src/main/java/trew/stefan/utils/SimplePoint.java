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
public class SimplePoint {

    public int X;
    public int Y;
    public int Z;

    public SimplePoint(int x, int y, int z) {
        X = x;
        Y = y;
        Z = z;
    }

    @Override
    public String toString() {
        return String.format("<x= %3d, y=%3d, z=%3d>", X, Y, Z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePoint point = (SimplePoint) o;
        return Objects.equals(X, point.X) &&
                Objects.equals(Y, point.Y) &&
                Objects.equals(Z, point.Z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y, Z);
    }

}
