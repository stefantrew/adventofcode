package trew.stefan.aoc2019.completed;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MockPaintProcessor implements PaintProcessor {

    private Queue<Integer> expectedInput = new LinkedList<>();
    private Queue<Integer> expectedOutput = new LinkedList<>();
    private Long outputColour;
    private Long outputDirection;

    public MockPaintProcessor(List<String> lines) {
        expectedInput.add(0);
        expectedInput.add(0);
        expectedInput.add(0);
        expectedInput.add(0);
        expectedInput.add(1);
        expectedInput.add(0);
        expectedInput.add(0);

        expectedOutput.add(1);
        expectedOutput.add(0);

        expectedOutput.add(0);
        expectedOutput.add(0);

        expectedOutput.add(1);
        expectedOutput.add(0);

        expectedOutput.add(1);
        expectedOutput.add(0);

        expectedOutput.add(0);
        expectedOutput.add(1);

        expectedOutput.add(1);
        expectedOutput.add(0);

        expectedOutput.add(1);
        expectedOutput.add(0);


    }

    @Override
    public boolean run(int input) throws Exception {

        if (expectedInput.isEmpty()) return false;

        int expected = expectedInput.remove();
        if (expected != input) {
            throw new Exception("Input not correct" + input + " should be " + expected);
        }

        outputColour = Long.valueOf(expectedOutput.remove());
        outputDirection = Long.valueOf(expectedOutput.remove());
        return true;
    }

    @Override
    public Long getOutputColour() {
        return outputColour;
    }

    @Override
    public Long getOutputDirection() {
        return outputDirection;
    }
}
