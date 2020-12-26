package trew.stefan.aoc2019.day20;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Data
@Slf4j
@Accessors(chain = true)
@NoArgsConstructor
public class MazePoint {


    public Integer extra = 0;

    public MazePoint(int x, int y, MazePoint cur) {
        this.X = x;
        this.Y = y;
        this.parent = cur;
    }

    enum Type {
        WALL,
        LABEL,
        FREE,
        OPEN

    }

    private Type type;
    public Integer X;
    public Integer Y;
    MazePoint parent;

    MazePoint portal = null;
    boolean isOuter = false;

    Direction labelDirection;
    String label = "";
    String id = "";

    public MazePoint(int x, int y, String label, Type type) {
        X = x;
        Y = y;
        this.type = type;
        switch (type) {

            case WALL:
                this.label = "#";
                break;
            case LABEL:
                this.label = label;
                break;
            case FREE:
                this.label = " ";
                break;
            case OPEN:
                this.label = ".";
                break;
        }
    }

    void setPortal(MazePoint portal, Direction labelDirection) throws Exception {
        if (!portal.label.equals(label)) {
            throw new Exception("Labels not the same");
        }
        this.labelDirection = labelDirection;

        this.portal = portal;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazePoint point = (MazePoint) o;
        return Objects.equals(X, point.X) &&
                Objects.equals(Y, point.Y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

}
