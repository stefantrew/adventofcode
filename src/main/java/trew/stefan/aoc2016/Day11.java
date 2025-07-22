package trew.stefan.aoc2016;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Day11 extends AbstractAOC {

    static class State {
        int elevator = 0;
        int steps = 0;
        int previous = 0;

        HashMap<Integer, Set<String>> floors = new HashMap<>();

        public State(int elevator, HashMap<Integer, Set<String>> floors) {
            this.elevator = elevator;
            this.floors = floors;
            this.elevator = previous;
        }

        public State(State state) {
            this.elevator = state.elevator;
            this.previous = state.previous;
            this.steps = state.steps;
            for (var integerSetEntry : state.floors.entrySet()) {
                var temp = new HashSet<>(integerSetEntry.getValue());
                floors.put(integerSetEntry.getKey(), temp);
            }
        }

        public void print(List<String> allItems) {

            for (int i = 3; i >= 0; i--) {
                var sb = new StringBuilder();
                sb.append("F" + (i + 1) + " ");
                if (elevator == i) {
                    sb.append("E  ");
                } else {
                    sb.append(".  ");
                }
                for (var i1 = 0; i1 < allItems.size(); i1++) {

                    if (floors.get(i).contains(allItems.get(i1))) {
                        sb.append(allItems.get(i1) + " ");
                    } else {
                        sb.append(".  ");
                    }
                }
                log.info(sb.toString());
            }
        }

        public List<State> getMoves() {

            var result = new ArrayList<State>();
            var currentFloor = floors.get(elevator);
            for (int i = 1; i  > -2; i--) {

                if (elevator + i < 0 || elevator + i > 3 || i == 0) {
                    continue;
                }

                for (var item : currentFloor) {
                    var newState = new State(this);
                    newState.elevator += i;
                    newState.steps++;
                    newState.floors.get(elevator).remove(item);
                    newState.floors.get(elevator + i).add(item);

                    if (newState.isValid()) {
                        result.add(newState);
                    }

                    for (var item2 : this.floors.get(elevator)) {

                        if (item.equals(item2)) {
                            continue;
                        }
                        newState = new State(this);
                        newState.elevator += i;
                        newState.steps++;
                        newState.floors.get(elevator).remove(item);
                        newState.floors.get(elevator).remove(item2);
                        newState.floors.get(elevator + i).add(item);
                        newState.floors.get(elevator + i).add(item2);
                        if (newState.isValid()) {
                            result.add(newState);
                        }
                    }

                }

            }
            return result;
        }

        public boolean isFinal() {
            return floors.get(0).isEmpty() && floors.get(1).isEmpty() && floors.get(2).isEmpty();
        }

        public boolean isValid() {
            for (var integerSetEntry : floors.entrySet()) {
                var chips = integerSetEntry.getValue().stream().filter(s -> s.endsWith("M")).collect(Collectors.toSet());
                var generators = integerSetEntry.getValue().stream().filter(s -> s.endsWith("G")).collect(Collectors.toSet());

                if (chips.size() <= 1) {
                    continue;
                }
                for (var chip : chips) {
                    if (!generators.contains(chip.replace("M", "G"))) {
                        return false;
                    }
                }

            }
            return true;
        }

        public boolean equals(Object obj) {
            if (obj instanceof State) {
                State other = (State) obj;
                if (elevator != other.elevator) {
                    return false;
                }
                if (previous != other.previous) {
                    return false;
                }
                for (int i = 0; i < floors.size(); i++) {
                    if (!floors.get(i).equals(other.floors.get(i))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }

        private Integer hash = null;

        public int hashCode() {

            if (hash != null) {
                return hash;
            }
            int result = elevator;
            for (int i = 0; i < floors.size(); i++) {
                result = 31 * result + floors.get(i).hashCode();
            }
            hash = result;
//            result = 31 * result + previous;
            return result;
        }
    }


    @Override
    public String runPart1() {

        var total = 0;
        var result = "";

        var floors = new HashMap<Integer, Set<String>>();
//        floors.put(0, new HashSet<>(Set.of("HM", "LM")));
//        floors.put(1, new HashSet<>(Set.of("HG")));
//        floors.put(2, new HashSet<>(Set.of("LG")));
//        floors.put(3, new HashSet<>());
        //, "CG", "CM"
        // "TG", "TM",
        floors.put(0, new HashSet<>(Set.of("PG",  "KG", "RG", "RM","CG", "CM", "TG", "TM")));
        floors.put(1, new HashSet<>(Set.of("PM", "KM")));
        floors.put(2, new HashSet<>());
        floors.put(3, new HashSet<>());

        var state = new State(0, floors);
//        state.print(List.of("HM", "LM", "HG", "LG"));
        var pg = new ArrayList<String>();
        floors.entrySet().stream().flatMap(e -> e.getValue().stream()).forEach(pg::add);
        state.print(pg);

        var visited = new HashSet<State>();
        var queue = new LinkedList<State>();
        queue.add(state);
        visited.add(state);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            if (current.isFinal()) {
                result = current.steps + "";
                break;
            }

            if (current.steps == 16 || visited.size() % 100 == 0) {
                log.info("===================================");
                log.info("Visited: {}", visited.size());
                current.print(pg);
//                break;
            }
            visited.add(current);
            var moves = current.getMoves();
            for (var move : moves) {
                if (!visited.contains(move)) {
                    queue.add(move);
                }
            }
        }

//        var moves = state.getMoves();
//        for (var move : moves) {
//            log.info("==========================");
//            move.print();
//        }
//        F4 .  .  .  .  .
//        F3 .  .  .  LG .
//        F2 .  HG .  .  .
//        F1 E  .  HM .  LM

//        var list = getStringInput().stream().map(this::mapper).toList();

        var list = getStringInput();
//        var list = getLongInput();
//        var list = getIntegerInput();
//        var list = getDoubleInput();

//        for (var s : list) {
//            log.info("{}", s);
//        }


        return formatResult(result);
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
