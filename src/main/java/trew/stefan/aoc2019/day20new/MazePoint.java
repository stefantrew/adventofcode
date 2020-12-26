package trew.stefan.aoc2019.day20new;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MazePoint {

    public void setEntry() {
        type = Type.ENTRY;
        id = "AA";
    }

    public void setExit() {
        type = Type.EXIT;
        id = "ZZ";
    }

    public void setPortal(MazePoint portal, String id, boolean isInner) {
        type = isInner ? Type.INNER_PORTAL : Type.OUTER_PORTAL;
        this.id = id;
        this.portal = portal;
    }

    public boolean isWall() {
        return type == Type.WALL || type == Type.OPEN_SPACE || type == Type.LABEL;
    }


    public boolean isInnerPortal() {
        return type == Type.INNER_PORTAL;
    }

    public boolean isOuterPortal() {
        return type == Type.OUTER_PORTAL;
    }

    public MazePoint getPortal() {
        return portal;
    }

    public boolean isPortal() {
        return portal != null;
    }

    public boolean isFree() {
        return type == Type.FREE;
    }

    enum Type {
        WALL,
        LABEL,
        FREE,
        OPEN_SPACE,
        ENTRY,
        EXIT,
        OUTER_PORTAL,
        INNER_PORTAL

    }

    @Getter
    private String id = "";
    @Getter
    private int X;
    @Getter
    private int Y;
    @Getter
    private Type type;
    private char sourceChar;
    private MazePoint portal;

    public MazePoint(int x, int y, char sourceChar) {
        X = x;
        Y = y;
        this.sourceChar = sourceChar;

        switch (this.sourceChar) {
            case '#':
                type = Type.WALL;
                break;
            case '.':
                type = Type.FREE;
                break;
            case ' ':
                type = Type.OPEN_SPACE;
                break;

            default:
                type = Type.LABEL;
        }
    }

    public boolean isEntry() {
        return type == Type.ENTRY;
    }

    public boolean isExit() {
        return type == Type.EXIT;
    }

    public boolean isSameCoord(int x, int y) {
        return X == x && Y == y;
    }

    @Override
    public String toString() {
        return String.valueOf(sourceChar);
    }
}
