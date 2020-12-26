package trew.stefan.aoc2019.day20;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static trew.stefan.aoc2019.day20.Day20.DIRECTIONS;

@Slf4j
@SuppressWarnings("DuplicatedCode")
public class MazeMap {

    @Getter
    int sizeV;
    @Getter
    int sizeH;
    @Getter
    MazePoint[][] map;
    @Getter
    private MazePoint entry;
    private MazePoint exit;


    public MazeMap(int sizeH, int sizeV) {
        this.sizeH = sizeH;
        this.sizeV = sizeV;
        this.map = new MazePoint[sizeH][sizeV];
//        log.info("Building maze {} x {}", sizeH, sizeV);

    }

    public void printMap() {

        log.info("---------------------------------------------------------------");
        for (int i = 0; i < sizeV; i++) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < sizeH; j++) {
                if (map[j][i] == null) {
                    builder.append(" ");
                } else {
                    builder.append(map[j][i]);

                }
            }
            log.info(builder.toString());
        }
        log.info("---------------------------------------------------------------");
    }


    public void linkPoints() {
        log.info("---------------------------------------------------------------");
        Map<String, MazePoint> portals = new HashMap<>();
        for (int i = 2; i < sizeV - 2; i++) {
            for (int j = 2; j < sizeH - 2; j++) {
                MazePoint point = map[j][i];
                if (point == null || point.getType() != MazePoint.Type.OPEN) {
                    continue;
                }

                for (int[] direction : DIRECTIONS) {
                    MazePoint other = map[point.getX() + direction[0]][point.getY() + direction[1]];
                    if (other != null && other.getType() == MazePoint.Type.LABEL) {
                        MazePoint other2 = map[point.getX() + 2 * direction[0]][point.getY() + 2 * direction[1]];
                        Direction direction1 = Direction.NORTH;
                        switch (direction[2]) {

                            case 0:
                                direction1 = Direction.SOUTH;
                                break;
                            case 1:
                                direction1 = Direction.EAST;
                                break;
                            case 2:
                                direction1 = Direction.NORTH;
                                break;
                            case 3:
                                direction1 = Direction.WEST;
                                break;
                        }

                        String label1 = other.label;
                        String label2 = other2.label;

                        if (label1.equals("A") && label2.equals("A")) {
                            entry = point;
                            point.id = "AA";
                        } else if (label1.equals("Z") && label2.equals("Z")) {
                            exit = point;
                            point.id = "ZZ";
                        } else {
                            String id = label1.compareTo(label2) < 0 ? label1 + label2 : label2 + label1;
                            point.id = id;
                            point.labelDirection = direction1;
                            if (other2.X == 0 || other2.Y == 0 || other2.X == sizeH - 1 || other2.Y == sizeV - 1) {
                                point.isOuter = true;
                            } else {
                                point.isOuter = false;
                            }
                            log.info("Found {} {} isOuter {} <{},{}>", id, direction1.name(), point.isOuter, other2.X, other2.Y);

                            if (portals.containsKey(id)) {
                                MazePoint otherPoint = portals.get(id);
                                otherPoint.portal = point;
                                point.portal = otherPoint;
                            } else {
                                portals.put(id, point);
                            }
                        }


                    }
                }


            }
        }
        log.info("---------------------------------------------------------------");


    }

    public boolean isWall(Integer x, Integer y) {
        return map[x][y].getType() == MazePoint.Type.WALL;
    }


    public boolean isExit(Integer x, Integer y) {
        return exit.getX().equals(x) && exit.getY().equals(y);
    }
 public boolean isValidLocation(Integer x, Integer y) {
        return map[x][y].getType() == MazePoint.Type.OPEN;
    }


}
