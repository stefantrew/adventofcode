package trew.stefan.aoc2023;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.regex.Pattern;

@Slf4j
public class Day24 extends AbstractAOC {


    @ToString
    class HailStone {
        long x;
        long y;
        long z;
        double c;
        double m;
        long dx;
        long dy;
        long dz;

        public HailStone(long x, long y, long z, long dx, long dy, long dz) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.dx = dx;
            this.dy = dy;
            this.dz = dz;
            m = (double) dy / dx;
            this.c = (y - m * x);

        }

        public double getXIntercept(HailStone other) {
            var temp = other.c - c;
            var temp2 = other.m - m;
            return -(double) temp / temp2;
        }

        public double getYIntercept(HailStone other) {
            return m * getXIntercept(other) + c;
        }


    }

    public boolean doesIntercept(HailStone stone1, HailStone stone2, long min, long max) {
        log.info("==========================================================");


        if (stone1.m == stone2.m) {
            log.info("Parallel lines");
            return false;
        }

        var temp = stone1.c - stone2.c;
        var temp2 = stone1.m - stone2.m;
        var x = -(double) temp / temp2;
        var y = stone1.m * x + stone1.c;

        log.info("X = {}, y = {}", x, y);


        if (x < min || x > max) {
            log.info("X out of bounds");
            return false;
        }

        if (y < min || y > max) {
            log.info("Y out of bounds");
            return false;
        }

        if (x > stone1.x && stone1.m < 0) {
            log.info("In the past for stone 1");
            return false;
        }

        if (x > stone2.x && stone2.m < 0) {
            log.info("In the past for stone 2");
            return false;
        }


        return true;
    }

    @Override
    public String runPart1() {

        var total = 0;
        var result = "";

        final String regex = "(\\d*), (\\d*), (\\d*) @ (-?\\d*), (-?\\d*), (-?\\d*)";

        var pattern = Pattern.compile(regex);

//        var list = getStringInput().stream().map(this::mapper).toList();
        var stones = new ArrayList<HailStone>();
        var list = getStringInput("");
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

        for (var s : list) {
            var matcher = pattern.matcher(s);
            if (matcher.find()) {
                HailStone stone = new HailStone(
                        Long.parseLong(matcher.group(1)),
                        Long.parseLong(matcher.group(2)),
                        Long.parseLong(matcher.group(3)),
                        Long.parseLong(matcher.group(4)),
                        Long.parseLong(matcher.group(5)),
                        Long.parseLong(matcher.group(6))

                );
                stones.add(stone);
//                log.info("{}", stone);


            }
        }
        //12807
        long min = 200000000000000L;
        long max = 400000000000000L;
//        long min = 7;
//        long max = 27;
//
//        var s1 = new HailStone(19, 13, 30, -2, 1, -2);
//        var s2 = new HailStone(18, 19, 22, -1, -1, -2);
//        log.info("inside: {} ", this.doesIntercept(s1, s2, min, max));
//
//        s1 = new HailStone(19, 13, 30, -2, 1, -2);
//        s2 = new HailStone(20, 25, 34, -2, -2, -4);
//        log.info("outside: {} ", this.doesIntercept(s1, s2, min, max));
//
//        s1 = new HailStone(19, 13, 30, -2, 1, -2);
//        s2 = new HailStone(12, 31, 28, -1, -2, -1);
//        log.info("outside: {} ", this.doesIntercept(s1, s2, min, max));
//
//        s1 = new HailStone(18, 19, 22, -1, -1, -2);
//        s2 = new HailStone(20, 25, 34, -2, -2, -4);
////        log.info("X 14.333 {}", s1.getXIntercept(s2));
////        log.info("Y 15.333 {}", s1.getYIntercept(s2));
//        log.info("parallel: {} ", this.doesIntercept(s1, s2, min, max));
//
//        s1 = new HailStone(19, 13, 30 , -2, 1, -2);
//        s2 = new HailStone(20, 19, 15 , 1, -5, -3);
//        log.info("in the past: {} ", this.doesIntercept(s1, s2, min, max));

        for (int i = 0; i < stones.size(); i++) {
            for (int j = i + 1; j < stones.size(); j++) {


                if (this.doesIntercept(stones.get(i), stones.get(j), min, max)) {
                    total++;
                }
            }

        }

        return formatResult(total);
    }

    @Override
    public String runPart2() {


        var list = getStringInput();

        return formatResult("");
    }

    @Override
    public String getAnswerPart1() {
        return "";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
