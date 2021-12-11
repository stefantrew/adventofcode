package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day10 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;


        var list = getStringInput("");
        for (var s : list) {

            total += extracted(s);

        }


        return formatResult(total);
    }

    private int extracted(String s) {

        var set = new HashSet<String>();
        set.add("{");
        set.add("<");
        set.add("(");
        set.add("[");

        var stk = new Stack<String>();

        for (char c : s.toCharArray()) {
            var x = String.valueOf(c);

            if (set.contains(x)) {
                stk.push(x);
            } else {
                var value = 0;
                var opp = "";
                switch (x) {
                    case "}":
                        opp = "{";
                        value = 1197;
                        break;
                    case ">":
                        opp = "<";
                        value = 25137;
                        break;
                    case ")":
                        opp = "(";
                        value = 3;
                        break;
                    case "]":
                        value = 57;
                        opp = "[";
                        break;
                }

                var next = stk.pop();
                if (!next.equals(opp)) {

                    return value;
                }
            }
        }
        return 0;
    }

    private Long extracted2(String s) {


        var set = new HashSet<String>();
        set.add("{");
        set.add("<");
        set.add("(");
        set.add("[");
        var stk = new Stack<String>();

        for (char c : s.toCharArray()) {
            var x = String.valueOf(c);

            if (set.contains(x)) {
                stk.push(x);
            } else {
                String opp = getOpposite(x);

                var next = stk.pop();
                if (!next.equals(opp)) {

                    return 0L;
                }
            }
        }
        var sb = "";
        var score = 0L;
        while (!stk.empty()) {
            score *= 5;
            var opposite = getOpposite(stk.pop());
            sb += opposite;
            switch (opposite) {
                case ")":
                    score += 1;
                    break;
                case "]":
                    score += 2;
                    break;
                case "}":
                    score += 3;
                    break;
                case ">":
                    score += 4;
                    break;
            }
        }

        return score;
    }

    private String getOpposite(String x) {
        var opp = "";
        switch (x) {
            case "{":
                opp = "}";
                break;
            case "<":
                opp = ">";
                break;
            case "(":
                opp = ")";
                break;
            case "[":
                opp = "]";
                break;
            case "}":
                opp = "{";
                break;
            case ">":
                opp = "<";
                break;
            case ")":
                opp = "(";
                break;
            case "]":
                opp = "[";
                break;
        }
        return opp;
    }

    @Override
    public String runPart2() {


        var totals = new ArrayList<Long>();

        var list = getStringInput("");
        for (var s : list) {

            var score = extracted2(s);
            if (score > 0) {
                totals.add(score);
            }
        }


        return formatResult(totals.stream().sorted().collect(Collectors.toList()).get(totals.size() / 2));
    }

    @Override
    public String getAnswerPart1() {
        return "339411";
    }

    @Override
    public String getAnswerPart2() {
        return "2289754624";
    }
}
