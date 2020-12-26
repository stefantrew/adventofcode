package trew.stefan.aoc2019.day20;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static trew.stefan.aoc2019.day20.Day20.DIRECTIONS;

@SuppressWarnings("DuplicatedCode")

@Slf4j
public class PortalMazeWalker {

    public List<MazePoint> backtrackPath(MazePoint cur) {
        List<MazePoint> path = new ArrayList<>();
        MazePoint iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.parent;
        }

        return path;
    }

    public void setVisited(Integer x, Integer y, boolean b) {
        visited[x][y] = b;
    }


    public boolean isExplored(Integer x, Integer y) {
        return visited[x][y];
    }

    private boolean[][] visited;
    int level = 0;


    public MazePoint solve(MazeMap maze, int level, MazePoint start, int depth) {

        if (depth == 7000) {
            return null;
        }

        this.level = level;
        this.visited = new boolean[maze.sizeH][maze.sizeV];
        LinkedList<MazePoint> nextToVisit
                = new LinkedList<>();
        nextToVisit.add(start);
//        log.info("Starting {} at X={} & Y={}", level, start.X, start.Y);

        MazePoint best = null;

        while (!nextToVisit.isEmpty()) {
            MazePoint cur = nextToVisit.remove();

            MazePoint currentActual = maze.map[cur.X][cur.Y];
            MazePoint portal = currentActual.portal;
            Direction portalDirection = currentActual.labelDirection;

            if (!maze.isValidLocation(cur.getX(), cur.getY())
                    || isExplored(cur.getX(), cur.getY())
            ) {
                continue;
            }


            if (maze.isWall(cur.getX(), cur.getY())) {
                setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (maze.isExit(cur.getX(), cur.getY()) && level == 0) {
//                log.info("Exiting {} ZZ at X={} & Y={}", level, cur.X, cur.Y);

                return cur;
            }


            for (int[] direction : DIRECTIONS) {
                MazePoint coordinate = new MazePoint(cur.getX() + direction[0], cur.getY() + direction[1], cur);

                Direction direction1 = Direction.values()[direction[2]];

                if (portal != null && direction1 == portalDirection) {

                    if (cur.X.equals(start.X) && cur.Y.equals(start.Y)) {
                        continue;
                    }
                    if (level == 0 && currentActual.isOuter && !currentActual.id.equals("ZZ")) {
                        continue;
                    } else if (level != 0 && currentActual.isOuter && (currentActual.id.equals("AA") || currentActual.id.equals("ZZ"))) {
                        continue;
                    }

                    coordinate.X = portal.X;
                    coordinate.Y = portal.Y;
                    coordinate.id = portal.id;
                    coordinate.setType(portal.getType());

                    PortalMazeWalker walker = new PortalMazeWalker();
                    if (currentActual.isOuter) {

//                        log.info("{} Return to level {} through {}", depth + 1, level - 1, coordinate.id);
                        coordinate = walker.solve(maze, level - 1, coordinate, depth + 1);
                        if (coordinate != null) {
                            if (best == null || backtrackPath(coordinate).size() < backtrackPath(best).size()) {
                                best = coordinate;
                            }
                            return coordinate;
                        }
                    } else {

//                        log.info("{} Recurse into level {} through {}", depth + 1, level + 1, coordinate.id);
                        coordinate = walker.solve(maze, level + 1, coordinate, depth + 1);
                        if (coordinate != null) {
                            if (best == null || backtrackPath(coordinate).size() < backtrackPath(best).size()) {
                                best = coordinate;
                            }
                            return coordinate;
                        }
                    }

                } else {
                    nextToVisit.add(coordinate);

                }
                setVisited(cur.getX(), cur.getY(), true);
            }
        }
        return best;
    }

}
