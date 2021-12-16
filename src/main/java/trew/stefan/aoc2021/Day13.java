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

    class InputData {
        ArrayList<String> points = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();
        Matrix<Character> matrix;
    }

    @Override
    public String runPart1() {
        InputData data = getInputData();
        return formatResult(fold(data.matrix, data.steps.get(0)).count(character -> character == '#'));
    }

    @Override
    public String runPart2() {


        InputData data = getInputData();

        for (String step : data.steps) {

            data.matrix = fold(data.matrix, step);
        }
        data.matrix.printMatrix(false);

        return formatResult("PFKLKCFP");
    }

    private InputData getInputData() {
        var list = getStringInput("");
        var data = new InputData();

        var mode = 1;

        for (var s : list) {
            if (s.equals("")) {
                mode = 2;
                continue;
            }
            if (mode == 1) {
                data.points.add(s);
            } else {
                data.steps.add(s);
            }
        }
        var maxX = -1;
        var maxY = -1;
        for (String point : data.points) {

            var p = Pattern.compile("(\\d*),(\\d*)");
            var m = new AOCMatcher(p.matcher(point));

            if (m.find()) {
                m.print();
                maxX = Math.max(maxX, m.getInt(1));
                maxY = Math.max(maxY, m.getInt(2));

            }

        }

        data.matrix = new Matrix<Character>(maxY + 1, maxX + 1, Character.class, '.');

        for (String point : data.points) {

            var p = Pattern.compile("(\\d*),(\\d*)");
            var m = new AOCMatcher(p.matcher(point));

            if (m.find()) {
                data.matrix.set(m.getInt(2), m.getInt(1), '#');
            }

        }
        return data;
    }

    private Matrix<Character> fold(Matrix<Character> matrix, String step) {

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


    @Override
    public String getAnswerPart1() {
        return "661";
    }

    @Override
    public String getAnswerPart2() {
        return "PFKLKCFP";
    }
}
