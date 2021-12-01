package trew.stefan.aoc2020;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

@Slf4j
public class Day01 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getIntegerInput();

        for (Integer integer : list) {
            for (Integer second : list) {

                if (integer + second == 2020) {
                    return String.valueOf(integer * second);
                }
            }

        }

        return "";
    }

    @Override
    public String runPart2() {


        var list = getIntegerInput();

        for (Integer integer : list) {
            for (Integer second : list) {
                for (Integer third : list) {

                    if (integer + second + third == 2020) {
                        return String.valueOf(integer * second * third);
                    }
                }
            }

        }


        return "";
    }

    @Override
    public String getAnswerPart1() {
        return "898299";
    }

    @Override
    public String getAnswerPart2() {
        return "143933922";
    }
}
