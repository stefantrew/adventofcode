package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.List;

@Slf4j
public class Day21 extends AbstractAOC {

    class Node {

        int row;
        int col;
    }

    @Override
    public String runPart1() {

        var total = 0;
        var list = getStringInput("");

        var map = new char[list.size()][list.get(0).length()];
        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);

            for (int j = 0; j < s.length(); j++) {
                map[i][j] = s.charAt(j);
            }

        }

        var count = 0;
        while (count++ < 64) {
            total = takeStep(map);
        }

        return formatResult(total);
    }

    private int takeStep(char[][] map) {
        for (int row = 0; row < map.length; row++) {
            var s = map[row];
            for (int col = 0; col < s.length; col++) {
                var c = s[col];
                if (c == '#' || c == '.' || c == 'O') {
                    continue;
                }

                if (row > 0 && map[row - 1][col] == '.') {
                    map[row - 1][col] = 'O';
                }
                if (row < map.length - 1 && map[row + 1][col] == '.') {
                    map[row + 1][col] = 'O';
                }
                if (col > 0 && map[row][col - 1] == '.') {
                    map[row][col - 1] = 'O';
                }
                if (col < s.length - 1 && map[row][col + 1] == '.') {
                    map[row][col + 1] = 'O';
                }
                map[row][col] = '.';
            }
        }

        var total = 0;
        for (int row = 0; row < map.length; row++) {
            var s = map[row];
            for (int col = 0; col < s.length; col++) {
                var c = s[col];
                if (c == 'O') {
                    map[row][col] = 'S';
                    total++;
                }
            }
        }

        return total;
    }

    @Override
    public String runPart2() {

        var total = 0;
        var list = getStringInput("");

        var n = 15;
        var mid = n / 2;
        var height = list.size();
        var map = new char[height * n][height * n];
        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);

            for (int j = 0; j < s.length(); j++) {
                var c = s.charAt(j);
                for (int k = 0; k < n; k++) {

                    for (int l = 0; l < n; l++) {

                        var c2 = c;
                        if (c == 'S') {
                            c2 = '.';
                        }
                        if (c == 'S' && (k == mid && l == mid)) {
                            c2 = 'S';
                        }
                        map[k * height + i][l * height + j] = c2;

                    }
                }
            }

            log.info("{}", s);
        }


//
        var count = 0;
        var old = 0;
        var old2 = 0;
        while (count++ < 1000) {
            total = takeStep(map);
            if (count % list.size() == 65) {
                var t = count / list.size() ;
                var a = 28838 / 6;
                var b = 29009 / 2;
                var prediction = a * t * t * t   +  b * t * t ;
                var grad = a * 2 * t + b;
//total - old, total - old2 - old
                var diff = total - prediction;
                var i = t > 0 ?  diff / t : 0;
                log.info("{} ({}) {}  => {} delta {}", count,t, total, prediction, i);
//                log.info("{},{} {} {} {}", count, total,total - old, total - old2 - old,grad);
                old2 = total - old;
                old = total;
            }
        }

//        for (char[] chars : map) {
//            log.info("{}", new String(chars));
//        }
        return formatResult(total);
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
