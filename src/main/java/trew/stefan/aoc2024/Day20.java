package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Direction;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

@Slf4j
public class Day20 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");
        var size = list.get(0).length();
        var matrix = new Matrix<Character>(size, size, Character.class, '.');

        var count = 0;
        for (int row = 0; row < list.size(); row++) {
            var s = list.get(row);

            for (var col = 0; col < s.length(); col++) {
                var c = s.charAt(col);
                if (col == 0 || col == size - 1 || row == 0 || row == size - 1) {
                    matrix.set(row, col, '%');
                } else {


                    matrix.set(row, col, s.charAt(col));
                }
            }
        }
        matrix.printMatrix(false);
        var start = matrix.find('S').get(0).getRcPoint();
        var end = matrix.find('E').get(0).getRcPoint();

        matrix.set(start, '.');
        matrix.set(end, '.');
        var baseLine = getTotal(matrix, size, start, end);
        log.info("{}", baseLine);

        var cheats = new HashMap<String, Integer>();
        var totals = new HashMap<Integer, Integer>();

        for (var matrixPoint : matrix.find('#')) {
            var point = matrixPoint.getRcPoint();
            for (var dir : Direction.basic()) {
                var newPoint = point.move(dir);
                var value = matrix.get(newPoint);
                if (value.equals('.')) {
                    var key = point.toString() + newPoint.toString();

                    if (cheats.containsKey(key)) {
                    } else {
                        cheats.put(key, 1);

                        var old = matrix.get(point);
                        var old2 = matrix.get(newPoint);
                        matrix.set(point, '1');
                        matrix.set(newPoint, '2');
                        var total1 = baseLine - getTotal(matrix, size, start, end);

                        matrix.set(point, old);
                        matrix.set(newPoint, old2);
                        if (total1 == 0 || total1 == baseLine) {
                            continue;
                        }

                        if (total1 >= 100) {
                            total++;
                            var count1 = totals.getOrDefault(total1, 0);
                            totals.put(total1, count1 + 1);
                        }
                    }

                }
            }
        }

        log.info("{}", totals);

//1382
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


        return formatResult(total);
    }

    private int getTotal(Matrix<Character> matrix, int size, RCPoint start, RCPoint end) {
        var cache = new HashSet<RCPoint>();

        var queue = new LinkedList<RCPoint>();
        queue.add(start);
        var usedCheat = matrix.find('1').isEmpty();

        while (!queue.isEmpty()) {
            var point = queue.poll();
            if (cache.contains(point)) {
                continue;
            }
            cache.add(point);
            for (var dir : Direction.basic()) {
                var current = matrix.get(point);
                var newPoint = point.move(dir);
                var next = matrix.get(newPoint);

                var x = newPoint.getCol();
                var y = newPoint.getRow();
                if (x >= 0 && x < size && y >= 0 && y < size) {
                    if (current == '1' && next == '2') {
                        usedCheat = true;
                        queue.add(newPoint);
                    } else if (current == '1') {
                        continue;
                    } else if (next == '2' && current != '.') {
                        continue;
                    } else if (next == '.' || next == '1') {
                        queue.add(newPoint);
                    }
                }


                if (newPoint.equals(end)) {
                    return usedCheat ? getDistance(newPoint) : 0;
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
