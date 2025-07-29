package trew.stefan.aoc2015;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
public class Day02 extends AbstractAOC {


    @Override
    public String runPart1() {


        var total = getStringInput("")
                .parallelStream()
                .map(value -> {
                    var sides = Arrays.stream(value.split("x")).mapToInt(Integer::parseInt)
                            .toArray();
                    var h = sides[0];
                    var w = sides[1];
                    var l = sides[2];

                    var min = IntStream.of(h * w, h * l, w * l).min().orElse(0);
                    var area = IntStream.of(h * w, h * l, w * l).sum();
                    return 2 * area + min;

                })
                .reduce(0, Integer::sum);


        return formatResult(total);
    }

    @Override
    public String runPart2() {


        var total = getStringInput("")
                .parallelStream()
                .map(value -> {
                    var sides = Arrays.stream(value.split("x")).mapToInt(Integer::parseInt)
                            .sorted()
                            .limit(2)
                            .sum();
                    var volume = Arrays.stream(value.split("x")).mapToInt(Integer::parseInt)
                            .reduce(1, (a, b) -> a * b);


                    return 2 * sides + volume;

                })
                .reduce(0, Integer::sum);


        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "1606483";
    }

    @Override
    public String getAnswerPart2() {
        return "3842356";
    }
}
