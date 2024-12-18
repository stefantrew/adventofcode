package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
public class Day05 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");

        var rules = new ArrayList<String>();
        var total = 0;

        for (var i = 0; i < list.size(); i++) {

            var s = list.get(i);
            if (s.contains("|")) {
                rules.add(s);
            }

            if (s.contains(",")) {
                var pages = Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
                List<Integer> pageList = new ArrayList();
                for (var page : pages) {
                    pageList.add(page);
                }

                boolean isValid = isValidOrder(pageList, rules);
                if (isValid) {
                    total += pages[pages.length / 2];
                }
            }


        }


        return String.valueOf(total);
    }

    private boolean isValidOrder(List<Integer> list, ArrayList<String> rules) {


        for (var rule : rules) {
            var left = Integer.parseInt(rule.split("\\|")[0]);
            var right = Integer.parseInt(rule.split("\\|")[1]);
            var leftIndex = list.indexOf(left);
            var rightIndex = list.indexOf(right);
            if (leftIndex == -1 || rightIndex == -1) {
                continue;
            }

            if (leftIndex > rightIndex) {
                return false;
            }
        }

        return true;
    }

    private void fixOrder(List<Integer> list, ArrayList<String> rules) {


        for (var rule : rules) {
            var left = Integer.parseInt(rule.split("\\|")[0]);
            var right = Integer.parseInt(rule.split("\\|")[1]);
            var leftIndex = list.indexOf(left);
            var rightIndex = list.indexOf(right);
            if (leftIndex == -1 || rightIndex == -1) {
                continue;
            }

            if (leftIndex > rightIndex) {
                list.set(leftIndex, right);
                list.set(rightIndex, left);
            }
        }

    }


    @Override
    public String runPart2() {

        var list = getStringInput("");

        var rules = new ArrayList<String>();
        var total = 0;

        for (var i = 0; i < list.size(); i++) {

            var s = list.get(i);
            if (s.contains("|")) {
                rules.add(s);
            }

            if (s.contains(",")) {
                var pages = Arrays.stream(s.split(",")).mapToInt(Integer::parseInt).toArray();
                List<Integer> pageList = new ArrayList();
                for (var page : pages) {
                    pageList.add(page);
                }

                boolean isValid = isValidOrder(pageList, rules);
                if (!isValid) {

                    while (!isValidOrder(pageList, rules)) {

                        fixOrder(pageList, rules);
                    }
                    log.info("{}", pageList);
                    total += pageList.get(pageList.size() / 2);
                }
            }


        }


        return String.valueOf(total);
    }


    @Override
    public String getAnswerPart1() {
        return " ";
    }

    @Override
    public String getAnswerPart2() {
        return " ";
    }
}
