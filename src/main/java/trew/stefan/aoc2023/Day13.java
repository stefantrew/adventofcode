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

    private Integer isReflection2(String s, int i) {
        int a = i;
        int b = i + 1;
        int error = 0;

        while (a >= 0 && b < s.length()) {
            if (s.charAt(a) != s.charAt(b)) {
                error++;
            }


            a--;
            b++;
        }


        return error;
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


    private Integer runList(List<String> list, boolean part2) {
        var i = checkList(list, part2);
        if (i == null) {
            var list2 = transpose(list);
            var result = checkList(list2, part2);
            i = result * 100;
        }
        return i;
    }

    private Integer checkList(List<String> list, boolean part2) {
        var length = list.get(0).length();

        for (int i = 0; i < length - 1; i++) {
            var flag = part2 ? isIndexReflective2(list, i) : isIndexReflective(list, i);
            if (flag) {
                return i + 1;
            }
        }

        return null;
    }

    private boolean isIndexReflective(List<String> list, int i) {

        for (var s : list) {
            var reflection = isReflection(s, i);
            if (!reflection) {
                return false;
            }
        }
        return true;
    }

    private boolean isIndexReflective2(List<String> list, int i) {

        var error = 0;
        for (var s : list) {
            error += isReflection2(s, i);
            if (error > 1) {
                return false;
            }
        }
        return error == 1;
    }

    @Override
    public String runPart1() {

        var total = 0;

        var list = getStringInput("");


        var subSet = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            if (s.isEmpty()) {
                total += runList(subSet, false);
                subSet.clear();
            } else {
                subSet.add(s);
            }

        }
        if (!subSet.isEmpty()) {

            total += runList(subSet, false);
        }

        return formatResult(total);
    }


    @Override
    public String runPart2() {

        var total = 0;

        var list = getStringInput("");

        var subSet = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            var s = list.get(i);
            if (s.isEmpty()) {
                total += runList(subSet, true);
                subSet.clear();
            } else {
                subSet.add(s);
            }

        }
        if (!subSet.isEmpty()) {

            total += runList(subSet, true);
        }

        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "27742";
    }

    @Override
    public String getAnswerPart2() {
        return "32728";
    }
}
