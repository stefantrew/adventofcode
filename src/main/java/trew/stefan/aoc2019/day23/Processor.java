package trew.stefan.aoc2019.day23;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
class Packet {
    Long X;
    Long Y;
    Long sourceId;
    Long destId;

    public Packet(long sourceId, long destId) {
        this.sourceId = sourceId;
        this.destId = destId;
    }
}

@Slf4j
@SuppressWarnings("DuplicatedCode")

public class Processor implements Runnable {

    private int rel = 0;
    private long id;

    private boolean doStop = false;

    BlockingQueue<Packet> inputQueue = new ArrayBlockingQueue<>(1024);
    BlockingQueue<Packet> outputQueue = new ArrayBlockingQueue<>(1024);

    int current = 0;
    List<Long> numbers;

    public synchronized void doStop() {
        doStop = true;
    }

    private synchronized boolean keepRunning() {
        return  !doStop;
    }

    public Processor(List<String> lines, long id, BlockingQueue<Packet> inputQueue, BlockingQueue<Packet> outputQueue) {
        numbers = lines.stream().map(Long::parseLong).collect(Collectors.toList());
        this.id = id;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
    }

    private void setValue(List<Long> numbers, char mode, int position, Long value) throws Exception {
        switch (mode) {
            case '0':
                position = (int) numbers.get(position).intValue();
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


    public void run() {
        try {
            Packet outBound = null;
            Packet inBound = null;
            boolean idInputFlag = false;
            while (keepRunning()) {

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
                        return; // numbers.get(0);
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
                        long input;
                        if (!idInputFlag) {
                            input = id;
                            idInputFlag = true;
                        } else if (inBound != null) {
                            input = inBound.getY();
                            inBound = null;
                        } else if (inputQueue.size() == 0) {
                            input = -1;
                        } else {
                            inBound = inputQueue.poll();
                            input = inBound.getX();
                        }
                        if (input > 0) {
//                            log.info("------------------- INPUT {} ------------------", input);
                        }

                        setValue(numbers, param1, current + 1, (long) input);
                        current += 2;
                        break;
                    case 4:
                        long lastOutput = getValue(numbers, param1, (current + 1));
                        if (outBound == null) {
                            outBound = new Packet(this.id, lastOutput);
                        } else if (outBound.X == null) {
                            outBound.setX(lastOutput);
                        } else if (outBound.Y == null) {
                            outBound.setY(lastOutput);
                            outputQueue.add(outBound);
                            outBound = null;
                        }
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
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


}

