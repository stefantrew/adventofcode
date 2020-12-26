package trew.stefan.aoc2020.day13;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day13 implements AOCDay {
    @AllArgsConstructor
    class Bus {
        public int id;
        public int offset;
    }

    @AllArgsConstructor
    class Result {
        public BigDecimal inc;
        public BigDecimal start;
    }

    private int day = 13;

    @Override
    public String runPart1() {
        List<String> lines = InputReader2020.readStrings(day, "");

        int time = 0;

        long minTime = 0;
        long minTimeBus = 0;
        for (String line : lines) {
            if (time == 0) {
                time = Integer.parseInt(line);
                continue;
            }
            if (line.equals("x")) {
                continue;
            }

            int bus = Integer.parseInt(line);
            int delta = time - time % bus;

            if (minTime == 0 || delta < minTime) {
                minTime = delta;
                minTimeBus = bus;
            }


        }
        return String.valueOf((minTime + minTimeBus - time) * minTimeBus);
    }

    @Override
    public String runPart2() {

        List<String> lines = InputReader2020.readStrings(day, "");


        int id = 0;

        int offset = 0;
        List<Bus> list = new ArrayList<>();
        long a = 0;
        for (String line : lines) {
            if (id == 0) {
                id = Integer.parseInt(line);
                continue;
            }
            offset++;
            if (line.equals("x")) {
                continue;
            }

            Bus bus = new Bus(Integer.parseInt(line), offset - 1);
            list.add(bus);

//            log.info("{} {} ", bus.id, bus.offset);
        }

        Result res = new Result(BigDecimal.valueOf(1), BigDecimal.valueOf(1));
        for (int i = 2; i < list.size() + 1; i++) {

            res = run2(list, res.start, res.inc, i);
//            log.info("{} {}", res.start, res.inc);
        }
        return String.valueOf(res.start);
    }

    private Result run2(List<Bus> list, BigDecimal start, BigDecimal inc, int n) {


        BigDecimal x = start;
        BigDecimal last = new BigDecimal(0);


        while (true) {
            boolean flag = true;
            for (Bus bus : list.subList(0, n)) {
                if ((x.add(BigDecimal.valueOf(bus.offset))).remainder(BigDecimal.valueOf(bus.id)).longValue() > 0) {
                    flag = false;
                    break;
                }
            }
            if (flag && x.longValue() > 0) {
//                log.info("{} {}", x, x.subtract(last));
                if (last.longValue() != 0) {
                    return new Result(x.subtract(last), last);
                }
                last = x;
            }


            x = x.add(inc);

        }
    }
}
