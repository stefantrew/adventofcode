package trew.stefan.aoc2019.day23;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// 17000 too high
// 16674 too high
// 10246 too low
@Slf4j
public class Day23 implements Day {


    public void run() {
        List<String> lines = InputReader2019.readStrings(23, "");

        List<Runnable> runnables = new ArrayList<>();
        Map<Long, Processor> processors = new HashMap<>();
        Map<Long, BlockingQueue<Packet>> inputQueues = new HashMap<>();
        BlockingQueue<Packet> outputQueue = new ArrayBlockingQueue<>(10024);
        for (long i = 0; i < 50; i++) {
            BlockingQueue<Packet> inputQueue = new ArrayBlockingQueue<>(1024);

            Processor processor = new Processor(lines, i, inputQueue, outputQueue);
            Thread thread = new Thread(processor);
            runnables.add(processor);
            thread.start();
            inputQueues.put(i, inputQueue);
        }

        Packet lastSentNat = null;
        Packet lastNat = null;
        while (true) {
            try {
                Thread.sleep(10);
//                log.info("Out Queue: {}", outputQueue.size());
                while (outputQueue.size() > 0) {
                    Packet packet = outputQueue.poll();
                    long destId = packet.getDestId();
                    if (inputQueues.containsKey(destId)) {
                        inputQueues.get(destId).add(packet);
                    } else {
                        log.info("ADDRESS NOT FOUND {} X={}, Y={}", destId, packet.getX(), packet.getY());
                        lastNat = packet;
                    }
                }

                boolean flag = true;
                for (BlockingQueue<Packet> queue : inputQueues.values()) {
                    if (queue.size() > 0) {
                        flag = false;
                        break;
                    }
                }

                if (flag && lastNat != null) {

                    inputQueues.get(0L).add(lastNat);
                    if (lastSentNat != null && lastNat.getY().equals(lastSentNat.getY())) {
                        log.info("Y {}", lastSentNat.getY());
                        runnables.forEach(runnable -> {
                            ((Processor)runnable).doStop();
                        });
                        return;
                    }
                    lastSentNat = lastNat;
                    lastNat = null;

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
