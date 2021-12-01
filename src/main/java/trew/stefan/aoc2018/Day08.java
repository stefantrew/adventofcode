package trew.stefan.aoc2018;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day08 extends AbstractAOC {

    class Node {

        int numChild;
        int numMeta;

        List<Node> childNodes = new ArrayList<>();
        List<Integer> meta = new ArrayList<>();

        public int sumMeta() {

            int total = childNodes.stream().mapToInt(Node::sumMeta).sum();
            total += meta.stream().mapToInt(value -> value).sum();

            return total;
        }

        public int sumMeta2() {

            if (childNodes.isEmpty()) {
                int sum = meta.stream().mapToInt(value -> value).sum();
                return sum;

            }
            int total = 0;

            for (Integer index : meta) {
                index--;
                if (childNodes.size() <= index) {
                    continue;
                }
                total += childNodes.get(index).sumMeta2();

            }

            return total;
        }
    }

    @Override
    public String runPart1() {


        Node node = buildRootNode();

        return String.valueOf(node.sumMeta());
    }

    private Node buildRootNode() {
        var list = getStringInput();
        var data = Arrays.stream(list.get(0).split(" ")).map(Integer::parseInt).collect(Collectors.toList());

        var node = processNode(new LinkedList<>(data));
        return node;
    }

    private Node processNode(LinkedList<Integer> data) {
        var node = new Node();
        node.numChild = data.poll();
        node.numMeta = data.poll();

        for (int i = 0; i < node.numChild; i++) {
            node.childNodes.add(processNode(data));
        }

        for (int i = 0; i < node.numMeta; i++) {
            node.meta.add(data.poll());
        }
        return node;
    }

    @Override
    public String runPart2() {


        Node node = buildRootNode();

        return String.valueOf(node.sumMeta2());

    }

    @Override
    public String getAnswerPart1() {
        return "45865";
    }

    @Override
    public String getAnswerPart2() {
        return "22608";
    }
}
