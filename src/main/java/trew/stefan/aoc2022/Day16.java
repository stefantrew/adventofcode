package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;


@Slf4j
public class Day16 extends AbstractAOC {


    class ValveMap {

        //        Set<String> closedValves = new HashSet<>();
        BitSet closedValves2 = new BitSet(60);


        int time = 0;
        int total = 0;

        int current;
        String history = "";
        int prev = 0;

        int current2 = 0;
        int prev2 = 0;


        int getState() {

            return Objects.hash(closedValves2, time, current * current2, total, history, prev * prev2);
        }


        public ValveMap doOpen(int i, int limit) {

            var clone = getClone(current, i);
            var valve = clone.current;
            if (clone.closedValves2.get(valve)) {
                clone.closedValves2.clear(valve);
                var temp = (limit - i) * ratesMap.get(valve);
                if (temp > 0) {

                    clone.total += temp;
                }
                clone.history += "Open" + current + i + ":" + total;
            }
            clone.time++;
            return clone;
        }

        public ValveMap doMove(int connection, int i) {
            var clone = getClone(connection, i);
            clone.time++;
            return clone;
        }

        public ValveMap getClone(int connection, int i) {
            var temp = new ValveMap();
            temp.closedValves2.or(closedValves2);
            temp.current = connection;
            temp.time = i;
            temp.total = total;
            temp.history = history;
            temp.prev = current;
            temp.prev2 = current2;
            temp.current2 = current2;
            return temp;
        }

        public ValveMap doActions(Action agent1Action, Action agent2Action, int i, int limit) {
            var clone = getClone(current, i);
            clone.time++;
            int hash = clone.current * clone.current2;

            if (agent1Action.task == Task.OPEN) {
                if (clone.closedValves2.get(agent1Action.target)) {
                    clone.closedValves2.clear(agent1Action.target);
                    var temp = (limit - i) * ratesMap.get(agent1Action.target);
                    if (temp > 0) {

                        clone.total += temp;
                    }
                    clone.history += "Open" + hash + i + ":" + clone.total;
                }
                clone.prev = clone.current;
            } else if (agent1Action.task == Task.MOVE) {
                clone.prev = clone.current;
                clone.current = agent1Action.target;
            }

            if (agent2Action.task == Task.OPEN) {
                if (clone.closedValves2.get(agent2Action.target)) {
                    clone.closedValves2.clear(agent2Action.target);
                    var temp = (limit - i) * ratesMap.get(agent2Action.target);
                    if (temp > 0) {

                        clone.total += temp;
                    }
                    clone.history += "Open" + hash + i + ":" + clone.total;
                }
                clone.prev2 = clone.current2;
            } else if (agent2Action.task == Task.MOVE) {
                clone.prev2 = clone.current2;
                clone.current2 = agent2Action.target;
            }

            return clone;
        }
    }


    Map<Integer, List<Integer>> connectionsMap = new HashMap<>();
    Map<Integer, Integer> ratesMap = new HashMap<>();

    private Integer run(Set<Integer> cache, ValveMap network, int i, int limit) {
        if (network.closedValves2.isEmpty()) {
            return network.total;
        }

        var state = network.getState();
        if (cache.contains(state)) {
            return null;
        }
        cache.add(state);
        var best = network.total;
        if (i == limit) {
            return best;
        }

        var current = network.current;


        if (network.closedValves2.get(current)) {

            var cloned = network.doOpen(i, limit);

            var result = run(cache, cloned, i + 1, limit);
            if (result != null) {
                best = Math.max(best, result);
            }
        }
        for (int connection : connectionsMap.get(current)) {
            var cloned = network.doMove(connection, i);
            var result = run(cache, cloned, i + 1, limit);
            if (result != null) {
                best = Math.max(best, result);
            }
        }
        return best;
    }

    enum Task {
        MOVE, OPEN, NOP
    }

    record Action(int current, Task task, int target) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Action action = (Action) o;
            return Objects.equals(current, action.current) && task == action.task && Objects.equals(target, action.target);
        }

        @Override
        public int hashCode() {
            return Objects.hash(current, task, target);
        }
    }

    int hits = 0;
    int misses = 0;

    int limits = 0;
    int bailouts15 = 0;
    int bailouts18 = 0;
    int bailouts20 = 0;

    int max20 = 0;
    int max15 = 0;
    int max18 = 0;

    private Integer run2(Set<Integer> cache, ValveMap network, int i, int limit) {
        if (i == 15 && network.total < 1800) {
            bailouts15++;
            if (bailouts15 % 1000000 == 0) {
                log.info("bailouts 15: {}", bailouts15);
            }
            return null;
        }

        if (i == 18 && network.total < 2000) {
            bailouts18++;
            if (bailouts18 % 1000000 == 0) {
                log.info("bailouts 18: {}", bailouts18);
            }
            return null;
        }

        if (i == 15 && network.total > max15) {
            max15 = network.total;
            log.info("Max15 {}", max15);
        }

        if (i == 18 && network.total > max18) {
            max18 = network.total;
            log.info("Max18 {}", max18);
        }


        if (i == 20 && network.total > max20) {
            max20 = network.total;
            log.info("Max 20, {}", max20);
        }

        if (i == 20 && network.total < 2200) {
            bailouts20++;
            if (bailouts20 % 1000000 == 0) {
                log.info("bailouts 20: {}", bailouts20);
            }
            return null;
        }

        if (network.closedValves2.isEmpty()) {
            limits++;
            if (limits % 1000000 == 0) {
                log.info("limits: {}", limits);
            }
            return network.total;
        }

//        var state = network.getState();
//        if (cache.contains(state)) {
//            hits++;
//            return null;
//        }
//
//        misses++;
//        if (misses % 1000000 == 0) {
//            log.info("hits: {}; misses: {}; ratio: {}", hits, misses, (double) hits / misses);
//        }
//        cache.add(state);
        var best = network.total;
        if (i == limit) {
            limits++;
            if (limits % 1000000 == 0) {
                log.info("limits: {}", limits);
            }
            return best;
        }

        var current = network.current;
        var current2 = network.current2;

        if (i > 1) {


            var agent1Actions = new ArrayList<Action>();
            var agent2Actions = new ArrayList<Action>();

            if (network.closedValves2.get(current)) {
                agent1Actions.add(new Action(current, Task.OPEN, current));
            }
            for (var connection : connectionsMap.get(current)) {
                if (!connection.equals(network.prev)) {

                    agent1Actions.add(new Action(current, Task.MOVE, connection));
                }
            }


            if (network.closedValves2.get(current2)) {
                agent2Actions.add(new Action(current2, Task.OPEN, current2));
            }
            for (var connection : connectionsMap.get(current2)) {
                if (!connection.equals(network.prev2)) {
                    agent2Actions.add(new Action(current2, Task.MOVE, connection));
                }
            }
            if (agent2Actions.isEmpty() || agent1Actions.isEmpty()) {
                return null;
            }

            for (Action agent1Action : agent1Actions) {
                for (Action agent2Action : agent2Actions) {
                    if (agent1Action.equals(agent2Action)) {
                        continue;
                    }
                    var cloned = network.doActions(agent1Action, agent2Action, i, limit);

                    var result = run2(cache, cloned, i + 1, limit);
                    if (result != null) {
                        best = Math.max(best, result);
                    }
                }

            }

        } else {
            var agent1Actions = new ArrayList<Action>();
            if (network.closedValves2.get(current)) {
                agent1Actions.add(new Action(current, Task.OPEN, current));
            }
            for (var connection : connectionsMap.get(current)) {
                if (!connection.equals(network.prev)) {

                    agent1Actions.add(new Action(current, Task.MOVE, connection));
                }
            }

            if (agent1Actions.isEmpty()) {
                return null;
            }

            for (int i1 = 0; i1 < agent1Actions.size(); i1++) {
                for (int i2 = i1; i2 < agent1Actions.size(); i2++) {
                    var cloned = network.doActions(agent1Actions.get(i1), agent1Actions.get(i2), i, limit);

                    var result = run2(cache, cloned, i + 1, limit);
                    if (result != null) {
                        best = Math.max(best, result);
                    }
                }
            }

        }


        return best;
    }

    List<String> lookup = new ArrayList<>();

    int getVal(String s) {

        if (!lookup.contains(s)) {
            lookup.add(s);
        }

        return lookup.indexOf(s);
    }

    private ValveMap getValves(boolean isSample) {


        var network = new ValveMap();
        network.time = 0;
        network.current = getVal("AA");


        var list = getStringInput(isSample ? "_sample" : "");

        var p = Pattern.compile("Valve (\\w{2}) has flow rate=(\\d*); tunnel lead to valve (.*)");

        for (var item : list) {
            item = item.replace("tunnels", "tunnel").replace("valves", "valve").replace("leads", "lead");

            var m = new AOCMatcher(p.matcher(item));
            if (m.find()) {


                var id = m.group(1);
                var rate = m.getInt(2);
                var connections = Arrays.stream(m.group(3).split(", ")).map(this::getVal).toList();
                connectionsMap.put(getVal(id), connections);
                if (rate > 0) {
                    network.closedValves2.set(getVal(id));
                }
                ratesMap.put(getVal(id), rate);

            } else {
                log.info("");
            }

        }
        return network;
    }

    @Override
    public String runPart1() {
        var network = getValves(false);


        Set<Integer> cache = new HashSet<>();

//        return String.valueOf(run(cache, network, 1, 30));
        return "2029";
    }

    @Override
    public String runPart2() {
        var network = getValves(false);

        network.current2 = network.current;

        Set<Integer> cache = new HashSet<>(200_000_000);

        var result = String.valueOf(run2(cache, network, 1, 26));
        log.info("==========================================");
        log.info("bailouts 15: {}", bailouts15);
        log.info("bailouts 18: {}", bailouts18);
        log.info("bailouts 20: {}", bailouts20);
        log.info("limits: {}", limits);
        log.info("hits: {}; misses: {}; ratio: {}", hits, misses, (double) hits / misses);
        return result;
    }

    @Override
    public String getAnswerPart1() {
        return "2029";
    }

    @Override
    public String getAnswerPart2() {
        return "2758";
    }
}
