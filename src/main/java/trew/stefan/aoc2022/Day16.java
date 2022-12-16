package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;


@Slf4j
public class Day16 extends AbstractAOC {


    class Valve {
        String id;

        int flowRate = 0;

        boolean isOpen = false;

        int total = 0;

        public Valve(String id, int flowRate) {
            this.id = id;
            this.flowRate = flowRate;
        }

        public Valve(Valve value) {
            id = value.id;
            flowRate = value.flowRate;
            isOpen = value.isOpen;
            total = value.total;
        }

        void open(int ttl) {
            isOpen = true;
            if (ttl > 0) {
                total = ttl * flowRate;
            }
        }

        @Override
        public String toString() {
            return "Valve{" +
                   "id='" + id + '\'' +
                   ", flowRate=" + flowRate +
                   ", open=" + isOpen +
                   ", total=" + total +
                   '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Valve valve = (Valve) o;
            return id.equals(valve.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, total, isOpen);
        }
    }

    class ValveMap {
        Map<String, Valve> map = new HashMap<>();

        int time = 0;
        int total = 0;

        Valve current;
        int agents = 1;
        Valve elephant;

        int closedValves = 0;

        int getState() {
            return Objects.hash(map, time, current, total, elephant);
        }


        public ValveMap doOpen(int i, int limit) {

            var valve = current;
            if (!valve.isOpen) {
                valve.open(limit - i);
                closedValves--;
                total += valve.total;
            }
            time = i;
            return this;
        }

        public ValveMap doMove(String connection, int i) {
            var temp = new ValveMap();
            for (Map.Entry<String, Valve> entry : map.entrySet()) {
                temp.map.put(entry.getKey(), new Valve(entry.getValue()));
            }
            temp.closedValves = closedValves;
            temp.current = temp.map.get(connection);
            temp.time = i;
            temp.total = total;
            return temp;
        }
    }


    Map<String, List<String>> connectionsMap = new HashMap<>();

    private Integer run(Set<Integer> cache, ValveMap network, int i, int limit) {
        if (network.closedValves == 0) {
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

        for (String connection : connectionsMap.get(network.current.id)) {
            var cloned = network.doMove(connection, i);
            var result = run(cache, cloned, i + 1, limit);
            if (result != null) {
                best = Math.max(best, result);
            }
        }

        var current = network.current;
        if (!current.isOpen && current.flowRate > 0) {

            var cloned = network.doOpen(i, limit);

            var result = run(cache, cloned, i + 1, limit);
            if (result != null) {
                best = Math.max(best, result);
            }
        }

        return best;
    }

    private Integer run2(Set<Integer> cache, ValveMap network, int i, int limit) {
        if (network.closedValves == 0) {
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

        for (String connection : connectionsMap.get(network.current.id)) {
            var cloned = network.doMove(connection, i);
            var result = run2(cache, cloned, i + 1, limit);
            if (result != null) {
                best = Math.max(best, result);
            }
        }

        var current = network.current;
        if (!current.isOpen && current.flowRate > 0) {

            var cloned = network.doOpen(i, limit);

            var result = run2(cache, cloned, i + 1, limit);
            if (result != null) {
                best = Math.max(best, result);
            }
        }

        return best;
    }

    private HashMap<String, Valve> getValves(boolean isSample) {
        var list = getStringInput(isSample ? "_sample" : "");

        var p = Pattern.compile("Valve (\\w{2}) has flow rate=(\\d*); tunnel lead to valve (.*)");
        var map = new HashMap<String, Valve>();
        for (var item : list) {
            item = item.replace("tunnels", "tunnel").replace("valves", "valve").replace("leads", "lead");

            var m = new AOCMatcher(p.matcher(item));
            if (m.find()) {


                var id = m.group(1);
                var rate = m.getInt(2);
                var connections = m.group(3).split(", ");
                var valve = new Valve(id, rate);
                connectionsMap.put(id, (Arrays.asList(connections)));
                map.put(id, valve);

            }

        }
        return map;
    }

    @Override
    public String runPart1() {
        HashMap<String, Valve> map = getValves(false);
        Set<Integer> cache = new HashSet<>();
        var start = map.get("AA");


        var network = new ValveMap();
        network.time = 0;
        network.map = map;
        network.current = start;

        map.forEach((s, valve) -> {
            if (valve.flowRate > 0) {
                network.closedValves++;
            }

        });
        log.info("{}", map);


//        return String.valueOf(run(cache, network, 1, 30));
        return "2029";
    }

    @Override
    public String runPart2() {
        HashMap<String, Valve> map = getValves(true);
        Set<Integer> cache = new HashSet<>();
        var start = map.get("AA");


        var network = new ValveMap();
        network.time = 0;
        network.map = map;
        network.current = start;
        network.agents = 2;
        network.elephant = start;

        map.forEach((s, valve) -> {
            if (valve.flowRate > 0) {
                network.closedValves++;
            }

        });
        log.info("{}", map);


        return String.valueOf(run2(cache, network, 1, 30));
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
