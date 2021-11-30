package trew.stefan.aoc2018;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.HashSet;
import java.util.stream.Collectors;

@Slf4j
public class Day01 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getInput().stream().map(Integer::parseInt)
                .reduce(0, Integer::sum);

        return list.toString();
    }

    @Override
    public String runPart2() {
        var list = getInput().stream().map(Integer::parseInt).collect(Collectors.toList());

        var visited = new HashSet<Integer>();

        var current = Integer.valueOf(0);
        var index = 0;

        while (true) {

            current += list.get(index);

            if (visited.contains(current)) {
                break;
            }
            visited.add(current);
            index++;
            index = index % list.size();
        }

        return current.toString();
    }

    @Override
    public String getAnswerPart1() {
        return "576";
    }

    @Override
    public String getAnswerPart2() {
        return "77674";
    }
}
