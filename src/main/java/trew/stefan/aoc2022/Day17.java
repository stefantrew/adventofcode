package trew.stefan.aoc2022;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Slf4j
public class Day17 extends AbstractAOC {

    enum RockType {

        FLAT_ROCK,
        PLUS_ROCK,
        ANGLE_ROCK,
        TALL_ROCK,
        SQUARE_ROCK
    }

    class FlatRock extends Rock {

        public FlatRock(int row) {

            addPoint(row, 2, '-');
            addPoint(row, 3, '-');
            addPoint(row, 4, '-');
            addPoint(row, 5, '-');
        }
    }

    class PlusRock extends Rock {
        public PlusRock(int row) {

            addPoint(row, 3, '+');
            addPoint(row + 1, 3, '+');
            addPoint(row + 2, 3, '+');
            addPoint(row + 1, 2, '+');
            addPoint(row + 1, 4, '+');
        }
    }

    class AngleRock extends Rock {
        public AngleRock(int row) {

            addPoint(row, 2, 'L');
            addPoint(row, 3, 'L');
            addPoint(row, 4, 'L');
            addPoint(row + 1, 4, 'L');
            addPoint(row + 2, 4, 'L');
        }
    }


    class TallRock extends Rock {
        public TallRock(int row) {

            addPoint(row, 2, 'I');
            addPoint(row + 1, 2, 'I');
            addPoint(row + 2, 2, 'I');
            addPoint(row + 3, 2, 'I');
        }
    }

    class SquareRock extends Rock {
        public SquareRock(int row) {

            addPoint(row, 2, '=');
            addPoint(row + 1, 2, '=');
            addPoint(row, 3, '=');
            addPoint(row + 1, 3, '=');
        }
    }

    class Point {
        int row;
        int col;

        char symbol;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Point(int row, int col, char symbol) {
            this.row = row;
            this.col = col;
            this.symbol = symbol;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    abstract class Rock {

        protected Set<Point> points = new HashSet<>();

        int highest = 0;
        int lowest = 0;

        public Set<Point> getPoints() {
            return points;
        }

        public Point getPoint(int row, int col) {
            for (Point point : points) {
                if (point.row == row && point.col == col) {
                    return point;
                }
            }
            return null;
        }


        protected void addPoint(int row, int col, char symbol) {

            points.add(new Point(row, col, symbol));
            highest = Math.max(row, highest);
            if (lowest == 0) {
                lowest = row;
            }
            lowest = Math.min(row, lowest);
        }

        public void compute() {
            lowest = 0;
            highest = 0;
            for (Point point : points) {
                highest = Math.max(point.row, highest);
                if (lowest == 0) {
                    lowest = point.row;
                }
                lowest = Math.min(point.row, lowest);
            }
        }

        public void move(char charAt) {
            var delta = charAt == '<' ? -1 : 1;
            for (Point point : points) {
                point.col += delta;
            }
            compute();
        }

        public void drop() {
            for (Point point : points) {
                point.row--;
            }
            compute();

        }

        public boolean canMove(Set<Point> staticPoints, char charAt) {
            var delta = charAt == '<' ? -1 : 1;
            for (Point point : points) {

                var col = point.col + delta;
                if (col < 0 || col == 7) {
                    return false;
                }
                if (staticPoints.contains(new Point(point.row, col))) {
                    return false;
                }
            }

            return true;
        }

        public boolean canDrop(Set<Point> staticPoints) {
            for (Point point : points) {

                var row = point.row - 1;
                if (row < 0) {
                    return false;
                }
                if (staticPoints.contains(new Point(row, point.col))) {
                    return false;
                }
            }

            return true;
        }
    }

    Rock produceRock(RockType type, HashSet<Rock> rocks) {

        var highest = -1;
        for (var rock : rocks) {
            highest = Math.max(rock.highest, highest);
        }

        highest += 4;
//        log.info("Producing {} {} @ {}", rocks.size(), type, highest);
        var rock = switch (type) {

            case FLAT_ROCK -> new FlatRock(highest);
            case PLUS_ROCK -> new PlusRock(highest);
            case ANGLE_ROCK -> new AngleRock(highest);
            case TALL_ROCK -> new TallRock(highest);
            case SQUARE_ROCK -> new SquareRock(highest);
            default -> throw new IllegalArgumentException("Type needed");
        };
        rocks.add(rock);
        return rock;

    }

    public void printRock(Set<Rock> rocks) {

        var highest = 0;
        var points = new HashSet<Point>();
        for (Rock rock : rocks) {
            points.addAll(rock.getPoints());
            highest = Math.max(highest, rock.highest);
        }

        log.info("=======================");

        for (int row = highest + 5; row >= 0; row--) {
            var str = "";
            for (int col = 0; col < 7; col++) {

                Point point = null;
                for (Point refPoint : points) {
                    if (refPoint.row == row && refPoint.col == col) {
                        point = refPoint;
                        break;
                    }
                }


                str += point == null ? "." : point.symbol;
            }
            log.info(str + ' ' + row);
        }


    }

    @Override
    public String runPart1() {
        return getAnswer(2022);
    }

    private String getAnswer(long target) {
        var list = getStringInput("");
        var profile = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";
        profile = list.get(0);
        RockType[] types = {
                RockType.FLAT_ROCK,
                RockType.PLUS_ROCK,
                RockType.ANGLE_ROCK,
                RockType.TALL_ROCK,
                RockType.SQUARE_ROCK
        };
        var rocks = new HashSet<Rock>();


        var i = 0;
        var rock = produceRock(types[i], rocks);
        var staticPoints = new HashSet<Point>();

        printRock(rocks);
        var ventIndex = 0;
        while (true) {


            if (rock.canMove(staticPoints, profile.charAt(ventIndex))) {

                rock.move(profile.charAt(ventIndex));
            }
            if (!rock.canDrop(staticPoints)) {
                staticPoints.addAll(rock.points);
                if (rocks.size() == target) {
                    break;
                }
                i++;
                i = i % 5;
                rock = produceRock(types[i], rocks);
//                printRock(rocks);

            } else {

                rock.drop();
            }

            ventIndex++;
            ventIndex %= profile.length();
        }
//        printRock(rocks);
        return String.valueOf(rock.highest + 1);
    }


    @Override
    public String runPart2() {

        return getAnswer(1_000_000_000_000L);
    }

    @Override
    public String getAnswerPart1() {
        return " ";
    }

    @Override
    public String getAnswerPart2() {
        return " ";
    }
}
