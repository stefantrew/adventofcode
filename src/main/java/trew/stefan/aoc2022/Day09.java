package trew.stefan.aoc2022;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Matrix;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Vector;


@Slf4j
public class Day09 extends AbstractAOC {
    @AllArgsConstructor
    class Point {
        int y;
        int x;

        @Override
        public String toString() {
            return "Point{" +
                   "y=" + y +
                   ", x=" + x +
                   '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return y == point.y && x == point.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }

        Point moveRight() {
            return new Point(y, x + 1);
        }

        Point moveDown() {
            return new Point(y + 1, x);
        }

        Point moveUp() {
            return new Point(y - 1, x);
        }

        Point moveLeft() {
            return new Point(y, x - 1);
        }

        int distance(Point ref) {
            return Math.max(Math.abs(ref.x - x), Math.abs(ref.y - y));
        }

    }

    @AllArgsConstructor
    class Point2 {
        int y;
        int x;

        String symbol;

        Point2 child;

        @Override
        public String toString() {
            return "Point{" +
                   "y=" + y +
                   ", x=" + x +
                   ", " + symbol +
                   '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return y == point.y && x == point.x;
        }

        @Override
        public int hashCode() {
            return Objects.hash(y, x);
        }

        Point2 moveRight() {
            return new Point2(y, x + 1, symbol, child);

        }

        Point2 moveDown() {
            return new Point2(y + 1, x, symbol, child);

        }

        Point2 moveUp() {
            return new Point2(y - 1, x, symbol, child);

        }

        Point2 moveLeft() {
            return new Point2(y, x - 1, symbol, child);

        }

        int distance(Point2 ref) {
            return Math.max(Math.abs(ref.x - x), Math.abs(ref.y - y));
        }

        public void moveTail() {
            if (child == null) {
                return;
            }
            if (distance(child) > 1) {

                if (x > child.x) {
                    child.x++;
                } else if (x < child.x) {
                    child.x--;
                }
                if (y > child.y) {
                    child.y++;
                } else if (y < child.y) {
                    child.y--;
                }
            }
            child.moveTail();
        }

        private Point2 cloneChild() {
            return new Point2(y, x, symbol, child);
        }

        public Point getPoint() {
            return new Point(y, x);
        }
    }

    @Override
    public String runPart1() {
        var input = getStringInput("");


        var visited = new HashSet<Point>();
        var head = new Point(0, 0);
        var prev = head;
        Point tail = head;

        for (String s1 : input) {
            var items = s1.split(" ");
            var dist = Integer.valueOf(items[1]);
            while (dist-- > 0) {
                prev = head;

                switch (items[0]) {
                    case "R":
                        head = head.moveRight();
                        break;
                    case "U":
                        head = head.moveUp();
                        break;
                    case "D":
                        head = head.moveDown();
                        break;
                    case "L":
                        head = head.moveLeft();
                        break;
                }

                if (head.distance(tail) > 1) {
                    tail = prev;
                }

                visited.add(tail);
            }
        }
        return String.valueOf(visited.size());
    }

    @Override
    public String runPart2() {
        var input = getStringInput("");


        var visited = new HashSet<Point>();

        var tail = new Point2(0, 0, "9", null);
        var last = tail;
        for (int i = 8; i >= 0; i--) {
            var segment = new Point2(0, 0, String.valueOf(i), last);

            last = segment;
        }
        var head = last;
        head.symbol = "H";


        for (String s1 : input) {
            var items = s1.split(" ");
            var dist = Integer.valueOf(items[1]);
            while (dist-- > 0) {
                var prev = head;
                switch (items[0]) {
                    case "R":
                        head = head.moveRight();
                        break;
                    case "U":
                        head = head.moveUp();
                        break;
                    case "D":
                        head = head.moveDown();
                        break;
                    case "L":
                        head = head.moveLeft();
                        break;
                }

                head.moveTail();


                visited.add(tail.getPoint());
            }
        }

        return String.valueOf(visited.size());
    }


    @Override
    public String getAnswerPart1() {
        return "6503";
    }

    @Override
    public String getAnswerPart2() {
        return "2724";
    }
}
