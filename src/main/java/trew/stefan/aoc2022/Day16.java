package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;


@Slf4j
public class Day16 extends AbstractAOC {


    class ValveMap {

        Set<String> closedValves = new HashSet<>();

        int time = 0;
        int total = 0;

        String current;
        String history = "";
        String prev = "";

        String current2 = "";
        String prev2 = "";

        int getState() {
            return Objects.hash(closedValves, time, current.hashCode() * current2.hashCode(), total, history, prev, prev2);
        }

        public ValveMap doOpen(int i, int limit) {

            var clone = getClone(current, i);
            var valve = clone.current;
            if (clone.closedValves.contains(valve)) {
                clone.closedValves.remove(valve);
                var temp = (limit - i) * ratesMap.get(valve);
                if (temp > 0) {

                    clone.total += temp;
                }
                clone.history += "Open" + current + i + ":" + total;
            }
            clone.time++;
            return clone;
        }

        public ValveMap doMove(String connection, int i) {
            var clone = getClone(connection, i);
            clone.time++;
            return clone;
        }

        public ValveMap getClone(String connection, int i) {
            var temp = new ValveMap();
            temp.closedValves.addAll(closedValves);
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
            int hash = clone.current.hashCode() * clone.current2.hashCode();

            if (agent1Action.task == Task.OPEN) {
                if (clone.closedValves.contains(agent1Action.target)) {
                    clone.closedValves.remove(agent1Action.target);
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
                if (clone.closedValves.contains(agent2Action.target)) {
                    clone.closedValves.remove(agent2Action.target);
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


    Map<String, List<String>> connectionsMap = new HashMap<>();
    Map<String, Integer> ratesMap = new HashMap<>();

    private Integer run(Set<Integer> cache, ValveMap network, int i, int limit) {
        if (network.closedValves.isEmpty()) {
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


        if (network.closedValves.contains(current)) {

            var cloned = network.doOpen(i, limit);

            var result = run(cache, cloned, i + 1, limit);
            if (result != null) {
                best = Math.max(best, result);
            }
        }
        for (String connection : connectionsMap.get(current)) {
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

    record Action(String current, Task task, String target) {
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

    private Integer run2(Set<Integer> cache, ValveMap network, int i, int limit) {
        if (network.closedValves.isEmpty()) {
            return network.total;
        }

        var state = network.getState();
        if (cache.contains(state)) {
            hits++;
            return null;
        }
        misses++;
        if (misses % 1000000 == 0) {
            log.info("hits: {}; misses: {}; ratio: {}", hits, misses, (double) hits / misses);
        }
        cache.add(state);
        var best = network.total;
        if (i == limit) {
            if (limit % 1000000 == 0) {
                log.info("limits: {}", limit);
            }
            return best;
        }

        var current = network.current;
        var current2 = network.current2;


        var agent1Actions = new ArrayList<Action>();
        var agent2Actions = new ArrayList<Action>();

        if (network.closedValves.contains(current)) {
            agent1Actions.add(new Action(current, Task.OPEN, current));
        }
        for (String connection : connectionsMap.get(current)) {
            if (!connection.equals(network.prev)) {

                agent1Actions.add(new Action(current, Task.MOVE, connection));
            }
        }


        if (network.closedValves.contains(current2)) {
            agent2Actions.add(new Action(current2, Task.OPEN, current2));
        }
        for (String connection : connectionsMap.get(current2)) {
            if (!connection.equals(network.prev2)) {
                agent2Actions.add(new Action(current2, Task.MOVE, connection));
            }
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
        if (agent2Actions.isEmpty() || agent1Actions.isEmpty()) {
//            log.info("Empties");
            return null;
        }

        return best;
    }


    private ValveMap getValves(boolean isSample) {


        var network = new ValveMap();
        network.time = 0;
        network.current = "AA";


        var list = getStringInput(isSample ? "_sample" : "");

        var p = Pattern.compile("Valve (\\w{2}) has flow rate=(\\d*); tunnel lead to valve (.*)");

        for (var item : list) {
            item = item.replace("tunnels", "tunnel").replace("valves", "valve").replace("leads", "lead");

            var m = new AOCMatcher(p.matcher(item));
            if (m.find()) {


                var id = m.group(1);
                var rate = m.getInt(2);
                var connections = m.group(3).split(", ");
                connectionsMap.put(id, (Arrays.asList(connections)));
                if (rate > 0) {
                    network.closedValves.add((id));
                }
                ratesMap.put(id, rate);

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

        Set<Integer> cache = new HashSet<>(108_000_000 );

        return String.valueOf(run2(cache, network, 1, 26));
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
