package trew.stefan.aoc2020.day24;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.AOCDay;
import trew.stefan.Day;
import trew.stefan.utils.InputReader2019;
import trew.stefan.utils.InputReader2020;
import trew.stefan.utils.Point;

import java.util.*;

@Slf4j
public class Day24 implements AOCDay {
    Tile[][] grid = new Tile[250][250];

    Tile getTile(Integer X, Integer Y) {

        if (grid[X + 125][Y + 125] == null) {
            grid[X + 125][Y + 125] = new Tile(X, Y);
        }
        return grid[X + 125][Y + 125];
    }

    class Tile {
        private Integer X;
        private Integer Y;
        //        List<Tile> result = new ArrayList<>();
        Tile[] result2 = new Tile[6];

        public Tile(Integer x, Integer y) {
            X = x;
            Y = y;
        }

        Tile[] getAdjacent() {
            if (result2[0] == null) {

                result2[0] = getTile(X + 1, Y + 1);
                result2[1] = getTile(X - 1, Y + 1);
                result2[2] = getTile(X + 1, Y - 1);
                result2[3] = getTile(X - 1, Y - 1);
                result2[4] = getTile(X - 2, Y);
                result2[5] = getTile(X + 2, Y);

            }
            return result2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile point = (Tile) o;
            return Objects.equals(X, point.X) &&
                    Objects.equals(Y, point.Y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(X, Y);
        }

    }

    private int day = 24;


    private void getPoints(Set<Tile> points, List<String> lines) {
        for (String line : lines) {

            int x = 0;
            int y = 0;
//            log.info("{}", line);
            char prev = ' ';
            for (char c : line.toCharArray()) {
                if (c == 'n' || c == 's') {
                    prev = c;
                    continue;
                }

                switch (prev) {
                    case ' ':
                        if (c == 'e') {
                            x += 2;
                        } else {
                            x -= 2;
                        }
                        break;
                    case 'n':
                        if (c == 'e') {
                            x += 1;
                        } else {
                            x -= 1;
                        }
                        y--;
                        break;
                    case 's':
                        if (c == 'e') {
                            x += 1;
                        } else {
                            x -= 1;
                        }
                        y++;
                        break;
                }
                prev = ' ';
//                log.info("{} {}", x, y);
            }

            Tile point = getTile(x, y);
            if (points.contains(point)) {
                points.remove(point);
            } else {
                points.add(point);
            }
        }
    }

    @Override
    public String runPart1() {
        return run(0);
    }

    @Override
    public String runPart2() {

        return run(100);
    }

    public String run(int x) {
        Set<Tile> blackTiles = new HashSet<>();

        List<String> lines = InputReader2020.readStrings(day, "");

        getPoints(blackTiles, lines);

        while (x-- > 0) {
            blackTiles = iterate(blackTiles);


        }
        return String.valueOf(blackTiles.size());
    }

    private Set<Tile> iterate(Set<Tile> blackTiles) {
        Set<Tile> whiteTiles = new HashSet<>();
        Set<Tile> newTiles = new HashSet<>();
        for (Tile tile : blackTiles) {
            Tile[] adjacent = tile.getAdjacent();
            int countBlack = 0;
            for (Tile sideTile : adjacent) {
                if (blackTiles.contains(sideTile)) {
                    countBlack++;
                } else {
                    whiteTiles.add(sideTile);
                }
            }

            if (countBlack == 1 || countBlack == 2) {
                newTiles.add(tile);
            }
        }

        for (Tile tile : whiteTiles) {
            Tile[] adjacent = tile.getAdjacent();
            int countBlack = 0;
            for (Tile sideTile : adjacent) {
                if (blackTiles.contains(sideTile)) {
                    countBlack++;
                }
            }

            if (countBlack == 2) {
                newTiles.add(tile);
            }

        }

        return newTiles;
    }
}
