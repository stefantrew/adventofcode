package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.*;

@Slf4j
public class Day25 extends AbstractAOC {

    class Component {

        private String name;
        private Set<Component> connections = new HashSet<>();

        public Component(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name + " " + connections.size();
        }
    }

    private void removeConnection(Map<String, Component> map, String connection) {

        var left = map.get(connection.split(" ")[0]);
        var right = map.get(connection.split(" ")[1]);
        left.connections.remove(right);
        right.connections.remove(left);
    }

    private void addConnection(Map<String, Component> map, String connection) {

        var left = map.get(connection.split(" ")[0]);
        var right = map.get(connection.split(" ")[1]);
        left.connections.add(right);
        right.connections.add(left);
    }

    private Component getComponent(Map<String, Component> map, String name) {
        if (!map.containsKey(name)) {
            map.put(name, new Component(name));
        }

        return map.get(name);
    }

    @Override
    public String runPart1() {

        var map = new HashMap<String, Component>();
        var connections = new ArrayList<String>();

        var list = getStringInput("");

        for (var s : list) {
            var leftName = s.split(":")[0];
            var rights = s.split(": ")[1].split(" ");
            var left = getComponent(map, leftName);
            for (String rightName : rights) {

                connections.add(leftName + " " + rightName);
                var right = getComponent(map, rightName);

                left.connections.add(right);
                right.connections.add(left);
            }

//            log.info("{}", left);
        }

//        for (Map.Entry<String, Component> entry : map.entrySet()) {
//            log.info("{}", entry.getValue());
//        }
        //hfx/pzl, the wire between bvb/cmg, and the wire between nvd/jqt, you will
//        log.info("{}", connections);
// 3310
// 15:20
// 15:44
        // 1603
        var todo = new ArrayList<>(map.values());
        for (int i = 0; i < connections.size(); i++) {
            removeConnection(map, connections.get(i));

            for (int j = i + 1; j < connections.size(); j++) {
                log.info("I J {} {} ", i, j);
                removeConnection(map, connections.get(j));
                for (int k = j + 1; k < connections.size(); k++) {

                    removeConnection(map, connections.get(k));
                    int total = validate(todo.get(0), map.size());
                    if (total > 0) {
                        return formatResult(total);
                    }
                    addConnection(map, connections.get(k));
                }
                addConnection(map, connections.get(j));
            }
            addConnection(map, connections.get(i));
        }


        return "not found";
    }

    private int validate(Component item, int target) {
        var current = 0;


//            log.info("target {}: size {}", target, todo.size());
        var set = new HashSet<Component>();
        countElements(item, set);
        int setSize = set.size();

        if (setSize != target) {
            return setSize * (target - setSize);
        }

        return 0;
    }
    /*
    private int validate(List<Component> todo, int target) {
        var current = 0;


        while (!todo.isEmpty()) {
//            log.info("target {}: size {}", target, todo.size());
            var item = todo.get(0);
            todo.remove(0);
            var set = new HashSet<Component>();
            countElements(item, set, todo);

            int setSize = set.size();
            if (setSize != target) {
                log.info("setSize {}", setSize);
            }
//            log.info("{} {}", entry.getKey(), setSize);
            if (current > 0 && setSize != current && current + setSize != target) {
                return 0;
            }
            if (setSize == target) {
                return 0;
            } else if (current == 0) {
                current = setSize;
            } else if (current + setSize == target) {
//                log.info("{} {}", current, setSize);
                return current * setSize;
            }
        }

        return 0;
    }
     private void countElements(Component component, HashSet<Component> set, List<Component> todo) {
        if (set.contains(component)) {
            return;
        }
        todo.remove(component);
        set.add(component);

        for (Component connection : component.connections) {
            countElements(connection, set, todo);
        }

    }
     */

    private void countElements(Component component, HashSet<Component> set) {
        if (set.contains(component)) {
            return;
        }
        set.add(component);

        for (Component connection : component.connections) {
            countElements(connection, set);
        }

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
