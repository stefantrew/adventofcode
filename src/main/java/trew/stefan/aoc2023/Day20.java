package trew.stefan.aoc2023;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class Day20 extends AbstractAOC {

    @Data
    static class Pulse {

        private Module sender;
        private Module receiver;
        private boolean value;

        public Pulse(Module sender, Module receiver, boolean value) {
            this.sender = sender;
            this.receiver = receiver;
            this.value = value;
        }
    }

    abstract static class Module {
        protected final String name;


        protected int parentCount = 0;
        protected int childCount = 0;
        protected List<Module> connections = new ArrayList<>();

        public abstract List<Pulse> receiveSignal(Pulse pulse);

        public Module(String name) {
            this.name = name;
        }

        public List<Pulse> send(boolean value) {
            return connections.stream()
                    .map(module -> new Pulse(this, module, value))
                    .toList();
        }

        public void setConnections(List<Module> connections) {
            this.connections = connections;
            for (Module connection : connections) {
                if (connection instanceof Conjunction conjunction) {
                    conjunction.setConnection(this);
                }
                connection.parentCount++;
            }
            childCount = connections.size();
        }


    }


    static class Broadcaster extends Module {


        public Broadcaster(String name) {
            super(name);
        }

        @Override
        public List<Pulse> receiveSignal(Pulse pulse) {
            return send(pulse.value);
        }
    }

    static class Conjunction extends Module {

        private final Map<String, Boolean> buffer = new HashMap<>();

        public Conjunction(String name) {
            super(name);
        }

        public void setConnection(Module connection) {
            buffer.put(connection.name, false);
        }

        @Override
        public List<Pulse> receiveSignal(Pulse pulse) {

            buffer.put(pulse.sender.name, pulse.value);

            return send(buffer.containsValue(false));
        }
    }

    static class FlipFlop extends Module {

        private boolean state = false;

        public FlipFlop(String name) {
            super(name);
        }

        @Override
        public List<Pulse> receiveSignal(Pulse pulse) {

            if (pulse.value) {
                return List.of();
            }

            state = !state;

            return send(state);
        }
    }

    static class Output extends Module {

        @Getter
        private boolean last;

        public Output(String name) {
            super(name);
        }

        @Override
        public List<Pulse> receiveSignal(Pulse pulse) {
            last = pulse.value;
            return List.of();
        }
    }

    static class Button extends Module {
        public Button(String name) {
            super(name);
        }

        @Override
        public List<Pulse> receiveSignal(Pulse pulse) {
            return List.of();
        }
    }

    @Override
    public String runPart1() {

        var map = new HashMap<String, Module>();

        buildModules(map);

        var broadcaster = map.get("broadcaster");
        var result = new long[2];

        for (int i = 0; i < 1000; i++) {

            var iteration = pressButton(new Pulse(new Button("button"), broadcaster, false));
            result[0] += iteration[0];
            result[1] += iteration[1];
        }


        return formatResult(result[0] * result[1]);
    }

    private long[] pressButton(Pulse buttonPulse) {

        var pulses = new LinkedList<Pulse>();
        pulses.add(buttonPulse);
        var result = new long[2];

        while (!pulses.isEmpty()) {

            var pulse = pulses.pop();
            if (pulse.value) {
                result[0]++;
            } else {
                result[1]++;
            }
            pulses.addAll(pulse.receiver.receiveSignal(pulse));


        }

        return result;
    }

    private int lastDt = 0;
    private int lastJs = 0;
    private int lastQs = 0;
    private int lastTs = 0;

    private void pressButton2(Pulse buttonPulse, int n) {

        var pulses = new LinkedList<Pulse>();
        pulses.add(buttonPulse);

        while (!pulses.isEmpty()) {

            var pulse = pulses.pop();
            if (pulse.receiver.name.equals("cl")) {
                var cl = (Conjunction) pulse.receiver;
                if (cl.buffer.get("dt") && n != lastDt) {
                    lastDt = n;
                }
                if (cl.buffer.get("js") && n != lastJs) {
                    lastJs = n;
                }
                if (cl.buffer.get("qs") && n != lastQs) {
                    lastQs = n;
                }
                if (cl.buffer.get("ts") && n != lastTs) {
                    lastTs = n;
                }
            }
            pulses.addAll(pulse.receiver.receiveSignal(pulse));


        }

    }


    private void buildModules(HashMap<String, Module> map) {
        var list = getStringInput("");

        final String regex = "([%&]?)(\\w*) -> ([\\w,\\s]*)";
        var pattern = Pattern.compile(regex);

        for (var s : list) {
            if (s.startsWith("#")) {
                continue;
            }

            var matcher = pattern.matcher(s);
            if (matcher.find()) {
                var type = matcher.group(1);
                var name = matcher.group(2);

                var module = switch (type) {
                    case "%" -> new FlipFlop(name);
                    case "&" -> new Conjunction(name);
                    case "" -> new Broadcaster(name);
                    default -> new Output(name);
                };

                map.put(name, module);
            }

        }

        for (var s : list) {
            if (s.startsWith("#")) {
                continue;
            }

            var matcher = pattern.matcher(s);
            if (matcher.find()) {
                var name = matcher.group(2);
                var module = map.get(name);
                var connections = matcher.group(3).split(", ");

                var tempList = new ArrayList<Module>();
                for (String connection : connections) {
                    if (!map.containsKey(connection)) {
                        map.put(connection, new Output(connection));
                    }

                    tempList.add(map.get(connection));
                }


                module.setConnections(tempList);

            }


        }
    }

    @Override
    public String runPart2() {


        var map = new HashMap<String, Module>();

        buildModules(map);

        var result = 0;
        var broadcaster = map.get("broadcaster");

        Pulse pulseButton = new Pulse(new Button("button"), broadcaster, false);
        while (lastQs * lastTs * lastDt * lastJs == 0) {

            result++;

            pressButton2(pulseButton, result);


        }

        var number = BigDecimal.valueOf(lastDt).multiply(BigDecimal.valueOf(lastJs))
                .multiply(BigDecimal.valueOf(lastTs))
                .multiply(BigDecimal.valueOf(lastQs));
        return formatResult(number.toString());
    }

    @Override
    public String getAnswerPart1() {
        return "938065580";
    }

    @Override
    public String getAnswerPart2() {
        return "250628960065793";
    }
}
