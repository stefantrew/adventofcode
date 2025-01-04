package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Direction;
import trew.stefan.utils.DirectionalRCPoint;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day16 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


        var list = getStringInput("");
        var matrix = new Matrix<Character>(list.size(), list.get(0).length(), Character.class, '.');

        var row = 0;
        for (var s : list) {
            for (int i = 0; i < s.length(); i++) {
                matrix.set(i, row, s.charAt(i));
            }
            row++;
        }

        var best = getBest(matrix);

        return formatResult(best);
    }

    private Integer getBest(Matrix<Character> matrix) {
        var start = matrix.findFirst('S');
        var queue = new LinkedList<DirectionalRCPoint>();
        var rcPoint = new DirectionalRCPoint(start.getRcPoint(), Direction.RIGHT);
        queue.add(rcPoint);
        var visited = new HashMap<DirectionalRCPoint, Integer>();
        var results = new HashSet<Integer>();

        while (!queue.isEmpty()) {
            var point = queue.poll();
            if (visited.containsKey(point) && visited.get(point) < calcDistance(point)) {
                continue;
            }
            visited.put(point, calcDistance(point));

            if (matrix.get(point) == 'E') {
                results.add(calcDistance(point));
            }
            for (var dir : Direction.basic()) {

                if (dir.flip() == point.getDirection()) {
                    continue;
                }

                var newPoint = point.move(dir);
                if (matrix.get(newPoint) == 'E') {
                    results.add(calcDistance(newPoint));
                }
                if (matrix.get(newPoint) == '.') {
                    queue.add(newPoint);
                }
            }
        }
        log.info("Results: {}", results);
        var best = results.stream().sorted().toList().get(0);
        return best;
    }

    int calcDistance(DirectionalRCPoint point) {
        var distance = 0;
        while (point.getParent() != null) {
            distance++;
            if (point.getDirection() != point.getParent().getDirection()) {
                distance += 1000;
            }
            point = point.getParent();
        }
        return distance;
    }

    @Override
    public String runPart2() {


        var list = getStringInput("");
        var matrix = new Matrix<Character>(list.size(), list.get(0).length(), Character.class, '.');

        var row = 0;
        for (var s : list) {
            for (int i = 0; i < s.length(); i++) {
                matrix.set(row, i, s.charAt(i));
            }
            row++;
        }

        var best = getBest(matrix);
        matrix.printMatrix(false);

        log.info("Best: {}", best);
        var start = matrix.findFirst('S');
        var queue = new LinkedList<DirectionalRCPoint>();
        var rcPoint = new DirectionalRCPoint(start.getRcPoint(), Direction.RIGHT);
        queue.add(rcPoint);
        var visited = new HashMap<DirectionalRCPoint, Integer>();
        var spots = new HashSet<DirectionalRCPoint>();

        while (!queue.isEmpty()) {
            var point = queue.poll();
            var distance = calcDistance(point);

            if (visited.containsKey(point) && visited.get(point) < distance) {
                continue;
            }
            visited.put(point, distance);

            if (matrix.get(point) == 'E') {
                if (distance == best) {
                    addSpots(point, spots);
                }
            }
            for (var dir : Direction.basic()) {

                if (dir.flip() == point.getDirection()) {
                    continue;
                }

                var newPoint = point.move(dir);
                if (matrix.get(newPoint) == 'E') {
                    if (calcDistance(newPoint) == best) {
                        addSpots(newPoint, spots);
                    }
                } else if (matrix.get(newPoint) == '.') {
                    queue.add(newPoint);
                }
            }
        }

        for (var spot : spots) {
            matrix.set(spot, 'O');
        }

        matrix.printMatrix(false);

        return formatResult(matrix.count(character -> character == 'O'));
    }

    private void addSpots(DirectionalRCPoint point, HashSet<DirectionalRCPoint> spots) {
        log.info("Adding spot: {}", point);
        spots.add(point);
        while (point.getParent() != null) {
            point = point.getParent();
            spots.add(point);
        }
    }


    @Override
    public String getAnswerPart1() {
        return "";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
