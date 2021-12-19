package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class Day18 extends AbstractAOC {


    private Map<String, String> cache2 = new HashMap<>();
    int hits2 = 0;


    @Override
    public String runPart1() {

        var total = 0;


        var list = getStringInput("");


//        test();

        var s = doAdd(list, false);

        return formatResult(calculate(s));
    }

    @Override
    public String runPart2() {

        var list = getStringInput("");
        var max = 0;
        for (String s : list) {
            for (String s1 : list) {
                if (s1.equals(s)) {
                    continue;
                }

                var val = calculate(doAdd(s, s1, false));
                max = Math.max(max, Integer.parseInt(val));
            }
        }
        log.info("hits2 {} {}", hits2, cache2.size());

        return formatResult(max);
    }

    public String calculate(String s) {
        if (cache2.containsKey(s)) {
            hits2++;
            return cache2.get(s);
        }
        var orig = s;
        var p = Pattern.compile("\\[(\\d+),(\\d+)\\]");

        var m1 = p.matcher(s);
        if (m1.find()) {

            var lhs = s.substring(0, m1.start());
            var rhs = s.substring(m1.end());
            var n1 = Integer.parseInt(m1.group(1));
            var n2 = Integer.parseInt(m1.group(2));
            var x = 3 * n1 + 2 * n2;

            s = lhs + x + rhs;
            return calculate(s);
        }
        cache2.put(orig, s);
        return s;
    }

    private String doAdd(List<String> list, boolean debug) {


        String result = null;
        for (var s : list) {
            if (result == null) {
                result = s;
                continue;

            }
            result = doAdd(result, s, debug);
        }
        return result;
    }

    private String doAdd(String s1, String s2, boolean debug) {
        return reduce(String.format("[%s,%s]", s1, s2), debug);
    }


    private String reduce(String s, boolean debug) {


        var orig = s;

        while (true) {

            var splitIndex = getFirstSplitIndex(s);
            var explodeIndex = getFirstExplodeIndex(s);

            if (splitIndex == null && explodeIndex == null) {
                return s;
            }
            if (splitIndex == null) {
                var temp = explodePair(s);
                if (debug) {
                    log.info("======= After Explode {} => {}", s, temp);
                }
                s = temp;
            } else if (explodeIndex == null) {
                var temp = split(s);
                if (debug) {
                    log.info("======= After Split {} => {}", s, temp);
                }
                s = temp;
            } else {
                var temp = explodePair(s);
                if (debug) {
                    log.info("======= After  Explode {} => {}", s, temp);
                }
                s = temp;
            }

            if (s == null) {
                throw new IllegalArgumentException("We fucked up");
            }

        }

    }

    private String split(String s) {
        var p = Pattern.compile("\\d{2,}");

        var m1 = p.matcher(s);
        while (m1.find()) {
            var lhs = s.substring(0, m1.start());
            var rhs = s.substring(m1.end());
            var num = Integer.parseInt(m1.group(0));
            var n1 = num / 2;
            var n2 = num % 2 == 0 ? n1 : n1 + 1;


            return lhs + "[" + n1 + "," + n2 + "]" + rhs;
        }

        return null;
    }

    private Integer getFirstSplitIndex(String s) {
        var p = Pattern.compile("\\d{2,}");

        var m1 = p.matcher(s);
        if (m1.find()) {


            return m1.start();

        }
        return null;
    }

    private Integer getFirstExplodeIndex(String s) {
        var p = Pattern.compile("\\[(\\d+),(\\d+)\\]");

        var m1 = p.matcher(s);
        while (m1.find()) {

            var lhs = s.substring(0, m1.start());
            int count = getCount(lhs);
            if (count == 4) {
                return m1.start();
            }

        }
        return null;
    }

    private String explodePair(String s) {

        var p = Pattern.compile("\\[(\\d+),(\\d+)\\]");

        var m1 = p.matcher(s);
//        log.info("{}", s);
        while (m1.find()) {

            var lhs = s.substring(0, m1.start());
            var rhs = s.substring(m1.end());
            int count = getCount(lhs);
            if (count == 4) {
                var n1 = Integer.parseInt(m1.group(1));
                var n2 = Integer.parseInt(m1.group(2));
                return doExplode(n1, n2, lhs, rhs);
            }

        }
        return null;
    }

    private int getCount(String lhs) {
        int count = 0;
        for (char c : lhs.toCharArray()) {
            if (c == '[') {
                count++;
            }
            if (c == ']') {
                count--;
            }
        }
        return count;
    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    private String doExplode(int n1, int n2, String lhs, String rhs) {
        var rightNumbers = getNumbers(rhs);
        var leftNumbers = getNumbers(lhs);


        if (!leftNumbers.isEmpty()) {
            var left = leftNumbers.get(leftNumbers.size() - 1);
            var target = String.valueOf(left + n1);
            lhs = replaceLast(lhs, left + "", target);
        }

        if (!rightNumbers.isEmpty()) {
            var right = rightNumbers.get(0);
            var temp = rhs.replaceFirst(right + "", String.valueOf(right + n2));
            rhs = temp;
        }

        return lhs + "0" + rhs;
    }

    List<Integer> getNumbers(String s) {
        var res = new ArrayList<Integer>();
        var p2 = Pattern.compile("\\d+");
        var m2 = p2.matcher(s);
        while (m2.find()) {
            res.add(Integer.parseInt(m2.group(0)));
        }

        return res;
    }


    @Override
    public String getAnswerPart1() {
        return "3574";
    }

    @Override
    public String getAnswerPart2() {
        return "4763";
    }
}
