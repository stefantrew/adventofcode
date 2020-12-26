package trew.stefan.aoc2019;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day19 implements Day {

    @Data
    @EqualsAndHashCode
    class XY {
        private int X;
        private int Y;

        public XY(int x, int y) {
            X = x;
            Y = y;
        }


    }

    class Processor {


        private int rel = 0;
        @Getter
        private Long lastOutput;

        int current = 0;
        List<Long> numbers = new ArrayList<>();

        public Processor(List<String> lines) {
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


        public boolean run(Queue<Integer> input) throws Exception {

            while (true) {

                int instruction = numbers.get(current).intValue();

                String actionString = Long.toString(instruction);
                int action = instruction % 100;
                int params = instruction / 100;
                String paramsString = "0000" + Integer.toString(params);

                char param1 = paramsString.charAt(paramsString.length() - 1);
                char param2 = paramsString.charAt(paramsString.length() - 2);
                char param3 = paramsString.charAt(paramsString.length() - 3);


                switch ((int) action) {
                    case 99:
                        return false; // numbers.get(0);
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
//                        log.info("------------------- INPUT {} ------------------", input);
                        if (input.size() > 0)
                            setValue(numbers, param1, current + 1, (long) input.poll());
                        current += 2;
                        break;
                    case 4:

                        lastOutput = getValue(numbers, param1, (current + 1));
                        current += 2;
//                        log.info("------------------- OUTPUT {} ------------------", lastOutput);

                        break;
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

    List<String> lines;
    Map<XY, Boolean> cache = new HashMap<>();


    boolean isInBeam(int x, int y) {
        XY xy = new XY(x, y);
        if (cache.containsKey(xy)) {
            return cache.get(xy);
        }
        Processor processor = new Processor(lines);

        Queue<Integer> input = new LinkedList<>();
        input.add(x);
        input.add(y);
        try {
            processor.run(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cache.put(xy, processor.lastOutput == 1);
        return processor.lastOutput == 1;
    }

    boolean validate(int x_offset, int y_offset, int r) {
        try {
            for (int x = 0; x < r; x++) {
                for (int y = 0; y < r; y++) {
                    if (!isInBeam(x + x_offset, y + y_offset)) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void run() {
        lines = InputReader2019.readStrings(19, "");

        Queue<Integer> input = new LinkedList<>();
        long counter = 0;
        try {
            /**
             *      10  140     94
             *      20  295     198
             *      40  602     404
             *      100 1400    940
             */
            for (int y = 1000; y < 1500; y++) {
                log.info("Y {}", y);
                for (int x =1400; x < 1800; x++) {
                    Processor processor = new Processor(lines);

                    processor.run(input);
                    if (isInBeam(x, y)) {
                        if (validate(x, y, 100)) {
                            log.info("{} {} {}", x, y, x * 10000 + y);
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
