package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day12 extends AbstractAOC {


    Map<String, Node> nodes = new HashMap<>();
    Set<String> routes = new HashSet<>();

    @Override
    public String runPart1() {


        return run(false);
    }

    private String run(boolean allowDuplicates) {
        var list = getStringInput("");
        nodes = new HashMap<>();
        routes = new HashSet<>();
        Node startNode = null;

        for (var s : list) {
            var p = Pattern.compile("(\\w*)-(\\w*)");
            var m = new AOCMatcher(p.matcher(s));

            if (m.find()) {
                m.print();
                var start = nodes.getOrDefault(m.getString(1), new Node(m.getString(1)));
                var end = nodes.getOrDefault(m.getString(2), new Node(m.getString(2)));

                start.siblings.add(end);
                end.siblings.add(start);

                nodes.put(start.label, start);
                nodes.put(end.label, end);

                if (start.label.equals("start")) {
                    startNode = start;
                }


            }
        }


        getRoutes(startNode, new ArrayList<>(), allowDuplicates);

        return formatResult(routes.size());
    }

    private void getRoutes(Node startNode, List<Node> path, boolean allowDuplicates) {

        if (startNode.isEnd()) {
            routes.add(path.toString());
            return;
        }

        if (startNode.isStart() && !path.isEmpty()) {
            return;
        }

        for (Node sibling : startNode.siblings) {


            if (sibling.isSmallCave && path.contains(sibling)) {

                if (allowDuplicates) {

                    var flag = false;

                    var smalls = path.stream().filter(node -> node.isSmallCave).collect(Collectors.toList());
                    var set = new HashSet<Node>();
                    for (Node small : smalls) {
                        if (set.contains(small)) {
                            flag = true;
                            break;
                        }

                        set.add(small);
                    }
                    if (flag) {
                        continue;
                    }
                } else {
                    continue;
                }
            }

            var temp = new ArrayList<Node>(path);
            temp.add(sibling);
            getRoutes(sibling, temp, allowDuplicates);
        }

    }


    @AllArgsConstructor
    class Node {

        String label;
        Set<Node> siblings = new HashSet<>();
        boolean isSmallCave;

        public Node(String label) {
            this.label = label;
            isSmallCave = label.toLowerCase().equals(label);
        }

        boolean isStart() {
            return label.equals("start");
        }

        boolean isEnd() {
            return label.equals("end");
        }

        @Override
        public String toString() {
            return label;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(label, node.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(label);
        }
    }


    @Override
    public String runPart2() {


        return run(true);
    }

    @Override
    public String getAnswerPart1() {
        return "3369";
    }

    @Override
    public String getAnswerPart2() {
        return "85883";
    }
}
