package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day13 extends AbstractAOC {


    private boolean isReflection(String s, int i) {
        int a = i;
        int b = i + 1;


        while (a >= 0 && b < s.length()) {
            if (s.charAt(a) != s.charAt(b)) {
                return false;
            }


            a--;
            b++;
        }


        return true;
    }

    private List<String> transpose(List<String> input) {

        var length = input.get(0).length();

        var result = new StringBuilder[length];

        for (int i = 0; i < length; i++) {
            result[i] = new StringBuilder();
        }

        for (var s : input) {
            for (int i = 0; i < length; i++) {
                result[i].append(s.charAt(i));
            }
        }

        var output = new ArrayList<String>();

        for (var s : result) {
            output.add(s.toString());
        }
        return output;

    }

    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");


        var subSet = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            if (s.isEmpty()) {
                total += runList(subSet);
                subSet.clear();
            } else {
                subSet.add(s);
            }

        }
        if (!subSet.isEmpty()) {

            total += runList(subSet);
        }

        return formatResult(total);
    }

    private Integer runList(List<String> list) {
        var i = checkList(list);
        if (i == null) {
            var list2 = transpose(list);
            var result = checkList(list2);
            if (result == null) {
                log.info("XXXXXXXXXXXXXXXXXXXXX");
                for (String s : list) {
                    log.info(s);

                }
                log.info("=====================");
                for (String s : list2) {
                    log.info(s);

                }
                log.info("XXXXXXXXXXXXXXXXXXXXX");

                return 0;
            }
            i = result * 100;
        }
//        log.info("i: {}", i);
        return i;
    }

    private Integer checkList(List<String> list) {
        var length = list.get(0).length();

        for (int i = 0; i < length - 1; i++) {
            var flag = isIndexReflective(list, i);
            if (flag) {
                return i + 1;
            }
        }

        return null;
    }

    private boolean isIndexReflective(List<String> list, int i) {
        log.info("");
        for (var s : list) {
            var reflection = isReflection(s, i);
            log.info("{} {} {}", i, s, reflection);
            if (!reflection) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String runPart2() {


        var list = getStringInput();

        return formatResult("");
    }

    @Override
    public String getAnswerPart1() {
        //27742
        return "";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
