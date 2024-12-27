package trew.stefan.aoc2024;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


@Slf4j
public class Day13 extends AbstractAOC {

    @ToString
    static class Machine {

        Long aX;
        Long aY;
        Long bX;
        Long bY;
        Long tX;
        Long tY;


    }

    @Override
    public String runPart1() {

        var list = getStringInput("");
        var total = 0;
//
//        var machines = getMachines(list);
//
//        for (var machine : machines) {
//            var compute = compute(machine, true, false, false);
//            if (compute != null) {
//
//                total += compute;
//            }
//        }

        return String.valueOf(total);
    }

    private Long compute(Machine machine, boolean max100, boolean useB, boolean useX) {

        var leftX = useB ? machine.bX : machine.aX;
        var leftY = useB ? machine.bY : machine.aY;
        var left = useX ? machine.tX / leftX : machine.tY / leftY;

        var max = max100 ? 100 : left;
//        max = 100;
        log.info("max {}", max);
        if (!useB) {
            for (long i = max - 1; i >= 0; i--) {
                var py = machine.aY * i;
                var px = machine.aX * i;
                var cost = 3L * i;
                var dx = machine.tX - px;
                var dy = machine.tY - py;
                if (dx % machine.bX == 0 && dy % machine.bY == 0 && (dx / machine.bX) == (dy / machine.bY)) {
                    cost = dx / machine.bX + cost;
                    if (max100 && dx / machine.bX >= 100) {
                        log.info("skipping {}", dx / machine.aX);
                        continue;
                    }
                    log.info("{} {} ", i, dx / machine.bX);
                    return cost;
                }
            }
        } else {

            for (long i = max - 1; i >= 0; i--) {
                var py = machine.bY * i;
                var px = machine.bX * i;
                var cost = i;
                var dx = machine.tX - px;
                var dy = machine.tY - py;
                if (dx % machine.aX == 0 && dy % machine.aY == 0 && (dx / machine.aX) == (dy / machine.aY)) {
                    cost = 3L * dx / machine.aX + cost;
                    if (max100 && dx / machine.aX >= 100) {
                        log.info("skipping {}", dx / machine.aX);
                        continue;
                    }
                    log.info("{} {} ", i, dx / machine.aX);
                    return cost;
                }
            }
        }
        log.info("no solution");
        return null;
    }

    private List<Machine> getMachines(List<String> list) {
        var result = new ArrayList<Machine>();

        var current = new Machine();
        final String regex = "(\\d*), Y.(\\d*)";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        for (var string : list) {
            var matcher = pattern.matcher(string);
            if (matcher.find()) {

                if (string.contains("Button A")) {
                    current.aX = Long.parseLong(matcher.group(1));
                    current.aY = Long.parseLong(matcher.group(2));
                } else if (string.contains("Button B")) {
                    current.bX = Long.parseLong(matcher.group(1));
                    current.bY = Long.parseLong(matcher.group(2));
                } else if (string.contains("Prize")) {
                    current.tX = Long.parseLong(matcher.group(1));
                    current.tY = Long.parseLong(matcher.group(2));
                    result.add(current);
                    log.info("{}", current);
                    current = new Machine();
                }
            }
        }


        return result;

    }


    @Override
    public String runPart2() {


        var list = getStringInput("_sample");
        var total = 0;

        var machines = getMachines(list);

        for (var machine : machines) {
            machine.tX += 10000000000000L;
            machine.tY += 10000000000000L;
            log.info("================== {}", machine);
            var compute = compute(machine);
            if (compute != null) {

                total += compute;
            }
        }

        return String.valueOf(total);
    }

    private Long compute(Machine machine) {

        var min = compute(machine, false, false, true);

        var result = compute(machine, false, false, false);
        if (result != null) {
            if (min == null || result < min) {
                min = result;
            }
        }

        result = compute(machine, false, true, true);
        if (result != null) {
            if (min == null || result < min) {
                min = result;
            }
        }

        result = compute(machine, false, true, false);
        if (result != null) {
            if (min == null || result < min) {
                min = result;
            }
        }


        return min;
    }

    @Override
    public String getAnswerPart1() {
        return "1546338";
    }

    @Override
    public String getAnswerPart2() {
        return "978590";
    }
    //978590 too low
}
