package trew.stefan.aoc2019.completed;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class Processor implements Day {

    private int currentLine = 0;
    private int lastOutput = 0;
    private boolean running = true;
    private List<Integer> numbers;

    public Processor(List<String> lines, int phase) {
        numbers = lines.stream().map(Integer::parseInt).collect(Collectors.toList());
        numbers.set(numbers.get(currentLine + 1), phase);
        currentLine += 2;
    }


    private int getValue(List<Integer> numbers, char mode, int position) {
        int result = mode == '1' ? numbers.get(position) : numbers.get(numbers.get(position));
//        log.info("Get Value: mode: {} param: {} result: {}", mode, position, result);
        return result;
    }

    public void run() {

    }

    public int run2(int input) {

//        List<String> lines = InputReader.readStrings(5, "");
//        log.info("{}", numbers);
        int output = 0;

        while (true) {
            int instruction = numbers.get(currentLine);

            int action = instruction % 100;
            int params = instruction / 100;
            String paramsString = "0000" + Integer.toString(params);

            char param1 = paramsString.charAt(paramsString.length() - 1);
            char param2 = paramsString.charAt(paramsString.length() - 2);

//            log.info("ACTION {}", action);
//            log.info("PARAMS {}", param1);
//            log.info("PARAMS {}", param2);

            switch (action) {
                case 99:
                    running = false;
                    return lastOutput; // numbers.get(0);
                case 1:
                    int val1 = getValue(numbers, param1, (currentLine + 1));
                    int val2 = getValue(numbers, param2, (currentLine + 2));
                    int sum = val1 + val2;
//                    log.info("Adding {} + {} = {} to {}", val1, val2, sum, numbers.get(currentLine + 3));
                    numbers.set(numbers.get(currentLine + 3), sum);
                    currentLine += 4;
                    break;
                case 2:
                    int val1b = getValue(numbers, param1, (currentLine + 1));
                    int val2b = getValue(numbers, param2, (currentLine + 2));
                    int sumb = val1b * val2b;
//                    log.info("Mul {} + {} = {} to {}", val1b, val2b, sumb, numbers.get(currentLine + 3));
                    numbers.set(numbers.get(currentLine + 3), sumb);
                    currentLine += 4;
                    break;
                case 3:
                    // set value

//                        log.info("Writing input {} to pos {}", input, numbers.get(currentLine + 1));
                    numbers.set(numbers.get(currentLine + 1), input);

                    currentLine += 2;
                    break;
                case 4:

                    output = getValue(numbers, param1, (currentLine + 1));
//                    log.info("output pos {} = {}", numbers.get(val4), val4);
//                    if (numbers.get(val4) != 0) return;
                    currentLine += 2;
                    lastOutput = output;
                    return output;
//                    log.info("------------------- OUTPUT {} ------------------", output);
                case 5:
                    int val5a = getValue(numbers, param1, (currentLine + 1));
                    if (val5a != 0) {
                        currentLine = getValue(numbers, param2, (currentLine + 2));
                    } else {
                        currentLine += 3;
                    }

                    break;
                case 6:
                    int val6a = getValue(numbers, param1, (currentLine + 1));
                    if (val6a == 0) {
                        currentLine = getValue(numbers, param2, (currentLine + 2));
                    } else {
                        currentLine += 3;
                    }
                    break;
                case 7:
                    int val7a = getValue(numbers, param1, (currentLine + 1));
                    int val7b = getValue(numbers, param2, (currentLine + 2));
                    numbers.set(numbers.get(currentLine + 3), val7a < val7b ? 1 : 0);
                    currentLine += 4;

                    break;
                case 8:
                    int val8a = getValue(numbers, param1, (currentLine + 1));
                    int val8b = getValue(numbers, param2, (currentLine + 2));
                    numbers.set(numbers.get(currentLine + 3), val8a == val8b ? 1 : 0);
                    currentLine += 4;

                    break;
            }
        }
    }


}

