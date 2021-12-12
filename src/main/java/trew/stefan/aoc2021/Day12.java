package trew.stefan.aoc2021;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day12 extends AbstractAOC {


    Map<String, Node> nodes = new HashMap<>();
    List<String> routes = new ArrayList<>();

    @Override
    public String runPart1() {


        return run(false);
    }

    private String run(boolean allowDuplicates) {
        var list = getStringInput("");
        nodes = new HashMap<>();
        routes = new ArrayList<>();
        Node startNode = new Node("start");

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

        getRoutes(startNode, new NodePath(), allowDuplicates);

        return formatResult(routes.size());
    }

    private void getRoutes(Node startNode, NodePath path, boolean allowDuplicates) {

        if (startNode.isEnd()) {
            routes.add(path.toString());
            return;
        }

        if (startNode.isStart() && !path.isEmpty()) {
            return;
        }

        for (Node sibling : startNode.siblings) {


            if (path.canAddNode(sibling, allowDuplicates)) {
                var temp = new NodePath(path);


                getRoutes(sibling, temp.addNode(sibling), allowDuplicates);
            }
        }

    }

    @NoArgsConstructor
    class NodePath {

        List<Node> path = new ArrayList<>();
        HashSet<Node> smalls = new HashSet<>();
        boolean hasSecondSmall = false;

        public NodePath(NodePath temp) {
            this.path.addAll(temp.path);
            this.smalls.addAll(temp.smalls);
            this.hasSecondSmall = temp.hasSecondSmall;
        }

        boolean canAddNode(Node node, boolean allowDuplicates) {
            if (node.isSmallCave) {
                if (!smalls.contains(node)) {
                    return true;
                }
                return allowDuplicates ? !hasSecondSmall : !smalls.contains(node);
            }

            return true;

        }

        NodePath addNode(Node node) {


            if (node.isSmallCave) {

                if (smalls.contains(node)) {
                    hasSecondSmall = true;
                }
                smalls.add(node);
            }
            path.add(node);
            return this;
        }

        @Override
        public String toString() {
            return path.toString();
        }

        public boolean isEmpty() {
            return path.isEmpty();
        }
    }

    @AllArgsConstructor
    class Node {

        String label;
        Set<Node> siblings = new HashSet<>();
        boolean isSmallCave;

        public Node(String label) {
            this.label = label;
            isSmallCave = label.toLowerCase().equals(label) && !isStart() && !isEnd();
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
