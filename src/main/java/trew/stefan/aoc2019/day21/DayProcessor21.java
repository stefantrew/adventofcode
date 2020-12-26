package trew.stefan.aoc2019.day21;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class DayProcessor21 {

    private int rel = 0;
    private Long lastOutput;

    int current = 0;
    List<Long> numbers = new ArrayList<>();

    public DayProcessor21(List<String> lines) {
        numbers = lines.stream().map(Long::parseLong).collect(Collectors.toList());
    }


    private void setValue(List<Long> numbers, char mode, int position, Long value) throws Exception {
//        log.info("SET {} {} {}", mode, position, value);
        switch (mode) {
            case '0':
                position = (int) numbers.get(position).intValue();
//                log.info("pos {}", position);
                break;
            case '1':
                break;
            case '2':
                position = rel + numbers.get(position).intValue();
                break;
        }

        while (numbers.size() <= position) {
            numbers.add(0L);
        }
//        log.info("set {} with {}", position, value);
        if (position < 0) {
            throw new Exception("Cannont be neg");
        }
        numbers.set(position, value);
    }

    private long getValue(List<Long> numbers, char mode, int position) throws Exception {

        switch (mode) {
            case '0':
                position = numbers.get(position).intValue();
                break;
            case '1':
                break;
            case '2':
                position = rel + numbers.get(position).intValue();
                break;
        }
        while (numbers.size() <= position) {
            numbers.add(0L);
        }
        if (position < 0) {
            throw new Exception("Cannont be neg");
        }
        return numbers.get(position);
    }


    public Long run(Integer input) throws Exception {

        while (true) {

            int instruction = numbers.get(current).intValue();

            int action = instruction % 100;
            int params = instruction / 100;
            String paramsString = "0000" + params;

            char param1 = paramsString.charAt(paramsString.length() - 1);
            char param2 = paramsString.charAt(paramsString.length() - 2);
            char param3 = paramsString.charAt(paramsString.length() - 3);


            switch (action) {
                case 99:
                    return null; // numbers.get(0);
                case 1:
                    long val1 = getValue(numbers, param1, (current + 1));
                    long val2 = getValue(numbers, param2, (current + 2));
                    long sum = val1 + val2;
//                    log.info("Adding {} + {} = {} to {}", val1, val2, sum, numbers.get(current + 3));
                    setValue(numbers, param3, current + 3, sum);
                    current += 4;
                    break;
                case 2:
                    long val1b = getValue(numbers, param1, (current + 1));
                    long val2b = getValue(numbers, param2, (current + 2));
                    long sumb = val1b * val2b;
//                    log.info("Mul {} + {} = {} to {}", val1b, val2b, sumb, numbers.get(current + 3));
                    setValue(numbers, param3, current + 3, sumb);

                    current += 4;
                    break;
                case 3:
                    // set value
                    if (input == null) {
                        return null;
                    }
//                    log.info("------------------- INPUT {} {}------------------", input, (char) input.intValue());

                    setValue(numbers, param1, current + 1, (long) input);
                    input = null;
                    current += 2;
                    break;
                case 4:

                    lastOutput = getValue(numbers, param1, (current + 1));
                    current += 2;
//                    log.info("------------------- OUTPUT {} ------------------", lastOutput);

                    return lastOutput;
                case 5:
                    long val5a = getValue(numbers, param1, (current + 1));
                    if (val5a != 0) {
                        current = (int) getValue(numbers, param2, (current + 2));
                    } else {
                        current += 3;
                    }

                    break;
                case 6:
                    long val6a = getValue(numbers, param1, (current + 1));
                    if (val6a == 0) {
                        current = (int) getValue(numbers, param2, (current + 2));
                    } else {
                        current += 3;
                    }
                    break;
                case 7:
                    long val7a = getValue(numbers, param1, (current + 1));
                    long val7b = getValue(numbers, param2, (current + 2));
                    setValue(numbers, param3, current + 3, val7a < val7b ? 1L : 0L);
                    current += 4;

                    break;
                case 8:
                    long val8a = getValue(numbers, param1, (current + 1));
                    long val8b = getValue(numbers, param2, (current + 2));
                    setValue(numbers, param3, current + 3, val8a == val8b ? 1L : 0L);
                    current += 4;

                    break;
                case 9:

                    long val9 = getValue(numbers, param1, (current + 1));
                    rel += val9;
                    current += 2;

                    break;
            }
        }
    }


}

