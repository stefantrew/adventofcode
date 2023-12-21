package trew.stefan.utils;


public enum Direction {
    UP("^"),
    DOWN("v"),
    LEFT("<"),
    RIGHT(">"),

    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W"),

    NONE("R"),

    NORTH_EAST("NE"),
    NORTH_SOUTH("NS"),
    EAST_WEST("EW"),
    SOUTH_EAST("SE"),
    NORTH_WEST("NW"),
    SOUTH_WEST("S");

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

            case NORTH -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case EAST -> SOUTH_EAST;
            case SOUTH_EAST -> SOUTH;
            case SOUTH -> SOUTH_WEST;
            case SOUTH_WEST -> WEST;
            case WEST -> NORTH_WEST;
            case NORTH_WEST -> NORTH;
            case NONE -> NONE;
            default -> throw new RuntimeException("Unknown direction");
        };

    }

    public Direction rotateCounterClockWise() {
        return switch (this) {

            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;


            case NORTH -> NORTH_WEST;
            case SOUTH -> SOUTH_EAST;
            case EAST -> NORTH_EAST;
            case WEST -> SOUTH_WEST;
            case NORTH_EAST -> NORTH;
            case SOUTH_EAST -> EAST;
            case NORTH_WEST -> WEST;
            case SOUTH_WEST -> SOUTH;
            case NONE -> NONE;
            default -> throw new RuntimeException("Unknown direction");
        };

    }

    public Direction flip() {
        return switch (this) {

            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;

            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case NORTH_EAST -> SOUTH_WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case NORTH_WEST -> SOUTH_EAST;
            case SOUTH_WEST -> NORTH_EAST;
            case NONE -> NONE;
            default -> throw new RuntimeException("Unknown direction");
        };

    }


    @Override
    public String toString() {
        return name;
    }
}
