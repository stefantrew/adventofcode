package trew.stefan.aoc2019.day15;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Day15MazeWalker {

    private static int[][] DIRECTIONS
            = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    private List<Point> backtrackPath(Point cur) {
        List<Point> path = new ArrayList<>();
        Point iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.parent;
        }

        return path;
    }

    public List<Point> solve(Day15.FloorMap maze) {
        LinkedList<Point> nextToVisit
                = new LinkedList<>();
        Point start = maze.getEntry();
        nextToVisit.add(start);

        while (!nextToVisit.isEmpty()) {
            Point cur = nextToVisit.remove();

            if (!maze.isValidLocation(cur.getX(), cur.getY())
                    || maze.isExplored(cur.getX(), cur.getY())
            ) {
                continue;
            }

            if (maze.isWall(cur.getX(), cur.getY())) {
                maze.setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (maze.isExit(cur.getX(), cur.getY())) {
                return backtrackPath(cur);
            }

            for (int[] direction : DIRECTIONS) {
                Point coordinate = new Point(cur.getX() + direction[0], cur.getY() + direction[1], cur);
                nextToVisit.add(coordinate);
                maze.setVisited(cur.getX(), cur.getY(), true);
            }
        }
        return Collections.emptyList();
    }

}
