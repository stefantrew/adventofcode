package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.Arrays;

@Slf4j
public class Day12 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";


        var list = getStringInput("");
        var max = 0;

        for (var s : list) {
            var process = process(s);
            log.info("process: {}", process);
            total += process;
        }

        return formatResult(total);
    }

    boolean isValid(String springs, int[] config) {


        var parts = Arrays.stream(springs.split("\\.")).filter(s -> s.length() > 0).toList();
        if (parts.size() != config.length) {
            return false;
        }

        for (int i = 0; i < parts.size(); i++) {
            var part = parts.get(i);
            if (part.length() != config[i]) {
                return false;
            }
        }

        return true;
    }

    private int process(String s) {
        var parts = s.split(" ");
        var springs = parts[0];
        var config = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();

//        log.info("springs: {}, config: {}", springs, config);

        var total = 0;
        var count = 0;
        for (int i = 0; i < springs.length(); i++) {
            if (springs.charAt(i) == '?') {
                count++;
            }
        }

//        log.info("count: {}", count);

        for (int i = 0; i < Math.pow(2, count); i++) {
            var binaryString = Integer.toBinaryString(i);
            while (binaryString.length() < count) {
                binaryString = "0" + binaryString;
            }

            var tempString = springs;
            for (int j = 0; j < binaryString.length(); j++) {
                var c = binaryString.charAt(j) == '0' ? '#' : '.';
                tempString = tempString.replaceFirst("\\?", c + "");
            }
//            log.info("tempString: {}", tempString);
            if (isValid(tempString, config)) {
                total++;
            }
//            log.info("i: {} {}", binaryString, tempString);
        }


        return total;
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
