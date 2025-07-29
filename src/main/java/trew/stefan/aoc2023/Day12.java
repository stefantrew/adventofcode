package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.Arrays;

@Slf4j
public class Day12 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;

//
//        var list = getStringInput("_sample");
//
//        for (var s : list) {
//            var process = process(s);
//            log.info("process: {}", process);
//            total += process;
//        }

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

    private int process(String s, boolean b, int prev) {
        var parts = s.split(" ");
        var config = Arrays.stream(parts[1].split(",")).mapToInt(Integer::parseInt).toArray();
        var springs = parts[0];
        if (b) {
            if (prev == 1 && !springs.endsWith(".")) {
                return 0;
            } else {
                if (config[0] == 1 || springs.endsWith(".")) {

                    springs = "?" + springs;
                }
                if (config[config.length - 1] == 1) {
                    springs = springs + "?";
                }
            }

        }


        var total = 0;
        var count = 0;
        for (int i = 0; i < springs.length(); i++) {
            if (springs.charAt(i) == '?') {
                count++;
            }
        }


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
            if (isValid(tempString, config)) {
                total++;
            }
        }


        return total;
    }

    @Override
    public String runPart2() {


        var total = 0L;


        var list = getStringInput("");

        for (var s : list) {
            var process = process(s, false, 0);
            var process2 = process(s, true, process);
            var sum = process;
            var springs = s.split(" ")[0];
            if (process2 != 0) {

                sum *= Math.pow(process2, 4);
            }
            log.info("process: {} {} {} => {}", s, process, process2, sum);

            total += sum;
        }

        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "";
    }

    @Override
    public String getAnswerPart2() {
        //44924528177
        return "";
    }
}
