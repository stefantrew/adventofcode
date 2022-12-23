package trew.stefan.aoc2022;

public enum TileColour {

    GREEN("G"),
    RED("R"),
    YELLOW("Y"),
    BLUE("B"),
    ORANGE("O"),
    WHITE("W"),
    VOID(" ");

    final String name;

    TileColour(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
