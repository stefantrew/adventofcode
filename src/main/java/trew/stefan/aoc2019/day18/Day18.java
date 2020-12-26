package trew.stefan.aoc2019.day18;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class Day18 implements Day {


    MazeMap map;
    Day18MazeWalker walker = new Day18MazeWalker();
    Map<String, Result> cache = new HashMap<>();

    public void run() {
        List<String> lines = InputReader2019.readStrings(18, "_gold");
        buildMap(lines);
        map.printMap();
        Result result = getReachableKeys(map);
        log.info("Result {} {} Target {}", result.distance, result.path, lines.get(0));
        log.info("counter {}", counter);
    }

    void buildMap(List<String> lines) {

        map = new MazeMap(lines.size() - 1, lines.get(1).length());
        for (int i = 1; i < lines.size(); i++) {
            map.setRow(i - 1, lines.get(i));
        }
    }

    int counter = 0;

    Result getReachableKeys(MazeMap map) {
        if (++counter % 10000 == 0) log.info("counter {} {}", counter, cache.size());
        if (map.keys.size() == 0) {
            Result lowestResult = new Result();
            lowestResult.distance = 0;
            return lowestResult;
        }
        String code = map.getHash();
        if (cache.containsKey(code)) {
            return cache.get(code).getClone();
        }

        Result lowestResult = null;
        for (char c : map.keys.keySet()) {
            Point key = map.getKey(c);
            List<Point> path = walker.solve(map, c, key.getQuadrant());
//            log.info("{} {}", c, path.size());
            if (path.size() > 0) {
                MazeMap map2 = map.moveToKey(c);
                String code2 = map2.getHash();

                Result distance = getReachableKeys(map2);
                if (distance != null) {

                    distance.distance += path.size() - 1;
                    if (lowestResult == null || distance.distance < lowestResult.distance) {
                        lowestResult = distance;
                        lowestResult.path = c + (lowestResult.path.equals("") ? " " : ", ") + lowestResult.path;
                    }
                }
            }
        }
        if (lowestResult != null) {
            cache.put(code, lowestResult.getClone());

        }


        return lowestResult;
    }
}
