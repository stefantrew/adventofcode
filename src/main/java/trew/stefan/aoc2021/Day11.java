package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

@Slf4j
public class Day11 extends AbstractAOC {
    @AllArgsConstructor
    class PowerResult {

        int size;
        int x;
        int y;
        int power;

        @Override
        public String toString() {
            return x + "," + y;
        }
    }

    public int getPower(int x, int y, int serial) {
        var rackId = x + 10;
        var powerLevel = rackId * y + serial;
        powerLevel *= rackId;

        var digit = (powerLevel / 100) % 10;
        return digit - 5;
    }

    @Override
    public String runPart1() {


        var input = 2187;

        var limit = 300;

        int[][] grid = buildGrid(input, limit);


        return getMax(limit, grid, 3).toString();


    }

    private int[][] buildGrid(int input, int limit) {
        var grid = new int[limit][limit];

        for (int x = 0; x < limit; x++) {
            for (int y = 0; y < limit; y++) {
                grid[y][x] = getPower(x, y, input);
            }
        }
        return grid;
    }

    private PowerResult getMax(int limit, int[][] grid, int size) {
        size--;
        Integer max = null;
        var maxX = 0;
        var maxY = 0;
        for (int x = 0; x < limit - size; x++) {
            for (int y = 0; y < limit - size; y++) {
                var sum = 0;
                for (int i = 0; i <= size; i++) {
                    for (int j = 0; j <= size; j++) {

                        sum += grid[y + i][x + j];
                    }
                }

                if (max == null || sum > max) {
                    maxX = x;
                    maxY = y;
                    max = sum;
                }

            }
        }

        return new PowerResult(size + 1, maxX, maxY, max);
    }

    @Override
    public String runPart2() {

        var input = 2187;

        var limit = 300;

        int[][] grid = buildGrid(input, limit);

        PowerResult max = null;
        for (int i = 1; i < 20; i++) {
            var result = getMax(limit, grid, i);

            if (max == null || result.power > max.power) {
                max = result;
            }
        }
        return max + "," + max.size;
    }

    @Override
    public String getAnswerPart1() {
        return "235,85";
    }

    @Override
    public String getAnswerPart2() {
        return "233,40,13";
    }
}
