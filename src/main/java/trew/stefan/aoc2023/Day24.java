package trew.stefan.aoc2023;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.regex.Pattern;

@Slf4j
public class Day24 extends AbstractAOC {


    @ToString
    class HailStone {
        long x;
        long y;
        long z;
        BigDecimal c;
        BigDecimal m;
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
            m = BigDecimal.valueOf(dy).divide(BigDecimal.valueOf(dx), 10, RoundingMode.FLOOR);
            this.c = BigDecimal.valueOf(y).subtract(m.multiply(BigDecimal.valueOf(x)));

        }

        public BigDecimal getXIntercept(HailStone other) {
            var temp = other.c.subtract(c);
            var temp2 = other.m.subtract(m);
            return temp.divide(temp2, 10, RoundingMode.FLOOR).negate();
        }


    }

    public boolean doesIntercept(HailStone stone1, HailStone stone2, long min, long max) {
        log.info("==========================================================");


        if (stone1.m.equals(stone2.m)) {
            log.info("Parallel lines");
            return false;
        }

        var temp = stone1.c.subtract(stone2.c);
        var temp2 = stone1.m.subtract(stone2.m);
        var x = temp.divide(temp2, 10, RoundingMode.FLOOR).negate();


        var y = stone1.m.multiply(x).add(stone1.c);

        log.info("X = {}, y = {}", x, y);


        if (x.longValue() < min || x.longValue() > max) {
            log.info("X out of bounds");
            return false;
        }

        if (y.longValue() < min || y.longValue() > max) {
            log.info("Y out of bounds");
            return false;
        }

        if (x.subtract(BigDecimal.valueOf(stone1.x)).signum() * Math.signum(stone1.dx) < 0) {
            log.info("In the past for stone 1");
            return false;
        }

        if (x.subtract(BigDecimal.valueOf(stone2.x)).signum() * Math.signum(stone2.dx) < 0) {

            log.info("In the past for stone 2");
            return false;
        }


        return true;
    }

    @Override
    public String runPart1() {

        var total = 0;

        final String regex = "(\\d*), (\\d*), (\\d*) @ (-?\\d*), (-?\\d*), (-?\\d*)";

        var pattern = Pattern.compile(regex);

        var stones = new ArrayList<HailStone>();
        var list = getStringInput("");

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
        long min = 200000000000000L;
        long max = 400000000000000L;


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

        return formatResult("");
    }

    public String testPart1() {

        long min = 7;
        long max = 27;

        var s1 = new HailStone(19, 13, 30, -2, 1, -2);
        var s2 = new HailStone(18, 19, 22, -1, -1, -2);
        log.info("inside: {} ", this.doesIntercept(s1, s2, min, max));
//
        s1 = new HailStone(19, 13, 30, -2, 1, -2);
        s2 = new HailStone(20, 25, 34, -2, -2, -4);
        log.info("inside: {} ", this.doesIntercept(s1, s2, min, max));
//
        s1 = new HailStone(19, 13, 30, -2, 1, -2);
        s2 = new HailStone(12, 31, 28, -1, -2, -1);
        log.info("outside: {} ", this.doesIntercept(s1, s2, min, max));
//
        s1 = new HailStone(19, 13, 30, -2, 1, -2);
        s2 = new HailStone(20, 19, 15, 1, -5, -3);
        log.info("outside: {} ", this.doesIntercept(s1, s2, min, max));
//

        s1 = new HailStone(18, 19, 22, -1, -1, -2);
        s2 = new HailStone(20, 25, 34, -2, -2, -4);
        log.info("parallel: {} ", this.doesIntercept(s1, s2, min, max));


        s1 = new HailStone(18, 19, 22, -1, -1, -2);
        s2 = new HailStone(12, 31, 28, -1, -2, -1);
        log.info("outside: {} ", this.doesIntercept(s1, s2, min, max));

        s1 = new HailStone(18, 19, 22, -1, -1, -2);
        s2 = new HailStone(20, 19, 15, 1, -5, -3);
        log.info("past: {} ", this.doesIntercept(s1, s2, min, max));


        s1 = new HailStone(20, 25, 34, -2, -2, -4);
        s2 = new HailStone(12, 31, 28, -1, -2, -1);
        log.info("past: {} ", this.doesIntercept(s1, s2, min, max));

        s1 = new HailStone(20, 25, 34, -2, -2, -4);
        s2 = new HailStone(20, 19, 15, 1, -5, -3);
        log.info("past: {} ", this.doesIntercept(s1, s2, min, max));


//        var s1 = new HailStone(12, 31, 28, -1, -2, -1);
//        var s2 = new HailStone(20, 19, 15, 1, -5, -3);
//        log.info("in the past: {} ", this.doesIntercept(s1, s2, min, max));

        return formatResult("");
    }

    @Override
    public String getAnswerPart1() {
        return "16018";
    }

    @Override
    public String getAnswerPart2() {
        return "";
    }
}
