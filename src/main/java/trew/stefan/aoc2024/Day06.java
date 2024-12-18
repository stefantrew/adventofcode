package trew.stefan.aoc2024;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


@Slf4j
public class Day06 extends AbstractAOC {


    enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST;

        public Direction getNextDirection() {
            return switch (this) {
                case NORTH -> EAST;
                case SOUTH -> WEST;
                case EAST -> SOUTH;
                case WEST -> NORTH;
            };
        }
    }

    @Override
    public String runPart1() {

        var list = getStringInput("");


        var map = new Matrix<Character>(list, Character.class, '.');

        var visited = new HashSet<RCPoint>();

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> (char) c)
                    .toList());

        }

        var direction = Direction.NORTH;


        getRoute(visited, map);


        return String.valueOf(visited.size());
    }

    private boolean getRoute(HashSet<RCPoint> visited, Matrix<Character> map) {
        var direction = Direction.NORTH;

        var current = map.find('^').get(0);
        map.set(current, '.');
        var count = 0;

        visited.add(current.getRcPoint());
        while (true) {
            try {
                var next = switch (direction) {
                    case NORTH -> map.getPoint(current.getRow() - 1, current.getCol());
                    case SOUTH -> map.getPoint(current.getRow() + 1, current.getCol());
                    case EAST -> map.getPoint(current.getRow(), current.getCol() + 1);
                    case WEST -> map.getPoint(current.getRow(), current.getCol() - 1);
                };

                if (next.getValue().equals('#')) {
                    direction = direction.getNextDirection();
                } else {
                    current = next;
                    visited.add(current.getRcPoint());
                }

                if (count++ == 7000) {
                    visited.clear();
                    return true;
                }


            } catch (Exception e) {
                break;
            }

        }
        return false;
    }


    @Override
    public String runPart2() {

        var list = getStringInput("");


        var map = new Matrix<Character>(list, Character.class, '.');

        var visited = new HashSet<RCPoint>();

        for (var i = 0; i < list.size(); i++) {
            map.setRow(i, list.get(i).chars().mapToObj(c -> (char) c)
                    .toList());

        }
        var count = 0;
        getRoute(visited, map.copy());
        for (var rcPoint : visited) {
            var result = testObstacle(map, rcPoint);
            if (result) {
                count++;
            }
        }


        return String.valueOf(count);
    }

    private boolean testObstacle(Matrix<Character> map, RCPoint point) {
        var test = map.copy();
        if (test.get(point).equals('^')) {
            return false;
        }
        if (test.get(point.getRow() - 1, point.getCol()).equals('^')) {
            return false;
        }
        test.set(point, '#');

        return getRoute(new HashSet<>(), test);
    }

    @Override
    public String getAnswerPart1() {
        return "5239";
    }

    @Override
    public String getAnswerPart2() {
        return "1753";
    }
}
