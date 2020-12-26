package trew.stefan.aoc2019.day20new;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static trew.stefan.aoc2019.day20.Day20.DIRECTIONS;

@Getter
class JumpHistoryNode {

    enum JumpDirection {
        INNER_TO_OUTER,
        OUTER_TO_INNER
    }

    int level;
    String id;
    JumpDirection direction;

    public JumpHistoryNode(int level, String id, MazePoint.Type type) {
        this.level = level;
        this.id = id;
        this.direction = type == MazePoint.Type.INNER_PORTAL ? JumpDirection.INNER_TO_OUTER : JumpDirection.OUTER_TO_INNER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JumpHistoryNode that = (JumpHistoryNode) o;
        return level == that.level &&
                Objects.equals(id, that.id) &&
                direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, id, direction);
    }
}

@SuppressWarnings("DuplicatedCode")
@Getter
@Slf4j
class MazeWalkerNode {

    private int X;
    private int Y;
    private int level;
    private String levelId;
    private MazeWalkerNode parent;
    private MazePoint reference;
    private int distance = 0;
    private List<JumpHistoryNode> history = new ArrayList<>();
    private boolean isInHistory = false;

    public MazeWalkerNode(MazePoint reference, MazeWalkerNode parent, int level, String levelId) {
        X = reference.getX();
        Y = reference.getY();
        this.reference = reference;
        this.parent = parent;
        this.levelId = levelId;
        this.level = level;
        if (parent != null) {
//            history.addAll(parent.history);
            distance = parent.distance + 1;
            MazePoint portalReference = parent.getReference().getPortal();
            if (reference.equals(portalReference)) {
                MazePoint.Type type = reference.getType();
//                JumpHistoryNode node = new JumpHistoryNode(level, reference.getId(), type);
//                isInHistory = history.contains(node);
//                if (isInHistory){
//                    log.info("Is in history {}", history);
//                }
//                history.add(node);
            }

        }
    }

    @Override
    public String toString() {
        return String.format("Level %3d - %2s => %2s <%3d,%3d> %s %s %d",
                level,
                levelId,
                reference.getId(),
                reference.getX(),
                reference.getY(),
                reference.toString(),
                reference.getType().name(),
                distance);
    }

    public String getId() {
        return reference.getId();
    }
}

@SuppressWarnings("DuplicatedCode")

@Slf4j
public class NewMazeWalker {

    private boolean[][] visited;
    int level = 0;

    public List<MazeWalkerNode> backtrackPath(MazeWalkerNode cur) {
        List<MazeWalkerNode> path = new ArrayList<>();
        MazeWalkerNode iter = cur;

        while (iter != null) {
            if (iter.getReference().isFree()) {
                path.add(iter);

            }
            iter = iter.getParent();
        }

        return path;
    }

    public void printBacktrackPath(MazeWalkerNode cur) {
        List<MazeWalkerNode> path = new ArrayList<>();
        MazeWalkerNode iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.getParent();
        }

        Collections.reverse(path);
        int i = 1;
        for (MazeWalkerNode node : path) {
            if (node.getReference().isFree()) {
//                log.info(String.format("%3d: %s", i++, node));
            } else if (node.getId().equals("CK") || node.getLevelId().equals("CK")) {
                log.info(String.format("   : %s", node));

            }
        }

    }

    public void setVisited(Integer x, Integer y, boolean b) {
        visited[x][y] = b;
    }


    public boolean isExplored(Integer x, Integer y) {
        return visited[x][y];
    }

    public MazeWalkerNode solve(MazeMap map) {
        return solve(map, 0, new MazeWalkerNode(map.getEntry(), null, 0, "AA"), 0);
    }

    private MazeWalkerNode solve(MazeMap maze, int level, MazeWalkerNode start, int depth) {


        if (depth == 15500) {
            return null;
        }
        MazeWalkerNode best = null;
        this.level = level;
        this.visited = new boolean[maze.getWidth()][maze.getHeight()];
        LinkedList<MazeWalkerNode> nextToVisit = new LinkedList<>();
        nextToVisit.add(start);


        while (!nextToVisit.isEmpty()) {
            MazeWalkerNode cur = nextToVisit.remove();
            MazePoint reference = cur.getReference();

            if (isExplored(cur.getX(), cur.getY())) {
                continue;
            }


            if (reference.isWall()) {
                setVisited(cur.getX(), cur.getY(), true);
                continue;
            }

            if (reference.isOuterPortal() && level != 0) {
                setVisited(cur.getX(), cur.getY(), true);
            }

            if (reference.isExit()) {
//                log.info("Exiting  -------------{} ZZ at {}", level, cur);
                if (level == 0) {
                    return cur;
                } else {
                    setVisited(cur.getX(), cur.getY(), true);
                    continue;
                }
            }


            for (int[] direction : DIRECTIONS) {
                MazePoint refCoord = maze.getPoint(cur.getX() + direction[0], cur.getY() + direction[1]);
                if (refCoord == null) {
                    continue;
                }

                MazeWalkerNode coordinate = new MazeWalkerNode(refCoord, cur, level, start.getId());

                if (isExplored(coordinate.getX(), coordinate.getY())) {
                    continue;
                }


                if (refCoord.isInnerPortal()) {
                    setVisited(refCoord.getX(), refCoord.getY(), true);
                    NewMazeWalker walker = new NewMazeWalker();
                    coordinate = walker.solve(maze, level + 1, new MazeWalkerNode(refCoord.getPortal(), coordinate, level + 1, start.getId()), depth + 1);
//                    if (coordinate != null) {
//                        return coordinate;
//                    }
                    if (coordinate != null && (best == null || coordinate.getDistance() < best.getDistance())) {
                        best = coordinate;
                    }

                } else if (refCoord.isOuterPortal() && level != 0) {
                    setVisited(refCoord.getX(), refCoord.getY(), true);
                    NewMazeWalker walker = new NewMazeWalker();
                    coordinate = walker.solve(maze, level - 1, new MazeWalkerNode(refCoord.getPortal(), coordinate, level - 1, start.getId()), depth + 1);
//                    if (coordinate != null && (best == null || coordinate.getDistance() < best.getDistance())) {
//                        best = coordinate;
//                    }
                    if (coordinate != null) {
                        return coordinate;
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
