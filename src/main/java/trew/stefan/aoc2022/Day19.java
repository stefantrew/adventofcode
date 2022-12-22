package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
public class Day19 extends AbstractAOC {
    record Blueprint(int id, int oreRobot, int clayRobot, int obsidianOreRobot, int obsidianClayRobot,
                     int geodeOreRobot, int geodeObsidianRobot) {
    }

    class RobotSystem {

        Blueprint blueprint;

        int tick;
        int oreCount = 0;
        int clayCount = 0;
        int obsidianCount = 0;

        int geodeCount = 0;
        int oreRobotCount = 1;
        int clayRobotCount = 0;
        int obsidianRobotCount = 0;
        int geodeRobotCount = 0;

        public RobotSystem(Blueprint blueprint) {
            this.blueprint = blueprint;
        }

        void mineOre() {
            oreCount += oreRobotCount;
            clayCount += clayRobotCount;
            obsidianCount += obsidianRobotCount;
            geodeCount += geodeRobotCount;
        }


        public void buildRobot(BuildAction buildAction) {
            switch (buildAction) {

                case ORE -> oreCount -= blueprint.oreRobot;
                case CLAY -> oreCount -= blueprint.clayRobot;
                case OBSIDIAN -> {
                    oreCount -= blueprint.obsidianOreRobot;
                    clayCount -= blueprint.obsidianClayRobot;
                }
                case GEODE -> {
                    oreCount -= blueprint.geodeOreRobot;
                    obsidianCount -= blueprint.geodeObsidianRobot;
                }
                case NOP -> {
                }
                default -> throw new IllegalStateException("Unexpected value: " + buildAction);
            }
        }

        public void completeRobot(BuildAction buildAction) {
            switch (buildAction) {

                case ORE -> oreRobotCount++;
                case CLAY -> clayRobotCount++;
                case OBSIDIAN -> obsidianRobotCount++;
                case GEODE -> geodeRobotCount++;
                case NOP -> {
                }
                default -> throw new IllegalStateException("Unexpected value: " + buildAction);
            }
        }

        public List<BuildAction> getBuildActions() {
            var result = new ArrayList<BuildAction>();
            result.add(BuildAction.NOP);

            if (oreCount >= blueprint.oreRobot) {
                result.add(BuildAction.ORE);
            }

            if (oreCount >= blueprint.clayRobot) {
                result.add(BuildAction.CLAY);
            }

            if (oreCount >= blueprint.obsidianOreRobot && clayCount >= blueprint.obsidianClayRobot) {
                result.add(BuildAction.OBSIDIAN);
            }

            if (oreCount >= blueprint.geodeOreRobot && obsidianCount >= blueprint.geodeObsidianRobot) {
                result.add(BuildAction.GEODE);
            }

            return result;
        }

        public RobotSystem getClone() {
            var robotSystem = new RobotSystem(blueprint);
            robotSystem.tick = tick + 1;
            robotSystem.oreCount = oreCount;
            robotSystem.clayCount = clayCount;
            robotSystem.obsidianCount = obsidianCount;
            robotSystem.geodeCount = geodeCount;
            robotSystem.oreRobotCount = oreRobotCount;
            robotSystem.clayRobotCount = clayRobotCount;
            robotSystem.obsidianRobotCount = obsidianRobotCount;
            robotSystem.geodeRobotCount = geodeRobotCount;
            return robotSystem;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RobotSystem that = (RobotSystem) o;
            return tick == that.tick && oreCount == that.oreCount && clayCount == that.clayCount && obsidianCount == that.obsidianCount && oreRobotCount == that.oreRobotCount && clayRobotCount == that.clayRobotCount && obsidianRobotCount == that.obsidianRobotCount && geodeRobotCount == that.geodeRobotCount && geodeCount == that.geodeCount;
        }

        @Override
        public int hashCode() {
            return Objects.hash(tick, oreCount, clayCount, obsidianCount, oreRobotCount, clayRobotCount, obsidianRobotCount, geodeRobotCount, geodeCount);
        }

        @Override
        public String toString() {
            return "RobotSystem{" +
                   "oreRobotCount=" + oreRobotCount +
                   ", clayRobotCount=" + clayRobotCount +
                   ", obsidianRobotCount=" + obsidianRobotCount +
                   ", geodeRobotCount=" + geodeRobotCount +
                   '}';
        }
    }

    enum BuildAction {
        ORE, CLAY, OBSIDIAN, GEODE, NOP

    }

    int limits = 0;
    int bailouts15 = 0;
    int bailouts18 = 0;
    int bailouts22 = 0;
    int bailouts25 = 0;
    int bailouts24 = 0;

    int[] maximums = new int[33];

    public Integer computeMaximums(RobotSystem system, int limit, int tick, BuildAction buildAction, HashSet<Integer> cache) {

        var total = system.geodeCount;

        if (total > maximums[tick] && tick < 28) {
            maximums[tick] = total;
            log.info("Max {}: {}", tick, total);
        }

        system.buildRobot(buildAction);
        system.mineOre();
        system.completeRobot(buildAction);

        Integer best = null;
        if (tick == limit) {
            return system.geodeCount;
        }

        var hashCode = system.hashCode();
//        log.info("{}", hashCode);
        if (cache.contains(hashCode)) {
            return null;
        }
        cache.add(hashCode);

        for (BuildAction action : system.getBuildActions()) {
            var clone = system.getClone();
            var result = computeMaximums(clone, limit, tick + 1, action, cache);

            if (result != null) {
                if (best == null) {
                    best = result;
                }
                best = Math.max(result, best);
            }
        }

        return best;
    }

    public Integer doWork(RobotSystem system, int limit, int tick, BuildAction buildAction, HashSet<Integer> cache, int[] maximums) {

        var total = system.geodeCount;

        if (tick == 22 && total < maximums[tick] - 2) {
            bailouts22++;
            if (bailouts22 % 1000000 == 0) {
                log.info("bailouts 22: {}", bailouts22);
            }
            return null;
        }

        if (tick == 24 && total < maximums[tick] - 2) {
            bailouts24++;
            if (bailouts24 % 1000000 == 0) {
                log.info("bailouts 26: {}", bailouts24);
            }
            return null;
        }


        if (tick == 25 && total < maximums[tick] - 2) {
            bailouts25++;
            if (bailouts25 % 1000000 == 0) {
                log.info("bailouts 25: {}", bailouts25);
            }
            return null;
        }
//
        if (total > maximums[tick]) {
            maximums[tick] = total;
            log.info("Max {}: {}", tick, total);
        }

        system.buildRobot(buildAction);
        system.mineOre();
        system.completeRobot(buildAction);

        Integer best = null;
        if (tick == limit) {
            return system.geodeCount;
        }

        var hashCode = system.hashCode();
//        log.info("{}", hashCode);
        if (cache.contains(hashCode)) {
            return null;
        }
        cache.add(hashCode);

        for (BuildAction action : system.getBuildActions()) {
            var clone = system.getClone();
            var result = doWork(clone, limit, tick + 1, action, cache, maximums);

            if (result != null) {
                if (best == null) {
                    best = result;
                }
                best = Math.max(result, best);
            }
        }

        return best;
    }

    @Override
    public String runPart1() {

        var total = 0;


//        var list = getStringInput("").stream().map(this::mapper).toList();
//
//        for (var s : list) {
//            var system = new RobotSystem(s);
//            var result = doWork(system, 24, 1, BuildAction.NOP, new HashSet<Integer>());
//            log.info("{}{ {} {}", s.id, system, result);
//            total += result * s.id;
//        }

        return formatResult(total);
    }


    Blueprint mapper(String input) {

        var p = Pattern.compile("Blueprint (\\d*): Each ore robot costs (\\d*) ore. Each clay robot costs (\\d*) ore. Each obsidian robot costs (\\d*) ore and (\\d*) clay. Each geode robot costs (\\d*) ore and (\\d*) obsidian.");
        var m = new AOCMatcher(p.matcher(input));

        if (m.find()) {
            m.print();
            return new Blueprint(m.getInt(1), m.getInt(2), m.getInt(3), m.getInt(4), m.getInt(5), m.getInt(6), m.getInt(7));
        }
        return null;
    }


    @Override
    public String runPart2() {


        var total = 1;


        var list = getStringInput("").stream().map(this::mapper).toList();

        if (list.size() > 3) {
            list = list.subList(0, 3);
        }

        for (var s : list) {


            limits = 0;
            bailouts15 = 0;
            bailouts18 = 0;
            bailouts22 = 0;
            bailouts24 = 0;
            bailouts25 = 0;

            maximums = new int[33];

//Max [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 5, 7, 9, 12, 15, 0, 0, 0, 0, 0]
//Max [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 5, 7, 0, 0, 0, 0, 0, 0, 0, 0]
            var system = new RobotSystem(s);
            computeMaximums(system, 24, 1, BuildAction.NOP, new HashSet<Integer>());
            log.info("Max {}", maximums);
            var result = doWork(new RobotSystem(s), 32, 1, BuildAction.NOP, new HashSet<Integer>(), maximums);
            log.info("{} {} {}", s.id, system, result);
            log.info("Max {}", maximums);
            total *= result;
        }

        return formatResult(total);
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
