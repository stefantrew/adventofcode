package trew.stefan.aoc2019;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.Point;

import java.util.*;
import java.util.concurrent.locks.*;

@Slf4j
public class Day13 implements Day {

    public class CPU implements Runnable {

        private int rel = 0;
        private List<Long> numbers;
        private int current = 0;
        private Data data;
        public Queue<Integer> outputBuffer = new LinkedList<>();

        @Getter
        private boolean gameEnded = false;

        private Lock queueLock;

        public CPU(List<Long> numbers, Day13.Data data, Lock queueLock) {
            this.data = data;
            this.numbers = numbers;
            this.queueLock = queueLock;
        }

        private void setValue(char mode, int position, Long value) throws Exception {
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

        private long getValue(char mode, int position) throws Exception {

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
//        log.info("{}", position);
            if (position < 0) {
                throw new Exception("Cannont be neg");
            }
            return numbers.get(position);
        }

        public void run() {
            try {
                while (true) {
                    int instruction = numbers.get(current).intValue();
                    int params = instruction / 100;
                    int action = instruction % 100;
                    String paramsString = "0000" + params;

                    char param1 = paramsString.charAt(paramsString.length() - 1);
                    char param2 = paramsString.charAt(paramsString.length() - 2);
                    char param3 = paramsString.charAt(paramsString.length() - 3);

                    switch (action) {
                        case 99:
                            gameEnded = true;
                            return;
                        case 1:
                            long val1 = getValue(param1, (current + 1));
                            long val2 = getValue(param2, (current + 2));
                            setValue(param3, current + 3, val1 + val2);
                            current += 4;
                            break;
                        case 2:
                            long val1b = getValue(param1, (current + 1));
                            long val2b = getValue(param2, (current + 2));
                            setValue(param3, current + 3, val1b * val2b);
                            current += 4;
                            break;
                        case 3:
                            setValue(param1, current + 1, (long) data.receive());
                            current += 2;
                            break;
                        case 4:

                            try {
                                queueLock.lock();
                                outputBuffer.add((int) getValue(param1, (current + 1)));
                            } finally {
                                queueLock.unlock();
                            }
                            current += 2;

                            break;
                        case 5:
                            if (getValue(param1, (current + 1)) != 0) {
                                current = (int) getValue(param2, (current + 2));
                            } else {
                                current += 3;
                            }

                            break;
                        case 6:
                            if (getValue(param1, (current + 1)) == 0) {
                                current = (int) getValue(param2, (current + 2));
                            } else {
                                current += 3;
                            }
                            break;
                        case 7:
                            long val7a = getValue(param1, (current + 1));
                            long val7b = getValue(param2, (current + 2));
                            setValue(param3, current + 3, val7a < val7b ? 1L : 0L);
                            current += 4;

                            break;
                        case 8:
                            long val8a = getValue(param1, (current + 1));
                            long val8b = getValue(param2, (current + 2));
                            setValue(param3, current + 3, val8a == val8b ? 1L : 0L);
                            current += 4;
                            break;
                        case 9:
                            rel += getValue(param1, (current + 1));
                            current += 2;
                            break;
                    }
                }

            } catch (Exception e) {
                log.error("", e);
            }
        }
    }


    class Data {

        private int packet;

        private boolean transfer = true;

        public synchronized void send(int packet) {
            while (!transfer) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Thread interrupted", e);
                }
            }
            transfer = false;

            this.packet = packet;
            notifyAll();
        }

        public synchronized int receive() {
            while (transfer) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    log.error("Thread interrupted", e);
                }
            }
            transfer = true;

            notifyAll();
            return packet;
        }
    }

    private class GameMap {

        @Getter
        @Setter
        int score = 0;
        int sizeV;
        int sizeH;
        private int[][] map;

        @Getter
        int blockTiles = 0;

        Point ball;
        Point paddle;

        GameMap(int sizeV, int sizeH) {
            this.sizeV = sizeV;
            this.sizeH = sizeH;
            map = new int[sizeH][sizeV];
            for (int i = 0; i < sizeV; i++) {
                for (int j = 0; j < sizeH; j++) {
                    map[j][i] = 0;
                }
            }
        }

        void setPoint(Point point, int value) {

            if (value == 3) {
                paddle = point;
            } else if (value == 4) {
                ball = point;
            }

            if (point.getX() >= sizeH || point.getY() >= sizeV) {
                throw new IndexOutOfBoundsException(point.toString());
            }
            if (point.getX() < 0 || point.getY() < 0) {
                throw new IndexOutOfBoundsException(point.toString());
            }
            map[point.getX()][point.getY()] = value;
        }


        public void printMap() {

            log.info("");
            log.info("");
            for (int i = 0; i < sizeV; i++) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < sizeH; j++) {

                    switch (map[j][i]) {

                        case 0:
                            builder.append(' ');
                            break;
                        case 1:
                            builder.append('#');
                            break;
                        case 2:
                            builder.append('@');
                            blockTiles++;
                            break;
                        case 3:
                            builder.append('_');
                            break;
                        case 4:
                            builder.append('*');
                            break;
                    }
                }
                log.info(builder.toString());
            }

            log.info("Block Tiles {}   Score {}", blockTiles, score);
            log.info("Ball <{}>", ball);
            log.info("Paddle <{}>", paddle);
        }
    }


    private GameMap map = new GameMap(30, 40);
    private final Lock queueLock = new ReentrantLock();

    @SuppressWarnings("ConstantConditions")
    public void run() {
        List<Long> lines = InputReader2019.readCommaStringsLone(13, "");

        Data data = new Data();
        CPU proc = new CPU(lines, data, queueLock);
        Thread cpu = new Thread(proc);

        cpu.start();
        while (true) {
            try {
                Thread.sleep(1);
                queueLock.lock();
                try {

                    while (proc.outputBuffer.size() >= 3) {
                        int a = proc.outputBuffer.poll();
                        int b = proc.outputBuffer.poll();
                        int c = proc.outputBuffer.poll();
                        if (a == -1) {
                            map.setScore(c);
//                            log.info("Score {}", map.score);
                        } else {
                            map.setPoint(new Point(a, b), c);
                        }
                    }
                } finally {
                    queueLock.unlock();
                }

                if (proc.isGameEnded()) {
                    log.info("Score {}", map.score);
                    return;
                }
                if (cpu.getState() == Thread.State.WAITING && map.ball != null && map.paddle != null) {
                    if (map.ball.getX() > map.paddle.getX()) {
                        data.send(1);
                    } else if (map.ball.getX() < map.paddle.getX()) {
                        data.send(-1);
                    } else {
                        data.send(0);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
