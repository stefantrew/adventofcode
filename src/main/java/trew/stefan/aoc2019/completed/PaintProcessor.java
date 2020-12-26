package trew.stefan.aoc2019.completed;

public interface PaintProcessor {
    boolean run(int input) throws Exception;

    Long getOutputColour();

    Long getOutputDirection();
}
