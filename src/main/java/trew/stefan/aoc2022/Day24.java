package trew.stefan.aoc2022;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.AOCMatcher;
import trew.stefan.utils.Direction;
import trew.stefan.utils.RCPoint;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class Day24 extends AbstractAOC {


    class Gust {
        RCPoint point;

        Direction direction;

        public Gust(Direction direction, int row, int col) {
            this.direction = direction;
            point = new RCPoint(row, col);
        }

        public Gust(Gust gust) {
            direction = gust.direction;
            point = gust.point.clonePoint();
        }

        public void move() {
            point = point.move(direction);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Gust gust = (Gust) o;
            return Objects.equals(point, gust.point) && direction == gust.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(point, direction);
        }
    }

    class Blizzard {

        RCPoint point;
        List<Gust> gusts = new ArrayList<>();

        int height;

        int width;
        int dist = 1;

        boolean isRest = false;
        Direction lastMove = null;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Blizzard blizzard = (Blizzard) o;
            return Objects.equals(point, blizzard.point) && Objects.equals(gusts, blizzard.gusts);
        }

        @Override
        public int hashCode() {
            return Objects.hash(point, gusts, dist, lastMove);
        }

        Map<RCPoint, List<Gust>> getMap() {
            var map = new HashMap<RCPoint, List<Gust>>();

            for (Gust gust : gusts) {
                if (!map.containsKey(gust.point)) {
                    map.put(gust.point, new ArrayList<>());
                }
                map.get(gust.point).add(gust);
            }

            return map;
        }

        Set<RCPoint> getSet() {
            var map = new HashSet<RCPoint>();

            for (Gust gust : gusts) {
                map.add(gust.point);
            }

            return map;
        }

        public void moveGusts() {
            for (Gust gust : gusts) {
                gust.move();
                var pos = gust.point;
                if (pos.getCol() == 0) {
                    pos.setCol(width - 2);
                } else if (pos.getCol() == width - 1) {
                    pos.setCol(1);
                }

                if (pos.getRow() == 0) {
                    pos.setRow(height - 2);
                } else if (pos.getRow() == height - 1) {
                    pos.setRow(1);
                }
            }
        }

        public boolean isValid(RCPoint pos) {
            if (pos.getCol() == width - 2 && pos.getRow() == height - 1) {
                return true;
            }
            if (pos.getCol() == 1 && pos.getRow() == 0) {
                return true;
            }

            if (pos.getCol() <= 0 || pos.getCol() >= width - 1) {
                return false;
            }

            if (pos.getRow() <= 0 || pos.getRow() >= height - 1) {
                return false;
            }

            return true;
        }

        public Set<Direction> getMoves(Set<Direction> dirSet) {

            var moves = new HashSet<Direction>();

            var map = getSet();


            for (Direction direction : dirSet) {
                var temp = point.move(direction);
                if (isValid(temp) && !map.contains(temp)) {
                    moves.add(direction);
                }
            }

            return moves;
        }

        public boolean isEnd() {
            return point.getCol() == width - 2 && point.getRow() == height - 2;
        }

        public Blizzard doMove(Direction move) {
            var temp = new Blizzard();
            temp.height = height;
            temp.width = width;
            temp.point = point.move(move);
            for (Gust gust : gusts) {
                temp.gusts.add(new Gust(gust));

            }
            temp.dist = dist + 1;
            temp.isRest = move == Direction.NONE;
            temp.lastMove = move;

            return temp;
        }
    }

    @Override
    public String runPart1() {

        var total = 0;


        var list = getStringInput("");
        Blizzard blizzard = getBlizzard(list);

        printBlizzard(blizzard);


        var result = doMove(blizzard, 1, new HashSet<Integer>());

        return formatResult(result);
    }

    private Integer doMove(Blizzard blizzard, int i, HashSet<Integer> cache) {
        var dirSet = new HashSet<Direction>();
        dirSet.add(Direction.EAST);
        dirSet.add(Direction.SOUTH);
        dirSet.add(Direction.WEST);
        dirSet.add(Direction.NORTH);
        dirSet.add(Direction.NONE);
        var queue = new LinkedList<Blizzard>();

        var least = 344;
        blizzard.moveGusts();
        queue.add(blizzard);
        while (!queue.isEmpty()) {

            var current = queue.poll();


            if (current.isEnd()) {
                least = Math.min(least, current.dist);
                log.info("Least {}", least);
                continue;
            }

            if (current.dist > least) {
                log.info("doh");
                continue;
            }

            var hashCode = current.hashCode();
            if (cache.contains(hashCode)) {
                continue;
            }
            cache.add(hashCode);


            var moves = current.getMoves(dirSet);
            for (Direction move : moves) {

                var next = current.doMove(move);
                next.moveGusts();

                queue.add(next);

            }

        }
        log.info("cache count {}", cache.size());
        return least;
    }

    private Blizzard getBlizzard(List<String> list) {
        var blizzard = new Blizzard();
        blizzard.point = new RCPoint(0, 1);
        blizzard.height = list.size();
        blizzard.width = list.get(0).length();

        for (int row = 0; row < list.size(); row++) {
            String s = list.get(row);
            log.info("{}", s);
            for (int col = 0; col < s.toCharArray().length; col++) {
                var c = s.charAt(col);
                var direction = switch (c) {
                    case '<' -> Direction.LEFT;
                    case '>' -> Direction.RIGHT;
                    case '^' -> Direction.UP;
                    case 'v' -> Direction.DOWN;
                    default -> null;
                };
                if (direction != null) {
                    blizzard.gusts.add(new Gust(direction, row, col));
                }
            }
        }
        return blizzard;
    }

    private void printBlizzard(Blizzard blizzard) {
        var s = "";
        log.info(s);
        for (int col = 0; col < blizzard.width; col++) {
            s += col == 1 ? '.' : '#';
        }

        log.info(s);
        var map = blizzard.getMap();

        for (int row = 1; row < blizzard.height - 1; row++) {
            s = "";
            for (int col = 0; col < blizzard.width; col++) {
                var point = new RCPoint(row, col);
                if (col == 0 || col == blizzard.width - 1) {
                    s += '#';
                } else if (blizzard.point.equals(point)) {
                    s += 'E';
                } else {
                    if (!map.containsKey(point)) {
                        s += '.';

                    } else {
                        var list = map.get(point);

                        if (list.size() == 1) {
                            s += list.get(0).direction.toString();
                        } else {
                            s += list.size();
                        }
                    }

                }
            }

            log.info(s);

        }


        s = "";
        for (int col = 0; col < blizzard.width; col++) {
            s += col == blizzard.width - 2 ? '.' : '#';
        }
        log.info(s);
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
