package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;


@Slf4j
public class Day01 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput();
        var total = 0;

        for (String s : list) {

            s = s.replaceAll("[^0-9]", "");
            var a = s.charAt(0);
            var c = s.charAt(s.length() - 1);
            String s1 = a + String.valueOf(c);
            total += Integer.parseInt(s1);
        }


        return String.valueOf(total);
    }


    @Override
    public String runPart2() {

        var list = getStringInput();
        var total = 0;

        for (String s : list) {

            Integer first = getFirst(s);
            Integer last = getLast(s);

            String s1 = first + String.valueOf(last);
            total += Integer.parseInt(s1);
        }


        return String.valueOf(total);
    }

    private static Integer getFirst(String s) {
        Integer first = null;
        var temp = s;
        while (first == null) {
            var d = temp.charAt(0);
            if (Character.isDigit(d)) {
                first = Integer.parseInt(String.valueOf(d));
            } else if (temp.startsWith("zero")) {
                first = 0;
            } else if (temp.startsWith("one")) {
                first = 1;
            } else if (temp.startsWith("two")) {
                first = 2;
            } else if (temp.startsWith("three")) {
                first = 3;
            } else if (temp.startsWith("four")) {
                first = 4;
            } else if (temp.startsWith("five")) {
                first = 5;
            } else if (temp.startsWith("six")) {
                first = 6;
            } else if (temp.startsWith("seven")) {
                first = 7;
            } else if (temp.startsWith("eight")) {
                first = 8;
            } else if (temp.startsWith("nine")) {
                first = 9;
            }
            temp = temp.substring(1);

        }

        return first;
    }

    private static Integer getLast(String s) {
        Integer first = null;
        var temp = s;
        while (first == null) {
            var d = temp.charAt(temp.length() -1);
            if (Character.isDigit(d)) {
                first = Integer.parseInt(String.valueOf(d));
            } else if (temp.endsWith("one")) {
                first = 1;
            } else if (temp.endsWith("two")) {
                first = 2;
            } else if (temp.endsWith("three")) {
                first = 3;
            } else if (temp.endsWith("four")) {
                first = 4;
            } else if (temp.endsWith("five")) {
                first = 5;
            } else if (temp.endsWith("six")) {
                first = 6;
            } else if (temp.endsWith("seven")) {
                first = 7;
            } else if (temp.endsWith("eight")) {
                first = 8;
            } else if (temp.endsWith("nine")) {
                first = 9;
            }
            temp = temp.substring(0, temp.length() - 1);

        }

        return first;
    }

    @Override
    public String getAnswerPart1() {
        return "54990";
    }

    @Override
    public String getAnswerPart2() {
        return "54473";
    }
}
