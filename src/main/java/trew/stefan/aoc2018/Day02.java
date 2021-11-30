package trew.stefan.aoc2018;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.HashMap;

@Slf4j
public class Day02 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getInput();

        var doubles = 0;
        var triples = 0;

        for (var item : list) {

            var counter = new HashMap<Character, Integer>();

            for (var c : item.toCharArray()) {

                var count = counter.getOrDefault(c, 0);
                count++;
                counter.put(c, count);
            }

            if (counter.containsValue(2)) {
                doubles++;
            }
            if (counter.containsValue(3)) {
                triples++;
            }
        }


        return String.valueOf(doubles * triples);
    }

    @Override
    public String runPart2() {

        var list = getInput();
        for (var item : list) {

            for (var sub : list) {

                if (sub.equals(item)) {
                    continue;
                }

                StringBuilder same = new StringBuilder();
                var diff = 0;

                for (var i = 0; i < item.length(); i++) {
                    if (item.charAt(i) == sub.charAt(i)) {
                        same.append(item.charAt(i));
                    } else {
                        if (diff == 0) {
                            diff = 1;
                        } else {
                            diff = 2;
                            break;
                        }
                    }

                }
                if (diff == 1) {
                    return same.toString();
                }
            }
        }

        return "";
    }

    @Override
    public String getAnswerPart1() {
        return "6944";
    }

    @Override
    public String getAnswerPart2() {
        return "srijafjzloguvlntqmphenbkd";
    }
}
