package trew.stefan.aoc2020;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day05 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


        var list = getStringInput();
        for (var s : list) {
            var upper = 127;
            var lower = 0;

            var upper2 = 7;
            var lower2 = 0;
            for (char c : s.toCharArray()) {

                var mid = (upper - lower) / 2 + 1;
                var mid2 = (upper2 - lower2) / 2 + 1;
                if (c == 'F') {
                    upper -= mid;
                } else if (c == 'B') {

                    lower += mid;
                }
                if (c == 'L') {
                    upper2 -= mid2;
                } else if (c == 'R') {
                    lower2 += mid2;
                }
            }
            var id = upper * 8 + upper2;
            total = Math.max(id, total);
        }


        return formatResult(total);
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


        var list = getStringInput();
        var set = new HashSet<Integer>();
        for (var s : list) {
            var upper = 127;
            var lower = 0;

            var upper2 = 7;
            var lower2 = 0;
            //

            for (char c : s.toCharArray()) {

                var mid = (upper - lower) / 2 + 1;
                var mid2 = (upper2 - lower2) / 2 + 1;
                if (c == 'F') {
                    upper -= mid;
                } else if (c == 'B') {

                    lower += mid;
                }
                if (c == 'L') {
                    upper2 -= mid2;
                } else if (c == 'R') {
                    lower2 += mid2;
                }
            }
            var id = upper * 8 + upper2;
            set.add(id);
            total = Math.max(id, total);
        }

        for (int i = 10; i < total; i++) {
            if (set.contains(i + 2) && !set.contains(i + 1)) {
                return formatResult(i + 1);
            }
        }

        return formatResult("");
    }

    @Override
    public String getAnswerPart1() {
        return "951";
    }

    @Override
    public String getAnswerPart2() {
        return "653";
    }
}
