package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;


@Slf4j
public class Day05 extends AbstractAOC {


    @Override
    public String runPart1() {

        return run(true);
    }

    private String run(boolean moveSingle) {
        var result = "";

        var p = Pattern.compile("move (\\d*) from (\\d*) to (\\d*)");
        var str = getStringInput("");
        var stacks = new ArrayList<Stack<Character>>();
        var mode = "pots";
        for (var s : str) {
            if (s.isBlank()) {
                mode = "moves";
                continue;
            }
            if (mode.equals("pots")) {
                var e = new Stack<Character>();
                for (var c : s.toCharArray()) {
                    e.add(c);
                }
                Collections.reverse(e);
                stacks.add(e);

            } else {
                var rec = new AOCMatcher(p.matcher(s));
                rec.find();

                var qty = rec.getInt(1);

                var start = stacks.get(rec.getInt(2) - 1);
                var des = stacks.get(rec.getInt(3) - 1);

                if (moveSingle) {

                    while (qty-- > 0) {
                        des.add(start.pop());
                    }

                } else {
                    var temp = new ArrayList<Character>();
                    while (qty-- > 0) {
                        temp.add(start.pop());
                    }
                    Collections.reverse(temp);
                    des.addAll(temp);
                }
            }

        }

        result = stacks.stream().collect(StringBuilder::new, (sb, stack) -> sb.append(stack.pop()), StringBuilder::append).toString();

        return result;
    }

    @Override
    public String runPart2() {
        return run(false);
    }

    @Override
    public String getAnswerPart1() {
        return "VQZNJMWTR";
    }

    @Override
    public String getAnswerPart2() {
        return "NLCDCLVMQ";
    }
}
