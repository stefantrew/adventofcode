package trew.stefan.utils;


public enum Direction {
    UP("^"),
    DOWN("v"),
    LEFT("<"),
    RIGHT(">");

    final String name;

    Direction(String name) {
        this.name = name;
    }


    public Direction rotate(boolean clockWise) {
        return clockWise ? rotateClockWise() : rotateCounterClockWise();

    }

    public Direction rotateClockWise() {
        return switch (this) {

            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
        };

    }

    public Direction rotateCounterClockWise() {
        return switch (this) {

            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
        };

    }

    public Direction flip() {
        return switch (this) {

            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };

    }


    @Override
    public String toString() {
        return name;
    }
}
