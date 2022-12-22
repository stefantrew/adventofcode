package trew.stefan.aoc2022;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class Day20 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "_sample";


        var tests = new String[]{
                "2, 1, -3, 3, -2, 0, 4",
                "1, -3, 2, 3, -2, 0, 4",
                "1, 2, 3, -2, -3, 0, 4",
                "1, 2, -2, -3, 0, 3, 4",
                "1, 2, -3, 0, 3, 4, -2",
                "1, 2, -3, 0, 3, 4, -2",
                "1, 2, -3, 4, 0, 3, -2"
        };

        var original = getLongInput("_sample");
        var list = new ArrayList<Long>();
        list.addAll(original);
        log.info("{}", original);
        for (var number : original) {

            var curr = list.indexOf(number);
//            log.info("{} {}", number, list);
            int newIndex = (int) (curr + number );
            newIndex--;
            while (newIndex < 0) {
                newIndex += list.size();
            }


//            if (number < 0) {
//            }
//            if (newIndex % list.size() < curr) {
//                newIndex++;
//            }
            newIndex %= list.size();
//            log.info("Moving {} from {} to {}", number, curr, newIndex);
            list.remove(curr);
            list.add(newIndex, number);

//            log.info("result {}", list);
//            log.info("result [{}]", tests[original.indexOf(number)]);
//            log.info("");
        }
        log.info("{}", original);
        log.info("{}", list);
        // -6964
        // -2735
        log.info("[1, 2, -3, 4, 0, 3, -2]");

        var ind = list.indexOf(0L);
        total += list.get((ind + 1000) % list.size());
        log.info("{}", list.get((ind + 1000) % list.size()));
        total += list.get((ind + 2000) % list.size());
        log.info("{}", list.get((ind + 2000) % list.size()));
        total += list.get((ind + 3000) % list.size());
        log.info("{}", list.get((ind + 3000) % list.size()));
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
