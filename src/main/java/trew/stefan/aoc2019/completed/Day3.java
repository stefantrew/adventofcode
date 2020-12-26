package trew.stefan.aoc2019.completed;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.Point;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day3 implements Day {

    List<Point> getPoints(String[] input) {
        List<Point> result = new ArrayList<>();
        Point current = new Point(0, 0);
        for (String step : input) {
            char dir = step.charAt(0);
            int dist = Integer.parseInt(step.substring(1));
            for (int i = 0; i < dist; i++) {
                switch (dir) {
                    case 'U':
                        current.addY(-1);
                        break;
                    case 'D':
                        current.addY(1);
                        break;
                    case 'L':
                        current.addX(-1);
                        break;
                    case 'R':
                        current.addX(1);
                        break;
                }
                result.add(current.getClone());
            }

        }

        return result;
    }

    public void run() {
        List<String> lines = InputReader2019.readStrings(3, "_sample");

        List<Point> pathA = getPoints(lines.get(0).split(","));
        List<Point> pathB = getPoints(lines.get(1).split(","));
//        log.info("A {}", pathA);
//        log.info("B {}", pathB);
        Integer lowest = null;
        Integer leastSteps = null;
        for (Point temp : pathA) {
            if (pathB.contains(temp)) {
                int d = temp.getDistance();
                int a = pathA.indexOf(temp) + 1;
                int b = pathB.indexOf(temp) + 1;
                log.info("{} {}", temp, d);
                lowest = lowest == null ? d : Math.min(d, lowest);
                leastSteps = leastSteps == null ? (a+b) : Math.min(a + b, leastSteps);
            }
        }
        log.info("{}", lowest);
        log.info("{}", leastSteps);
    }
}
