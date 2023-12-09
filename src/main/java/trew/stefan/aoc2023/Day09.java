package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

@Slf4j
public class Day09 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;
        var list = getStringInput("");

        for (var s : list) {
            var seq = Arrays.stream(s.split(" ")).map(Integer::parseInt).toList();
            total += compute(process(seq));

        }


        return formatResult(total);
    }

    private Stack<List<Integer>> process(List<Integer> seq) {

        var lists = new Stack<List<Integer>>();
        lists.add(seq);

        while (true) {

            var onlyZeroes = true;
            var delta = new ArrayList<Integer>();
            for (int i = 0; i < seq.size() - 1; i++) {
                var diff = seq.get(i + 1) - seq.get(i);
                delta.add(diff);
                if (diff != 0) {
                    onlyZeroes = false;
                }
            }

            if (onlyZeroes) {
                break;
            }
            seq = delta;
            lists.add(delta);
        }

        return lists;

    }

    private int compute(Stack<List<Integer>> lists) {
        var total = 0;
        while (!lists.isEmpty()) {

            var first = lists.pop();
            var value = first.get(first.size() - 1);
            total += value;
        }

        return total;
    }

    private int compute2(Stack<List<Integer>> lists) {
        var last = 0;
        while (!lists.isEmpty()) {
            var first = lists.pop();
            var value = first.get(0);
            last = value - last;
        }

        return last;
    }

    @Override
    public String runPart2() {


        var total = 0;
        var list = getStringInput("");

        for (var s : list) {
            var seq = Arrays.stream(s.split(" ")).map(Integer::parseInt).toList();
            total += compute2(process(seq));

        }

        return formatResult(total);
    }

    @Override
    public String getAnswerPart1() {
        return "1980437560";
    }

    @Override
    public String getAnswerPart2() {
        return "977";
    }
}
