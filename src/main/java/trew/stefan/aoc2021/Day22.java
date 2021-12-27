package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day22 extends AbstractAOC {

    @Override
    public String runPart1() {

        var total = 0L;
        return formatResult(total);
    }

    private long getTotal(java.util.List<Step> list, int lowerX, int lowerY, int lowerZ, int size) {
        var h = size;
        long total = 0L;
        var grid = new boolean[h][h][h];


        return total;
    }

    @AllArgsConstructor
    @ToString
    class IntersectLine {

        Character axis;
        int point;
    }

    @ToString
    class Box {
        int startX;
        int endX;
        int startY;
        int endY;
        int startZ;
        int endZ;

        Set<Step> steps = new HashSet<>();


        public boolean sameSpace(Box box) {
            return startX == box.startX && endX == box.endX && startY == box.startY && endY == box.endY && startZ == box.startZ && endZ == box.endZ;
        }


        List<Box> split(IntersectLine line) {

            var result = new ArrayList<Box>();
            if (line.axis == 'x') {
                if (startX < line.point && line.point < endX) {

                    result.add(new Box(steps, startX, line.point, startY, endY, startZ, endZ));
                    result.add(new Box(steps, line.point, endX, startY, endY, startZ, endZ));
                } else {
                    result.add(this);
                }
            }
            if (line.axis == 'y') {
                if (startY < line.point && line.point < endY) {

                    result.add(new Box(steps, startX, endX, startY, line.point, startZ, endZ));
                    result.add(new Box(steps, startX, endX, line.point, endY, startZ, endZ));
                } else {
                    result.add(this);
                }
            }
            if (line.axis == 'z') {
                if (startZ < line.point && line.point < endZ) {

                    result.add(new Box(steps, startX, endX, startY, endY, startZ, line.point));
                    result.add(new Box(steps, startX, endX, startY, endY, line.point, endZ));
                } else {
                    result.add(this);
                }
            }
            return result;
        }

        public Box(Step step, int startX, int endX, int startY, int endY, int startZ, int endZ) {
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
            this.startZ = startZ;
            this.endZ = endZ;

            this.steps.add(step);

        }

        public Box(Set<Step> steps, int startX, int endX, int startY, int endY, int startZ, int endZ) {
            this.startX = startX;
            this.endX = endX;
            this.startY = startY;
            this.endY = endY;
            this.startZ = startZ;
            this.endZ = endZ;

            this.steps = steps;

        }

        public List<IntersectLine> intersects(Box test) {
            var result = new ArrayList<IntersectLine>();
            if (inStartRange(test.startX, startX, endX)) {
                result.add(new IntersectLine('x', test.startX));
            }
            if (inEndRange(test.endX, startX, endX)) {
                result.add(new IntersectLine('x', test.endX));
            }
            if (inStartRange(test.startY, startY, endY)) {
                result.add(new IntersectLine('y', test.startY));
            }
            if (inEndRange(test.endY, startY, endY)) {
                result.add(new IntersectLine('y', test.endY));
            }
            if (inStartRange(test.startZ, startZ, endZ)) {
                result.add(new IntersectLine('z', test.startZ));
            }
            if (inEndRange(test.endZ, startZ, endZ)) {
                result.add(new IntersectLine('z', test.endZ));
            }

            return result;
        }

        private boolean inStartRange(int point, int start2, int end2) {
            return start2 < point && point < end2;
        }

        private boolean inEndRange(int point, int start2, int end2) {
            return start2 < point && point < end2;
        }

        long getVolume() {

            var state = false;
            var list = steps.stream().sorted(Comparator.comparingInt(o -> o.order)).collect(Collectors.toList());

            for (Step step : list) {
                state = step.task.equals("on");
            }

            if (!state) {
                return 0;
            }

            return (endX - startX) * (endY - startY) * (endZ - startZ);

        }

    }

    @ToString
    @NoArgsConstructor
    class Step {

        String task;
        int order;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Step step = (Step) o;
            return order == step.order && Objects.equals(task, step.task);
        }

        @Override
        public int hashCode() {
            return Objects.hash(task, order);
        }

        public Step(String task, int order) {
            this.task = task;

            this.order = order;
        }

    }

    List<Box> split(Box box1, Box box2, List<IntersectLine> lines, List<IntersectLine> lines2) {
        var result = split(box1, lines);

        var temp = split(box2, lines2);
        for (Box boxA : temp) {
            var found = false;
            for (Box boxB : result) {
                if (boxA.sameSpace(boxB)) {
                    boxA.steps.addAll(boxB.steps);
//                    log.info("Found match {}", boxA);
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(boxA);
            }
        }

        return result;
    }

    List<Box> split(Box box, List<IntersectLine> lines) {
        var result = new ArrayList<Box>();

        result.add(box);

        for (IntersectLine line : lines) {
            var temp = new ArrayList<Box>();

            for (Box box1 : result) {
                temp.addAll(box1.split(line));
            }
            result = temp;
        }

        return result;
    }

    @Override
    public String runPart2() {


        var total = 0L;

        List<Box> boxes = new ArrayList<>();
        for (String s : getStringInput("_sample")) {

            var p = Pattern.compile("(on|off) x=(-?\\d*)\\.\\.(-?\\d*),y=(-?\\d*)\\.\\.(-?\\d*),z=(-?\\d*)\\.\\.(-?\\d*)");
            var m = new AOCMatcher(p.matcher(s));

            if (m.find()) {
                m.print();
                var step = new Step(m.group(1), boxes.size());
                boxes.add(new Box(step, m.getInt(2), m.getInt(3) + 1, m.getInt(4), m.getInt(5) + 1, m.getInt(6), m.getInt(7) + 1));
            }

        }


        List<Box> result = new ArrayList<Box>();

        for (Box box : boxes) {

            result = getBoxes(result, box);
        }


        for (Box splitBox : result) {
            total += splitBox.getVolume();
            log.info("result {} {}", splitBox, splitBox.getVolume());
        }
        return formatResult(total);
    }

    List<Box> getBoxes(List<Box> boxes, Box box2) {
        List<Box> result = new ArrayList<>();
        if (boxes.isEmpty()) {
            result.add(box2);
            return result;

        }

        for (Box box : boxes) {
            var temp = getBoxes(box, box2);
            for (Box boxA : temp) {
                var found = false;
                for (Box boxB : result) {
                    if (boxA.sameSpace(boxB)) {
                        boxA.steps.addAll(boxB.steps);
//                        log.info("Found match {}", boxA);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    result.add(boxA);
                }
            }

        }

        return result;
    }

    List<Box> getBoxes(Box box1, Box box2) {
        var intersects = box1.intersects(box2);
        var intersects2 = box2.intersects(box1);
//        log.info("box1 {} {} ", box1, box1.getVolume());
//        log.info("box2 {} {} ", box2, box2.getVolume());
//        log.info("intersects {}", intersects);
//        log.info("intersects2 {}", intersects2);
        return split(box1, box2, intersects, intersects2);

    }

    @Override
    public String getAnswerPart1() {
        return "611378";
    }

    @Override
    public String getAnswerPart2() {
//        return "2758514936282235";
        return "474140";
    }
}
