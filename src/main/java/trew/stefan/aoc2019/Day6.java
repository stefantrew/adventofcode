package trew.stefan.aoc2019;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;

import java.util.*;

@Slf4j
public class Day6 implements Day {

    class Node {

        public String id;
        public List<Node> children = new ArrayList<>();
        public Node parent;

        Node(String id) {
            this.id = id;
        }

        List<String> getAncestors() {

            List<String> result;

            if (parent == null) {
                result = new ArrayList<>();
            } else {
                result = this.parent.getAncestors();

            }
            result.add(id);
            return result;
        }

        int getDistance() {
            if (parent == null) return 0;
            return 1 + parent.getDistance();
        }
    }

    Map<String, Node> allNodes = new HashMap<>();

    Node getNode(String id) {
        Node n = allNodes.getOrDefault(id, new Node(id));
        allNodes.put(id, n);
        return n;
    }

    public void run() {
        log.info("running");
        List<String> lines = InputReader2019.readStrings(6, "");


        for (String s : lines) {
            String[] parts = s.split("\\)");
            Node parent = getNode(parts[0]);
            Node child = getNode(parts[1]);

            child.parent = parent;
            parent.children.add(child);
        }
        log.info("{}", allNodes);
        int size = 0;
        for (Node n : allNodes.values()) {
            size += n.getDistance();
            log.info("{} {} ", n.id, n.getDistance());

        }

        Node you = getNode("YOU");
        Node santa = getNode("SAN");
        log.info("{}", size);

        List<String> youA = you.getAncestors();
        List<String> santaA = santa.getAncestors();
        log.info("{}", you.getAncestors());
        log.info("{}", santa.getAncestors());

        List<String> common = new ArrayList<>();
        for (String item : youA) {
            if (santaA.contains(item)) {
                common.add(item);
            }
        }

        int distance = youA.size() - common.size() * 2 + santaA.size();
        log.info("{}", distance -2);
//        List<String> lines = InputReader.readStrings(6, "");
//        List<Integer> numbers = lines.stream().map(Integer::parseInt).collect(Collectors.toList());

//        log.info("{} {} {}",,,);
//        log.info("{} {} {} {}",,,,);
//        log.info("{} {} {} {} {}",,,,);

    }
}
