package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


@Slf4j
public class Day12 extends AbstractAOC {


    enum Directions {
        NORTH(1, 0),
        SOUTH(-1, 0),
        EAST(0, 1),
        WEST(0, -1);

        private final int r;
        private final int c;

        Directions(int r, int c) {
            this.r = r;
            this.c = c;
        }


    }

    @Override
    public String runPart1() {

        var list = getStringInput("");
        var total = 0;

        var map = new Matrix<>(list, Character.class, '.');

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> (char) c)
                    .toList());

        }

        map.printMatrix(false);

        while (true) {

            var node = map.findFirstNot('.');
            if (node == null) {
                break;
            }
            var nodeSet = computeRegion(node, map);
            var perimeter = 0;
            for (var point : nodeSet) {

                for (var d : Directions.values()) {
                    var r = point.getRow() + d.r;
                    var c = point.getCol() + d.c;
                    if (r >= 0 && r < map.getRows() && c >= 0 && c < map.getCols()) {
                        if (map.get(r, c) != node.getValue()) {
                            perimeter++;
                        }
                    } else {
                        perimeter++;
                    }
                }
            }
            for (var point : nodeSet) {
                map.set(point, '.');
            }
            total += nodeSet.size() * perimeter;
        }
        return String.valueOf(total);
    }

    private HashSet<RCPoint> computeRegion(Matrix.MatrixPoint node, Matrix<Character> map) {
        var region = new HashSet<RCPoint>();
        var toCheck = new ArrayList<RCPoint>();
        toCheck.add(new RCPoint(node.getRow(), node.getCol()));

        while (!toCheck.isEmpty()) {
            var point = toCheck.remove(0);
            region.add(point);

            for (var d : Directions.values()) {
                var r = point.getRow() + d.r;
                var c = point.getCol() + d.c;
                if (r >= 0 && r < map.getRows() && c >= 0 && c < map.getCols()) {
                    if (map.get(r, c) == node.getValue()) {
                        var newPoint = new RCPoint(r, c);
                        if (!region.contains(newPoint) && !toCheck.contains(newPoint)) {
                            toCheck.add(newPoint);
                        }
                    }
                }
            }
        }

        return region;
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");
        var total = 0;

        var map = new Matrix<>(list, Character.class, '.');

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> (char) c)
                    .toList());

        }

        map.printMatrix(false);

        while (true) {

            var node = map.findFirstNot('.');
            if (node == null) {
                break;
            }
            var nodeSet = computeRegion(node, map);
            var perimeter = 0;
            var edgeMap = new HashMap<Directions, HashMap<Integer, List<Integer>>>();
            for (var d : Directions.values()) {
                edgeMap.put(d, new HashMap<>());
            }

            for (var point : nodeSet) {

                for (var d : Directions.values()) {
                    var r = point.getRow() + d.r;
                    var c = point.getCol() + d.c;
                    if (r >= 0 && r < map.getRows() && c >= 0 && c < map.getCols()) {
                        if (map.get(r, c) != node.getValue()) {
                            addEdge(edgeMap, d, new RCPoint(r, c));
                        }
                    } else {
                        addEdge(edgeMap, d, new RCPoint(r, c));
                    }
                }
            }
            for (var point : nodeSet) {
                map.set(point, '.');
            }

            perimeter = computePerimeter(edgeMap);

            total += nodeSet.size() * perimeter;
        }
        return String.valueOf(total);
    }

    private Integer computePerimeter(HashMap<Directions, HashMap<Integer, List<Integer>>> edgeMap) {
        var total = 0;
        for (var d : Directions.values()) {
            var map = edgeMap.get(d);
            for (var key : map.keySet()) {
                var list = map.get(key);
                list.sort(Integer::compareTo);
                Integer last = null;
                for (var i : list) {
                    if (last == null) {
                        last = i;
                        total++;
                        continue;
                    }
                    if (i == last + 1) {
                        last = i;
                        continue;
                    }

                    last = i;
                    total++;
                }
            }
        }
        return total;
    }

    private void addEdge(HashMap<Directions, HashMap<Integer, List<Integer>>> edgeMap, Directions d, RCPoint rcPoint) {
        var map = edgeMap.get(d);
        var key = d.r == 0 ? rcPoint.getCol() : rcPoint.getRow();
//        map.put(key, map.getOrDefault(key, 0) + 1);
        var list = map.getOrDefault(key, new ArrayList<>());
        list.add(d.r == 0 ? rcPoint.getRow() : rcPoint.getCol());
        map.put(key, list);
    }


    @Override
    public String getAnswerPart1() {
        return "1546338";
    }

    @Override
    public String getAnswerPart2() {
        return "978590";
    }
}
