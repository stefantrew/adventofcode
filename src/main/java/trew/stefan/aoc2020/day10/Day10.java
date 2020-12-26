package trew.stefan.aoc2020.day10;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.util.*;

@Slf4j
public class Day10 implements AOCDay {

    private int day = 10;
    List<Integer> numbers = new ArrayList<>();


    @Override
    public String runPart1() {

        int max = numbers.get(numbers.size() - 1) + 3;
        numbers.add(max);
        int prev = 0;
        int count1 = 0;
        int count3 = 0;
        for (
                int number : numbers) {
            if (number == prev + 1) {
                count1++;
            }

            if (number == prev + 3) {
                count3++;
            }

            prev = number;

        }

        return String.valueOf(count1 * count3);

    }


    public Day10() {
        List<String> lines = InputReader2020.readStrings(day, "");


        for (String line : lines) {
            numbers.add(Integer.valueOf(line));
        }

        Collections.sort(numbers);


    }

    @Override
    public String runPart2() {
        long count = loop(numbers, 0, numbers.get(numbers.size() - 1));
        return String.valueOf(count);
    }

    int counter = 0;
    Map<Integer, Long> visited = new HashMap<>();

    public long loop(List<Integer> numbers, int start, int target) {
        boolean flag = false;
        long count = 0;
        if (numbers.contains(start + 1)) {
            if (visited.containsKey(start + 1)) {
                count += visited.get(start + 1);
            } else {
                count += loop(numbers, start + 1, target);

            }


        }
        if (numbers.contains(start + 2)) {
            if (visited.containsKey(start + 2)) {
                count += visited.get(start + 2);
            } else {
                count += loop(numbers, start + 2, target);

            }

        }

        if (numbers.contains(start + 3)) {
            if (visited.containsKey(start + 3)) {
                count += visited.get(start + 3);
            } else {
                count += loop(numbers, start + 3, target);

            }

        }

        if (start == target) {
            count = 1;
        }
        visited.put(start, count);
        return count;

    }
}
//959315968
