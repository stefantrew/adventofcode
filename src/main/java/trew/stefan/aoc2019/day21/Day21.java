package trew.stefan.aoc2019.day21;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.List;

@Slf4j
public class Day21 implements Day {


    public void run() {
        List<String> lines = InputReader2019.readStrings(21, "");

        try {

            DayProcessor21 processor = new DayProcessor21(lines);
            log.info("---------------- SILVER ----------------");

            readLine(processor);

            sendString(processor, "NOT J J");
            sendString(processor, "AND A J");
            sendString(processor, "AND B J");
            sendString(processor, "AND C J");
            sendString(processor, "NOT J J");
            sendString(processor, "AND D J");

            sendString(processor, "WALK");

            readLine(processor);

            processor = new DayProcessor21(lines);
            log.info("---------------- GOLD ----------------");

            readLine(processor);

            sendString(processor, "NOT J J");
            sendString(processor, "AND A J");
            sendString(processor, "AND B J");
            sendString(processor, "AND C J");
            sendString(processor, "NOT J J");

            sendString(processor, "AND D J");

            sendString(processor, "OR E T");
            sendString(processor, "OR H T");
            sendString(processor, "AND T J");


            sendString(processor, "RUN");
            readLine(processor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void sendString(DayProcessor21 processor, String str) throws Exception {
        log.info(str);
        for (char c : str.toCharArray()) {
//            log.info("{} {}", c, (byte) c);
            processor.run((int) c);
        }
//        log.info("{}", 10);
//        log.info("After input {}", );

        processor.run(10);
    }

    private void readLine(DayProcessor21 processor) {
        StringBuilder sb = new StringBuilder();
        try {
            while (true) {
                Long output = processor.run(null);
                if (output == null) {
                    break;
                }
                if (output < 255)
                    sb.append((char) output.intValue());
                else sb.append(output);
            }
            log.info("{}", sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
