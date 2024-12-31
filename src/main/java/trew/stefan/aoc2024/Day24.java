package trew.stefan.aoc2024;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;

@Slf4j
public class Day24 extends AbstractAOC {

    @ToString
    class Wire {
        String name;
        Integer value;
        Gate input;
        List<Gate> output = new ArrayList<>();

        Set<String> getInput() {
            var result = new HashSet<String>();
            if (input != null) {
                result.addAll(input.input1.getInput());
                result.addAll(input.input2.getInput());
            } else {

                result.add(name);
            }
            return result;
        }

        void reset() {
            if (name.startsWith("x") || name.startsWith("y")) {
                value = 0;
            } else {

                value = null;
            }
        }
    }

    @ToString
    class Gate {

        Wire input1;
        Wire input2;
        Wire output;
        boolean hasOutput = false;

        String operation;

        void reset() {
            hasOutput = false;
        }

        void compute() {
            var value1 = input1.value;
            var value2 = input2.value;
            if (value1 == null || value2 == null) {
                return;
            }
            hasOutput = true;

            switch (operation) {
                case "AND":
                    output.value = value1 & value2;
                    break;
                case "OR":
                    output.value = value1 | value2;
                    break;
                case "XOR":
                    output.value = (~value1 & value2) | (value1 & ~value2);
                    break;
                default:
                    output.value = value1;
            }

        }

    }

    @Override
    public String runPart1() {

        var list = getStringInput("");

        var gates = new ArrayList<Gate>();
        var wires = new HashMap<String, Wire>();

        for (var s : list) {
            if (s.isBlank()) {
                continue;
            }
            if (s.contains(":")) {
                var wire = new Wire();
                wire.name = s.split(":")[0].trim();
                wire.value = Integer.parseInt(s.split(":")[1].trim());
                wires.put(wire.name, wire);
            } else {
                var gate = new Gate();
                gate.input1 = getWire(s.split(" ")[0], wires);
                gate.operation = s.split(" ")[1];
                gate.input2 = getWire(s.split(" ")[2], wires);
                gate.output = getWire(s.split(" ")[4], wires);
                gates.add(gate);
            }
        }

        while (gates.stream().anyMatch(g -> !g.hasOutput)) {
            for (var gate : gates) {
                gate.compute();
            }
        }
        var parsed = getValue(wires, "z");
        return formatResult(parsed);
    }

    private long getValue(Map<String, Wire> wires, String pre) {
        var output = wires.values()
                .stream()
                .filter(w -> w.name.startsWith(pre))
                .sorted((w1, w2) -> w1.name.compareTo(w2.name))
                .reduce("", (s, w) -> w.value + s, String::concat);

        var parsed = Long.parseLong(output, 2);
        return parsed;
    }

    private Wire getWire(String s, HashMap<String, Wire> wires) {

        if (!wires.containsKey(s)) {
            var wire = new Wire();
            wire.name = s;
            wires.put(s, wire);
        }
        return wires.get(s);
    }

    @Override
    public String runPart2() {


        var list = getStringInput("");

        var gates = new ArrayList<Gate>();
        var wires = new HashMap<String, Wire>();

        for (var s : list) {
            if (s.isBlank()) {
                continue;
            }
            if (s.contains(":")) {
                var wire = new Wire();
                wire.name = s.split(":")[0].trim();
                wire.value = Integer.parseInt(s.split(":")[1].trim());
                wires.put(wire.name, wire);
            } else {
                var gate = new Gate();
                gate.input1 = getWire(s.split(" ")[0], wires);
                gate.operation = s.split(" ")[1];
                gate.input2 = getWire(s.split(" ")[2], wires);
                gate.output = getWire(s.split(" ")[4], wires);
                gate.input1.output.add(gate);
                gate.input2.output.add(gate);
                gate.output.input = gate;
                gates.add(gate);
            }
        }

        while (gates.stream().anyMatch(g -> !g.hasOutput)) {
            for (var gate : gates) {
                gate.compute();
            }
        }

        var z0 = wires.get("z00");
                wires.values()
                .stream()
                .filter(w -> w.name.startsWith("z"))
                .sorted((w1, w2) -> w1.name.compareTo(w2.name))
                .forEachOrdered(w -> log.info("{}: {}", w.name, w.getInput()));


        var xValue = getValue(wires, "x");
        var yValue = getValue(wires, "y");
        var zValue = getValue(wires, "z");
//        log.info("x: {} y: {} z: {}", xValue, yValue, zValue);
//        var o = xValue + yValue;
//        log.info("x + y: {} y z: {}", o, zValue);
//        log.info("{} {}", o ^ zValue, Long.toBinaryString(o ^ zValue));
//        var result = new HashSet<String>();
//        result.addAll(compute(35184372088831L, 35184372088831L, wires, gates));
//        result.addAll(compute(0L, 35184372088831L, wires, gates));
//        result.addAll(compute(35184372088831L, 0L, wires, gates));
//        result.addAll(compute(15184372088831L, 0L, wires, gates));
//        result.addAll(compute(15184372088831L, 1241257987597L, wires, gates));
//        result.addAll(compute(1241257987597L,15184372088831L, wires, gates));
//        result.addAll(compute(1241257987597L,15184372088831L, wires, gates));
//        result.addAll(compute(1241257987597L,151843720L, wires, gates));
//        result.addAll(compute(2897435L,151843720L, wires, gates));
//
//        log.info("Result: {}", result.size());

        return formatResult(zValue);
    }

    Set<String> compute(long x, long y, Map<String, Wire> wires, List<Gate> gates) {
        wires.forEach((k, v) -> v.reset());
        gates.forEach(Gate::reset);

        setInput(wires, x, "x");
        setInput(wires, y, "y");


        while (gates.stream().anyMatch(g -> !g.hasOutput)) {
            for (var gate : gates) {
                gate.compute();
            }
        }

        var value = getValue(wires, "z");


        //        log.info("x: {} y: {} z: {}", xValue, yValue, zValue);
        var o = x + y;
        if (o != value) {
            log.info("=====================================");
            log.info("x: {} y: {} z: {}", x, y, value);
            log.info("x + y: {} z: {}", o, value);
            return compareOutput(wires, o, "z");
        }
//        log.info("{} {}", o ^ zValue, Long.toBinaryString(o ^ zValue));

        return new HashSet<String>();
    }

    private static void setInput(Map<String, Wire> wires, long value, String prefix) {
        var str = Long.toBinaryString(value);
        for (var i = str.length() - 1; i >= 0; i--) {
            var other = (str.length() - 1 - i);
            var index = other < 10 ? "0" + other : "" + other;
            var key = prefix + index;
            if (!wires.containsKey(key)) {
                log.info("Missing key: {}", key);
            }
            wires.get(key).value = str.toCharArray()[i] == '1' ? 1 : 0;
        }
    }

    private Set<String> compareOutput(Map<String, Wire> wires, long value, String prefix) {
        var result = new HashSet<String>();
        var str = Long.toBinaryString(value);
        for (var i = str.length() - 1; i >= 0; i--) {
            var other = (str.length() - 1 - i);
            var index = other < 10 ? "0" + other : "" + other;
            var key = prefix + index;
            if (!wires.containsKey(key)) {
                log.info("Missing key: {}", key);
            }
            var valid = wires.get(key).value == (str.toCharArray()[i] == '1' ? 1 : 0);
            if (!valid) {
                result.add(key);
                log.info("Invalid key: {} expected: {} actual: {}", key, str.toCharArray()[i], wires.get(key).value);
            }
        }
        return result;
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
