package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Day14 extends AbstractAOC {

    class Robot {
        int x;
        int y;
        int dx;
        int dy;
        int id;
    }

    @Override
    public String runPart1() {

        var isSample = false;

        List<Robot> list = getStringInput(isSample ? "_sample" : "").stream().map(this::mapper).toList();

        var width = isSample ? 11 : 101;
        var height = isSample ? 7 : 103;


        for (var i = 0; i < 100; i++) {
            moveRobot(list, width, height);
        }

        var midx = width / 2;
        var midy = height / 2;
        var upperLeft = 0;
        var upperRight = 0;
        var lowerLeft = 0;
        var lowerRight = 0;
        for (var robot : list) {
            if (robot.x < midx && robot.y < midy) {
                upperLeft++;
            } else if (robot.x > midx && robot.y < midy) {
                upperRight++;
            } else if (robot.x < midx && robot.y > midy) {
                lowerLeft++;
            } else if (robot.x > midx && robot.y > midy) {
                lowerRight++;
            }


        }

        return formatResult(upperLeft * upperRight * lowerLeft * lowerRight);
    }

    private void moveRobot(List<Robot> list, int width, int height) {
        for (var s : list) {
            s.x = (s.x + s.dx + width) % width;
            s.y = (s.y + s.dy + height) % height;
        }
    }

    private static void print(List<Robot> list, int width, int height) {
        var matrix = new Matrix<>(height, width, String.class, ".");

        for (var s : list) {
            var current = matrix.get(s.y, s.x);
            if (Objects.equals(current, ".")) {
                matrix.set(s.y, s.x, "1");
            } else {
                var next = Integer.parseInt(current) + 1;
                matrix.set(s.y, s.x, next + "");
            }
        }

        matrix.printMatrix(false);


    }

    private Robot mapper(String s) {
        var reg = "p=(.*),(.*) v=(.*),(.*)";
        var pattern = java.util.regex.Pattern.compile(reg);
        var matcher = pattern.matcher(s);
        if (matcher.find()) {
            var robot = new Robot();
            robot.x = Integer.parseInt(matcher.group(1));
            robot.y = Integer.parseInt(matcher.group(2));
            robot.dx = Integer.parseInt(matcher.group(3));
            robot.dy = Integer.parseInt(matcher.group(4));
            return robot;
        }
        return null;
    }

    @Override
    public String runPart2() {


        var isSample = false;
        var count = 0;

        List<Robot> list = getStringInput(isSample ? "_sample" : "").stream().map(this::mapper).toList();

        var width = isSample ? 11 : 101;
        var height = isSample ? 7 : 103;
        for (var i = 0; i < list.size(); i++) {
            list.get(i).id = i;
        }


        print(list, width, height);
        for (var i = 0; i < 10000; i++) {
            moveRobot(list, width, height);
            var xPots = new HashMap<Integer, Integer>();
            var yPots = new HashMap<Integer, Integer>();

            for (var robot : list) {
                xPots.put(robot.x, xPots.getOrDefault(robot.x, 0) + 1);
                yPots.put(robot.y, yPots.getOrDefault(robot.y, 0) + 1);
            }

            var x = 0;
            var y = 0;

            for (var integerIntegerEntry : xPots.entrySet()) {
                if (integerIntegerEntry.getValue() >= 30) {
                    x++;
                }
            }
            for (var integerIntegerEntry : yPots.entrySet()) {
                if (integerIntegerEntry.getValue() >= 30) {
                    y++;
                }
            }
            if (x > 1 && y > 1) {
                print(list, width, height);
                return formatResult(i);
            }
        }


        return formatResult(count);
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
