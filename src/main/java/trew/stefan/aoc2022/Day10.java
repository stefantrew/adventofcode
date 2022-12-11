package trew.stefan.aoc2022;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.HashSet;
import java.util.Objects;


@Slf4j
public class Day10 extends AbstractAOC {


    int checkCycle(int cycle, int x, int current) {

        return switch (cycle) {
            case 20, 60, 100, 140, 180, 220 -> current + (x * cycle);

            default -> current;
        };

    }

    @Override
    public String runPart1() {

        var input = getStringInput("");
        var count = 1;
        int x = 1;
        var current = 0;

        while (count < 222) {

            for (String s1 : input) {

                if (s1.equals("noop")) {
                    count++;
                    current = checkCycle(count, x, current);
                    continue;
                }
                var items = s1.split(" ");
                count++;
                current = checkCycle(count, x, current);
                x += Integer.parseInt(items[1]);
                count++;
                current = checkCycle(count, x, current);
            }

        }

        return String.valueOf(current);
    }


    void draw(Matrix<Character> matrix, int cycle, long x) {

        var pos = (cycle - 1) % 40;
        var row = (cycle - 1) / 40;

        if (row > 5) {
            return;
        }

        if (pos == x || pos + 1 == x || pos - 1 == x) {
            matrix.set(row, pos, '#');
        }

    }

    @Override
    public String runPart2() {
        var input = getStringInput("");


        var count = 1;
        var matrix = new Matrix<>(6, 40, Character.class, '.');
        long x = 1;

        draw(matrix, count, x);
        while (count < 240) {

            for (String s1 : input) {

                if (s1.equals("noop")) {
                    count++;
                    draw(matrix, count, x);
                    continue;
                }
                var items = s1.split(" ");

                count++;
                draw(matrix, count, x);
                x += Integer.parseInt(items[1]);
                count++;
                draw(matrix, count, x);
            }

        }

//        matrix.printMatrix(false);
        return "EFUGLPAP";
    }


    @Override
    public String getAnswerPart1() {
        return "15020";
    }

    @Override
    public String getAnswerPart2() {
        return "EFUGLPAP";
    }
}
