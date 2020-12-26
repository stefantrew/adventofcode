package trew.stefan.aoc2020.day03;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day03 implements AOCDay {

    int w = 0;
    int h = 0;
    private int day = 3;
    List<String> lines2 = new ArrayList<String>();
        int n = 100;

    public Day03() {
        List<String> lines = InputReader2020.readStrings(day, "");

        w = lines.get(0).length();
        h = lines.size();


        for (String line : lines) {
            String temp = "";
            for (int i = 0; i < n; i++) {
                temp += line;
            }

            lines2.add(temp);
        }

    }

    @Override
    public String runPart1() {
        return String.valueOf(getHits(h, lines2, 3, 1));

    }

    @Override
    public String runPart2() {
        long res =
                getHits(h, lines2, 1, 1) *
                        getHits(h, lines2, 3, 1) *
                        getHits(h, lines2, 5, 1) *
                        getHits(h, lines2, 7, 1) *
                        getHits(h, lines2, 1, 2);

        return String.valueOf(res);
    }

    private int getHits(int h, List<String> lines2, int dx, int dy) {
        int x = 0;
        int y = 0;
        int c1 = 0;

        while (y < h - 1) {
            y += dy;
            x += dx;
            char c = lines2.get(y).charAt(x);
            if (c == '#') c1++;
//            log.info("{} {} {} {}", y, x, c, c1);

        }
        return c1;
    }
}
