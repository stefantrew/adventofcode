package trew.stefan.aoc2020.day09;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day09 implements AOCDay {

    private int day = 9;
    List<String> lines;

    public Day09() {
        lines = InputReader2020.readStrings(day, "");

    }

    @Override
    public String runPart1() {

        List<Long> list = new ArrayList<>();

        for (String line : lines) {

            list.add(Long.valueOf(line));
        }
        int l = 25;
        for (int i = l; i < list.size(); i++) {

            long target = list.get(i);

            boolean found = false;
            for (int x = 0; x < l; x++) {
                for (int y = 0; y < l; y++) {

                    if (x != y && list.get(i + x - l) + list.get(i + y - l) == target) {
                        found = true;
                    }
                }

            }
            if (!found) {
                return String.valueOf(target);

            }

        }

        return null;
    }


    @Override
    public String runPart2() {


        List<Long> list = new ArrayList<>();

        for (String line : lines) {

            list.add(Long.valueOf(line));
        }

        for (int i = 2; i < lines.size(); i++) {

            Long result = run2(lines, i, list);
            if (result != null) {
                return String.valueOf(result);
            }
        }

        return "";
    }

    private Long run2(List<String> lines, int l, List<Long> list) {
        long target = 1930745883;
        for (int i = l; i < lines.size(); i++) {
            Long min = null;
            Long max = null;
            long sum = 0L;
            for (int a = i - l; a < i; a++) {

                long num1 = list.get(a);
                if (min == null) {
                    min = num1;
                }

                if (max == null) {
                    max = num1;
                }
                max = Math.max(max, num1);
                min = Math.min(min, num1);

                sum += num1;

            }

            if (sum == target) {
                return max + min;
            }

        }
        return null;
    }
}
