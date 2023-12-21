package trew.stefan.aoc2023;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AbstractAOC;
import trew.stefan.utils.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Slf4j
public class Day16 extends AbstractAOC {


    class Beam {

        int row;
        int col;
        Direction direction;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Beam beam = (Beam) o;
            return row == beam.row && col == beam.col && direction == beam.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row * 1000000, col, direction.toString());
        }

        public Beam(int row, int col, Direction direction) {
            this.row = row;
            this.col = col;
            this.direction = direction;
        }

        public List<Beam> move() {
            var beams = new ArrayList<Beam>();
            switch (direction) {
                case EAST -> beams.add(new Beam(row, col + 1, direction));
                case WEST -> beams.add(new Beam(row, col - 1, direction));
                case NORTH -> beams.add(new Beam(row - 1, col, direction));
                case SOUTH -> beams.add(new Beam(row + 1, col, direction));
                case EAST_WEST -> {
                    beams.add(new Beam(row, col + 1, Direction.EAST));
                    beams.add(new Beam(row, col - 1, Direction.WEST));
                }
                case NORTH_SOUTH -> {
                    beams.add(new Beam(row - 1, col, Direction.NORTH));
                    beams.add(new Beam(row + 1, col, Direction.SOUTH));
                }
                default -> throw new RuntimeException("Unknown direction");
            }
            return beams;
        }
    }

    @Override
    public String runPart1() {


        var list = getStringInput("");
        var charMatrix = list.stream().map(s -> s.toCharArray()).toArray(char[][]::new);


        int total = getTotal(charMatrix, new Beam(0, -1, Direction.EAST));

        return formatResult(total);
    }

    private int getTotal(char[][] charMatrix, Beam start) {
        var visited = new char[charMatrix.length][charMatrix[0].length];


        var beams = new ArrayList<Beam>();
        beams.add(start);

        var total = 0;
        var cache = new HashSet<Integer>();

        while (!beams.isEmpty()) {
            var newBeams = new ArrayList<Beam>();

            for (Beam beam : beams) {


                var moved = beam.move();

                for (Beam newBeam : moved) {

                    if (cache.contains(newBeam.hashCode())) {
                        continue;
                    }
                    cache.add(newBeam.hashCode());

                    if (!isValid(charMatrix, newBeam)) {
                        continue;
                    }

                    var c = charMatrix[newBeam.row][newBeam.col];
                    visited[newBeam.row][newBeam.col] = '#';

                    switch (c) {
                        case '.' -> newBeams.add(newBeam);
                        case '|' -> {
                            switch (newBeam.direction) {
                                case NORTH, SOUTH -> newBeams.add(newBeam);
                                case EAST, WEST -> {
                                    newBeam.direction = Direction.NORTH_SOUTH;
                                    newBeams.add(newBeam);
                                }
                                case default -> throw new RuntimeException("Unknown direction");
                            }
                        }
                        case '-' -> {
                            switch (newBeam.direction) {
                                case EAST, WEST -> newBeams.add(newBeam);
                                case NORTH, SOUTH -> {
                                    newBeam.direction = Direction.EAST_WEST;
                                    newBeams.add(newBeam);
                                }
                                case default -> throw new RuntimeException("Unknown direction");
                            }
                        }
                        case '/' -> {
                            switch (newBeam.direction) {
                                case EAST -> newBeam.direction = Direction.NORTH;
                                case WEST -> newBeam.direction = Direction.SOUTH;
                                case NORTH -> newBeam.direction = Direction.EAST;
                                case SOUTH -> newBeam.direction = Direction.WEST;
                                case default -> throw new RuntimeException("Unknown direction");
                            }
                            newBeams.add(newBeam);
                        }
                        case '\\' -> {
                            switch (newBeam.direction) {
                                case EAST -> newBeam.direction = Direction.SOUTH;
                                case WEST -> newBeam.direction = Direction.NORTH;
                                case NORTH -> newBeam.direction = Direction.WEST;
                                case SOUTH -> newBeam.direction = Direction.EAST;
                                case default -> throw new RuntimeException("Unknown direction");
                            }
                            newBeams.add(newBeam);
                        }
                        default -> throw new RuntimeException("Unknown direction");
                    }

                }


            }

            beams = newBeams;
        }

        total = 0;

        for (int i = 0; i < visited.length; i++) {

            var str = "";
            char[] chars = visited[i];
            for (int j = 0; j < chars.length; j++) {
                var c = chars[j];
                if (c == '#') {
                    total++;
                    c = charMatrix[i][j];
                }
                if (c == 0) {
                    c = ' ';
                }

                str += c;
            }

        }
        return total;
    }

    private static boolean isValid(char[][] charMatrix, Beam newBeam) {
        return newBeam.col >= 0 && newBeam.col < charMatrix[0].length && newBeam.row >= 0 && newBeam.row < charMatrix.length;
    }

    @Override
    public String runPart2() {

        var list = getStringInput("");
        var charMatrix = list.stream().map(s -> s.toCharArray()).toArray(char[][]::new);

        var max = 0;
        for (int i = 0; i < charMatrix.length; i++) {

            max = Math.max(max, getTotal(charMatrix, new Beam(i, -1, Direction.EAST)));
            max = Math.max(max, getTotal(charMatrix, new Beam(i, charMatrix.length, Direction.WEST)));
            max = Math.max(max, getTotal(charMatrix, new Beam(-1, i, Direction.SOUTH)));
            max = Math.max(max, getTotal(charMatrix, new Beam(charMatrix.length, i, Direction.NORTH)));
        }

        return formatResult(max);
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
