package trew.stefan.aoc2021;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.stream.Collectors;

@Slf4j
public class Day01 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getIntegerInput();

        int count = 0;
        var current = 0;
        for (var item : list) {
            if (current == 0) {
                current = item;
                continue;
            }

            if (current < item) {
                count++;
            }
            current = item;

        }

        return String.valueOf(count);
    }

    @Override
    public String runPart2() {


        var list = getIntegerInput();

        int count = 0;
        var current = 0;
        var current1 = 0;
        var current2 = 0;
        var prev = 0;
        for (var item : list) {
            if (current2 == 0) {
                current2 = current1;
                current1 = current;
                current = item;
                continue;
            }

            var sum = current + current1 + current2;

            if (prev < sum) {
                count++;
            }
            current2 = current1;
            current1 = current;
            current = item;
            prev = sum;
        }

        return String.valueOf(count);
    }

    @Override
    public String getAnswerPart1() {
        return "1688";
    }

    @Override
    public String getAnswerPart2() {
        return "1728";
    }
}
