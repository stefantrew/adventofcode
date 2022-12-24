package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Direction;
import trew.stefan.utils.Matrix;
import trew.stefan.utils.RCPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static trew.stefan.utils.Direction.*;
import static trew.stefan.utils.Direction.WEST;

@Slf4j
public class Day23 extends AbstractAOC {


    class Elf {

        RCPoint point;
        RCPoint move;

        public Elf(RCPoint point) {
            this.point = point;
        }
    }

    @Override
    public String runPart1() {

        var order = new ArrayList<Direction>();

        order.add(NORTH);
        order.add(SOUTH);
        order.add(WEST);
        order.add(EAST);


        var orderIndex = 0;


        ArrayList<Elf> elves = getElves();

        ArrayList<Direction> compass = getDirections();

        HashMap<Direction, List<Direction>> directionSets = getDirectionListHashMap();

        printElves(elves);
        for (int i = 0; i < 10; i++) {
            doMove(order, orderIndex + i, elves, compass, directionSets);

        }

        return formatResult(printElves(elves));
    }

    private static ArrayList<Direction> getDirections() {
        var compass = new ArrayList<Direction>();
        compass.add(NORTH);
        compass.add(SOUTH);
        compass.add(EAST);
        compass.add(WEST);
        compass.add(NORTH_EAST);
        compass.add(NORTH_WEST);
        compass.add(SOUTH_EAST);
        compass.add(SOUTH_WEST);
        return compass;
    }

    private static HashMap<Direction, List<Direction>> getDirectionListHashMap() {
        var east = new ArrayList<Direction>();
        east.add(EAST);
        east.add(NORTH_EAST);
        east.add(SOUTH_EAST);

        var west = new ArrayList<Direction>();
        west.add(WEST);
        west.add(SOUTH_WEST);
        west.add(NORTH_WEST);

        var north = new ArrayList<Direction>();
        north.add(NORTH);
        north.add(NORTH_EAST);
        north.add(NORTH_WEST);

        var south = new ArrayList<Direction>();
        south.add(SOUTH);
        south.add(SOUTH_EAST);
        south.add(SOUTH_WEST);

        var directionSets = new HashMap<Direction, List<Direction>>();
        directionSets.put(EAST, east);
        directionSets.put(NORTH, north);
        directionSets.put(SOUTH, south);
        directionSets.put(WEST, west);
        return directionSets;
    }

    private ArrayList<Elf> getElves() {
        var list = getStringInput("");
        var elves = new ArrayList<Elf>();
        for (int row = 0; row < list.size(); row++) {
            String s = list.get(row);
            for (int col = 0; col < s.toCharArray().length; col++) {
                var c = s.charAt(col);

                if (c == '#') {
                    var point = new RCPoint(row, col);
                    var elf = new Elf(point);

                    elves.add(elf);
                }
            }
        }
        return elves;
    }

    private int printElves(ArrayList<Elf> elves) {
        var rowMin = elves.get(0).point.getRow();
        var rowMax = elves.get(0).point.getRow();
        var colMin = elves.get(0).point.getCol();
        var colMax = elves.get(0).point.getCol();

        for (Elf elf : elves) {
            rowMin = Math.min(rowMin, elf.point.getRow());
            rowMax = Math.max(rowMax, elf.point.getRow());
            colMin = Math.min(colMin, elf.point.getCol());
            colMax = Math.max(colMax, elf.point.getCol());
        }

        var matrix = new Matrix<Character>(rowMax - rowMin + 1, colMax - colMin + 1, Character.class, '.');
        for (Elf elf : elves) {
            var row = elf.point.getRow() - rowMin;
            var col = elf.point.getCol() - colMin;
            matrix.set(row, col, 'E');
        }

        return matrix.count(character -> character == '.');
    }

    private boolean doMove(ArrayList<Direction> order, int orderIndex, ArrayList<Elf> elves, ArrayList<Direction> compass, HashMap<Direction, List<Direction>> directionSets) {
        Map<RCPoint, Elf> map = buildMap(elves);
        var moveCount = new HashMap<RCPoint, Integer>();
        for (Elf elf : elves) {

            if (!hasProximity(elf, map, compass)) {
                continue;
            }

            for (int i = 0; i < 4; i++) {
                var direction = order.get((orderIndex + i) % 4);
                var hasProximity = hasProximity(elf, map, directionSets.get(direction));
                if (!hasProximity) {

                    var move = elf.point.move(direction);
                    elf.move = move;
                    if (moveCount.containsKey(move)) {
                        moveCount.compute(move, (rcPoint, count) -> count == null ? 1 : count + 1);
                    } else {
                        moveCount.put(move, 1);
                    }
                    break;
                }
            }

        }

        var hasMoved = false;
        for (Elf elf : elves) {

            if (elf.move != null && moveCount.get(elf.move) == 1) {
                elf.point = elf.move;
                hasMoved = true;
            }


        }
        return hasMoved;
    }

    private Map<RCPoint, Elf> buildMap(ArrayList<Elf> elves) {
        var map = new HashMap<RCPoint, Elf>();
        for (Elf elf : elves) {

            map.put(elf.point, elf);
            elf.move = null;
        }

        return map;
    }

    private boolean hasProximity(Elf elf, Map<RCPoint, Elf> map, List<Direction> directions) {


        for (Direction direction : directions) {
            var point = elf.point.move(direction);
            if (map.containsKey(point)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String runPart2() {


        var order = new ArrayList<Direction>();

        order.add(NORTH);
        order.add(SOUTH);
        order.add(WEST);
        order.add(EAST);

        ArrayList<Elf> elves = getElves();

        ArrayList<Direction> compass = getDirections();

        HashMap<Direction, List<Direction>> directionSets = getDirectionListHashMap();
        var i = 0;
        while (true) {
            if (!doMove(order, i++, elves, compass, directionSets)) break;
        }

        return formatResult(i);
    }

    @Override
    public String getAnswerPart1() {
        return "4005";
    }

    @Override
    public String getAnswerPart2() {
        return "1008";
    }
}
