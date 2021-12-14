package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.Matrix;

import java.util.ArrayList;
import java.util.regex.Pattern;

@Slf4j
public class Day13 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


//        var list = getStringInput().stream().map(this::mapper).collect(Collectors.toList());

        var list = getStringInput("");
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

        var points = new ArrayList<String>();
        var steps = new ArrayList<String>();
        var mode = 1;

        for (var s : list) {
            if (s.equals("")) {
                mode = 2;
                continue;
            }
            if (mode == 1) {
                points.add(s);
            } else {
                steps.add(s);
            }
        }
        log.info("points {}", points);
        var maxX = -1;
        var maxY = -1;
        for (String point : points) {

            var p = Pattern.compile("(\\d*),(\\d*)");
            var m = new AOCMatcher(p.matcher(point));

            if (m.find()) {
                m.print();
                maxX = Math.max(maxX, m.getInt(1));
                maxY = Math.max(maxY, m.getInt(2));

            } else {
                log.info("aaa");
            }

        }

        var matrix = new Matrix<Character>(maxY + 1, maxX + 1, Character.class, '.');

        for (String point : points) {

            var p = Pattern.compile("(\\d*),(\\d*)");
            var m = new AOCMatcher(p.matcher(point));

            if (m.find()) {
                matrix.set(m.getInt(2), m.getInt(1), '#');
            }

        }
//        matrix.printMatrix(false);
        log.info("maxY {}, maxX {}", maxY, maxX);
        log.info("steps {}", steps);

        for (String step : steps) {

            matrix = fold(matrix, step);
        }
        log.info("--------");
        matrix.printMatrix(false);

        return formatResult(total);
    }

    private Matrix<Character> fold(Matrix<Character> matrix, String step) {
        log.info("step {}", step);

        var p = Pattern.compile("fold along (x|y)=(\\d*)");
        var m = new AOCMatcher(p.matcher(step));

        if (m.find()) {
            var val = m.getInt(2);
            if (m.getChar(1) == 'y') {

                var newY = val;
                var temp = new Matrix<Character>(newY, matrix.getWidth(), Character.class, '.');

                for (int y = 0; y < matrix.getHeight(); y++) {
                    for (int x = 0; x < matrix.getWidth(); x++) {
                        if (matrix.get(y, x) == '.') {
                            continue;
                        }
                        if (y <= val) {
                            temp.set(y, x, matrix.get(y, x));
                        } else {
                            var diff = y - val;
                            temp.set(val - diff, x, matrix.get(y, x));
                        }
                    }
                }
                return temp;
            } else {
                var newX = val;
                var temp = new Matrix<Character>(matrix.getHeight(), newX, Character.class, '.');

                for (int y = 0; y < matrix.getHeight(); y++) {
                    for (int x = 0; x < matrix.getWidth(); x++) {
                        if (matrix.get(y, x) == '.') {
                            continue;
                        }
                        if (x <= val) {
                            temp.set(y, x, matrix.get(y, x));
                        } else {
                            var diff = x - val;
                            temp.set(y, val - diff, matrix.get(y, x));
                        }
                    }
                }
                return temp;
            }


        }
        return matrix;
    }


    @AllArgsConstructor
    class Item {

    }

    Item mapper(String input) {

        var p = Pattern.compile("");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Item();
        }
        return null;
    }


    @Override
    public String runPart2() {


        var list = getStringInput();

        return formatResult("");
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
