package trew.stefan.aoc2015;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;


@Slf4j
public class Day01 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = getStringInput().parallelStream()
                .flatMapToInt(String::chars)
                .reduce(0, (accumulator, next) -> accumulator + (next == 40 ? 1 : -1));

        return formatResult(total);
    }

    @Override
    public String runPart2() {


        var total = 0;
        var input = getStringInput().get(0);

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 40) {
                total++;
            } else {
                total--;
            }
            if (total == -1) {
                return formatResult(i + 1);
            }
        }

        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "74";
    }

    @Override
    public String getAnswerPart2() {
        return "1795";
    }
}
