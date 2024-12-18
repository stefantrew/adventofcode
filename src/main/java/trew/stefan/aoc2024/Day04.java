package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class Day04 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");
        AtomicInteger total = new AtomicInteger();


        var map = new Matrix<Character>(list, Character.class, '.');

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> (char) c)
                    .toList());

        }

        var directions = new int[][]{
                {0, 1},
                {0, -1},
                {1, 0},
                {-1, 0},
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, -1}
        };

        map.find('X').forEach(result -> {
            var r = result.getRow();
            var c = result.getCol();

            for (var d : directions) {


                if (checkDirection(d, r, c, map)) {
                    total.getAndIncrement();
                }
            }

        });

        return String.valueOf(total.get());
    }

    private static boolean checkDirection(int[] d, int r, int c, Matrix<Character> map) {
        var word = "X";
        while (word.length() < 4) {

            r = r + d[0];
            c = c + d[1];
            if (r >= 0 && r < map.getHeight() && c >= 0 && c < map.getWidth()) {
                word += map.getPoint(r, c).getValue();
            } else {
                return false;
            }
        }

        return word.equals("XMAS");
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");
        AtomicInteger total = new AtomicInteger();


        var map = new Matrix<Character>(list, Character.class, '.');

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> (char) c)
                    .toList());

        }

        var directions = new int[][]{
                {1, 1},
                {1, -1},
                {-1, 1},
                {-1, -1}
        };

        map.find('A').forEach(result -> {
            var r = result.getRow();
            var c = result.getCol();

            if (r > 0 && c > 0 && r < map.getHeight() - 1 && c < map.getWidth() - 1) {
                var NW = map.getPoint(r - 1, c - 1).getValue();
                var NE = map.getPoint(r - 1, c + 1).getValue();
                var SW = map.getPoint(r + 1, c - 1).getValue();
                var SE = map.getPoint(r + 1, c + 1).getValue();

                if (NE.equals(SW)) {
                    return;
                }
                if (NW.equals(SE)) {
                    return;
                }
                if (!NE.equals('S') && !NE.equals('M')) {
                    return;
                }

                if (!SE.equals('S') && !SE.equals('M')) {
                    return;
                }

                if (!SW.equals('S') && !SW.equals('M')) {
                    return;
                }

                if (!NW.equals('S') && !NW.equals('M')) {
                    return;
                }

                total.getAndIncrement();
            }

        });

        return String.valueOf(total.get());
    }


    @Override
    public String getAnswerPart1() {
        return " ";
    }

    @Override
    public String getAnswerPart2() {
        return " ";
    }
}
