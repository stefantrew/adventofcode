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

        int getState() {
            return Objects.hash(closedValves, time, current, total, history, prev);
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
            return temp;
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
            if (best > 0) {
//                log.info("nop {} {}", network.closedValves, network.history);
            }
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

        return String.valueOf(run(cache, network, 1, 30));
//        return "2029";
    }

    @Override
    public String runPart2() {
        var network = getValves(true);


        Set<Integer> cache = new HashSet<>();

        return "";// String.valueOf(run(cache, network, 1, 30));
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
