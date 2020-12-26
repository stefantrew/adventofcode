package trew.stefan.aoc2020.day06;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class Day06 implements AOCDay {

    private int day = 6;

    @Override
    public String runPart1() {

        List<String> lines = InputReader2020.readStrings(day, "");

        HashMap<Character, Integer> map = new HashMap<>();

        int count = 0;
        int num = 0;
        for (String line : lines) {

            num++;
            for (Character c : line.toCharArray()) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }

            }

            if (line.equals("")) {
                for (Character c : map.keySet()) {
                    count++;
                }
                map.clear();
                num = 0;
            }
        }

        return String.valueOf(count);
    }

    @Override
    public String runPart2() {

        List<String> lines = InputReader2020.readStrings(day, "");

        HashMap<Character, Integer> map = new HashMap<>();

        int count = 0;
        int num = 0;
        for (String line : lines) {

            num++;
            for (Character c : line.toCharArray()) {
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                } else {
                    map.put(c, 1);
                }

            }

            if (line.equals("")) {
                for (Character c : map.keySet()) {
                    if (map.get(c) == num - 1) {
                        count++;
                    }
                }
                map.clear();
                num = 0;
            }
        }

        return String.valueOf(count);

    }
}
