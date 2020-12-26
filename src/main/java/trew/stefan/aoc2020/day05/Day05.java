package trew.stefan.aoc2020.day05;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day05 implements AOCDay {

    private int day = 5;


    @Override
    public String runPart2() {
        List<String> lines = InputReader2020.readStrings(day, "");

        List<Integer> list = new ArrayList<Integer>();
        int max = 0;
        for (String line : lines) {

            int res = processLine(line);
            list.add(res);
            if (res > max) max = res;
        }

        for (int i = 10; i < 1024; i++) {
            if (!list.contains(i))
                return String.valueOf(i);
        }


        return String.valueOf(max);
    }

    @Override
    public String runPart1() {
        List<String> lines = InputReader2020.readStrings(day, "");

        List<Integer> list = new ArrayList<Integer>();
        int max = 0;
        for (String line : lines) {

            int res = processLine(line);
            list.add(res);
            if (res > max) max = res;
        }




        return String.valueOf(max);
    }

    private int processLine(String line) {

        int lower = 0;
        int upper = 127;
        for (int i = 0; i < 7; i++) {

            char c = line.charAt(i);

            int mid = lower + (upper - lower - 1) / 2;
            if (c == 'F') {
                upper = mid;
            } else {
                lower = mid + 1;
            }

        }
        int row = lower;

        lower = 0;
        upper = 7;
        for (int i = 7; i < 10; i++) {

            char c = line.charAt(i);

            int mid = lower + (upper - lower - 1) / 2;
            if (c == 'L') {
                upper = mid;
            } else {
                lower = mid + 1;
            }

        }

        return row * 8 + lower;
    }


}
