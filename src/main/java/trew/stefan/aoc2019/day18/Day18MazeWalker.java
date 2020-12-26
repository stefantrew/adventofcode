package trew.stefan.aoc2019.day18;

import lombok.extern.slf4j.Slf4j;
import trew.stefan.utils.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@SuppressWarnings("DuplicatedCode")
public class Day18MazeWalker {

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

    private boolean[][] visited;

    public void setVisited(Integer x, Integer y, boolean b) {
        visited[x][y] = b;
    }

    public boolean isExplored(Integer x, Integer y) {
        return visited[x][y];
    }


    public List<Point> solve(MazeMap maze, char key, byte quadrant) {
        visited = new boolean[maze.sizeH][maze.sizeV];

        LinkedList<Point> nextToVisit = new LinkedList<>();
        Point start = maze.getEntries()[quadrant];
        Point end = maze.getKey(key);
        nextToVisit.add(start);
        while (!nextToVisit.isEmpty()) {
            Point cur = nextToVisit.remove();
            if (!maze.isValidLocation(cur.getX(), cur.getY(), key)
                    || isExplored(cur.getX(), cur.getY())
            ) {
                continue;
            }

            if (maze.isWall(cur.getX(), cur.getY())) {
                setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (end.equals(cur)) {
                return backtrackPath(cur);
            }

            for (int[] direction : DIRECTIONS) {
                Point coordinate = new Point(cur.getX() + direction[0], cur.getY() + direction[1], cur, maze.getQuadrant(cur.getX() + direction[0], cur.getY() + direction[1]));
                nextToVisit.add(coordinate);
                setVisited(cur.getX(), cur.getY(), true);
            }
        }
        return Collections.emptyList();
    }

}
