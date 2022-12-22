package trew.stefan.aoc2022;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
public class Day21 extends AbstractAOC {


    @Override
    public String runPart1() {


        var list = getStringInput("").stream().map(this::mapper).toList();

        var map = new HashMap<String, Monkey>();


        for (var s : list) {
            map.put(s.id, s);
        }
        var root = map.get("root");
        while (root.number == null) {
            for (var s : list) {
                if (s.number == null) {
                    var m1 = map.get(s.partA).number;
                    var m2 = map.get(s.partB).number;

                    if (m1 == null || m2 == null) {
                        continue;
                    }

                    s.number = switch (s.operator) {
                        case "+" -> m1 + m2;
                        case "*" -> m1 * m2;
                        case "/" -> m1 / m2;
                        case "-" -> m1 - m2;
                        default -> throw new IllegalArgumentException(s.operator);
                    };
                }
            }

        }


        return formatResult(root.number);
    }

    @ToString
    class Monkey {
        String id;
        Long number;
        String partA;
        String partB;

        String operator;
    }

    Monkey mapper(String input) {

        var p = Pattern.compile("(\\w{4}): (\\w{4}) (.) (\\w{4})");
        var p2 = Pattern.compile("(\\w{4}): (\\d*)");
        var m = new AOCMatcher(p.matcher(input));
        var m2 = new AOCMatcher(p2.matcher(input));

        if (m.find()) {
            m.print();
            var monkey = new Monkey();
            monkey.id = m.getString(1);
            monkey.partA = m.getString(2);
            monkey.operator = m.getString(3);
            monkey.partB = m.getString(4);
            return monkey;
        }

        if (m2.find()) {
            var monkey = new Monkey();
            monkey.id = m2.getString(1);
            monkey.number = m2.getLong(2);
            return monkey;
        }
        return null;
    }


    @Override
    public String runPart2() {


        var list = getStringInput("").stream().map(this::mapper).toList();

        var map = new HashMap<String, Monkey>();


        for (var s : list) {
            map.put(s.id, s);
        }
        var root = map.get("root");
        root.operator = "=";
        var input = 3305669217830L;
        while (true) {

            if (doRun(input, list, map)) {
                return formatResult(input);
            }
            input++;
        }


    }

    private boolean doRun(long input, List<Monkey> list, HashMap<String, Monkey> map) {
        for (var s : list) {
            if (s.operator != null) {
                s.number = null;
            }
        }

        var humn = map.get("humn");
        humn.number = input;

        while (true) {
            for (var s : list) {
                if (s.number == null) {
                    var m1 = map.get(s.partA).number;
                    var m2 = map.get(s.partB).number;

                    if (m1 == null || m2 == null) {
                        continue;
                    }

                    if (Objects.equals(s.operator, "=")) {
                        return m1.equals(m2);
                    }

                    s.number = switch (s.operator) {
                        case "+" -> m1 + m2;
                        case "*" -> m1 * m2;
                        case "/" -> m1 / m2;
                        case "-" -> m1 - m2;
                        default -> throw new IllegalArgumentException(s.operator);
                    };
                }
            }

        }
    }

    @Override
    public String getAnswerPart1() {
        return "51928383302238";
    }

    @Override
    public String getAnswerPart2() {
        return "3305669217840";
    }
}
