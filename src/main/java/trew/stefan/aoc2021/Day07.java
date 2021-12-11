package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.regex.Pattern;

@Slf4j
public class Day07 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


        var list = getIntegerSplit(",", "");
//        var list = getDoubleInput();

        var max = 0;
        for (var s : list) {
            max = Math.max(max, s);
        }
        var min = 1000000000;

        for (int i = 0; i < max; i++) {

            var cost = 0;
            for (Integer integer : list) {
                cost += Math.abs(integer - i);
            }
            min = Math.min(min, cost);

        }


        return formatResult(min);
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


        var total = 0;
        var result = "";


        var list = getIntegerSplit(",", "");
//        var list = getDoubleInput();

        var max = 0;
        for (var s : list) {
            max = Math.max(max, s);
        }
        var min = 1000000000;

        for (int i = 0; i < max; i++) {

            var cost = 0;
            for (Integer integer : list) {
                var abs = Math.abs(integer - i);
                var x = 1;

                while (abs-- > 0) {
                    cost += x;

                    x++;
                }
            }
            min = Math.min(min, cost);

        }


        return formatResult(min);
    }

    @Override
    public String getAnswerPart1() {
        return "337488";
    }

    @Override
    public String getAnswerPart2() {
        return "89647695";
    }
}
