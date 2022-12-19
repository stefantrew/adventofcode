package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.regex.Pattern;

@Slf4j
public class Day18 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;

        var grid = new boolean[25][25][25];

        var list = getStringInput("");
        var p = Pattern.compile("(\\d*),(\\d*),(\\d*)");
        for (var s : list) {

            var m = new AOCMatcher(p.matcher(s));

            if (m.find()) {

                var x = m.getInt(1) + 1;
                var y = m.getInt(2) + 1;
                var z = m.getInt(3) + 1;

                if (grid[x - 1][y][z]) total--;
                else total++;
                if (grid[x + 1][y][z]) total--;
                else total++;
                if (grid[x][y - 1][z]) total--;
                else total++;
                if (grid[x][y + 1][z]) total--;
                else total++;
                if (grid[x][y][z - 1]) total--;
                else total++;
                if (grid[x][y][z + 1]) total--;
                else total++;

                grid[x][y][z] = true;
            }

        }


        return formatResult(total);
    }


    @Override
    public String runPart2() {


        var total = 0;

        var grid = new char[25][25][25];

        var list = getStringInput("");
        var p = Pattern.compile("(\\d*),(\\d*),(\\d*)");
        for (var s : list) {

            var m = new AOCMatcher(p.matcher(s));

            if (m.find()) {

                var x = m.getInt(1) + 1;
                var y = m.getInt(2) + 1;
                var z = m.getInt(3) + 1;

                if (grid[x - 1][y][z] == 'L') total--;
                else total++;
                if (grid[x + 1][y][z] == 'L') total--;
                else total++;
                if (grid[x][y - 1][z] == 'L') total--;
                else total++;
                if (grid[x][y + 1][z] == 'L') total--;
                else total++;
                if (grid[x][y][z - 1] == 'L') total--;
                else total++;
                if (grid[x][y][z + 1] == 'L') total--;
                else total++;

                grid[x][y][z] = 'L';
            }

        }


        for (int x = 0; x < 25; x++) {
            for (int y = 0; y < 25; y++) {
                grid[x][y][0] = 'W';
                grid[x][y][24] = 'W';
            }
        }


        for (int z = 0; z < 25; z++) {
            for (int y = 0; y < 25; y++) {
                grid[0][y][z] = 'W';
                grid[24][y][z] = 'W';
            }
        }


        for (int x = 0; x < 25; x++) {
            for (int z = 0; z < 25; z++) {
                grid[x][0][z] = 'W';
                grid[x][24][z] = 'W';
            }
        }

        while (true) {

            int change = flood(grid);
            if (change == 0) {
                break;
            }
        }

        var empty = countEmpty(grid);

        return formatResult(total - empty);
    }

    private int countEmpty(char[][][] grid) {
        var total = 0;
        for (int x = 1; x < 24; x++) {
            for (int y = 1; y < 24; y++) {
                for (int z = 1; z < 24; z++) {
                    if (grid[x][y][z] == 'L' || grid[x][y][z] == 'W') {
                        continue;
                    }

                    if (grid[x - 1][y][z] == 'L') total++;
                    if (grid[x + 1][y][z] == 'L') total++;
                    if (grid[x][y - 1][z] == 'L') total++;
                    if (grid[x][y + 1][z] == 'L') total++;
                    if (grid[x][y][z - 1] == 'L') total++;
                    if (grid[x][y][z + 1] == 'L') total++;


                }
            }
        }


        return total;
    }


    private int flood(char[][][] grid) {
        var change = 0;
        for (int x = 1; x < 24; x++) {
            for (int y = 1; y < 24; y++) {
                for (int z = 1; z < 24; z++) {
                    if (grid[x][y][z] == 'L' || grid[x][y][z] == 'W') {
                        continue;
                    }
                    if (grid[x - 1][y][z] == 'W'
                        || grid[x + 1][y][z] == 'W'
                        || grid[x][y - 1][z] == 'W'
                        || grid[x][y + 1][z] == 'W'
                        || grid[x][y][z - 1] == 'W'
                        || grid[x][y][z + 1] == 'W') {
                        grid[x][y][z] = 'W';
                        change++;
                    }

                }
            }
        }
        return change;
    }

    @Override
    public String getAnswerPart1() {
        return "4474";
    }

    @Override
    public String getAnswerPart2() {
        return "2518";
    }
}
