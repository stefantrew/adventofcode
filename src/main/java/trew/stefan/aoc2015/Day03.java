package trew.stefan.aoc2015;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

@Slf4j
public class Day03 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


//        var list = getStringInput().stream().map(this::mapper).toList();

        var list = getStringInput();
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

        for (var s : list) {
            log.info("{}", s);
        }


        return formatResult(total);
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
