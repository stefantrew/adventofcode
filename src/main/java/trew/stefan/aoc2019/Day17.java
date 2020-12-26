package trew.stefan.aoc2019;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.aoc2019.completed.Day17Processor;
import trew.stefan.utils.InputReader2019;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day17 implements Day {

    char[][] map;
    Day17Processor processor;

    public void printMap() {
        log.info("---------------------------------------------------------------");
        for (int i = 0; i < 35; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < 45; j++) {
                builder.append(map[j][i]);
            }
            log.info(builder.toString());
        }
        log.info("---------------------------------------------------------------");
    }

    public void findIntersects() {
        int sum = 0;
        for (int i = 1; i < 34; i++) {
            for (int j = 1; j < 44; j++) {
                if (map[j][i] == '#' && map[j - 1][i] == '#' && map[j + 1][i] == '#' && map[j][i - 1] == '#' && map[j][i + 1] == '#') {
                    log.info("X - {}; Y - {}", i, j);
                    sum += i * j;
                }
            }
        }
        log.info("{}", sum);
    }

    public void sendString(String str) throws Exception {
        log.info(str);
        for (char c : str.toCharArray()) {
//            log.info("{} {}", c, (byte) c);
            processor.run((byte) c);
        }
//        log.info("{}", 10);
        processor.run(10);

    }

    public void run() {
        List<String> lines = InputReader2019.readStrings(17, "");

        processor = new Day17Processor(lines);
//
        log.info("done");
        try {
            buildMap();
            printMap();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        try {
            processor.wakeUp();
            readLine();
            sendString("A,A,B,C,C,A,C,B,C,B");
            readLine();
            sendString("L,4,L,4,L,6,R,10,L,6");
            readLine();
            sendString("L,12,L,6,R,10,L,6");
            readLine();
            sendString("R,8,R,10,L,6");
            readLine();
            sendString("n");
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
            readLine();
//            buildMap();
            log.info("output ___ {}", processor.run(0));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void readLine() throws Exception {
        StringBuilder sb = new StringBuilder();
        while (true) {
            Character c = processor.run(0);
            if (c == 10) break;
            sb.append(c);
        }
        log.info("{}", sb.toString());
    }

    void buildMap() {


        map = new char[45][45];

        List<Character> chars = new ArrayList<>();
        int row = 0;
        int col = 0;
        while (true) {
            try {
                Character c = processor.run(0);
                if (c == null) {
                    int counter = 0;
                    return;
                }
                switch (c) {
                    case 94:
                    case '#':
                    case '.':
//                        log.info("{}", c);
                        chars.add(c);
                        map[col++][row] = c;
                        break;
                    case 10:
                        col = 0;
                        row++;
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
