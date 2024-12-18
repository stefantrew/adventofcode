package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.stream.Collectors;


@Slf4j
public class Day08 extends AbstractAOC {


    @Override
    public String runPart1() {

        var list = getStringInput("");

        var map = new Matrix<>(list, Character.class, '.');
        var map2 = new Matrix<>(list, Character.class, '.');

        var charSet = new HashSet<Character>();

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> (char) c)
                    .toList());


            charSet.addAll(list.get(i).chars().mapToObj(c -> (char) c)
                    .collect(Collectors.toSet()));
        }

        charSet.remove('.');
        for (var c : charSet) {
            log.info("{}", c);
            var nodes = map.find(c);
            for (int i = 0; i < nodes.size(); i++) {
                var node = nodes.get(i);
                for (int j = i + 1; j < nodes.size(); j++) {
                    var node2 = nodes.get(j);


                    var xDiff = node.getCol() - node2.getCol();
                    var yDiff = node.getRow() - node2.getRow();
                    var pointA = new RCPoint(node.getRow() + yDiff, node.getCol() + xDiff);
                    var pointB = new RCPoint(node2.getRow() - yDiff, node2.getCol() - xDiff);

                    if (pointA.getRow() >= 0 && pointA.getRow() < map.getHeight() && pointA.getCol() >= 0 && pointA.getCol() < map.getWidth()) {
                        map2.set(pointA, '#');
                    }
                    if (pointB.getRow() >= 0 && pointB.getRow() < map.getHeight() && pointB.getCol() >= 0 && pointB.getCol() < map.getWidth()) {
                        map2.set(pointB, '#');
                    }

                }

                log.info("{}", node);
            }
            map2.printMatrix(false);
        }


        log.info("{}", charSet);
        return String.valueOf(map2.find('#').size());
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");

        var map = new Matrix<>(list, Character.class, '.');
        var map2 = new Matrix<>(list, Character.class, '.');

        var charSet = new HashSet<Character>();

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> (char) c)
                    .toList());


            charSet.addAll(list.get(i).chars().mapToObj(c -> (char) c)
                    .collect(Collectors.toSet()));
        }

        charSet.remove('.');
        for (var c : charSet) {
            log.info("{}", c);
            var nodes = map.find(c);
            for (int i = 0; i < nodes.size(); i++) {
                var node = nodes.get(i);
                for (int j = i + 1; j < nodes.size(); j++) {
                    var node2 = nodes.get(j);
                    var xDiff = node.getCol() - node2.getCol();
                    var yDiff = node.getRow() - node2.getRow();

                    for (int k = 0; k < 55; k++) {

                        var pointA = new RCPoint(node.getRow() + yDiff * k, node.getCol() + xDiff * k);
                        var pointB = new RCPoint(node2.getRow() - yDiff * k, node2.getCol() - xDiff * k);

                        if (pointA.getRow() >= 0 && pointA.getRow() < map.getHeight() && pointA.getCol() >= 0 && pointA.getCol() < map.getWidth()) {
                            map2.set(pointA, '#');
                        }
                        if (pointB.getRow() >= 0 && pointB.getRow() < map.getHeight() && pointB.getCol() >= 0 && pointB.getCol() < map.getWidth()) {
                            map2.set(pointB, '#');
                        }
                    }

                }

                log.info("{}", node);
            }
            map2.printMatrix(false);
        }


        log.info("{}", charSet);
        return String.valueOf(map2.find('#').size());
    }

    @Override
    public String getAnswerPart1() {
        return "249";
    }

    @Override
    public String getAnswerPart2() {
        return "905";
    }
}
