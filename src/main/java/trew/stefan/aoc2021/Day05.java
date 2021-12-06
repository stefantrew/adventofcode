package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.Matrix;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day05 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getStringInput("").stream().map(this::mapper).collect(Collectors.toList());

        var max = 0;
        for (var s : list) {
            max = Math.max(max, s.x1);
            max = Math.max(max, s.y1);
            max = Math.max(max, s.x2);
            max = Math.max(max, s.y2);
        }

        var max2 = 0;
        var grid = new Matrix<>(max + 1, max + 1, Integer.class, 0);

        for (Item item : list) {

            if (item.x1 != item.x2 && item.y2 != item.y1) {
                continue;
            }

            for (int i = Math.min(item.x1, item.x2); i < Math.max(item.x1, item.x2) + 1; i++) {
                for (int y = Math.min(item.y1, item.y2); y < Math.max(item.y1, item.y2) + 1; y++) {
                    var t = grid.apply(y, i, integer -> integer + 1);
                    if (t == 2) {
                        max2++;
                    }

                }
            }
        }

        return formatResult(max2);
    }

    @ToString
    @AllArgsConstructor
    class Item {
        int x1;
        int y1;
        int x2;
        int y2;
    }

    Item mapper(String input) {

        var p = Pattern.compile("(\\d*),(\\d*) -> (\\d*),(\\d*)");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Item(m.getInt(1), m.getInt(2), m.getInt(3), m.getInt(4));
        }
        return null;
    }


    @Override
    public String runPart2() {


        var list = getStringInput("").stream().map(this::mapper).collect(Collectors.toList());



        var max = 0;
        for (var s : list) {
            max = Math.max(max, s.x1);
            max = Math.max(max, s.y1);
            max = Math.max(max, s.x2);
            max = Math.max(max, s.y2);
        }

        var max2 = 0;
        var grid = new Matrix<Integer>(max + 1, max + 1, Integer.class, 0);

        for (Item item : list) {

            if (item.x1 != item.x2 && item.y2 != item.y1) {


                var x1 = Math.min(item.x1, item.x2);
                var x2 = Math.max(item.x1, item.x2);
                var y1 = item.x1 == x1 ? item.y1 : item.y2;
                var y2 = item.x1 != x1 ? item.y1 : item.y2;
                var m = y1 < y2 ? 1 : -1;
                var y = y1;
                for (int i = x1; i < x2 + 1; i++) {

                    var t = grid.apply(y, i, integer -> integer + 1);

                    if (t == 2) {
                        max2++;
                    }
                    y += m;

                }
                continue;
            }

            for (int i = Math.min(item.x1, item.x2); i < Math.max(item.x1, item.x2) + 1; i++) {
                for (int y = Math.min(item.y1, item.y2); y < Math.max(item.y1, item.y2) + 1; y++) {
                    var t = grid.apply(y, i, integer -> integer + 1);
                    if (t == 2) {
                        max2++;
                    }

                }
            }
        }


        return formatResult(max2);
    }

    @Override
    public String getAnswerPart1() {
        return "7436";
    }

    @Override
    public String getAnswerPart2() {
        return "21104";
    }
}
