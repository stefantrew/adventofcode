package trew.stefan.aoc2019.completed;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.InputReader2019;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ComputerOld {

    public static int run(int noun, int verb) {
        List<String> lines = InputReader2019.readStrings(2, "");
        List<Integer> numbers = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
        numbers.set(1, noun);
        numbers.set(2, verb);
        log.info("{}", numbers);
        int current = 0;
        while (true) {
            int action = numbers.get(current);

            switch (action) {
                case 99:
                    return numbers.get(0);
                case 1:
                    int val1 = numbers.get(numbers.get(current + 1));
                    int val2 = numbers.get(numbers.get(current + 2));
                    int sum = val1 + val2;
                    numbers.set(numbers.get(current + 3), sum);
                    break;
                case 2:
                    int val1b = numbers.get(numbers.get(current + 1));
                    int val2b = numbers.get(numbers.get(current + 2));
                    int sumb = val1b * val2b;
                    numbers.set(numbers.get(current + 3), sumb);
                    break;
            }
            current += 4;
        }
    }
}
