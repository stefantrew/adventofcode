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
    int count = 0;

    @Override
    public String runPart1() {


        return run(false);
    }

    private String run(boolean allowDuplicates) {
        var list = getStringInput("");
        nodes = new HashMap<>();
        count = 0;
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

        return formatResult(count);
    }

    private void getRoutes(Node startNode, NodePath path, boolean allowDuplicates) {


        for (Node sibling : startNode.siblings) {


            if (sibling.isStart) {
                continue;
            }
            if (sibling.isEnd) {
                count++;
                continue;
            }
            if (path.canAddNode(sibling, allowDuplicates)) {
                var temp = new NodePath(path);


                getRoutes(sibling, temp.addNode(sibling), allowDuplicates);
            }
        }

    }

    @NoArgsConstructor
    class NodePath {

        HashSet<Node> smalls = new HashSet<>();
        boolean hasSecondSmall = false;

        public NodePath(NodePath temp) {
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
            return this;
        }


    }

    @AllArgsConstructor
    class Node {

        String label;
        Set<Node> siblings = new HashSet<>();
        boolean isSmallCave;
        boolean isStart;
        boolean isEnd;

        public Node(String label) {
            this.label = label;
            isStart = label.equals("start");
            isEnd = label.equals("end");
            isSmallCave = label.toLowerCase().equals(label) && !isStart && !isEnd;
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
