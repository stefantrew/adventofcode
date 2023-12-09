package trew.stefan;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.aoc2018.Day10;
import trew.stefan.utils.AOCMatcher;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class DayTemplate extends AbstractAOC {


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
