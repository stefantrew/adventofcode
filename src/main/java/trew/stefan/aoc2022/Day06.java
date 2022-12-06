package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.HashSet;


@Slf4j
public class Day06 extends AbstractAOC {

    @Override
    public String runPart1() {
        return process(4);
    }

    @Override
    public String runPart2() {
        return process(14);
    }

    private String process(int len) {
        var list = getStringInput().get(0);
        for (var i = 0; i < list.length() - 3; i++) {

            var sub = list.substring(i, i + len);
            var set = new HashSet<Character>();
            for (var c : sub.toCharArray()) {
                set.add(c);
            }

            if (set.size() == len) {
                return String.valueOf(i + len);
            }

        }

        return "";
    }


    @Override
    public String getAnswerPart1() {
        return "1816";
    }

    @Override
    public String getAnswerPart2() {
        return "2625";
    }
}
