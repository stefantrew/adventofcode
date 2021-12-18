package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
public class Day18 extends AbstractAOC {


    @Override
    public String runPart1() {

        var total = 0;


        var list = getStringInput("");


//        test();

        var s = doAdd(list, false);
        return formatResult(calculate(s));
    }

    private void test(int index, String input, String target) {
        var result = reduce(input, false);
        var assertion = target.equals(result);
        if (!assertion) {
            reduce(input, true);
        }
        log.info("{} TEST RED {} {} {} {}", index, input, result, target, assertion);
    }

    private void test(int index, List<String> input, String target) {
        var result = doAdd(input, false);
        var assertion = target.equals(result);
        if (!assertion) {
            doAdd(input, true);
        }
        log.info("{} TEST LST {} {} {} {}", index, input.size(), result, target, assertion);
    }

    private void test(int index, String input, String input2, String target) {
        var result = doAdd(input, input2, false);
        var assertion = target.equals(result);
        if (!assertion) {
            doAdd(input, input2, true);
        }
        log.info("{} TEST ADD {} + {} = {} _{}_ {}", index, input, input2, result, target, assertion);
    }

    private void test() {

//        test(1, "[[[[[9,8],1],2],3],4]", "[[[[0,9],2],3],4]");
//        test(2, "[7,[6,[5,[4,[3,2]]]]]", "[7,[6,[5,[7,0]]]]");
//        test(3, "[[6,[5,[4,[3,2]]]],1]", "[[6,[5,[7,0]]],3]");
//        test(4, "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
//        test(5, "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]", "[[3,[2,[8,0]]],[9,[5,[7,0]]]]");
//        test(6, "[[[[4,3],4],4],[7,[[8,4],9]]]", "[1,1]", "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]");
//
//        test(21, "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]", "[2,9]", "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]");
//        test(22, "[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]", "[1,[[[9,3],9],[[9,0],[0,7]]]]", "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]");
//        test(23, "[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]", "[[[5,[7,4]],7],1]", "[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]");
//        test(24, "[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]", "[[[[4,2],2],6],[8,7]]", "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]");
//
//
//        test(7, "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]", "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]", "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]");
////        test(8, "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]", "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]");
//
//        var list = new ArrayList<String>();
//        list.add("[1,1]");
//        list.add("[2,2]");
//        list.add("[3,3]");
//        list.add("[4,4]");
//        test(9, list, "[[[[1,1],[2,2]],[3,3]],[4,4]]");
//        list.add("[5,5]");
//        test(10, list, "[[[[3,0],[5,3]],[4,4]],[5,5]]");
//        list.add("[6,6]");
//        test(11, list, "[[[[5,0],[7,4]],[5,5]],[6,6]]");
//
//        list.clear();
//        list.add("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]");
//        list.add("[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]");
//        test(12, list, "[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]");
//
//        list.add("[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]");
//        test(13, list, "[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]");
//        list.add("[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]");
//        test(14, list, "[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]");
//        list.add("[7,[5,[[3,8],[1,4]]]]");
//        test(15, list, "[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]");
//        list.add("[[2,[2,2]],[8,[8,1]]]");
//        test(16, list, "[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]");


        log.info("calc {}", calculate("[1,9]"));
        log.info("calc {}", calculate("[[1,2],[[3,4],5]]"));
    }

    public String calculate(String s) {

        var p = Pattern.compile("\\[(\\d+),(\\d+)\\]");

        var m1 = p.matcher(s);
//        log.info("{}", s);
        if (m1.find()) {

            var lhs = s.substring(0, m1.start());
            var rhs = s.substring(m1.end());
            var n1 = Integer.parseInt(m1.group(1));
            var n2 = Integer.parseInt(m1.group(2));
            var x = 3 * n1 + 2 * n2;

            s = lhs + x + rhs;
            return calculate(s);
        }
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
        var s = "[" + s1 + "," + s2 + "]";

        return reduce(s, debug);
    }

    private String reduce(String s, boolean debug) {
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
//            } else if (splitIndex < explodeIndex) {
//                var temp = split(s);
//                if (debug) {
//                    log.info("======= After Split  {} => {}", s, temp);
//                }
//                s = temp;
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

    private String doExplode(int n1, int n2, String lhs, String rhs) {
        var rightNumbers = getNumbers(rhs);
        var leftNumbers = getNumbers(lhs);


        if (!leftNumbers.isEmpty()) {
            var left = leftNumbers.get(leftNumbers.size() - 1);
            var target = reverse(String.valueOf(left + n1));
            var temp = reverse(reverse(lhs).replaceFirst(reverse(left + ""), target));
            lhs = temp;
        }

        if (!rightNumbers.isEmpty()) {
            var right = rightNumbers.get(0);
            var temp = rhs.replaceFirst(right + "", String.valueOf(right + n2));
            rhs = temp;
        }

        return lhs + "0" + rhs;
    }


    public String reverse(String input) {


        StringBuilder input1 = new StringBuilder();

        // append a string into StringBuilder input1
        input1.append(input);

        // reverse StringBuilder input1
        input1.reverse();

        return input1.toString();
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
//    private String explodePair(Pattern p1, Pattern p2, String s) {
//        log.info("{}", s);
//        var m1 = p1.matcher(s);
//        if (m1.find()) {
//
//            var group = m1.group(1);
//            var m2 = p2.matcher(group);
//            var clean = group.replaceAll("[\\[\\]]", "");
//            var numbers = Arrays.stream(clean.split(",")).mapToInt(Integer::parseInt).toArray();
//            var temp = m2.find() ?
//                    "0," + (numbers[1] + numbers[2]) :
//                    (numbers[0] + numbers[1]) + ",0";
//
//            var aa = Pattern.compile(group, Pattern.LITERAL).matcher(s).replaceFirst(temp);
//            log.info("AA {} {} => {} =>> {}", s, group, temp, aa);
//            s = aa;
//        }
//
//        return s;
//    }


    @Override
    public String runPart2() {


        var total = 0;


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

        return formatResult(max);
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
