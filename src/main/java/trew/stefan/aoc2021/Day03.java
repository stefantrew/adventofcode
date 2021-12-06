package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day03 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getStringInput();

        var gamma = "";
        var epsilon = "";
        for (int i = 0; i < 12; i++) {

            var sum = 0;
            for (var s : list) {
                if (s.charAt(i) == '1') {
                    sum++;
                }

            }

            if (sum > list.size() / 2) {
                gamma += "1";
                epsilon += "0";
            } else {
                gamma += "0";
                epsilon += "1";
            }

        }


        return formatResult(Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2));
    }


    @Override
    public String runPart2() {


        var list = getStringInput("");


        String o2 = filter(list, true);
        String co2 = filter(list, false);
        return formatResult(Integer.parseInt(o2, 2) * Integer.parseInt(co2, 2));
    }

    private String filter(java.util.List<String> list, boolean common) {
        for (int i = 0; i < list.get(0).length(); i++) {

            var c = getCommon(list, i, common);

            int finalI = i;
            list = list.stream().filter(s -> s.charAt(finalI) == c).collect(Collectors.toList());

            if (list.size() == 1) {
                return list.get(0);
            }
        }
        return "";
    }

    private char getCommon(java.util.List<String> list, int i, boolean common) {
        var sum = 0;
        var sum2 = 0;
        for (var s : list) {
            if (s.charAt(i) == '1') {
                sum++;
            } else {
                sum2++;
            }

        }

        if (sum >= sum2) {
            return common ? '1' : '0';

        } else {
            return common ? '0' : '1';
        }
    }

    @Override
    public String getAnswerPart1() {
        return "3912944";
    }

    @Override
    public String getAnswerPart2() {
        return "4996233";
    }
}
