package trew.stefan.aoc2020.day01;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day01 implements AOCDay {

    private int day = 1;

    List<Integer> numbers = new ArrayList<Integer>();

    public Day01() {
        List<String> lines = InputReader2020.readStrings(day, "");


        for (String s : lines) numbers.add(Integer.valueOf(s));
    }

    public void run() {
        log.info("Running Day {}", day);


    }

    @Override
    public String runPart1() {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {

                int sum = numbers.get(i) + numbers.get(j);
                if (sum == 2020) {
                    return String.valueOf(numbers.get(i) * numbers.get(j));
                }
            }
        }
        return "";
    }

    @Override
    public String runPart2() {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                for (int k = j + 1; k < numbers.size(); k++) {

                    int sum = numbers.get(i) + numbers.get(j) + numbers.get(k);
                    if (sum == 2020) {
                        return String.valueOf(numbers.get(i) * numbers.get(j) * numbers.get(k));
                    }
                }
            }
        }
        return "";
    }
}
