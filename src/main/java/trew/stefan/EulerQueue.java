package trew.stefan;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.Matrix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class EulerQueue {

    @AllArgsConstructor
    class Point {
        int row;
        int col;
        Long distance;
    }

    private Matrix<Long> map;
    private Matrix<Long> visitedMap;
    Queue<Point> q1 = new LinkedList<>();

    public List<String> readStrings() {
        var path = getClass().getResource("/inputs/matrix.txt").getFile();

        File file = new File(path);
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public EulerQueue() {

//        var strings = new ArrayList<String>();
//        strings.add("131,673,234,103,18");
//        strings.add("201,96,342,965,150");
//        strings.add("630,803,746,422,111");
//        strings.add("573,699,497,121,956");
//        strings.add("805,732,524,37,331");

        var strings = readStrings();
        int n = strings.size();

        map = new Matrix<>(n, n, Long.class, 0L);


        for (int row = 0; row < strings.size(); row++) {

            var s = strings.get(row);

            var split = s.split(",");
            for (int col = 0; col < split.length; col++) {
                map.set(row, col, Long.parseLong(split[col]));
            }
        }

        map.printMatrix(true);

        var row = 0;
        var col = 0;

        log.info("{}", map.get(col, row));
        Long best = null;
        for (int i = 0; i < n; i++) {
            map.resetVisited();

            q1 = new LinkedList<>();
            visitedMap = new Matrix<>(n, n, Long.class, 0L);
            enqueue(i, col, 0);
            var result = walk();
            best = best == null ? result : Math.min(result, best);
        }
        log.info("best {}", best);
    }

    private void enqueue(int row, int col, long distance) {
        if (row < 0 || col < 0 || row == map.getHeight() || col == map.getWidth()) {
            return;
        }

        q1.add(new Point(row, col, distance));
    }

    private Long walk() {

        Long bestDistance = null;
        while (!q1.isEmpty()) {

            var point = q1.poll();

            var row = point.row;
            var col = point.col;
            var distance = point.distance;

            if (map.hasVisited(row, col)) {

                var bestValue = visitedMap.get(row, col);
                if (bestValue < distance) {
                    continue;
                }
            }

            map.setVisited(row, col, true);
            visitedMap.set(row, col, distance);
            distance += map.get(row, col);

            if (col == map.getWidth() - 1) {
                if (bestDistance == null) {
                    bestDistance = distance;
                }
                bestDistance = Math.min(bestDistance, distance);
            }
            enqueue(row + 1, col, distance);
            enqueue(row - 1, col, distance);
            enqueue(row, col + 1, distance);
//            enqueue(row, col - 1, distance);
        }
        return bestDistance;
    }

    public static void main(String[] args) {
        new EulerQueue();
    }
}
