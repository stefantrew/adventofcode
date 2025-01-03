package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Direction;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.HashSet;
import java.util.LinkedList;

@Slf4j
public class Day18 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");
        var size = 71;
        var matrix = new Matrix<Character>(size, size, Character.class, '.');

        var count = 0;
        for (var s : list) {
            if (count++ == 1024) {
                break;
            }
            log.info("{}", s);
            var x = Integer.parseInt(s.split(",")[0]);
            var y = Integer.parseInt(s.split(",")[1]);
            matrix.set(y, x, '#');
        }

        var cache = new HashSet<RCPoint>();
        var node = matrix.getPoint(0, 0);

        var queue = new LinkedList<RCPoint>();
        queue.add(node.getRcPoint());

        while (!queue.isEmpty()) {
            var point = queue.poll();
            if (cache.contains(point)) {
                continue;
            }
            cache.add(point);
            for (var dir : Direction.basic()) {
                var newPoint = point.move(dir);
                if (newPoint.getRow() == size - 1 && newPoint.getCol() == size - 1) {
                    return formatResult(getDistance(newPoint));
                }

                var x = newPoint.getCol();
                var y = newPoint.getRow();
                if (x >= 0 && x < size && y >= 0 && y < size) {
                    if (matrix.get(y, x) == '.') {
                        queue.add(newPoint);
                    }
                }

            }
        }

        return formatResult(total);
    }

    int getDistance(RCPoint node) {
        var count = 0;
        while (node != null) {
            count++;
            node = node.getParent();
        }
        return count - 1;
    }

    @Override
    public String runPart2() {


        var total = 0;

        var list = getStringInput("");
        var size = 71;
        var matrix = new Matrix<Character>(size, size, Character.class, '.');

        var count = 0;
        for (var s : list) {
            var x = Integer.parseInt(s.split(",")[0]);
            var y = Integer.parseInt(s.split(",")[1]);
            matrix.set(y, x, '#');
            total = getTotal(matrix, size);
            log.info("{} => {} {}", count, total, s);
            if (total == 0) {
                return s;
            }
        }
        matrix.printMatrix(false);


        return formatResult(total);
    }

    private int getTotal(Matrix<Character> matrix, int size) {
        var cache = new HashSet<RCPoint>();
        var node = matrix.getPoint(0, 0);

        var queue = new LinkedList<RCPoint>();
        queue.add(node.getRcPoint());

        while (!queue.isEmpty()) {
            var point = queue.poll();
            if (cache.contains(point)) {
                continue;
            }
            cache.add(point);
            for (var dir : Direction.basic()) {
                var newPoint = point.move(dir);
                if (newPoint.getRow() == size - 1 && newPoint.getCol() == size - 1) {
                    return getDistance (newPoint);
                }

                var x = newPoint.getCol();
                var y = newPoint.getRow();
                if (x >= 0 && x < size && y >= 0 && y < size) {
                    if (matrix.get(y, x) == '.') {
                        queue.add(newPoint);
                    }
                }

            }
        }
        return 0;
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
