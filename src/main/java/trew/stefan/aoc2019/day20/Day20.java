package trew.stefan.aoc2019.day20;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.List;

import static trew.stefan.aoc2019.day20.Direction.*;

@Slf4j
public class Day20 implements Day {

    public static int[][] DIRECTIONS
            = {{0, 1, SOUTH.ordinal()}, {1, 0, EAST.ordinal()}, {0, -1, NORTH.ordinal()}, {-1, 0, WEST.ordinal()}};

    public void run() {
        List<String> lines = InputReader2019.readStrings(20, "");
        MazeMap map = buildMap(lines);
//        map.printMap();
        PortalMazeWalker walker = new PortalMazeWalker();
        MazePoint result = walker.solve(map, 0, map.getEntry(), 0);
        log.info("result {}", walker.backtrackPath(result).size() - 1);
        log.info("Target {}", lines.get(0));
    }

    MazeMap buildMap(List<String> lines) {
        String str = lines.get(3);
        log.info("{} {}", str.length(), str);
        MazeMap map = new MazeMap(str.length() + 2, lines.size() - 1);
        for (int y = 0; y < lines.size() - 1; y++) {
            char[] chars = lines.get(y + 1).toCharArray();
            for (int x = 0; x < chars.length; x++) {
                char c = chars[x];
                MazePoint.Type type = MazePoint.Type.LABEL;
                switch (c) {
                    case ' ':
                        type = MazePoint.Type.FREE;
                        break;
                    case '.':
                        type = MazePoint.Type.OPEN;
                        break;
                    case '#':
                        type = MazePoint.Type.WALL;
                        break;
                }
                map.map[x][y] = new MazePoint(x, y, c + "", type);
            }

        }

        map.linkPoints();


        return map;
    }


}

