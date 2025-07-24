package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Direction;

import java.util.*;

@Slf4j
public class Day17 extends AbstractAOC {

    public static class Move {

        Direction direction;
        long totalValue;
        int stepsInDirection;
        int x;
        int y;

        String history;

        public Move(Direction direction, long totalValue, int stepsInDirection, int x, int y, String history, int value) {
            this.direction = direction;
            this.totalValue = totalValue;
            this.stepsInDirection = stepsInDirection;
            this.x = x;
            this.y = y;
            this.history = history + ", " + direction.toString() + value;
        }

        public int getHash() {
            return Objects.hash(x + 1000000, y * 25789, stepsInDirection * 422, direction);
        }

    }

    public Move getUpMove(Move current, int[][] map, int min, int max) {

        var y = current.y;
        var x = current.x;

        var dir = current.direction;
        boolean inStepRange = current.stepsInDirection < max;
        boolean minStepRange = current.stepsInDirection >= min;

        if (dir == Direction.DOWN || y == 0) {
            return null;
        }

        if (dir == Direction.UP && !inStepRange) {
            return null;
        }

        if ((dir == Direction.LEFT || dir == Direction.RIGHT) && (!minStepRange || y - min < 0)) {
            return null;
        }

        y -= 1;
        var steps = dir == Direction.UP ? current.stepsInDirection + 1 : 1;

        return new Move(Direction.UP, current.totalValue + map[y][x], steps, x, y, current.history, map[y][x]);

    }

    public Move getDownMove(Move current, int[][] map, int min, int max) {

        var y = current.y;
        var x = current.x;

        var height = map.length;
        var dir = current.direction;
        boolean inStepRange = current.stepsInDirection < max;
        boolean minStepRange = current.stepsInDirection >= min;

        if (dir == Direction.UP || y + 1 >= height) {
            return null;
        }

        if (dir == Direction.DOWN && !inStepRange) {
            return null;
        }

        if ((dir == Direction.LEFT || dir == Direction.RIGHT) && (!minStepRange || y + min >= height)) {
            return null;
        }

        y += 1;
        var steps = dir == Direction.DOWN ? current.stepsInDirection + 1 : 1;

        return new Move(Direction.DOWN, current.totalValue + map[y][x], steps, x, y, current.history, map[y][x]);

    }

    public Move getRightMove(Move current, int[][] map, int min, int max) {

        var y = current.y;
        var x = current.x;

        var width = map[0].length;
        var dir = current.direction;
        boolean inStepRange = current.stepsInDirection < max;
        boolean minStepRange = current.stepsInDirection >= min;

        if (dir == Direction.LEFT || x + 1 >= width) {
            return null;
        }

        if (dir == Direction.RIGHT && !inStepRange) {
            return null;
        }

        if ((dir == Direction.UP || dir == Direction.DOWN) && (!minStepRange || x + min  >= width)) {
            return null;
        }

        x += 1;
        var steps = dir == Direction.RIGHT ? current.stepsInDirection + 1 : 1;

        return new Move(Direction.RIGHT, current.totalValue + map[y][x], steps, x, y, current.history, map[y][x]);

    }


    public Move getLeftMove(Move current, int[][] map, int min, int max) {

        var y = current.y;
        var x = current.x;

        var dir = current.direction;
        boolean inStepRange = current.stepsInDirection < max;
        boolean minStepRange = current.stepsInDirection >= min;

        if (dir == Direction.RIGHT || x == 0) {
            return null;
        }

        if (dir == Direction.LEFT && !inStepRange) {
            return null;
        }

        if ((dir == Direction.UP || dir == Direction.DOWN) && (!minStepRange || x - min < 0)) {
            return null;
        }

        x -= 1;
        var steps = dir == Direction.LEFT ? current.stepsInDirection + 1 : 1;

        return new Move(Direction.LEFT, current.totalValue + map[y][x], steps, x, y, current.history, map[y][x]);

    }

    public List<Move> getMoves(Move current, int[][] map, int min, int max) {
        var result = new ArrayList<Move>();

        var right = getRightMove(current, map, min, max);
        var up = getUpMove(current, map, min, max);
        var down = getDownMove(current, map, min, max);
        var left = getLeftMove(current, map, min, max);

        if (up != null) {
            result.add(up);
        }

        if (down != null) {
            result.add(down);
        }

        if (left != null) {
            result.add(left);
        }

        if (right != null) {
            result.add(right);
        }

        return result;

    }

    @Override
    public String runPart1() {

        var list = getStringInput("_sample");

        int[][] map = new int[list.size()][list.get(0).length()];

        var row = 0;
        for (var s : list) {
//            log.info("{}", s);
            for (int i = 0; i < s.length(); i++) {
                map[row][i] = Integer.parseInt(String.valueOf(s.charAt(i)));
            }
            row++;
        }

//        for (int[] ints : map) {
//            log.info("{} ", ints);
//        }

        var queue = new LinkedList<Move>();
        queue.add(new Move(Direction.RIGHT, map[0][1], 1, 1, 0, "", 0));
        queue.add(new Move(Direction.DOWN, map[1][0], 1, 0, 1, "", 0));

        var visited = new HashMap<Integer, Move>();
        var tx = map[0].length - 1;
        var ty = map.length - 1;
        var best = -1L;
//        while (!queue.isEmpty()) {
//
//            var move = queue.pop();
//            if (move.totalValue < 0) {
//                log.info("{} ", move.totalValue);
//            }
//
//            var visitedMove = visited.get(move.getHash());
//
//            if (visitedMove != null && visitedMove.totalValue <= move.totalValue) {
//                continue;
//            }
//
//            visited.put(move.getHash(), move);
//
//            if (move.x == tx && move.y == ty) {
//                log.info("{}", move.totalValue);
//                best = best == -1 ? move.totalValue : Math.min(best, move.totalValue);
//                continue;
//            }
//
//            var moves = getMoves(move, map, 1, 3);
//
//            queue.addAll(moves);
//
//        }

        return formatResult(best);
    }

    @Override
    public String runPart2() {


        var list = getStringInput("");

        int[][] map = new int[list.size()][list.get(0).length()];

        var row = 0;
        for (var s : list) {
            log.info("{}", s);
            for (int i = 0; i < s.length(); i++) {
                map[row][i] = Integer.parseInt(String.valueOf(s.charAt(i)));
            }
            row++;
        }

        for (int[] ints : map) {
            log.info("{} ", ints);
        }

        var queue = new LinkedList<Move>();
        queue.add(new Move(Direction.RIGHT, map[0][1], 1, 1, 0, "", 0));
        queue.add(new Move(Direction.DOWN, map[1][0], 1, 0, 1, "", 0));


        var visited = new HashMap<Integer, Move>();
        var tx = map[0].length - 1;
        var ty = map.length - 1;
        var best = -1L;
        var bestHistory = "";
        log.info("Target {},{}", tx, ty);
        while (!queue.isEmpty()) {

            var move = queue.pop();
            if (move.totalValue < 0) {
                log.info("{} ", move.totalValue);
            }

            var visitedMove = visited.get(move.getHash());

            if (visitedMove != null && visitedMove.totalValue <= move.totalValue) {
                continue;
            }

            visited.put(move.getHash(), move);

            if (move.x == tx && move.y == ty) {
                log.info("{}", move.totalValue);
                if (best == -1 || best > move.totalValue) {
                    best = move.totalValue;
                    bestHistory = move.history;
                }
                continue;
            }

            var moves = getMoves(move, map, 4, 10);

            queue.addAll(moves);

        }
        log.info("History: {}", bestHistory);
//989
        return formatResult(best);
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
