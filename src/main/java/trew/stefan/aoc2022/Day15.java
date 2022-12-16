package trew.stefan.aoc2022;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.IntList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;


@Slf4j
public class Day15 extends AbstractAOC {

    enum NodeType {

        SENSOR("S"),
        BEACON("B"),
        VALID("."),
        INVALID("#");

        String label;

        NodeType(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    @AllArgsConstructor
    static class Node {

        private int x;
        private int y;

        private NodeType type;

        private Node relatedNode;

        int getDistance(Node ref) {
            return Math.abs(ref.x - x) + Math.abs(ref.y - y);

        }

        int getRadius() {
            if (type != NodeType.SENSOR) {
                return 0;
            }
            return getDistance(relatedNode);
        }

        public Integer getValue(int target) {
            return x * target + y;
        }
    }


    @Override
    public String runPart1() {
        var list = getStringInput("_sample");

        var pattern = Pattern.compile("Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)");
        var ascore = 0;
//        var target = 2000000;

        var target = 10;
        var sensors = new ArrayList<Node>();
        var invalids = new HashSet<Integer>();
        for (var item : list) {
            if (item.isBlank()) continue;
            var m = new AOCMatcher(pattern.matcher(item));
            if (m.find()) {
                var beacon = new Node(m.getInt(3), m.getInt(4), NodeType.BEACON, null);
                var sensor = new Node(m.getInt(1), m.getInt(2), NodeType.SENSOR, beacon);
//                log.info("{}", sensor.getRadius());
                if (beacon.y == target) {
                    invalids.add(beacon.x);
                }
                if (sensor.y == target) {
                    invalids.add(sensor.x);

                }
                sensors.add(sensor);
            }
        }

        ascore = invalids.size();
        for (Node sensor : sensors) {

            var distance = sensor.getRadius();
            var minX = sensor.x - distance;
            var maxX = sensor.x + distance;
            var minY = sensor.y - distance;
            var maxY = sensor.y + distance;

            if (minY > target || maxY < target) {
                continue;
            }
            for (int x = minX; x <= maxX; x++) {
                var node = new Node(x, target, NodeType.INVALID, null);
                if (node.getDistance(sensor) <= distance) {
                    invalids.add(x);
                }
            }

        }


        return String.valueOf(invalids.size() - ascore);
    }

    boolean inRange(int target, int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }
        if (x > target || y > target) {
            return false;
        }
        return true;
    }

//    @Override
//    public String runPart2() {
//        var list = getStringInput("");
//
//        var pattern = Pattern.compile("Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)");
//        var ascore = 0;
//        var target = 4000000;
//
//        var sensors = new ArrayList<Node>();
//        var invalids = new HashSet<Integer>();
//
//        for (int i = 0; i < target; i++) {
//            for (int j = 0; j < target; j++) {
//                invalids.add(i * target + j);
//            }
//        }
//
//        for (var item : list) {
//            if (item.isBlank()) continue;
//            var m = new AOCMatcher(pattern.matcher(item));
//            if (m.find()) {
//                var beacon = new Node(m.getInt(3), m.getInt(4), NodeType.BEACON, null);
//                var sensor = new Node(m.getInt(1), m.getInt(2), NodeType.SENSOR, beacon);
//                log.info("{}", sensor.getRadius());
//                if (inRange(target, beacon.x, beacon.y)) {
//                    invalids.remove(beacon.getValue(target));
//                }
//
//                if (inRange(target, sensor.x, sensor.y)) {
//                    invalids.remove(sensor.getValue(target));
//                }
//                sensors.add(sensor);
//            }
//        }
//
//        ascore = invalids.size();
//        for (Node sensor : sensors) {
//
//            var distance = sensor.getRadius();
//            var minX = sensor.x - distance;
//            var maxX = sensor.x + distance;
//            var minY = sensor.y - distance;
//            var maxY = sensor.y + distance;
//
//            minX = Math.max(minX, 0);
//            minY = Math.max(minY, 0);
//
//            maxX = Math.min(maxX, target);
//            maxY = Math.min(maxY, target);
//            for (int x = minX; x <= maxX; x++) {
//                for (int y = minY; y <= maxY; y++) {
//                    var node = new Node(x, y, NodeType.INVALID, null);
//                    if (node.getDistance(sensor) <= distance) {
//                        invalids.remove(node.getValue(target));
//                    }
//                }
//            }
//
//        }
//
//
//        return String.valueOf(invalids.size());
//    }

    class SignalRange {

        int start = 0;
        int end = 20;

        NodeType type = NodeType.VALID;

        public SignalRange(int start, int end, NodeType type) {
            this.start = start;
            this.end = end;
            this.type = type;
        }

        public List<SignalRange> split(SignalRange range) {
            var result = new ArrayList<SignalRange>();
            if (start > range.end) {

                return result;
            }
            if (end < range.start) {

                return result;
            }

            if (start >= range.start && end <= range.end) {

                return result;
            }
//            if (start < range.end) {
//                result.add(new SignalRange())
//            }

            return result;
        }
    }

    @Override
    public String runPart2() {

        var sample = false;
        var box = sample ? 20 : 4000000;


        var sensors = getSensors(sample);
        for (int i = 0; i < box + 1; i++) {
            if (i % 100 == 0) {
                log.info("--- {} {} ---", i, box - i);
            }
            scanRow(i, box, sensors, new SignalRange(0, box, NodeType.VALID));
        }

        return "";
    }

    int scanRow(int target, int box, ArrayList<Node> sensors, SignalRange range) {
        var ascore = 0;


        for (Node sensor : sensors) {

            var distance = sensor.getRadius();
            var minX = sensor.x - distance;
            var maxX = sensor.x + distance;
            var minY = sensor.y - distance;
            var maxY = sensor.y + distance;

            minX = Math.max(minX, 0);
            maxX = Math.min(maxX, box);

            if (minY > target || maxY < target) {
                continue;
            }
            range.split(new SignalRange(minX, minY, NodeType.INVALID));
//            for (int x = minX; x <= maxX; x++) {
//                var node = new Node(x, target, NodeType.INVALID, null);
//                if (node.getDistance(sensor) <= distance) {
//                    invalids.remove(node.getValue(target));
//                }
//            }

        }

        return 1;
//        return range.hasValid();
    }

    private ArrayList<Node> getSensors(boolean sample) {
        var list = getStringInput(sample ? "_sample" : "");

        var pattern = Pattern.compile("Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)");


        var sensors = new ArrayList<Node>();
        for (var item : list) {
            if (item.isBlank()) continue;
            var m = new AOCMatcher(pattern.matcher(item));
            if (m.find()) {
                var beacon = new Node(m.getInt(3), m.getInt(4), NodeType.BEACON, null);
                var sensor = new Node(m.getInt(1), m.getInt(2), NodeType.SENSOR, beacon);
//                log.info("{}", sensor.getRadius());

                sensors.add(sensor);
            }
        }
        return sensors;
    }

    @Override
    public String getAnswerPart1() {
        return "7553";
    }

    @Override
    public String getAnswerPart2() {
        return "2758";
    }
}
