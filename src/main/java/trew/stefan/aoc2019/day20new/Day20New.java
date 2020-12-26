package trew.stefan.aoc2019.day20new;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.List;

import static trew.stefan.aoc2019.day20.Direction.*;

@SuppressWarnings("DuplicatedCode")

@Slf4j
public class Day20New implements Day {

    static int[][] DIRECTIONS
            = {{0, 1, SOUTH.ordinal()}, {1, 0, EAST.ordinal()}, {0, -1, NORTH.ordinal()}, {-1, 0, WEST.ordinal()}};

    public void run() {


        List<String> lines = InputReader2019.readStrings(20, "");
        MazeMap map = buildMap(lines);
//        map.printMap();
        NewMazeWalker walker = new NewMazeWalker();
        MazeWalkerNode node = walker.solve(map);
        if (node != null) {
            log.info("Done with {} steps", walker.backtrackPath(node).size());
            walker.printBacktrackPath(node);
        } else {
            log.info("No luck :(");
        }
    }


    MazeMap buildMap(List<String> lines) {
        String str = lines.get(3);
        MazeMap map = new MazeMap(str.length() + 2, lines.size() - 1);
        for (int y = 0; y < lines.size() - 1; y++) {
            char[] chars = lines.get(y + 1).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                map.map[x][y] = new MazePoint(x, y, chars[x]);
            }

        }

        map.linkPoints();


        return map;
    }
}

